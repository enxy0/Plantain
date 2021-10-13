# :leaves: Plantain

Plantain - это Android приложение для чтения и записи информации с электронных карт «Подорожник» (СПб).  
Использует алгоритмы и наработки из [PlantainReader](https://github.com/krikunts/plantainreader) и [MifareClassicTool](https://github.com/ikarus23/MifareClassicTool).

**Предупреждение**:
[Автор не несет ответственности](https://github.com/enxy0/Plantain/blob/master/LICENSE)
за использование данного приложения. Все действия вы производите на свой страх и риск. Помните, что создание поддельных карт, т.е. запись недостоверной информации на носитель и дальнейшее его использование, является уголовно наказуемым
преступлением (327 УК РФ).

В случае если вы все же решились на запись данных, то знайте - карта будет активна в течение 4-5 дней, если пользоваться только городским наземным транспортом.  
Если пользоваться только маршрутками, то блокировки можно избежать до окончания баланса на карте (или отсрочить до 1-2 недель, а может и больше).
Если использовать карту в метро, то блокировка наступает мгновенно (даже не пытайтесь).

:arrow_down: Скачать: [Plantain v2.1](https://github.com/enxy0/Plantain/releases/tag/v2.1). Другие версии доступны во вкладке [Releases](https://github.com/enxy0/Plantain/releases).

## Скриншоты
| Home | Read/Write | Dumps |
| ---- | ---------- | -------- |
| <img  src="https://raw.githubusercontent.com/enxy0/Plantain/master/.github/home.jpg"/> | <img src="https://raw.githubusercontent.com/enxy0/Plantain/master/.github/read-write.jpg"/> | <img src="https://raw.githubusercontent.com/enxy0/Plantain/master/.github/dumps.jpg"/> |

## Библиотеки
- [Android KTX](https://developer.android.com/kotlin/ktx) - a set of Kotlin extensions that are included with Android Jetpack and other Android libraries.
to keep you focused on your app, not on your tools.
- [Coil](https://coil-kt.github.io/coil/) - An image loading library for Android backed by Kotlin Coroutines.
- [Koin](https://insert-koin.io/) - a smart Kotlin injection library
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - is a rich library for coroutines developed by JetBrains. It contains a number of high-level coroutine-enabled primitives that this guide covers, including launch, async and others.
- [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization) - Kotlin serialization consists of a compiler plugin, that generates visitor code for serializable classes, runtime library with core serialization API and support libraries with various serialization formats.
- [Kotpref](https://github.com/chibatching/Kotpref) - Android SharedPreference delegation for Kotlin.
- [Logger](https://github.com/orhanobut/logger) - Simple, pretty and powerful logger for android.
- [Room](https://developer.android.com/jetpack/androidx/releases/room) - The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.

## Сборка проекта

Для сборки требуется [Android Studio 2020.3.1](https://developer.android.com/studio/preview/index.html)
-   В Android Studio `New` → `Project from Version Control...` → `CLONE`.
-   Добываете ключи для 4 и 5 сектора. Кто знает, тот найдет :wink:.
-   Вставляете полученные hex ключи в файл `local.properties`:
    ```kotlin
    sdk.dir= ...

    KEY_4A=AAAAAAAAAAAA
    KEY_4B=BBBBBBBBBBBB
    KEY_5A=CCCCCCCCCCCC
    KEY_5B=DDDDDDDDDDDD
    ```

Проект готов к сборке и запуску на устройстве :smiley:
