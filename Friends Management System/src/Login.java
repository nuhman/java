import javax.swing.*;
import java.sql.*;
import java.awt.*;

public class Login{
    private JButton LogIn;
    private JButton new_user;
    private JLabel user_name;
    private static JTextField user;
    private JLabel password;
    private static JPasswordField pass;
    public static Long act=0L;
    private static JFrame frame;
    private static JPanel Log;
    private String name="";
    private String code="";
    private static JLabel error_message;
    private boolean loggedIn=false;
    Login(){
        frame=new JFrame();
        
        Log=new JPanel();
        Log.setLayout(null);
        Log.setSize(850,500);
        Log.setBackground(new Color(128,128,128));
        
        JPanel box=new JPanel();
        box.setSize(250,200);
        box.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        box.setBounds(90,90,260,150);
        box.setOpaque(false);
       
        user_name=new JLabel("UserName");
        user_name.setForeground(Color.WHITE);
        user=new JTextField("",30);
        error_message=new JLabel();
        error_message.setForeground(Color.WHITE);
        password=new JLabel("Password");
        password.setForeground(Color.WHITE);
        pass=new JPasswordField("",30);
        
        user_name.setBounds(110,110,60,30);
        user.setBounds(180,110,150,30);
        error_message.setBounds(360,110,200,30);
        password.setBounds(110,145,60,30);
        pass.setBounds(180,145,150,30);
        
        Log.add(user_name);
        Log.add(user);
        Log.add(error_message);
        Log.add(password);
        Log.add(pass);
        Log.add(box);
        
        LogIn=new JButton("Login");
        LogIn.setBounds(140,185,80,30);
        LogIn.addActionListener(e->handle(LogIn));
        new_user=new JButton("New User");
        new_user.addActionListener(e->handle(new_user));
        new_user.setBounds(230,185,100,30);
   
        LogIn.setBackground(new Color(255,140,140));
        LogIn.setForeground(Color.WHITE);
        LogIn.setFont(new Font("Tahoma", Font.BOLD, 12));
        new_user.setBackground(new Color(255,140,140));
        new_user.setForeground(Color.WHITE);
        new_user.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        Log.add(LogIn);
        Log.add(new_user);
        frame.add(Log);
        frame.setTitle("Login");
        frame.setSize(850,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    public static void reEnter(){
        user.setText("");
        pass.setText("");
        error_message.setText("");
        frame.setTitle("Login");
        Log.setVisible(true);
    }
    
    public static void reEnter(String message){
        user.setText("");
        pass.setText("");
        error_message.setText("");
        frame.setTitle("Login");
        Log.setVisible(true);
        error_message.setText(message);
    }

    private void handle(JButton button) { //login in 
        if(button==LogIn){
            name=user.getText();
            code=pass.getText();
        
            if(name.isEmpty()){user.setBackground(new Color(255,140,140));}
            else user.setBackground(Color.WHITE);
        
            if(code.isEmpty()){pass.setBackground(new Color(255,140,140));}
            else pass.setBackground(Color.WHITE);
            
            loggedIn=check.validLogin(name,code);
           
            if(loggedIn){
               Log.setVisible(false);
               new SignIn(frame,name,act);
            }
            else error_message.setText("*Incorrect username or password!");
        }
        if(button==new_user){
            name=user.getText();
            user.setText("");
            code=pass.getText();
            pass.setText("");
            user.setBackground(Color.WHITE);
            pass.setBackground(Color.WHITE);
            Log.setVisible(false);
            new Register(frame,name);
        }    
     }
}
class check{
    public static boolean validLogin(String user,String password){
        Connection con;
        String name="";
        try{        
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/friends","root","delta"); 
        Statement stmt = con.createStatement();       
        ResultSet rs = stmt.executeQuery("select * from friends_signup where user='"+user+"'and password='"+password+"';");
        while(rs.next()){
            name=rs.getString(1);
            Login.act=rs.getLong(3);
        }
        rs.close();
        stmt.close();
        con.close();
       }
      catch(Exception e){e.printStackTrace();}
      if(name.matches(user)&&name.isEmpty()==false)
        return true;
      else 
          return false;
    }
}