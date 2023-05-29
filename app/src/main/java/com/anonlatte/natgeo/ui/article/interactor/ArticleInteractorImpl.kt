package com.anonlatte.natgeo.ui.article.interactor

import com.anonlatte.natgeo.data.model.article.Article
import com.anonlatte.natgeo.data.network.ErrorState
import com.anonlatte.natgeo.data.network.RequestState
import com.anonlatte.natgeo.data.repository.LocalRepository
import com.anonlatte.natgeo.ui.article.state.ArticleUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArticleInteractorImpl @Inject constructor(
    private val localRepository: LocalRepository
) : ArticleInteractor {
    override suspend fun getArticle(id: Int): Flow<ArticleUiState> {
        return buildRequestFlow { localRepository.getArticle(id) }
    }

    private fun buildRequestFlow(
        request: suspend () -> RequestState<Article>
    ): Flow<ArticleUiState> {
        return flow {
            emit(ArticleUiState.Loading)
            val newsUiState = when (val requestState = request()) {
                is RequestState.Success -> {
                    ArticleUiState.Success(requestState.value)
                }

                else -> {
                    ArticleUiState.Error(requestState as ErrorState)
                }
            }
            emit(newsUiState)
        }
    }
}