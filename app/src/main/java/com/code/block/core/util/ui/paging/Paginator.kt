package com.code.block.core.util.ui.paging

interface Paginator<T> {

    suspend fun loadNextItems()
}
