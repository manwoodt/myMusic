# Музыкальное приложение (Avito Internship Test Task)

## Описание
Приложение для поиска, прослушивания и управления музыкальными треками. Используется **Deezer API** для онлайн-треков и **локальное хранилище** для скачанных песен. Реализована поддержка **фонового воспроизведения**.

## Функциональность
- Поиск треков в **Deezer API**
- Прослушивание превью треков онлайн
- Сохранение треков в **локальную библиотеку**
- Навигация между экранами
- Прогресс-бар с перемоткой, отображение текущего времени и длительности трека

## Технологии
- **Архитектура**: Clean Architecture + MVVM
- **UI**: View + ViewBinding
- **Сеть**: Retrofit 
- **База данных**: Room
- **Многопоточность**: Kotlin Coroutines + Flow
- **DI**: Koin
- **Навигация**: Jetpack Navigation + BottomNavigation
- **Плеер**: ExoPlayer

## Установка и запуск
1. Клонировать репозиторий:
   ```sh
   git clone https://github.com/manwoodt/myMusic.git
   ```
2. Открыть проект в **Android Studio**
3. Запустить на эмуляторе/устройстве

## Архитектура проекта
```
📂 app
📂 domain          # Бизнес-логика (UseCases, модели)
📂 data            # Репозитории, API, БД
📂 presentation    # UI, ViewModel
📂 tracks          # Общий модуль экранов треков
```

## API
Используется **Deezer API**:
- Топ 10 треков: `https://api.deezer.com/chart`
- Поиск треков: `https://api.deezer.com/search?q={query}`
- Информация о треке: `https://api.deezer.com/track/{id}`

## Проблемы и их решения

1. Отображение списка треков
- Проблема: Нужно было разделить локальные и онлайн-треки, но использовать общий код.

- Решение: Создан базовый TracksFragment, от которого наследуются ApiTracksFragment и DownloadedTracksFragment.

2. Продумывание архитектуры.
- Проблема: Часто возникала зацикленность между модулями.

- Решение: Использование интерфейса и абстрактных объектов в модуле tracks.

3. Передача trackId.
- Проблема: TrackId нельзя было передать из-за того, что экраны реализованы в разных модулях.

- Решение: Использование интерфейса и safe args.

4. Отображение прогресса трека
- Проблема: onPositionDiscontinuity оказался нестабильным API, а seekbar не работал корректно.

- Решение: Использование player.currentPosition в updateProgress() через flow и collectLatest.

5. Длительность трека в UI
- Проблема: player.duration возвращал 0 в начале воспроизведения.

- Решение: Использование player.addListener() для получения корректного значения после onMediaItemTransition.

6. Смещение currentTime в макете
- Проблема: currentTime отображался раньше, чем trackDuration, что вызывало сдвиг макета.

- Решение: Установка обоих значений по умолчанию, а затем обновление одновременно.
