package com.cypods.geBuddy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static com.cypods.geBuddy.Window.primaryStage;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemsDbTest {

    @Autowired
    public ConfigurableApplicationContext ac;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void contextLoads() {
        ConfigurableApplicationContext ac = new SpringApplicationBuilder(Window.class).run();
    }

    @Autowired
    private ItemsRepository itemsRepository;

    @Test
    public void testRetrieve() throws Exception {
        Window w = new Window();
        w.start(primaryStage);

        ItemsDb itemsDb = itemsRepository.findByItemIdEquals(2);

        assertNotNull(itemsDb,"ItemDb should not be null");
        assertEquals("Cannonball",itemsDb.getItem_name());
    }


}