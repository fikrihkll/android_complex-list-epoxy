package com.teamdagger.complexlistepoxyairbnb.epoxy_model

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.teamdagger.complexlistepoxyairbnb.R
import com.teamdagger.complexlistepoxyairbnb.model.LikesItem

@EpoxyModelClass(layout = R.layout.view_holder_like)
abstract class LikesView(
    private val context: Context
) : EpoxyModelWithHolder<LikesView.Holder>() {

        @EpoxyAttribute
        var dataId: Int = -1

        @EpoxyAttribute
        lateinit var entity: LikesItem

        @EpoxyAttribute
        lateinit var onLikeClicked: (workoutId: Int) -> Unit

        override fun bind(holder: Holder) {
            super.bind(holder)
            Glide.with(context).load(entity.userAvatars[0]).into(holder.likeAvatar)
            if (entity.hasUserLiked)
                holder.likeIcon.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_thumb_up_24))
            else
                holder.likeIcon.setImageDrawable(context.getDrawable(R.drawable.ic_outline_thumb_up_24))
            holder.likeCounTextView.text = "${entity.likesCount} Like${if (entity.likesCount > 1) "s" else ""}"

            holder.likeIcon.setOnClickListener {
                onLikeClicked.invoke(dataId)
            }
        }

        inner class Holder : EpoxyHolder() {

            lateinit var likeIcon: ImageView
            lateinit var likeCounTextView: TextView
            lateinit var likeAvatar: ImageView

            override fun bindView(itemView: View) {
                likeIcon = itemView.findViewById(R.id.thumbIconImageView)
                likeCounTextView = itemView.findViewById(R.id.likesCountTextView)
                likeAvatar = itemView.findViewById(R.id.likeAvatarImageView)
            }

        }

}