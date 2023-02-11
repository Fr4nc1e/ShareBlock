package com.code.block.feature.profile.presentation.searchscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.R
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.components.StandardTextField
import com.code.block.core.presentation.ui.theme.IconSizeMedium
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.feature.profile.presentation.searchscreen.components.UserProfileItem

@Composable
fun SearchScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: SearchScreeViewModel = hiltViewModel(),
) {
    val state = viewModel.searchState
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(state.value.showKeyboard) {
        focusRequester.requestFocus()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SpaceMedium),
            ) {
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester = focusRequester),
                    text = viewModel.searchTextFieldState.value.text,
                    hint = stringResource(id = R.string.search_hint),
                    error = "",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = stringResource(R.string.search_icon),
                            tint = MaterialTheme.colors.onBackground,
                            modifier = Modifier.size(IconSizeMedium),
                        )
                    },
                    trailingIcon = {
                        if (viewModel.searchTextFieldState.value.text.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    viewModel.onEvent(SearchEvent.ClearSearchText)
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = stringResource(id = R.string.clear_text),
                                    tint = MaterialTheme.colors.onBackground,
                                    modifier = Modifier.size(IconSizeMedium),
                                )
                            }
                        }
                    },
                    onValueChange = {
                        viewModel.onEvent(SearchEvent.EnteredSearchText(it))
                        viewModel.onEvent(SearchEvent.Search)
                    },
                )

                Spacer(modifier = Modifier.height(SpaceMedium))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(state.value.userItems) {
                        UserProfileItem(
                            user = it,
                            actionIcon = {
                                IconButton(
                                    onClick = {
                                        viewModel.onEvent(SearchEvent.FollowMotion(userId = it.userId))
                                    },
                                    modifier = Modifier.size(IconSizeMedium),
                                ) {
                                    Icon(
                                        imageVector = if (it.isFollowing) {
                                            Icons.Default.PersonRemove
                                        } else { Icons.Default.PersonAdd },
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.onBackground,
                                    )
                                }
                            },
                            onItemClick = {
                                onNavigate(Screen.ProfileScreen.route + "?userId=${it.userId}")
                            },
                        )
                        Spacer(modifier = Modifier.height(SpaceMedium))
                    }
                }
            }
        }
        if (state.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}
