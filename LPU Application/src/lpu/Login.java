package lpu;

import java.security.cert.X509Certificate;
import javax.net.ssl.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Login{
	public static String sessionID=null;
	String view_state=null;
	String tb2=null;
	String base="https://ums.lpu.in/lpuums";
    
	public Login(String user,String password){ 
	try{
		//Ignore SSL Certificate
		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, new TrustManager[]{ new TrustAllX509TrustManager() }, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier((String string, SSLSession ssls) -> true);
		//END Ignore SSL Certificate
	}
	catch(Exception e){System.out.println(e);}
         
        try{
	    String login_url=base+"/loginnew.aspx";
	    Document login_sourcecode=Jsoup.connect(login_url).get();
	    String view_state=login_sourcecode.getElementById("__VIEWSTATE").attr("value");
        
	    Connection.Response response = Jsoup.connect(login_url)
	    			.data("__VIEWSTATE",view_state)
	    			.data("TextBox1",user)
	    			.data("TextBox2",password)
	    			.data("DropDownList1", "1")
	    			.data("ddlStartWith", "default3.aspx")
	    			.data("iBtnLogin.x", "30")
	    			.data("iBtnLogin.y", "39")
	    			.method(Connection.Method.POST)
	    			.timeout(10000)
	    			.execute();	
		
	    sessionID = response.cookie("ASP.NET_SessionId");
		}
        
	catch(Exception e){System.out.println(e);}
    }
	
	public String get_sessionID(){
		return sessionID;
	}      
}

class TrustAllX509TrustManager implements X509TrustManager {
    public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
    public void checkClientTrusted(java.security.cert.X509Certificate[] certs,String authType) {}
    public void checkServerTrusted(java.security.cert.X509Certificate[] certs,String authType) {}               
}  






