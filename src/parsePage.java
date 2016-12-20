import java.sql.Connection;

import KeyWord.KeyWord;
import PageRank.PageRank;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import java.util.ArrayList;
import java.util.List;

public class parsePage {
    public static List<String> parseFromString(String url, String content, Connection conn) throws Exception {
        //TODO:网页权重
        String linkUrl;
//        cpOutput output = new cpOutput("/Users/sunning/IdeaProjects/Liyang/src/pagerank.txt");
        String mainurl = "http://lavasoft.blog.51cto.com/";
        List<String> list = new ArrayList<String>();
        String kd;
        float[] pageRank;
        KeyWord keyWord = null;
        Datar datar = new Datar();
        list.add(url);

        Parser parser = new Parser(content);
        HasAttributeFilter filter = new HasAttributeFilter("href");
        NodeList alllist = parser.parse(filter);

        for (int i = 0; i < alllist.size(); i++) {
            Node node = alllist.elementAt(i);
            if (node instanceof LinkTag) {
                LinkTag link = (LinkTag) node;
                String nextlink = link.extractLink();
                if (nextlink.startsWith(mainurl)) {
                    list.add(nextlink);
                    if (datar.isExists(conn, nextlink))
                        continue;
                    else {
                        kd = new KeyWord(nextlink).getKeyWord();
                        datar.insert(conn, nextlink, kd);
                    }
                }
            }
        }
//        pageRank=new PageRank(list).getPageRank();
        datar.update(conn, url);
        return list;
    }
}