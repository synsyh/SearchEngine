import KeyWord.KeyWord;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Sunning on 2016/10/12.
 */

public class Crawler {
    public static void main(String args[]) throws Exception {
        List<String> pageLink;
        /*爬取的网页内容*/
        String responseBody;
        String frontpage = "http://lavasoft.blog.51cto.com/";
        String url = frontpage;
        Connection conn = null;
        Datar datar = new Datar();
        conn = datar.connectDB(conn);
        KeyWord keyWord = new KeyWord(frontpage);
        String kw = keyWord.getKeyWord();

        int count = 0;

        if (conn != null) {
            datar.init(conn);
            datar.insert(conn, frontpage, kw);
            while (true) {
                responseBody = httpGet.getByString(url, conn);
                pageLink = parsePage.parseFromString(url, responseBody, conn);
                url = datar.findNextLink(conn);
                if (url.isEmpty())
                    break;
            }
        }
        conn.close();
        System.out.println("Done.");
        System.out.println(count);
    }
}