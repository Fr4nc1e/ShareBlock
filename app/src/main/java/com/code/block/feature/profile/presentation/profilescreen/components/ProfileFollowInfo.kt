package com.code.block.feature.profile.presentation.profilescreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.code.block.core.domain.model.User
import com.code.block.core.presentation.ui.theme.SpaceMedium

@Composable
fun ProfileFollowInfo(
    user: User,
    color: Color?,
    onFollowingClick: () -> Unit = {},
    onFollowerClick: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = user.followingCount.toString(),
                color = color ?: MaterialTheme.colors.onSurface,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        onFollowingClick()
                    },
            )
            Text(
                text = "  following",
                style = MaterialTheme.typography.body2,
                color = color ?: MaterialTheme.colors.onSurface,
            )
        }

        Spacer(modifier = Modifier.width(SpaceMedium))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = user.followerCount.toString(),
                color = color ?: MaterialTheme.colors.onSurface,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        onFollowerClick()
                    },
            )
            Text(
                text = "  follower(s)",
                style = MaterialTheme.typography.body2,
                color = color ?: MaterialTheme.colors.onSurface,
            )
        }
    }
}
