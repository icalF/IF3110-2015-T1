import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class search extends Page
{ 
  private String name;
  private String mail;
  private String vote;
  private String topic;
  private String datetime;
  private String isi;
  private String id;

  public search() throws Throwable {}

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    header(out);
    out.println("<div class='searchbar' align='center'>\n" +
                "  <tr>\n" +
                "    <form method='get' action='search'><td>\n" +
                "      <input type='text' name='q' size='89'>\n" +
                "    </td> \n" +
                "    <td>\n" +
                "      <input type='submit' value='Search'>\n" +
                "    </td></form>\n" +
                "  </tr>\n" +
                "</div>\n" +
                "<div align='center'>Cannot find what you are looking for? <a href='edit'>Ask here</a></div><br>\n" +
                "<div id='recent-question'><h3>Search results :</h3></div>");

    String q = request.getParameter("q");
    String query = "SELECT * FROM question WHERE topik LIKE '%" + q + "%' OR isi LIKE '%" + q + "%'";

    try {
      ResultSet rs;
      rs = dbms.exec(query);
      if (rs.next())
      {
        do {
          id = rs.getString("Id");
          name = rs.getString("username");
          mail = rs.getString("email");
          topic = rs.getString("topik");
          vote = rs.getString("vote");
          datetime = rs.getString("datetime");
          isi = rs.getString("isi");

          // handling special chars
          isi = isi.replaceAll("\n", "<br>");
          isi = isi.replaceAll("\t", "&nbsp;&nbsp");

          int ans = 0;
          try {
            query = "SELECT COUNT(*) FROM answer WHERE qid = " + id;

            ResultSet tmp = dbms.exec(query);
            tmp.next();
            ans = tmp.getInt("COUNT(*)");
          } catch (Exception e) {}

          out.println("<a href='view?id=" + id + "'>\n" +
                      "<div class='question-summary'>\n" +
                      "  <div class='votes-counter'>\n" +
                      "    <div class='votes-counter-num'>" + vote + "</div>\n" +
                      "    <div>Votes</div>\n" +
                      "  </div>\n" +
                      "  <div class='answers-counter'>\n" +
                      "    <div class='answers-counter-num'>" + ans + "</div>\n" +
                      "    <div>Answers</div>\n" +
                      "  </div>\n" +
                      "  <div class='question-text'>\n" +
                      "    <div class='mini-title'><b>" + topic + "</b></div>\n" +
                      "    <div>" + isi + "</div>\n" +
                      "    <div class='question-time-menu'>\n" +
                      "      <div class='question-menu'>\n" +
                      "        | <a href='edit?id=" + id + "' id='edit-menu'>edit</a>\n" +
                      "        | <a href='delete?id=" + id + "' onclick='return deleteConfirm()' id='delete-menu'>delete</a>\n" +
                      "      </div>\n" +
                      "      <div class='author-info'>\n" +
                      "        oleh <a href='mailto:" + mail + "'>" + name + "</a> pada " + datetime + "\n" +
                      "      </div>\n" +
                      "    </div>\n" +
                      "  </div>\n" +
                      "</div></a>");
        } while (rs.next());
      }
      else {
        out.println("<br><h3 align='center'>Question not found</h3>");
      }
    } 
    catch (Throwable e) {
      e.printStackTrace();
    }

    footer(out);
  }
}
