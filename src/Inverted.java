import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunning on 2016/12/20.
 */

//TODO:将已经爬出来的网页链接URL和关键字插入倒排表，需要传进来URL和该URL的关键字的List数组
public class Inverted {
    String URL;
    List<String> keyword = new ArrayList<String>();

    Connection conn;
    String sql;
    ResultSet rs;
    Statement stmt;
    PreparedStatement pstmt = null;

    public Inverted(String URL, List<String> keyword, Connection conn) {

        this.URL = URL;
        this.keyword = keyword;
        this.conn = conn;
        try {
            addItem();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addItem() throws SQLException {
        for (String tmp : keyword) {
            sql = "USE crawler";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            //查找该关键字在数据库中，是否已经添加过了这条URL
            sql = "SELECT URL FROM inverted WHERE keyword = '" + tmp + "'";
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                System.out.println(rs.getString(1));
                //如果存在
                if (rs.getString(1).equals(URL)) {
                    continue;
                } else {
                    //如果不存在则插入
                    sql = "INSERT INTO inverted (keyword, URL) VALUES ('" + tmp + "','" + URL + "')";
                    pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    pstmt.execute();
                    System.out.println(URL);
                    System.out.println(keyword);

                }
            } else {
                System.out.println("there is no such item");
            }
        }
    }
}
