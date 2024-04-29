# Payaut REST service (100% Java)

```mermaid
erDiagram
    item {
        bigint id PK
        timestamp created_at
        varchar item_name
        enum item_type
        enum item_unit
        bigint discount_id FK
    }

 discount {
        bigint id PK
        enum discount_unit
        boolean is_constant_slab
    }

    discount_slab {
        bigint id PK
        double discount_amount
        bigint units_to_get_discount
        bigint discount_id FK
    }

    discount ||--|{ discount_slab : discount_id
    discount |o --|| item : discount_id
```
## Documentation
All the endpoints and possible requests/exceptions are documented in Postman(and many of them in Javadoc). The requisitions are with fulfilled bodies and ready to be tested locally

<a href="https://documenter.getpostman.com/view/16889380/2sA3BuWUZa#b0fa6b13-1953-4e33-9cdd-9451b3ead934" target="_blank"><img src="https://run.pstmn.io/button.svg" alt="Run in Postman"></a>

## How it works?
<img width="1423" alt="Capture d’écran 2024-04-29 à 09 17 59" src="https://github.com/devkiloton/Payaut-assignment/assets/78966160/e958274a-b02b-4907-a899-ead349d73908">

## The DB
I have used H2 in memory db (100% Java), so if you want to take a look in the tables access `/api/v1/h2` and you will have access to the console (credentials in the application.properties)

https://github.com/devkiloton/Payaut-assignment/assets/78966160/3e8e9065-042d-4fe1-8e80-35f230b6c907


## Item's cheat sheet
Use these identifiers (default data) to interact with the API
| ID   | CREATED_AT          | ITEM_NAME    | ITEM_PRICE   | ITEM_TYPE   | ITEM_UNIT   | DISCOUNT_ID   |
|------|---------------------|--------------|--------------|-------------|-------------|---------------|
| 1    | 2024-04-24 10:00:00 | Vegetables   | 0.01         | VEGETABLES  | GRAM        | 1             |
| 2    | 2024-04-24 11:00:00 | Dutch Beer   | 0.5          | BEERS       | PIECE       | 2             |
| 3    | 2024-04-25 11:00:00 | Bread        | 1.0          | BREADS      | PIECE       | 3             |
| 4    | 2024-04-24 11:00:00 | Belgium Beer | 0.75         | BEERS       | PIECE       | 4             |
| 5    | 2024-04-24 11:00:00 | German Beer  | 1.0          | BEERS       | PIECE       | 5             |

