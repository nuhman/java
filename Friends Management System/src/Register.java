import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.math.BigInteger;

public class Register{  //register users
    private JButton new_user;
    private JLabel user_name;
    private JTextField user;
    private JLabel password;
    private JPasswordField pass;
    private JLabel error_message;
    private boolean status=true;
    private boolean act_isDigit=false;
    
    private JLabel l_pass_2;
    private JLabel l_act_no;
    private JPasswordField pass_2;
    private JTextField act_no;
    private JButton register;
    private JButton back;
    private JFrame frame;
    private JPanel Log;
    public Register(JFrame frm,String u){
        frame=frm;
        frame.setTitle("Sign Up!");
        user=new JTextField("");
        pass=new JPasswordField("");
        try{
        user.setText(u);
        }
        catch(NullPointerException e){
        }
        Log=new JPanel();
        Log.setLayout(null);
        Log.setSize(850,500);
        Log.setBackground(new Color(128,128,128));
        
        JPanel box=new JPanel();
        box.setSize(250,200);
        box.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        box.setBounds(55,70,290,215);
        box.setOpaque(false);
        
        user_name=new JLabel("UserName");
        password=new JLabel("Password");
        l_pass_2=new JLabel("Reenter Password");
        l_act_no=new JLabel("Phone No");
        pass_2=new JPasswordField("");
        act_no=new JTextField("");
         
        
        user_name.setBounds(110,90,60,30);
        user_name.setForeground(Color.WHITE);
        user.setBounds(180,90,150,30);
        password.setBounds(110,125,60,30);
        password.setForeground(Color.WHITE);
        pass.setBounds(180,125,150,30);
        
        l_pass_2.setBounds(62,160,120,30);
        l_pass_2.setForeground(Color.WHITE);
        pass_2.setBounds(180,160,150,30);
        l_act_no.setBounds(110,195,60,30);
        l_act_no.setForeground(Color.WHITE);
        act_no.setBounds(180,195,150,30);
        
        error_message=new JLabel("");
        error_message.setBounds(360,90,150,30);
        error_message.setForeground(Color.WHITE);
        
        register=new JButton("Register");
        register.setBounds(180,230,150,30);
        register.addActionListener(e->handle());
        
        back=new JButton("Back");
        back.setBounds(90,230,80,30);
        back.addActionListener(e->{
            Log.setVisible(false);
            Login.reEnter();
        });
        
        back.setBackground(new Color(255,140,140));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        back.setFont(new Font("Tahoma", Font.BOLD, 12));
        register.setBackground(new Color(255,140,140));
        register.setForeground(Color.WHITE);
        register.setFocusPainted(false);
        register.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        Log.add(user_name);
        Log.add(user);
        Log.add(password);
        Log.add(pass);
        Log.add(l_pass_2);
        Log.add(l_act_no);
        Log.add(pass_2);
        Log.add(act_no);
        Log.add(register);
        Log.add(back);
        Log.add(error_message);
        Log.add(box);
    
        frame.add(Log);
    }

    private void handle() {  //Register USER
        String p1=pass.getText();
        String p2=pass_2.getText();
        String name=user.getText();
        if(p1.matches(p2)&&p1.isEmpty()==false){pass.setBackground(Color.GREEN);pass_2.setBackground(Color.GREEN);status=true;}
        else{pass.setBackground(new Color(255,140,140));pass_2.setBackground(new Color(255,140,140));status=false;}
        Long ac = 0L;
        try{
         ac=Long.parseLong(act_no.getText());
         act_isDigit=true;
         act_no.setBackground(Color.GREEN);
         }
         catch(NumberFormatException e){
             e.printStackTrace();
            status=false;
            act_no.setBackground(new Color(255,140,140));
         }
        if(name.isEmpty())user.setBackground(new Color(255,140,140));
        else user.setBackground(Color.GREEN);
            
        if((status)&&(act_isDigit)&&(name.isEmpty()==false)){
            Connection con;
            Statement stmt;
            try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/friends","root","delta"); 
            stmt = con.createStatement(); 
            ResultSet result = stmt.executeQuery("SELECT * FROM friends_signup WHERE user='"+name+"'");
            if (result.getRow() != 0){error_message.setText("*User Already Exists");user.setBackground(new Color(255,140,140));} 
            else{
            try{
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/friends","root","delta"); 
                    
                    BigInteger bi = new BigInteger(ac.toString());
                    
                    PreparedStatement stm = con.prepareStatement("insert into friends_signup values(?,?,?)");
                    stm.setString(1,name);
                    stm.setString(2,p1);
                    
                    stm.setObject(3,ac,Types.BIGINT);
                    stm.execute();
                    result.close();
                    stm.close();
                    stmt.close();
                    con.close(); 
                    Log.setVisible(false);
                    Login.reEnter("*Account Registration Successful!");
                }
                catch(Exception e){
                    e.printStackTrace();
                    error_message.setText("*Account already exists!");
                    user.setBackground(new Color(255,140,140));
                    act_no.setBackground(new Color(255,140,140));
                    result.close();
                    stmt.close();
                    con.close();
                    }
                }
            }
          catch(Exception e){e.printStackTrace();}
        }
        else{error_message.setText("*Registration Failed!");}
    }
}