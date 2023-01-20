package com.code.block.feature.profile.presentation.profilescreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.code.block.R
import com.code.block.core.presentation.ui.theme.ProfilePictureSizeLarge
import com.code.block.core.presentation.ui.theme.SpaceLarge
import com.code.block.core.presentation.ui.theme.SpaceSmall

@Composable
fun BannerSection(
    modifier: Modifier = Modifier,
    bannerUrl: String? = null,
    profilePictureUrl: String? = null,
    imageModifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = rememberAsyncImagePainter(bannerUrl),
            contentDescription = stringResource(R.string.banner_image),
            contentScale = ContentScale.Crop,
            modifier = imageModifier
                .fillMaxSize()
                .aspectRatio(2.5f)
        )

        Image(
            painter = rememberAsyncImagePainter(profilePictureUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(
                    x = SpaceSmall,
                    y = ProfilePictureSizeLarge - SpaceLarge
                )
                .size(ProfilePictureSizeLarge)
                .clip(CircleShape)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colors.background,
                    shape = CircleShape
                )
        )
    }
}
