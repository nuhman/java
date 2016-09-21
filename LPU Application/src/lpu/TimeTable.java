package lpu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TimeTable extends Thread{
    public String period[];
    public boolean rendered=false;
    public String user;
    public String password;
    public TimeTable(String user,String password){
         this.user=user;
         this.password=password;
    }
    public void run(){
        String base="https://ums.lpu.in/lpuums";
        try {
            String time_table_url=base+"/Reports/frmStudentTimeTable.aspx";
            String sessionID=Login.sessionID;	
			String iframe=base+Jsoup.connect(time_table_url)
					.cookie("ASP.NET_SessionId", sessionID)
                                        .timeout(10000)
					.get()
					.getElementById("ReportFrameReportViewerabcd").attr("src");
			
			String frame=base+Jsoup.connect(iframe)
					.cookie("ASP.NET_SessionId", sessionID)
                                        .timeout(10000)
					.get()
					.getElementById("report").attr("src");
			
			Document time_table=Jsoup.connect(frame)
                            .cookie("ASP.NET_SessionId", sessionID)
                            .timeout(10000)
                            .get();
                        
                        Elements monday=time_table.getElementsByClass("a96cl");
			Elements tuesday=time_table.getElementsByClass("a100cl");
			Elements wednesday=time_table.getElementsByClass("a104cl");
			Elements thursday=time_table.getElementsByClass("a108cl");  
			Elements friday=time_table.getElementsByClass("a112cl");
			
			int trav=monday.size();
			period=new String[40];
                        
                        final Pattern pattern = Pattern.compile("a");
                       
                        
			for(int i=0;i<trav;i++){
                          period[5*i]=extractClass(monday.get(i).text());
		          period[5*i+1]=extractClass(tuesday.get(i).text());
		          period[5*i+2]=extractClass(wednesday.get(i).text());
			  period[5*i+3]=extractClass(thursday.get(i).text());
		          period[5*i+4]=extractClass(friday.get(i).text());
                        }
                        
                     rendered=true;   
        } catch (Exception e) {
        }
    }
    public int get_credit(String course){
            int credit=0;
            course=course.toLowerCase();
            for(int i=0;i<40;i++){
                if(course.matches(period[i].toLowerCase()))
                    credit++;
            }
            return credit;
        }
    public int get_classes_week(){
            int credit=0;
            String course="";
            for(int i=0;i<40;i++){
                if(course.matches(period[i].toLowerCase()))
                    credit++;
            }
            return 40-credit+1;
        }
    public String extractClass(String line){
                String pattern = "C:(.*)/";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(line);
                if (m.find( ))
                      return "" + m.group(1);
                else 
                      return "";
        }
}   

