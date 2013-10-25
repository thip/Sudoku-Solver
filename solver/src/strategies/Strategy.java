package strategies;

import structure.Board;
import structure.Cell;
import structure.Value;

import java.util.LinkedList;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
public abstract class Strategy {
    final Board board;

    //LinkedList<String> activityLog;

    Strategy(Board board)
    {
        this.board = board;

    }

    public boolean isStagnated() {
        return stagnated;
    }

    boolean stagnated = false;

    public abstract void run();

    public void setCellValues() {
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

   /* protected void clearActivityLog()
    {
        activityLog = new LinkedList<String>();
    }

    void log(String string){
        activityLog.add(string);
    }

    public LinkedList<String> getActivityLog()
    {
        return activityLog;
    } */


}
