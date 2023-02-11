package com.code.block.feature.chat.presentation.messagescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.core.domain.resource.Resource
import com.code.block.core.domain.state.PageState
import com.code.block.core.domain.state.TextFieldState
import com.code.block.core.usecase.GetOwnUserIdUseCase
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.UiText
import com.code.block.core.util.ui.paging.PaginationImpl
import com.code.block.feature.chat.domain.model.Message
import com.code.block.feature.chat.presentation.messagescreen.event.MessageEvent
import com.code.block.feature.chat.presentation.messagescreen.event.MessageUpdateEvent
import com.code.block.usecase.chat.ChatUseCases
import com.code.block.usecase.profile.ProfileUseCases
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
    private val savedStateHandle: SavedStateHandle,
    private val profileUseCases: ProfileUseCases,
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
) : ViewModel() {

    private val _messageTextFieldState = mutableStateOf(TextFieldState())
    val messageTextFieldState: State<TextFieldState> = _messageTextFieldState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _messageUpdatedEvent = MutableSharedFlow<MessageUpdateEvent>(replay = 1)
    val messageReceived = _messageUpdatedEvent.asSharedFlow()

    private val _pagingState = mutableStateOf<PageState<Message>>(PageState())
    val pagingState: State<PageState<Message>> = _pagingState

    private val _state = mutableStateOf(MessageState())
    val state: State<MessageState> = _state

    private val _ownProfilePicture = MutableStateFlow<String?>(null)
    val ownProfilePicture = _ownProfilePicture.asStateFlow()

    val ownUserId
        get() = getOwnUserIdUseCase()

    private val pagination = PaginationImpl(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(isLoading = isLoading)
        },
        onRequest = { nextPage ->
            savedStateHandle.get<String>("chatId")?.let { chatId ->
                chatUseCases.getMessagesForChatUseCase(
                    chatId,
                    nextPage,
                )
            } ?: Resource.Error(uiText = UiText.unknownError())
        },
        onError = { errorUiText ->
            _eventFlow.emit(UiEvent.SnackBarEvent(errorUiText))
        },
        onSuccess = { messages ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + messages,
                endReached = messages.isEmpty(),
                isLoading = false,
            )
            viewModelScope.launch {
                _messageUpdatedEvent.emit(MessageUpdateEvent.MessagePageLoaded)
            }
        },
    )

    fun onEvent(event: MessageEvent) {
        when (event) {
            is MessageEvent.EnteredMessage -> {
                _messageTextFieldState.value = _messageTextFieldState.value.copy(
                    text = event.messageText,
                )
                _state.value = state.value.copy(
                    canSendMessage = event.messageText.isNotBlank(),
                )
            }
            MessageEvent.SendMessage -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.SendMessage)
                }
            }
        }
    }

    init {
        chatUseCases.initRepositoryUseCase()
        loadNextMessages()
        observeChatEvents()
        observeChatMessages()
        getOwnProfilePicture()
    }

    private fun observeChatMessages() {
        viewModelScope.launch {
            chatUseCases.observeMessages()
                .collect { message ->
                    _pagingState.value = pagingState.value.copy(
                        items = pagingState.value.items + message,
                    )
                    _messageUpdatedEvent.emit(MessageUpdateEvent.SingleMessageUpdate)
                }
        }
    }

    private fun observeChatEvents() {
        chatUseCases.observeChatEvents()
            .onEach { event ->
                when (event) {
                    is WebSocket.Event.OnConnectionOpened<*> -> {
                        println("Connection was opened")
                    }
                    is WebSocket.Event.OnConnectionFailed -> {
                        println("Connection failed: ${event.throwable}")
                    }
                    else -> Unit
                }
            }.launchIn(viewModelScope)
    }

    fun sendMessage() {
        val toId = savedStateHandle.get<String>("remoteUserId") ?: return
        if (messageTextFieldState.value.text.isBlank()) {
            return
        }
        val chatId = savedStateHandle.get<String>("chatId")
        chatUseCases.sendMessage(toId, messageTextFieldState.value.text, chatId)
        _messageTextFieldState.value = TextFieldState()
        _state.value = state.value.copy(
            canSendMessage = false,
        )
        viewModelScope.launch {
            _messageUpdatedEvent.emit(MessageUpdateEvent.MessageSent)
        }
    }

    private fun getOwnProfilePicture() {
        viewModelScope.launch {
            _ownProfilePicture.value = profileUseCases
                .getProfileUseCase(
                    getOwnUserIdUseCase(),
                )
                .data
                ?.profilePictureUrl
        }
    }

    fun loadNextMessages() {
        viewModelScope.launch {
            pagination.loadNextItems()
        }
    }
}
