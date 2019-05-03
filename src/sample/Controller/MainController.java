package sample.Controller;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Model.Cell;
import sample.View.CellView;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;



public class MainController {


    // instance fields of Main Controller
    private ScheduledExecutorService executor =
            Executors.newScheduledThreadPool(5);

    private ScheduledFuture future;


    private boolean gamePaused = true;


    public Cell[][] getCellArray() {
        return cellArray;
    }

    private Cell[][] cellArray;
    private CellView[][] cellViewArray;

    final private int ROWNUM = 40; /// This is on the heap because MainController is on the heap
    final private int COLNUM = 40;

    private int delay = 1000;
    private int speed = 1000;

    Runnable updateGame;



    ArrayList<Integer> delayList = new ArrayList<>(Arrays.asList( 1000, 70, 200, 500 ));
    ArrayList<Integer> speedList = new ArrayList<>(Arrays.asList(1000,  70, 200, 500 ));


    Scene scene;


    public void initializeGame(Stage primaryStage) {

        // My learning notes
        // when function runs local variables etc are on the stack and when function finish they are removed from stack
        // and then when the reference is removed, garbage collector will delete it from heap

        GridPane grid = new GridPane();
        primaryStage.setTitle("Game of Life");

        int gridHeight = 10; // on stack
        int gridWidth = 10;

        cellArray = new Cell[COLNUM][ROWNUM]; // reference to the place on the heap
        cellViewArray = new CellView[COLNUM][ROWNUM];

        grid.setVgap(3);
        grid.setHgap(3);

        for (int row = 0; row < ROWNUM; row++) {
            for (int col = 0; col < COLNUM; col++) {
                Cell cell = new Cell();
                cellArray[row][col] = cell;

                CellView cellview = new CellView(cell);
                cellViewArray[row][col]= cellview;

                GridPane.setRowIndex(cellview, row);
                GridPane.setColumnIndex(cellview, col);
                grid.getChildren().addAll(cellview);
            }
        }

        scene = new Scene(grid, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void playGame() {

        Boolean[][] states = new Boolean[COLNUM][ROWNUM];

        for (int row = 1; row < ROWNUM - 1; row++) {
            for (int col = 1; col < COLNUM - 1; col++) {

                int aliveNeighbours = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (cellArray[row + i][col + j].isAlive()) {
                            aliveNeighbours += 1;
                        }
                    }
                }
                if (cellArray[row][col].isAlive()) {
                    aliveNeighbours -= 1;
                };
                states[row][col] = cellShouldLiveInNextGeneration(cellArray[row][col].isAlive(), aliveNeighbours);
            }
        }


        for (int row = 1; row < ROWNUM - 1; row++) {
            for (int col = 1; col < COLNUM - 1; col++) {
                if (states[row][col] == true) {
                    cellViewArray[row][col].setFill(Color.BLUE);
                    cellArray[row][col].setAlive(true);
                } else {
                    cellViewArray[row][col].setFill(Color.AQUA);
                    cellArray[row][col].setAlive(false);

                }
            }
        }
        ;
    }

    public static boolean cellShouldLiveInNextGeneration(boolean isAlive, int aliveNeighbours) {
        // Cell is lonely and dies
        if (isAlive && (aliveNeighbours < 2)) {
            return false;
        }
        // Cell dies due to over population
        else if (isAlive && (aliveNeighbours > 3)) {
            return false;
        }
        // A new cell is born
        else if ((!isAlive) && (aliveNeighbours == 3)) {
            return true;
        }
        else {
            return isAlive;
        }

    }

    public void update() {

        updateGame = new Runnable() {
            @Override
            public void run() {
                if (gamePaused) {
                    return;
                }
                playGame();
            }
        };

        future = executor.scheduleAtFixedRate(updateGame, delay, speed, TimeUnit.MILLISECONDS);
    }

    public void keys() {
        scene.setOnKeyTyped(event -> {
            if (event.getCharacter().equals("b")) {
                // stop and start the game
                gamePaused = !gamePaused;
            }
            if (event.getCharacter().equals("s")){
               //speed change

                Collections.rotate(delayList,1);
                Collections.rotate(speedList, 1);
                speed = speedList.get(0);
                delay = delayList.get(0);
                future.cancel(false);
                future = executor.scheduleAtFixedRate(updateGame, delay, speed, TimeUnit.MILLISECONDS);
            }
        });
    }

}


