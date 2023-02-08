package com.code.block.feature.profile.presentation.profilescreen.components

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.code.block.R
import com.code.block.core.domain.model.User
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.core.presentation.ui.theme.SpaceSmall

@Composable
fun ProfileInteract(
    user: User,
    color: Color?,
    modifier: Modifier = Modifier,
    isOwnProfile: Boolean = true,
    isFollowing: Boolean = true,
    onFollowClick: () -> Unit = {},
    onFollowingClick: () -> Unit = {},
    onFollowerClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        ProfileFollowInfo(
            user = user,
            color = color,
            onFollowerClick = onFollowerClick,
            onFollowingClick = onFollowingClick,
        )
        Spacer(modifier = Modifier.height(SpaceSmall))

        if (!isOwnProfile) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedButton(
                    onClick = {
                        onFollowClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isFollowing) {
                            MaterialTheme.colors.onSurface
                        } else {
                            MaterialTheme.colors.primary
                        },
                    ),
                    shape = RoundedCornerShape(50),
                ) {
                    Text(
                        text = if (isFollowing) {
                            stringResource(R.string.unfollow)
                        } else {
                            stringResource(R.string.follow)
                        },
                        color = if (isFollowing) {
                            MaterialTheme.colors.surface
                        } else {
                            MaterialTheme.colors.onPrimary
                        },
                        modifier = Modifier.padding(2.dp),
                    )
                }

                Spacer(modifier = Modifier.width(SpaceMedium))

                OutlinedButton(
                    onClick = {
                        onMessageClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                    ),
                    shape = RoundedCornerShape(50),
                ) {
                    Text(
                        text = stringResource(R.string.message),
                        modifier = Modifier.padding(2.dp),
                    )
                }
            }
        }
    }
}
