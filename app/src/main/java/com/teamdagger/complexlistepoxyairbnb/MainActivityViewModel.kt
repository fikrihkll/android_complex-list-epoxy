package com.teamdagger.complexlistepoxyairbnb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamdagger.complexlistepoxyairbnb.model.*
import com.teamdagger.complexlistepoxyairbnb.repository.CommentsRepository
import com.teamdagger.complexlistepoxyairbnb.repository.LikesRepository
import com.teamdagger.complexlistepoxyairbnb.repository.WorkoutRepository
import com.teamdagger.complexlistepoxyairbnb.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
@Inject
constructor(
    private val likesRepository: LikesRepository,
    private val workoutRepository: WorkoutRepository,
    private val commentsRepository: CommentsRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<WorkoutSummaryViewState>()
    val viewState: LiveData<WorkoutSummaryViewState> = _viewState

    private val likesItemChannel = MutableStateFlow<ViewState<LikesItem>>(ViewState.Loading())
    private val workoutItemChannel = MutableStateFlow<ViewState<WorkoutSummaryItem>>(ViewState.Loading())
    private val commentsItemChannel = MutableStateFlow<ViewState<CommentsItem>>(ViewState.Loading())
    private val coverImagesItemChannel = MutableStateFlow<ViewState<CoverImagesItem>>(ViewState.Loading())

    fun loadData(workoutId: Int) {
        viewModelScope.launch {
            combine(
                listOf(
                    loadCoverImages(workoutId),
                    loadLikesItem(workoutId),
                    loadComments(workoutId),
                    loadWorkoutSummary(workoutId)
                )
            ) { arrayOfViewStates ->
                // The order in the array is the same as in the combine function
                WorkoutSummaryViewState(
                    coverImagesItem = arrayOfViewStates[0] as ViewState<CoverImagesItem>,
                    likesItem = arrayOfViewStates[1] as ViewState<LikesItem>,
                    commentsItem = arrayOfViewStates[2] as ViewState<CommentsItem>,
                    workoutSummaryItem = arrayOfViewStates[3] as ViewState<WorkoutSummaryItem>
                )
            }.conflate()
                .flowOn(Dispatchers.Default)
                .onEach { viewState ->
                    // Updating view state
                    _viewState.value = viewState
                }
                .collect()
        }
    }

    private fun loadLikesItem(
        workoutId: Int,
        initialState: ViewState<LikesItem> = ViewState.Loading()
    ): StateFlow<ViewState<LikesItem>> {
        viewModelScope.launch {
            try {
                // Load likes from db/server. likesRepository.loadLikes() is Main Safe.
                // NOTE: Make sure your API is Main Safe or wrap call with withContext(Dispatchers.IO).
                val loadedLikes = likesRepository.loadLikes(workoutId)
                likesItemChannel.emit(
                    ViewState.Loaded(
                        LikesItem(
                            // bind below props from loaded data,
                            hasUserLiked = loadedLikes.hasUserLiked,
                            likesCount = loadedLikes.likesCount,
                            userAvatars = loadedLikes.userAvatars,
                            onClickHandler = ::onLikeClicked
                        )
                    )
                )
            } catch (e: Exception) {
                likesItemChannel.emit(ViewState.Error(e, initialState.data))
            }
        }
        return likesItemChannel
    }

    private fun loadWorkoutSummary(
        workoutId: Int,
        initialState: ViewState<WorkoutSummaryItem> = ViewState.Loading()
    ): StateFlow<ViewState<WorkoutSummaryItem>> {
        viewModelScope.launch {

            try {
                // Load likes from db/server. likesRepository.loadLikes() is Main Safe.
                // NOTE: Make sure your API is Main Safe or wrap call with withContext(Dispatchers.IO).
                val loadedSummary = workoutRepository.loadWorkoutSummary(workoutId)
                workoutItemChannel.emit(
                    ViewState.Loaded(
                        WorkoutSummaryItem(
                            duration = loadedSummary.duration,
                            distance = loadedSummary.distance,
                            avgPower = loadedSummary.avgPower,
                            avgPace = loadedSummary.avgPace,
                            maxPace = loadedSummary.maxPace,
                            avgHeartRate = loadedSummary.avgHeartRate
                        )
                    )
                )
            } catch (e: Exception) {
                workoutItemChannel.emit(ViewState.Error(e, initialState.data))
            }
        }
        return workoutItemChannel
    }

    private fun loadComments(
        workoutId: Int,
        initialState: ViewState<CommentsItem> = ViewState.Loading()
    ): StateFlow<ViewState<CommentsItem>> {
        viewModelScope.launch {
            // Initial state, empty
            commentsItemChannel.emit(initialState)

            try {
                // Load likes from db/server. likesRepository.loadLikes() is Main Safe.
                // NOTE: Make sure your API is Main Safe or wrap call with withContext(Dispatchers.IO).
                val loadedComments = commentsRepository.loadComments(workoutId)
                commentsItemChannel.emit(
                    ViewState.Loaded(
                        CommentsItem(
                            comments = loadedComments.comments
                        )
                    )
                )
            } catch (e: Exception) {
                commentsItemChannel.emit(ViewState.Error(e, initialState.data))
            }
        }
        return commentsItemChannel
    }

    private fun loadCoverImages(
        workoutId: Int,
        initialState: ViewState<CoverImagesItem> = ViewState.Loading()
    ): Flow<ViewState<CoverImagesItem>> {
        viewModelScope.launch {
            // Initial state, empty
            coverImagesItemChannel.emit(initialState)

            try {
                // Load likes from db/server. likesRepository.loadLikes() is Main Safe.
                // NOTE: Make sure your API is Main Safe or wrap call with withContext(Dispatchers.IO).
                val loadedImageUrls = workoutRepository.loadCoverImages(workoutId)
                coverImagesItemChannel.emit(
                    ViewState.Loaded(
                        CoverImagesItem(
                            imagesUrls = loadedImageUrls,
                            onClickHandler = {}
                        )
                    )
                )
            } catch (e: Exception) {
                coverImagesItemChannel.emit(ViewState.Error(e, initialState.data))
            }
        }
        return coverImagesItemChannel
    }

    private fun onLikeClicked(workoutId: Int) {
        viewModelScope.launch {
            // We can get the old state from the channel
            val oldState = likesItemChannel.first() // TODO try-catch needed here

            try {
                // store like in local DB. storeLike() is Main Safe.
                likesRepository.storeLike(workoutId)

                // Reload likes, but this time we provide the previous state as initial state
                // By reloading, we get a fresh state from the local DB
                loadLikesItem(workoutId, oldState)
            } catch (e: Exception) {
                // Emit an error with the previous state. This way we can show an error,
                // while still keeping the previous state in place.
                likesItemChannel.emit(ViewState.Error(e, oldState.data))
            }
        }
    }

}