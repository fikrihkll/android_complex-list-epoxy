package com.teamdagger.complexlistepoxyairbnb.model

import com.teamdagger.complexlistepoxyairbnb.util.ViewState

data class WorkoutSummaryViewState(
    val workoutId: Int = 0,
    val coverImagesItem: ViewState<CoverImagesItem>,
    val likesItem: ViewState<LikesItem>,
    val commentsItem: ViewState<CommentsItem>,
    val workoutSummaryItem: ViewState<WorkoutSummaryItem>
)

typealias OnClickHandler = (workoutId: Int) -> Unit

data class CoverImagesItem(
    val imagesUrls: List<String>,
    val onClickHandler: OnClickHandler
)

data class LikesItem(
    val hasUserLiked: Boolean,
    val likesCount: Int,
    val userAvatars: List<String>,
    val onClickHandler: OnClickHandler?
)

data class CommentsItem(
    val comments: List<UserComment>
)

data class WorkoutSummaryItem(
    val duration: Int,
    val distance: Double,
    val avgPower: Int,
    val avgPace: Int,
    val maxPace: Int,
    val avgHeartRate: Int,
)

data class UserComment(
    val id: Int,
    val name: String,
    val message: String,
    val time: Long
)