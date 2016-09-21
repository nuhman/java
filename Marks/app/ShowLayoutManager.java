package app;

import javafx.scene.layout.Pane;

public class ShowLayoutManager {
    public static Pane show_layout;
    public ShowLayoutManager(){
        show_layout=new Pane();  
        createPane();
    }
    public void createPane(){
       new MarksCalculatorPane();
     
    }
      public Pane getPane(){
        return show_layout;
    }
}
