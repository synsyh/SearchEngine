import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunning on 2016/12/20.
 */
public class InvertedTest {
    public static void main(String[] args) {
        String frontpage = "http://lavasoft.blog.51cto.com/";
        String url = frontpage;
        Connection conn = null;
        Datar datar = new Datar();
        conn = datar.connectDB(conn);
        List<String> keyword=new ArrayList<String>();
        keyword.add("熔岩");
        Inverted inverted=new Inverted("http://lavasoft.blog.51cto.com/rss.php?uid=62575/",keyword,conn);
    }
}
