import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Page extends HttpServlet 
{
  protected SQLManager dbms;

  public Page() throws Throwable
  { dbms = new SQLManager(); }

  public void header(PrintWriter outResponse)
  {
    String title = "Simple Stack Exchange";
    String docType = "<!DOCTYPE html>\n";

    outResponse.println(docType +
              "<html>\n"+
              "<head>\n"+
              "  <meta charset='utf-8'>\n"+
              "  <meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"+
              "  <title>Simple Stack Exchange</title>\n"+
              "  <script src='js/main.js'></script>\n"+
              "  <link rel='stylesheet' type='text/css' href='css/main.css'>\n"+
              "</head>\n"+
              "<body>\n"+
              "<div class='title-box'>\n"+
              "<a href = '/'>\n"+
              "  <h1>Simple Stack Exchange</h1><br>\n"+
              "</a>\n"+
              "</div>");
  }

  public void footer(PrintWriter outResponse) 
  {
    outResponse.println("</body>\n</html>\n");
  }
}