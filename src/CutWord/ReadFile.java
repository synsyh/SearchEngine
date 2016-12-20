package CutWord;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ReadFile {
    List<File_word> nf = new ArrayList<>();  //存放目录中的所有文件
    int n=0;
    public List<File> getFileList(String strPath) {    //读取目录下的所有文件
        List<File> filelist = new ArrayList<>();
        File dir = new File(strPath);
        File[] files = dir.listFiles();  // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) {   // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath());   // 获取文件绝对路径
                } else if (fileName.endsWith("txt")) {   // 判断文件名是否以.txt结尾
                    String strFileName = files[i].getAbsolutePath();
                  //  System.out.println("---" + strFileName);
                    filelist.add(files[i]);
                } else {
                    continue;
                }
            }
        }
        return filelist;
    }

    public void getWord_TF(List<File> filelist,String filepath) throws IOException { //计算关键词的频率
        for (int i = 0; i < filelist.size(); i++) {
            InputStreamReader ips = new InputStreamReader(new FileInputStream(filelist.get(i).getAbsoluteFile()), "gbk"); //gb2312
         //   System.out.println("读取文件："+filelist.get(i).getAbsoluteFile());
            if(filelist.get(i).getAbsoluteFile().equals(filepath))
                n=i;
            BufferedReader file = new BufferedReader(ips);
            while (true) {
                if (file.lines() != null) {
                    String s = file.readLine();
                    if (s != null) {
                      //  System.out.println(s);
                        //文档内容
                        String[] keyword = s.split(" ");
                      //  System.out.println(keyword.length);
                        HashMap<String, Integer> hm = new HashMap<String, Integer>();
                        for (int j = 0; j < keyword.length; j++) {
                            Integer cou = hm.get(keyword[j]);
                            hm.put(keyword[j], cou == null ? 1 : cou + 1);
                        }
                        this.nf.add(new File_word(s,hm));
                       // System.out.println(hm);
                        Set<String> keys = hm.keySet();
                        for (String key : keys) {
                        //    System.out.println(key + "=" + hm.get(key));
                            //   count += hm.get(key);
                        }
                    } else
                        break;
                }
            }
        }
        //  System.out.println(count);
    }

    public double getword_idf(String word){  //计算关键词的逆文档频率
        int count=0;
        for(int i=0; i<this.nf.size(); i++){
            if(this.nf.get(i).getContent().contains(word))
                count++;
        }
        double idf = Math.log((double)count/this.nf.size());
        //System.out.println(word+"关键词的逆文档频率为"+idf);
        return idf;
    }

    public String comput_keyword()throws IOException{//计算每个文件里面关键词的重要程度，并且降序输出
      String keyword="";
        for(int k=0;k<this.nf.size(); k++)
        {
            List<Word> wordlist =  new ArrayList<>();
            //System.out.println("文件"+k+"的关键词按计算出来的tf_idf值排序：");
            Set<String> keys = this.nf.get(k).getHm().keySet();
            for (String key : keys) {
                double tf_idf_value = this.nf.get(k).getHm().get(key) * getword_idf(key);
                Word word = new Word(key,tf_idf_value);
                wordlist.add(word);
                //  System.out.println(key+"的tfidf值为："+this.nf.get(i).getHm().get(key) * getword_idf(key));
            }
            for(int i=0; i<wordlist.size(); i++){   //按照tf-tdf排序，结果还是存在wordlist
                for(int j=i; j<wordlist.size();j++){
                    if(wordlist.get(i).getTf_idf_value()<wordlist.get(j).getTf_idf_value()){
                        Word w = new Word();
                        w = wordlist.get(i);
                        wordlist.set(i,wordlist.get(j));
                        wordlist.set(j,w);
                    }
                }
            }
            if(k==n) {
                for(int n =0; n<20; n++) {
                     keyword = keyword + wordlist.get(n).getContent()+" ";
                }
            }
//            for(int n =0; n<wordlist.size(); n++){
//                System.out.println(wordlist.get(n).getContent()+ wordlist.get(n).getTf_idf_value());
//                if(n>20) break;
//            }
        }
        return keyword;
    }
}