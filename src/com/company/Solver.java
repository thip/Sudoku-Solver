package com.company;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
class Solver {

    private Board board;
    private int valuesSet = 0;
    private final LinkedList<Cell> visitedByPairsInRow = new LinkedList<Cell>();
    private final LinkedList<Cell> visitedByPairsInColumn = new LinkedList<Cell>();

    public void Solve(Board board) {
        this.board = board;

        System.out.println(board.toString());

        for (int i = 0; i < 100; i++) {

            //XWingSolver xWing = new XWingSolver();  //order does something funny here
            //xWing.solve(board);

            hiddenSingles();
            nakedSingles();
            blockingPairsInRow();
            blockingPairsInColumn();
            setCellValues();
            pointingCellsInColumns();
            pointingCellsInRows();



        }

        System.out.println(board.toString());
        System.out.println(valuesSet);
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
                            System.out.println("pair in row");



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
                valuesSet++;  //debug line
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
                            System.out.println("pair in column");
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

    void nakedSingles() {
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

                cell.getCandidates().removeAll(takenValues);

                //System.out.println(cell.getX()+ "," + cell.getY() + ":" +cell.getCandidates());

                setCellValues();
            }
        }
    }

    void hiddenBoxSingle()
    {
        for (Cell cell : board.getCellList()) {
            if (cell.getValue() == Value.EMPTY) {





                // System.out.println(cell.getX()+ "," + cell.getY() + ":" +cell.getCandidates());     // debug line

                HashSet<Value> mentionedValues = new HashSet<Value>();

                for (Cell conflictingCell : cell.getCellsInSameBox()) {
                    if (conflictingCell.getValue().equals(Value.EMPTY) )
                        mentionedValues.addAll(conflictingCell.getCandidates());
                }



                if (!mentionedValues.containsAll(cell.getCandidates())){
                    cell.getCandidates().removeAll(mentionedValues);
                }

                //System.out.println(mentionedValues);   //debug line
                //System.out.println(cell.getX()+ "," + cell.getY() + ":" +unmentionedValues);   //debug line


                setCellValues();
            }
        }
    }

    void hiddenRowSingle()
    {
        for (Cell cell : board.getCellList()) {
            if (cell.getValue() == Value.EMPTY) {





                // System.out.println(cell.getX()+ "," + cell.getY() + ":" +cell.getCandidates());     // debug line

                HashSet<Value> mentionedValues = new HashSet<Value>();

                for (Cell conflictingCell : cell.getCellsInSameRow()) {
                    if (conflictingCell.getValue().equals(Value.EMPTY) )
                        mentionedValues.addAll(conflictingCell.getCandidates());
                }



                if (!mentionedValues.containsAll(cell.getCandidates())){
                    cell.getCandidates().removeAll(mentionedValues);
                }

                //System.out.println(mentionedValues);   //debug line
                //System.out.println(cell.getX()+ "," + cell.getY() + ":" +unmentionedValues);   //debug line


                setCellValues();
            }
        }
    }

    void hiddenColumnSingle()
    {
        for (Cell cell : board.getCellList()) {
            if (cell.getValue() == Value.EMPTY) {





                // System.out.println(cell.getX()+ "," + cell.getY() + ":" +cell.getCandidates());     // debug line

                HashSet<Value> mentionedValues = new HashSet<Value>();

                for (Cell conflictingCell : cell.getCellsInSameColumn()) {
                    if (conflictingCell.getValue().equals(Value.EMPTY) )
                        mentionedValues.addAll(conflictingCell.getCandidates());
                }



                if (!mentionedValues.containsAll(cell.getCandidates())){
                    cell.getCandidates().removeAll(mentionedValues);
                }

                //System.out.println(mentionedValues);   //debug line
                //System.out.println(cell.getX()+ "," + cell.getY() + ":" +unmentionedValues);   //debug line


                setCellValues();
            }
        }
    }

    void hiddenSingles() {
        hiddenBoxSingle();
        hiddenRowSingle();
        hiddenColumnSingle();
    }

    void pointingCellsInColumns()
    {
        for (Cell cell : board.getCellList())
        {
            if (cell.getValue().equals(Value.EMPTY)){
            LinkedList<Cell> cellsInWholeColumn = new LinkedList<Cell>(cell.getCellsInSameColumn());
            LinkedList<Cell> cellsInSameBox = new LinkedList<Cell>(cell.getCellsInSameBox());

            LinkedList<Cell> cellsInColumnAndBox = new LinkedList<Cell>(cellsInSameBox);
            cellsInColumnAndBox.retainAll(cellsInWholeColumn);

            LinkedList<Cell> cellsInBoxNotColumn = new LinkedList<Cell>(cellsInSameBox);
            cellsInBoxNotColumn.removeAll(cellsInColumnAndBox);

            LinkedList<Cell> cellsInColumnNotBox = new LinkedList<Cell>(cellsInWholeColumn);
            cellsInColumnNotBox.removeAll(cellsInColumnAndBox);

            LinkedList<Value> candidatesInColumnAndBox = new LinkedList<Value>(cell.getCandidates());


            for (Cell cellFromColumnAndBox : cellsInColumnAndBox)
            {
                if (cellFromColumnAndBox.getValue().equals(Value.EMPTY)){
                candidatesInColumnAndBox.addAll(cellFromColumnAndBox.getCandidates());
                }
            }

            for (Cell cellFromBoxNotColumn : cellsInBoxNotColumn )
            {
                if (cellFromBoxNotColumn.getValue().equals(Value.EMPTY)){
                candidatesInColumnAndBox.removeAll(cellFromBoxNotColumn.getCandidates());   }
            }

            for (Value candidate : candidatesInColumnAndBox)
            {
                if (Collections.frequency(candidatesInColumnAndBox, candidate) > 1)
                {
                      for (Cell blockedCell : cellsInColumnNotBox)
                      {
                          if (blockedCell.getValue().equals(Value.EMPTY))  {
                          blockedCell.getCandidates().remove(candidate);
                          }

                      }

                }
            }


            setCellValues();   }

        }
    }

    void pointingCellsInRows()
    {
        for (Cell cell : board.getCellList())
        {
            if (cell.getValue().equals(Value.EMPTY)){
            LinkedList<Cell> cellsInWholeRow = new LinkedList<Cell>(cell.getCellsInSameRow());
            LinkedList<Cell> cellsInSameBox = new LinkedList<Cell>(cell.getCellsInSameBox());

            LinkedList<Cell> cellsInRowAndBox = new LinkedList<Cell>(cellsInSameBox);
            cellsInRowAndBox.retainAll(cellsInWholeRow);

            LinkedList<Cell> cellsInBoxNotRow = new LinkedList<Cell>(cellsInSameBox);
            cellsInBoxNotRow.removeAll(cellsInRowAndBox);

            LinkedList<Cell> cellsInRowNotBox = new LinkedList<Cell>(cellsInWholeRow);
            cellsInRowNotBox.removeAll(cellsInRowAndBox);

            LinkedList<Value> candidatesInRowAndBox = new LinkedList<Value>(cell.getCandidates());


            for (Cell cellFromRowAndBox : cellsInRowAndBox)
            {
                if (cellFromRowAndBox.getValue().equals(Value.EMPTY)){
                candidatesInRowAndBox.addAll(cellFromRowAndBox.getCandidates());
                }
            }

            for (Cell cellFromBoxNotRow : cellsInBoxNotRow )
            {
                if (cellFromBoxNotRow.getValue().equals(Value.EMPTY)){
                candidatesInRowAndBox.removeAll(cellFromBoxNotRow.getCandidates());   }
            }

            for (Value candidate : candidatesInRowAndBox)
            {
                if (Collections.frequency(candidatesInRowAndBox, candidate) > 1)
                {
                      for (Cell blockedCell : cellsInRowNotBox)
                      {
                          if (blockedCell.getValue().equals(Value.EMPTY))  {
                          blockedCell.getCandidates().remove(candidate);
                          }

                      }

                }
            }


            setCellValues();   }

        }
    }


}

/*class XWingSolver
{
    HashSet<Cell> cellsAffected = new HashSet<Cell>();
    HashSet<Cell> cellsOfInterest = new HashSet<Cell>();
    Cell lastFound = null;  //think about stacks for this?
    Cell lastlastFound = null;  //think about stacks for this?

    int depth = 0;


    public boolean recurse(Cell cell, boolean column)
    {
        LinkedList<Cell> potentialMatches;
        LinkedList<Cell> justSeen = new LinkedList<Cell>();

        //depth++;
        //System.out.println(depth);

        if (column){
             potentialMatches = cell.getCellsInSameColumn();
        } else {
             potentialMatches = cell.getCellsInSameRow();
        }

        for (Cell potentialMatch : potentialMatches)
        {
            if (cellsOfInterest.contains(potentialMatch) && !justSeen.contains(potentialMatch))
            {
                return true;
            } else {

                if ( potentialMatch.getCandidates().equals(cell.getCandidates()) )
                {
                   // System.out.println("sniff");
                    cellsOfInterest.add(potentialMatch);
                    justSeen.add(potentialMatch);


                    cellsAffected.addAll(potentialMatch.getCellsInSameColumn());
                    cellsAffected.addAll(potentialMatch.getCellsInSameRow());

                    recurse(potentialMatch, !column);
                }

            }

        }

        //depth--;
        //System.out.println(depth);
        return false;

    }

    public boolean recurse(LinkedList<Cell> cells)
    {
        for (Cell cell : cells)
        {

            if (cell.getCandidates().size() == 2)
            {
                if (recurse(cell, true) || recurse(cell, false)){

                    System.out.println("something exciting");

                    cellsAffected.removeAll(cellsOfInterest);

                    Cell anInterestingCell = (Cell)cellsOfInterest.toArray()[0];
                    HashSet<Value> thePair = anInterestingCell.getCandidates();

                    for (Cell affectedCell : cellsAffected)
                    {
                        affectedCell.getCandidates().removeAll(thePair);
                        HashSet<Value> lol = affectedCell.getCandidates(); //debug line
                    }

                    return true;
                }

            }
        }
        return false;
    }

    public void solve(Board board)
    {
        if ( recurse(board.getCellList())){

            for (Cell cell : board.getCellList())
            {
                if ((cell.getValue() == Value.EMPTY) && (cell.getCandidates().size() == 1))
                {
                    cell.setValue((Value)cell.getCandidates().toArray()[0]);
                    System.out.println("BOOOOOOOOOM");
                }
            }
        }
    }

}  */
