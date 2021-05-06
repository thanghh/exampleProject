package com.example.articles.extension

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.squareup.picasso.Picasso
import kotlin.reflect.KClass


fun ImageView.loadImage(url: String) {
        Picasso.get()
            .load(url)
            .fit()
            .centerCrop().into(this)
}


fun Context.showToast(message: String?) {
    if (!message.isNullOrEmpty()) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun onClickListener(onClick: () -> Unit) = View.OnClickListener {
    onClick.invoke()
}

@MainThread
inline fun <reified VM : ViewModel> Fragment.viewModels(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(VM::class, { ownerProducer().viewModelStore }, factoryProducer)

@MainThread
fun <VM : ViewModel> Fragment.createViewModelLazy(
    viewModelClass: KClass<VM>,
    storeProducer: () -> ViewModelStore,
    factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }
    return ViewModelLazy(viewModelClass, storeProducer, factoryPromise)
}

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.viewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }

    return ViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
}
