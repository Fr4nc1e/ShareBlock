package com.code.block.core.domain.state

data class PageState<T>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
)
