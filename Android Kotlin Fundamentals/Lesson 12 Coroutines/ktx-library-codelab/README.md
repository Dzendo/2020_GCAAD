# Using Kotlin Coroutines in your Android app

This folder contains the source code for the [Kotlin Coroutines codelab](https://codelabs.developers.google.com/codelabs/kotlin-coroutines/index.html).

## License

    Copyright 2018 Google LLC

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

https://codelabs.developers.google.com/codelabs/building-kotlin-extensions-library/#0c

Используйте сопрограммы с LiveData
Каждый emit()вызов приостанавливает выполнение блока до тех пор,
 пока LiveData значение не будет установлено в основном потоке.
  emit(Result.error(ioException)) emit(data)

val plants: LiveData<List<Plant>> = plantDao.getPlants()

val plants: LiveData<List<Plant>> = liveData<List<Plant>> {
   val plantsLiveData = plantDao.getPlants()
   val customSortOrder = plantsListSortOrderCache.getOrAwait()
   emitSource(plantsLiveData.map { plantList -> plantList.applySort(customSortOrder) })
}

Вы также реализуете ту же логику с Flow:
private val customSortFlow = plantsListSortOrderCache::getOrAwait.asFlow()

val plantsFlow: Flow<List<Plant>>
   get() = plantDao.getPlantsFlow()
       .combine(customSortFlow) { plants, sortOrder ->
           plants.applySort(sortOrder)
       }
       .flowOn(defaultDispatcher)
       .conflate()
       
