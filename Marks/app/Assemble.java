package app;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
public class Assemble extends Application {
    public static Stage window;
    @Override
    public void start(Stage primaryStage) {
         window=primaryStage;
         Scene scene=new Scene(new ShowLayoutManager().getPane(),1080,580);
         scene.getStylesheets().add("app/sheet.css");
         window.setResizable(false);
         window.setScene(scene);
         window.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
