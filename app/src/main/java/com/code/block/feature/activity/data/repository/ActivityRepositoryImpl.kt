package com.code.block.feature.activity.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.code.block.core.domain.model.Activity
import com.code.block.core.util.Constants
import com.code.block.feature.activity.data.source.api.ActivityApi
import com.code.block.feature.activity.data.source.paging.ActivitySource
import com.code.block.feature.activity.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow

class ActivityRepositoryImpl(
    private val api: ActivityApi,
) : ActivityRepository {
    override val activities: Flow<PagingData<Activity>>
        get() = Pager(PagingConfig(pageSize = Constants.PAGE_SIZE_POSTS)) {
            ActivitySource(api)
        }.flow
}
