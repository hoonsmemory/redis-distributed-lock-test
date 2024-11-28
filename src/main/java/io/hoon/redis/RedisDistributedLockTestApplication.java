package io.hoon.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisDistributedLockTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDistributedLockTestApplication.class, args);
    }

}
