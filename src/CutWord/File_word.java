package CutWord;

import java.util.HashMap;

/**
 * Created by SAMSUNG on 2016/12/14.
 */
public class File_word {
    private String content;  //文章对应的内容以及哈希表，存放词语词频的属性
    private HashMap<String, Integer> hm;

    public File_word(String content ,HashMap<String, Integer> hm ){
        this.content=content;
        this.hm=hm;
    }

    public String getContent(){
        return this.content;
    }

    public HashMap<String, Integer> getHm(){
        return this.hm;
    }

}
