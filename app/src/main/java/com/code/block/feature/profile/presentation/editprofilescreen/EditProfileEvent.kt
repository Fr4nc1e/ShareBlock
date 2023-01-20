package com.code.block.feature.profile.presentation.editprofilescreen

import android.net.Uri

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
    data class CropProfilePicture(val uri: Uri?) : EditProfileEvent()
    data class CropBannerImage(val uri: Uri?) : EditProfileEvent()
    object EditionCompleted : EditProfileEvent()
}
