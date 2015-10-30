import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class edit extends Page
{ 
  private String name = "";
  private String mail = "";
  private String topic = "";
  private String datetime = "";
  private String cont = "";
  private String qid;

  public edit() throws Throwable {}

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    header(out);

    qid = request.getParameter("id");

    if (qid != null) {
      String query = "SELECT * FROM question WHERE Id = " + qid;
      ResultSet rs;

      try {
        rs = dbms.exec(query);      
        rs.next();

        name = rs.getString("username");
        mail = rs.getString("email");
        topic = rs.getString("topik");
        cont = rs.getString("isi");
        datetime = rs.getString("datetime");
      } 
      catch (Throwable e) {
        e.printStackTrace();
      }
    } else qid = "-1";

    out.println("<h2>What is your question :</h2>\n" +
                "<hr>\n" +
                "<div class='thread-editor'>\n" +
                "  <form name='myform' method='post' action='save' onsubmit='return validateForm()'>\n" +
                "    <input type='hidden' name='id' value=" + qid + ">\n" +
                "    <div>\n" +
                "      <input type='text' name='name' placeholder='Name' value='" + name + "'>\n" +
                "    </div>\n" +
                "    <div>\n" +
                "      <input type='text' name='email' placeholder='Email' value='" + mail + "'>\n" +
                "    </div>\n" +
                "    <div>\n" +
                "      <input id='topic' type='text' name='topic' placeholder='Topic' value='" + topic + "'>\n" +
                "    </div>\n" +
                "    <div>\n" +
                "      <textarea name='content' rows='10' maxlength='1500' placeholder='Content'>" + cont + "</textarea>\n" +
                "    </div>\n" +
                "    <br>\n" +
                "    <input id='submit' type='submit' value='Submit'>\n" +
                "  </form>\n" +
                "</div>\n");

    footer(out);
  }
}