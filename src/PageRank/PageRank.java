package PageRank;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by sunning on 2016/12/15.
 */
public class PageRank {
    List<String> linesarr = new ArrayList<String>();
    float[] pageRank;

    public PageRank(List<String> linesarr) {
        this.linesarr = linesarr;
        Hashtable<String, Integer> docIDandNum = new Hashtable<String, Integer>();
        int total = linesarr.size();
        int father, son;
        int outdegree = 0;

        // 读取文件，得到docid，计算链接总数total，outdegree在迭代的时候计算
        if (linesarr.size() > 0) {
            outdegree = linesarr.size() - 1;
            for (int j = 1; j <= linesarr.size() - 1; ++j) {
                if (linesarr.get(j).equals(linesarr.get(0)))
                    outdegree--;
            }
            if (linesarr.get(0) != null) {
                docIDandNum.put(linesarr.get(0), total);
                System.out.println("链接" + linesarr.get(0) + "的出度为" + outdegree);
            }
        }
        linesarr = new ArrayList<String>();

        System.out.println("链接总数为：" + total);

        if (total > 0)

        {
            // pageRank[]存放PR值
            float[] pageRank = new float[total + 1];

            // 链入页面的计算总和
            float[] prTmp = new float[total + 1];

            // 设置pageRank[]初始值为1.0f
            for (int i = 1; i <= total; ++i) {
                pageRank[i] = 1.0f;
                prTmp[i] = 0.0f;
            }

            // 当前页面的PR值
            float fatherRank = 1f;

            // 阻尼系数d或称为alpha
            float alpha = 0.85f;

            // 进行10次迭代
            for (int iterator = 0; iterator < 10; iterator++) {
                long startTime = System.currentTimeMillis();


                if (linesarr.size() > 0) {
                    outdegree = linesarr.size() - 1;
                    for (int j = 1; j <= linesarr.size() - 1; ++j) {
                        // 指向自身的链接无效，不计算在内
                        if (linesarr.get(j).equals(linesarr.get(0)))
                            outdegree--;
                    }
                }
                if (outdegree > 0) {
                    father = (int) docIDandNum.get(linesarr.get(0));
                    // 对应公式中的pr(Ti)/c(Ti),Ti为指向father的页面
                    fatherRank = pageRank[father] / outdegree;
                    for (int k = 1; k <= linesarr.size() - 1; ++k) {
                        if (linesarr.get(0).equals(linesarr.get(0))) {
                            continue;
                        }
                        son = docIDandNum.get(linesarr.get(k));
                        if (total >= son && son >= 0) {
                            prTmp[son] += fatherRank;
                        }
                    }
                }

                // 准备下次迭代的初始值
                for (int i = 1; i <= total; ++i) {
                    // PR公式1
                    prTmp[i] = 0.15f + alpha * prTmp[i];

                    // PR公式2
                    prTmp[i] = 0.15f / total + alpha * prTmp[i];

                    // 每次迭代后的真正pr值
                    pageRank[i] = prTmp[i];

                    prTmp[i] = 0.0f;
                }

                long endTime = System.currentTimeMillis();
                System.out.println("第" + iterator + "次迭代耗时" + (endTime - startTime) + "ms");
            }

            for (float i : pageRank) {
                System.out.println(i);
                pageRank = null;
                prTmp = null;
            }
        }
    }

    public float[] getPageRank() {
        return pageRank;
    }
}
