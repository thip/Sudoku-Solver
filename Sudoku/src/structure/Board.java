package structure;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
public class Board extends Structure{

    /* fields */

    private final Box[][] boxes;
    private final Cell[][] cells;


    private final LinkedList<Box> boxList;
    private final LinkedList<Line> rows;
    private final LinkedList<Line> columns;

    public Board(Board board) {
        super();

        //Initialise the arrays and lists
        cells = new Cell[9][9];
        boxes = new Box[3][3];
        cellList = new LinkedList<Cell>(board.cellList);
        boxList = new LinkedList<Box>(board.boxList);
        rows = new LinkedList<Line>(board.rows);
        columns = new LinkedList<Line>(board.columns);

        System.arraycopy(board.cells, 0, this.cells, 0, board.cells.length);
    }



    /* accessors */


    public  LinkedList<Line> getColumns()
    {
         return columns;
    }

    public  LinkedList<Line> getRows ()
    {
         return rows;
    }


    /* methods */

    public Board( String s )
    {
        super();

        //Initialise the arrays and lists
        cells = new Cell[9][9];
        boxes = new Box[3][3];
        cellList = new LinkedList<Cell>();
        boxList = new LinkedList<Box>();
        rows = new LinkedList<Line>();
        columns = new LinkedList<Line>();

        //then create the cells and boxes and knit them all together
        fillCells(s);
        fillBoxes();
        fillRowLists();
        fillColumnLists();
        fillBoxLists();

        fillRowsAndColumns();
    }



    private void fillRowsAndColumns() {
        for (int index = 0; index < 9; index++) {
            Line row = new Line();
            Line column = new Line();

            for (int position = 0; position < 9; position++) {
                row.addCell(cells[position][index]);
                column.addCell(cells[index][position]);
            }

            rows.add(row);
            columns.add(column);
        }
    }

    private void fillCells( String s )
    {
        //Split up the string into an array of lines.
        String[] lines = s.split("\n");

        //For each row on the board
        for (int yPosition = 0; yPosition < 9; yPosition++) {

            //And each cell position in that row
            for (int xPosition = 0; xPosition < 9; xPosition++) {

                //create a new cell
                Cell cell = new Cell(   xPosition,  //  Pass the cell
                                        yPosition,  //  it's position.

                                        //and give alue, converted from the current character in the line.
                                        Value.characterToValue(lines[yPosition].charAt(xPosition))
                                    );

                //Add the cell to the array and the list
                cells[xPosition][yPosition] = cell;
                cellList.add(cell);
            }

        }

    }



    private void fillBoxes()
    {
        //For each column of Boxes
        for (int boxX = 0; boxX < 3; boxX++) {

            //And for each Box in that Column
            for (int boxY = 0; boxY < 3; boxY++) {

                //Create a new box and...
                Box box = new Box();

                //set the relevent position in the array to it
                boxes[boxX][boxY] = box;

                //and add it to the list of boxes.
                boxList.add(box);


                /**********************************************************************************************
                    The following code matches each cell in the box to the relevant cell in the boards array
                 **********************************************************************************************/

                //For each column of cells in the box
                for (int cellX = 0; cellX < 3; cellX++) {

                    //and for each cell in that box
                    for (int cellY = 0; cellY < 3; cellY++) {

                        //work out the cells position on the board
                        int boardX = (boxX * 3)+ cellX;
                        int boardY = (boxY * 3)+ cellY;

                        //and add that cell to the current box.
                        boxes[boxX][boxY].setCell(cells[boardX][boardY]);
                        box.addCell(cells[boardX][boardY]);

                    }

                }

            }

        }

    }


    private void fillRowLists()
    {
        //Declare a LinkedList to represent the row
        LinkedList<Cell> row;

        //For each row on the board
        for (int rowNumber = 0; rowNumber < 9; rowNumber++)  {

            //create a new list of cells to represent that row.
            row = new LinkedList<Cell>();

            //and for each cell in the row
            for (int xPosition = 0; xPosition < 9; xPosition++) {

                //add the cell to the row's list
                row.add(cells[xPosition][rowNumber]);

            }

            //For each cell in this row
            for ( Cell cell : row) {

                //create a list of all the cells in the row
                LinkedList<Cell> rowMinusThisCell = new LinkedList<Cell>(row);

                //remove the current cell from the list
                rowMinusThisCell.remove(cell);

                //and pass this list to the cell so it knows about its horizontal neighbours
                cell.setCellsInSameRow(rowMinusThisCell);
            }

        }

    }



    private void fillColumnLists()
    {

        //Declare a LinkedList to represent the column
        LinkedList<Cell> column;

        //For each column on the board
        for (int columnNumber = 0; columnNumber < 9; columnNumber++) {

            //create a new list of cells representing the column
            column = new LinkedList<Cell>();

            //Convert the current column in the board's array to a list and fill the column list with it.
            column.addAll(Arrays.asList(cells[columnNumber]).subList(0, 9));


            //For each cell in this column
            for ( Cell cell : column) {

                //create a list of all the cells in this row
                LinkedList<Cell> columnMinusThisCell = new LinkedList<Cell>(column);

                //remove the current cell from the list
                columnMinusThisCell.remove(cell);

                //and pass this list to the cell so it knows about it's vertical neighbours
                cell.getCellsInSameColumn(columnMinusThisCell);
            }

        }

    }

    private void fillBoxLists()
    {
        //For each box on the board
        for (Box theBox : boxList)
        {
            //And for each cell in each box
            for (Cell cell : theBox.getCellList())
            {
                //create a list of all the cells in the box
                LinkedList<Cell> someList = new LinkedList<Cell>(theBox.getCellList());

                //remove the current cell from the list
                someList.remove(cell);

                //and pass this list to the cell so it knows about its neighbours in its box.
                cell.setCellsInSameBox(someList);
            }

        }

    }

    @Override
    public boolean isValid()
    {
        for (Line line : rows)
        {
            if (!line.isValid())
            {
                return false;
            }
        }

        for (Line line : columns)
        {
            if (!line.isValid())
            {
                return false;
            }
        }

        for (Box box : boxList)
        {
            if (!box.isValid())
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isSolved()
    {
        for (Line line : rows)
        {
            if (!line.isSolved())
            {
                return false;
            }
        }

        for (Line line : columns)
        {
            if (!line.isSolved())
            {
                return false;
            }
        }

        for (Box box : boxList)
        {
            if (!box.isSolved())
            {
                return false;
            }
        }

        return true;
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

    /**
     * Checks first to see if obj is a Board, then checks if the arrays in this and the object are the same.
     * @param obj The object we are checking for equality against.
     * @return  True or False depending on whether the objects are equal or not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
        {
            return false;
        }

        if (this.getClass() != obj.getClass())
        {
            return false;
        }

        return Arrays.deepEquals(this.cells, ((Board)obj).cells);
    }


    public LinkedList<Box> getBoxes() {
        return boxList;
    }
}
