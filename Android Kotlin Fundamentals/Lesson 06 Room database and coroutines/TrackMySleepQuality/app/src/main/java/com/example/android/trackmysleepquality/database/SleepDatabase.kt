/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// создаете Room базу данных, которая использует Entity и DAO
// код для создания базы данных Room для вашего приложения
package com.example.android.trackmysleepquality.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Код будет практически одинаковым для любой Room базы данных,
// поэтому вы можете использовать этот код в качестве шаблона

/*
Поставьте в SleepNight качестве единственного пункта со списком entities.
Установите version как 1. Всякий раз, когда вы меняете схему, вам придется увеличивать номер версии.
Установите exportSchema на false, чтобы не держать схемы резервного копирования истории версий.
 */
@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {

    abstract val sleepDatabaseDao: SleepDatabaseDao // База данных должна знать о DAO
    // Вы можете иметь несколько DAO.

    companion object { // без создания экземпляра класса, т.к. нужна только одна база данных

        /*
        Аннотировать INSTANCE с @Volatile.
         Значение изменчивой переменной никогда не будет кэшироваться,
         и все операции записи и чтения будут выполняться в и из основной памяти.
         Это помогает убедиться, что значение INSTANCE
         всегда актуально и одинаково для всех потоков выполнения.
         Это означает, что изменения, сделанные одним потоком INSTANCE, сразу видны всем другим потокам,
         и вы не получите ситуацию, когда, скажем, два потока каждый обновляют одну и ту же сущность в кеше,
         что может создать проблему.
         */
        @Volatile
        private var INSTANCE: SleepDatabase? = null
        // INSTANCE Переменная будет хранить ссылку на базу данных, когда один был создан.
        // Это поможет вам избежать повторного открытия соединений с базой данных, что дорого.

        // метод, который понадобится построителю базы данных
        fun getInstance(context: Context): SleepDatabase {
            synchronized(this) {  //  получить доступ к контексту
            // Несколько потоков могут потенциально запрашивать экземпляр базы данных одновременно
            // Заключение кода в базу данных synchronized означает,
            // что только один поток выполнения одновременно может войти в этот блок кода,
            // что обеспечивает инициализацию базы данных только один раз
                var instance = INSTANCE
                // Это позволяет использовать интеллектуальное приведение,
                // которое доступно только для локальных переменных

                if (instance == null) {  // еще нет базы данных
                    instance = Room.databaseBuilder(  //  построитель базы данных, чтобы получить базу данных
                                    context.applicationContext,
                                    SleepDatabase::class.java,
                                    "sleep_history_database"
                            )
                            .fallbackToDestructiveMigration() // требуемую стратегию миграции в компоновщик
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

/*
Вам нужно создать абстрактный класс держателя базы данных с пометкой @Database.
Этот класс имеет один метод, который либо создает экземпляр базы данных,
если база данных не существует, либо возвращает ссылку на существующую базу данных.

Получение Room базы данных немного сложнее, поэтому вот общий процесс, прежде чем начать с кода:

* Создайте public abstract класс, который extends RoomDatabase.
    Этот класс должен действовать как держатель базы данных.
    Класс является абстрактным, потому что Room создает реализацию для вас.
* Аннотируйте класс с помощью @Database.
    В аргументах объявите сущности для базы данных и установите номер версии.
* Внутри companion объекта определите абстрактный метод или свойство,
    которое возвращает SleepDatabaseDao. Room сгенерирует тело для вас.
* Вам нужен только один экземпляр Room базы данных для всего приложения,
    поэтому сделайте RoomDatabase его единичным.
* Используйте Room построитель базы данных для создания базы данных,
    только если база данных не существует. В противном случае верните существующую базу данных.
 */

/*
Обычно вам необходимо предоставить объекту миграции стратегию миграции, когда схема изменяется.
Объект миграции - это объект, который определяет,
как вы берете все строки со старой схемой и конвертируете их в строки в новой схеме, чтобы данные не терялись.
Миграция выходит за рамки этой кодовой метки.
Простое решение состоит в том, чтобы уничтожить и восстановить базу данных, что означает, что данные будут потеряны.
 */