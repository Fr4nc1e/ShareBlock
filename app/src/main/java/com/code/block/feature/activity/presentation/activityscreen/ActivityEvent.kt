package com.code.block.feature.activity.presentation.activityscreen

sealed class ActivityEvent {
    data class ClickedOnUser(val userId: String) : ActivityEvent()
    data class ClickedOnParent(val parentId: String) : ActivityEvent()
    object Refresh : ActivityEvent()
}
