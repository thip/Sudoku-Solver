import strategies.*;
import structure.Board;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Provides access to solving strategies used to find a solution to the board
 * @author David Capper <dmc2@aber.ac.uk>
 */
public class Solver {

    /**
     * List containing Strategy objects which will be used to solve the board
     * @see Strategy
     */
    LinkedList<Strategy> strategies;

    /**
     * A reference to the Strategy currently being used
     * @see Strategy
     */
    Strategy currentStrategy;


    /**
     * A reference to the board being solved
     * @see Board
     */
    Board board;

    /**
     * Constructor for the Solver class. Adds Strategy objects to the strategies list and sets the board being solved.
     * @param board  the board to be solved.
     * @see Board
     * @see Strategy
     */
    public Solver (Board board)
    {
        //Initialies the strategies list
        strategies = new LinkedList<Strategy>();

        //Add all the Strategy objects we want to use to the list
        strategies.add(new NakedSingles(board));
        strategies.add(new HiddenSingles(board));
        strategies.add(new HiddenPairs2(board));
        strategies.add(new BlockingPairs(board));
        strategies.add(new PointingCells(board));
        strategies.add(new BoxLineReduction(board));

        this.board = board;
    }


    /**
     * Solves the board one step at a time (one step being one succesful pass of a single stragegy.
     * @return true if any strategies have succeeded in progressing the state of the board, false if it has stagnated.
     */
    public boolean solveStep()
    {
        ListIterator<Strategy> strategiesIterator = strategies.listIterator();

        do {

            if (!strategiesIterator.hasNext()){

                //Return false if we have run out of strategies to use
                return false;

            } else {

                //otherwise move onto the next strategy
                currentStrategy = strategiesIterator.next();

            }

             //run the strategy
             currentStrategy.run();

       } while (currentStrategy.isStagnated()); //keep looping through strategies until we find one that works

        //return true if we have succesfully applied a strategy to the board.
        return true;

    }
}
