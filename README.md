# Тестовое задание МойСклад

+ [Сборка](#Сборка)
+ [Описание API](#Описание-API)
    - [Добавление документа о закупке товара на склад](#Добавление-документа-о-закупке-товара-на-склад)
    - [Добавление документа о продаже товара со склада](#Добавление-документа-о-продаже-товара-со-склада)
    - [Добавление документа о перемещении товара](#Добавление-документа-о-перемещении-товара)
    - [Отображение списка всех оставшихся на складах продуктов](#Отображение-списка-всех-оставшихся-на-складах-продуктов)
    - [Добавлеине новой разновидности продукта](#Добавлеине-новой-разновидности-продукта)
    - [Добавлеине склада](#Добавлеине-склада)
    
    
## Сборка
 
В проекте использовалась БД H2, при желании можно заменить на Postgresql, раскоментировав необходимую часть в hibernate.cfg.xml (и убрав H2), и создав базу с именем "storagedb".

---

## Описание API

Изначально доступны некоторые наименования продутов и склаодов, добавляются в [init.sql](src/main/resources/init.sql).


Все вычисления основны на добавленных документах, т.е. нам необходимо добавить документы "Послупление". При отсутсвтии товаров на складе, будет невозможно создать документ "Продажа" или "Перемещение".

### Добавление документа о закупке товара на склад 

запрос Get http://localhost:8080/add/doc/arrival

Тело JSON

```JSON
    {
    "item": "123412331",
    "storageName": "Storage 2",
    "docArrivalProducts": [
        {
            "article": "1001", 
            "quantity": 10,
            "price": 100.23
        },
        {
            "article": "1002",
            "quantity": 10,
            "price": 120.23
        },
        {
            "article": "1003",
            "quantity": 10,
            "price": 99.23
        },
        {
            "article": "1005",
            "quantity": 10,
            "price": 120.23
        },
        {
            "article": "1004",
            "quantity": 10,
            "price": 120.23
        }
    ]
    }
```

### Добавление документа о продаже товара со склада 

запрос Get http://localhost:8080/add/doc/sale

Тело JSON

```JSON
    {
    "item": "12341230",
    "storageName": "Storage 2",
    "docArrivalProducts": [
        {
            "article": "1001", 
            "quantity": 10,
            "price": 100.23
        },
        {
            "article": "1002",
            "quantity": 10,
            "price": 120.23
        },
        {
            "article": "1003",
            "quantity": 10,
            "price": 1330.23
        },
        {
            "article": "1001",
            "quantity": 10,
            "price": 120.23
        }
    ]
    }
```

### Добавление документа о перемещении товара 

Уже не успевал полностью реализовать нормально функционал, поэтому на уровне сервиса в классе 
[DocService](src/main/java/ru/maximenko/service/DocService.java) методе
addDocMove вызываются методы для продажи с одного склада, затем закупки на другой.


запрос POST http://localhost:8080/add/doc/move

Тело JSON

```JSON
    {
    "item": "12341231",
    "storageFromName": "Storage 2",
    "storageToName": "Storage 3",
    "docArrivalProducts": [
        {
            "article": "1001", 
            "quantity": 10,
            "price": 100.23
        },
        {
            "article": "1002",
            "quantity": 10,
            "price": 120.23
        },
        {
            "article": "1003",
            "quantity": 10,
            "price": 99.23
        },
        {
            "article": "1005",
            "quantity": 10,
            "price": 120.23
        }
    ]
    }
```

### Отображение списка всех оставшихся на складах продуктов
запрос GET http://localhost:8080/show/leftproducts

### Добавлеине новой разновидности продукта
запрос POST http://localhost:8080/add/product

Тело JSON

```JSON
    {
    "article": "1006",
    "name": "Product 6"
    }
```

### Добавлеине склада
запрос POST http://localhost:8080/add/storage

Тело JSON

```JSON
    {
    "name": "Storage 6"
    }
```