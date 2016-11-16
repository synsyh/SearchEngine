/**
 * 截取一片文章中频繁出现的关键字，并给予分组排序（倒叙），以数组格式返回n个关键字
 * 并该类内部含有一个List2Map方法，可将重复<String>集合转换为Map<String, Integer>格式
 * 并算出该<String>重复次数，放入相应的value中
 */
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Getword {
    public String keyWord;
    private  Integer NUM=20;
    private  Integer QUANTITY=1;  //截取关键字在几个单词以上的数量

//传入String类型的文章，智能提取单词放入list中
    public  void Getword(String keyWord){
        this.keyWord=keyWord;
        this.NUM=30;
        this.QUANTITY=1;
    }

    public List<String> extract(String article) throws IOException {
        Integer a=this.QUANTITY;
        List<String> list =new ArrayList<String>(); //定义一个list来接收将要截取出来单词
        IKAnalyzer analyzer = new IKAnalyzer(); //初始化IKAnalyzer
        analyzer.setUseSmart(true); //将IKAnalyzer设置成智能截取
        TokenStream tokenStream= //调用tokenStream方法(读取文章的字符流)
                analyzer.tokenStream("", new StringReader(article));
        while (tokenStream.incrementToken()) { //循环获得截取出来的单词
            CharTermAttribute charTermAttribute = //转换为char类型
                    tokenStream.getAttribute(CharTermAttribute.class);
            String keWord= charTermAttribute.toString(); //转换为String类型
            if (keWord.length()>a) { //判断截取关键单词以上)
                list.add(keWord); //将最终获得的单词放入list集合中字在几个单词以上的数量(默认为2个
            }
        }
        return list;
    }

    //将list中的集合转换成Map中的key，value为数量默认为1
    private  Map<String, Integer> list2Map(List<String> list){
        Map<String, Integer> map=new HashMap<String, Integer>();
        for(String key:list){           //循环获得的List集合
            if (list.contains(key)) { //判断这个集合中是否存在该字符串
                map.put(key, map.get(key) == null ? 1 : map.get(key)+1);
            } //将集中获得的字符串放在map的key键上
        } //并计算其value是否有值，如有则+1操作
        return map;
    }
    /**
     * 提取关键字方法
    */
    public  String[] getKeyWords(String article,Integer a,Integer n) throws IOException {
        List<String> keyWordsList= extract(article); //调用提取单词方法
        Map<String, Integer> map=list2Map(keyWordsList); //list转map并计次数
        //使用Collections的比较方法进行对map中value的排序
        ArrayList<Entry<String, Integer>> list = new ArrayList<Entry<String,Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        if (list.size()<n) n=list.size(); //排序后的长度，以免获得到null的字符
        String[] keyWords=new String[n]; //设置将要输出的关键字数组空间
        for(int i=0; i< list.size(); i++) { //循环排序后的数组
            if (i<n) {                       //判断个数
                keyWords[i]=list.get(i).getKey(); //设置关键字进入数组
            }
        }
        return keyWords;
    }

    public  String[] getKeyWords(String article) throws IOException{
        return getKeyWords(article,this.QUANTITY,this.NUM);
    }

//    public static void main(String[] args) {
//       String keyWord = "软件工程是一门研究用工程化方法构建和维护有效的、实用的和高质量的软件的学科。它涉及程序设计语言、数据库、软件开发工具、系统平台、标准、设计模式等方面。\n" +
//                "在现代社会中，软件应用于多个方面。典型的软件有电子邮件、嵌入式系统、人机界面、办公套件、操作系统、编译器、数据库、游戏等。同时，各个行业几乎都有计算机软件的应用，如工业、农业、银行、航空、政府部门等。这些应用促进了经济和社会的发展，也提高了工作效率和生活效率 ";
//
//        Getword gw=new Getword();
//        gw.keyWord=keyWord;
//        try {
//            String [] keywords = gw.getKeyWords(gw.keyWord);
//            for(int i=0; i<keywords.length; i++){
//                System.out.println(keywords[i]);
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}