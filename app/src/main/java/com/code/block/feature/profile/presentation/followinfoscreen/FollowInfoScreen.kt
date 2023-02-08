package com.code.block.feature.profile.presentation.followinfoscreen

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.R
import com.code.block.core.presentation.components.Screen
import com.code.block.core.presentation.components.StandardTopBar
import com.code.block.core.presentation.ui.theme.IconSizeMedium
import com.code.block.core.presentation.ui.theme.SpaceLarge
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.asString
import com.code.block.feature.profile.presentation.searchscreen.components.UserProfileItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FollowInfoScreen(
    onNavigate: (String) -> Unit = {},
    scaffoldState: ScaffoldState,
    viewModel: FollowInfoViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.SnackBarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.uiText.asString(context),
                    )
                }
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        StandardTopBar(
            title = {
                Text(
                    text = stringResource(id = R.string.follow_info),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                )
            },
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(SpaceLarge),
        ) {
            items(state.users) {
                UserProfileItem(
                    user = it,
                    ownUserId = viewModel.ownUserId.value,
                    actionIcon = {
                        Icon(
                            imageVector = if (it.isFollowing) {
                                Icons.Default.PersonRemove
                            } else {
                                Icons.Default.PersonAdd
                            },
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground,
                            modifier = Modifier.size(IconSizeMedium),
                        )
                    },
                    onItemClick = {
                        onNavigate(Screen.ProfileScreen.route + "?userId=${it.userId}")
                    },
                    onActionItemClick = {
                        viewModel.onEvent(FollowInfoEvent.FollowUser(it.userId))
                    },
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
            }
        }
    }
}
