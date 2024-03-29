
Kotlin Bootcamp для программистов 5.1: расширения
Kotlin REPL (Read-Eval-Print Loop) in IntelliJ IDEA Читать выполнение строки кода Цикл Печати

2. Задание: узнать о парах и тройках
В этом задании вы узнаете о парах и тройках и их разрушении.
Пары и тройки - это готовые классы данных для 2 или 3 общих предметов.
Это может быть полезно, например, для функции, возвращающей более одного значения.

В приведенных выше примерах используется один и тот же тип для всех частей пары или тройки, но это не обязательно.
Части могут быть, например, строкой, числом или списком - даже другой парой или тройкой.

Разделение пар и троек на их части называется деструктуризацией.
Назначьте пару или тройку соответствующему количеству переменных,
и Kotlin назначит значение каждой части по порядку.

Обратите внимание, что деструктурирование пар и троек работает так же, как с классами данных,
которые были рассмотрены в предыдущем коде.

3. Задача: понять коллекции
Шаг 1: Узнайте больше о списках
Списки и изменяемые списки были введены в более раннем уроке.
Это очень полезная структура данных, поэтому Kotlin предоставляет ряд встроенных функций для списков.
Просмотрите этот частичный список функций для списков.
Вы можете найти полные списки в документации Kotlin для List и MutableList.

add(element: E)  Добавьте элемент в изменяемый список.
remove(element: E) Удалить элемент из изменяемого списка.
reversed() Вернуть копию списка с элементами в обратном порядке.
contains(element: E) Возврат true, если список содержит элемент.
subList(fromIndex: Int, toIndex: Int) Вернуть часть списка, от первого индекса до второго индекса, но не включая его.

list2.
Примечание. Чтобы увидеть функциональность класса, создайте объект в IntelliJ IDEA,
добавьте точку после имени, а затем посмотрите на список автозаполнения во всплывающей подсказке.
Это работает для любого объекта.

Шаг 2: Попробуйте хеш-карты
В Kotlin вы можете сопоставить практически все, что угодно, используя hashMapOf().
Хеш-карты напоминают список пар, где первое значение выступает в качестве ключа.
Если ключа нет на карте, попытка вернуть соответствующую болезнь вернется null.
В зависимости от данных карты может быть обычным не совпадать с возможным ключом.
Для таких случаев, Kotlin предоставляет getOrDefault() функцию.
Если вам нужно сделать больше, чем просто вернуть значение, Kotlin предоставляет эту getOrElse() функцию.
Вместо того, чтобы возвращать простое значение по умолчанию, выполняется любой код между фигурными скобками {}.
В этом примере else просто возвращается строка, но это может быть так же сложно,
как найти веб-страницу с лекарством и вернуть ее.

Так же , как mutableListOf вы можете сделать mutableMapOf.
Изменяемая карта позволяет ставить и удалять элементы.
Мутабильное означает просто способность к изменению, неизменное означает неспособность измениться.

Примечание. Неизменяемые коллекции особенно полезны в многопоточной среде,
 где могут возникнуть проблемы, если несколько потоков касаются одной коллекции.


4. Задание: изучение констант
Шаг 1: Узнайте о const против val
Значение присваивается и не может быть изменено, что очень похоже на объявление обычного val.
Так в чем разница между const valи val?
Значение для const val определяется во время компиляции,
где значение для val определяется во время выполнения программы,
что означает, что valможет быть назначено функцией во время выполнения.
Это означает, что val может быть назначено значение из функции, но const val не может.

Кроме того, const val работает только на верхнем уровне и в одноэлементных классах,
объявленных с object, а не с обычными классами.
Вы можете использовать это, чтобы создать файл или одноэлементный объект,
который содержит только константы, и импортировать их по мере необходимости.

Шаг 2: Создайте сопутствующий объект
Котлин не имеет понятия о константах уровня класса.

Чтобы определить константы внутри класса, вы должны обернуть их в сопутствующие объекты,
объявленные с companion ключевым словом.
Сопутствующий объект - это в основном одноэлементный объект в классе.

Основное различие между объектами-компаньонами и обычными объектами:
Сопутствующие объекты инициализируются из статического конструктора содержащего класса,
 то есть они создаются при создании объекта.
Обычные объекты инициализируются лениво при первом доступе к этому объекту; то есть когда они впервые используются.
Это еще не все, но все, что вам нужно знать на данный момент, - это обернуть константы в классах в объект-компаньон.

5. Task: Write extensions 5. Задача: написать расширения
В этом задании вы узнаете о расширении поведения классов.
Очень часто пишут служебные функции для расширения поведения класса.
Kotlin предоставляет удобный синтаксис для объявления этих служебных функций:
функции расширения.
Функции расширения позволяют добавлять функции в существующий класс без необходимости доступа к его исходному коду.
Например, вы можете объявить их в файле Extensions.kt, который является частью вашего пакета.
Это на самом деле не изменяет класс, но позволяет вам использовать точечную нотацию
при вызове функции для объектов этого класса.

Шаг 1: Написать функцию расширения
Шаг 2. Изучите ограничения расширений
Функции расширения имеют доступ только к общедоступному API класса, который они расширяют.
Переменные которые private нет доступа.
Примечание. Функции расширения разрешаются статически во время компиляции в зависимости от типа переменной.

Шаг 3: Добавьте свойство расширения
В дополнение к функциям расширения Kotlin также позволяет добавлять свойства расширения.
Подобно функциям расширения, вы указываете расширяемый класс,
за которым следует точка, за которой следует имя свойства.

Шаг 4: Знать об обнуляемых приемниках nullable receivers
Расширяемый вами класс называется получателем , и этот класс можно сделать обнуляемым. class nullable
Если вы сделаете это, this переменная, используемая в теле, может быть null, поэтому обязательно проверьте это.
Вы можете захотеть получить обнуляемый получатель, если ожидаете,
что вызывающие абоненты захотят вызвать ваш метод расширения для переменных, допускающих обнуляемость,
или если вы хотите обеспечить поведение по умолчанию при применении вашей функции null.

Функции расширения очень мощные, и большая часть стандартной библиотеки Kotlin реализована как функции расширения.

6. Резюме
На этом уроке вы узнали больше о коллекциях, узнали о константах и ​​почувствовали мощь функций и свойств расширения.
Пары и тройки могут использоваться для возврата более одного значения из функции. Например:
val twoLists = fish.partition { isFreshWater(it) }
Котлин имеет много полезных функций для List, например reversed(), contains() и subList().
HashMapМожет быть использован для отображения ключей к значениям. Например:
val cures = hashMapOf("white spots" to "Ich", "red sores" to "hole disease")
Объявите константы времени компиляции, используя const ключевое слово.
Вы можете поместить их на верхний уровень, организовать их в одноэлементный объект или поместить в сопутствующий объект.
Сопутствующий объект - это одноэлементный объект в определении класса, определенный с помощью companion ключевого слова.
Функции и свойства расширения могут добавлять функциональность в класс.
Например:
fun String.hasSpaces() = find { it == ' ' } != null
Обнуляемый получатель позволяет вам создавать расширения для класса, который может быть null.
?.Оператор может работать в паре с , applyчтобы проверить nullперед выполнением кода. Например:
this?.apply { println("removing $this") }

7. Узнайте больше
Котлинская документация
Если вам нужна дополнительная информация по какой-либо теме этого курса или вы застряли,
https://kotlinlang.org - ваша лучшая отправная точка.

Kotlin соглашения о кодировании
Котлин идиомы
Pair
Triple
List
MutableList
HashMap
Сопутствующие объекты
расширения
Обнуляемый приемник
Котлин учебники
Веб- сайт https://play.kotlinlang.org содержит подробные учебные пособия под названием Kotlin Koans,
веб-переводчик , а также полный набор справочной документации с примерами.

Курс Udacity
Чтобы просмотреть курс Udacity по этой теме, см. Kotlin Bootcamp for Programmers .

IntelliJ IDEA
Документацию для IntelliJ IDEA можно найти на веб-сайте JetBrains.