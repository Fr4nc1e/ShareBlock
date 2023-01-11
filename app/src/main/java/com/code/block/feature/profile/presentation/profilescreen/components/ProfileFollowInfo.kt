package com.code.block.feature.profile.presentation.profilescreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.code.block.core.domain.model.User
import com.code.block.core.presentation.ui.theme.SpaceSmall

@Composable
fun ProfileFollowInfo(
    user: User,
    onFollowingClick: () -> Unit = {},
    onFollowerClick: () -> Unit = {}
) {
    Row {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(user.followingCount.toString())
                }

                append("  following")
            },
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .clickable {
                    onFollowingClick()
                }
        )

        Spacer(modifier = Modifier.width(SpaceSmall))

        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(user.followerCount.toString())
                }

                append("  follower(s)")
            },
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .clickable {
                    onFollowerClick()
                }
        )
    }
}
