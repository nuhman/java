import java.sql.*;
import java.awt.Color;
import java.awt.Font;
import java.sql.DriverManager;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;

public class Assemble{
    public static void main(String args[]){
        new Login();
    }
}

class Action {  //register users
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
    Action(JFrame frm,String u){
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
        Log.setSize(750,500);
        Log.setBackground(new Color(128,128,128));
        
        JPanel box=new JPanel();
        box.setSize(250,200);
        box.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        box.setBounds(55,70,290,215);
        box.setOpaque(false);
        
        user_name=new JLabel("UserName");
        password=new JLabel("Password");
        l_pass_2=new JLabel("Reenter Password");
        l_act_no=new JLabel("Acount No");
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
        int ac=0;
        try{
         ac=Integer.parseInt(act_no.getText());
         act_isDigit=true;
         act_no.setBackground(Color.GREEN);
         }
         catch(NumberFormatException e){
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","delta"); 
            stmt = con.createStatement(); 
            ResultSet result = stmt.executeQuery("SELECT * FROM bank_signup WHERE user='"+name+"'");
            if (result.getRow() != 0){error_message.setText("*User Already Exists");user.setBackground(new Color(255,140,140));} 
            else{
            try{
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","delta"); 
                
                    PreparedStatement stm = con.prepareStatement("insert into bank_signup values(?,?,?)");
                    stm.setString(1,name);
                    stm.setString(2,p1);
                    stm.setInt(3, ac);
                    stm.execute();
                    stm = con.prepareStatement("insert into bank_data values(?,?)");
                    stm.setInt(1,ac);
                    stm.setInt(2,0);
                    stm.execute();
                    result.close();
                    stm.close();
                    stmt.close();
                    con.close(); 
                    Log.setVisible(false);
                    Login.reEnter("*Account Registration Successful!");
                }
                catch(Exception e){
                    error_message.setText("*Account already exists!");
                    user.setBackground(new Color(255,140,140));
                    act_no.setBackground(new Color(255,140,140));
                    result.close();
                    stmt.close();
                    con.close();
                    }
                }
            }
          catch(Exception e){}
        }
        else{error_message.setText("*Registration Failed!");}
    }
}

class Login{
    private JButton LogIn;
    private JButton new_user;
    private JLabel user_name;
    private static JTextField user;
    private JLabel password;
    private static JPasswordField pass;
    public static int act=0;
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
        Log.setSize(750,500);
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
        frame.setSize(750,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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
               new signIn(frame,name,act);
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
            new Action(frame,name);
        }    
     }
}
class check{
    public static boolean validLogin(String user,String password){
        Connection con;
        String name="";
        try{        
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","delta"); 
        Statement stmt = con.createStatement();       
        ResultSet rs = stmt.executeQuery("select * from bank_signup where user='"+user+"'and password='"+password+"';");
        while(rs.next()){
            name=rs.getString(1);
            Login.act=rs.getInt(3);
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

class signIn{ 
    private JFrame frame;
    private JPanel Log;
    private JPanel topLeft;
    private JPanel bottomDown;
    private JLabel user_name;
    private JLabel act_no;
    private JLabel balance;
    private JPanel center;
    private int bal=0;
    private JLabel label1;
    private JLabel label2;
    private JLabel line;
    private JLabel label3;
    private int account_no;
    JButton commit;
    private int temp=0;
    private JTextField money;
    signIn(JFrame frm,String user,int act){
        account_no=act;
        Connection con=null;
        try{
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","delta"); 
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery("SELECT * FROM bank_data WHERE act_no ='" + act + "';");
        while(rs.next())
            bal=rs.getInt(2);
        rs.close();
        stmt.close();
        con.close();
        }
        catch(Exception e){
        
        }
        
        frame=frm;
        frame.setTitle("Welcome!");
        frame.setLayout(null);
        
        user_name=new JLabel("Welcome!   "+user);
        act_no=new JLabel("Account Number:"+act);
        balance=new JLabel("Balance:"+bal);
        
        Log=new JPanel();
        Log.setLayout(null);
        Log.setSize(750,500);
        
        topLeft=new JPanel();
        topLeft.setBackground(new Color(128,128,128));
        topLeft.setSize(250,250);
        topLeft.setBounds(0,0,250,250);
        topLeft.setLayout(null);
        
        user_name.setBounds(70,80,200,30);
        act_no.setBounds(65,100,200,30);
        balance.setBounds(85,120,200,30);
        
        topLeft.add(user_name);
        topLeft.add(act_no);
        topLeft.add(balance);
        bottomDown=new JPanel();
        bottomDown.setSize(250,250);
        bottomDown.setBackground(new Color(135,135,135));
        
        bottomDown.setBounds(0,250,250,250);
        bottomDown.setLayout(null);
        JButton logout=new JButton("Logout");
        logout.setBounds(80,160,100,30);
        logout.setBackground(new Color(255,140,140));
        logout.setForeground(Color.WHITE);
        logout.setFocusPainted(false);
        logout.setFont(new Font("Tahoma", Font.BOLD, 12));
        logout.addActionListener(e->{
            Log.setVisible(false);
            Login.reEnter("*Logout Successful!");
        });
        bottomDown.add(logout);
        
        center=new JPanel();
        center.setLayout(null);
        center.setBounds(250,0,500,500);
        center.setBackground(new Color(50,80,80));
        
        JPanel transaction=new JPanel();
        transaction.setLayout(null);
        transaction.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        transaction.setBackground(new Color(50,80,80));
        transaction.setBounds(55,80,400,150);
        
        label1=new JLabel("Balance--------------------------------------------------------->  "+bal);
        label1.setBounds(50,20,400,30);
        label1.setForeground(Color.WHITE);
        label2=new JLabel();
        label2.setBounds(205,45,400,30);
        label2.setForeground(Color.WHITE);
        
        line=new JLabel("------------------------------------------------------------------------------------------");
        line.setBounds(25,80,400,30);
        line.setForeground(Color.WHITE);
        
        label3=new JLabel("Total Money---------------------------------------------------------->"+bal);
        label3.setBounds(30,105,400,30);
        label3.setForeground(Color.WHITE);
     
        transaction.add(label1);
        transaction.add(label2);
        transaction.add(line); 
        transaction.add(label3);
        
        JPanel op=new JPanel();
        op.setLayout(null);
        op.setBorder(BorderFactory.createLineBorder(new Color(90,90,90)));
        op.setBackground(new Color(50,80,80));
        op.setBounds(55,280,400,115);
        
        JButton deposit=new JButton("Depsoit");
        deposit.setBackground(new Color(140,200,140));
        deposit.setForeground(Color.WHITE);
        deposit.setFocusPainted(false);
        deposit.setFont(new Font("Tahoma", Font.BOLD, 12));
        money=new JTextField();
        money.setBackground(new Color(230,230,230));
        JButton withdraw=new JButton("Withdraw");
        withdraw.setBackground(new Color(200,140,140));
        withdraw.setForeground(Color.WHITE);
        withdraw.setFocusPainted(false);
        withdraw.setFont(new Font("Tahoma", Font.BOLD, 12));
        commit=new JButton("Proceed");
        commit.setBackground(new Color(150,150,230));
        commit.setForeground(Color.WHITE);
        commit.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        deposit.setBounds(260,20,110,30);
        money.setBounds(140,20,110,30);
        withdraw.setBounds(20,20,110,30);
        commit.setBounds(221,63,150,30); 
        
        deposit.addActionListener(e->deposit(money.getText()));
        withdraw.addActionListener(e->withdraw(money.getText()));
        commit.addActionListener(e->commit());
        op.add(commit);
        op.add(withdraw);
        op.add(deposit);
        op.add(money);
        
        
        
        center.add(op);
        center.add(transaction);
        
        Log.add(center);
        Log.add(topLeft);
        Log.add(bottomDown);
        frame.add(Log);
    }

    private void deposit(String text) {
        try{
                commit.setEnabled(true);
                temp=Integer.parseInt(text);
                label2.setText("Deposoit------------------> "+temp);
                label3.setText("Total Money---------------------------------------------------------->"+(bal+temp));
           }
        catch(NumberFormatException exp){
               
        }   
    }

    private void withdraw(String text) {
        try{
                temp=Integer.parseInt(text);
                label2.setText("Withdraw-------------->   -"+temp);
                label3.setText("Total Money---------------------------------------------------------->"+(bal-temp));
                if((bal-temp)<0){label3.setBackground(Color.red);commit.setEnabled(false);}
                else{label3.setBackground(new Color(50,80,80));commit.setEnabled(true);}
                temp=0-temp;
            }
           catch(NumberFormatException exp){
               
           }
    }

    private void commit() {
        Connection con;
        try{
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","delta"); 
        Statement stmt = con.createStatement();       
        stmt.executeUpdate("update bank_data set balance ="+(bal+temp)+" where act_no="+account_no);
        bal=bal+temp;
        temp=0;
        stmt.close();
        con.close();
        }
        catch(Exception e){
           
        }
        label2.setText("");
        balance.setText("Balance:"+bal);
        label1.setText("Balance--------------------------------------------------------->  "+bal);
        label3.setText("Total Money---------------------------------------------------------->"+bal);
        money.setText("");
    }
}
