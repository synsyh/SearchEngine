import java.sql.*;

/**
 * Created by sunning on 2016/12/15.
 */
public class Datar {

    String sql = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;

    ResultSet rs;

    public Connection connectDB(Connection conn) {
        try {
            //加载sql数据库
            Class.forName("com.mysql.jdbc.Driver");
            String dburl = "jdbc:mysql://localhost:3306?useUnicode=true&characterEncoding=utf8";
            conn = DriverManager.getConnection(dburl, "root", "");
            System.out.println("connection built");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void init(Connection conn) {

        try {
            sql = "CREATE DATABASE IF NOT EXISTS crawler";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            sql = "USE crawler";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            sql = "create table if not exists record (recordID int(5) not null auto_increment, URL text , keyword text ,outdegree int(3) ,crawled tinyint(1), primary key (recordID)) engine=InnoDB DEFAULT CHARSET=utf8";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            sql = "create table if not exists inverted (keywordID int(5) not null auto_increment, keyword text , URL text, primary key (keywordID)) engine=InnoDB DEFAULT CHARSET=utf8";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isExists(Connection conn, String url) {
        sql = "SELECT * FROM record WHERE URL = '" + url + "'";
        try {
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                System.out.println("exists");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void insert(Connection conn, String url, String keyword) {
        sql = "SELECT * FROM record WHERE URL = '" + url + "'";
        try {
            sql = "INSERT INTO record (URL,keyword, crawled) VALUES ('" + url + "','" + keyword + "',0)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.execute();
            System.out.println(url);
            System.out.println(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Connection conn, String url) {
        try {
            sql = "UPDATE record SET crawled = 1 WHERE URL = '" + url + "'";
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String findNextLink(Connection conn) {
        String url = null;
        try {
            sql = "SELECT * FROM record WHERE crawled = 0";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                url = rs.getString(2);
            } else {
                //stop crawling if reach the bottom of the list
                System.out.println("there is the end of the linklist");
                url = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
