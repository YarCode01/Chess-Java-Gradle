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

public class TestApp {
    
    public static App app;

    
    @BeforeAll
    public static void initTest() {
        app = new App();
        app.noLoop();
        PApplet.runSketch(new String[]{"App"}, app);
        app.settings();
        app.setup();
    }

    @Test
    @Order(1)
    public void appStarts(){
        assertNotNull(app);
    }

    @Test
    @Order(2)
    public void getKingWorks(){
        assertEquals(app.getKing(PlayerColour.BLACK), app.board[7][0].getPiece());
        assertEquals(app.getKing(PlayerColour.WHITE), app.board[7][13].getPiece());
    }

    @Test
    @Order(3)
    public void timer_created(){
        assertNotNull(app.timer_black);
        assertNotNull(app.timer_white);
        app.timer_white.start();
        assertTrue(app.timer_white.isRunning());
        assertFalse(app.timer_black.isRunning());
    }
    @Test
    @Order(4)
    public void updateTimeWorks(){
        app.noLoop();
        app.update_time(app.timer_white, app.timer_black, PlayerColour.BLACK);
        assertTrue(app.timer_black.isRunning());
        assertFalse(app.timer_white.isRunning());

        app.update_time( app.timer_white, app.timer_black, PlayerColour.WHITE);
        assertTrue(app.timer_white.isRunning());
        assertFalse(app.timer_black.isRunning());

        app.timer_white = new Timer(0,0,0);

        app.timer_black = new Timer(0,0,0);
        app.setup();
    }

    @Test
    @Order(5)
    public void isLegalMoveWorks(){
        assertTrue(app.isLegalMove(0, 1, 0, 2));
    }

    @Test
    @Order(6)
    public void reset_colourWorks(){
        app.reset_colour();
        assertEquals(app.board[0][0].getColour(), CellColour.LIGHT_BROWN);
        assertEquals(app.board[0][1].getColour(), CellColour.DARK_BROWN);
    }

    @Test
    @Order(7)
    public void changeTurnWorks(){
        app.noLoop();
        app.turn = PlayerColour.BLACK;
        app.ChangeTurn();
        assertEquals(app.turn, PlayerColour.WHITE);
        app.ChangeTurn();
        assertEquals(app.turn, PlayerColour.BLACK);
    }

    @Test
    @Order(8)
    public void draw_moveWorks(){
        app.draw_move(0, 1, 0, 2);
        assertEquals(app.board[0][1].getColour(), CellColour.YELLOW);
        assertEquals(app.board[0][2].getColour(), CellColour.YELLOW);
        app.draw_move(3, 1, 3, 3);
        assertEquals(app.board[3][1].getColour(), CellColour.YELLOW);
        assertEquals(app.board[3][3].getColour(), CellColour.YELLOW);

        app.draw_move(4, 1, 4, 3);
        assertEquals(app.board[4][1].getColour(), CellColour.YELLOW);
        assertEquals(app.board[4][3].getColour(), CellColour.YELLOW);
        app.reset_last_move();
    }

    @Test
    @Order(9)
    public void select_current_pieceWorks(){
        app.select_current_piece(0, 1);
        assertEquals(app.board[0][1].getColour(), CellColour.GREEN);
    }

    @Test
    @Order(10)
    public void select_free_cellWorks(){
        ChessPiece piece = app.board[0][1].getPiece();
        ArrayList<int[]> moves = piece.getLegalMoves(app);
        app.select_free_cell(moves);
        
    }

    @Test
    @Order(11)
    public void select_occupied_cellWorks(){
        app.select_occupied_cell(5,2);
        assertEquals(app.board[5][2].getColour(), CellColour.ORANGE);
        ChessPiece piece = app.board[5][1].getPiece();
        ArrayList<int[]> moves = piece.getLegalMoves(app);
        app.select_occupied_cell(moves);

    }

    @Test
    @Order(12)
    public void display_timeWorks(){
        app.setup();
        app.display_time(app.timer_white, app.timer_black, PlayerColour.BLACK);
        app.draw();
        app.noLoop();
    }

    @Test
    @Order(13)
    public void click_works(){
        app.setup();
        app.reset_colour();
        app.reset_last_move();
        app.click = true;
        app.turn = PlayerColour.WHITE;
        app.x_clicked = 2;
        app.y_clicked = 12;
        app.draw();

        app.click = true;
        app.turn = PlayerColour.WHITE;
        app.x_clicked = 2;
        app.y_clicked = 11;
        app.draw();

        app.reset_colour();
        app.reset_last_move();
        app.click = true;
        app.turn = PlayerColour.WHITE;
        app.x_clicked = 5;
        app.y_clicked = 5;
        app.draw();
    }

    @Test
    @Order(14)
    public void getScoreWorks(){
        app.setup();
        double score = app.getScore();
        assertEquals(score, 0.0);
    }

    @Test
    @Order(15)
    public void castlingWorks(){
        app.settings();
        app.configPath = "castlingTest.json";
        app.setup();
        ChessPiece king = app.getKing(PlayerColour.WHITE);
        king.move(app, king.getPosition()[0] + 2, king.getPosition()[1]);
    }






}
