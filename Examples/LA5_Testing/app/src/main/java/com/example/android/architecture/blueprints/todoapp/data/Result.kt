/*
 * Copyright (C) 2019 The Android Open Source Project
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

package com.example.android.architecture.blueprints.todoapp.data

//import com.example.android.architecture.blueprints.todoapp.data.Result.Success

/**
 * A generic class that holds a value with its loading status.
 * Универсальный класс, который содержит значение со своим статусом загрузки.
 * @param <T>
 *  Он используется как возвращаемое значение из источников данных и репозитория.
 *  Котлин запечатанный класс с тремя типами, что представляет состояние операции чтения / записи / загрузки
 *  Success, Error, and Loading
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
// Not obligatory
    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 *  true` если [Result] имеет тип [Success] и содержит ненулевые [Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null
// Not obligatory
fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Result.Success<T>)?.data ?: fallback
}
