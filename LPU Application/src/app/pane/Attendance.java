package app.pane;

import static app.layout.Hamburger.hide_hamburger;
import static app.layout.Hamburger.show_hamburger;
import static app.layout.ShowLayoutManager.show_layout;
import static app.SignIn.attendance_report;
import static app.SignIn.timetable_report;
import javafx.animation.TranslateTransition;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import static javafx.scene.paint.Color.rgb;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Attendance {
     public static Pane attendance_pane;
     private int total_week=14;
     private LineChart<Number,Number> lineChart;
     private Pane graph_pane;
     public Attendance(){
        attendance_pane=new Pane();
        attendance_pane.setId("attendance_pane");
        attendance_pane.setMinSize(1100,600);
        Button hamburger_button=new Button();
        hamburger_button.setId("ham_button");
        hamburger_button.setLayoutX(10);hamburger_button.setLayoutY(18);
        hamburger_button.setMinSize(30,18);
        hamburger_button.setOnAction(e->show_hamburger());
        //HEADERS
        Label total_attendance=new Label();
        PaneComponents(total_attendance,"attendance_pane_headers",900,20);
        Label total_classes=new Label();
        PaneComponents(total_classes,"attendance_pane_headers",500,20);
        Label total_attended=new Label();
        PaneComponents(total_attended,"attendance_pane_headers",680,20);

        Pane interactive=new Pane();
        interactiveAttendance(interactive);
        interactive.setMinSize(200,200);
        interactive.setLayoutX(70);interactive.setLayoutY(110);

        if(attendance_report.rendered==true){
            total_attendance.setText("Attendance:"+attendance_report.total_attendance+"%");
            total_classes.setText("Total Classes:"+attendance_report.total_class);
            total_attended.setText("Total Attended:"+(attendance_report.total_class_attended+" + "+attendance_report.total_duty_leaves));
        }
        Line line=new Line(0,70,1100,72);
        Line b1=new Line(10,20,40,20);
        Line b2=new Line(10,26,40,26);
        Line b3=new Line(10,32,40,32);
        Line b4=new Line(10,38,40,38);
        //END HEADERS
        float i=attendance_report.total_class_attended+attendance_report.total_duty_leaves,j=attendance_report.total_class;
        prepareChart(i,j,timetable_report.get_classes_week()*total_week,0,0);


        attendance_pane.getChildren().addAll(hamburger_button,b1,b2,b3,b4,total_attendance,total_classes,total_attended,line,
        interactive);
        attendance_pane.setLayoutX(0);attendance_pane.setLayoutY(0);
        show_layout.getChildren().add(attendance_pane);
      } 
      private void prepareChart(float lec_atd,float lec_del,float tol_class,float miss_overall,float miss_now){
          //CHART
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Number of Classes");
            yAxis.setLabel("Percentage");
            yAxis.setForceZeroInRange(false);
            xAxis.setForceZeroInRange(false);
            lineChart = 
                    new LineChart<Number,Number>(xAxis,yAxis);

            lineChart.setTitle("Attendance Simulation!");
            XYChart.Series series = new XYChart.Series();
            series.setName("Classes");
            float i,j;
            i=lec_atd;j=lec_del;
            for(int k=(int)j;j<tol_class;j+=1,k+=1){
            series.getData().add(new XYChart.Data(k,(i/j)*100));
              if(miss_now==0){
                  if(k<(tol_class-miss_overall))
                  i++;
              }
              else{
                  miss_now--;
              }
            }
            lineChart.getData().add(series);
            lineChart.setMinSize(650,480);
            lineChart.setLayoutX(0);lineChart.setLayoutY(0);
            graph_pane=new Pane();
            graph_pane.setId("back_ground");
            graph_pane.setLayoutX(420);graph_pane.setLayoutY(80);
            graph_pane.getChildren().add(lineChart);
            DropShadow ds=new DropShadow();ds.setOffsetX(7.0);ds.setColor(rgb(100,100,100));
            graph_pane.setEffect(ds);
            attendance_pane.getChildren().add(graph_pane);

        //END CHART
      }
      private void interactiveAttendance(Pane pane){
          Label classes_attended_l=new Label("Class Attended");
          Label lectures_delivered_l=new Label("Lectures Delivered");
          Label total_misses_perweek_l=new Label("Miss now");
          Label total_overall_misses_l=new Label("Over All Misses");
          Label total_classes_l=new Label("Total Classes");

          classes_attended_l.setLayoutX(10);classes_attended_l.setLayoutY(20);
          lectures_delivered_l.setLayoutX(10);lectures_delivered_l.setLayoutY(70);
          total_misses_perweek_l.setLayoutX(10);total_misses_perweek_l.setLayoutY(120);
          total_overall_misses_l.setLayoutX(10);total_overall_misses_l.setLayoutY(170);
          total_classes_l.setLayoutX(10);total_classes_l.setLayoutY(220);

          TextField classes_attended=new TextField();
          classes_attended.setText(""+(attendance_report.total_class_attended+attendance_report.total_duty_leaves));
          TextField lectures_delivered=new TextField();
          lectures_delivered.setText(""+attendance_report.total_class);
          TextField total_misses_perweek=new TextField("0");
          TextField total_overall_misses=new TextField("0");
          TextField total_classes=new TextField();
          total_classes.setText(""+timetable_report.get_classes_week()*total_week);

          classes_attended.setLayoutX(120);classes_attended.setLayoutY(20);
          lectures_delivered.setLayoutX(120);lectures_delivered.setLayoutY(70);  
          total_misses_perweek.setLayoutX(120);total_misses_perweek.setLayoutY(120);
          total_overall_misses.setLayoutX(120);total_overall_misses.setLayoutY(170);
          total_classes.setLayoutX(120);total_classes.setLayoutY(220);


          Button button=new Button("Redraw Graph!");
          button.setLayoutX(220);button.setLayoutY(340);


          button.setOnAction(e->{
              attendance_pane.getChildren().remove(graph_pane);
              int i=Integer.parseInt(classes_attended.getText());
              int j=Integer.parseInt(lectures_delivered.getText());
              int c=Integer.parseInt(total_classes.getText());;
              int overall=Integer.parseInt(total_overall_misses.getText());
              int missperweak=Integer.parseInt(total_misses_perweek.getText());
              prepareChart(i,j,c,overall,missperweak);

            });


          pane.getChildren().addAll(classes_attended,lectures_delivered,total_misses_perweek,total_overall_misses,total_classes,
          classes_attended_l,lectures_delivered_l,total_misses_perweek_l,total_overall_misses_l,total_classes_l,button);
      }
    private void PaneComponents(Control label,String string,int x,int y){
        label.setId(string);
        label.setLayoutX(x);label.setLayoutY(y);
    }
    public static void show(){  
        attendance_pane.toFront();
        TranslateTransition any_pane_show=new TranslateTransition(Duration.millis(400),attendance_pane);
        any_pane_show.setFromX(1100);any_pane_show.setToX(0);
        any_pane_show.play();
        hide_hamburger();
  }  
}
