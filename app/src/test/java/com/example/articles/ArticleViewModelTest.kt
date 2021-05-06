package com.example.articles

import android.content.Context
import androidx.databinding.Observable
import com.example.articles.ui.features.detail.ArticleDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class ArticleViewModelTest {

    @Mock
    var context: Context? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        context = Mockito.spy(Context::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun onTest() {
        runBlocking {
            val articleBaseObservable = context?.let { ArticleDetailViewModel.ArticleObservable(it) }
            val listener: Observable.OnPropertyChangedCallback = mock(Observable.OnPropertyChangedCallback::class.java)
            articleBaseObservable?.addOnPropertyChangedCallback(listener)

            val image = "articleImage"
            val detail = "articleDetail"

            articleBaseObservable?.notifyImage(image)
            articleBaseObservable?.notifyDetail(detail)

            verify(listener).onPropertyChanged(articleBaseObservable, BR.articleImage)
            verify(listener).onPropertyChanged(articleBaseObservable, BR.articleDetail)

        }
    }
}
