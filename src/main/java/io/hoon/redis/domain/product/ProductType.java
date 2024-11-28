package io.hoon.redis.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ProductType {

    REGULAR("기본 상품"),
    POPULAR("인기 상품");

    private final String text;

}
