import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Congwz on 2019/2/15.
 */
public class test {

    @Test
    public void self() {
        /**
         String pattern = "yyyyMMddHHmmss";
         DateFormat df = new SimpleDateFormat(pattern);

         Date now = new Date();
         long getTime = now.getTime();
         System.out.println(getTime); //1551341546898
         System.out.println(df.format(now)); //20190228161226 2019-02-28 16:12:26


         //long time = 10*60*1000;   //10分钟
         long time = 24*60*60*1000; //一天
         Date beforeDate = new Date(now.getTime() - time);
         System.out.println(beforeDate.getTime()); //1551340946898
         System.out.println(df.format(beforeDate)); //20190228160226 2019-02-28 16:02:26
         **/

        //逗号分割字符串，返回结果为字符串数组
        String t = "title,name,age";
        String[] res = t.split(",");
        List<String> list = new ArrayList<String>(Arrays.asList(res));  //数组转化为List
        list.forEach(item -> {
            System.out.println(item);
        });
        //System.out.println(list.toString());

    }
}
