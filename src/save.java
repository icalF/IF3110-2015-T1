import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class save extends Page
{ 
  private String name;
  private String mail;
  private String topic;
  private String cont;
  private String query;
  private int qid;

  public save() throws Throwable {}

  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
    name = request.getParameter("name");
    mail = request.getParameter("email");
    cont = request.getParameter("content");

    qid = Integer.parseInt(request.getParameter("id"));

    if (request.getParameter("ans") != null)
    {
      query = "INSERT INTO answer (qid, username, email, isi) VALUES (" + qid + ", '" + name + "', '" + mail + "', '" + cont + "')";
    } 
    else 
    {
      topic = request.getParameter("topic");
      if (qid < 0) 
      {
        query = "INSERT INTO question (username, topik, email, isi) VALUES ('" + name + "', '" + topic + "', '" + mail + "', '" + cont + "')";
      } 
      else 
      {
        query = "UPDATE question SET username='" + name + "', topik='" + topic + "', email='" + mail + "', isi='" + cont + "' WHERE Id = " + qid + "";
      }
    }

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