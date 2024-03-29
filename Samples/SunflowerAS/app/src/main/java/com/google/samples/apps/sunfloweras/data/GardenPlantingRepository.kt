/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunfloweras.data

/**
 * Repository module for handling data operations.
 * Модуль репозитория для обработки операций с данными.
 * Работает полностью через DAO интерфейс
 */
/*
Здесь в sunfloweras пристроен просто так прогонно, т.к. нет инета и нет кеширования
Практически здесь повторяет DAO и через него обращается к ROOM - образец и реализует DAO
 */
class GardenPlantingRepository private constructor(
    private val gardenPlantingDao: GardenPlantingDao
) {

    suspend fun createGardenPlanting(plantId: String) {
        val gardenPlanting = GardenPlanting(plantId)
        gardenPlantingDao.insertGardenPlanting(gardenPlanting)
    }

     suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
    }

    // Вставил AS для удаления растения из садика и для комплекта (добавить в тесты)
    suspend fun getGardenPlanting(plantId: String): GardenPlanting = gardenPlantingDao.getGardenPlanting(plantId)

    // Вставил AS для очистки садика и для комплекта (добавить в тесты)
    suspend fun clearGarden() = gardenPlantingDao.clearGarden()

    fun isPlanted(plantId: String) = gardenPlantingDao.isPlanted(plantId)

    fun getPlantedGardens() = gardenPlantingDao.getPlantedGardens()

    companion object {

        // For Singleton instantiation Для одноэлементный экземпляр
        @Volatile private var instance: GardenPlantingRepository? = null
        // создание самого репозиторя (Static)  и базы данных через  private constructor
        fun getInstance(gardenPlantingDao: GardenPlantingDao) =
                instance ?: synchronized(this) {
                    instance ?: GardenPlantingRepository(gardenPlantingDao).also { instance = it }
                }
    }
}