package XXLChess;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class AI {
    private PlayerColour colour;
    private ArrayList<ChessPiece> available_pieces;
    private Difficulty difficulty;

    public enum Difficulty{
        EASY,
        MEDIUM,
        HARD}
    

    public AI(App app, PlayerColour colour, String difficulty){
        this.colour = colour;
        this.available_pieces = app.getPieces(this.colour);

        if(difficulty.toUpperCase().equals("EASY")){
            this.difficulty = Difficulty.EASY;
        }
        else if(difficulty.toUpperCase().equals("MEDIUM")){
            this.difficulty = Difficulty.MEDIUM;
        }
        else if(difficulty.toUpperCase().equals("HARD")){
            this.difficulty = Difficulty.HARD;
        }
        else{
            this.difficulty = Difficulty.EASY;
        }

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

    public int[][] random_move(App app){
        ArrayList<ChessPiece> pieces = app.getPieces(app.turn);
        int[][] move;
        for(int i = 0; i < 1000; i++){
            ChessPiece piece = pieces.get((int)(Math.random() * pieces.size()));
            ArrayList<int[]> moves = piece.getLegalMoves(app);
            if (moves.size() > 0){
                int index = (int)(Math.random() * moves.size());
                move = new int[][] {{piece.getPosition()[0], piece.getPosition()[1]},{moves.get(index)[0], moves.get(index)[1]}};
                return move;
            }
        }
        return null;
    }

    public void move(App app){
        if (app.isCheck(this.colour)){
            ChessPiece king = app.getKing(this.colour);
            int[] position = king.getPosition();
            app.board[position[0]][position[1]].setColour(CellColour.RED);
        }
        
        int[][] move = new int[2][2];
        if(this.difficulty == Difficulty.EASY){
            move = random_move(app);
        }
        else if(this.difficulty == Difficulty.MEDIUM){
            move = minimax(app, 1, this.colour);
        }
        else if(this.difficulty == Difficulty.HARD){
            move = minimax(app, 2, this.colour);
        }
        else{
            move = random_move(app);
        }
        ChessPiece piece = app.board[move[0][0]][move[0][1]].getPiece();
        app.last_move[0] = new int[] {piece.getPosition()[0],piece.getPosition()[1]};
        app.last_move[1] = new int[] {move[1][0], move[1][1]};
        piece.move(app, move[1][0], move[1][1]);
        return;
    

    }
}
