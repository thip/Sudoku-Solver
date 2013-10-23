package strategies;

import structure.Board;
import structure.Cell;
import structure.Value;

import java.util.LinkedList;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
public class BlockingPairs extends Strategy {

    private final LinkedList<Cell> visitedByPairsInRow = new LinkedList<Cell>();
    private final LinkedList<Cell> visitedByPairsInColumn = new LinkedList<Cell>();

    public BlockingPairs(Board board) {
        super(board);
    }

    @Override
    public void run() {
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
                                blockedCell.getCandidates().removeAll(cell.getCandidates()); //naively assume nothing only has one of these as a candidate
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
                                blockedCell.getCandidates().removeAll(cell.getCandidates()); //naively assume nothing only has one of these as a candidate
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
