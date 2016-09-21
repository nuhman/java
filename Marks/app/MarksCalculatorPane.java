package app;

import static app.ShowLayoutManager.show_layout;
import static java.lang.Math.ceil;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.rgb;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class MarksCalculatorPane {
        public static Pane marksCalculatorPane;
        Pane operator;
        Pane cal_pane;
        Boolean calHidden=true;
        
        private int number_of_ca=0;
        private int ca_component=0;
        private int attendance_value=0;
        private int end_term_value=0;
        private int mid_term_value=0;
        
        int attendance_marks=0;
        int mid_term_marks=0;
        
        private TextField ca[];
        private Label ca_l[];
        private TextField no_of_cas; 
        
        PieChart chart;
        
        Line line_ca;
        Line line_second;
        public MarksCalculatorPane(){
            marksCalculatorPane=new Pane();
            marksCalculatorPane.setId("marks_calculator_pane");
            marksCalculatorPane.setMinSize(1100,600);
            operator=new Pane();
            DropShadow ds=new DropShadow();ds.setOffsetX(5.0);ds.setColor(rgb(100,100,100));
            
            Label no_of_cas_l=label("No of cas",80);
            Label ca_weight_l=label("Ca weight",190);
            Label attd_weight_l=label("attd weight",350);
            Label mid_term_weight_l=label("Midterm weight",510);
         
            
            no_of_cas=textField("2","No of cas",80);
            TextField ca_weight=textField("20","Ca weight",190);
            
            TextField attd_weight=textField("5","attd weight",350);
            attd_weight.setMaxSize(130,30);
            
            TextField mid_term_weight=textField("25","Mid weight",510);
            mid_term_weight.setMaxSize(130,30);
            
            ca=new TextField[10];
            ca_l=new Label[10];
            Button intialize=new Button("$");
            intialize.setMaxSize(50,30);
            intialize.setMinSize(50,30);
            intialize.setLayoutX(740);intialize.setLayoutY(100);
            intialize.setId("compute");
            intialize.setEffect(ds);
            intialize.setOnMousePressed(e->intialize.setEffect(null));
            intialize.setOnMouseReleased(e->intialize.setEffect(ds));
            intialize.setOnAction(e->{
              no_of_cas.setId(null);
              try{number_of_ca=Integer.parseInt(no_of_cas.getText());}catch(NumberFormatException exp){number_of_ca=0;}
              try{ca_component=Integer.parseInt(ca_weight.getText());}catch(NumberFormatException exp){ca_component=0;}
              try{attendance_value=Integer.parseInt(attd_weight.getText());}catch(NumberFormatException exp){attendance_value=0;}
              try{mid_term_value=Integer.parseInt(mid_term_weight.getText());}catch(NumberFormatException exp){mid_term_value=0;}
              end_term_value=100-(ca_component+attendance_value+mid_term_value);
              handle();
            });
            //FUNCTION PANE
            cal_pane=new Pane();
            cal_pane.setMinSize(850,350);
            cal_pane.setLayoutX(30);cal_pane.setLayoutY(600);
            line_ca=new Line(280,20,280,300);
            
            //SECOND SEGMENT            
            TextField midterm_t=new TextField();
            Label midterm_l=new Label("Mid Term Marks");
            midterm_t.setPromptText("Mid Term Marks");
            midterm_t.setMinSize(120,30);
            midterm_l.setMinSize(120,30);
            midterm_t.setLayoutX(340);midterm_t.setLayoutY(60);
            midterm_l.setLayoutX(320);midterm_l.setLayoutY(30);
            
            
            TextField attendance_t=new TextField();
            Label attendance_l=new Label("Attendance");
            attendance_t.setPromptText("Attendance");
            attendance_t.setMinSize(120,30);
            attendance_l.setMinSize(120,30);
            attendance_t.setLayoutX(340); attendance_t.setLayoutY(130);
            attendance_l.setLayoutX(320);attendance_l.setLayoutY(100);
                        
            Button compute=new Button();
            compute.setId("compute");
            compute.setMinSize(100,100);
            compute.setLayoutX(365);compute.setLayoutY(180);
            compute.setEffect(ds);
            compute.setText("Compute");
            
            compute.setOnMousePressed(e->compute.setEffect(null));
            compute.setOnMouseReleased(e->compute.setEffect(ds));
            compute.setOnAction(e->{
                try{attendance_marks=Integer.parseInt(attendance_t.getText());}catch(Exception exp){attendance_marks=0;}
                try{mid_term_marks=Integer.parseInt(midterm_t.getText());}catch(Exception exp){mid_term_marks=0;}
                calculate();
            });
            
            line_second=new Line(540,20,540,300);
            cal_pane.getChildren().addAll(line_ca,midterm_l,midterm_t,attendance_l,attendance_t,
                    line_second,compute);
            cal_pane.setId("cal_pane");
            //END FUNCTION PANE
            cal_pane.setEffect(ds);
            
            operator.setMinSize(900,450);
            operator.setLayoutX(100);operator.setLayoutY(30);
            operator.setId("operator");
            operator.getChildren().addAll(cal_pane,intialize);  
            marksCalculatorPane.getChildren().addAll(operator);
            marksCalculatorPane.setLayoutX(0);marksCalculatorPane.setLayoutY(0);
            marksCalculatorPane.setLayoutX(0);marksCalculatorPane.setLayoutY(0);
            show_layout.getChildren().add(marksCalculatorPane);
        }
        private void handle(){
            ScaleTransition st;
            try{
            for(int i=number_of_ca-1;i<10;i++)
                if(ca[i]!=null){
                    st = new ScaleTransition(Duration.millis(1000),ca[i]);
                    st.setByX(-1.0f);
                    st.setByY(-1.0f);
                    st.play();
                    ca[i]=null;
                    //cal_pane.getChildren().remove(ca[i]);
                }
            for(int i=0,j=0;i<number_of_ca;i+=2,j++){
                cal_pane.getChildren().remove(ca[i]);
                ca[i]=new TextField();
                ca[i].setPromptText("Assement:"+(i+1));
                ca[i].setLayoutX(40);ca[i].setLayoutY(50+(j*40));
                ca[i].setMaxWidth(100);
                cal_pane.getChildren().add(ca[i]);
                ca[i].setId("ca_label");
            }
            for(int i=1,j=0;i<number_of_ca;i+=2,j++){
                cal_pane.getChildren().remove(ca[i]);
                ca[i]=new TextField();
                ca[i].setPromptText("Assement:"+(i+1));
                ca[i].setLayoutX(160);ca[i].setLayoutY(70+(j*40));
                ca[i].setMaxWidth(100);
                cal_pane.getChildren().add(ca[i]);
                ca[i].setId("ca_label");
            }
            if(calHidden){
                    TranslateTransition tt = new TranslateTransition(Duration.millis(1000),cal_pane);
                    tt.setFromY(0);tt.setToY(-450);
                    tt.play();
                    TranslateTransition tt1 = new TranslateTransition(Duration.millis(1000),line_ca);
                    tt1.setFromY(-900);tt1.setToY(0);
                    tt1.play();
                    TranslateTransition tt2 = new TranslateTransition(Duration.millis(1000),line_second);
                    tt2.setFromY(-900);tt2.setToY(0);
                    tt2.play();
                    calHidden=false;
            }
            }
            catch(ArrayIndexOutOfBoundsException ae){
                no_of_cas.setId("error_red");
                for(int i=0;i<10;i++)if(ca[i]!=null)cal_pane.getChildren().remove(ca[i]);
            }
        }
        public void calculate(){
            int ca_marks=0;
            for(int i=0;i<number_of_ca;i++)
                try{ca_marks+=Integer.parseInt(ca[i].getText());}catch(Exception exp){ca_marks+=0;}
            
            double ca_com=((double)ca_marks/((double)number_of_ca*30))*((double)ca_component/100)*100;
            int attd=attendance_marks;
            double mid_term=((double)mid_term_marks/40)*((double)mid_term_value/100)*100;
            
            int end_term=40-((int)Math.ceil(ca_com)+attd+(int)Math.ceil(mid_term));
            if(end_term<=0)
                end_term=0;
            
            thirdSegment((int)Math.ceil(ca_com),attd,(int)Math.ceil(mid_term),end_term);
        }
        public void thirdSegment(int ca,int attd,int mid,int req){
            //THIRD SElGMENT
            if(chart!=null)cal_pane.getChildren().remove(chart);
            ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("CA_Component:"+ca,ca),
                new PieChart.Data("MidTerm:"+mid,mid),
                new PieChart.Data("Attdenace:"+attd,attd),
                new PieChart.Data("EndTerm Required:"+(int)Math.ceil(req*(100/(double)end_term_value)*0.70f),req));
             final PieChart chart = new PieChart(pieChartData);
             chart.setLabelsVisible(false);
             chart.setMaxSize(300,300);
             chart.setMinSize(300,300);
             chart.setLayoutX(550);chart.setLayoutY(40);
             cal_pane.getChildren().add(chart); 
            //END THIRD SEGMENT
        }
        
        private TextField textField(String value,String string,int x){
        TextField textfield=new TextField(value);
        textfield.setPromptText(string);
        textfield.setLayoutX(x);textfield.setLayoutY(100);
        textfield.setMaxSize(100,30);
        textfield.setMinSize(100,30);
        operator.getChildren().add(textfield);
        return textfield;
        }
        private Label label(String string,int x){
        Label label=new Label(string);
        label.setLayoutX(x);label.setLayoutY(80);
        label.setMaxSize(100,30);
        operator.getChildren().add(label);
        label.setId("marks_calculator_label");
        return label;
        }
}
