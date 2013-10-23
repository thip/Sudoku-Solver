package structure;

import java.util.LinkedList;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
class Box extends Structure{




    private final LinkedList<Cell> cellList;

    public Box()
    {
        cellList = new LinkedList<Cell>();

    }



    public void setCell(Cell cell) {


        cellList.add(cell);
    }
}
