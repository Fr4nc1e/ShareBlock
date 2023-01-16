package com.code.block.feature.post.presentation.createpostscreen

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.core.domain.state.TextFieldState
import com.code.block.feature.post.domain.usecase.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
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
                        postUseCases.createPostUseCase(
                            description = descriptionState.value.text,
                            contentUri = it
                        )
                    }
                }
            }
        }
    }
}
