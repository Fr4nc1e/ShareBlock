package com.code.block.feature.chat.presentation.messagescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.core.domain.state.TextFieldState
import com.code.block.core.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor() : ViewModel() {

    private val _messageTextFieldState = mutableStateOf(TextFieldState())
    val messageTextFieldState: State<TextFieldState> = _messageTextFieldState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = mutableStateOf(MessageState())
    val state: State<MessageState> = _state

    fun onEvent(event: MessageEvent) {
        when (event) {
            is MessageEvent.EnteredMessage -> {
                _messageTextFieldState.value = _messageTextFieldState.value.copy(
                    text = event.messageText
                )
            }
            MessageEvent.SendMessage -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.SendMessage)
                }
            }
        }
    }
}
