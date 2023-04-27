package XXLChess;
public enum CellColour{
    LIGHT_BLUE (173, 216, 230),
    DARK_BLUE (135, 206, 250),
    LIGHT_BROWN (232, 235, 239),
    DARK_BROWN (125, 135, 150),
    GREEN (50,205,50),
    YELLOW (255,255,102),
    ORANGE (255, 172, 28),
    RED (170, 74, 68);
    public final int R;
    public final int G;
    public final int B;
    CellColour(int R, int G, int B){
        this.R = R;
        this.G = G;
        this.B = B;
    }
}
