package lpu;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class ResetPassword {
    String base="https://ums.lpu.in/lpuums";
    int i=0;
    public ResetPassword(String user,String password){
        String sessionID=Login.sessionID;
        reset(user,password,sessionID);
        reset(user,password,new Login(user,"Abcd#5").get_sessionID());
    }
    private void reset(String user,String password,String sessionID){
        try {
            String login_url=base+"/frmchangepassword.aspx";
            
            String view_state=Jsoup.connect(login_url)
                                        .cookie("ASP.NET_SessionId", sessionID).timeout(10000)
					.get().getElementById("__VIEWSTATE").attr("value");
            
            Connection.Response response = Jsoup.connect(base+"/frmchangepassword.aspx")
                    .data("__VIEWSTATE",view_state)
                    .data("txtOldPassword",(i==0)?password:"Abcd#5")
                    .data("txtNewPassword",(i==0)?"Abcd#5":password)
                    .data("txtConfirmPassword",(i==0)?"Abcd#5":password)
                    .data("btnUpdate","Change Password")
                    .data("hdPasswordLength","6")
                    .data("hdRollid","3")
                    .cookie("ASP.NET_SessionId", sessionID)
                    .method(Connection.Method.POST).timeout(10000).execute();       
            i++;
        } 
        catch (Exception e) {i++;}
    }
}