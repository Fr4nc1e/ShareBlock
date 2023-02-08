package com.code.block.feature.activity.presentation.activityscreen

import com.code.block.core.domain.model.Activity

data class ActivityState(
    val activities: List<Activity> = emptyList(),
    val isLoading: Boolean = false,
)
