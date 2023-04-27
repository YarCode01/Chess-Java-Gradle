package XXLChess;

import java.util.*;

public class AI {
    private PlayerColour colour;
    private ArrayList<ChessPiece> available_pieces;
    

    public AI(App app, PlayerColour colour){
        this.colour = colour;
        this.available_pieces = app.getPieces(this.colour);
    }

    public void move(App app){
        if (app.isCheck()){
            ChessPiece king = app.getKing(app.turn);
            int[] position = king.getPosition();
            app.board[position[0]][position[1]].setColour(CellColour.RED);
        }
        Random rand = new Random();
        this.available_pieces = app.getPieces(this.colour);
        for(int i = 0; i < this.available_pieces.size(); i++){
            // int index = rand.nextInt(this.available_pieces.size());
            ChessPiece piece = this.available_pieces.get(i);
            ArrayList<int[]> available_captures = piece.getAvailableCaptures(app);
            ArrayList<int[]> illegal_moves = piece.getIllegalMoves(app);
            ArrayList<int[]> possible_captures = piece.getLegalMoves(app, available_captures, illegal_moves);
            for(int[] move : possible_captures){
                app.last_move[0] = new int[] {piece.getPosition()[0],piece.getPosition()[1]};
                app.last_move[1] = new int[] {move[0], move[1]};
                piece.move(app, move[0], move[1]);
                return;
            }
        }
        for(int i = 0; i < this.available_pieces.size(); i++){
            // int rand_index = rand.nextInt(this.available_pieces.size());
            ChessPiece piece = this.available_pieces.get(i);
            ArrayList<int[]> available_moves = piece.getAvailableMoves(app);
            ArrayList<int[]> illegal_moves = piece.getIllegalMoves(app);
            ArrayList<int[]> possible_moves = piece.getLegalMoves(app);
            for(int[] move : possible_moves){
                app.last_move[0] = new int[] {piece.getPosition()[0],piece.getPosition()[1]};
                app.last_move[1] = new int[] {move[0], move[1]};
                piece.move(app, move[0], move[1]);
                return;
            }
        }
    }
}
