package KeyWord;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sunning on 2016/12/1.
 */
public class KeyWord {
    private String headerstr, pagestr;
    String strpage = "";

    public KeyWord(String url) throws IOException {

        HtmlParser a = new HtmlParser(url);
        ArrayList<String> hrefList = a.getHrefList();

        HtmlKeyword hk = new HtmlKeyword();
        headerstr = hk.GettitleKeyword(a.htmlpage);  //title里的关键词
        pagestr = hk.GetPageKeyword(a.htmlpage);  //网页中文
        Getword gw = new Getword();
        gw.keyWord = pagestr;

        try {
            String[] keywords = gw.getKeyWords(gw.keyWord);
            for (int i = 0; i < keywords.length; i++) {
                strpage = strpage + keywords[i] + " ";
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        strpage = headerstr + strpage;
    }

    public String getKeyWord() {
        return strpage;
    }

    public void writeFile() {
        WriteStringToTxt wtxt = new WriteStringToTxt();
        String file = "/Users/sunning/" + headerstr + ".txt";
        wtxt.WriteStringToFile(file, strpage);
    }
}
