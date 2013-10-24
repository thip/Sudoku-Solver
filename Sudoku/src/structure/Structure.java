package structure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
public abstract class Structure {

    protected LinkedList<Cell> cellList;

    protected Structure()
    {
        cellList = new LinkedList<Cell>();
    }

    public void addCell(Cell cell)
    {
        cellList.add(cell);
    }

    public LinkedList<Cell> getCellList() {
        return cellList;
    }

    public LinkedList<Cell> getCellsWithCandidate(Value candidate) {

        LinkedList<Cell> cellsWithCandidate = new LinkedList<Cell>();

        for ( Cell cell : cellList)
        {
            if (cell.getValue().equals(Value.EMPTY) && cell.hasCandidate(candidate)) { cellsWithCandidate.add(cell); }
        }

        return cellsWithCandidate;
    }

    public HashSet<Value> getCandidates()
    {
        HashSet<Value> candidates = new HashSet<Value>();

        for (Cell cell : cellList)
        {
            if (cell.getValue().equals(Value.EMPTY)) { candidates.addAll(cell.getCandidates());   }
        }

        return candidates;
    }

    public boolean isValid(){

        HashSet<Value> values = new HashSet<Value>();

        for (Cell cell : cellList)
        {
            Value cellValue = cell.getValue();
            if (!cellValue.equals(Value.EMPTY))
            {
                if (values.contains(cellValue))
                {
                    return false;
                } else {
                    values.add(cellValue);
                }
            }
        }

        return true;
    }

    public boolean isSolved()
    {
        HashSet<Value> values = new HashSet<Value>();

        for (Cell cell : cellList)
        {
            Value cellValue = cell.getValue();
            if (!cellValue.equals(Value.EMPTY))
            {
                if (values.contains(cellValue))
                {
                    return false;
                } else {
                    values.add(cellValue);
                }
            } else {
                return false;
            }
        }

        return true;
    }


}
