package strategies;

import structure.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 *
 * @author David Capper <dmc2@aber.ac.uk>
 */
public class HiddenPairs2 extends Strategy {

    HashSet<Cell> solvedCells = new HashSet<Cell>();

    public HiddenPairs2(Board board) {
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

        stagnated = true;

        for (Cell cell : structure.getCellList()) {

            if (cell.getValue().equals(Value.EMPTY) && !solvedCells.contains(cell)) {

                LinkedList<Cell> otherCells = new LinkedList<Cell>(structure.getCellList());
                otherCells.remove(cell);

                for (Cell otherCell : otherCells){

                    if (otherCell.getValue().equals(Value.EMPTY) && !solvedCells.contains(otherCell) ){

                        HashSet<Value> sharedCandidates = new HashSet<Value>(cell.getCandidates());
                        sharedCandidates.retainAll(otherCell.getCandidates());

                        if (sharedCandidates.size() >= 2){
                            HashSet<HashSet<Value>> possibleCombinations = new HashSet<HashSet<Value>>();
                            for (Value candidate1 : sharedCandidates)
                            {
                                for (Value candidate2 : sharedCandidates)
                                {
                                    if (candidate1 != candidate2)
                                    {
                                        possibleCombinations.add(new HashSet<Value>(Arrays.asList(candidate1, candidate2)));
                                    }

                                    for (HashSet<Value> possibleCombination : possibleCombinations)
                                    {
                                        LinkedList<Cell> conflictingCells = new LinkedList<Cell>(otherCells);
                                        conflictingCells.remove(otherCell);

                                        boolean match = true;
                                        for (Cell conflictingCell : conflictingCells)
                                        {
                                             if (conflictingCell.getValue().equals(Value.EMPTY) && !Collections.disjoint(conflictingCell.getCandidates(), possibleCombination)){
                                                 match = false;
                                                 break;
                                             }
                                        }

                                        if (match == true)
                                        {
                                            cell.getCandidates().retainAll(possibleCombination);
                                            otherCell.getCandidates().retainAll(possibleCombination);

                                            solvedCells.add(cell);
                                            solvedCells.add(otherCell);

                                            System.out.println("setting " + cell.getX() + "," + cell.getY() + " and " + otherCell.getX() + "," + otherCell.getY() + " to " + possibleCombination);
                                            stagnated = false;
                                        }
                                    }
                                }
                            }
                        }




                    }
                }

            }
        }

        setCellValues();

    }
}


