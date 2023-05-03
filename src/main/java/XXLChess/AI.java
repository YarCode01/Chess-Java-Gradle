package XXLChess;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class AI {
    private PlayerColour colour;
    private ArrayList<ChessPiece> available_pieces;
    private int i = 0;
    

    public AI(App app, PlayerColour colour){
        this.colour = colour;
        this.available_pieces = app.getPieces(this.colour);
    }

    public PlayerColour getColour(){
        return this.colour;
    }

    public int[][] minimax(App app, int depth, PlayerColour turn) {
        if (depth == 0 || app.isCheckMate(PlayerColour.WHITE) || app.isCheckMate(PlayerColour.BLACK)) {
            return null; 
        }

        if (turn == PlayerColour.WHITE) {
            double best_score = -100000; 
            int[][] best_move = null;
            ArrayList<ChessPiece> pieces = app.getPieces(turn);
            for(ChessPiece piece : pieces){
                ArrayList<int[]> moves = piece.getLegalMoves(app);
                for (int[] move : moves){
                    Cell original_cell = new Cell(app.board[move[0]][move[1]].getColour(), app.board[move[0]][move[1]].getPiece());
                    int[] position = piece.getPosition();
                    app.board[position[0]][position[1]].setPiece(null);
                    app.board[move[0]][move[1]].setPiece(piece);
                    piece.setPosition(move[0],move[1]);
                    int[][] opposite_best_move = minimax(app, depth - 1, PlayerColour.BLACK);
                    if (opposite_best_move != null){
                        int[] opposite_position = opposite_best_move[0];
                        int[] opposite_move = opposite_best_move[1];
                        Cell opposite_original_cell = new Cell(app.board[opposite_move[0]][opposite_move[1]].getColour(), app.board[opposite_move[0]][opposite_move[1]].getPiece());
                        ChessPiece opposite_piece = app.board[opposite_position[0]][opposite_position[1]].getPiece();

                        app.board[opposite_position[0]][opposite_position[1]].setPiece(null);
                        app.board[opposite_move[0]][opposite_move[1]].setPiece(opposite_piece);
                        opposite_piece.setPosition(opposite_move[0],opposite_move[1]);

                        double score = app.getScore();
                        if (score > best_score){
                            best_score = score;
                            best_move = new int[][] {{position[0],position[1]}, {move[0],move[1]}};
                        }

                        app.board[opposite_position[0]][opposite_position[1]].setPiece(opposite_piece);
                        app.board[opposite_move[0]][opposite_move[1]] = opposite_original_cell;
                        opposite_piece.setPosition(opposite_position[0], opposite_position[1]);
                    }
                    else{
                        double score = app.getScore();
                        if (score > best_score){
                            best_score = score;
                            best_move = new int[][] {{position[0],position[1]}, {move[0],move[1]}};
                        }
                    }


                    app.board[position[0]][position[1]].setPiece(piece);
                    app.board[move[0]][move[1]] = original_cell;
                    piece.setPosition(position[0], position[1]);
                }
            }

            return best_move;
        }
        else {
            double best_score = 100000;
            int[][] best_move = null;
            ArrayList<ChessPiece> pieces = app.getPieces(turn);
            for(ChessPiece piece : pieces){
                ArrayList<int[]> moves = piece.getLegalMoves(app);
                for (int[] move : moves){

                    Cell original_cell = new Cell(app.board[move[0]][move[1]].getColour(), app.board[move[0]][move[1]].getPiece());
                    int[] position = piece.getPosition();
                    app.board[position[0]][position[1]].setPiece(null);
                    app.board[move[0]][move[1]].setPiece(piece);
                    piece.setPosition(move[0],move[1]);
                    int[][] opposite_best_move = minimax(app, depth - 1, PlayerColour.WHITE);
                    if (opposite_best_move != null){
                        int[] opposite_position = opposite_best_move[0];
                        int[] opposite_move = opposite_best_move[1];
                        Cell opposite_original_cell = new Cell(app.board[opposite_move[0]][opposite_move[1]].getColour(), app.board[opposite_move[0]][opposite_move[1]].getPiece());
                        ChessPiece opposite_piece = app.board[opposite_position[0]][opposite_position[1]].getPiece();
                        
                        app.board[opposite_position[0]][opposite_position[1]].setPiece(null);
                        app.board[opposite_move[0]][opposite_move[1]].setPiece(opposite_piece);
                        opposite_piece.setPosition(opposite_move[0],opposite_move[1]);

                        double score = app.getScore();
                        if (score < best_score){
                            best_score = score;
                            best_move = new int[][] {{position[0],position[1]}, {move[0],move[1]}};
                        }

                        app.board[opposite_position[0]][opposite_position[1]].setPiece(opposite_piece);
                        app.board[opposite_move[0]][opposite_move[1]] = opposite_original_cell;
                        opposite_piece.setPosition(opposite_position[0], opposite_position[1]);
                    }
                    else{
                        double score = app.getScore();
                        if (score < best_score){
                            best_score = score;
                            best_move = new int[][] {{position[0],position[1]}, {move[0],move[1]}};
                        }
                    }

                    app.board[position[0]][position[1]].setPiece(piece);
                    app.board[move[0]][move[1]] = original_cell;
                    piece.setPosition(position[0], position[1]);
                }
            }
            return best_move;
        }
    }

    public void move(App app){
        if (app.isCheck(app.turn)){
            ChessPiece king = app.getKing(app.turn);
            int[] position = king.getPosition();
            app.board[position[0]][position[1]].setColour(CellColour.RED);
        }
        
        // MinMax AI
        int[][] min_max_move = minimax(app, 2, app.turn);
        ChessPiece piece = app.board[min_max_move[0][0]][min_max_move[0][1]].getPiece();
        app.last_move[0] = new int[] {piece.getPosition()[0],piece.getPosition()[1]};
        app.last_move[1] = new int[] {min_max_move[1][0], min_max_move[1][1]};
        piece.move(app, min_max_move[1][0], min_max_move[1][1]);
        return;
    

    }
}
