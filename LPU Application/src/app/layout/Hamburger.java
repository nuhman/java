package app.layout;

import app.SignIn;
import app.pane.TimeTablePane;
import app.pane.Attendance;
import app.pane.MarksCalculatorPane;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import static javafx.scene.paint.Color.rgb;
import javafx.util.Duration;


public class Hamburger{
    public static TranslateTransition menuShow;
    public  static TranslateTransition menuHide;
    private static boolean hamburger_on=false;
    public static Pane hamburger;
    
    public Hamburger(){
    hamburger=new Pane();   
    hamburger.setId("hamburger");
    hamburger.setMinSize(200,600);
    hamburger.setLayoutX(-210);
    hamburger.toFront();
    menuShow = new TranslateTransition(Duration.millis(1000),hamburger);
    menuShow.setFromX(-210);menuShow.setToX(210);
    menuHide = new TranslateTransition(Duration.millis(500),hamburger);
    menuHide.setFromX(210);menuHide.setToX(-210);
      //BUTTON IN MENU START
        Button time_table_button=new Button("Time Table View");
        time_table_button.setMinWidth(200);time_table_button.setLayoutY(40);
        time_table_button.setId("hamburger_button");
        time_table_button.setOnAction(e->{
            TimeTablePane.show();
            hide_hamburger();
        });
        Button attendance_button=new Button("Attendance View");
        attendance_button.setMinWidth(200);
        attendance_button.setLayoutY(80);
        attendance_button.setId("hamburger_button");
        attendance_button.setOnAction(e->{
            Attendance.show();
            hide_hamburger();
        });
        
        Button marks_calculator_button=new Button("Marks Calculator");
        marks_calculator_button.setMinWidth(200);marks_calculator_button.setLayoutY(120);
        marks_calculator_button.setId("hamburger_button");
        marks_calculator_button.setOnAction(e->{
            MarksCalculatorPane.show();
            hide_hamburger();
        });
        
        Button reset_password_button=new Button("Reset Password");
        reset_password_button.setMinWidth(200);reset_password_button.setLayoutY(160);
        reset_password_button.setId("hamburger_button");
        reset_password_button.setOnAction(e->{
            SignIn.resetPassword();
            hide_hamburger();
        });
        
        Button settings_button=new Button("Settings");
        settings_button.setMinWidth(200);settings_button.setLayoutY(450);
        settings_button.setId("hamburger_button");
        settings_button.setOnAction(e->{
            
            hide_hamburger();
        });
        
        Button logout_button=new Button("Logout");
        logout_button.setMinWidth(200);logout_button.setLayoutY(490);
        logout_button.setId("hamburger_button");
        logout_button.setOnAction(e->{
            
            hide_hamburger();
        });
        //BUTTON IN MENU END
    DropShadow ds=new DropShadow();ds.setOffsetX(5.0);ds.setColor(rgb(100,100,100));
    hamburger.setEffect(ds);
    hamburger.getChildren().addAll(time_table_button,attendance_button,marks_calculator_button,settings_button,
           logout_button,reset_password_button);
     //CONFIGURING HAMBURGER 
    
    //END CONFIGURING HAMBURGER
  }
    public  static void show_hamburger(){  
            hamburger_on=true;  
            menuShow.play();  
            hamburger.toFront();
        }
    public static void hide_hamburger(){
            menuHide.play();
            hamburger_on=false;
    }
    public Pane getPane(){
        return hamburger;
    }
    
}
