package XXLChess;
public class Cell {
    private CellColour colour;
    private ChessPiece piece;

    public Cell(CellColour colour,ChessPiece piece){
        this.colour = colour;
        this.piece = piece;
    }
    
    public Cell(CellColour colour){
        this.colour = colour;
    }

    public CellColour getColour(){
        return this.colour;
    }

    public void setColour(CellColour colour){
        this.colour = colour;
    }

    public ChessPiece getPiece(){
        return this.piece; 
    }

    public void setPiece(ChessPiece piece){
        this.piece = piece;
    }
}
