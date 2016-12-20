package CutWord;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        HtmlParser a = new HtmlParser("http://baike.baidu.com/link?url=TgYxLQAHY4XdTbwqzozy21ZcAWQfW10fklvp666NAuj-TQP_238dKSCTyDfbnpFA2-bj7JTX6wPEO8pOiOEv_OLH4W8Xbcd1gnFENzOm7R6aAZiFvLjhazoqkJCnqN1U");
        ArrayList<String> hrefList = a.getHrefList();
        //System.out.println(a.htmlpage);
        HtmlContent hc = new HtmlContent();
        String content = hc.GetPageKeyword(a.htmlpage);
       // System.out.println(a.htmlpage);
        CutWord ct = new CutWord();
        String word = ct.cutword(content);
        System.out.println(word);
        String filepath ="/Users/sunning/IdeaProjects/Liyang/1.txt";
        try {
            FileOutputStream fos = new FileOutputStream(filepath);
            fos.write(word.getBytes("gbk"));
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ReadFile rf = new ReadFile();
        String strPath = "/Users/sunning/IdeaProjects/Liyang/";
        List<File> filelist = rf.getFileList(strPath);
        rf.getWord_TF(filelist,strPath);
        String keyword = rf.comput_keyword();  //每个文件内部关键词的tf-idf值
        System.out.println(keyword);  //keyword就是排序后的关键词，空格分隔的
    }
}