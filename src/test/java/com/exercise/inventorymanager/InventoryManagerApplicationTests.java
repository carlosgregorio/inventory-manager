package com.exercise.inventorymanager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InventoryManagerApplicationTests {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("Spring context is loaded successfully on application start")
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }
}