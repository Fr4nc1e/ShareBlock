package com.code.block.core.utils

typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(
    val data: T? = null,
    val uiText: UiText? = null
) {
    class Success<T>(data: T? = null, uiText: UiText?) : Resource<T>(data, uiText)
    class Error<T>(data: T? = null, uiText: UiText?) : Resource<T>(data, uiText)
}
