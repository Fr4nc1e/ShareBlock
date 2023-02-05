package com.code.block.feature.profile.presentation.followinfoscreen.type

sealed class FollowType(val type: String) {
    object Followings : FollowType("followings")
    object Followers : FollowType("followers")
}
