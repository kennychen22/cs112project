public interface PuzzleGame {

    public abstract void getGrid();

    public abstract void emptyGrid();

    public abstract void fillGrid();

    public abstract void setSpot (int r, int c);

    public abstract boolean checkCurrent();

    public abstract boolean checkAll();

    public abstract void solvedMessage();





}
