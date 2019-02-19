import com.viverselftest.config.Redis;
import com.viverselftest.consts.ElasticSearchConstants;
import com.viverselftest.po.elasticsearch.EsLFPO;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Congwz on 2019/2/11.
 */
public class EsProjectTest {



    private TransportClient client;

    @Before
    public void getClient() throws Exception {
        //设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "my-application").build();// 集群名
        //创建client
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
    }

    @After
    public void closeClient() {
        if (null != client) {
            client.close();
        }
    }

    private List<EsLFPO> createLostData() {
        List<EsLFPO> list = new ArrayList<>();
        EsLFPO itemL1 = new EsLFPO();
        itemL1.setId("L1");
        itemL1.setTitle("寻猫启示");
        itemL1.setUrl("https://www.imooc.com/");
        itemL1.setContent("我家的是一只很可爱的猫，身形肉嘟嘟，指甲还未修剪，胆子很小怕生。我家的猫是一只很可爱的猫，身形肉嘟嘟，指甲还未修剪，我家的猫是一只很可爱的猫，身形肉嘟嘟，指甲还未修剪，我家的猫是一只很可爱的猫，身形肉嘟嘟，指甲还未修剪，我家的猫是一只很可爱的猫，身形肉嘟嘟，指甲还未修剪，我家的猫是一只很可爱的猫，身形肉嘟嘟，指甲还未修剪，我家的猫是一只很可爱的猫，身形肉嘟嘟，指甲还未修剪，我家的猫是一只很可爱的猫，身形肉嘟嘟，指甲还未修剪。");
        itemL1.setAddr("苏州");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        itemL1.setTime(sdf.format(new Date()));
        itemL1.setMoney("1000元");
        itemL1.setName("李先生");
        list.add(itemL1);

        EsLFPO itemL2 = new EsLFPO();
        itemL2.setId("L2");
        itemL2.setTitle("美国短尾猫在新湖明珠城小区8幢走丢了");
        itemL2.setUrl("https://www.baidu.com/");
        itemL2.setContent("短尾猫是一只已绝育的杂色母猫名为艾草，五斤左右看起来很瘦，指甲还未修剪，胆子很小怕生。它的眼睛非常大琥珀色，肚子白色毛发, 背部为黄黑灰相间，尾巴是灰黑色，请各位爱心人士看到帮忙留意，或者有些抓猫者已抓走艾草，也恳请通知本人，必当重谢！");
        itemL2.setAddr("新湖明珠城小区8幢");
        itemL2.setTime("2019-01-30");
        itemL2.setMoney("$500");
        itemL2.setName("诸葛云玥");
        list.add(itemL2);

        EsLFPO itemL3 = new EsLFPO();
        itemL3.setId("L3");
        itemL3.setTitle("急寻华为mate7手机一部");
        itemL3.setUrl("https://www.baidu.com/");
        itemL3.setContent("本人于下午3点左右在苏州观前街乘坐178公交车的时候，不小心遗失了华为mate7手机，手机里有工作和生活重要的联系人，对我来说十分重要，希望看到或者拾到的朋友可以与我联系，联系人：王女士，她的电话是13251305347，如能找到手机必有重谢，还请好心人帮忙关注下！本人于下午3点左右在苏州观前街乘坐178公交车的时候，不小心遗失了华为mate7手机，手机里有工作和生活重要的联系人，对我来说十分重要，希望看到或者拾到的朋友可以与我联系，联系人：王女士，她的电话是13251305347，如能找到手机必有重谢，还请好心人帮忙关注下！本人于下午3点左右在苏州观前街乘坐178公交车的时候，不小心遗失了华为mate7手机，手机里有工作和生活重要的联系人，对我来说十分重要，希望看到或者拾到的朋友可以与我联系，联系人：王女士，她的电话是13251305347，如能找到手机必有重谢，还请好心人帮忙关注下！");
        itemL3.setAddr("苏州观前街");
        itemL3.setTime("2019-02-01");
        itemL3.setMoney("800元");
        itemL3.setName("林岭");
        list.add(itemL3);

        EsLFPO itemL4 = new EsLFPO();
        itemL4.setId("L4");
        itemL4.setTitle("首都机场老娘舅餐厅里遗失了箱包");
        itemL4.setUrl("https://www.baidu.com/");
        itemL4.setContent("箱包黑色，把手处系着一块紫色飘巾，上面绣着\"悦\"字，使用粉+蓝夹带点黄色的丝线秀成的。由于在机场附近的老娘舅吃午饭，一不留神把放在旁边椅子上的箱包弄丢了，餐厅录像已经显示是一位身穿黑色衣服，下身是蓝色休闲牛仔裤的小伙子拿去了，希望这位年轻人看到这条发布消息，速与我联系，我在登机3号口的候车厅等待。");
        itemL4.setAddr("北京首都机场老娘舅餐厅");
        itemL4.setTime("2019-01-28");
        itemL4.setMoney("0");
        itemL4.setName("胡程晨");
        list.add(itemL4);

        EsLFPO itemL5 = new EsLFPO();
        itemL5.setId("L5");
        itemL5.setTitle("安卓手机遗失");
        itemL5.setUrl("https://www.baidu.com/");
        itemL5.setContent("家里老人用的一部安卓老款手机在公园里遗失了，上午老人在李公堤公园里散步，大约1个小时候后回来发现带在身边的手机没有了，可能是老人坐在公园里的石板上休息的时候，被小偷顺手拿走了，希望看见的好心人能与我们联系，更希望盗窃人可以归还，谢谢。");
        itemL5.setAddr("李公堤公园");
        itemL5.setTime("2019-02-11");
        itemL5.setMoney("0");
        itemL5.setName("海女士");
        list.add(itemL5);

        EsLFPO itemL6 = new EsLFPO();
        itemL6.setId("L6");
        itemL6.setTitle("寻找在小区门口遗失的小猫咪");
        itemL6.setUrl("https://www.baidu.com/");
        itemL6.setContent("小区门口的小猫遗失了几天了，希望看到的朋友通知我一下哈");
        itemL6.setAddr("小区门口");
        itemL6.setTime("2019-01-27");
        itemL6.setMoney("0");
        itemL6.setName("波涛");
        list.add(itemL6);

        EsLFPO itemL7 = new EsLFPO();
        itemL7.setId("L7");
        itemL7.setTitle("水果色外壳的iphone5遗失");
        itemL7.setUrl("https://www.baidu.com/");
        itemL7.setContent("本人晚上6点左右在地铁站等待的时候，上车过程中人很多，慌忙之中遗失了口袋里的iphone5，手机使用了2年多，里面收藏了一些聚餐、郊游等回忆相册，其它倒也没什么珍贵的东西，还望拾到者速与我联系，本人必有重谢！本人晚上6点左右在地铁站等待的时候，上车过程中人很多，慌忙之中遗失了口袋里的iphone5，手机使用了2年多，里面收藏了一些聚餐、郊游等回忆相册，其它倒也没什么珍贵的东西，还望拾到者速与我联系，本人必有重谢！本人晚上6点左右在地铁站等待的时候，上车过程中人很多，慌忙之中遗失了口袋里的iphone5，手机使用了2年多，里面收藏了一些聚餐、郊游等回忆相册，其它倒也没什么珍贵的东西，还望拾到者速与我联系，本人必有重谢！本人晚上6点左右在地铁站等待的时候，上车过程中人很多，慌忙之中遗失了口袋里的iphone5，手机使用了2年多，里面收藏了一些聚餐、郊游等回忆相册，其它倒也没什么珍贵的东西，还望拾到者速与我联系，本人必有重谢！");
        itemL7.setAddr("南京地铁1号线3号口");
        itemL7.setTime("2018-10-09");
        itemL7.setMoney("600元");
        itemL7.setName("小袁");
        list.add(itemL7);

        EsLFPO itemL8 = new EsLFPO();
        itemL8.setId("L8");
        itemL8.setTitle("遗失行李箱");
        itemL8.setUrl("https://www.baidu.com/");
        itemL8.setContent("在回北京的一列火车上，本人的白色行李箱遗失了，假期后从老家赶回外地上班的人比较多，火车上大家都挤在一起，监控录像里看不清，我的行李箱就这样遗失了，箱内有ipad一个，还有就是一些衣服什么的，希望看到的人可以帮忙联系我！在回北京的一列火车上，本人的白色行李箱遗失了，假期后从老家赶回外地上班的人比较多，火车上大家都挤在一起，监控录像里看不清，我的行李箱就这样遗失了，箱内有ipad一个，还有就是一些衣服什么的，希望看到的人可以帮忙联系我！在回北京的一列火车上，本人的白色行李箱遗失了，假期后从老家赶回外地上班的人比较多，火车上大家都挤在一起，监控录像里看不清，我的行李箱就这样遗失了，箱内有ipad一个，还有就是一些衣服什么的，希望看到的人可以帮忙联系我！在回北京的一列火车上，本人的白色行李箱遗失了，假期后从老家赶回外地上班的人比较多，火车上大家都挤在一起，监控录像里看不清，我的行李箱就这样遗失了，箱内有ipad一个，还有就是一些衣服什么的，希望看到的人可以帮忙联系我！");
        itemL8.setAddr("南通开往北京的火车上");
        itemL8.setTime("2019-02-15");
        itemL8.setMoney("0");
        itemL8.setName("闵小露");
        list.add(itemL8);

        EsLFPO itemL9 = new EsLFPO();
        itemL9.setId("L9");
        itemL9.setTitle("遗失家猫");
        itemL9.setUrl("https://www.baidu.com/");
        itemL9.setContent("我家的猫是一只灰色毛发，短耳朵的小猫，它的指甲刚剪过，是一只爱干净的猫。中午家猫在白蒲路附近溜达了一圈就再也没有看到了，希望看到的朋友与我联系。我家的猫是一只灰色毛发，短耳朵的小猫，它的指甲刚剪过，是一只爱干净的猫。中午家猫在白蒲路附近溜达了一圈就再也没有看到了，希望看到的朋友与我联系。");
        itemL9.setAddr("如皋白蒲路附近");
        itemL9.setTime("2019-02-22");
        itemL9.setMoney("￥120");
        itemL9.setName("张女士");
        list.add(itemL9);

        EsLFPO itemL10 = new EsLFPO();
        itemL10.setId("L10");
        itemL10.setTitle("大黄狗丢了");
        itemL10.setUrl("https://www.baidu.com/");
        itemL10.setContent("大黄狗是我的一位好朋友送给我的礼物，我们相处的很开心，它个头大，但是眼睛很有神，非常机灵的。陪伴我的时间2年多了，我很珍惜它，晚上6点左右在西海路陪它一起出来溜达，在小摊附近走丢了，希望大家有看到的与我联系。");
        itemL10.setAddr("西海路小摊附近");
        itemL10.setTime("2019-02-20");
        itemL10.setMoney("50美金");
        itemL10.setName("黎恬");
        list.add(itemL10);

        EsLFPO itemL21 = new EsLFPO();
        itemL21.setId("L21");
        itemL21.setTitle("寻狗启示");
        itemL21.setUrl("https://www.imooc.com/");
        itemL21.setContent("我家的狗，身形肉嘟嘟，胆子很小怕生。我家的狗，身形肉嘟嘟，胆子很小怕生。我家的狗，身形肉嘟嘟，胆子很小怕生。我家的狗，身形肉嘟嘟，胆子很小怕生。我家的狗，身形肉嘟嘟，胆子很小怕生。我家的狗，身形肉嘟嘟，胆子很小怕生。我家的狗，身形肉嘟嘟，胆子很小怕生。我家的狗，身形肉嘟嘟，胆子很小怕生。我家的狗，身形肉嘟嘟，胆子很小怕生。我家的狗，身形肉嘟嘟，胆子很小怕生。我家的狗，身形肉嘟嘟，胆子很小怕生。我家的狗，身形肉嘟嘟，胆子很小怕生。我家的狗，身形肉嘟嘟，胆子很小怕生。我家的狗，身形肉嘟嘟，胆子很小怕生。");
        itemL21.setAddr("上海闵行区");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        itemL21.setTime(sdf2.format(new Date()));
        itemL21.setMoney("0");
        itemL21.setName("郝女士");
        list.add(itemL21);

        EsLFPO itemL22 = new EsLFPO();
        itemL22.setId("L22");
        itemL22.setTitle("遗失苹果手机iphone8");
        itemL22.setUrl("https://www.baidu.com/");
        itemL22.setContent("我的iphone8是黑色款，没有手机壳包裹，使用了一年多，周六中午在金鸡湖商业景区游玩时遗失，急寻，希望好心人与我朋友联系，万分感谢啊！");
        itemL22.setAddr("金鸡湖商业区");
        itemL22.setTime("2019-02-16");
        itemL22.setMoney("2000元");
        itemL22.setName("明铭");
        list.add(itemL22);

        EsLFPO itemL23 = new EsLFPO();
        itemL23.setId("L23");
        itemL23.setTitle("粉色钱包遗失");
        itemL23.setUrl("https://www.baidu.com/");
        itemL23.setContent("本人于2019年2月20日下午3点左右，在南大街的三福百货遗失粉色钱包一个，钱包里有一张全家福照片、本人身份证、驾驶证、浙商银行卡一张、一些琐碎发票，还有200元左右的现金，还望拾到者可以联系我的家人或者朋友，必有重谢。本人于2019年2月20日下午3点左右，在南大街的三福百货遗失粉色钱包一个，钱包里有一张全家福照片、本人身份证、驾驶证、浙商银行卡一张、一些琐碎发票，还有200元左右的现金，还望拾到者可以联系我的家人或者朋友，必有重谢。本人于2019年2月20日下午3点左右，在南大街的三福百货遗失粉色钱包一个，钱包里有一张全家福照片、本人身份证、驾驶证、浙商银行卡一张、一些琐碎发票，还有200元左右的现金，还望拾到者可以联系我的家人或者朋友，必有重谢。本人于2019年2月20日下午3点左右，在南大街的三福百货遗失粉色钱包一个，钱包里有一张全家福照片、本人身份证、驾驶证、浙商银行卡一张、一些琐碎发票，还有200元左右的现金，还望拾到者可以联系我的家人或者朋友，必有重谢。本人于2019年2月20日下午3点左右，在南大街的三福百货遗失粉色钱包一个，钱包里有一张全家福照片、本人身份证、驾驶证、浙商银行卡一张、一些琐碎发票，还有200元左右的现金，还望拾到者可以联系我的家人或者朋友，必有重谢。");
        itemL23.setAddr("南大街三福百货");
        itemL23.setTime("2019-02-15");
        itemL23.setMoney("1000元");
        itemL23.setName("陈梦姚");
        list.add(itemL23);

        EsLFPO itemL24 = new EsLFPO();
        itemL24.setId("L24");
        itemL24.setTitle("丢失银色行李箱");
        itemL24.setUrl("https://www.baidu.com/");
        itemL24.setContent("银色行李箱是在二案商业街附近丢失，箱子没有特别，里面只是一些换洗衣服和2本杂志，如果有好心人看到还请联系我，本人微信：123nihao，或者QQ: 2345768990，或者电话：13579036785。银色行李箱是在二案商业街附近丢失，箱子没有特别，里面只是一些换洗衣服和2本杂志，如果有好心人看到还请联系我，本人微信：123nihao，或者QQ: 2345768990，或者电话：13579036785银色行李箱是在二案商业街附近丢失，箱子没有特别，里面只是一些换洗衣服和2本杂志，如果有好心人看到还请联系我，本人微信：123nihao，或者QQ: 2345768990，或者电话：13579036785银色行李箱是在二案商业街附近丢失，箱子没有特别，里面只是一些换洗衣服和2本杂志，如果有好心人看到还请联系我，本人微信：123nihao，或者QQ: 2345768990，或者电话：13579036785");
        itemL24.setAddr("二案商业街附近");
        itemL24.setTime("2019-01-23");
        itemL24.setMoney("0");
        itemL24.setName("张小可");
        list.add(itemL24);

        EsLFPO itemL25 = new EsLFPO();
        itemL25.setId("L25");
        itemL25.setTitle("安卓oppo R17丢了");
        itemL25.setUrl("https://www.baidu.com/");
        itemL25.setContent("我的oppo是刚买没多久，20号中午在西塘游玩，与闺蜜几人一起在景区内吃吃喝喝，一番狂嗨后，回到旅社发现手机遗失，希望看到的人可以与我闺蜜联系，谢谢。我的oppo是刚买没多久，20号中午在西塘游玩，与闺蜜几人一起在景区内吃吃喝喝，一番狂嗨后，回到旅社发现手机遗失，希望看到的人可以与我闺蜜联系，谢谢。我的oppo是刚买没多久，20号中午在西塘游玩，与闺蜜几人一起在景区内吃吃喝喝，一番狂嗨后，回到旅社发现手机遗失，希望看到的人可以与我闺蜜联系，谢谢。");
        itemL25.setAddr("杭州西塘景区");
        itemL25.setTime("2019-02-20");
        itemL25.setMoney("$200");
        itemL25.setName("程香湘");
        list.add(itemL25);

        EsLFPO itemL26 = new EsLFPO();
        itemL26.setId("L26");
        itemL26.setTitle("寻找可爱小猫咪Luncy");
        itemL26.setUrl("https://www.baidu.com/");
        itemL26.setContent("Luncy是从外处来到我家的小猫咪，绿宝石似的眼睛，炯炯有神！小猫咪最近饮食不佳，似有疲惫，昨天中午Luncy还在小区门口晒太阳的，下午就没有看见它了，家人找了附近各个地方也没有找到，还望看到的朋友可以联系我。Luncy是从外处来到我家的小猫咪，绿宝石似的眼睛，炯炯有神！小猫咪最近饮食不佳，似有疲惫，昨天中午Luncy还在小区门口晒太阳的，下午就没有看见它了，家人找了附近各个地方也没有找到，还望看到的朋友可以联系我。Luncy是从外处来到我家的小猫咪，绿宝石似的眼睛，炯炯有神！小猫咪最近饮食不佳，似有疲惫，昨天中午Luncy还在小区门口晒太阳的，下午就没有看见它了，家人找了附近各个地方也没有找到，还望看到的朋友可以联系我。Luncy是从外处来到我家的小猫咪，绿宝石似的眼睛，炯炯有神！小猫咪最近饮食不佳，似有疲惫，昨天中午Luncy还在小区门口晒太阳的，下午就没有看见它了，家人找了附近各个地方也没有找到，还望看到的朋友可以联系我。");
        itemL26.setAddr("新柳溪小区2幢1单元");
        itemL26.setTime("2019-01-10");
        itemL26.setMoney("0");
        itemL26.setName("唐女士");
        list.add(itemL26);

        EsLFPO itemL27 = new EsLFPO();
        itemL27.setId("L27");
        itemL27.setTitle("iphoneX遗失了");
        itemL27.setUrl("https://www.baidu.com/");
        itemL27.setContent("我朋友的iphoneX今天下午2点左右在华莱士餐厅用餐的时候遗失了，他很着急，希望看到的好心人可以与我联系，酬金600元。我朋友的iphoneX今天下午2点左右在华莱士餐厅用餐的时候遗失了，他很着急，希望看到的好心人可以与我联系，酬金1000元。我朋友的iphoneX今天下午2点左右在华莱士餐厅用餐的时候遗失了，他很着急，希望看到的好心人可以与我联系，酬金1000元。");
        itemL27.setAddr("华莱士餐厅");
        itemL27.setTime("2019-02-27");
        itemL27.setMoney("￥600");
        itemL27.setName("郁钟淼");
        list.add(itemL27);

        EsLFPO itemL28 = new EsLFPO();
        itemL28.setId("L28");
        itemL28.setTitle("猫儿在吴中区东方大道附近走失");
        itemL28.setUrl("https://www.baidu.com/");
        itemL28.setContent("家里的猫是一只体型偏胖，活泼胆大的猫，很机灵。它经常在东方大道附近觅食游荡。它虽然个头较大，但是很和善，不随便欺负小猫，近日，猫儿在大道附近走失，至今没有回家，还望看到者与我联系。家里的猫是一只体型偏胖，活泼胆大的猫，很机灵。它经常在东方大道附近觅食游荡。它虽然个头较大，但是很和善，不随便欺负小猫，近日，猫儿在大道附近走失，至今没有回家，还望看到者与我联系。家里的猫是一只体型偏胖，活泼胆大的猫，很机灵。它经常在东方大道附近觅食游荡。它虽然个头较大，但是很和善，不随便欺负小猫，近日，猫儿在大道附近走失，至今没有回家，还望看到者与我联系。家里的猫是一只体型偏胖，活泼胆大的猫，很机灵。它经常在东方大道附近觅食游荡。它虽然个头较大，但是很和善，不随便欺负小猫，近日，猫儿在大道附近走失，至今没有回家，还望看到者与我联系。家里的猫是一只体型偏胖，活泼胆大的猫，很机灵。它经常在东方大道附近觅食游荡。它虽然个头较大，但是很和善，不随便欺负小猫，近日，猫儿在大道附近走失，至今没有回家，还望看到者与我联系。家里的猫是一只体型偏胖，活泼胆大的猫，很机灵。它经常在东方大道附近觅食游荡。它虽然个头较大，但是很和善，不随便欺负小猫，近日，猫儿在大道附近走失，至今没有回家，还望看到者与我联系。家里的猫是一只体型偏胖，活泼胆大的猫，很机灵。它经常在东方大道附近觅食游荡。它虽然个头较大，但是很和善，不随便欺负小猫，近日，猫儿在大道附近走失，至今没有回家，还望看到者与我联系。");
        itemL28.setAddr("吴中区东方大道附近");
        itemL28.setTime("2019-02-18");
        itemL28.setMoney("￥70");
        itemL28.setName("闵小露");
        list.add(itemL28);

        EsLFPO itemL29 = new EsLFPO();
        itemL29.setId("L29");
        itemL29.setTitle("遗失波斯猫一只");
        itemL29.setUrl("https://www.baidu.com/");
        itemL29.setContent("一个月前从外地购买的长毛波斯猫脸happy，它额宽耳小，圆眼塌鼻口吻短宽，躯体因毛长而感觉浑圆，四肢粗短柔软，尾蓬松粗大，给人一种雍容华贵的感觉。今天下午5点左右在松塔公园走失，望看见的人与我联系一个月前从外地购买的长毛波斯猫脸happy，它额宽耳小，圆眼塌鼻口吻短宽，躯体因毛长而感觉浑圆，四肢粗短柔软，尾蓬松粗大，给人一种雍容华贵的感觉。今天下午5点左右在松塔公园走失，望看见的人与我联系一个月前从外地购买的长毛波斯猫脸happy，它额宽耳小，圆眼塌鼻口吻短宽，躯体因毛长而感觉浑圆，四肢粗短柔软，尾蓬松粗大，给人一种雍容华贵的感觉。今天下午5点左右在松塔公园走失，望看见的人与我联系");
        itemL29.setAddr("松塔公园");
        itemL29.setTime("2019-02-22");
        itemL29.setMoney("￥120");
        itemL29.setName("张女士");
        list.add(itemL29);

        EsLFPO itemL20 = new EsLFPO();
        itemL20.setId("L20");
        itemL20.setTitle("急寻苏格兰折耳猫");
        itemL20.setUrl("https://www.baidu.com/");
        itemL20.setContent("我家的苏格兰折耳猫是一只中等体型，圆浑浑的，骨骼中等，肌肉结实的雄性猫。它的四肢短，粗壮，肥胖，浑圆。体重2.5到6公斤。头部是圆形，前额凸鼓。脸颊浑圆。侧看像是缓和的曲线，有双下巴。耳朵朝前折，间隔大，耳尖呈弧形。耳朵的大小中等，向前翻折，如同帽子，看上去令头部更加圆浑。鼻子宽而短。微微有鼻中断是可以接受的。吻部微微呈圆形。髭毛垫呈圆形。下巴坚实。脖子不太长，但肌肉结实，毛发比其它身体部份的较长。眼睛大而圆，颜色以毛色为准，间距相当大。颜色和被毛相呼应。颈短而有肌肉感。腿和爪：长度和身体长度成比例。骨骼中等大小，长度与身体相称浑圆而紧凑的爪。脚掌圆圆的，非常齐整。尾：不大于身长的2/3。基部粗大，向尾尖逐渐变细，最后尾尖收拢为圆形。非常柔软和灵活。性格：平易近人、性格温和、聪明。不良性征：头过于纤细。过于明显的中断。");
        itemL20.setAddr("华润万家超市");
        itemL20.setTime("2019-02-21");
        itemL20.setMoney("100美金");
        itemL20.setName("景儿");
        list.add(itemL20);

        EsLFPO itemL31 = new EsLFPO();
        itemL31.setId("L31");
        itemL31.setTitle("急寻家里的短毛小猫");
        itemL31.setUrl("https://www.baidu.com/");
        itemL31.setContent("家猫身形小，毛发比较短，耳朵一般，眼睛闪亮而深邃。在普阳小街走丢。家猫身形小，毛发比较短，耳朵一般，眼睛闪亮而深邃。在普阳小街走丢。家猫身形小，毛发比较短，耳朵一般，眼睛闪亮而深邃。在普阳小街走丢。家猫身形小，毛发比较短，耳朵一般，眼睛闪亮而深邃。在普阳小街走丢。家猫身形小，毛发比较短，耳朵一般，眼睛闪亮而深邃。在普阳小街走丢。");
        itemL31.setAddr("晋安区普阳小街");
        itemL31.setTime("2019-01-18");
        itemL31.setMoney("0");
        itemL31.setName("小容");
        list.add(itemL31);

        EsLFPO itemL32 = new EsLFPO();
        itemL32.setId("L32");
        itemL32.setTitle("iphone4苹果手机遗失");
        itemL32.setUrl("https://www.baidu.com/");
        itemL32.setContent("iPhone 4是结合照相手机、个人数码助理、媒体播放器以及无线通信设备的掌上设备，iPhone 4延续自iPhone一代的多点触摸（Multi-touch）屏界面，并首次加入视网膜屏幕、前置摄像头、陀螺仪、后置闪光灯、相机像素提高至500万，并首次向世人展示其独有的facetime视频电话的创新功能。加入了30万像素的前置摄像头。");
        itemL32.setAddr("北京5环外滩");
        itemL32.setTime("2019-02-27");
        itemL32.setMoney("￥100");
        itemL32.setName("夏女士");
        list.add(itemL32);

        EsLFPO itemL33 = new EsLFPO();
        itemL33.setId("L33");
        itemL33.setTitle("急寻iphone6 plus");
        itemL33.setUrl("https://www.baidu.com/");
        itemL33.setContent("TechCrunch和Engadget都提到，iPhone 6 Plus让人想起了iPad mini，尤其是后者的侧边也是弧形设计。iPhone 6 Plus同样需要双手来操作，想要单手模式的话，轻拍两下Home键（不是按下去），显示内容会下移大约一半的尺寸。本人的iPhone 6 Plus是美国苹果公司在北京时间2014年9月10日推出的一款智能手机。使用5.5英寸屏幕 [1-2]  ，于2014年10月17日在中国内地上市。");
        itemL33.setAddr("无锡观乐街");
        itemL33.setTime("2019-01-22");
        itemL33.setMoney("0");
        itemL33.setName("王安国");
        list.add(itemL33);

        EsLFPO itemL34 = new EsLFPO();
        itemL34.setId("L34");
        itemL34.setTitle("西海游船区附近遗失深空灰色iPhone8Plus");
        itemL34.setUrl("https://www.baidu.com/");
        itemL34.setContent("双1200万像素，苹果强调采用面积更大、速度更快的感光元件，视频拍摄支持4K 60FPS。闪光灯加入了“慢速同步技术”，前置摄像头700万像素。 [5]  iPhone 8 Plus后置双摄像头主打机器学习的人像背景虚化拍摄，支持60fps码流的4K视频拍摄。支持无线充电。还有一个特点是其图形传感器加入了对AR技术的支持iPhone 8 Plus 可防溅、抗水、防尘，在受控实验室条件下经测试，其效果在 IEC 60529 标准下达到 IP67 级别 (在最深 1 米的水下停留时间最长可达 30 分钟)。防溅、抗水、防尘功能并非永久有效，防护性能可能会因日常磨损而下降。请勿为潮湿状态下的 iPhone 充电；请参阅使用手册了解清洁和干燥说明。由于浸入液体而导致的损坏不在保修范围之内。");
        itemL34.setAddr("常州万科小区一期");
        itemL34.setTime("2019-02-22");
        itemL34.setMoney("520人民币");
        itemL34.setName("杨先生");
        list.add(itemL34);

        EsLFPO itemL35 = new EsLFPO();
        itemL35.setId("L35");
        itemL35.setTitle("找狗：哈士奇");
        itemL35.setUrl("https://www.baidu.com/");
        itemL35.setContent("西伯利亚雪橇犬（俄语：Сибирский хаски，英语：Siberian husky），常见别名哈士奇，昵称为二哈。西伯利亚雪橇犬体重介于雄犬20-27公斤，雌犬16-23公斤，身高大约雄犬肩高53-58厘米，雌犬51-56厘米，是一种中型犬。西伯利亚雪橇犬是原始的古老犬种，在西伯利亚东北部、格陵兰南部生活。哈士奇名字的由来，是源自其独特的嘶哑声。哈士奇性格多变，有的极端胆小，也有的极端暴力，进入大陆和家庭的哈士奇，都已经没有了这种极端的性格，比较温顺，是一种流行于全球的宠物犬。与金毛犬、拉布拉多并列为三大无攻击型犬类。被世界各地广泛饲养，并在全球范围内，有大量该犬种的赛事。值得一提的是“蓝眼，三火”，蓝眼指的就是眼睛是蓝色的；三火指的是额头上的三道白色痕迹，看起来像三把燃烧的火苗。“蓝眼，三火”曾经一度被误认为是哈士奇的标准，其实这并不是判断哈士奇品质标准，反之蓝眼三火更是哈士奇普遍的存在，血统赛级哈士奇就极少有“蓝眼睛，三把火”的存在，多为两只褐眼。因为西伯利亚雪撬犬有着和狼非常相似的外观，所以我们所看到电影里的狼大多都是哈士奇扮演。通常是蓝色和褐色（棕色）。一般我们所说的“二哈”多为浅蓝色眼色的哈士奇，蓝色（随着年龄增长蓝色变淡近为白色），蓝色眼睛哈士奇看起来总是带着一种蔑视凶残的眼神，但实则很是温顺。在赛场上出现的多为褐色眼睛哈士奇，给人温柔呆萌的感觉，但认真的时候眼神间也透露出瘆人的狼意。还有，一只犬可能有一只眼是棕色或浅褐色的，而另一只却是蓝色的，恶魔与天使面孔同存，这种现象被称作\"bi-eyed\"；或者一只眼是蓝色的而另一只眼的虹膜是杂色的，即虹膜异色症，被饲养西伯利亚雪撬犬的爱好者称为\"parti-eyed\"。尾巴翘至背部甚至卷起来（否则担心纯种与否）。尾巴上的毛通常比体毛长且硬直，也没有体毛柔顺，挥动有力。尾巴是哈士奇表达情绪的重要方式，在高兴的时候，会表现出追尾巴的行为，在害怕或攻击的时候，尾巴会拱形夹在后腿之间，疑惑的时候，尾巴甚至会上下摆动");
        itemL35.setAddr("四川成都一条龙小吃街");
        itemL35.setTime("2019-01-27");
        itemL35.setMoney("0");
        itemL35.setName("朱先生");
        list.add(itemL35);

        return list;

    }

    private List<EsLFPO> createFindData() {
        List<EsLFPO> list = new ArrayList<>();
        EsLFPO itemF1 = new EsLFPO();
        itemF1.setId("F1");
        itemF1.setTitle("新湖明珠城小区8幢走丢的美国短尾猫");
        itemF1.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemF1.setContent("感谢王先生的帮助，在他家别墅院子里找到我家的短耳猫，并归还本人。感谢王先生的帮助，在他家别墅院子里找到我家的短耳猫，并归还本人。感谢王先生的帮助，在他家别墅院子里找到我家的短耳猫，并归还本人。");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        itemF1.setTime(sdf.format(new Date()));
        list.add(itemF1);

        EsLFPO itemF2 = new EsLFPO();
        itemF2.setId("F2");
        itemF2.setTitle("华为mate7手机");
        itemF2.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemF2.setContent("公交车司机在打扫卫生的时候看见手机遗失在座位底下，请失主前来认领，感谢司机师傅！");
        itemF2.setTime("2019-02-01");
        list.add(itemF2);

        EsLFPO itemF3 = new EsLFPO();
        itemF3.setId("F3");
        itemF3.setTitle("招领安卓手机一部");
        itemF3.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemF3.setContent("公园里遗失的一部安卓手机已找回，请失主前来认领。公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领公园里遗失的一部安卓手机已找回，请失主前来认领");
        itemF3.setTime("2019-02-01");
        list.add(itemF3);

        EsLFPO itemF4 = new EsLFPO();
        itemF4.setId("F4");
        itemF4.setTitle("小区门口遗失的小猫咪");
        itemF4.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemF4.setContent("有朋友将一只小猫咪带到招领办，希望正在寻找小猫咪的失主可以来招领办办理招领手续。有朋友将一只小猫咪带到招领办，希望正在寻找小猫咪的失主可以来招领办办理招领手续。有朋友将一只小猫咪带到招领办，希望正在寻找小猫咪的失主可以来招领办办理招领手续。有朋友将一只小猫咪带到招领办，希望正在寻找小猫咪的失主可以来招领办办理招领手续。有朋友将一只小猫咪带到招领办，希望正在寻找小猫咪的失主可以来招领办办理招领手续。有朋友将一只小猫咪带到招领办，希望正在寻找小猫咪的失主可以来招领办办理招领手续。");
        itemF4.setTime("2019-01-29");
        list.add(itemF4);

        EsLFPO itemF5 = new EsLFPO();
        itemF5.setId("F5");
        itemF5.setTitle("体系偏胖的家猫");
        itemF5.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemF5.setContent("今日上午陆先生将一只体型微胖，指甲未修剪得家猫送至招领办，请相关人士前来认领。今日上午陆先生将一只体型微胖，指甲未修剪得家猫送至招领办，请相关人士前来认领。");
        itemF5.setTime("2019-02-01");
        list.add(itemF5);

        EsLFPO itemF6 = new EsLFPO();
        itemF6.setId("F6");
        itemF6.setTitle("招领狗：哈士奇");
        itemF6.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemF6.setContent("哈士奇雪橇三傻之一,也是颜值很高的狗狗,饲养哈士奇能带来很多的欢乐,所以现在饲养二哈的人越来越多。哈士奇雪橇三傻之一,也是颜值很高的狗狗,饲养哈士奇能带来很多的欢乐,所以现在饲养二哈的人越来越多。哈士奇雪橇三傻之一,也是颜值很高的狗狗,饲养哈士奇能带来很多的欢乐,所以现在饲养二哈的人越来越多。哈士奇雪橇三傻之一,也是颜值很高的狗狗,饲养哈士奇能带来很多的欢乐,所以现在饲养二哈的人越来越多。");
        itemF6.setTime("2019-02-12");
        list.add(itemF6);

        EsLFPO itemF7 = new EsLFPO();
        itemF7.setId("F7");
        itemF7.setTitle("水果色外壳的iphone5");
        itemF7.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemF7.setContent("已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。已找到水果色外壳的iphone5，请正在寻找该手机的失主速来招领办领取。");
        itemF7.setTime("2019-02-28");
        list.add(itemF7);

        EsLFPO itemF8 = new EsLFPO();
        itemF8.setId("F8");
        itemF8.setTitle("深空灰色iPhone8Plus");
        itemF8.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemF8.setContent("支持无线充电。还有一个特点是其图形传感器加入了对AR技术的支持iPhone 8 Plus 可防溅、抗水、防尘，在受控实验室条件下经测试，其效果在 IEC 60529 标准下达到 IP67 级别 (在最深 1 米的水下停留时间最长可达 30 分钟)。防溅、抗水、防尘功能并非永久有效，防护性能可能会因日常磨损而下降。请勿为潮湿状态下的 iPhone 充电；请参阅使用手册了解清洁和干燥说明。由于浸入液体而导致的损坏不在保修范围之内。支持无线充电。还有一个特点是其图形传感器加入了对AR技术的支持iPhone 8 Plus 可防溅、抗水、防尘，在受控实验室条件下经测试，其效果在 IEC 60529 标准下达到 IP67 级别 (在最深 1 米的水下停留时间最长可达 30 分钟)。防溅、抗水、防尘功能并非永久有效，防护性能可能会因日常磨损而下降。请勿为潮湿状态下的 iPhone 充电；请参阅使用手册了解清洁和干燥说明。由于浸入液体而导致的损坏不在保修范围之内");
        itemF8.setTime("2019-02-28");
        list.add(itemF8);

        EsLFPO itemF9 = new EsLFPO();
        itemF9.setId("F9");
        itemF9.setTitle("苏格兰折耳猫");
        itemF9.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemF9.setContent("苏格兰折耳猫是一只中等体型，圆浑浑的，骨骼中等，肌肉结实的雄性猫。它的四肢短，粗壮，肥胖，浑圆。体重2.5到6公斤。头部是圆形，前额凸鼓。脸颊浑圆。性格：平易近人、性格温和、聪明。不良性征：头过于纤细。过于明显的中断。现招领苏格兰折耳猫一只，请失主携带有效证明来招领办领取！");
        itemF9.setTime("2019-02-20");
        list.add(itemF9);

        EsLFPO itemF10 = new EsLFPO();
        itemF10.setId("F10");
        itemF10.setTitle("短毛小猫");
        itemF10.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemF10.setContent("猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。猫的毛发短，身形中等，看起来很机灵，请相关失物前来认领。");
        itemF10.setTime("2019-01-26");
        list.add(itemF10);


        return list;
    }



    /**
     * ik分词
     *
     * @param title
     * @return
     */
    public List<String> ikAnalyse(String title) {
        AnalyzeRequestBuilder ikRequest = new AnalyzeRequestBuilder(client, AnalyzeAction.INSTANCE, ElasticSearchConstants.ES_DB, title);
        ikRequest.setTokenizer("ik_max_word");
        List<AnalyzeResponse.AnalyzeToken> ikTokenList = ikRequest.execute().actionGet().getTokens();
        //循环赋值
        List<String> ikList = new ArrayList<>();
        ikTokenList.forEach(ikToken -> {
            if (ikToken.getTerm().length() > 1) { //过滤串
                ikList.add(ikToken.getTerm());
            }
        });

        AnalyzeRequestBuilder ikRequestPinYin = new AnalyzeRequestBuilder(client, AnalyzeAction.INSTANCE, ElasticSearchConstants.ES_DB, title);
        ikRequestPinYin.setTokenizer("pinyin");
        List<AnalyzeResponse.AnalyzeToken> ikTokenListPinYin = ikRequestPinYin.execute().actionGet().getTokens();
        ikTokenListPinYin.forEach(ikToken -> {
            if (ikToken.getTerm().length() > 1) { //过滤串
                ikList.add(ikToken.getTerm());
            }
        });

        return ikList;
    }

    /**
     * 创建索引并添加映射
     *
     * @throws IOException
     */
    @Test
    public void createIndexAndMapping() throws IOException {
        CreateIndexRequestBuilder cib = client.admin().indices().prepareCreate(ElasticSearchConstants.ES_DB);
        XContentBuilder mappingLost = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties") //设置之定义字段
                .startObject("id")
                .field("type", "keyword") //设置数据类型
                .endObject()
                .startObject("title")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("url")
                .field("type", "keyword")
                .endObject()
                .startObject("content")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("addr")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("time")
                .field("type", "date")
                .endObject()
                .startObject("money")
                .field("type", "keyword")
                .endObject()
                .startObject("name")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("suggest")  //搜索建议
                .field("type", "completion")
                .field("analyzer", "ik_max_word")
                .endObject()
                .endObject()
                .endObject();
        cib.addMapping(ElasticSearchConstants.ES_TABLE_LOST, mappingLost);

        XContentBuilder mappingFind = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties") //设置之定义字段
                .startObject("id")
                .field("type", "keyword") //设置数据类型
                .endObject()
                .startObject("title")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("url")
                .field("type", "keyword")
                .endObject()
                .startObject("content")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("time")
                .field("type", "date")
                .endObject()
                .startObject("suggest")  //搜索建议
                .field("type", "completion")
                .field("analyzer", "ik_max_word")
                .endObject()
                .endObject()
                .endObject();
        cib.addMapping(ElasticSearchConstants.ES_TABLE_FIND, mappingFind);

        CreateIndexResponse res = cib.execute().actionGet();
        System.out.println(res.isAcknowledged());
    }


    /**
     * 批量添加失物数据
     *
     * @throws IOException
     */
    @Test
    public void addLostData() throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        List<EsLFPO> list = createLostData();
        list.forEach(item -> {
            IndexRequestBuilder requestBuilder = null;
            try {
                requestBuilder = client.prepareIndex(ElasticSearchConstants.ES_DB, ElasticSearchConstants.ES_TABLE_LOST, String.valueOf(++ElasticSearchConstants.ID_LOST))
                        .setSource(
                                XContentFactory.jsonBuilder()
                                        .startObject()
                                        .field("id", item.getId())
                                        .field("title", item.getTitle())
                                        .field("url", item.getUrl())
                                        .field("content", item.getContent())
                                        .field("addr", item.getAddr())
                                        .field("time", item.getTime())
                                        .field("money", item.getMoney())
                                        .field("name", item.getName())
                                        .startObject("suggest")  //搜索建议
                                        .field("input", ikAnalyse(item.getTitle()))  //数组
                                        .field("weight", 10)
                                        .endObject()
                                        .endObject()
                        );
            } catch (IOException e) {
                e.printStackTrace();
            }
            bulkRequest.add(requestBuilder);
        });
        BulkResponse response = bulkRequest.get();
        System.out.println(response.hasFailures()); //false 批量操作成功
        System.out.println(response.getTook()); //How long the bulk execution took
        if (response.hasFailures()) { //true
            System.out.println("lost-bulk批量添加失败了");
        }

        /*try {
            int id = 0;
            for (EsLFPO item : list) {
                id = id + 1;
                IndexRequestBuilder requestBuilder = client.prepareIndex(ElasticSearchConstants.ES_DB,ElasticSearchConstants.ES_TABLE_LOST,String.valueOf(id))
                        .setSource(
                                XContentFactory.jsonBuilder()
                                        .startObject()
                                        .field("id", item.getId())
                                        .field("title", item.getTitle())
                                        .field("url", item.getUrl())
                                        .field("content", item.getContent())
                                        .field("addr", item.getAddr())
                                        .field("time", item.getTime())
                                        .field("money", item.getMoney())
                                        .field("name", item.getName())
                                        .startObject("suggest")  //搜索建议
                                        .field("input", ikAnalyse(item.getTitle()))  //数组
                                        .field("weight", 10)
                                        .endObject()
                                        .endObject()
                        );
                bulkRequest.add(requestBuilder);
            }

            BulkResponse response = bulkRequest.get();
            System.out.println(response.hasFailures()); //false 批量操作成功
            System.out.println(response.getTook()); //How long the bulk execution took
            if (response.hasFailures()) { //true
                System.out.println("bulk批量添加失败了");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    /**
     * 批量添加招领数据
     *
     * @throws IOException
     */
    @Test
    public void addFindData() throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        List<EsLFPO> list = createFindData();
        list.forEach(item -> {
            IndexRequestBuilder requestBuilder = null;
            try {
                requestBuilder = client.prepareIndex(ElasticSearchConstants.ES_DB, ElasticSearchConstants.ES_TABLE_FIND, "find" + (++ElasticSearchConstants.ID_FIND))
                        .setSource(
                                XContentFactory.jsonBuilder()
                                        .startObject()
                                        .field("id", item.getId())
                                        .field("title", item.getTitle())
                                        .field("url", item.getUrl())
                                        .field("content", item.getContent())
                                        .field("time", item.getTime())
                                        .startObject("suggest")  //搜索建议
                                        .field("input", ikAnalyse(item.getTitle()))  //数组
                                        .field("weight", 8)
                                        .endObject()
                                        .endObject()
                        );
            } catch (IOException e) {
                e.printStackTrace();
            }
            bulkRequest.add(requestBuilder);
        });
        BulkResponse response = bulkRequest.get();
        System.out.println(response.hasFailures()); //false 批量操作成功
        System.out.println(response.getTook()); //How long the bulk execution took
        if (response.hasFailures()) { //true
            System.out.println("find-bulk批量添加失败了");
        }

    }





}
