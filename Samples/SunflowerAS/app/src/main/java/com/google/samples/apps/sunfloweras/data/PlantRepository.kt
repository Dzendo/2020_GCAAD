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
class PlantRepository private constructor(private val plantDao: PlantDao) {

    fun getPlants() = plantDao.getPlants()

    fun getPlant(plantId: String) = plantDao.getPlant(plantId)

    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
            plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: PlantRepository? = null
        // создание самого репозиторя (Static)  и базы данных
        fun getInstance(plantDao: PlantDao) =
                instance ?: synchronized(this) {
                    instance ?: PlantRepository(plantDao).also { instance = it }
                }
    }
}
