package com.code.block.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.code.block.R
import com.code.block.core.presentation.ui.theme.HintGray
import com.code.block.core.utils.Constants

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    collapsedMaxLine: Int = Constants.MAX_POST_DESCRIPTION_LINES
) {
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableStateOf(0) }

    Box(
        modifier = modifier
            .clickable(clickable) {
                isExpanded = !isExpanded
            }
    ) {
        Text(
            text = buildAnnotatedString {
                val showMoreText = stringResource(id = R.string.show_more)
                val showLessText = stringResource(id = R.string.show_less)
                if (clickable) {
                    if (isExpanded) {
                        append(text)
                        withStyle(
                            SpanStyle(
                                color = HintGray
                            )
                        ) {
                            append(" ")
                            append(showLessText)
                        }
                    } else {
                        val adjustText = text
                            .substring(
                                startIndex = 0,
                                endIndex = lastCharIndex
                            )
                            .dropLast(showMoreText.length)
                            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                        append(adjustText)
                        withStyle(
                            SpanStyle(
                                color = HintGray
                            )
                        ) {
                            append(showMoreText)
                        }
                    }
                } else {
                    append(text)
                }
            },
            style = MaterialTheme.typography.body1,
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            onTextLayout = {
                if (!isExpanded && it.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = it.getLineEnd(collapsedMaxLine - 1)
                }
            }
        )
    }
}
