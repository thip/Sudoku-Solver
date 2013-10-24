package strategies;

import structure.Board;
import structure.Cell;
import structure.Value;

import java.util.Collections;
import java.util.LinkedList;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
public class PointingCells extends Strategy {

    public PointingCells(Board board) {
        super(board);
    }

    @Override
    public void run() {
        stagnated = true;
        pointingCellsInColumns();
        pointingCellsInRows();
    }

    void pointingCellsInColumns() {
        for (Cell cell : board.getCellList()) {
            if (cell.getValue().equals(Value.EMPTY)) {
                LinkedList<Cell> cellsInWholeColumn = new LinkedList<Cell>(cell.getCellsInSameColumn());
                LinkedList<Cell> cellsInSameBox = new LinkedList<Cell>(cell.getCellsInSameBox());

                LinkedList<Cell> cellsInColumnAndBox = new LinkedList<Cell>(cellsInSameBox);
                cellsInColumnAndBox.retainAll(cellsInWholeColumn);

                LinkedList<Cell> cellsInBoxNotColumn = new LinkedList<Cell>(cellsInSameBox);
                cellsInBoxNotColumn.removeAll(cellsInColumnAndBox);

                LinkedList<Cell> cellsInColumnNotBox = new LinkedList<Cell>(cellsInWholeColumn);
                cellsInColumnNotBox.removeAll(cellsInColumnAndBox);

                LinkedList<Value> candidatesInColumnAndBox = new LinkedList<Value>(cell.getCandidates());


                for (Cell cellFromColumnAndBox : cellsInColumnAndBox) {
                    if (cellFromColumnAndBox.getValue().equals(Value.EMPTY)) {
                        candidatesInColumnAndBox.addAll(cellFromColumnAndBox.getCandidates());
                    }
                }

                for (Cell cellFromBoxNotColumn : cellsInBoxNotColumn) {
                    if (cellFromBoxNotColumn.getValue().equals(Value.EMPTY)) {
                        candidatesInColumnAndBox.removeAll(cellFromBoxNotColumn.getCandidates());
                    }
                }

                for (Value candidate : candidatesInColumnAndBox) {
                    if (Collections.frequency(candidatesInColumnAndBox, candidate) > 1) {
                        for (Cell blockedCell : cellsInColumnNotBox) {
                            if (blockedCell.getValue().equals(Value.EMPTY) && blockedCell.getCandidates().contains(candidate)) {
                                blockedCell.getCandidates().remove(candidate);
                                stagnated = false;
                            }

                        }

                    }
                }


                setCellValues();
            }

        }
    }

    void pointingCellsInRows() {
        for (Cell cell : board.getCellList()) {
            if (cell.getValue().equals(Value.EMPTY)) {
                LinkedList<Cell> cellsInWholeRow = new LinkedList<Cell>(cell.getCellsInSameRow());
                LinkedList<Cell> cellsInSameBox = new LinkedList<Cell>(cell.getCellsInSameBox());

                LinkedList<Cell> cellsInRowAndBox = new LinkedList<Cell>(cellsInSameBox);
                cellsInRowAndBox.retainAll(cellsInWholeRow);

                LinkedList<Cell> cellsInBoxNotRow = new LinkedList<Cell>(cellsInSameBox);
                cellsInBoxNotRow.removeAll(cellsInRowAndBox);

                LinkedList<Cell> cellsInRowNotBox = new LinkedList<Cell>(cellsInWholeRow);
                cellsInRowNotBox.removeAll(cellsInRowAndBox);

                LinkedList<Value> candidatesInRowAndBox = new LinkedList<Value>(cell.getCandidates());


                for (Cell cellFromRowAndBox : cellsInRowAndBox) {
                    if (cellFromRowAndBox.getValue().equals(Value.EMPTY)) {
                        candidatesInRowAndBox.addAll(cellFromRowAndBox.getCandidates());
                    }
                }

                for (Cell cellFromBoxNotRow : cellsInBoxNotRow) {
                    if (cellFromBoxNotRow.getValue().equals(Value.EMPTY)) {
                        candidatesInRowAndBox.removeAll(cellFromBoxNotRow.getCandidates());
                    }
                }

                for (Value candidate : candidatesInRowAndBox) {
                    if (Collections.frequency(candidatesInRowAndBox, candidate) > 1) {
                        for (Cell blockedCell : cellsInRowNotBox) {
                            if (blockedCell.getValue().equals(Value.EMPTY) && blockedCell.getCandidates().contains(candidate)) {
                                blockedCell.getCandidates().remove(candidate);
                                stagnated = false;
                            }

                        }

                    }
                }


                setCellValues();
            }

        }
    }
}
