import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class vote extends Page
{ 
  private String query;
  private String val;
  private String dbname;
  private String id;

  public vote() throws Throwable {}

  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
    val = request.getParameter("val");
    id = request.getParameter("id");
    dbname = request.getParameter("dbname");

    query = "UPDATE " + dbname + " SET vote = " + val + " WHERE Id = " + id + "";

    try {
      dbms.upd(query);
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }
}