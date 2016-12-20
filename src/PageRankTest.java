import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by sunning on 2016/12/20.
 */
public class PageRankTest {
    public static void main(String[] args) {
        Connection conn = null;
        Datar datar = new Datar();
        conn = datar.connectDB(conn);
        try {
            PageRank pageRank=new PageRank(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
