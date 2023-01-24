package com.code.block.feature.post.presentation.postdetailscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.R
import com.code.block.core.domain.state.TextFieldState
import com.code.block.core.domain.util.Resource
import com.code.block.core.util.UiEvent
import com.code.block.core.util.UiText
import com.code.block.feature.post.domain.usecase.PostUseCases
import com.code.block.feature.post.presentation.postdetailscreen.components.CommentError
import com.code.block.feature.post.presentation.postdetailscreen.components.CommentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(PostDetailState())
    val state: State<PostDetailState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _commentState = mutableStateOf(CommentState())
    val commentState: State<CommentState> = _commentState

    private val _commentTextState = mutableStateOf(TextFieldState())
    val commentTextState: State<TextFieldState> = _commentTextState

    init {
        savedStateHandle.get<String>("postId")?.let { postId ->
            loadPostDetails(postId)
            loadCommentsForPost(postId)
        }
    }

    fun onEvent(event: PostDetailEvent) {
        when (event) {
            is PostDetailEvent.LikePost -> {
            }
            is PostDetailEvent.Comment -> {
                createComment(
                    postId = savedStateHandle.get<String>("postId") ?: "",
                    comment = commentTextState.value.text
                )
            }
            is PostDetailEvent.LikeComment -> {
            }
            is PostDetailEvent.SharePost -> {
            }
            is PostDetailEvent.EnteredComment -> {
                _commentTextState.value = _commentTextState.value.copy(
                    text = event.comment,
                    error = if (event.comment.isBlank()) CommentError.FieldEmpty else null
                )
            }
        }
    }

    private fun createComment(postId: String, comment: String) {
        viewModelScope.launch {
            _commentState.value = _commentState.value.copy(
                isLoading = true
            )
            delay(500L)
            postUseCases.createCommentUseCase(
                postId = postId,
                comment = comment
            ).apply {
                when (this) {
                    is Resource.Error -> {
                        _eventFlow.emit(
                            UiEvent.SnackBarEvent(uiText = this.uiText ?: UiText.unknownError())
                        )
                        _commentState.value = _commentState.value.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Success -> {
                        _eventFlow.emit(
                            UiEvent.SnackBarEvent(uiText = UiText.StringResource(R.string.comment_created))
                        )
                        _commentState.value = _commentState.value.copy(
                            isLoading = false
                        )
                        _commentTextState.value = TextFieldState()
                        loadCommentsForPost(postId)
                    }
                }
            }
        }
    }

    private fun loadPostDetails(postId: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoadingPost = true
            )
            when (val result = postUseCases.getPostDetailUseCase(postId)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        post = result.data,
                        isLoadingPost = false
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoadingPost = false
                    )
                    _eventFlow.emit(
                        UiEvent.SnackBarEvent(
                            result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }

    private fun loadCommentsForPost(postId: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoadingComments = true
            )
            when (val result = postUseCases.getCommentsForPostUseCase(postId)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        comments = result.data ?: emptyList(),
                        isLoadingComments = false
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoadingComments = false
                    )
                }
            }
        }
    }
}
