## Redisson을 활용한 분산락 사용 사례
실무에서 적용했던 사례를 예제로 구현.  
[Redis를 활용한 분산락 적용 사례](https://github.com/hoonsmemory/memo/blob/main/Redis/Redis%EB%A5%BC%20%ED%99%9C%EC%9A%A9%ED%95%9C%20%EB%B6%84%EC%82%B0%EB%9D%BD%20%EC%A0%81%EC%9A%A9%20%EC%82%AC%EB%A1%80.md)  
<br>

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
