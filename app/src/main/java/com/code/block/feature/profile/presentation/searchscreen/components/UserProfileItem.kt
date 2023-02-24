package com.code.block.feature.profile.presentation.searchscreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.code.block.core.presentation.ui.theme.IconSizeMedium
import com.code.block.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.code.block.core.presentation.ui.theme.SpaceMedium
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.feature.profile.domain.model.UserItem

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserProfileItem(
    user: UserItem,
    modifier: Modifier = Modifier,
    ownUserId: String = "",
    actionIcon: @Composable () -> Unit = {},
    onItemClick: () -> Unit = {},
    onActionItemClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = onItemClick,
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = SpaceSmall,
                    horizontal = SpaceMedium
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = rememberAsyncImagePainter(user.profileImageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(ProfilePictureSizeSmall)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        brush = Brush.sweepGradient(
                            listOf(
                                Color(0xFF9575CD),
                                Color(0xFFBA68C8),
                                Color(0xFFE57373),
                                Color(0xFFFFB74D),
                                Color(0xFFFFF176),
                                Color(0xFFAED581),
                                Color(0xFF4DD0E1),
                                Color(0xFF9575CD)
                            )
                        ),
                        shape = CircleShape
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = SpaceSmall)
            ) {
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.onSurface
                    )
                )

                Spacer(modifier = Modifier.height(SpaceSmall))

                if (user.bio.isNotBlank()) {
                    Text(
                        text = user.bio,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        modifier = Modifier.heightIn(
                            min = MaterialTheme.typography.body2.fontSize.value.dp * 3f
                        )
                    )
                }
            }
            if (ownUserId != user.userId) {
                IconButton(
                    onClick = onActionItemClick,
                    modifier = Modifier.size(IconSizeMedium)
                ) {
                    actionIcon()
                }
            }
        }
    }
}
