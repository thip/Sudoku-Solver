import strategies.*;
import structure.Board;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
public class Solver {
    LinkedList<Strategy> strategies;
    Strategy currentStrategy;

    Board board;

    public Solver (Board board)
    {
        strategies = new LinkedList<Strategy>();

        strategies.add(new NakedSingles(board));
        strategies.add(new HiddenSingles(board));
        strategies.add(new BlockingPairs(board));
        strategies.add(new PointingCells(board));
        strategies.add(new BoxLineReduction(board));

        this.board = board;
    }

    public boolean solveStep()
    {
        ListIterator<Strategy> strategiesIterator = strategies.listIterator();

        Board boardOld = new Board(board);

        currentStrategy = strategies.getFirst();

        while (board.equals(boardOld))
         {
             currentStrategy.run();

             if (!strategiesIterator.hasNext()){
                 return false;
             }

             currentStrategy = strategiesIterator.next();

         }

         return true;

    }
}
