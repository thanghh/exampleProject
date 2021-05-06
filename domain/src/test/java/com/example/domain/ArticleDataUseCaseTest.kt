package com.example.domain

import com.example.domain.model.Resource
import com.example.domain.model.response.ArticleResponse
import com.example.domain.usecase.GetArticleDataUseCase
import junit.framework.Assert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ArticleDataUseCaseTest {

    @Mock
    private lateinit var articleDataUseCase : GetArticleDataUseCase

    @Mock
    private lateinit var articleResponse: Resource<ArticleResponse>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun onTest() {
        runBlocking {

            val flow = flow {
                emit(articleResponse)
            }

            Mockito.`when`(articleDataUseCase.getArticleData()).thenReturn(flow)

            Assert.assertNotNull(articleResponse)

        }
    }
}