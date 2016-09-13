package win.liumian.stock.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by liumian on 16/8/8.
 */
public class CodeSingleton {

    private static Logger logger = Logger.getLogger(CodeSingleton.class);

    private String[] codes;

    private CodeSingleton(String fileName){
        try {


            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while((line = br.readLine())!= null){
                sb.append(line);
            }
            this.codes =  sb.toString().split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String[] getCodes(){
        return codes;
    }

}
