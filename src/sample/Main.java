package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.Controller.MainController;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        MainController controller = new MainController();
        controller.initializeGame(primaryStage);
        controller.keys();
        controller.update();


    }
}


