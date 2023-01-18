package com.code.block.feature.profile.presentation.profilescreen.components

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.code.block.R
import com.code.block.core.domain.model.User
import com.code.block.core.presentation.ui.theme.IconSizeMedium
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.core.presentation.ui.theme.SpaceSmall

@Composable
fun ProfileHeaderSection(
    modifier: Modifier = Modifier,
    user: User,
    isOwnProfile: Boolean = true,
    isFollowing: Boolean = true,
    onEditClick: () -> Unit = {},
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
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.primary
                )
            )

            Spacer(modifier = Modifier.height(SpaceSmall))

            if (user.description.isNotBlank()) {
                Text(
                    text = user.description,
                    style = MaterialTheme.typography.body2
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

        Box(
            modifier = Modifier
                .align(CenterVertically)
                .padding(PaddingValues(SpaceSmall))
                .offset(y = -SpaceMedium)
        ) {
            if (isOwnProfile) {
                IconButton(
                    onClick = {
                        onEditClick()
                    },
                    modifier = Modifier
                        .size(IconSizeMedium)
                        .align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit)
                    )
                }
            }
        }
    }
}
