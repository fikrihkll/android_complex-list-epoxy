package com.teamdagger.complexlistepoxyairbnb

import android.content.Context
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.carousel
import com.teamdagger.complexlistepoxyairbnb.model.WorkoutSummaryViewState
import com.teamdagger.complexlistepoxyairbnb.util.ViewState
import com.teamdagger.complexlistepoxyairbnb.epoxy_model.*
import java.util.*

class HomeController(
    private val context: Context
) : TypedEpoxyController<WorkoutSummaryViewState>(
    EpoxyAsyncUtil.getAsyncBackgroundHandler(),
    EpoxyAsyncUtil.getAsyncBackgroundHandler()
){
    override fun buildModels(data: WorkoutSummaryViewState?) {
        data?.let {
            addCoverImages(it)
            addLikes(it)
            addComments(it)
        }
    }


    private fun addComments(viewState: WorkoutSummaryViewState) {
        val comments = viewState.commentsItem.data

        if (viewState.commentsItem is ViewState.Loaded && comments != null) {
            comments.comments.forEach {
                commentsView {
                    id("comments")
                    entity(it)
                }
            }
        }
    }

    private fun addCoverImages(viewState: WorkoutSummaryViewState) {
        val coverImage = viewState.coverImagesItem.data

        if (viewState.coverImagesItem is ViewState.Loaded && coverImage != null) {
            val models = coverImage.imagesUrls.map {
                CoverImageView_(context)
                    .id(UUID.randomUUID().toString())
                    .entity(it)
            }
            carousel {
                id("cover")
                models(models)
            }
        }
    }

    private fun addLikes(viewState: WorkoutSummaryViewState) {
        val likesItem = viewState.likesItem.data

        if (viewState.likesItem is ViewState.Loaded && likesItem != null) {
            likesView(context) {
                id("likes")
                dataId(1)
                entity(likesItem)
                onLikeClicked {
                    likesItem.onClickHandler?.invoke(it)
                }
            }
        }
    }
}