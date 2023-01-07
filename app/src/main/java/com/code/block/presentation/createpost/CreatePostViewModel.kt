package com.code.block.presentation.createpost

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(CreatePostState())
    val state: State<CreatePostState> = _state

    fun onEvent(event: CreatePostEvent) {
        when (event) {
            is CreatePostEvent.EnteredDescription -> {
                _state.value = _state.value.copy(
                    description = event.description
                )
            }
            is CreatePostEvent.InputPicture -> {
                _state.value = _state.value.copy(
                    imageUrl = event.imageUrl
                )
            }
            is CreatePostEvent.Post -> {}
        }
    }
}
