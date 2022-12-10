package com.teamdagger.complexlistepoxyairbnb.repository

import com.teamdagger.complexlistepoxyairbnb.model.LikesItem
import kotlinx.coroutines.delay

class LikesRepository {

    private var likes = LikesItem(
        hasUserLiked = false,
        likesCount = 2,
        userAvatars = listOf(
            "https://assets.pikiran-rakyat.com/crop/0x159:1080x864/x/photo/2022/04/03/941016597.jpeg",
            "https://avatars.githubusercontent.com/u/57880863?v=4",
        ),
        onClickHandler = null
    )

    suspend fun loadLikes(workoutId: Int) : LikesItem {
        delay(1500)
        return likes
    }

    suspend fun storeLike(workoutId: Int) {
        delay(2000)
        likes = likes.copy(
            likesCount = likes.likesCount+1,
            hasUserLiked = true,
            userAvatars = listOf(
                "https://www.fikrihkl.me/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Fprofile.df1cda0f.jpg&w=128&q=75",
                *(likes.userAvatars.toTypedArray())
            ),
            onClickHandler = null
        )
    }

}