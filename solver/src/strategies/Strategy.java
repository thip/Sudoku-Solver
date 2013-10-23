package strategies;

import structure.Board;
import structure.Cell;
import structure.Value;

import java.util.LinkedList;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
public abstract class Strategy {
    Board board;

    protected Strategy(Board board)
    {
        this.board = board;
    }

    public abstract void run();

    protected void setCellValues() {
        for (Cell cell : board.getCellList()) {

            LinkedList<Cell> conflictingCells = new LinkedList<Cell>();

            conflictingCells.addAll(cell.getCellsInSameColumn());
            conflictingCells.addAll(cell.getCellsInSameRow());
            conflictingCells.addAll(cell.getCellsInSameBox());

            if (cell.getCandidates().size() == 1) {
                Value newValue = (Value) cell.getCandidates().toArray()[0];
                cell.setValue(newValue);
                cell.clearCandidates();


                for (Cell conflictingCell : conflictingCells) {
                    conflictingCell.getCandidates().remove(newValue);
                }

            }
        }
    }


}
