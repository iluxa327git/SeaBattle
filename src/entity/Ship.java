package entity;

public class Ship {

    private int[][] position;
    private int cellsCount;
    private boolean[] cellsState;
    private boolean isVertical;

    public Ship(int[][] position, int cellsCount, boolean[] cellsState, boolean isVertical) {
        this.position = position;
        this.cellsCount = cellsCount;
        this.cellsState = cellsState;
        this.isVertical = isVertical;
    }

    public Ship() {
    }

    public int[][] getPosition() {
        return position;
    }

    public void setPosition(int[][] position) {
        this.position = position;
    }

    public int getCellsCount() {
        return cellsCount;
    }

    public void setCellsCount(int cellsCount) {
        this.cellsCount = cellsCount;
    }

    public boolean[] getCellsState() {
        return cellsState;
    }

    public void setCellsState(boolean[] cellsState) {
        this.cellsState = cellsState;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }
}
