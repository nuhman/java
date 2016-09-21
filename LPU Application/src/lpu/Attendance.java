package lpu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Attendance extends Thread{
	String base="https://ums.lpu.in/lpuums";
        public boolean rendered=false;
        public int total_duty_leaves;
        public int total_class;
        public int total_class_attended;
        public int total_attendance;
        public Elements course;
        public Elements total_classes;
        public Elements classes_present;
	public Elements course_attendance;  
	public Elements duty_leave;
        public String user;
        public String password;
	public Attendance(String user,String password){
              this.user=user;
              this.password=password;
        }
        public void run(){
		try{
			String attendance_url=base+"/Reports/rptCourseWiseStudentAttendance.aspx";
			String sessionID=Login.sessionID;
			String iframe=base+Jsoup.connect(attendance_url)
					.cookie("ASP.NET_SessionId",sessionID)
                                        .timeout(10000)
					.get()
					.getElementById("ReportFrameReportViewer1").attr("src");
			
			String frame=base+Jsoup.connect(iframe)
					.cookie("ASP.NET_SessionId", sessionID)
                                        .timeout(10000)
					.get()
					.getElementById("report").attr("src");
			
			Document attendance_table=Jsoup.connect(frame)
                            .cookie("ASP.NET_SessionId", sessionID)
                            .timeout(10000)
                            .get();
			
			course=attendance_table.getElementsByClass("a96");
			total_classes=attendance_table.getElementsByClass("a108");
			classes_present=attendance_table.getElementsByClass("a112c");
			course_attendance=attendance_table.getElementsByClass("a116");  
			duty_leave=attendance_table.getElementsByClass("a104");
			
			int trav=course.size();
                       total_duty_leaves=Integer.parseInt(attendance_table.getElementsByClass("a129").text());
                       total_class=Integer.parseInt(attendance_table.getElementsByClass("a133").text());
                       total_class_attended=Integer.parseInt(attendance_table.getElementsByClass("a137").text());
                       total_attendance=Integer.parseInt(attendance_table.getElementsByClass("a141").text());      
                       rendered=true;
        }
		catch(Exception e){}
	}        
        public int get_list(String cour){
            cour=cour.toLowerCase();
            for(int i=0;i<course.size();i++){
                String subject=extractClass(course.get(i).text()).toLowerCase();
                if(cour.matches(subject))
                    return i;
            }
            return 99;
        }
        public String extractClass(String line){
                String pattern = "(.*\\d+)";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(line);
                if (m.find( ))
                      return "" + m.group(1);
                else 
                      return "";
        }
}