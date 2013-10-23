package strategies;

import structure.Board;
import structure.Cell;
import structure.Value;

import java.util.HashSet;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
public class HiddenSingles extends Strategy {

    public HiddenSingles(Board board) {
        super(board);
    }

    @Override
    public void run() {
        hiddenBoxSingle();
        hiddenRowSingle();
        hiddenColumnSingle();
    }

    void hiddenBoxSingle() {
        for (Cell cell : board.getCellList()) {
            if (cell.getValue() == Value.EMPTY) {


                HashSet<Value> mentionedValues = new HashSet<Value>();

                for (Cell conflictingCell : cell.getCellsInSameBox()) {
                    if (conflictingCell.getValue().equals(Value.EMPTY))
                        mentionedValues.addAll(conflictingCell.getCandidates());
                }


                if (!mentionedValues.containsAll(cell.getCandidates())) {
                    cell.getCandidates().removeAll(mentionedValues);
                }


                setCellValues();
            }
        }
    }

    void hiddenRowSingle() {
        for (Cell cell : board.getCellList()) {
            if (cell.getValue() == Value.EMPTY) {


                HashSet<Value> mentionedValues = new HashSet<Value>();

                for (Cell conflictingCell : cell.getCellsInSameRow()) {
                    if (conflictingCell.getValue().equals(Value.EMPTY))
                        mentionedValues.addAll(conflictingCell.getCandidates());
                }


                if (!mentionedValues.containsAll(cell.getCandidates())) {
                    cell.getCandidates().removeAll(mentionedValues);
                }


                setCellValues();
            }
        }
    }

    void hiddenColumnSingle() {
        for (Cell cell : board.getCellList()) {
            if (cell.getValue() == Value.EMPTY) {


                HashSet<Value> mentionedValues = new HashSet<Value>();

                for (Cell conflictingCell : cell.getCellsInSameColumn()) {
                    if (conflictingCell.getValue().equals(Value.EMPTY))
                        mentionedValues.addAll(conflictingCell.getCandidates());
                }


                if (!mentionedValues.containsAll(cell.getCandidates())) {
                    cell.getCandidates().removeAll(mentionedValues);
                }


                setCellValues();
            }
        }
    }
}
