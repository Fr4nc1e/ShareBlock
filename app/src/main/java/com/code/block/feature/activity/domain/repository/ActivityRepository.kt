package com.code.block.feature.activity.domain.repository

import androidx.paging.PagingData
import com.code.block.core.domain.model.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    val activities: Flow<PagingData<Activity>>
}
