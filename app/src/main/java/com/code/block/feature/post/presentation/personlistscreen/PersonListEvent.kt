package com.code.block.feature.post.presentation.personlistscreen

sealed class PersonListEvent {
    data class FollowUser(val userId: String) : PersonListEvent()
}
