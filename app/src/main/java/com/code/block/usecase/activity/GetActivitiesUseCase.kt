package com.code.block.usecase.activity

import androidx.paging.PagingData
import com.code.block.core.domain.model.Activity
import com.code.block.feature.activity.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow

class GetActivitiesUseCase(
    private val repository: ActivityRepository
) {
    operator fun invoke(): Flow<PagingData<Activity>> {
        return repository.activities
    }
}
