# Plantain

Plantain - это Android приложение для чтения информации с электронных карт «Подорожник» (СПб).
Использует алгоритмы и наработки из оригинального приложения [Plantain-Reader](https://github.com/krikunts/plantainreader).

## Скриншоты
<img  src="https://raw.githubusercontent.com/enxy0/Plantain/master/.github/home.jpg?raw=true"  width=25% /> <img  src="https://raw.githubusercontent.com/enxy0/Plantain/master/.github/info.jpg?raw=true"  width=25% />

## Планы
-   [x] Чтение информации с карты
-   [x] Отображение ошибки при неверном ключе сектора
-   [ ] Чтение карт из диалога (Открыть с помощью ...)

## Библиотеки
*   [Jetpack Compose](https://developer.android.com/jetpack/compose) -- Android’s modern toolkit for building native UI.
*   [Android Architecture Components: LiveData, ViewModel](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help design robust, testable, and maintainable apps.
*   [Android KTX](https://developer.android.com/kotlin/ktx) - KTX extensions provide concise, idiomatic Kotlin to Jetpack, Android platform, and other APIs.

## Сборка проекта
Требуется Android Studio 4.2 Canary 3 (для поддержки Jetpack Compose).
Проект также можно собрать на Canary 1 и 2, однако может потребоваться downgrade версии Gradle.
Для этого открываете файл `build.gradle` и изменяете на нужную версию:
```groovy
buildscript {
    // ...
    dependencies {
        classpath 'com.android.tools.build:gradle:4.2.0-alpha03' // меняете тут
        // ...
    }
}
```

Или делаете это через диалог `File` -> `Project Structure` -> `Project`
Изменяете первый пункт - `Android Gradle Plugin Version` (студия даже предложит версии).