# :leaves: Plantain

Plantain - это Android приложение для чтения и записи информации с электронных карт «Подорожник» (СПб).  
Использует алгоритмы и наработки из [PlantainReader](https://github.com/krikunts/plantainreader) и [MifareClassicTool](https://github.com/ikarus23/MifareClassicTool).

**Предупреждение**: 
[Автор не несет ответственности](https://github.com/enxy0/Plantain/blob/master/LICENSE) 
за использование данного приложения. Все действия вы производите на свой страх и риск. Помните, что создание поддельных карт (т.е. запись недостоверной информации на носитель) является уголовно наказуемым 
преступлением (327 УК РФ).

В случае если вы все же решились на запись данных, то знайте - карта будет активна в течение 4-5 дней, если пользоваться только наземным транспортом. Если использовать карту в метро, то блокировка наступает практически мгновенно (в течение 10 минут).

:arrow_down: Скачать: [Plantain v1.6](https://github.com/enxy0/Plantain/releases/tag/v1.6). Другие версии доступны во вкладке [Releases](https://github.com/enxy0/Plantain/releases).

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

## Сборка проекта

Для сборки требуется [Android Studio 4.2](https://developer.android.com/studio/preview/index.html) и выше (для поддержки Jetpack Compose).
-   В Android Studio `New` → `Project from Version Control...` → `CLONE`.
-   Добываете ключи для 4 и 5 сектора. Кто знает, тот найдет :wink:.
-   Вставляете полученные ключи в файл `app/src/main/java/kek/plantain/data/CardReader.kt`, вместо тех, что заполнены нулями.

Проект готов к сборке и запуску на устройстве :smiley:
