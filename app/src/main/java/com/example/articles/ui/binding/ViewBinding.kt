package com.example.articles.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.articles.extension.loadImage

object ViewBinding {
    @JvmStatic
    @BindingAdapter("loadImageURL")
    fun loadImageURL(imageView: ImageView, url: String?) {
        imageView.loadImage(url.orEmpty())
    }
}