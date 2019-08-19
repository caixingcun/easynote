package com.caixc.easynoteapp.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.caixc.easynoteapp.GlideApp
import com.caixc.easynoteapp.base.Preference
import com.caixc.easynoteapp.global.Constants
import com.caixc.easynoteapp.global.Urls

class ImageUtil {
    companion object {

        fun load(context: Context, url: String, iv: ImageView) {
            var url = Urls.IMAGE_URL + url
            var glideUrl = GlideUrl(
                url,
                LazyHeaders.Builder().addHeader("token", Preference.preferences.getString(Constants.TOKEN, "")).build()
            )
            GlideApp.with(context)
                .load(glideUrl)
                .into(iv)
        }

        fun loadWithPic(context: Context, url: String, iv: ImageView) {
            var url = Urls.IMAGE_URL + url
            var glideUrl = GlideUrl(
                url,
                LazyHeaders.Builder().addHeader("token", Preference.preferences.getString(Constants.TOKEN, "")).build()
            )

            GlideApp.with(context)
                .load(glideUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv)

//            GlideApp.with(context)
//                .load(glideUrl)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(object : SimpleTarget<Drawable>() {
//                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
//                        var width = resource.intrinsicWidth
//                        var height = resource.intrinsicHeight
//                        LogUtils.debug("width" + width + "height" + height)
//
//                        var layoutParams = iv.layoutParams
//                        layoutParams.height = layoutParams.width * height / width
//                        iv.layoutParams = layoutParams
//
//                        GlideApp.with(context)
//                            .load(glideUrl)
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)
//                            .into(iv)
//                    }
//                })
        }

    }
}
