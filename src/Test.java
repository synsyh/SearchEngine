import java.io.IOException;
import java.util.ArrayList;

public class Test {
        public static void main(String[] args) throws IOException {
            HtmlParser a = new HtmlParser("http://baike.baidu.com/link?url=i4GaKR6Pc1MkSrg0vlV7aBX2VCSepOXTDc5vIsx4gliY1JhWpGAmx16Dq-hfqrQ2XypGzoUdN-vsPJIMY2wp-YNrQekM7rZMz5jloks8CEWWk2eo_nkryLiYP3FKh25Y");
            ArrayList<String> hrefList = a.getHrefList();
            String headerstr, pagestr;
            HtmlKeyword hk=new HtmlKeyword();
            headerstr=hk.GettitleKeyword(a.htmlpage);  //title里的关键词
            pagestr=hk.GetPageKeyword(a.htmlpage);  //网页中文
            String strpage="";
            Getword gw=new Getword();
                gw.keyWord=pagestr;
                try {
                    String [] keywords = gw.getKeyWords(gw.keyWord);
                    for(int i=0; i<keywords.length; i++){
                        strpage=strpage+keywords[i]+" ";
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            strpage=headerstr+strpage;
            WriteStringToTxt wtxt = new WriteStringToTxt();
            String file = "e:/"+headerstr+".txt";
            wtxt.WriteStringToFile(file,strpage);
            }
        }





