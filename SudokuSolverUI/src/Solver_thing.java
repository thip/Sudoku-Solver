import structure.Board;
import structure.Cell;
import structure.Line;
import structure.Value;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
public class Solver_thing {

    private final LinkedList<Cell> visitedByPairsInRow = new LinkedList<Cell>();
    private final LinkedList<Cell> visitedByPairsInColumn = new LinkedList<Cell>();
    private Board board;

    public Solver_thing(Board board) {
        this.board = board;
    }

    public void solveStep() {

        nakedSingles();
        hiddenSingles();
        blockingPairs();
        pointingCells();
        boxColumnAndRowReduction();




        //}

        //System.out.println(board.toString());
    }

    private void pointingCells() {
        pointingCellsInColumns();
        pointingCellsInRows();
    }

    private void blockingPairs() {
        blockingPairsInRow();
        blockingPairsInColumn();
    }


    void boxColumnAndRowReduction() {
        boxLineReduction(board.getColumns());
        boxLineReduction(board.getRows());
    }

    void boxLineReduction(LinkedList<Line> lines) {


        for (Line line : lines) {
            for (Value candidate : line.getCandidates()) {
                LinkedList<Cell> possiblePartners = line.getCellsWithCandidate(candidate);
                if (possiblePartners.size() > 1 && possiblePartners.size() < 4) {
                    Cell cellOfInterest = (Cell) possiblePartners.toArray()[0];
                    possiblePartners.remove(cellOfInterest);
                    if (cellOfInterest.getCellsInSameBox().containsAll(possiblePartners)) {
                        LinkedList<Cell> markedCells = new LinkedList<Cell>(cellOfInterest.getCellsInSameBox());
                        markedCells.removeAll(possiblePartners);


                        for (Cell cell : markedCells) {
                            cell.getCandidates().remove(candidate);
                        }
                    }
                }
            }
        }

        setCellValues();
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

    private void setCellValues() {
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

    void nakedSingles() {       //something funky is happening with this with the dan.sud file
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

    void hiddenSingles() {
        hiddenBoxSingle();
        hiddenRowSingle();
        hiddenColumnSingle();
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
                            if (blockedCell.getValue().equals(Value.EMPTY)) {
                                blockedCell.getCandidates().remove(candidate);
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
                            if (blockedCell.getValue().equals(Value.EMPTY)) {
                                blockedCell.getCandidates().remove(candidate);
                            }

                        }

                    }
                }


                setCellValues();
            }

        }
    }


}

