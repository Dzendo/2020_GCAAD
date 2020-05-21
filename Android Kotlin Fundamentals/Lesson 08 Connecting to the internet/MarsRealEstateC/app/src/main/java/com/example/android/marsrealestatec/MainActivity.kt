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
 *
 */

package com.example.android.marsrealestatec

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// UI Controller (activity/fragment) + Observer --> ViewModel +LiveData --> Network;  Observer <-- LiveData
// Не используется слой Repository - Хранилище
// Будут использоваться библтотеки, разработанные сообществом RetroFit
// - помогают прил соответсвовать лучшим рекомендациям Android:
// Загрузка изображений в фоновом потоке и кеширование загруженных изображений
// REST - REpresentational  State Transfer architecture restfull-сервисы
// REST ---> URI (http...) --> server  ----> XML/JSON ---> REST

class MainActivity : AppCompatActivity() {

    /**
     * Our MainActivity is only responsible for setting the content view that contains the
     * Navigation Host.
     * Наша основная деятельность связана только с настройкой представления контента, содержащего
     * Навигационный Хост.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

/*
Данные по недвижимости Mars хранятся на веб-сервере в виде веб-службы REST.
Веб-сервисы, использующие архитектуру REST, создаются с использованием стандартных веб-компонентов и протоколов.

Вы делаете запрос к веб-сервису стандартным способом через URI.
Знакомый веб-URL на самом деле является типом URI, и оба они используются взаимозаменяемо на протяжении всего курса.
Например, в приложении для этого урока вы получаете все данные со следующего сервера:

https://android-kotlin-fun-mars-server.appspot.com

Если вы введете следующий URL в своем браузере, вы получите список всех доступных объектов недвижимости на Марсе!

https://android-kotlin-fun-mars-server.appspot.com/realestate

там данные о собсвенности на марсе, кот будем считывать

Ответ от веб-службы обычно форматируется в JSON , формате обмена для представления структурированных данных.
Вы узнаете больше о JSON в следующей задаче, но краткое объяснение состоит в том,
что объект JSON представляет собой набор пар ключ-значение,
иногда называемых словарем, хэш-картой или ассоциативным массивом.
Коллекция объектов JSON представляет собой массив JSON, и это массив, который вы возвращаете в ответ от веб-службы.

Чтобы получить эти данные в приложение,
вашему приложению необходимо установить сетевое соединение и установить связь с этим сервером,
а затем получить и проанализировать данные ответа в формате, который может использовать приложение.
В этой кодовой метке вы используете клиентскую библиотеку REST под названием Retrofit, чтобы установить это соединение.
 */

/* 08.2: загрузка и отображение изображений из интернета
3. Задача: отобразить интернет-изображение
Отображение фотографии с веб-адреса может показаться простым, но для того, чтобы она работала хорошо, нужно немного поработать.
Изображение должно быть загружено, буферизовано и декодировано из сжатого формата в изображение, которое может использовать Android.
Изображение должно быть кэшировано в кэш-память в памяти, кэш-память на основе хранилища или и то, и другое.
Все это должно происходить в фоновых потоках с низким приоритетом, чтобы пользовательский интерфейс оставался отзывчивым.
Кроме того, для лучшей производительности сети и процессора вы можете выбрать и декодировать более одного изображения одновременно.
Изучение того, как эффективно загружать изображения из сети, само по себе может быть кодовой меткой.

К счастью, вы можете использовать разработанную сообществом библиотеку Glide для загрузки, буферизации, декодирования и кэширования ваших изображений.
Glide оставляет вам гораздо меньше работы, чем если бы вам пришлось делать все это с нуля.
https://github.com/bumptech/glide  https://bumptech.github.io/glide/

Glide в основном нужны две вещи:

URL изображения, которое вы хотите загрузить и показать.
ImageViewОбъект для отображения этого изображения.

 */

/*
08.2: загрузка и отображение изображений из интернета
4. Задача: отобразить сетку изображений с помощью RecyclerView
Ваше приложение теперь загружает информацию о недвижимости из Интернета.
Используя данные из первого MarsProperty элемента списка,
вы создали LiveData свойство в модели представления
и использовали URL-адрес изображения из данных этого свойства для заполнения ImageView.
Но цель состоит в том, чтобы ваше приложение отображало сетку изображений,
поэтому вы хотите использовать RecyclerView с GridLayoutManager.
Шаг 1: Обновите модель представления
Прямо сейчас у модели представления есть объект,
_property LiveData который содержит один MarsProperty объект - первый в списке ответов от веб-службы.
На этом шаге вы измените его, LiveData чтобы он содержал весь список MarsProperty объектов.

 */

/*
5. Задача: добавить обработку ошибок в RecyclerView
Приложение MarsRealEstate отображает значок разорванного изображения, когда изображение не может быть получено.
Но когда нет сети, приложение показывает пустой экран.
Это не очень хороший пользовательский опыт.
В этой задаче вы добавите базовую обработку ошибок, чтобы дать пользователю лучшее представление о том, что происходит.
Если Интернет недоступен, приложение покажет значок ошибки подключения.
 Пока приложение MarsProperty загружает список, приложение покажет анимацию загрузки.
 Шаг 1: Добавить статус в модель представления
 Шаг 2: Добавьте привязывающий адаптер для статуса ImageView
 Шаг 3: Добавьте статус ImageView в макет
 */

/*7. Резюме
Чтобы упростить процесс управления изображениями, используйте библиотеку Glide для загрузки, буферизации, декодирования и кэширования изображений в вашем приложении.
Glide нужны две вещи , чтобы загрузить изображение из Интернета: URL образа, и ImageView. Объектные поместить изображение в Чтобы задать эти параметры, использовать load()и into()методы с Glide.
Связывающие адаптеры - это методы расширения, которые находятся между представлением и связанными данными этого представления. Связывающие адаптеры обеспечивают настраиваемое поведение при изменении данных, например, для вызова Glide для загрузки изображения из URL-адреса в ImageView.
Связующие адаптеры - это методы расширения, аннотированные @BindingAdapterаннотацией.
Чтобы добавить параметры в запрос Glide, используйте apply()метод. Например, используйте apply()с, placeholder()чтобы указать загружаемую отрисовку, и используйте apply()с, error()чтобы указать отрисовываемую ошибку.
Чтобы создать сетку изображений, используйте RecyclerViewс a GridLayoutManager.
Чтобы обновить список свойств при его изменении, используйте адаптер привязки между RecyclerViewи макетом.
*/

/*
8. Резюме
Обязательные выражения
Используйте выражения привязки в файлах макета XML для выполнения простых программных операций,
таких как математические или условные тесты, над связанными данными.
Чтобы ссылаться на классы внутри вашего файла макета, используйте <import> тег внутри <data>тега.
Параметры запроса веб-службы
Запросы к веб-сервисам могут включать необязательные параметры.
Чтобы указать параметры запроса в запросе, используйте @Query аннотацию в Retrofit .
 */