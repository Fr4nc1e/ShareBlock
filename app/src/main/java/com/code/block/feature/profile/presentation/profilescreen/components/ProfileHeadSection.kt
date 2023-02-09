package com.code.block.feature.profile.presentation.profilescreen.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.code.block.R
import com.code.block.core.domain.model.Profile
import com.code.block.core.domain.model.User
import com.code.block.core.presentation.ui.theme.ProfilePictureSizeLarge
import com.code.block.core.presentation.ui.theme.SpaceSmall
import com.code.block.core.util.PaletteGenerator
import com.code.block.core.util.ui.innerShadow

@Composable
fun ProfileHeadSection(
    modifier: Modifier = Modifier,
    profile: Profile,
    user: User,
    bitmap: Bitmap?,
    imageModifier: Modifier = Modifier,
    isOwnProfile: Boolean = true,
    isFollowing: Boolean = true,
    onFollowClick: () -> Unit = {},
    onFollowingClick: () -> Unit = {},
    onFollowerClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .padding(SpaceSmall)
            .clip(RoundedCornerShape(16.dp)),
    ) {
        val colors = PaletteGenerator.generateDominateColor(bitmap = bitmap)

        Image(
            painter = rememberAsyncImagePainter(profile.bannerUrl),
            contentDescription = stringResource(R.string.banner_image),
            contentScale = ContentScale.Crop,
            modifier = imageModifier
                .fillMaxSize()
                .innerShadow(
                    blur = 20.dp,
                    color = colors.first() ?: MaterialTheme.colors.surface,
                    offsetX = 0.5.dp,
                    offsetY = 0.5.dp,
                )
                .aspectRatio(1f),
        )
        Box(
            modifier = Modifier.align(Alignment.Center),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = rememberAsyncImagePainter(user.profilePictureUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(ProfilePictureSizeLarge)
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
                                    Color(0xFF9575CD),
                                ),
                            ),
                            shape = CircleShape,
                        ),
                )

                Text(
                    text = user.username,
                    style = MaterialTheme.typography.h1
                        .copy(
                            color = colors.last() ?: MaterialTheme.colors.onSurface,
                        ),
                )

                Spacer(modifier = Modifier.height(SpaceSmall))

                if (user.description.isNotBlank()) {
                    Text(
                        text = user.description,
                        style = MaterialTheme.typography.body2
                            .copy(color = colors.last() ?: MaterialTheme.colors.onSurface),
                    )
                }

                Spacer(modifier = Modifier.height(SpaceSmall))

                ProfileInteract(
                    user = user,
                    color = colors.last() ?: MaterialTheme.colors.onSurface,
                    isOwnProfile = isOwnProfile,
                    isFollowing = isFollowing,
                    onFollowingClick = onFollowingClick,
                    onFollowerClick = onFollowerClick,
                    onFollowClick = onFollowClick,
                    onMessageClick = onMessageClick,
                )
            }
        }
    }
}
