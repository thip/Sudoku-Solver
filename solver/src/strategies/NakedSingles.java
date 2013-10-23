package strategies;

import strategies.Strategy;
import structure.Board;
import structure.Cell;
import structure.Value;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
public class NakedSingles extends Strategy {

    public NakedSingles(Board board) {
        super(board);
    }

    @Override
    public void run() {
        for (Cell cell : board.getCellList()) {
            if (cell.getValue() == Value.EMPTY) {
                LinkedList<Cell> conflictingCells = new LinkedList<Cell>();

                conflictingCells.addAll(cell.getCellsInSameColumn());
                conflictingCells.addAll(cell.getCellsInSameRow());
                conflictingCells.addAll(cell.getCellsInSameBox());


                HashSet<Value> takenValues = new HashSet<Value>();

                for (Cell conflictingCell : conflictingCells) {
                    if (conflictingCell.getValue() != Value.EMPTY) takenValues.add(conflictingCell.getValue());
                }

                if (!takenValues.containsAll(cell.getCandidates())) cell.getCandidates().removeAll(takenValues);


                setCellValues();
            }
        }
    }
}

