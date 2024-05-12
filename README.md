### Процесс запуска тестирования:

1. Запустить на вашей машине Docker Desktop;
2. Запустить на вашей машине IntelliJ IDEA (для других IDE процесс будет идентичен);
3. Для загрузки проекта необходимо в IntelliJ IDEA создать пустой проект, в проекте выполнить в консоли команду:

`git clone https://github.com/zakhlystun/Netology-Diploma`

После выполнения команды необходимо убедиться что проект загрузился с GitHub и отображается в файловой системе вашей машины.
4. Далее необходимо запустить контейнеры, для этого в консоли IntelliJ IDEA требуется выполнить команду:

`docker-compose up -d` 

5. После того, как запустились контейнеры, в консоли IntelliJ IDEA необходимо выполнить команду для запуска самого приложения:

- для запуска с базой данных MySQL: 

 `java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app`;

- для запуска с базой данных  PostgreSQL: 

`java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app`;

6. После запуска приложения мы можем переходить к запуску тестов. В консоли IntelliJ IDEA выполняем команду, для той базы данной, которую выбрали при запуске приложения:

- для MySQL: 
 
`.\gradlew clean test -D dbUrl=jdbc:mysql://localhost:3306/app -D dbUser=app -D dbPass=pass`

- для PostgreSQL:
 
`.\gradlew clean test -D dbUrl=jdbc:postgresql://localhost:5432/app -D dbUser=app -D dbPass=pass`

7. После завершения нам доступен просмотр отчёта о тестировании в браузере. Чтобы его открыть, в консоли IntelliJ IDEA необходимо выполнить команду:

`.\gradlew allureServe`
