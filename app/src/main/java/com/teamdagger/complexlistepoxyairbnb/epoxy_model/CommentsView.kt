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
import com.teamdagger.complexlistepoxyairbnb.model.UserComment
import java.text.SimpleDateFormat
import java.util.*

@EpoxyModelClass(layout = R.layout.view_holder_comment)
abstract class CommentsView(
) : EpoxyModelWithHolder<CommentsView.Holder>() {

    @EpoxyAttribute
    lateinit var entity: UserComment

    override fun bind(holder: Holder) {
        super.bind(holder)

        holder.commentTextView.text = entity.message
        holder.userNameTextView.text = entity.name
        holder.timeTextView.text = SimpleDateFormat("HH:mm - EEE,MMM dd yyyy").format(Date(entity.time))
    }

    inner class Holder : EpoxyHolder() {

        lateinit var userNameTextView: TextView
        lateinit var commentTextView: TextView
        lateinit var timeTextView: TextView

        override fun bindView(itemView: View) {
            userNameTextView = itemView.findViewById(R.id.userNameTextView)
            commentTextView = itemView.findViewById(R.id.commentTextView)
            timeTextView = itemView.findViewById(R.id.timeTextView)
        }

    }

}