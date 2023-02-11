package com.code.block.core.util.ui.paging

interface Pagination<T> {

    suspend fun loadNextItems()
}
