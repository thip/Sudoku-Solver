package com.company;

import java.util.LinkedList;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
class Box {
    private final Cell[][] cells;

    public LinkedList<Cell> getCellList() {
        return cellList;
    }

    private final LinkedList<Cell> cellList;

    public Box()
    {
        cellList = new LinkedList<Cell>();
        cells = new Cell[3][3];
    }



    public void setCell(int cellX, int cellY, Cell cell) {

        cells[cellX][cellY] = cell;
        cellList.add(cell);
    }
}
