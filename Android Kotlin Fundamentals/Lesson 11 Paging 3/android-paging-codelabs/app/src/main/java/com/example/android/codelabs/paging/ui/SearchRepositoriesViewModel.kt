/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.paging.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.android.codelabs.paging.data.GithubRepository
import com.example.android.codelabs.paging.model.Repo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * ViewModel for the [SearchRepositoriesActivity] screen.
 * The ViewModel works with the [GithubRepository] to get the data.
 */
@ExperimentalCoroutinesApi
class SearchRepositoriesViewModel(private val repository: GithubRepository) : ViewModel() {
    private var currentQueryValue: String? = null
    // 12. Добавление разделителей списков
    private var currentSearchResult: Flow<PagingData<UiModel>>? = null

    fun searchRepo(queryString: String): Flow<PagingData<UiModel>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<UiModel>> = repository.getSearchResultStream(queryString)
                .map { pagingData -> pagingData.map { UiModel.RepoItem(it) } }
                .map {
                    it.insertSeparators<UiModel.RepoItem, UiModel> { before, after ->
                        if (after == null) {
                            // we're at the end of the list
                            return@insertSeparators null
                        }

                        if (before == null) {
                            // we're at the beginning of the list
                            return@insertSeparators UiModel.SeparatorItem("${after.roundedStarCount}0.000+ stars")
                        }
                        // check between 2 items
                        if (before.roundedStarCount > after.roundedStarCount) {
                            if (after.roundedStarCount >= 1) {
                                UiModel.SeparatorItem("${after.roundedStarCount}0.000+ stars")
                            } else {
                                UiModel.SeparatorItem("< 10.000+ stars")
                            }
                        } else {
                            // no separator
                            null
                        }
                    }
                }
                .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}

sealed class UiModel {
    data class RepoItem(val repo: Repo) : UiModel()
    data class SeparatorItem(val description: String) : UiModel()
}
private val UiModel.RepoItem.roundedStarCount: Int
    get() = this.repo.stars / 10_000
/*

  Теперь посмотрим, какие изменения мы внесли SearchRepositoriesViewModel:

Добавлены новые элементы запроса String и результатов поиска Flow.
Обновлен searchRepo()метод с использованием ранее описанных функций.
Удалены queryLiveData и, repoResult поскольку их назначение покрывается Paging 3.0 и Flow.
Удален, listScrolled()поскольку библиотека подкачки справится с этим за нас.
Удален, companion object потому что VISIBLE_THRESHOLD больше не нужен.
Удалено repoLoadStatus, поскольку в Paging 3.0 также есть механизм для отслеживания статуса загрузки,
 как мы увидим на следующем шаге.
 */


/*
Из нашего SearchRepositoriesViewModel выставляем repoResult: LiveData<RepoSearchResult>.
 Роль repoResult заключается в том, чтобы быть кешем в памяти для результатов поиска,
  который сохраняет изменения конфигурации.
   С Paging 3.0 мы не должны превратить нашу Flow в LiveData больше.
    Вместо этого у него SearchRepositoriesViewModel будет частный Flow<PagingData<Repo>>член,
     который служит той же цели, что repoResult и ..

Вместо того, чтобы использовать LiveData объект для каждого нового запроса, мы можем просто использовать String.
 Это поможет нам гарантировать, что всякий раз, когда мы получим новый поисковый запрос,
  который совпадает с текущим запросом, мы просто вернем существующий Flow.
   Нам нужно только позвонить, repository.getSearchResultStream()если новый поисковый запрос отличается.
 */

/*
SearchRepositoriesViewModel запрашивает данные GithubRepository и предоставляет их SearchRepositoriesActivity.
 Потому что мы хотим , чтобы убедиться , что мы не запрос данных несколько раз по изменению конфигурации
  (например , поворот), мы преобразуя Flow для LiveData в ViewModel использовании liveData()метода строителя.
   Таким образом, LiveData последний список результатов кэшируется в памяти,
    и при SearchRepositoriesActivity воссоздании его содержимое LiveData будет отображаться на экране.
 */