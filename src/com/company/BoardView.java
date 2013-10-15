package com.company;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
public class BoardView extends Application {

    private volatile Board board;
    private Canvas canvas;

    private static final int boardSize = 600;
    private static final int border = 5;

    public static void main(String[] args) {


        launch(args);
    }



    @Override
    public void start(Stage stage) throws Exception {

        File file = new File("problems/web.sud");
        String content = new Scanner(file).useDelimiter("\\Z").next()

        try {
            content = new Scanner(file).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        board = new Board(content);

        stage.setTitle("Sudoku Solver");
        Group root = new Group();
        canvas = new Canvas(boardSize + 2*border, boardSize + 2*border);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();






        Solver solver = new Solver();
        solver.Solve(board);

        draw(gc);
    }

    void draw(GraphicsContext gc)
    {



        int boxWidth = (int)Math.abs(boardSize /3);
        int cellWidth = (int)Math.abs(boardSize /9);
        int boxLineThickness = 4;

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.GREY);
        gc.setLineWidth(boxLineThickness);

        for (int ii = 0; ii < 4; ii++) {
            gc.strokeLine(border, border + ii*boxWidth, boardSize + border, border+ ii*boxWidth);
            gc.strokeLine(border + ii*boxWidth, border, border+ ii*boxWidth, boardSize + border );
        }

        gc.setLineWidth(2);
        for (int ii = 1; ii < 9; ii++) {
            gc.strokeLine(border, border + boxLineThickness + ii*cellWidth, boardSize + border , border + boxLineThickness +  ii*cellWidth);
            gc.strokeLine(border + boxLineThickness +  ii*cellWidth, border, border + boxLineThickness +   ii*cellWidth, boardSize + border );
        }

        gc.setStroke(Color.BLACK);


        for ( Cell cell : this.board.getCellList() )
        {
            if (cell.getValue() != Value.EMPTY)
            {
                gc.setStroke(Color.BLACK);
                gc.setFont(new Font(18));
                gc.strokeText(Integer.toString(cell.getValue().toInt()) , border + boxLineThickness + (0.5*cellWidth) + cell.getX() * cellWidth , border + boxLineThickness + (0.5* cellWidth) + cell.getY() * cellWidth);
            }
            if (cell.getValue() == Value.EMPTY)
            {

                gc.setFont(new Font(14));
                for (int ii = 0; ii < 3; ii++) {
                    for (int jj = 0; jj < 3; jj++) {
                        try {if (cell.getCandidates().toArray()[jj + ii*3] != null)
                        {
                            gc.setStroke(Color.BLUE);
                            gc.strokeText(Integer.toString(((Value)cell.getCandidates().toArray()[jj + ii*3]).toInt()) , border + boxLineThickness  + cell.getX() * cellWidth + jj * cellWidth/4 + boxLineThickness, border + boxLineThickness  + cell.getY() * cellWidth + ii * cellWidth/4 + 4*boxLineThickness);
                        } } catch (Exception e) {}
                    }
                }

            }
        }
    }
}
