package com.example.android.codelabs.paging

/*
Эта лаборатория кода знакомит вас с библиотекой подкачки страниц и ее основными компонентами:

- PagingData- контейнер для данных с разбивкой на страницы.
  Каждому обновлению данных будет соответствовать отдельный PagingData.

- PagingSource- a PagingSource- это базовый класс для загрузки снимков данных в поток PagingData.

- Pager.flow - строит на Flow<PagingData> основе a PagingConfig и функцию,
  которая определяет, как построить реализованный PagingSource.

- PagingDataAdapter - a RecyclerView.Adapter, представленный PagingData в a RecyclerView.
  PagingDataAdapterМожет быть подключен к Котлин Flow, А LiveData, в RxJava Flowable, или RxJava Observable.
  Он PagingDataAdapter прослушивает внутренние PagingData события загрузки по мере загрузки страниц
   и использует их DiffUtil в фоновом потоке для вычисления детализированных обновлений
    по мере получения обновленного содержимого в форме новых PagingData объектов.

- RemoteMediator - помогает реализовать пагинацию из сети и базы данных.

 */

/*
- Отслеживает ключи, которые будут использоваться для перехода к следующей и предыдущей странице.
- Автоматически запрашивает правильную страницу, когда пользователь прокручивает список до конца.
- Гарантирует, что несколько запросов не будут запущены одновременно.
- Позволяет кэшировать данные: если вы используете Kotlin, это делается в файле CoroutineScope;
  если вы используете Java, это можно сделать с помощью LiveData.
- Отслеживает состояние загрузки и позволяет отображать его в RecyclerView элементе списка
  или в другом месте пользовательского интерфейса, а также легко повторять неудачные загрузки.
- Позволяет выполнять общие операции, такие как map или filter в списке, который будет отображаться,
  независимо от того, используете ли вы Flow, LiveData или RxJava Flowable или Observable.
- Предоставляет простой способ реализации разделителей списков.
 */

/*
Руководство по архитектуре приложений предлагает архитектуру со следующими основными компонентами:

- Локальная база данных, которая служит единым источником достоверных данных для данных, представленных пользователю и управляемых пользователем.
- Служба веб-API.
- Репозиторий, который работает с базой данных и службой веб-API, обеспечивая единый интерфейс данных.
- Который ViewModel предоставляет данные, относящиеся к пользовательскому интерфейсу.
- Пользовательский интерфейс, который показывает визуальное представление данных в ViewModel.
 */

/*
5. Определите источник данных.
Итак, в data пакете создадим PagingSource реализацию под названием GithubPagingSource:

6. Создайте и настройте PagingData.
В нашей текущей реализации мы используем Flow<RepoSearchResult>в классе,
 GitHubRepository чтобы получить данные из сети и передать их в ViewModel.
  ViewModelЗатем преобразует его в LiveData и подвергает его к UI.
   Когда мы дойдем до конца отображаемого списка и из сети Flow<RepoSearchResult>будет загружено больше данных ,
    в дополнение к последним данным будет содержаться весь список ранее полученных данных для этого запроса.

RepoSearchResult инкапсулирует как случаи успеха, так и ошибки.
 В случае успеха хранятся данные репозитория. Случай ошибки содержит Exceptionпричину.

С Paging 3.0 нам больше не нужно RepoSearchResult,
 так как библиотека моделирует как успешные, так и ошибочные случаи с помощью LoadResult.
  Не стесняйтесь удалять, так RepoSearchResult как в следующих нескольких шагах мы заменим его.

Чтобы создать PagingData, нам сначала нужно решить,
 какой API мы хотим использовать для передачи PagingData другим уровням нашего приложения:

Котлин Flow- пользуйся Pager.flow.
LiveData- использовать Pager.liveData.
RxJava Flowable- используйте Pager.flowable.
RxJava Observable- используйте Pager.observable.
Поскольку мы уже используем Flow в нашем приложении, мы продолжим этот подход;
 но вместо использования Flow<RepoSearchResult> мы будем использовать Flow<PagingData<Repo>>.

 Теперь у вас должны быть ошибки компиляции в SearchRepositoriesViewModel.
  Посмотрим, какие изменения нужно там внести!

  7. Запросить и кэшировать PagingData в ViewModel.
Примечание. Если вы выполняете какие-либо операции с Flow, например, map или filter,
 обязательно вызывайте cachedIn после выполнения этих операций,
  чтобы убедиться, что вам не нужно запускать их снова.

  Теперь посмотрим, какие изменения мы внесли SearchRepositoriesViewModel:

Добавлены новые элементы запроса Stringи результатов поиска Flow.
Обновлен searchRepo()метод с использованием ранее описанных функций.
Удалены queryLiveData и, repoResult поскольку их назначение покрывается Paging 3.0 и Flow.
Удален, listScrolled()поскольку библиотека подкачки справится с этим за нас.
Удален, companion object потому что VISIBLE_THRESHOLD больше не нужен.
Удалено repoLoadStatus, поскольку в Paging 3.0 также есть механизм для отслеживания статуса загрузки,
 как мы увидим на следующем шаге.

 8. Заставьте адаптер работать с PagingData.
 Чтобы привязать a PagingData к a RecyclerView, используйте a PagingDataAdapter.
  Он PagingDataAdapter получает уведомление всякий раз, когда PagingData контент загружается,
   а затем сигнализирует об RecyclerView обновлении.

До сих пор мы внесли много изменений, но теперь мы всего в одном шаге от возможности запускать приложение
 - нам просто нужно подключить пользовательский интерфейс!

 9. Запуск обновлений сети.
 Давайте обновимся SearchRepositoriesActivity для работы с Paging 3.0.
 Чтобы иметь возможность работать Flow<PagingData>, нам нужно запустить новую сопрограмму.
 Мы сделаем это в lifecycleScope, который отвечает за отмену запроса при воссоздании действия.

 Наше приложение должно скомпилироваться и запуститься,
 но без нижнего колонтитула состояния загрузки и того, Toast что отображается при ошибке.
  На следующем шаге мы увидим, как отобразить нижний колонтитул состояния загрузки.

 */