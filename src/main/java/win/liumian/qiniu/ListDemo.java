package win.liumian.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liumian on 16/9/3.
 */
public class ListDemo {

    public static void main(String args[]){
        //设置需要操作账号的AK和SK
        String ACCESS_KEY = "ro5ATx5y-fJgq8rYgIgjnGF2UA_isd60kSl9a64Q";
        String SECRET_KEY = "8atGx_SPqThPELSeuT73Psu-Po_PIgQ7-nh5lrfQ";
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

        //实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth);

        //要列举文件的空间名
        String bucket = "liumian";

        try {
            //调用listFiles方法列举指定空间的指定文件
            //参数一：bucket    空间名
            //参数二：prefix    文件名前缀
            //参数三：marker    上一次获取文件列表时返回的marker
            //参数四：limit     每次迭代的长度限制，最大1000，推荐值100
            //参数五：delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
            FileListing fileListing = bucketManager.listFiles(bucket,null,null,100,null);
            FileInfo[] items = fileListing.items;

            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(FileInfo fileInfo:items){
                System.out.println(fileInfo.key+" - "+fileListing.marker+" - "+ format.format(new Date(fileInfo.putTime)));
            }
        } catch (QiniuException e) {
            //捕获异常信息
            Response r = e.response;
            System.out.println(r.toString());
        }
    }

}
