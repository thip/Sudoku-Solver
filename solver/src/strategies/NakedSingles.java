package strategies;

import strategies.Strategy;
import structure.Board;
import structure.Cell;
import structure.Value;

import java.util.Collections;
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
        stagnated = true;

        //clearActivityLog();

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

                if (!takenValues.containsAll(cell.getCandidates())) {
                    if (!Collections.disjoint(cell.getCandidates(), takenValues) )
                    {
                        HashSet<Value> candidatesToGo = new HashSet<Value>(cell.getCandidates());
                        candidatesToGo.retainAll(takenValues);

                        cell.getCandidates().removeAll(takenValues);

                        //log("Naked Singles: removing used candidates: " + candidatesToGo + " from cell (" + (cell.getX()+1) + "," + (cell.getY()+1) + ")");

                        stagnated = false;
                    }
                }


                setCellValues();
            }
        }
    }
}

