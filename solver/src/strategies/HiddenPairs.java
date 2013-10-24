package strategies;

import structure.*;


import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *   Kind of broken
 *
 * @author David Capper <dmc2@aber.ac.uk>
 */
public class HiddenPairs extends Strategy {

    LinkedList<Cell> alreadyVisited = new LinkedList<Cell>();

    public HiddenPairs(Board board) {
        super(board);
    }

    @Override
    public void run() {
        stagnated = true;

        for (Line row : board.getRows()) {
            hiddenPairsInStructure(row);
        }

        for (Line column : board.getColumns()) {
            hiddenPairsInStructure(column);
        }

        for (Box box : board.getBoxes()) {
            hiddenPairsInStructure(box);
        }
    }

    void hiddenPairsInStructure(Structure structure) {
        for (Cell cell : structure.getCellList()) {

            if (cell.getValue().equals(Value.EMPTY)) {

                LinkedList<Cell> otherCells = new LinkedList<Cell>((structure.getCellList()));
                otherCells.remove(cell);

                LinkedList<Cell> potentialCells = new LinkedList<Cell>();
                potentialCells.add(cell);

                HashSet<Value> candidates = cell.getCandidates();

                for (Cell otherCell : otherCells) {
                    if (otherCell.getValue().equals(Value.EMPTY)) {
                        HashSet<Value> potentialPair = new HashSet<Value>(candidates);
                        potentialPair.retainAll(otherCell.getCandidates());

                        if (potentialPair.size() == 2) {
                            LinkedList<Cell> otherOtherCells = new LinkedList<Cell>(otherCells);
                            otherOtherCells.remove(otherCell);

                            boolean winner = true;

                            for (Cell otherOtherCell : otherOtherCells)
                                if (otherOtherCell.getValue().equals(Value.EMPTY)) {
                                    {
                                        if (!Collections.disjoint(otherOtherCell.getCandidates(), potentialPair) || ( alreadyVisited.contains(cell) || alreadyVisited.contains(otherCell) ) ){
                                            winner = false;
                                            break;
                                        }

                                    }
                                }

                            if (winner) {
                                cell.getCandidates().retainAll(potentialPair);
                                otherCell.getCandidates().retainAll(potentialPair);

                                alreadyVisited.add(cell);
                                alreadyVisited.add(otherCell);

                                stagnated = false;
                                break;
                            }

                        }
                    }
                }
            }
        }

        setCellValues();

    }
}


