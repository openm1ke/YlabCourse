## CarShop-Service

ДЗ №1 - реквест https://github.com/MuxauJI/YlabCourse/pull/1

ДЗ №2 - реквест 

Разработайте приложение для управления автосалоном. Приложение должно позволять пользователям управлять базой данных автомобилей, обрабатывать заказы клиентов на покупку и обслуживание автомобилей, а также управлять учетными записями пользователей.

Реализовано три класса User - для пользователей, Car - для автомобилей, Order - сущность заказов.

У пользователя основные поля это логин и пароль и "роль", которая может быть ADMIN - администратор, MANAGER - управляющий менеджер, USER - обычный пользователь.

Основные поля автомобиля - марка, модель, год, цена, статус. Статус "AVAILABLE" - доступна для покупки, "RESERVED" - зарезервирована, "SOLD" - продана.

Заказ состоит и основых полей - Тип заказа (Новый, Завершенный, Отменен), айди автомобиля, айди пользователя, Тип заказа (Покупка нового авто, Помывка, Замена масла, Заправка кондиционера), дата создания заказа.

---

Компиляция приложения

`mvn clean install`

Запуск приложения

```bash 
java -jar ./target/hw1-1.0-SNAPSHOT.jar
```

Запуск тестов

`mvn test`

Создать файл документации

`mvn javadoc:javadoc`

---

После запуска появится меню авторизации/регистрации.

Администратор имеет полный доступ, позволяет создавать изменять удалять пользователей, автомобили, заказы.
Менеджер имеет возможность изменять тип заказа.
Пользователь имеет возможность создать заказ на покупку или обслуживание автомобиля. отменить заявку в случае если она еще не в статусе "выполнено".

