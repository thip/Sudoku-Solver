import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import structure.Board;
import structure.Cell;
import structure.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


/**
 *
 * Controller for the UI
 *
 * created following these tutorials:
 *
 *   http://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
 *   http://edu.makery.ch/blog/2012/11/20/javafx-tutorial-addressapp-3/
 *
 * @author David Capper <dmc2@aber.ac.uk>
 */
public class UIController {

    @FXML
    private MenuItem fileOpen;

    @FXML
    private MenuItem fileQuit;

    @FXML
    private MenuItem helpAbout;

    @FXML
    private Button buttonSolve;

    @FXML
    private Button buttonSolveStep;

    @FXML
    private Pane boardPain;

    //@FXML
    //private ListView<String> logEntriesList;

   // ObservableList<String> items = FXCollections.observableArrayList();


    private FileChooser fileChooser;

    private  Solver solver;

    private  Board board;

    private static final int boardSize = 390;
    private static final int border = 5;
    private Canvas canvas;
    private GraphicsContext gc;


    // Reference to the main application
    private Main mainApp;

    private Stage dialogStage;

    @FXML
    private void handleOpenFile()
    {



        File file = fileChooser.showOpenDialog(dialogStage);



        if (file != null)
        {
            loadBoardFrom(file);
        }
    }

    private void loadBoardFrom(File file) {
        String content = null;

        try {
            content = new Scanner(file).useDelimiter("\\Z").next();
            board = new Board(content);
            solver = new Solver(board);
            //solver.solveStep();
        } catch (FileNotFoundException e) {
            alert("Could not find file: " + file.getAbsolutePath());
        }

        drawBoard(gc, false);

    }

    @FXML
    private void handleQuit()
    {
        Platform.exit();
    }

    @FXML
    private void  handleSolve()
    {

        if (board != null){
        buttonSolveStep.setDisable(true);
        buttonSolve.setDisable(true);

        Task task = new Task<Void>() {
            @Override public Void call() {



                int n = 0;

                while (!board.isSolved()){
                    if (!solver.solveStep()) break;
                    n++ ;
                }

                if (!board.isSolved()) {

                    alert("Board stagnated in " + n + " steps.");
                }

                drawBoard(gc);

                buttonSolveStep.setDisable(false);
                buttonSolve.setDisable(false);


                return null;
            }

        };



        Platform.runLater(task);
        }

    }

    @FXML
    private void handleSolveStep()
    {
        if (board != null)
        {
            buttonSolveStep.setDisable(true);
            buttonSolve.setDisable(true);

            Task task = new Task<Void>() {
                @Override public Void call() {
                    solver.solveStep();
                    drawBoard(gc);

                    //items.addAll(new ArrayList<String>(solver.getLastLog()));
                    //logEntriesList.setItems(items);

                    buttonSolveStep.setDisable(false);
                    buttonSolve.setDisable(false);

                    return null;
                }
            };



            Platform.runLater(task);
        }

    }


    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public UIController() {
    }



    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Sudoku File");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Sudoku Files", "*.sud")
        );

        canvas = new Canvas(boardSize + 2 * border, boardSize + 2 * border);
        gc = canvas.getGraphicsContext2D();

        boardPain.getChildren().add(canvas);

        buttonSolve.arm();

    }

    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

    }


    /**
     * @author Sergey Grinev - http://stackoverflow.com/questions/8309981/how-to-create-and-show-common-dialog-error-warning-confirmation-in-javafx-2
     * @param message
     */
    private void alert(String message)
    {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setScene(new Scene(VBoxBuilder.create().
                children(new Text(message), new Button("Ok.")).
                alignment(Pos.CENTER).padding(new Insets(5)).build()));
        dialogStage.show();
    }

    void drawBoard(GraphicsContext gc)
    {
        drawBoard(gc, true);
    }

    void drawBoard(GraphicsContext gc, boolean drawCandidates) {


        int boxWidth = Math.abs(boardSize / 3);
        int cellWidth = Math.abs(boardSize / 9);
        int boxLineThickness = 4;

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.GREY);
        gc.setLineWidth(boxLineThickness);

        for (int ii = 0; ii < 4; ii++) {
            gc.strokeLine(border, border + ii * boxWidth, boardSize + border, border + ii * boxWidth);
            gc.strokeLine(border + ii * boxWidth, border, border + ii * boxWidth, boardSize + border);
        }

        gc.setLineWidth(2);
        for (int ii = 1; ii < 9; ii++) {
            gc.strokeLine(border, border + boxLineThickness + ii * cellWidth, boardSize + border, border + boxLineThickness + ii * cellWidth);
            gc.strokeLine(border + boxLineThickness + ii * cellWidth, border, border + boxLineThickness + ii * cellWidth, boardSize + border);
        }

        gc.setStroke(Color.BLACK);


        for (Cell cell : this.board.getCellList()) {
            if (cell.getValue() != Value.EMPTY) {
                gc.setFill(Color.BLACK);
                gc.setFont(new Font("Arial2 Bold", 18));
                gc.fillText(Integer.toString(cell.getValue().toInt()), border + boxLineThickness + (0.5 * cellWidth) + cell.getX() * cellWidth - 4.5, border + boxLineThickness + (0.5 * cellWidth) + cell.getY() * cellWidth + 4.5);
            }
            if (cell.getValue() == Value.EMPTY && drawCandidates) {

                gc.setFont(new Font(14));
                for (int ii = 0; ii < 3; ii++) {
                    for (int jj = 0; jj < 3; jj++) {
                        try {
                            if (cell.getCandidates().toArray()[jj + ii * 3] != null) {
                                gc.setFill(Color.BLUE);

                                gc.setFont(new Font("Arial2 Italic", 10));
                                gc.fillText(Integer.toString(((Value) cell.getCandidates().toArray()[jj + ii * 3]).toInt()), border + boxLineThickness + cell.getX() * cellWidth + jj * cellWidth / 4 + boxLineThickness, border + boxLineThickness + cell.getY() * cellWidth + ii * cellWidth / 4 + 4 * boxLineThickness);
                            }
                        } catch (Exception e) {
                        }
                    }
                }

            }
        }
    }
}

