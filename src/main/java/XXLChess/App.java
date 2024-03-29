package XXLChess;

//import org.reflections.Reflections;
//import org.reflections.scanners.Scanners;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.core.PFont;
import processing.event.MouseEvent;
import java.lang.Math;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.awt.Font;
import java.io.*;
import java.util.*;


public class App extends PApplet {

    public static final int SPRITESIZE = 480;
    public static final int CELLSIZE = 48;
    public static final int SIDEBAR = 120;
    public static final int BOARD_WIDTH = 14;
    public Cell[][] board = new Cell[14][14];
    public static int x_clicked;
    public static int y_clicked;
    private static int value;
    private Cell selected_cell;
    public static PImage b_rook, b_knight, b_pawn, b_archbishop, b_bishop, b_camel, b_amazon, b_chancellor, b_guard, b_queen, b_king, w_rook, w_knight, w_pawn, w_archbishop, w_bishop, w_camel, w_amazon, w_chancellor, w_guard, w_queen, w_king;
    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE;
    public static int message = 30;
    public String message2 = "";
    public static final int FPS = 60;
    public int timer = 100*FPS;
    public boolean click;
    private boolean keyboard_pressed;
    public PlayerColour turn;
    public String configPath;
    public int[][] last_move;
    public Timer timer_white, timer_black;
    public AI ai;
    public boolean resigned;
    public PlayerColour colour_of_player;
    public PlayerColour colour_of_cpu;

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void LoadBoard(String Name_of_file){
        try {
            File f = new File(Name_of_file);
            Scanner scan = new Scanner(f);
            for(int j=0; j < BOARD_WIDTH; j++){
                String line = scan.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    char name = line.charAt(i);
                    switch (name){
                        case 'P' :
                            board[i][j].setPiece(new Pawn(PlayerColour.BLACK, b_pawn, i, j));
                            break;
                        case 'R':
                            this.board[i][j].setPiece(new Rook(PlayerColour.BLACK, b_rook, i, j));
                            break;
                        case 'N' :
                            this.board[i][j].setPiece(new Knight(PlayerColour.BLACK, b_knight, i, j));
                            break;
                        case 'B' :
                            this.board[i][j].setPiece(new Bishop(PlayerColour.BLACK, b_bishop, i, j));
                            break;
                        case 'H' :
                            this.board[i][j].setPiece(new Archbishop(PlayerColour.BLACK, b_archbishop, i, j));
                            break;
                        case 'C' :
                            this.board[i][j].setPiece(new Camel(PlayerColour.BLACK, b_camel, i, j));
                            break;
                        case 'G' :
                            this.board[i][j].setPiece(new Guard(PlayerColour.BLACK, b_guard, i, j));
                            break;
                        case 'A' :
                            this.board[i][j].setPiece(new Amazon(PlayerColour.BLACK, b_amazon, i, j));
                            break;
                        case 'K' :
                            this.board[i][j].setPiece(new King(PlayerColour.BLACK, b_king, i, j));
                            break;
                        case 'E' :
                            this.board[i][j].setPiece(new Chancellor(PlayerColour.BLACK, b_chancellor, i, j));
                            break;
                        case 'Q' :
                            this.board[i][j].setPiece(new Queen(PlayerColour.BLACK, b_queen, i, j));
                            break;
                        case 'p' :
                            board[i][j].setPiece(new Pawn(PlayerColour.WHITE, w_pawn, i, j));
                            break;
                        case 'r':
                            this.board[i][j].setPiece(new Rook(PlayerColour.WHITE, w_rook, i, j));
                            break;
                        case 'n' :
                            this.board[i][j].setPiece(new Knight(PlayerColour.WHITE, w_knight, i, j));
                            break;
                        case 'b' :
                            this.board[i][j].setPiece(new Bishop(PlayerColour.WHITE, w_bishop, i, j));
                            break;
                        case 'h' :
                            this.board[i][j].setPiece(new Archbishop(PlayerColour.WHITE, w_archbishop, i, j));
                            break;
                        case 'c' :
                            this.board[i][j].setPiece(new Camel(PlayerColour.WHITE, w_camel, i, j));
                            break;
                        case 'g' :
                            this.board[i][j].setPiece(new Guard(PlayerColour.WHITE, w_guard, i, j));
                            break;
                        case 'a' :
                            this.board[i][j].setPiece(new Amazon(PlayerColour.WHITE, w_amazon, i, j));
                            break;
                        case 'k' :
                            this.board[i][j].setPiece(new King(PlayerColour.WHITE, w_king, i, j));
                            break;
                        case 'e' :
                            this.board[i][j].setPiece(new Chancellor(PlayerColour.WHITE, w_chancellor, i, j));
                            break;
                        case 'q' :
                            this.board[i][j].setPiece(new Queen(PlayerColour.WHITE, w_queen, i, j));
                            break;
                        
                    }
                }
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    public void setup() {
        frameRate(FPS);

        // Load images during setup
        
        b_pawn = loadImage("src/main/resources/XXLChess/b-pawn.png");
        b_rook = loadImage("src/main/resources/XXLChess/b-rook.png");
        b_knight = loadImage("src/main/resources/XXLChess/b-knight.png");
        b_bishop = loadImage("src/main/resources/XXLChess/b-bishop.png");
        b_guard = loadImage("src/main/resources/XXLChess/b-knight-king.png");
        b_queen = loadImage("src/main/resources/XXLChess/b-queen.png");
        b_archbishop = loadImage("src/main/resources/XXLChess/b-archbishop.png");
        b_camel = loadImage("src/main/resources/XXLChess/b-camel.png");
        b_amazon = loadImage("src/main/resources/XXLChess/b-amazon.png");
        b_king = loadImage("src/main/resources/XXLChess/b-king.png");
        b_chancellor = loadImage("src/main/resources/XXLChess/b-chancellor.png");

        w_pawn = loadImage("src/main/resources/XXLChess/w-pawn.png");
        w_rook = loadImage("src/main/resources/XXLChess/w-rook.png");
        w_knight = loadImage("src/main/resources/XXLChess/w-knight.png");
        w_bishop = loadImage("src/main/resources/XXLChess/w-bishop.png");
        w_guard = loadImage("src/main/resources/XXLChess/w-knight-king.png");
        w_queen = loadImage("src/main/resources/XXLChess/w-queen.png");
        w_archbishop = loadImage("src/main/resources/XXLChess/w-archbishop.png");
        w_camel = loadImage("src/main/resources/XXLChess/w-camel.png");
        w_amazon = loadImage("src/main/resources/XXLChess/w-amazon.png");
        w_king = loadImage("src/main/resources/XXLChess/w-king.png");
        w_chancellor = loadImage("src/main/resources/XXLChess/w-chancellor.png");
        


        for(int i = 0; i < BOARD_WIDTH; i++){
            for(int j = 0; j < BOARD_WIDTH; j++){
                if ((i+j)%2 == 0) this.board[i][j] = new Cell(CellColour.LIGHT_BROWN);
                else this.board[i][j] = new Cell(CellColour.DARK_BROWN);
            }
        }
        
        JSONObject conf = loadJSONObject(new File(this.configPath));
        String file_name = conf.getString("layout");

        int seconds_player = conf.getJSONObject("time_controls").getJSONObject("player").getInt("seconds");
        int increment_player = conf.getJSONObject("time_controls").getJSONObject("player").getInt("increment");

        int seconds_cpu = conf.getJSONObject("time_controls").getJSONObject("cpu").getInt("seconds");
        int increment_cpu = conf.getJSONObject("time_controls").getJSONObject("cpu").getInt("increment");

        colour_of_player = ((conf.getString("player_colour").equals("white")) ? PlayerColour.WHITE : PlayerColour.BLACK);
        colour_of_cpu = ((conf.getString("player_colour").equals("white")) ? PlayerColour.BLACK : PlayerColour.WHITE);

        if (colour_of_player == PlayerColour.WHITE){
            timer_white = new Timer((int) seconds_player/60, seconds_player%60, increment_player);
            timer_black = new Timer((int) seconds_cpu/60, seconds_cpu%60, increment_cpu);
        }
        else{
            timer_white = new Timer((int) seconds_cpu/60, seconds_cpu%60, increment_cpu);
            timer_black = new Timer((int) seconds_player/60, seconds_player%60, increment_player);
        }
        
        turn = PlayerColour.WHITE;
        LoadBoard(file_name);
        ai = new AI(this, colour_of_cpu, conf.getString("AI_difficulty"));
        resigned = false;
        last_move = new int[][] {null, null};

    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    @Override
    public void keyPressed() {
        if (key == 'r'){
            setup();
        }
        if (key == 'e'){
            resigned = true;
        }
    }   
    
    public void keyReleased(){

    }

    @Override
    public void mouseClicked(MouseEvent e) {
            x_clicked = e.getX()/CELLSIZE;
            y_clicked = e.getY()/CELLSIZE;
            click = true;
    }

    @Override
    public void mouseDragged(MouseEvent e){ 

    }

    /**
     * Draw all elements in the game by current frame. 
    */
    public void draw() {
        background(169,169,169);
        draw_board();
        textSize(40);
        fill(250, 0, 0);
        if(resigned){
            text("AI won due to resignation", 120, 360);
            return;
        }
        update_time(timer_white, timer_black, turn);
        display_time(timer_white, timer_black, turn);
        if(timer_black.getTime() <= 0 ){
            fill(250, 0, 0);
            if(colour_of_player == PlayerColour.BLACK)
                text("You lost on time",140, 360);
            else
                text("You won on time",140, 360);
            timer_black.finish();
            timer_white.finish();
        }
        else if (timer_white.getTime() <= 0){
            fill(250, 0, 0);
            if(colour_of_player == PlayerColour.WHITE)
                text("You lost on time",140, 360);
            else
                text("You won on time",140, 360);
            timer_black.finish();
            timer_white.finish();
        }
        else if(isCheckMate(PlayerColour.WHITE) || isCheckMate(PlayerColour.BLACK)){
            fill(250, 0, 0);
            if (isCheckMate(PlayerColour.WHITE)){
                if(colour_of_player == PlayerColour.WHITE)
                    text("You lost on checkmate",120, 360);
                else
                    text("You won on checkmate",120, 360);
            }
            else{
                if(colour_of_player == PlayerColour.BLACK)
                    text("You lost on checkmate",120, 360);
                else
                    text("You won on checkmate",120, 360);
            }
        }
        else if(turn == ai.getColour()){
            ai.move(this);
            ChangeTurn();
        }
        else{
            if (click){
                text(x_clicked + " " + y_clicked, 700, 360);
                if(board[x_clicked][y_clicked].getPiece() != null 
                && board[x_clicked][y_clicked].getPiece().getColour() == turn){
                    selected_cell = board[x_clicked][y_clicked];
                    reset_colour();
                    select_current_piece(x_clicked,y_clicked);
                    board[x_clicked][y_clicked].getPiece().select(this);
                }
                else if(board[x_clicked][y_clicked].getColour() == CellColour.LIGHT_BLUE || board[x_clicked][y_clicked].getColour() == CellColour.DARK_BLUE || board[x_clicked][y_clicked].getColour() == CellColour.ORANGE){   
                        selected_cell.getPiece().move(this, x_clicked, y_clicked);
                        last_move[0] = null;
                        last_move[1] = null;
                        reset_colour();
                        ChangeTurn();
                        selected_cell = null;
                    }
                }
            click = false;
        }   

    }
    public ChessPiece getKing(Cell[][] board, PlayerColour colour){
        for(int i = 0; i < BOARD_WIDTH; i++){
            for(int j = 0; j < BOARD_WIDTH; j++){
                ChessPiece piece = board[i][j].getPiece();
                if((piece instanceof King) && (piece.getColour() == colour))
                    return board[i][j].getPiece();
            }
        }
        System.out.println("it's null");
        return null;
    }

    public void update_time(Timer timer_white, Timer timer_black, PlayerColour turn){
        if (timer_black.getTime() <= 0 || timer_white.getTime() <= 0 || isCheckMate(PlayerColour.WHITE) || isCheckMate(PlayerColour.BLACK)){
            timer_black.finish();
            timer_white.finish();
        }
        else if (turn == PlayerColour.WHITE){
            if(timer_black.isRunning()){
                timer_black.stop();
            }
            if(!timer_white.isRunning()){
                timer_white.start();
            }
        }
        else{
            if(timer_white.isRunning()){
                timer_white.stop();
            }
            if(!timer_black.isRunning()){
                timer_black.start();
            }
        }
    }

    public double getScore() {
        double score = 0;
    
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                ChessPiece piece = board[i][j].getPiece();
                if (piece != null && !(piece instanceof King)) {
                    double value = piece.getValue();
                    if (piece.getColour() == PlayerColour.WHITE) {
                        if(i >= 3 && i <= 10 && j >=3 && j <= 10){
                            score++;
                        }
                        score += value;
                        // score += piece.getLegalMoves(this).size();
                    }
                    else {
                        if(i >= 3 && i <= 10 && j >= 3 && j <= 10){
                            score--;
                        }
                        score -= value;
                        // score -= piece.getLegalMoves(this).size();
                    }
                }
            }
        }
        if (isCheck(PlayerColour.WHITE)) {
            score -= 5;
        }
        if (isCheck(PlayerColour.BLACK)) {
            score += 5;
        }

        if (isCheckMate(PlayerColour.WHITE)) {
            score -= 10000;
        }
        if (isCheckMate(PlayerColour.BLACK)) {
            score += 10000;
        }
        return score;
    }
    

    public void display_time(Timer timer_white, Timer timer_black, PlayerColour turn){
        fill(255,255,255);
        long time_white = timer_white.getTime();
        long time_black = timer_black.getTime();
        String seconds = Long.toString(time_white%60);
        if(time_white%60 < 10) seconds = "0" + seconds;
        String time = Long.toString(time_white/60) + ":" + seconds;
        text(time, 675, 600);
        seconds = Long.toString(time_black%60);
        if(time_black%60 < 10) seconds = "0" + seconds;
        time = Long.toString(time_black/60) + ":" + seconds;
        text(time, 675, 100);
    }

    public ChessPiece getKing(PlayerColour colour){
        return getKing(this.board, colour);
    }
    public boolean isCheck(PlayerColour turn){
        ChessPiece king = getKing(this.board, turn);
        ArrayList<ChessPiece> opposite_pieces;
        if (turn == PlayerColour.WHITE){
            opposite_pieces = getPieces(board, PlayerColour.BLACK);
        }
        else {
            opposite_pieces = getPieces(board, PlayerColour.WHITE);
        }
        int[] king_position = king.getPosition();
        for(ChessPiece piece : opposite_pieces){
            ArrayList<int[]> moves = piece.getAvailableCaptures(this);
            for(int[] move : moves){
                if (move[0] == king_position[0] && move[1] == king_position[1]) return true;
            }
        }
        return false;
    }

    public boolean isCheck(){
        return isCheck(turn);
    }

    public ArrayList<ChessPiece> getPieces(PlayerColour colour){ 
        return getPieces(this.board, colour);
    }

    public ArrayList<ChessPiece> getPieces(Cell[][] board, PlayerColour colour){
        ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();
        for(int i = 0; i < BOARD_WIDTH; i++){
            for(int j = 0; j < BOARD_WIDTH; j++){
                if(board[i][j].getPiece() != null && board[i][j].getPiece().getColour() == colour){
                    ChessPiece piece = board[i][j].getPiece();
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }
    public ArrayList<ChessPiece> getPiecesCheckmate(){
        ArrayList<ChessPiece> pieces;
        ArrayList<ChessPiece> result = new ArrayList<ChessPiece>();
        ChessPiece king = getKing(turn);
        int[] king_position = king.getPosition();
        if (turn == PlayerColour.BLACK)
            pieces = getPieces(PlayerColour.WHITE);
        else
            pieces = getPieces(PlayerColour.BLACK);
        for (ChessPiece piece : pieces){
            ArrayList<int[]> moves = piece.getAvailableMoves(this);
            for(int[] move : moves){
                if ((Math.abs(move[0] - king_position[0]) + Math.abs(move[1] - king_position[1]) <= 2)&&(Math.abs(move[1] - king_position[1]) != 2)&& (Math.abs(move[0] - king_position[0]) != 2) ){
                    result.add(piece);
                }
            }
        }
        return result;
    }

    public boolean isLegalMove(ChessPiece piece, int new_x, int new_y){
        boolean legal = true;
        int[] position = piece.getPosition();
        Cell original_cell = new Cell(board[new_x][new_y].getColour(), board[new_x][new_y].getPiece());
        this.board[position[0]][position[1]].setPiece(null);
        this.board[new_x][new_y].setPiece(piece);
        piece.setPosition(new_x,new_y);
        if (isCheck(piece.getColour())){
            legal = false;
        }
        this.board[position[0]][position[1]].setPiece(piece);
        this.board[new_x][new_y] = original_cell;
        piece.setPosition(position[0], position[1]);
        return legal;
    }
    public boolean isLegalMove(int old_x, int old_y, int new_x, int new_y){
        ChessPiece piece = board[old_x][old_y].getPiece();
        boolean legal = true;
        Cell original_cell = new Cell(board[new_x][new_y].getColour(), board[new_x][new_y].getPiece());
        this.board[old_x][old_y].setPiece(null);
        this.board[new_x][new_y].setPiece(piece);
        piece.setPosition(new_x,new_y);
        if (isCheck()){
            legal = false;
        }
        this.board[old_x][old_y].setPiece(piece);
        this.board[new_x][new_y] = original_cell;
        piece.setPosition(old_x, old_y);
        return legal;
    }

    public void reset_colour(){
        for(int i = 0; i < BOARD_WIDTH; i++){
            for(int j = 0; j < BOARD_WIDTH; j++){
                // if(board[i][j].getColour() != CellColour.YELLOW || board[i][j].getColour() != CellColour.RED){
                    if ((i+j)%2 == 0) this.board[i][j].setColour(CellColour.LIGHT_BROWN);
                    else this.board[i][j].setColour(CellColour.DARK_BROWN);
                // }
            }
        }
    }

    public boolean isCheckMate(PlayerColour turn){
        ArrayList<ChessPiece> pieces = getPieces(turn);
        for (ChessPiece piece : pieces){
            if(piece.getLegalMoves(this).size() > 0){
                return false;
            }
        }
        if (isCheck(turn)){
            return true;
        }
        return false;
    }

    public boolean isCheckMate(){
        return isCheckMate(turn);
    }

    public void reset_last_move(){
        for(int i = 0; i < BOARD_WIDTH; i++){
            for(int j = 0; j < BOARD_WIDTH; j++){
                if(board[i][j].getColour() == CellColour.YELLOW){
                    if ((i+j)%2 == 0) this.board[i][j].setColour(CellColour.LIGHT_BROWN);
                    else this.board[i][j].setColour(CellColour.DARK_BROWN);
                }
            }
        }
    }

    private void draw_board(){
        if (last_move[0] != null && last_move[1] != null){
            draw_move(last_move[0][0], last_move[0][1], last_move[1][0], last_move[1][1]);
        }

        if(isCheck()){
            ChessPiece king = getKing(turn);
            int[] position = king.getPosition();
            if(board[position[0]][position[1]].getColour() == CellColour.LIGHT_BROWN ||
                board[position[0]][position[1]].getColour() == CellColour.DARK_BROWN){
                    board[position[0]][position[1]].setColour(CellColour.RED);
            }
        }
        if (isCheckMate()){
            ArrayList<ChessPiece> pieces= getPiecesCheckmate();
            for(ChessPiece piece : pieces){
                int[] position = piece.getPosition();
                board[position[0]][position[1]].setColour(CellColour.ORANGE);
            }
        }

        for(int i = 0; i < BOARD_WIDTH; i++){
            for(int j = 0; j < BOARD_WIDTH; j++){
                fill(this.board[i][j].getColour().R,this.board[i][j].getColour().G,this.board[i][j].getColour().B);
                this.rect(i*CELLSIZE, j*CELLSIZE, CELLSIZE, CELLSIZE);
                if(this.board[i][j].getPiece() != null){
                    this.board[i][j].getPiece().draw(this);
                }
            }
        }

    }
    public void select_current_piece(int x, int y){
        board[x][y].setColour(CellColour.GREEN);
    }

    public void select_free_cell(ArrayList<int[]> available_moves){
        for(int[] move : available_moves){
            this.select_free_cell(move[0], move[1]);
        }
    }

    public void select_free_cell(int x, int y){
        if((x+y)%2 == 0){
            board[x][y].setColour(CellColour.LIGHT_BLUE);
        }
        else board[x][y].setColour(CellColour.DARK_BLUE);
    }

    public void draw_move(int old_x, int old_y, int new_x, int new_y){
        if (board[old_x][old_y].getColour() == CellColour.LIGHT_BROWN 
            || board[old_x][old_y].getColour() == CellColour.DARK_BROWN){
        board[old_x][old_y].setColour(CellColour.YELLOW);
        }
        if (board[new_x][new_y].getColour() == CellColour.LIGHT_BROWN 
            || board[new_x][new_y].getColour() == CellColour.DARK_BROWN){
        board[new_x][new_y].setColour(CellColour.YELLOW);
        }
    }


    public void select_occupied_cell(int x, int y){
        board[x][y].setColour(CellColour.ORANGE);
    }

    public void select_occupied_cell(ArrayList<int[]> available_moves){
        for(int[] move : available_moves){
            this.select_occupied_cell(move[0], move[1]);
        }
    }
    
    public boolean occupied_by_enemy(ChessPiece piece, int x, int y){
        if (y < BOARD_WIDTH && y >= 0 && x < BOARD_WIDTH && x >= 0 &&board[x][y].getPiece() != null && board[x][y].getPiece().getColour() != piece.getColour()) return true;
        else return false;
    }
    public boolean cell_available(int x, int y){
        if (y < BOARD_WIDTH && y >= 0 && x < BOARD_WIDTH && x >= 0 && board[x][y].getPiece() == null)
            return true;
        return false;
    }

    public void ChangeTurn(){
        if(this.turn == PlayerColour.WHITE) this.turn = PlayerColour.BLACK;
        else this.turn = PlayerColour.WHITE;
    }

	
	// Add any additional methods or attributes you want. Please put classes in different files.


    public static void main(String[] args) {
        PApplet.main("XXLChess.App");
    }

}
