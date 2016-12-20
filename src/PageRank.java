import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunning on 2016/12/20.
 */

public class PageRank {
    Connection conn;
    String sql;
    ResultSet rs;
    Statement stmt;
    PreparedStatement pstmt = null;

    List<String> keywords = new ArrayList<String>();
    List<String> URLs = new ArrayList<String>();
    int[] pr;

    public PageRank(Connection conn) throws SQLException {
        this.conn = conn;
        sql = "USE crawler";
        stmt = conn.createStatement();
        stmt.executeUpdate(sql);

        sql = "SELECT DISTINCT keyword FROM `inverted`";
        stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            keywords.add(rs.getString("keyword"));
        }
        for (String keyword : keywords) {
            sql = "SELECT URL FROM `inverted` WHERE keyword='" + keyword + "'";
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                URLs.add(rs.getString("URL"));
            }
            //TODO:pagerank,返回一个对应URL的pr值数组
            pr = new int[URLs.size()];
            for (int i = 0; i < URLs.size(); i++) {
                pr[i] = i;
            }

            for (int j = 0; j < pr.length; j++) {
                sql = "UPDATE inverted SET pagerank = " + pr[j] + " WHERE keyword = '" + keyword + "'";
                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.execute();
            }
        }
    }
}
