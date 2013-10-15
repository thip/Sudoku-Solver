package com.company;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
class Cell {

    /**
     * Stores the value of the cell.
     * @see Value
     */
    private Value value;

    /**
     * Stores the potential values that the solving algorithm has decided the cell could hold.
     */
    private HashSet<Value> candidates;

    /**
     * Stores the position of the cell on the board.
     */
    private final int x;
    private final int y;  //Do I need these?

    /**
     * A list which contains all the cells on the same row as this one.
     */
    private LinkedList<Cell> cellRowInBoard;

    /**
     * A list which contains all the cells in the same column as this one
     */
    private LinkedList<Cell> cellColumnInBoard;

    /**
     * A list which contains all the cells in the same box as this one
     */
    private LinkedList<Cell> cellsInBox;



    public Cell(int x, int y, Value value) {

        this.x = x;
        this.y = y;
        this.value = value;


        candidates = new HashSet<Value>(Arrays.asList(Value.ONE,
                Value.TWO,
                Value.THREE,
                Value.FOUR,
                Value.FIVE,
                Value.SIX,
                Value.SEVEN,
                Value.EIGHT,
                Value.NINE));

    }

    /*public Cell(int x, int y) {
        this(x, y, Value.EMPTY);
    } */

    /*public void setCellRowInBox(LinkedList<Cell> cellRowInBox) {
        this.cellRowInBox = cellRowInBox;
    }

    public void setCellColumnInBox(LinkedList<Cell> cellColumnInBox) {
        this.cellColumnInBox = cellColumnInBox;
    }*/

    public HashSet<Value> getCandidates() {
        return candidates;
    }

    public LinkedList<Cell> getCellsInSameRow() {
        return cellRowInBoard;
    }

    public void setCellsInSameRow(LinkedList<Cell> cellRowInBoard) {
        this.cellRowInBoard = cellRowInBoard;
    }

    public LinkedList<Cell> getCellsInSameColumn() {
        return cellColumnInBoard;
    }

    public void getCellsInSameColumn(LinkedList<Cell> cellColumnInBoard) {
        this.cellColumnInBoard = cellColumnInBoard;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public LinkedList<Cell> getCellsInSameBox() {
        return cellsInBox;
    }

    public void setCellsInSameBox(LinkedList<Cell> cellsInBox) {
        this.cellsInBox = cellsInBox;
    }



    public void clearCandidates() {
        this.candidates = new HashSet<Value>();
    }
}
