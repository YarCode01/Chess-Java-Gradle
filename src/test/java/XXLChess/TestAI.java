package XXLChess;


import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)

public class TestAI {
    
    public static App app;

    
    @BeforeAll
    public static void initTest() {
        app = new App();
        app.noLoop();
        app.configPath = "AITest.json";
        PApplet.runSketch(new String[]{"App"}, app);
        app.settings();
        app.setup();    
    }

    @Test
    @Order(1)
    public void appStartsAI(){
        assertNotNull(app);
    }
    @Test
    @Order(2)
    public void testAImove(){
        app.settings();
        app.setup(); 
        app.ai.move(app);
    }

    @Test
    @Order(2)
    public void testEasyAI(){
        app.settings();
        app.configPath = "AITest.json";
        app.setup();
        app.noLoop();
        app.ai = new AI(app, PlayerColour.BLACK, "EASY");
        app.turn = PlayerColour.BLACK;
        app.ai.move(app);
        
    }





}
