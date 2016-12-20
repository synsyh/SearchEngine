package KeyWord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlParser {
    String htmlUrl;
    ArrayList<String> hrefList = new ArrayList();  //得到meta和title里面的内容
    String charSet;
    String htmlpage;  //全文内容

    public HtmlParser(String htmlUrl) {
        // TODO 自动生成的构造函数存根
        this.htmlUrl = htmlUrl;
    }

    public ArrayList<String> getHrefList() throws IOException {
        parser();
        return hrefList;
    }

    private void parser() throws IOException {
        URL url = new URL(htmlUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //int a=connection.getResponseCode();
        //System.out.println(a);
        connection.setDoOutput(true);
        //charSet ="gbk";
        charSet = "GBK";
        InputStreamReader isr = new InputStreamReader(
                connection.getInputStream(), charSet);
        BufferedReader br = new BufferedReader(isr);
        String str = null, rs = null, key = null;
        while ((str = br.readLine()) != null) {
            //传进去的str为整个网页html内容
            htmlpage = htmlpage + "\n" + str;
            rs = getHref(str);
            key = getKeyword(str);
            if (rs != null)
                hrefList.add(rs);
            if (key != null)
                hrefList.add(key);
        }
    }

    //让传进来的全文与要的标签匹配
    private String getHref(String str) {
        //Pattern pattern = Pattern.compile("<a href=.*</a>");
        // Pattern pattern = Pattern.compile("<img src=.*");
        Pattern pattern = Pattern.compile("<title>.*</title>");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find())
            return matcher.group(0);
        return null;
    }

    private String getKeyword(String str) {
        Pattern pattern = Pattern.compile("<meta name=.*>");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find())
            return matcher.group(0);
        return null;
    }
}

