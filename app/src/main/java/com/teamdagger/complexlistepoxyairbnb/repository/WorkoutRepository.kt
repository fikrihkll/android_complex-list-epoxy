package com.teamdagger.complexlistepoxyairbnb.repository

import com.teamdagger.complexlistepoxyairbnb.model.WorkoutSummaryItem

class WorkoutRepository {

    private val listCoverImages = listOf(
        listOf(
            "https://www.stack.com/wp-content/uploads/2018/05/08110222/Swiss-Ball-Exercises-Dead-Bug-STACK.png",
            "https://i.ytimg.com/vi/TY0f2mgR3GI/maxresdefault.jpg"
        ),
    )

    fun loadWorkoutSummary(workoutId: Int): WorkoutSummaryItem {
        return WorkoutSummaryItem(
            duration = 120,
            distance = 14.3,
            avgPower = 255,
            avgPace = 33,
            maxPace = 54,
            avgHeartRate = 87
        )
    }

    fun loadCoverImages(workoutId: Int): List<String> = listCoverImages[0]

}