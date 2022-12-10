package com.teamdagger.complexlistepoxyairbnb.repository

import com.teamdagger.complexlistepoxyairbnb.model.CommentsItem
import com.teamdagger.complexlistepoxyairbnb.model.UserComment

class CommentsRepository {

    private var comments = CommentsItem(
        comments = List(1000) {
            UserComment(
                id = 1,
                name = "Jayoma",
                "Absolutely amazing workout!!",
                1670658843266
            )
        }
    )

    fun loadComments(workoutId: Int) = comments

}