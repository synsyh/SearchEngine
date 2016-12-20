package CutWord;

/**
 * Created by SAMSUNG on 2016/12/14.
 */
public class Word {
    private String content;
    private double tf_idf_value;

    public Word(){}
    public Word(String word, double tf_idf_value){
        this.content=word;
        this.tf_idf_value=tf_idf_value;
    }

    public double getTf_idf_value(){return this.tf_idf_value;}
    public void setTf_idf_value(double x){this.tf_idf_value=x;}
    public String getContent(){return this.content;}
}
