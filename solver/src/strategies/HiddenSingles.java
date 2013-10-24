package strategies;

import structure.Board;
import structure.Cell;
import structure.Value;

import java.util.Collections;
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
        stagnated = true;
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

                    if (!Collections.disjoint(cell.getCandidates(), mentionedValues))
                    {
                        cell.getCandidates().removeAll(mentionedValues);
                        stagnated = false;
                    }

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
                    if (!Collections.disjoint(cell.getCandidates(), mentionedValues))
                    {
                        cell.getCandidates().removeAll(mentionedValues);
                        stagnated = false;
                    }

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
                    if (!Collections.disjoint(cell.getCandidates(), mentionedValues))
                    {
                        cell.getCandidates().removeAll(mentionedValues);
                        stagnated = false;
                    }
                }


                setCellValues();
            }
        }
    }
}
