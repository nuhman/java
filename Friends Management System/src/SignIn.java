import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.math.BigInteger;
public class SignIn{ 
    private JFrame frame;
    private JPanel Log; 
    private JPanel topLeft;
    private JPanel bottomDown;
    private JLabel user_name;
    private JLabel act_no;
    private JLabel balance;
    private JLabel creditL;
    private JPanel center;
    private int debit=0;
    private int credit=0;
    private JLabel label1;
    private JLabel label2;
    private JLabel line;
    private JLabel label3;
    private Long account_no;
    JButton commit;
    private int temp=0;
    private JTextField money;
    private JPanel transaction;
    private JPanel op;
    int count=0;
    private JPanel header[];
    public JCheckBox select[];
    public JCheckBox s;
    public Long delAgent[];
    JTextField nameLT;
    JTextField phoneLT;
    JTextField amountLT;
    SignIn(JFrame frm,String user,Long act){
        
         account_no=act;
         Connection con=null;
        frame=frm;
        frame.setTitle("Welcome!");
        user_name=new JLabel("Welcome!   "+user);
        act_no=new JLabel("Phone Number:"+act);
        balance=new JLabel("Debit:");
        creditL=new JLabel("Credit:");
        
        Log=new JPanel();
        Log.setLayout(null);
        Log.setSize(850,500);
        
        topLeft=new JPanel();
        topLeft.setBackground(new Color(128,128,128));
        topLeft.setSize(250,250);
        topLeft.setBounds(0,0,250,250);
        topLeft.setLayout(null);
        
        user_name.setBounds(70,80,200,30);
        act_no.setBounds(45,100,200,30);
        balance.setBounds(55,120,80,30);
        creditL.setBounds(135,120,80,30);
        
        topLeft.add(user_name);
        topLeft.add(act_no);
        topLeft.add(balance);
        topLeft.add(creditL);
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
        center.setBounds(250,0,600,500);
        center.setBackground(new Color(50,80,80));
        
        JButton delete=new JButton("Delete");
        delete.setBackground(new Color(150,150,230));
        delete.setForeground(Color.WHITE);
        delete.setFont(new Font("Tahoma", Font.BOLD, 12));
        delete.setBounds(405,20,150,30);
        delete.addActionListener(e->{
            int removed=0;
            for(int i=0;i<10;i++){
               if(header[i]!=null){ 
                if(select[i].isSelected()){
                    transaction.remove(header[i]);
                    header[i]=null;
                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/friends","root","delta");
                        Statement s=c.createStatement();
                        s.executeUpdate("delete from friends_bank where id="+account_no+" and phone_no="+delAgent[i]);
                    }
                    catch(Exception exp){
                        exp.printStackTrace();
                    }
                    removed++;
                }
               }
           }
            count-=removed;
            s.setSelected(false);
            handle();
        });
        
        
        transaction=new JPanel();
        transaction.setLayout(new GridLayout(11,1,0,5));
        transaction.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        transaction.setBackground(new Color(50,80,80));
        transaction.setBounds(55,50,500,250);
        
        labelGenerator("S.No","Friend","Phone","Amount","Mode");
        header=new JPanel[10];        
        select=new JCheckBox[10];
        delAgent=new Long[10];

      
        
        op=new JPanel();
        op.setLayout(null);
        op.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        op.setBackground(new Color(50,80,80));     
        op.setBounds(55,325,500,100);
        
        labelGeneratorText();
        handle();
        
        center.add(delete);
        center.add(transaction);
        center.add(op);
        Log.add(center);
        Log.add(topLeft);
        Log.add(bottomDown);
        frame.add(Log);
    }
    public void labelGenerator(String s_no,String name,String phone,String amount,String due){
        JPanel header=new JPanel();
        header.setLayout(new GridLayout(1,4,5,0));
        
        Color dark=new Color(235,235,235);
        s=new JCheckBox();
        JLabel s_noL=new JLabel(s_no);
        s_noL.setBackground(dark);
        JLabel nameL=new JLabel(name);
        nameL.setBackground(dark);
        JLabel phoneL=new JLabel(phone);
        phoneL.setBackground(dark);
        JLabel amountL=new JLabel(amount);
        amountL.setBackground(dark);
        JLabel dueL=new JLabel(due);
        dueL.setBackground(dark);
        
        header.add(s);
        s.addItemListener(e->{
           if(s.isSelected()){
               for(int i=0;i<10;i++){
                    if(this.header[i]!=null)
                        select[i].setSelected(true);
               }
           }
           else
               for(int i=0;i<10;i++){
                    if(this.header[i]!=null)    
                        select[i].setSelected(false);
               }
        });
        header.add(s_noL);
        header.add(nameL);
        header.add(phoneL);
        header.add(amountL);
        header.add(dueL);
        
        
        transaction.add(header);
    }
    public void labelGeneratorData(String name,String phone,String amount,String due){
        int i=0;
        for(i=0;i<count;i++)if(header[i]==null)break;
        if(i<10){
            header[i]=new JPanel();
            header[i].setLayout(new GridLayout(1,6));
            header[i].setBackground(new Color(50,80,80));

            Color dark=new Color(238,238,238);
            select[i]=new JCheckBox();
            select[i].setBackground(new Color(50,80,80));
            select[i].setName(""+i);
            JLabel s_noL=new JLabel(""+(i+1));
            s_noL.setForeground(dark);
            JLabel nameL=new JLabel(name);
            nameL.setForeground(dark);
            JLabel phoneL=new JLabel(phone);
            phoneL.setForeground(dark);
            JLabel amountL=new JLabel(amount);
            amountL.setForeground(dark);
            JLabel dueL=new JLabel(due);
            dueL.setForeground(dark);
            
            select[i].addActionListener(e->{
                nameLT.setText(name);
                amountLT.setText(amount);
                phoneLT.setText(phone);
            });

            header[i].add(select[i]);
            header[i].add(s_noL);
            header[i].add(nameL);
            header[i].add(phoneL);
            header[i].add(amountL);
            header[i].add(dueL);


            transaction.add(header[i]);
            SwingUtilities.updateComponentTreeUI(transaction);
        }
    }
    
    public void labelGeneratorText(){
        String s_no="";String name="";String phone="";String amount="";Boolean due=false;
        JPanel header=new JPanel();
        header.setLayout(new GridLayout(1,4));
        header.setBounds(50, 20,400,30);
        header.setBackground(new Color(50,80,80));
        
        Color dark=new Color(0,0,0);
        
        String []choice={"Credit","Debit"};
        
        nameLT=new JTextField(name);
        nameLT.setForeground(dark);
        phoneLT=new JTextField(phone);
        phoneLT.setForeground(dark);
        amountLT=new JTextField(amount);
        amountLT.setForeground(dark);
        JComboBox dueL=new JComboBox(choice);
        dueL.setSelectedIndex(0);
        dueL.setForeground(dark);
        
        commit=new JButton("Proceed");
        commit.setBackground(new Color(150,150,230));
        commit.setForeground(Color.WHITE);
        commit.setFont(new Font("Tahoma", Font.BOLD, 12));
        commit.setBounds(300,60,150,30); 
        
        commit.addActionListener(e->{
        
        int amt=Integer.parseInt(amountLT.getText());
        int c=0;
        if(dueL.getSelectedItem()=="Debit"){
                         c+=amt; 
        }
        else{
                         c-=amt;
        }
            
        
        String nam=nameLT.getText();
        Long phone_num=Long.parseLong(phoneLT.getText());
        BigInteger bi = new BigInteger(phone_num.toString());
                Connection con=null;
                try{
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/friends","root","delta"); 
                Statement stmt=con.createStatement();
                    PreparedStatement stm = con.prepareStatement("insert into friends_bank values(?,?,?,?)");
                    stm.setObject(1,bi,Types.BIGINT);
                    stm.setString(2,nam);
                    
                    stm.setInt(3,c);
                    stm.setObject(4,new BigInteger(account_no.toString()),Types.BIGINT);
                    stm.execute();   
                    stmt.close();
                    con.close();
                    handle();
                }
                
                catch(com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException exp){
                      try{
                            Class.forName("com.mysql.jdbc.Driver");
                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/friends","root","delta"); 
                            Statement stmt=con.createStatement();
                            ResultSet rs=stmt.executeQuery("SELECT * FROM friends_bank WHERE phone_no="+phone_num+" and id="+account_no);
                            int get=0;
                            while(rs.next()){
                                get=rs.getInt(3);                         
                            }
                            c+=get;
                            Statement stm=con.createStatement();
                            stm.executeUpdate("update friends_bank set balance ="+(c)+" where phone_no="+phone_num+" and id="+account_no);
                            stmt.close();
                            stm.close();
                            con.close();
                            handle();
                            
                      } 
                      catch(Exception ex){
                          ex.printStackTrace();
                      }
                      
                }
                catch(Exception exp){
                   exp.printStackTrace();
                }
                
            
           });
        
        header.add(nameLT);
        header.add(phoneLT);
        header.add(amountLT);
        header.add(dueL);
        
        op.add(header);
        op.add(commit);

    }
    public void handle(){
        for(int i=0;i<10;i++){
            if(header[i]!=null){
                transaction.remove(header[i]);
                header[i]=null;
                count--;
            }
        }
        int deb=0;
        int cre=0;
        try{
            Connection con;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/friends","root","delta");
            Statement stm=con.createStatement();
            ResultSet rs=stm.executeQuery("select * from friends_bank where id="+account_no);
            String name;
            String ph;
            String am;
            String d;
            while(rs.next()){
               d="Debit";
               ph=""+rs.getLong(1);
               name=rs.getString(2);
               am=""+rs.getInt(3);
               if(Integer.parseInt(am)<0){
                   d="Credit";
                   cre+=rs.getInt(3);
               }
               else{
                   deb+=rs.getInt(3);
               }
               delAgent[count]=rs.getLong(1);
               count++;
               labelGeneratorData(name,ph,am,d);
            }
            balance.setText("Debit:"+deb);
            creditL.setText("Credit:"+(cre*-1));
            
        }
        catch(Exception e){
            e.printStackTrace();
            
        }
        SwingUtilities.updateComponentTreeUI(transaction);
    }
}

