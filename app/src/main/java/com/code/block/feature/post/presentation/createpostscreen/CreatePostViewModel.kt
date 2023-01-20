package com.code.block.feature.post.presentation.createpostscreen

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.R
import com.code.block.core.domain.state.TextFieldState
import com.code.block.core.domain.util.Resource
import com.code.block.core.util.UiEvent
import com.code.block.core.util.UiText
import com.code.block.feature.post.domain.usecase.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val postUseCases: PostUseCases
) : ViewModel() {

    private val _descriptionState = mutableStateOf(TextFieldState())
    val descriptionState: State<TextFieldState> = _descriptionState

    private val _chosenContentUri = mutableStateOf<Uri?>(null)
    val chosenContentUri: State<Uri?> = _chosenContentUri

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: CreatePostEvent) {
        when (event) {
            is CreatePostEvent.EnteredDescription -> {
                _descriptionState.value = _descriptionState.value.copy(
                    text = event.description
                )
            }
            is CreatePostEvent.InputContent -> {
                _chosenContentUri.value = event.contentUri
            }
            is CreatePostEvent.CropImage -> {
                _chosenContentUri.value = event.contentUri
                println("URI IS ${event.contentUri}")
            }
            is CreatePostEvent.Post -> {
                chosenContentUri.value?.let {
                    viewModelScope.launch {
                        _isLoading.value = true
                        val result = postUseCases.createPostUseCase(
                            description = descriptionState.value.text,
                            contentUri = chosenContentUri.value
                        )
                        when (result) {
                            is Resource.Success -> {
                                _eventFlow.emit(
                                    UiEvent.SnackBarEvent(
                                        uiText = UiText.StringResource(R.string.post_created)
                                    )
                                )
                                _eventFlow.emit(UiEvent.NavigateUp)
                            }
                            is Resource.Error -> {
                                _eventFlow.emit(
                                    UiEvent.SnackBarEvent(
                                        result.uiText ?: UiText.unknownError()
                                    )
                                )
                            }
                        }
                        _isLoading.value = false
                    }
                }
            }
        }
    }
}
