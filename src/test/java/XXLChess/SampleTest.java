package XXLChess;


import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {

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
        String file_name = 
        app.LoadBoard("/Users/yaraslauivashynka/Desktop/projects/xxlchess_scaffold/level2.txt");
        app.turn = PlayerColour.WHITE;
        AI ai = new AI(app, PlayerColour.BLACK);
        ChessPiece piece = app.board[0][13].getPiece();
        piece.move(app,0,12);
        app.ChangeTurn();
        System.out.println(ai.minimax(app, 2, app.turn)[0][0]);
        System.out.println(ai.minimax(app, 2, app.turn)[0][1]);
        System.out.println(ai.minimax(app, 2, app.turn)[1][0]);
        System.out.println(ai.minimax(app, 2, app.turn)[1][1]);
        // app.ChangeTurn();
        // ai.move(app);
        // app.draw();
        
    }

}
