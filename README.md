## Redisson을 활용한 분산락 사용 사례
```java
실무에서 적용했던 사례를 예제로 구현.
```

## 실행 방법

```shell
./gradlew bootRun
```

## 주문 생성(Post)

```http
http://localhost:8080/api/v1/orders

{
  "orders": [
    { "productId": 1, "quantity": 1 },
    { "productId": 2, "quantity": 1 },
    { "productId": 3, "quantity": 1 }
  ]
}
```

## 재고 조회(Get)

```http
http://localhost:8080/api/v1/stocks
```

## 주문 조회(Get)

```http
http://localhost:8080/api/v1/orders
```
