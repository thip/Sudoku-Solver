package com.company;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
class Board {

    private final Box[][] boxes;
    private final Cell[][] cells;

    private final LinkedList<Cell> cellList;
    private final LinkedList<Box> boxList;


    private void fillCells( String s )
    {
        String[] lines = s.split("\n");
        Value value = Value.EMPTY;

        for (int ii = 0; ii < 9; ii++) {
            for (int jj = 0; jj < 9; jj++) {
                switch(lines[ii].charAt(jj))
                {
                    case ' ': value = Value.EMPTY; break;
                    case '1': value = Value.ONE; break;
                    case '2': value = Value.TWO; break;
                    case '3': value = Value.THREE; break;
                    case '4': value = Value.FOUR; break;
                    case '5': value = Value.FIVE; break;
                    case '6': value = Value.SIX; break;
                    case '7': value = Value.SEVEN; break;
                    case '8': value = Value.EIGHT; break;
                    case '9': value = Value.NINE; break;


                }
                Cell cell = new Cell(ii, jj, value);

                cells[ii][jj] = cell;
                cellList.add(cell);
            }

        }
    }

    public LinkedList<Cell> getCellList() {
        return cellList;
    }

    private void fillBoxes()
    {
        for (int boxX = 0; boxX < 3; boxX++) {
            for (int boxY = 0; boxY < 3; boxY++) {

                Box box = new Box();
                boxes[boxX][boxY] = box;
                boxList.add(box);

                for (int cellX = 0; cellX < 3; cellX++) {
                    for (int cellY = 0; cellY < 3; cellY++) {
                        int boardX = (boxX * 3)+ cellX;
                        int boardY = (boxY * 3)+ cellY;



                        boxes[boxX][boxY].setCell(cellX, cellY, cells[boardX][boardY]);

                    }
                }

            }
        }
    }

    private void fillRowLists()
    {
        LinkedList<Cell> row;
        for (int ii = 0; ii < 9; ii++) {
            row = new LinkedList<Cell>();
            for (int jj = 0; jj < 9; jj++) {
                row.add(cells[jj][ii]);
            }
            for ( Cell cell : row)
            {
                LinkedList<Cell> rowMinusThisCell = new LinkedList<Cell>(row);
                rowMinusThisCell.remove(cell);
                cell.setCellsInSameRow(rowMinusThisCell);
            }
        }

    }

    private void fillColumnLists()
    {
        LinkedList<Cell> column;
        for (int ii = 0; ii < 9; ii++) {

            column = new LinkedList<Cell>();
            column.addAll(Arrays.asList(cells[ii]).subList(0, 9));

            for ( Cell cell : column)
            {
                LinkedList<Cell> columnMinusThisCell = new LinkedList<Cell>(column);
                columnMinusThisCell.remove(cell);
                cell.getCellsInSameColumn(columnMinusThisCell);
            }
        }

    }

    private void fillBoxLists()
    {
       // LinkedList<Box> cellsBoxList = new LinkedList<Box>();

        for (Box theBox : boxList)
        {
            for (Cell cell : theBox.getCellList())
            {
                LinkedList<Cell> someList = new LinkedList<Cell>(theBox.getCellList());
                someList.remove(cell);
                cell.setCellsInSameBox(someList);
            }
        }

    }



    //Crazy knitting begins
    public Board( String s )
    {
        cells = new Cell[9][9];
        boxes = new Box[3][3];
        cellList = new LinkedList<Cell>();
        boxList = new LinkedList<Box>();


        fillCells(s);
        fillBoxes();
        fillRowLists();
        fillColumnLists();
        fillBoxLists();

    }



    @Override
    public String toString() {
        String result = "";

        for (int ii = 0; ii < 9; ii++) {
            for (int jj = 0; jj < 9; jj++) {
                result  += cells[ii][jj].getValue().toInt();

            }
            result += "\n";
        }

        return result;
    }


}
