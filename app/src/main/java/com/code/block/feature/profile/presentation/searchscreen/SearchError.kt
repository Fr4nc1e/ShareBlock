package com.code.block.feature.profile.presentation.searchscreen

import com.code.block.core.domain.parent.Error
import com.code.block.core.util.ui.UiText

data class SearchError(val message: UiText) : Error()
