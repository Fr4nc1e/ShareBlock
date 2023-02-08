package com.code.block.feature.activity.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.code.block.core.domain.model.Activity
import com.code.block.core.util.Constants
import com.code.block.feature.activity.data.source.ActivityApi
import retrofit2.HttpException
import java.io.IOException

class ActivitySource(
    private val api: ActivityApi,
) : PagingSource<Int, Activity>() {

    private var currentPage = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Activity> {
        return try {
            val nextPage = params.key ?: currentPage
            val activities = api.getActivities(
                page = nextPage,
                pageSize = Constants.PAGE_SIZE_POSTS,
            )
            LoadResult.Page(
                data = activities.map { it.toActivity() },
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (activities.isEmpty()) null else currentPage + 1,
            ).also { currentPage++ }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Activity>): Int? {
        return state.anchorPosition
    }
}
