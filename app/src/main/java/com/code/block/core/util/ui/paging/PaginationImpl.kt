package com.code.block.core.util.ui.paging

import com.code.block.core.domain.resource.Resource
import com.code.block.core.util.ui.UiText

class PaginationImpl<T>(
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextPage: Int) -> Resource<List<T>>,
    private val onError: suspend (UiText) -> Unit,
    private val onSuccess: (items: List<T>) -> Unit
) : Pagination<T> {
    var page = 0

    override suspend fun loadNextItems() {
        onLoadUpdated(true)
        when (val result = onRequest(page)) {
            is Resource.Error -> {
                onError(result.uiText ?: UiText.unknownError())
                onLoadUpdated(false)
            }
            is Resource.Success -> {
                val items = result.data ?: emptyList()
                page++
                onSuccess(items)
                onLoadUpdated(false)
            }
        }
    }
}
