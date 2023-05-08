package XXLChess;


import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestApp {


    @Test
    public void simpleTest() {
        App app = new App();
        app.loop();
        for(int i = 0; i < app.BOARD_WIDTH; i++){
            for(int j = 0; j < app.BOARD_WIDTH; j++){
                if ((i+j)%2 == 0) app.board[i][j] = new Cell(CellColour.LIGHT_BROWN);
                else app.board[i][j] = new Cell(CellColour.DARK_BROWN);
            }
        }
        app.setup();
        app.LoadBoard("level1.txt");
        app.turn = PlayerColour.WHITE;
        AI ai = new AI(app, PlayerColour.BLACK, "EASY");
        app.ChangeTurn();
        ai.move(app);
    }

}
