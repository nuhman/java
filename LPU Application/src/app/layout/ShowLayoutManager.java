package app.layout;

import app.pane.*;
import static app.pane.MarksCalculatorPane.marksCalculatorPane;
import javafx.scene.layout.Pane;

public class ShowLayoutManager {
    public static Pane show_layout;
    public ShowLayoutManager(){
        show_layout=new Pane();  
        createPane();
    }
    public void createPane(){
       new TimeTablePane();
       new Attendance();
       new MarksCalculatorPane();
       TimeTablePane.time_table_pane.toFront();
     
    }
    public Pane getPane(){
        return show_layout;
    }
}
