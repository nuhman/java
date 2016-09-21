package app;

import static app.Log.warn;
import app.layout.Hamburger;
import app.layout.LayoutManager;
import app.layout.ShowLayoutManager;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import lpu.Attendance;
import lpu.Login;
import lpu.ResetPassword;
import lpu.TimeTable;

public class SignIn{
     public static Attendance attendance_report;
     public static TimeTable timetable_report;
     public static boolean loggedIn=false;
     
     private static String user="";
     private static String password="";
     public SignIn(){
        try{ 
        user=Log.userName;
        password=Log.passWord;
        new Login(user,password);
        attendance_report = new Attendance(user,password); 
        timetable_report=new TimeTable(user,password);
        attendance_report.start();
        timetable_report.start();
        
        timetable_report.join();
        attendance_report.join();
       
        if(attendance_report.rendered=true && timetable_report.rendered==true);
            createPane();
        }
        catch(Exception e){
            e.printStackTrace();
            warn();
        }
     }
     public static void resetPassword(){
         new ResetPassword(user,password);
     }
      public void createPane(){
            Pane layout=new LayoutManager().main_layout;
            layout.getChildren().addAll(new Hamburger().getPane(),
            new ShowLayoutManager().getPane());
            Scene scene=new Scene(layout,1080,580);
            
            Assemble.window.setScene(scene);
            Assemble.window.setResizable(false);
            scene.getStylesheets().add("app/sheet.css");
            Assemble. window.setScene(scene);
            Assemble.window.show();
      }
}
