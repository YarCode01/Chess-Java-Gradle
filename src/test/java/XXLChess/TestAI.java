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
    public static void initTestAI() {
        app = new App();
        app.noLoop();
        app.configPath = "AITest.json";
        PApplet.runSketch(new String[]{"App"}, app);
        app.settings();
        app.setup();    
    }

    @Test
    public void appStartsAI(){
        assertNotNull(app);
    }
    @Test
    public void testAImove(){
        app.settings();
        app.setup(); 
        app.noLoop();
        app.ai.move(app);
    }

    @Test
    public void testAImoveBlack(){
        app.settings();
        app.setup(); 
        app.noLoop();
        app.colour_of_cpu = PlayerColour.BLACK;
        app.colour_of_player = PlayerColour.WHITE;
        app.turn = PlayerColour.BLACK;
        AI ai = new AI(app, PlayerColour.BLACK, "HARD");
        app.ai = ai;
        app.ai.move(app);
    }

    @Test
    public void testEasyAI(){
        app.settings();
        app.configPath = "config.json";
        app.setup();
        app.noLoop();
        app.turn = PlayerColour.BLACK;
        app.ai = new AI(app, PlayerColour.BLACK, "EASY");
        app.turn = PlayerColour.BLACK;
        app.ai.move(app);
        
    }





}
