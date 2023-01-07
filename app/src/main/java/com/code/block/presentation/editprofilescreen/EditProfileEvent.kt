package com.code.block.presentation.editprofilescreen

sealed class EditProfileEvent {
    data class EnteredUsername(val username: String) : EditProfileEvent()
    data class EnteredQq(val qq: String) : EditProfileEvent()
    data class EnteredWeChat(val weChat: String) : EditProfileEvent()
    data class EnteredGitHub(val gitHub: String) : EditProfileEvent()
    data class EnteredBio(val bio: String) : EditProfileEvent()
    object ClearUsername : EditProfileEvent()
    object ClearQq : EditProfileEvent()
    object ClearWeChat : EditProfileEvent()
    object ClearGitHub : EditProfileEvent()
    object ClearBio : EditProfileEvent()
    object EditionCompleted : EditProfileEvent()
}
