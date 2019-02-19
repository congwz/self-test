import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Congwz on 2019/2/1.
 */
public class ElasticSearchDemoTest {

    //根据index、type、id从es中查询数据
    @Test
    public void findDataByIndexTypeAndId() {
        //指定ES集群
        Settings settings = Settings.builder().put("cluster.name","my-application").build();

        TransportClient client = null;
        //创建访问es服务器的客户端
        try {
            client = new PreBuiltTransportClient(settings)
                                     .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //根据index、type、id查询数据
        /* 如同Kibana（http://localhost:5601） Dev Tools中输入：
        GET lagou/work/1
        * */
        GetResponse response = client.prepareGet("lagou","work","3").execute().actionGet();
        //得到查询出的数据
        String json = response.getSourceAsString();
        if (null != json) {
            System.out.println(json);
        } else {
            System.out.println("未查询到任何结果！");
        }

        //关闭客户端
        client.close();
    }
}
