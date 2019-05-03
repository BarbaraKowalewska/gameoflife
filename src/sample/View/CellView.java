package sample.View;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Model.Cell;

public class CellView extends Rectangle {

    private Color[] colors = {Color.ALICEBLUE, Color.BLUE, Color.BISQUE, Color.AQUA};
    private Cell cell;


    public CellView(Cell cell) {

        this.setFill(colors[3]);
        this.setWidth(12);
        this.setHeight(12);

        this.cell = cell;


        this.setOnMouseClicked(e -> {

            if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (this.cell.isAlive()) {
                    turnInactive();

                } else {
                    turnActive();
                }
            }
        });

    }

    public void turnInactive() {
        this.cell.setAlive(false);
        this.setFill(colors[3]);

//        System.out.println("state " + this.cell.isAlive());


    }

    ;

    public void turnActive() {
        this.cell.setAlive(true);
        this.setFill(colors[1]);

//        System.out.println("state " + this.cell.isAlive());


    }

    ;


}
