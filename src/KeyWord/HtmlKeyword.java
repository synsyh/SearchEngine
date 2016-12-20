package KeyWord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlKeyword {
    public String GetHeaderKeyword(ArrayList<String> hrefList) {
        String res = "";
        for (int i = 0; i < hrefList.size(); i++) {
            String s = hrefList.get(i);
            String regex = "[\\u4e00-\\u9fa5，。！、……\\d]+";   //匹配中文得到网页关键字
            Matcher matcher = Pattern.compile(regex).matcher(hrefList.get(i));
            if (matcher.find()) {
                s = matcher.group(0);
            }
            res = res + s;
        }
        return res;
    }


        public String GettitleKeyword(String HtmlPage) {
            String[] sourceStrArray = HtmlPage.split("\n");//分割出来的字符数组
            String title = "";
            String keyword = "";
            for (int i = 0; i < sourceStrArray.length; i++) {
                Pattern pattern = Pattern.compile("<title>.*</title>"); //匹配title标签
                Matcher matcher = pattern.matcher(sourceStrArray[i]);
                if (matcher.find()) {
                    title = matcher.group(0);
                    title = title.replace("<title>", "");
                    keyword = keyword + title.replace("</title>", "");
                }
            }
            return keyword;
        }

    public String GetPageKeyword(String HtmlPage) {
        String res = "";
        String[] sourceStrArray = HtmlPage.split("\n");//分割出来的字符数组
        for (int i = 0; i < sourceStrArray.length; i++) {
            String s="";
            // System.out.println(sourceStrArray[i]);
            String regex = "[\\u4e00-\\u9fa5，。！、……\\d]+";   //匹配中文得到网页关键字
            Matcher matcher = Pattern.compile(regex).matcher(sourceStrArray[i]);
            if (matcher.find()) {
                s = matcher.group(0);
            }
            res=res+s;
        }
        return res;
    }
}
