package XXLChess;

import java.util.Random;
import java.util.ArrayList; 
import processing.core.PImage;


public abstract class ChessPiece {
    protected int x;
    protected int y;
    protected PlayerColour colour;
    protected double value;
    protected PImage pieceSprite;

    public ChessPiece(PlayerColour colour, PImage pieceSprite, int x, int y, double value){
        this.pieceSprite = pieceSprite;
        this.x = x;
        this.y = y;
        this.value = value;
        this.colour = colour;
    }

    public int[] getPosition(){
        int[] arr = {this.x, this.y};
        return arr;
    }

    public abstract ArrayList<int[]> getAvailableMoves(App app);

    public ArrayList<int[]> getAvailableCaptures(App app){
        ArrayList<int[]> available_captures = new ArrayList<int[]>();
        ArrayList<int[]> available_moves = this.getAvailableMoves(app);
        for(int[] move: available_moves){
            if (app.occupied_by_enemy(this, move[0], move[1])){
                available_captures.add(new int[] {move[0], move[1]});
            }
        }
        return available_captures;
    }
    public ArrayList<int[]> getAvailableCaptures(App app, ArrayList<int[]> legal_captures, ArrayList<int[]> legal_moves){
        ArrayList<int[]> available_captures = new ArrayList<int[]>();
        ArrayList<int[]> available_moves = this.getAvailableMoves(app);
        for(int[] move: available_moves){
            if (app.occupied_by_enemy(this, move[0], move[1])){
                available_captures.add(new int[] {move[0], move[1]});
            }
        }
        return available_captures;
    }

    public void select(App app){
        ArrayList<int[]> legal_moves = this.getLegalMoves(app);
        for(int[] move : legal_moves){
            if (app.cell_available(move[0], move[1])) app.select_free_cell(move[0], move[1]);
            else app.select_occupied_cell(move[0], move[1]);
        }
    }


    public void move(App app, int new_x, int new_y){
        app.board[this.x][this.y].setPiece(null);
        app.board[new_x][new_y].setPiece(this);
        this.x = new_x;
        this.y = new_y;
    }

    public void take(int new_x, int new_y){
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public PlayerColour getColour(){
        return this.colour;
    }

    public double getValue(){
        return this.value;
    }

    public void draw(App app){
        if (pieceSprite.width != App.CELLSIZE && pieceSprite.height != App.CELLSIZE){
            pieceSprite.resize(App.CELLSIZE, App.CELLSIZE);
        }
        app.image(pieceSprite, x*app.CELLSIZE, y*app.CELLSIZE);
    }


    protected ArrayList<int[]> getMovesRow(App app){
        ArrayList<int[]> available_moves = new ArrayList<int[]>();
        for(int curr_x = this.x+1; curr_x < app.BOARD_WIDTH; curr_x++){
            if (app.cell_available(curr_x, this.y)){
                available_moves.add(new int[] {curr_x, this.y});
            }
            else if(app.occupied_by_enemy(this, curr_x, this.y)){
                available_moves.add(new int[] {curr_x, this.y});
                break;
            }
            else break;
        }
        for(int curr_x = this.x-1; curr_x >= 0; curr_x--){
            if (app.cell_available(curr_x, this.y)){
                available_moves.add(new int[] {curr_x, this.y});
            }
            else if(app.occupied_by_enemy(this, curr_x, this.y)){
                available_moves.add(new int[] {curr_x, this.y});
                break;
            }
            else break;
        }
        return available_moves;
    }

    protected ArrayList<int[]> getMovesColumn(App app){
        ArrayList<int[]> available_moves = new ArrayList<int[]>();
        for(int curr_y = this.y+1; curr_y < app.BOARD_WIDTH; curr_y++){
            if (app.cell_available(this.x, curr_y)){
                available_moves.add(new int[] {this.x, curr_y});
            }
            else if(app.occupied_by_enemy(this, this.x, curr_y)){
                available_moves.add(new int[] {this.x, curr_y});
                break;
            }
            else break;
        }
        for(int curr_y = this.y-1; curr_y >= 0; curr_y--){
            if (app.cell_available(this.x, curr_y)){
                available_moves.add(new int[] {this.x, curr_y});
            }
            else if(app.occupied_by_enemy(this, this.x, curr_y)){
                available_moves.add(new int[] {this.x, curr_y});
                break;
            }
            else break;
        }
        return available_moves;
    }

    protected ArrayList<int[]> getMovesDiagonals(App app){
        ArrayList<int[]> available_moves = new ArrayList<int[]>();
        for(int i = 1; i <= app.BOARD_WIDTH; i++){
            if (app.cell_available(this.x + i, this.y + i)){
                available_moves.add(new int[] {this.x + i, this.y + i});
            }
            else if(app.occupied_by_enemy(this, this.x + i, this.y + i)){
                available_moves.add(new int[] {this.x + i, this.y + i});
                break;
            }
            else
                break;
        }
        for(int i = 1; i <= app.BOARD_WIDTH; i++){
            if (app.cell_available(this.x - i, this.y + i)){
                available_moves.add(new int[]{this.x - i, this.y + i});
            }
            else if(app.occupied_by_enemy(this, this.x - i, this.y + i)){
                available_moves.add(new int[] {this.x - i, this.y + i});
                break;
            }
            else
                break;
        }
        for(int i = 1; i <= app.BOARD_WIDTH; i++){
            if (app.cell_available(this.x + i, this.y - i)){
                available_moves.add(new int[] {this.x + i, this.y - i});
            }
            else if(app.occupied_by_enemy(this, this.x + i, this.y - i)){
                available_moves.add(new int[] {this.x + i, this.y - i});
                break;
            }
            else
                break;
        }
        for(int i = 1; i <= app.BOARD_WIDTH; i++){
            if (app.cell_available(this.x - i, this.y - i)){
                available_moves.add(new int[] {this.x - i, this.y - i});
            }
            else if(app.occupied_by_enemy(this, this.x - i, this.y - i)){
                available_moves.add(new int[] {this.x - i, this.y - i});
                break;
            }
            else break;
        }
        return available_moves;
    }
    protected ArrayList<int[]> getSpecialMoves(App app, int[][] possible_moves){
        ArrayList<int[]> available_moves = new ArrayList<int[]>();
        for (int i = 0; i < possible_moves.length; i++){
            int curr_x = this.x + possible_moves[i][0];
            int curr_y = this.y + possible_moves[i][1];
            if (app.cell_available(curr_x, curr_y) || app.occupied_by_enemy(this, curr_x, curr_y)){
                available_moves.add(new int[] {curr_x, curr_y});
            }
        }
        return available_moves;
    }

    public ArrayList<int[]> getIllegalMoves(App app){
        ArrayList<int[]> illegal_moves = new ArrayList<int[]>();
        ArrayList<int[]> available_moves = this.getAvailableMoves(app);
        for (int[] move : available_moves){
            if(!app.isLegalMove(this.x,this.y, move[0], move[1])) illegal_moves.add(move);
        }
        return illegal_moves;
    }

    public ArrayList<int[]> getLegalMoves(App app){
        ArrayList<int[]> available_moves = getAvailableMoves(app);
        ArrayList<int[]> illegal_moves = getIllegalMoves(app); //new ArrayList<int[]>();
        ArrayList<int[]> moves = new ArrayList<int[]>();
        for(int[] move : available_moves){
            moves.add(new int[] {move[0], move[1]});
            for(int[] illegal_move : illegal_moves){
                if (move[0] == illegal_move[0] && move[1] == illegal_move[1]){
                    moves.remove(moves.size() - 1);
                    break;
                }
            }
        }
        return moves;
    }

    public ArrayList<int[]> getLegalMoves(App app, ArrayList<int[]> available_moves, ArrayList<int[]> illegal_moves){
        ArrayList<int[]> moves = new ArrayList<int[]>();
        for(int[] move : available_moves){
            moves.add(new int[] {move[0], move[1]});
            for(int[] illegal_move : illegal_moves){
                if (move[0] == illegal_move[0] && move[1] == illegal_move[1]){
                    moves.remove(moves.size() - 1);
                    break;
                }
            }
        }
        return moves;
    }

}

class Pawn extends ChessPiece{

    public Pawn(PlayerColour colour, PImage pieceSprite, int x, int y){
        super(colour, pieceSprite, x, y, 1);
    }

    public void move(App app, int new_x, int new_y){
        app.board[this.x][this.y].setPiece(null);
        if(new_y == App.BOARD_WIDTH - 8 && this.colour == PlayerColour.WHITE){
            app.board[new_x][new_y].setPiece(new Queen(PlayerColour.WHITE, App.w_queen, new_x, new_y));
        }
        else if(new_y == 7 && this.colour == PlayerColour.BLACK){
            app.board[new_x][new_y].setPiece(new Queen(PlayerColour.BLACK, App.b_queen, new_x, new_y));
        }
        else{
            this.x = new_x;
            this.y = new_y;
            app.board[new_x][new_y].setPiece(this);
        }
    }
    
    public ArrayList<int[]> getAvailableMoves(App app){
        int direction;
        ArrayList<int[]> available_moves = new ArrayList<int[]>();
        if (this.colour == PlayerColour.BLACK) direction = 1;
            else direction = -1;
        if (app.cell_available(x, y+1*direction)){
            available_moves.add(new int[] {this.x,this.y+1*direction});
            if((this.y == 1 || this.y == 12) && (app.cell_available(this.x,this.y+2*direction))){
                available_moves.add(new int[] {this.x,this.y+2*direction});
            }
        }
        if (app.occupied_by_enemy(this, x+1, y+1*direction)){
            available_moves.add(new int[] {x+1, y+1*direction});
        }
        if (app.occupied_by_enemy(this, x-1, y+1*direction)){
            available_moves.add(new int[] {x-1, y+1*direction});
        }
        return available_moves;
    }

}

class Rook extends ChessPiece{

    public Rook(PlayerColour colour, PImage pieceSprite, int x, int y){
        super(colour, pieceSprite, x, y, 5.25);
    }

    public ArrayList<int[]> getAvailableMoves(App app){
        ArrayList<int[]> available_moves = new ArrayList<int[]>();
        available_moves.addAll(this.getMovesRow(app));
        available_moves.addAll(this.getMovesColumn(app));
        return available_moves;
    }
}


class Bishop extends ChessPiece{
    public Bishop(PlayerColour colour, PImage pieceSprite, int x, int y){
        super(colour, pieceSprite, x, y, 3.625);
    }
    public ArrayList<int[]> getAvailableMoves(App app){
        ArrayList<int[]> available_moves = new ArrayList<int[]>();
        available_moves.addAll(this.getMovesDiagonals(app));
        return available_moves;
    }
}

class Knight extends ChessPiece{ 
    public static final int[][] possible_moves = {{2,1},{-2,1},{-2,-1},{2,-1},{1,2},{-1,2},{-1,-2},{1,-2}};
    public Knight(PlayerColour colour, PImage pieceSprite, int x, int y){
        super(colour, pieceSprite, x, y, 2);
    }
    public ArrayList<int[]> getAvailableMoves(App app){
        ArrayList<int[]> available_moves = new ArrayList<int[]>();
        available_moves.addAll(this.getSpecialMoves(app, possible_moves));
        return available_moves;
    }
}

class Archbishop extends ChessPiece{
    public Archbishop(PlayerColour colour, PImage pieceSprite, int x, int y){
        super(colour, pieceSprite, x, y, 7.5);
    }
    public ArrayList<int[]> getAvailableMoves(App app){
        ArrayList<int[]> available_moves = new ArrayList<int[]>();
        available_moves.addAll(this.getSpecialMoves(app, Knight.possible_moves));
        available_moves.addAll(this.getMovesDiagonals(app));
        return available_moves;
    }
}

class Camel extends ChessPiece{
    public static int[][] possible_moves = {{3,1},{-3,1},{-3,-1},{3,-1},{1,3},{-1,3},{-1,-3},{1,-3}};
    public Camel(PlayerColour colour, PImage pieceSprite, int x, int y){
        super(colour, pieceSprite, x, y, 2);
    } 
    public ArrayList<int[]> getAvailableMoves(App app){
        ArrayList<int[]> available_moves = new ArrayList<int[]>();
        available_moves.addAll(this.getSpecialMoves(app, possible_moves));
        return available_moves;
    }
}

class Guard extends ChessPiece{
    public Guard(PlayerColour colour, PImage pieceSprite, int x, int y){
        super(colour, pieceSprite, x, y, 5);
    }
    public ArrayList<int[]> getAvailableMoves(App app){
        ArrayList<int[]> available_moves = new ArrayList<int[]>();
        available_moves.addAll(this.getSpecialMoves(app, Knight.possible_moves));
        available_moves.addAll(this.getSpecialMoves(app, King.possible_moves));
        return available_moves;
    }
}

class Amazon extends ChessPiece{
    public Amazon(PlayerColour colour, PImage pieceSprite, int x, int y){
        super(colour, pieceSprite, x, y, 12);
    }
    public ArrayList<int[]> getAvailableMoves(App app){
        ArrayList<int[]> available_moves = new ArrayList<int[]>();
        available_moves.addAll(this.getSpecialMoves(app, Knight.possible_moves));
        available_moves.addAll(this.getMovesRow(app));
        available_moves.addAll(this.getMovesColumn(app));
        available_moves.addAll(this.getMovesDiagonals(app));
        return available_moves;
    }
}

class King extends ChessPiece{
    public static final int[][] possible_moves = {{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1},{1,0}};
    public King(PlayerColour colour, PImage pieceSprite, int x, int y){
        super(colour, pieceSprite, x, y, 1000);
    }
    public ArrayList<int[]> getAvailableMoves(App app){
        ArrayList<int[]> available_moves = new ArrayList<int[]>();
        available_moves.addAll(this.getSpecialMoves(app, possible_moves));
        return available_moves;
    }
}

class Chancellor extends ChessPiece{
    public Chancellor(PlayerColour colour, PImage pieceSprite, int x, int y){
        super(colour, pieceSprite, x, y, 8.5);
    }
    public ArrayList<int[]> getAvailableMoves(App app){
        ArrayList<int[]> available_moves = new ArrayList<int[]>();
        available_moves.addAll(this.getSpecialMoves(app, Knight.possible_moves));
        available_moves.addAll(this.getMovesRow(app));
        available_moves.addAll(this.getMovesColumn(app));
        return available_moves;
    }
}

class Queen extends ChessPiece{
    public Queen(PlayerColour colour, PImage pieceSprite, int x, int y){
        super(colour, pieceSprite, x, y, 9.5);
    }
    public ArrayList<int[]> getAvailableMoves(App app){
        ArrayList<int[]> available_moves = new ArrayList<int[]>(); 
        available_moves.addAll(this.getMovesDiagonals(app));
        available_moves.addAll(this.getMovesColumn(app));
        available_moves.addAll(this.getMovesRow(app));
        return available_moves;
    }
}


