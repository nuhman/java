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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import static javafx.scene.paint.Color.rgb;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class TimeTablePane {
    public static Pane time_table_pane;
    public Button button[];
    
    private boolean full_attendance_on=false;
    private boolean short_attendance_on=false;
    
    private TranslateTransition shortAttendanceHideFull;
    private TranslateTransition shortAttendanceShow;
    private TranslateTransition shortAttendanceShowFull;
    private TranslateTransition shortAttendanceHide;
    private Label total_attendance;
    private Label total_classes;
    private Label total_attended;
    private Pane interactiveShort;
    private LineChart<Number, Number> lineChart;
    private Pane graph_pane;
    private Pane short_attendance_pane;
    private int total_week=14;

    public TimeTablePane(){
        time_table_pane=new Pane();
        createTimeTablePane();
    }
    private void createTimeTablePane(){
    time_table_pane.setId("time_table_pane");
    time_table_pane.setMinSize(1100,600);
    //TIME_TABLE
    GridPane time_table_grid=new GridPane();
    time_table_grid.setLayoutX(150);time_table_grid.setLayoutY(20);
    time_table_grid.setId("time_table_grid");
    time_table_grid.setMinSize(800,500);
    Button hamburger_button=new Button();
    hamburger_button.setId("ham_button");
    hamburger_button.setLayoutX(10);hamburger_button.setLayoutY(18);
    hamburger_button.setMinSize(30,18);
    hamburger_button.setOnAction(e->show_hamburger());
    
    
    
        button=new Button[8*5];
        String string[]=timetable_report.period;
        
         
        for(int i=0,row=0;row<8;row++)
            for(int col=0;col<5;col++,i++){
                button[i]=new Button();
                final Button b=button[i];
                button[i].setText(string[i]);
                button[i].setId("time_period");
                button[i].setMinSize(160,62.5);
                button[i].setMaxSize(160,62.5);
                button[i].setOnMouseEntered(e->{
                    show_short(attendance_report.get_list(b.getText()));
                    select_similar(b);
                });
                button[i].setOnMouseClicked(e->show_full(attendance_report.get_list(b.getText()), 
                        timetable_report.get_credit(b.getText())));    
                GridPane.setConstraints(button[i],col,row);
                time_table_grid.getChildren().add(button[i]);
            }
        time_table_grid.setOnMouseExited(e->unMatch());
    //END_TABLE
    Line b1=new Line(10,20,40,20);
    Line b2=new Line(10,26,40,26);
    Line b3=new Line(10,32,40,32);
    Line b4=new Line(10,38,40,38);    
    time_table_pane.getChildren().addAll(hamburger_button,b1,b2,b3,b4,time_table_grid);
    time_table_pane.setLayoutX(0);time_table_pane.setLayoutY(0);
    show_layout.getChildren().add(time_table_pane);
    time_table_pane.toFront();
    }
    public static void show(){
        time_table_pane.toFront();
        TranslateTransition any_pane_show=new TranslateTransition(Duration.millis(400),time_table_pane);
        any_pane_show.setFromX(1100);any_pane_show.setToX(0);
        any_pane_show.play();
        hide_hamburger();
    }
    public void select_similar(Button b){
          for(int i=0;i<40;i++){
            if(button[i].getText().matches(b.getText())){
                   button[i].setId("selected");
            }
            else
                    button[i].setId("time_period");       
          }                                       
    }
    public void unMatch(){
                    attd_hide();
                     for(int i=0;i<40;i++){
                           button[i].setId("time_period");

                    }
    }
    
    //ATTENDANCE FUNCTIONS START
            private void createShortAttendancePane(Pane short_attendance_pane,int i){
                short_attendance_pane.setMinSize(1100,600);
                short_attendance_pane.setLayoutY(600);short_attendance_pane.setLayoutX(0);
                short_attendance_pane.setId("short_attendance_pane");
                short_attendance_pane.toFront();
                Button back_short_attendance=new Button("<---Back");
                back_short_attendance.setId("back_short_attendance");
                back_short_attendance.setMinSize(35,25);
                back_short_attendance.setLayoutX(1000);back_short_attendance.setLayoutY(20);
                back_short_attendance.setOnAction(e->{
                     if(full_attendance_on==true){
                           shortAttendanceHideFull.play();
                           short_attendance_on=false;
                           full_attendance_on=false;
                      }
                  });
                shortAttendanceShow = new TranslateTransition(Duration.millis(500),short_attendance_pane);
                shortAttendanceShow.setFromY(0);shortAttendanceShow.setToY(-80);
                shortAttendanceShowFull = new TranslateTransition(Duration.millis(500),short_attendance_pane);
                shortAttendanceShowFull.setFromY(-80);shortAttendanceShowFull.setToY(-600);

                shortAttendanceHide = new TranslateTransition(Duration.millis(500),short_attendance_pane);
                shortAttendanceHide.setFromY(-80);shortAttendanceHide.setToY(0);
                shortAttendanceHideFull = new TranslateTransition(Duration.millis(500),short_attendance_pane);
                shortAttendanceHideFull.setFromY(-600);shortAttendanceHideFull.setToY(0);
                
                total_attendance=new Label();
                PaneComponents(total_attendance,"attendance_pane_headers",800,20);

                total_classes=new Label();
                PaneComponents(total_classes,"attendance_pane_headers",400,20);

                total_attended=new Label();
                PaneComponents(total_attended,"attendance_pane_headers",590,20);

                if(attendance_report.rendered==true){
                    total_attendance.setText("Attendance:"+attendance_report.course_attendance.get(i).text()+"%");
                    total_classes.setText("Total Classes:"+attendance_report.total_classes.get(i).text());
                    total_attended.setText("Total Attended:"+attendance_report.classes_present.get(i).text()+" + "+attendance_report.duty_leave.get(i).text());
                }
                Line line=new Line(0,70,1100,72);
                interactiveShort=new Pane();
                interactiveShort.setMinSize(200,200);
                interactiveShort.setLayoutX(70);interactiveShort.setLayoutY(110);
                
                short_attendance_pane.getChildren().addAll(total_attendance,total_classes,total_attended,line,
                        back_short_attendance,interactiveShort);
                time_table_pane.getChildren().add(short_attendance_pane);
                
            }
            public void prepareChart(int no_classes,int at_classes,int final_no_classes,int miss_overall,int miss_now){
                                DropShadow ds=new DropShadow();ds.setOffsetX(5.0);ds.setColor(rgb(100,100,100));
                                final NumberAxis xAxis = new NumberAxis();
                                final NumberAxis yAxis = new NumberAxis();
                                xAxis.setLabel("Number of Classes");
                                yAxis.setLabel("Percentage");
                                yAxis.setForceZeroInRange(false);
                                xAxis.setForceZeroInRange(false);
                                lineChart = new LineChart<Number,Number>(xAxis,yAxis);
                                lineChart.setTitle("Attendance Simulation!");
                                lineChart.setMinSize(650,480);
                                lineChart.setLayoutX(420);lineChart.setLayoutY(80);
                                XYChart.Series series = new XYChart.Series();
                                series.setName("Classes");
                                float i=at_classes,j=no_classes;
                                for(int k=(int)j;j<final_no_classes;j+=1,k+=1){
                                series.getData().add(new XYChart.Data(k,(i/j)*100));
                                  if(miss_now==0){
                                      if(k<(final_no_classes-miss_overall))
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
                                graph_pane.setEffect(ds);
                                short_attendance_pane.getChildren().add(graph_pane);

            }
            private void interactiveAttendance(int i,int j){
                                    Pane interactiveAttendancePane=new Pane();
                                    interactiveAttendancePane.setId("interactiveAttendance");
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
                                    classes_attended.setText(""+i(attendance_report.classes_present.get(i).text(),attendance_report.duty_leave.get(i).text()));
                                    TextField lectures_delivered=new TextField();
                                    lectures_delivered.setText(""+attendance_report.total_classes.get(i).text());
                                    TextField total_misses_perweek=new TextField("0");
                                    TextField total_overall_misses=new TextField("0");
                                    TextField total_classes=new TextField();
                                    total_classes.setText(""+j);

                                    classes_attended.setLayoutX(120);classes_attended.setLayoutY(20);
                                    lectures_delivered.setLayoutX(120);lectures_delivered.setLayoutY(70);  
                                    total_misses_perweek.setLayoutX(120);total_misses_perweek.setLayoutY(120);
                                    total_overall_misses.setLayoutX(120);total_overall_misses.setLayoutY(170);
                                    total_classes.setLayoutX(120);total_classes.setLayoutY(220);


                                    Button button=new Button("Redraw Graph!");
                                    button.setLayoutX(220);button.setLayoutY(340);


                                    button.setOnAction(e->{
                                        short_attendance_pane.getChildren().remove(graph_pane);
                                        int a=Integer.parseInt(classes_attended.getText());
                                        int d=Integer.parseInt(lectures_delivered.getText());
                                        int c=Integer.parseInt(total_classes.getText());;
                                        int overall=Integer.parseInt(total_overall_misses.getText());
                                        int missperweak=Integer.parseInt(total_misses_perweek.getText());
                                        prepareChart(d,a,c,overall,missperweak);

                                      });
                        
      
                        interactiveAttendancePane.getChildren().addAll(classes_attended,lectures_delivered,total_misses_perweek,total_overall_misses,total_classes,
                        classes_attended_l,lectures_delivered_l,total_misses_perweek_l,total_overall_misses_l,total_classes_l,button);
  
                          
                        interactiveShort.getChildren().add(interactiveAttendancePane);
}
                        private void show_short(int i){ 
                                if(short_attendance_pane==null){
                                             short_attendance_pane=new Pane();
                                             createShortAttendancePane(short_attendance_pane,i);
                                }
                                if(short_attendance_on==false && full_attendance_on==false && i!=99){
                                        total_attendance.setText("Attendance:"+attendance_report.course_attendance.get(i).text()+"%");
                                        total_classes.setText("Total Classes:"+attendance_report.total_classes.get(i).text());
                                        total_attended.setText("Total Attended:"+attendance_report.classes_present.get(i).text()+" + "+attendance_report.duty_leave.get(i).text());
                                        shortAttendanceShow.play();
                                        short_attendance_on=true;
                                }
                                if(short_attendance_on==true && full_attendance_on==false && i!=99){
                                        total_attendance.setText("Attendance:"+attendance_report.course_attendance.get(i).text()+"%");
                                        total_classes.setText("Total Classes:"+attendance_report.total_classes.get(i).text());
                                        total_attended.setText("Total Attended:"+attendance_report.classes_present.get(i).text()+" + "+attendance_report.duty_leave.get(i).text());

                                }

              } 
                        private void show_full(int i,int j){
                            try{
                                if(full_attendance_on==false && short_attendance_on==true){
                                    int no_classes=Integer.parseInt(attendance_report.total_classes.get(i).text());
                                    int at_classes=Integer.parseInt(""+i(attendance_report.classes_present.get(i).text(),attendance_report.duty_leave.get(i).text()));
                                    short_attendance_pane.getChildren().remove(lineChart);
                                    prepareChart(no_classes,at_classes,j*total_week,0,0);
                                    interactiveAttendance(i,j*total_week);
                                    shortAttendanceShowFull.play();
                                    full_attendance_on=true;
                                    short_attendance_on=false;       
                                }
                            }
                            catch(Exception e){}
              }
                        private void attd_hide(){
                                if(short_attendance_on==true){
                                    shortAttendanceHide.play();
                                    short_attendance_on=false;
                                    full_attendance_on=false;
                                }

              }
             
   
    private void PaneComponents(Control label,String string,int x,int y){
        label.setId(string);
        label.setLayoutX(x);label.setLayoutY(y);
    }
    public int i(String s,String d){
         return Integer.parseInt(s)+Integer.parseInt(d);
    }
}


 