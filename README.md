Техническое задание к приложению доступно по ссылке:
https://smallpdf.com/ru/file#s=1a6f16ac-a407-43a5-8941-a50c8de78a60


Приложение запускается следующей командой

```
docker-compose up --build
```
либо без Docker'а прямо в IDE.
Для нормальной работы необходим второй микросервис (https://github.com/sbnetaa/itq-number-generate-service),
который должен лежать в одной папке с этим микросервисом.

Документация к REST API доступна по адресу:

```
http://localhost:8080/swagger-ui/index.html
```
Главная страница UI варианта доступна по адресу:
```
http://localhost:8080/
```

За примерно неделю мне так и не удалось заставить тесты микросервиса генерации номеров работать в среде Docker
(в IDE работают, а в первом микросервисе работают везде),
поэтому в Dockerfile используется флаг -DskipTests и надеюсь, что это не сильно критично.
На всякий случай (вдруг захочется попробовать :) ) оставил часть кода (некоторый закомментирован),
оставшегося от попыток справиться с этой проблемой.
