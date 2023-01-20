package com.code.block.feature.profile.presentation.searchscreen

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.R
import com.code.block.core.domain.model.User
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.components.StandardTextField
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.presentation.ui.theme.IconSizeMedium
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.feature.profile.presentation.searchscreen.components.SearchResultItem

@Composable
fun SearchScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: SearchScreeViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(state.value.showKeyboard) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardTopBar(
            title = {
                Text(
                    text = stringResource(id = R.string.search_for_users),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceMedium)
        ) {
            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester = focusRequester),
                text = state.value.searchText,
                hint = stringResource(id = R.string.search_hint),
                error = when (state.value.searchError) {
                    is SearchState.SearchError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty)
                    }
                    else -> ""
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = stringResource(R.string.search_icon),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium)
                    )
                },
                trailingIcon = {
                    if (state.value.searchText.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(SearchEvent.ClearSearchText)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = stringResource(id = R.string.clear_text),
                                tint = MaterialTheme.colors.onBackground,
                                modifier = Modifier.size(IconSizeMedium)
                            )
                        }
                    }
                },
                onValueChange = {
                    viewModel.onEvent(SearchEvent.EnteredSearchText(it))
                }
            )

            Spacer(modifier = Modifier.height(SpaceMedium))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(10) {
                    SearchResultItem(
                        user = User(
                            userId = "63c0e5a1b01a287e31cd1cd1",
                            profilePictureUrl = "http://172.28.211.51:8081/profile_pictures/b295fa5e-f2af-420c-be16-3fa266f02fc1.png",
                            username = "Superman",
                            description = "",
                            followerCount = 10,
                            followingCount = 10,
                            postCount = 10
                        ),
                        actionIcon = {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground,
                                modifier = Modifier.size(IconSizeMedium)
                            )
                        },
                        onItemClick = {
                            onNavigate(Screen.ProfileScreen.route + "?userId=63ca0e2a3c66b97823d2952b")
                        }
                    )
                    Spacer(modifier = Modifier.height(SpaceMedium))
                }
            }
        }
    }
}
