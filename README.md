#Cloud storage

Сервис предоставляет REST-интерфейс для интеграции с <a href="https://github.com/netology-code/jd-homeworks/tree/master/diploma/netology-diplom-backend">FRONT</a> приложением.
Сервис реализовывает следующие методы:

    Вывод списка файлов        endpoint /cloud/list    метод GET
    Добавление файла           endpoint /cloud/file    метод POST
    Выгрузка файла             endpoint /cloud/file    метод GET
    Удаление файла             endpoint /cloud/file    метод DELETE
    Переименование фала        endpoint /cloud/file    метод PUT
    Авторизация пользователя   endpoint /cloud/login   метод POST
    Выход из приложения        endpoint /cloud/logout  метод POST

Авторизация реализована через JWT.
Данные пользователей хранятся в БД под управлением PostgreSQL.

Файлы хранилища также хранятся в БД под управлением PostgreSQL.

Сервер работает на порту 8099 и ожидает подключение FRONT на порту 8080.
