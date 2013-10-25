package strategies;

import structure.Board;
import structure.Cell;
import structure.Line;
import structure.Value;

import java.util.LinkedList;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
public class BoxLineReduction extends Strategy {

    public BoxLineReduction(Board board) {
        super(board);
    }

    @Override
    public void run() {
        stagnated = true;
        //clearActivityLog();
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

                            if (cell.getCandidates().contains(candidate))
                            {
                                cell.getCandidates().remove(candidate);
                                stagnated = false;
                            }

                        }
                    }
                }
            }
        }

        setCellValues();
    }
}
