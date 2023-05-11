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

public class TestCheckmate {
    
    public static App app;

    
    @BeforeAll
    public static void initTest() {
        app = new App();
        
        app.configPath = "checkmate.json";
        PApplet.runSketch(new String[]{"App"}, app);
        app.settings();
        app.setup();
        app.noLoop();
    }

    @Test
    @Order(1)
    public void appStarts(){
        assertNotNull(app);
        app.draw();
    }





}
