package app;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import static javafx.scene.paint.Color.rgb;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Log {
    public static Pane log_pane;
    public static Rectangle rect;
    public static String userName;
    public static String passWord;
    static DropShadow line;
    static TranslateTransition revealHalf;
    public TextField user_name;
    public PasswordField password;
    public Log(){
           log_pane=new Pane();
           Pane logger=new Pane();
           DropShadow ds=new DropShadow();ds.setOffsetX(2.0);ds.setColor(rgb(255,255,255));
           line=new DropShadow();line.setOffsetX(2.0);line.setColor(rgb(255,255,255));
           logger.setEffect(ds);
           Label user=new Label("User");
           user_name=new TextField("11408062");
           Label pass=new Label("Password");
           password=new PasswordField();
           password.setText("Thunder#6");
           Button login=new Button("Login");
           login.setId("login");
           
           Label text=new Label("\"THAT MOMENT WHEN YOUR  \n \t\t\t\t\t\t\t DUTY LEAVES ARE FINALLY ADDED\"");
           text.setLayoutX(150);text.setLayoutY(140);
           text.setMinSize(200,100);
           text.setId("text");
           
           user.setLayoutX(20);user.setLayoutY(50);user.setMaxSize(100,30);
           user_name.setLayoutX(90);user_name.setLayoutY(50);user_name.setMaxSize(150,30);
           pass.setLayoutX(20);pass.setLayoutY(90);pass.setMaxSize(100,30);
           password.setLayoutX(90);password.setLayoutY(90);password.setMaxSize(150,30);
           login.setLayoutX(115);login.setLayoutY(140);login.setMinSize(120,30);
           
           rect=new Rectangle(0,588,1100,580);
           rect.setId("rect");
           rect.setEffect(null);
           
           login.setOnAction(e->{
               handle();
           });
           
           logger.setMaxSize(270,190);
           logger.setMinSize(270,190);
           logger.setId("logger");
           logger.setLayoutX(100);logger.setLayoutY(50);
           logger.getChildren().addAll(user,user_name,pass,password,login);
           log_pane.setId("log_pane");
           log_pane.getChildren().addAll(logger,text,rect);
           log_pane.setMinSize(1100,800);
           logger.toFront();
           
           
           
    }
    public void handle(){
               userName=user_name.getText();
               passWord=password.getText();
               rect.toFront();
               new SignIn();
    }
    
  /* public static void firstHalf(){
           
           revealHalf=new TranslateTransition(Duration.millis(10000),rect);
           revealHalf.setFromX(-1080);revealHalf.setToX(0);
           rect.setId("show_rect");
           rect.setEffect(line);
           revealHalf.play();
    }
    public static void secondHalf(){
           rect.setId("show_rect");
           DropShadow ds=new DropShadow();ds.setOffsetX(2.0);ds.setColor(rgb(255,255,255));
           rect.setEffect(ds);
           TranslateTransition tt=new TranslateTransition(Duration.millis(1000),rect);
           tt.setFromX(-540);tt.setToX(0);
           tt.play();
           log_pane.toFront();
    }
    public static void full(){
        rect.setId("show_rect");
        DropShadow ds=new DropShadow();ds.setOffsetX(2.0);ds.setColor(rgb(255,255,255));
        rect.setEffect(ds);
        TranslateTransition tt=new TranslateTransition(Duration.millis(500),rect);
        tt.setFromX(0);tt.setToX(0);
        tt.play();
        log_pane.toFront();
    }*/
    public static void warn(){
        rect.setId("warn");
        DropShadow ds=new DropShadow();ds.setOffsetX(2.0);ds.setColor(rgb(255,0,0));
        rect.setEffect(ds);
        rect.setLayoutX(0);
        log_pane.toFront();
    }
}