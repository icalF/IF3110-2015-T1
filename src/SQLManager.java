import java.sql.*;

public class SQLManager {
  final private String SRV = "jdbc:mysql://localhost/";
  final private String USR = "root";
  final private String PWD = "";
  final private String DB = "stackexchange";
  private Connection conn;
  private Statement stmt;

  public SQLManager() throws Throwable {
    Class.forName("com.mysql.jdbc.Driver");   // register JDBC driver
    conn = DriverManager.getConnection(SRV+DB, USR, PWD);  // open connection
    stmt = conn.createStatement();    // initialize statement for query
  }

  public ResultSet exec(String query) throws Throwable {
    return stmt.executeQuery(query);
  }

  public int upd(String query) throws Throwable {
    return stmt.executeUpdate(query);
  }

  protected void finalize() throws Throwable {
    stmt.close();       // close statement
    conn.close();       // close connection
    super.finalize();
  }
}