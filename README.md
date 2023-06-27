# Облачное хранилище

В рамках задания к дипломному проекту было разработано приложение Облачное хранилище.
Приложение на входе запрашивает логин и пароль, после чего идет проверка наличия зарегистрированного пользователя с такими даннымию
В случае обнаружения такого пользователя, происходит аутентификация и ему присваивается Токен, с помощью которого будут осуществляться все дальнейшие запросы.
Приложение умеет добавлять файлы, изменять имя, удалять файлы, скачивать файлы на компьютер и выводить список всех файлов.
- ###
Для проверки работы, подключаем MySQL в Database, через Query Console выполняем операции прописанные в файле `table.sql`.
А именно создаем схему и таблицы и заполняем пользователя в таблицу `user`.
Создать директорию `"C:\\Diplom\\"`
Далее стартуем приложение и в поле логин вводим `admin`, в поле пароль `admin`
- ###
Для добавления файла приложение принимает имя файла и файл типа `MultipartFile` из которого получаем размер файла и массив байтю
Далее следует проверка файла, проверяется чтобы файл не был равен null и массив байт был не равен 0. В противном случае мы получим ошибку.
Если все верно файл записывается в облачное хранилище и в базу данных.

- ###
Для изменения имени используется 2 параметра текущее имя и новое имя. Проводится проверка наличия необходимого файла в базе данных и если он был найден то изменяется его имя.
В противном случае у нас выпадет ошибка.

- ###
Для удаления файлов мы принимаем в параметре имя файла. Далее следует проверка наличия файла в базе данных.
Если файл найден, то он помечается как удаленный и в дальнейшем в приложении не отображается. Необходимости окончательно удалять файл нет, так как некоторые файлы в дальнейшем могут использоваться для сбора какой-либо статистической информации.
Если файл отсутствует-то получим исключение.

- ###
Для скачивания файла получаем параметром имя файла, проверяем его наличие в базе данных, если файл найден, то идет его преобразование в массив байт и происходит скачивание.
Если файл не был найден, тогда получим исключение.

- ###
- Приложение разработано с использованием Spring Boot
- Использован сборщик пакетов maven
- Для проверки корректности работы были прописаны unit-тесты с использованием mockito
- Для запуска используется docker

