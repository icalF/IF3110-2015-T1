import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class delete extends Page
{ 
  private String query;
  private String qid;

  public delete() throws Throwable {}

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
    qid = request.getParameter("id");
    query = "DELETE FROM question WHERE Id = " + qid;

    try {
      dbms.upd(query);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    
    query = "DELETE FROM answer WHERE qid = " + qid;

    try {
      dbms.upd(query);
    } catch (Throwable e) {
      e.printStackTrace();
    }

    response.setContentType("text/html");
    response.setStatus(response.SC_MOVED_TEMPORARILY);
    response.setHeader("Location", "/");
  }
}