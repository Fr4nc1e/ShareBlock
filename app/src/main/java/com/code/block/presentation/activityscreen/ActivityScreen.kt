package com.code.block.presentation.activityscreen

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.code.block.R
import com.code.block.domain.model.Activity
import com.code.block.domain.util.ActivityType
import com.code.block.domain.util.DateFormattedUtil
import com.code.block.presentation.activityscreen.components.ActivityItem
import com.code.block.presentation.components.StandardTopBar
import com.code.block.ui.theme.SpaceMedium
import com.code.block.ui.theme.SpaceSmall
import com.ramcosta.composedestinations.annotation.Destination
import kotlin.random.Random

@Destination
@Composable
fun ActivityScreen(
    viewModel: ActivityViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardTopBar(
            title = {
                Text(
                    text = stringResource(R.string.activity),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(SpaceMedium)
        ) {
            items(20) {
                ActivityItem(
                    activity = Activity(
                        username = "Superman",
                        profileImageUrl = R.drawable.superman,
                        activityType = if (Random.nextInt(2) == 0) {
                            ActivityType.LikedComment
                        } else ActivityType.CommentedOnPost,
                        formattedTime = DateFormattedUtil
                            .timestampToFormattedString(
                                timestamp = System.currentTimeMillis(),
                                pattern = "MMM dd, HH:mm"
                            )
                    )
                )

                if (it < 19) {
                    Spacer(modifier = Modifier.height(SpaceSmall))
                }
            }
        }
    }
}
