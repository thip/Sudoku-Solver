package strategies;

import structure.Board;
import structure.Cell;
import structure.Value;

import java.util.Collections;
import java.util.LinkedList;

/**
 * This Strategy looks for pairs of cells which have the same pair of candidates in the same row or column.
 * When we find pairs of cells like this we can remove their candidates from the other cells in the same row or column
 *
 * @author David Capper <dmc2@aber.ac.uk>
 * @see Strategy
 */
public class BlockingPairs extends Strategy {

    private final LinkedList<Cell> visitedByPairsInRow = new LinkedList<Cell>();
    private final LinkedList<Cell> visitedByPairsInColumn = new LinkedList<Cell>();


    public BlockingPairs(Board board) {
       super(board);
 }


    @Override
    public void run() {
        stagnated = true;

        //clearActivityLog();

        blockingPairsInRow();
        blockingPairsInColumn();
    }

    void blockingPairsInRow() {



        for (Cell cell : board.getCellList()) {
            if (cell.getValue() == Value.EMPTY) {
                if (cell.getCandidates().size() == 2) {
                    LinkedList<Cell> sameRow = new LinkedList<Cell>(cell.getCellsInSameRow());


                    for (Cell cellFromSameRow : sameRow) {
                        if (!visitedByPairsInRow.contains(cellFromSameRow) && cellFromSameRow.getCandidates().equals(cell.getCandidates())) {
                            visitedByPairsInRow.add(cellFromSameRow);
                            sameRow.remove(cellFromSameRow);



                            for (Cell blockedCell : sameRow) {
                                if (!Collections.disjoint(blockedCell.getCandidates(), cell.getCandidates())){
                                    blockedCell.getCandidates().removeAll(cell.getCandidates()); //naively assume nothing only has one of these as a candidate

                                    stagnated = false;
                                }
                            }
                            setCellValues();
                            break;
                        }
                    }
                }
            }

        }


    }

    void blockingPairsInColumn() {

        for (Cell cell : board.getCellList()) {
            if (cell.getValue() == Value.EMPTY) {
                if (cell.getCandidates().size() == 2) {
                    LinkedList<Cell> sameColumn = new LinkedList<Cell>(cell.getCellsInSameColumn());


                    for (Cell cellFromSameColumn : sameColumn) {
                        if (!visitedByPairsInColumn.contains(cellFromSameColumn) && cellFromSameColumn.getCandidates().equals(cell.getCandidates())) {
                            sameColumn.remove(cellFromSameColumn);

                            for (Cell blockedCell : sameColumn) {
                                if (!Collections.disjoint(blockedCell.getCandidates(), cell.getCandidates())){
                                    blockedCell.getCandidates().removeAll(cell.getCandidates()); //naively assume nothing only has one of these as a candidate

                                    stagnated = false;
                                }
                            }
                            setCellValues();
                            break;
                        }
                    }
                }
            }

        }


    }
}
