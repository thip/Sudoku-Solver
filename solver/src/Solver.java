import strategies.*;
import structure.Board;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Provides access to solving strategies used to find a solution to the board
 * @author David Capper <dmc2@aber.ac.uk>
 */
class Solver {

    /**
     * List containing Strategy objects which will be used to solve the board
     * @see Strategy
     */
    private final LinkedList<Strategy> strategies;

    /**
     * A reference to the Strategy currently being used
     * @see Strategy
     */
    private Strategy currentStrategy;


    /**
     * A reference to the board being solved
     * @see Board
     */
    private final Board board;


    LinkedList<String> activityLog = new LinkedList<String>();

    /**
     * Constructor for the Solver class. Adds Strategy objects to the strategies list and sets the board being solved.
     * @param board  the board to be solved.
     * @see Board
     * @see Strategy
     */
    public Solver (Board board)
    {
        //Initialise the strategies list
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
     * Solves the board one step at a time (one step being one successful pass of a single strategy.
     * @return true if any strategies have succeeded in progressing the state of the board, false if it has stagnated.
     */
    public boolean solveStep()
    {
        ListIterator<Strategy> strategiesIterator = strategies.listIterator();




        //keep looping through strategies until we find one that works
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

            //activityLog.addAll(new LinkedList<String>(currentStrategy.getActivityLog()));



        } while (currentStrategy.isStagnated());

        //return true if we have successfully applied a strategy to the board.
        return true;

    }

    public LinkedList<String> getLastLog() {
        return activityLog;
    }
}
