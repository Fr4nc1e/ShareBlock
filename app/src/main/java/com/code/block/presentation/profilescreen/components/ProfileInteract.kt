package com.code.block.presentation.profilescreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.code.block.R
import com.code.block.domain.model.User
import com.code.block.ui.theme.SpaceLarge
import com.code.block.ui.theme.SpaceMedium
import com.code.block.ui.theme.SpaceSmall

@Composable
fun ProfileInteract(
    user: User,
    modifier: Modifier = Modifier,
    isOwnProfile: Boolean = true,
    isFollowing: Boolean = true,
    onFollowClick: () -> Unit = {},
    onFollowingClick: () -> Unit = {},
    onFollowerClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
    ) {
        ProfileFollowInfo(
            user = user,
            onFollowerClick = onFollowerClick,
            onFollowingClick = onFollowingClick
        )

        if (!isOwnProfile) {
            Box(
                modifier = Modifier
                    .offset(
                        x = if (isFollowing) SpaceLarge * 6f - SpaceSmall else SpaceLarge * 6f,
                        y = -SpaceMedium
                    )
            ) {
                OutlinedButton(
                    onClick = {
                        onFollowClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isFollowing) {
                            MaterialTheme.colors.surface
                        } else {
                            MaterialTheme.colors.primary
                        }
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = if (isFollowing) {
                            stringResource(R.string.unfollow)
                        } else {
                            stringResource(R.string.follow)
                        },
                        color = if (isFollowing) {
                            MaterialTheme.colors.background
                        } else MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}
