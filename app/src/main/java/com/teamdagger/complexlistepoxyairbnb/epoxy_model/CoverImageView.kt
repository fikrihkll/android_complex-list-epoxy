package com.teamdagger.complexlistepoxyairbnb.epoxy_model

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.teamdagger.complexlistepoxyairbnb.R

@EpoxyModelClass(layout = R.layout.view_holder_cover_images)
abstract class CoverImageView(
    private val context: Context
) : EpoxyModelWithHolder<CoverImageView.Holder>() {

    @EpoxyAttribute
    lateinit var entity: String

    override fun bind(holder: Holder) {
        super.bind(holder)
        Glide.with(context).load(entity).into(holder.image)
    }

    inner class Holder : EpoxyHolder() {

        lateinit var image: ImageView

        override fun bindView(itemView: View) {
            image = itemView.findViewById(R.id.coverImageView)
        }

    }

}