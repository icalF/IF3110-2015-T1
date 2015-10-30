import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class view extends Page
{ 
  private String name;
  private String mail;
  private String vote;
  private String topik;
  private String datetime;
  private String isi;
  private String id;
  private String qid;

  public view() throws Throwable {}

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    header(out);

    qid = request.getParameter("id");
    String query = "SELECT * FROM question WHERE Id = " + qid;

    ResultSet rs;
    try {
      rs = dbms.exec(query);
      rs.next();

      topik = rs.getString("topik");
      name = rs.getString("username");
      datetime = rs.getString("datetime");
      mail = rs.getString("email");
      vote = rs.getString("vote");
      isi = rs.getString("isi").replaceAll("\n", "<br>");
      isi = isi.replaceAll(" ", "&nbsp;");
      isi = isi.replaceAll("\t", "&nbsp;&nbsp;");

      out.println(
      "<div class='question-details'>\n" +
      "  <div class='question-title'>\n" +
      "    <h3>" + topik + "</h3>\n" +
      "  </div>\n" +
      "  <div class='wrapper'>\n" +
      "    <div class='vote'>\n" +
      "      <div class='arrow-up' onclick='voter(1, false, " + qid + ", \"votenum-" + qid + "\")'></div>\n" +
      "      <div class='vote-number' id='votenum-" + qid + "'>" + vote + "</div>\n" +
      "      <div class='arrow-down' onclick='voter(-1, false, " + qid + ", \"votenum-" + qid + "\")'></div>\n" +
      "    </div>\n" +
      "    <div class='question-content'>\n<div>" +
            isi + "</div>\n" +
      "      <div class='author-info'>\n" +
      "        <div class='question-menu'>\n" +
      "          | <a href='edit.php?id=" + qid + "' id='edit-menu'>edit</a>\n" +
      "          | <a href='delete.php?id=" + qid + "' onclick='return deleteConfirm()' id='delete-menu'>delete</a>\n" + 
      "        </div>\n" +
      "        <div>asked by <a href='mailto:" + mail + "'>" + name + "</a> at " + datetime + "</div>\n" +
      "      </div>\n" +
        
      "  </div>\n" +
      "</div>\n" + 
      "<div class='answer-list'>");
    } catch (Throwable e) {
      e.printStackTrace();
    }

    query = "SELECT * FROM answer WHERE qid = " + qid + " ORDER BY vote DESC";

    try {
      rs = dbms.exec(query);
      if (rs.next())
      {
        out.println("<div style='border-bottom: solid 2px;'><h3>Answers :</h3></div>");

        do {
          id = rs.getString("Id");
          name = rs.getString("username");
          mail = rs.getString("email");
          datetime = rs.getString("datetime");
          isi = rs.getString("isi");
          vote = rs.getString("vote");

          // handling special chars
          isi = isi.replaceAll("\n", "<br>");
          isi = isi.replaceAll("\t", "&nbsp;&nbsp");

          out.println(
          "    <div class='wrapper' id='answer-box'>\n" +
          "      <div class='vote'>\n" +
          "        <div class='arrow-up' onclick='voter(1, true, " + id + ", \"votenum-" + qid + "-" + id + "\")'></div>\n" +
          "        <div class='vote-number' id='votenum-" + qid + "-" + id + "'>" + vote + "</div>\n" +
          "        <div class='arrow-down' onclick='voter(-1, true, " + id + ", \"votenum-" + qid + "-" + id + "\")'></div>\n" +
          "      </div>\n" +
          "      <div class='question-content'>\n" +
                  isi + "\n" +
          "        <div class='author-info'>answered by <a href='mailto:" + mail + "'>" + name + "</a> at " + datetime + "</div>\n" +
          "      </div>\n" +
          "    </div>");
        } while (rs.next());
      }
      else {
        out.println("<br><div class='not-available'><b>There is no answer so far</b><br>\n" +
        "Do you want to answer?</div><br>");
      }
    } 
    catch (Throwable e) {
      e.printStackTrace();
    }

    out.println(
    "  <div class='thread-editor'>\n" +
    "    <form name='myform' method='post' action='save.php' onsubmit='return validateForm()'>\n" +
    "      <br><h3>Your answer :</h3>\n" +
    "      <input type='hidden' name='id' value=" + qid + ">\n" +
    "      <input type='hidden' name='ans' value=1>\n" +
    "      <div>\n" +
    "        <input type='text' name='name' placeholder='Name'>\n" +
    "      </div>\n" +
    "      <div>\n" +
    "        <input type='text' name='email' placeholder='Email'>\n" +
    "      </div>\n" +
    "      <div>\n" +
    "        <textarea name='content' id='content' rows='10' maxlength='1500' placeholder='Content'></textarea>\n" +
    "      </div>\n" +
    "      <br>\n" +
    "      <input id='submit' type='submit' value='Submit'>\n" +
    "    </form>\n" +
    "  </div>\n" +
    "</div>");
    footer(out);
  }
}
