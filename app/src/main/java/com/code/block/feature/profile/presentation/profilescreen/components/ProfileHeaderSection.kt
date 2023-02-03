package com.code.block.feature.profile.presentation.profilescreen.components

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.code.block.core.domain.model.User
import com.code.block.core.presentation.ui.theme.SpaceSmall

@Composable
fun ProfileHeaderSection(
    modifier: Modifier = Modifier,
    user: User,
    isOwnProfile: Boolean = true,
    isFollowing: Boolean = true,
    onFollowClick: () -> Unit = {},
    onFollowingClick: () -> Unit = {},
    onFollowerClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier.padding(PaddingValues(SpaceSmall))
        ) {
            Text(
                text = user.username,
                style = MaterialTheme.typography.h1.copy(
                    color = MaterialTheme.colors.onSurface
                )
            )

            Spacer(modifier = Modifier.height(SpaceSmall))

            if (user.description.isNotBlank()) {
                Text(
                    text = user.description,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface
                )
            }

            Spacer(modifier = Modifier.height(SpaceSmall))

            ProfileInteract(
                user = user,
                isOwnProfile = isOwnProfile,
                isFollowing = isFollowing,
                onFollowingClick = onFollowingClick,
                onFollowerClick = onFollowerClick,
                onFollowClick = onFollowClick
            )
        }
    }
}
