# :leaves: Plantain

Plantain - это Android приложение для чтения и записи информации с электронных карт «Подорожник» (СПб).  
Использует алгоритмы и наработки из [PlantainReader](https://github.com/krikunts/plantainreader) и [MifareClassicTool](https://github.com/ikarus23/MifareClassicTool).

Помни, товарищ! Записывание данных на карту незаконно и к тому же не практично - карта блокируется в течение 4-5 дней (а в метро вообще после первого использования).

## Скриншоты
<img  src="https://raw.githubusercontent.com/enxy0/Plantain/master/.github/home.jpg?raw=true"  width=25% /> <img  src="https://raw.githubusercontent.com/enxy0/Plantain/master/.github/info.jpg?raw=true"  width=25% /> <img  src="https://raw.githubusercontent.com/enxy0/Plantain/master/.github/edit.jpg?raw=true"  width=25% />

## Планы
-   [x] Чтение информации с Подорожника
-   [x] Отображение ошибок
-   [x] Чтение карт из диалога (Открыть с помощью ...)
-   [x] Запись данных на карту
-   [x] Уведомление о результате записи данных
-   [ ] Подсказки при вводе значений (мин. и макс. значения)

## Библиотеки
-   [Jetpack Compose](https://developer.android.com/jetpack/compose) — Android’s modern toolkit for building native UI.
-   [Android Architecture Components: LiveData, ViewModel](https://developer.android.com/topic/libraries/architecture) — Collection of libraries that help design robust, testable, and maintainable apps.
-   [Android KTX](https://developer.android.com/kotlin/ktx) — KTX extensions provide concise, idiomatic Kotlin to Jetpack, Android platform, and other APIs.

## Сборка и запуск
Готовый APK файл можно найти во вкладке [Releases](https://github.com/enxy0/Plantain/releases). Последняя версия: [v1.5](https://github.com/enxy0/Plantain/releases/tag/v1.5)

Для сборки требуется [Android Studio 4.2](https://developer.android.com/studio/preview/index.html) и выше (для поддержки Jetpack Compose).
-   В Android Studio `New` → `Project from Version Control...` → `CLONE`.
-   Добываете ключи для 4 и 5 сектора. Кто знает, тот найдет :wink:.
-   Вставляете полученные ключи в файл `app/src/main/java/kek/plantain/data/CardReader.kt`, вместо тех, что заполнены нулями.

Проект готов к сборке и запуску на устройстве :smiley:
