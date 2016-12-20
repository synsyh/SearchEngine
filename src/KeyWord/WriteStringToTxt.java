package KeyWord;

import java.io.FileOutputStream;

public class WriteStringToTxt {

    public void WriteStringToFile(String filePath, String s) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(s.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}