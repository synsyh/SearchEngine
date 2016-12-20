package CutWord;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import java.io.IOException;
import java.io.StringReader;

public class CutWord {
    public String cutword(String content)throws IOException{
       // String text="基于java语言开发的轻量级的中文分词工具包";
        StringReader sr=new StringReader(content);
        IKSegmenter ik=new IKSegmenter(sr, true);
        Lexeme lex=null;
        String word ="";
        while((lex=ik.next())!=null){
            word = word+lex.getLexemeText()+" ";
        //    System.out.print(lex.getLexemeText()+" ");
            if((lex=ik.next())==null) break;
        }
        return word;
    }

//    public static void main(String[] args) throws IOException {
//        String text="基于java语言开发的轻量级的中文分词工具包";
//        StringReader sr=new StringReader(text);
//        IKSegmenter ik=new IKSegmenter(sr, true);
//        Lexeme lex=null;
//        while((lex=ik.next())!=null){
//            System.out.print(lex.getLexemeText()+" ");
//        }
//    }
}
