/**
 * Created by lango on 30.05.2017.
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Redirect extends HttpServlet{

    private static final String url = "jdbc:postgresql://dev.gracelogic.com:37402/test";
    private static final String user = "test";
    private static final String password = "test";

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException {
        try{
            int count=0;
            DriverManager.registerDriver(new org.postgresql.Driver());
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from counter");
            while (rs.next())
                count = rs.getInt("count");
            count++;
            stmt.executeUpdate("update counter set count=" + count);
            con.close();
            req.setAttribute("current_count", count);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } catch (SQLException ex) {}

    }
}