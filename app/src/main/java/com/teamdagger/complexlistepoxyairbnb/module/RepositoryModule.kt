package com.teamdagger.complexlistepoxyairbnb.module

import com.teamdagger.complexlistepoxyairbnb.repository.CommentsRepository
import com.teamdagger.complexlistepoxyairbnb.repository.LikesRepository
import com.teamdagger.complexlistepoxyairbnb.repository.WorkoutRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesLikesRepository(): LikesRepository = LikesRepository()

    @Singleton
    @Provides
    fun providesCommentsRepository(): CommentsRepository = CommentsRepository()

    @Singleton
    @Provides
    fun providesWorkoutRepository(): WorkoutRepository = WorkoutRepository()

}