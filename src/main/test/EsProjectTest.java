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
        itemL1.setImage("https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=1080100197,393118566&fm=85&s=4AE18A45435526750475C9970300F0C3");
        list.add(itemL1);

        EsLFPO itemL2 = new EsLFPO();
        itemL2.setId("L2");
        itemL2.setTitle("美国短尾猫在新湖明珠城小区8幢走丢了");
        itemL2.setUrl("https://www.baidu.com/");
        itemL2.setContent("短尾猫是一只已绝育的杂色母猫名为艾草，五斤左右看起来很瘦，指甲还未修剪，胆子很小怕生。它的眼睛非常大琥珀色，肚子白色毛发, 背部为黄黑灰相间，尾巴是灰黑色，请各位爱心人士看到帮忙留意，或者有些抓猫者已抓走艾草，也恳请通知本人，必当重谢！");
        itemL2.setAddr("新湖明珠城小区8幢");
        itemL2.setTime("2019-03-18");
        itemL2.setMoney("$500");
        itemL2.setName("诸葛云玥");
        list.add(itemL2);

        EsLFPO itemL3 = new EsLFPO();
        itemL3.setId("L3");
        itemL3.setTitle("急寻华为mate7手机一部");
        itemL3.setUrl("https://www.baidu.com/");
        itemL3.setContent("本人于下午3点左右在苏州观前街乘坐178公交车的时候，不小心遗失了华为mate7手机，手机里有工作和生活重要的联系人，对我来说十分重要，希望看到或者拾到的朋友可以与我联系，联系人：王女士，她的电话是13251305347，如能找到手机必有重谢，还请好心人帮忙关注下！本人于下午3点左右在苏州观前街乘坐178公交车的时候，不小心遗失了华为mate7手机，手机里有工作和生活重要的联系人，对我来说十分重要，希望看到或者拾到的朋友可以与我联系，联系人：王女士，她的电话是13251305347，如能找到手机必有重谢，还请好心人帮忙关注下！本人于下午3点左右在苏州观前街乘坐178公交车的时候，不小心遗失了华为mate7手机，手机里有工作和生活重要的联系人，对我来说十分重要，希望看到或者拾到的朋友可以与我联系，联系人：王女士，她的电话是13251305347，如能找到手机必有重谢，还请好心人帮忙关注下！");
        itemL3.setAddr("苏州观前街");
        itemL3.setTime("2019-03-21");
        itemL3.setMoney("800元");
        itemL3.setName("林岭");
        itemL3.setImage("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3569171748,3856999465&fm=58");
        list.add(itemL3);

        EsLFPO itemL4 = new EsLFPO();
        itemL4.setId("L4");
        itemL4.setTitle("首都机场老娘舅餐厅里遗失了箱包");
        itemL4.setUrl("https://www.baidu.com/");
        itemL4.setContent("箱包黑色，把手处系着一块紫色飘巾，上面绣着\"悦\"字，使用粉+蓝夹带点黄色的丝线秀成的。由于在机场附近的老娘舅吃午饭，一不留神把放在旁边椅子上的箱包弄丢了，餐厅录像已经显示是一位身穿黑色衣服，下身是蓝色休闲牛仔裤的小伙子拿去了，希望这位年轻人看到这条发布消息，速与我联系，我在登机3号口的候车厅等待。");
        itemL4.setAddr("北京首都机场老娘舅餐厅");
        itemL4.setTime("2019-03-28");
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
        itemL5.setImage("https://ss2.bdstatic.com/8_V1bjqh_Q23odCf/pacific/1692815277.png");
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
        itemL6.setImage("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2157270659,3841020697&fm=58&bpow=1024&bpoh=683");
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
        itemL10.setTime("2019-01-20");
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
        itemL21.setImage("https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=3942663771,121606674&fm=85&s=E96692546221330D80AE6DA70300706A");
        list.add(itemL21);

        EsLFPO itemL22 = new EsLFPO();
        itemL22.setId("L22");
        itemL22.setTitle("遗失苹果手机iphone8");
        itemL22.setUrl("https://www.baidu.com/");
        itemL22.setContent("我的iphone8是黑色款，没有手机壳包裹，使用了一年多，周六中午在金鸡湖商业景区游玩时遗失，急寻，希望好心人与我朋友联系，万分感谢啊！");
        itemL22.setAddr("金鸡湖商业区");
        itemL22.setTime("2019-03-26");
        itemL22.setMoney("2000元");
        itemL22.setName("明铭");
        itemL22.setImage("https://ss0.baidu.com/73x1bjeh1BF3odCf/it/u=3443257879,4253851129&fm=85&s=469214C56BA64692DA98B2FE03005021");
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
        itemL26.setImage("https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=60819368,2490029766&fm=85&s=B1B0CB355841E641172085F50300D063");
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
        itemL20.setImage("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1569462993,172008204&fm=5");
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
        itemL31.setImage("https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=3961416042,2825391643&fm=85&s=835E33D100783E3692A8D2230300A055");
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
        itemL33.setImage("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2265637821,3312937915&fm=85&app=57&f=JPEG?w=121&h=75&s=A0A047B5DEB2B6D0402D459E03008043");
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
        itemL35.setImage("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=865536755,2742351179&fm=85&app=57&f=JPEG?w=121&h=75");
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
        itemF1.setImage("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2311275533,588620051&fm=85&app=57&f=JPEG?w=121&h=75&s=2183DC5DCE775A057AA0857C03008013");
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
        itemF3.setImage("https://ss2.bdstatic.com/8_V1bjqh_Q23odCf/pacific/1692815277.png");
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
        itemF5.setImage("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1202510011,161257513&fm=85&app=57&f=JPEG?w=121&h=75&s=7123905710737A2D54BF1CF403005039");
        list.add(itemF5);

        EsLFPO itemF6 = new EsLFPO();
        itemF6.setId("F6");
        itemF6.setTitle("招领狗：哈士奇");
        itemF6.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemF6.setContent("哈士奇雪橇三傻之一，也是颜值很高的狗狗,饲养哈士奇能带来很多的欢乐，所以现在饲养二哈的人越来越多。哈士奇雪橇三傻之一，也是颜值很高的狗狗,饲养哈士奇能带来很多的欢乐，所以现在饲养二哈的人越来越多。哈士奇雪橇三傻之一，也是颜值很高的狗狗,饲养哈士奇能带来很多的欢乐，所以现在饲养二哈的人越来越多。哈士奇雪橇三傻之一，也是颜值很高的狗狗，饲养哈士奇能带来很多的欢乐，所以现在饲养二哈的人越来越多。");
        itemF6.setTime("2019-02-12");
        itemF6.setImage("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=865536755,2742351179&fm=85&app=57&f=JPEG?w=121&h=75");
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
        itemF8.setTitle("土豪金iPhone8Plus");
        itemF8.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemF8.setContent("支持无线充电。还有一个特点是其图形传感器加入了对AR技术的支持iPhone 8 Plus 可防溅、抗水、防尘，在受控实验室条件下经测试，其效果在 IEC 60529 标准下达到 IP67 级别 (在最深 1 米的水下停留时间最长可达 30 分钟)。防溅、抗水、防尘功能并非永久有效，防护性能可能会因日常磨损而下降。请勿为潮湿状态下的 iPhone 充电；请参阅使用手册了解清洁和干燥说明。由于浸入液体而导致的损坏不在保修范围之内。支持无线充电。还有一个特点是其图形传感器加入了对AR技术的支持iPhone 8 Plus 可防溅、抗水、防尘，在受控实验室条件下经测试，其效果在 IEC 60529 标准下达到 IP67 级别 (在最深 1 米的水下停留时间最长可达 30 分钟)。防溅、抗水、防尘功能并非永久有效，防护性能可能会因日常磨损而下降。请勿为潮湿状态下的 iPhone 充电；请参阅使用手册了解清洁和干燥说明。由于浸入液体而导致的损坏不在保修范围之内");
        itemF8.setTime("2019-02-28");
        itemF8.setImage("https://ss2.bdstatic.com/8_V1bjqh_Q23odCf/pacific/1740643364.jpg");
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
        itemF10.setImage("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1258324164,3366560962&fm=85&app=52&f=JPEG?w=121&h=75&s=A2905CCD961363C6D10160730300C05A");
        list.add(itemF10);

        EsLFPO itemFF1 = new EsLFPO();
        itemFF1.setId("FF1");
        itemFF1.setTitle("好心人送来一只小猫");
        itemFF1.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF1.setContent("猫，属于猫科动物，分家猫、野猫，是全世界家庭中较为广泛的宠物。家猫的祖先据推测是起源于古埃及的沙漠猫，波斯的波斯猫，已经被人类驯化了3500年（1560年，嘉靖皇帝朱厚熜的猫死了。皇城里出现了几丝不易嗅到的紧张气息，只有太监们敏感地察觉到天要变了。捧着爱猫“霜眉”的尸体，朱厚熜强忍眼泪，宣布要用道教礼仪设坛祭猫");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        itemFF1.setTime(sdf2.format(new Date()));
        itemFF1.setImage("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2039148305,2538599625&fm=58&bpow=800&bpoh=500");
        list.add(itemFF1);

        EsLFPO itemFF2 = new EsLFPO();
        itemFF2.setId("FF2");
        itemFF2.setTitle("招领交易猫");
        itemFF2.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF2.setContent("交易猫是国内专业的手机游戏交易平台,安全可靠的手游交易网站,提供手游账号交易、买号卖号、苹果代充值、游戏充值、首充号、装备道具、游戏币交易的手机网游交易平台!");
        itemFF2.setTime("2019-02-25");
        itemFF2.setImage("https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/whfpf%3D180%2C140%2C50/sign=0cfca997f6f2b211e47bd60eacbd5400/1c950a7b02087bf41615980dffd3572c10dfcf5d.jpg");
        list.add(itemFF2);

        EsLFPO itemFF3 = new EsLFPO();
        itemFF3.setId("FF3");
        itemFF3.setTitle("宠物猫");
        itemFF3.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF3.setContent("上海宠物猫价格频道为上海网民提供优质上海宠物猫多少钱一只、宠物猫转让信息，这里有大量的宠物猫图片信息供您。上海宠物猫价格频道为上海网民提供优质上海宠物猫多少钱一只、宠物猫转让信息，这里有大量的宠物猫图片信息供您。上海宠物猫价格频道为上海网民提供优质上海宠物猫多少钱一只、宠物猫转让信息，这里有大量的宠物猫图片信息供您。上海宠物猫价格频道为上海网民提供优质上海宠物猫多少钱一只、宠物猫转让信息，这里有大量的宠物猫图片信息供您。上海宠物猫价格频道为上海网民提供优质上海宠物猫多少钱一只、宠物猫转让信息，这里有大量的宠物猫图片信息供您。上海宠物猫价格频道为上海网民提供优质上海宠物猫多少钱一只、宠物猫转让信息，这里有大量的宠物猫图片信息供您。上海宠物猫价格频道为上海网民提供优质上海宠物猫多少钱一只、宠物猫转让信息，这里有大量的宠物猫图片信息供您。上海宠物猫价格频道为上海网民提供优质上海宠物猫多少钱一只、宠物猫转让信息，这里有大量的宠物猫图片信息供您。上海宠物猫价格频道为上海网民提供优质上海宠物猫多少钱一只、宠物猫转让信息，这里有大量的宠物猫图片信息供您。上海宠物猫价格频道为上海网民提供优质上海宠物猫多少钱一只、宠物猫转让信息，这里有大量的宠物猫图片信息供您。上海宠物猫价格频道为上海网民提供优质上海宠物猫多少钱一只、宠物猫转让信息，这里有大量的宠物猫图片信息供您。");
        itemFF3.setTime("2019-03-05");
        itemFF3.setImage("https://pic4.58cdn.com.cn/p1/big/n_v287eaf81325a642128b095bf46851fc64.jpg?w=338&h=253");
        list.add(itemFF3);

        EsLFPO itemFF4 = new EsLFPO();
        itemFF4.setId("FF4");
        itemFF4.setTitle("幸运土猫");
        itemFF4.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF4.setContent("幸运土猫“喵公馆”，最值得期待的喵星人寄养中心！");
        itemFF4.setTime("2019-03-11");
        itemFF4.setImage("");
        list.add(itemFF4);

        EsLFPO itemFF5 = new EsLFPO();
        itemFF5.setId("FF5");
        itemFF5.setTitle("招领爱猫happy");
        itemFF5.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF5.setContent("体型: 全部小型猫中型猫大型猫 毛长: 全部无毛猫短毛猫长毛猫!体型: 全部小型猫中型猫大型猫 毛长: 全部无毛猫短毛猫长毛猫!体型: 全部小型猫中型猫大型猫 毛长: 全部无毛猫短毛猫长毛猫!体型: 全部小型猫中型猫大型猫 毛长: 全部无毛猫短毛猫长毛猫!");
        itemFF5.setTime("2019-03-11");
        itemFF5.setImage("http://img.boqiicdn.com/Data/BK/P/img81471406191314.jpg");
        list.add(itemFF5);

        EsLFPO itemFF6 = new EsLFPO();
        itemFF6.setId("FF6");
        itemFF6.setTitle("招领沙特尔猫");
        itemFF6.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF6.setContent("沙特尔猫又叫夏特尔蓝猫，英文名Chartreux，原产法国，历史起源于14世纪。与英国短毛蓝猫、俄罗斯蓝猫(详情介绍)共称世界三大蓝猫。沙特尔猫的历史很悠久，在1558年的刊物上已有所记载，据信这个古老的法国品种是格勒诺布尔附近的大沙特。");
        itemFF6.setTime("2019-03-03");
        itemFF6.setImage("http://img.boqiicdn.com/Data/BK/P/img13171406105650.jpg");
        list.add(itemFF6);

        EsLFPO itemFF7 = new EsLFPO();
        itemFF7.setId("FF7");
        itemFF7.setTitle("主人急寻的塞尔凯克卷毛猫找到啦");
        itemFF7.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF7.setContent("1987年，怀俄明州的一个收容所一只花斑猫生下一窝小猫，其中一只猫是卷毛种，那只卷毛小猫在14个月大时，被繁殖家Jeri Newman发现，喜爱研究遗传基因的Jeri Newman将猫跟自己饲养的波斯猫异种繁殖，结果生下6只小猫。其中3只和母亲一样长着卷曲的被毛。第一只拥有卷毛基因的猫经十多年努力配种改良，终于在2000年被CFA确认，之后世界各地相继引入繁殖。1987年，怀俄明州的一个收容所一只花斑猫生下一窝小猫，其中一只猫是卷毛种，那只卷毛小猫在14个月大时，被繁殖家Jeri Newman发现，喜爱研究遗传基因的Jeri Newman将猫跟自己饲养的波斯猫异种繁殖，结果生下6只小猫。其中3只和母亲一样长着卷曲的被毛。第一只拥有卷毛基因的猫经十多年努力配种改良，终于在2000年被CFA确认，之后世界各地相继引入繁殖。1987年，怀俄明州的一个收容所一只花斑猫生下一窝小猫，其中一只猫是卷毛种，那只卷毛小猫在14个月大时，被繁殖家Jeri Newman发现，喜爱研究遗传基因的Jeri Newman将猫跟自己饲养的波斯猫异种繁殖，结果生下6只小猫。其中3只和母亲一样长着卷曲的被毛。第一只拥有卷毛基因的猫经十多年努力配种改良，终于在2000年被CFA确认，之后世界各地相继引入繁殖。1987年，怀俄明州的一个收容所一只花斑猫生下一窝小猫，其中一只猫是卷毛种，那只卷毛小猫在14个月大时，被繁殖家Jeri Newman发现，喜爱研究遗传基因的Jeri Newman将猫跟自己饲养的波斯猫异种繁殖，结果生下6只小猫。其中3只和母亲一样长着卷曲的被毛。第一只拥有卷毛基因的猫经十多年努力配种改良，终于在2000年被CFA确认，之后世界各地相继引入繁殖。1987年，怀俄明州的一个收容所一只花斑猫生下一窝小猫，其中一只猫是卷毛种，那只卷毛小猫在14个月大时，被繁殖家Jeri Newman发现，喜爱研究遗传基因的Jeri Newman将猫跟自己饲养的波斯猫异种繁殖，结果生下6只小猫。其中3只和母亲一样长着卷曲的被毛。");
        itemFF7.setTime("2019-03-03");
        itemFF7.setImage("http://img.boqiicdn.com/Data/BK/P/img72201406106623.jpg");
        list.add(itemFF7);

        EsLFPO itemFF8 = new EsLFPO();
        itemFF8.setId("FF8");
        itemFF8.setTitle("招领小区东方大道附近遗失的日本短尾猫");
        itemFF8.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF8.setContent("日本短尾猫是日本土生土长的猫种，在日本饲养的历史可追溯到很多世纪以前。传入美国后经过改良培育而成。据说有一个住在日本的美国女性爱猫者，发现日本有一种猫，尾巴像兔尾一样短，很是可爱。在二次世界大站结束后，她带了几只猫回国。这几只猫就是现在的日本短尾猫的祖先。现在日本短尾猫已跻身于世界著名观赏猫的行列，但在英国尚未得到公认。日本短尾猫坐着的时候往往要抬起一只前爪，据说这种姿势代表吉祥如意;玳瑁色-白色花猫则被认为大吉大利。三色猫常出现在一些国家的文艺作品和绘画中。日本短尾猫也叫花猫或短尾花猫，这种猫历史悠久，有人认为它是1000多年前由中国或朝鲜传入日本的。日本人认为花猫是幸运的象征，因此有很多家庭饲养。 1968年由日本传到美国，但欧洲饲养的不多。");
        itemFF8.setTime("2019-03-01");
        itemFF8.setImage("http://img.boqiicdn.com/Data/BK/P/img70661405933355.jpg");
        list.add(itemFF8);

        EsLFPO itemFF9 = new EsLFPO();
        itemFF9.setId("FF9");
        itemFF9.setTitle("猫：短毛");
        itemFF9.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF9.setContent("英国短毛猫有悠久的历史，但直到20世纪初才引起人们的宠爱。该猫体形圆胖，外型 由中型至大型。其骨架及肌肉很发达，短而肥的颈与及阔而平的肩膊相配合。头部圆而阔， 体粗短发达，被毛短而密，头大脸圆，大而圆的眼睛根据被毛不同而呈现各种颜色。最大的特征是支耳的距离很接近身。该猫温柔平静，对人友善，很容易饲养。");
        itemFF9.setTime("2019-03-03");
        itemFF9.setImage("http://img.boqiicdn.com/Data/BK/P/img70661405933355.jpg");
        list.add(itemFF9);

        EsLFPO itemFF10 = new EsLFPO();
        itemFF10.setId("FF10");
        itemFF10.setTitle("红色眼睛的猫儿");
        itemFF10.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF10.setContent("关于卡尔特猫这个品种历史悠久,品种的起源说法各种各样,据说在古时候卡尔特的短羊毛状的毛皮曾经被当成水濑毛皮来卖！现代来说,卡尔特猫起源于法国,别名传教士蓝猫,在法国生存了已有几个世纪之久。\n" +
                "\n" +
                "       传说CARTHUSIAN教士把卡尔特猫从南非带回，并养育了这些猫。那么这个品种的起源是什么呢？有人认为它的名字表现了它浓密，羊毛状的，类似一种叫CHARTREUX堆的西班牙羊毛纤维；还有人，如FIZNIGER，假定它是曼纽猫和埃及猫的杂交后代。事实上，卡尔特猫祖先可能起源于伊朗，叙利亚和土耳其山区，那里恶劣的环境需要它有厚厚的被毛。在十字军东征时期有一些卡尔特猫传入了法国。");
        itemFF10.setTime("2019-03-03");
        itemFF10.setImage("http://img.boqiicdn.com/Data/BK/P/img49891406109364.jpg");
        list.add(itemFF10);

        EsLFPO itemFF11 = new EsLFPO();
        itemFF11.setId("FF11");
        itemFF11.setTitle("米兰苑小区里的宠物猫");
        itemFF11.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF11.setContent("大家喜欢叫它加菲猫，憨憨的样子极其可爱。加菲猫(Garfield)是由吉姆·戴维斯(Jim Davis)所创，第一次出现在美国报纸上是在1978年6月19日。它是一只爱说风凉话、好吃、爱睡觉，以及爱捉弄人的大肥猫。无论成人还是孩子都被它的魅力所倾倒。   ");
        itemFF11.setTime("2019-01-15");
        itemFF11.setImage("http://img.boqiicdn.com/Data/BK/P/imagick13791542249182.jpg");
        list.add(itemFF11);

        EsLFPO itemFF12 = new EsLFPO();
        itemFF12.setId("FF12");
        itemFF12.setTitle("两只全身灰白的猫");
        itemFF12.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF12.setContent("波米拉猫，别称博美拉猫，拉丁学名为Burmilla。这个品种的猫咪在1981年诞生于英国，并在两年后成功参展，参展后的波米拉猫立刻引起人们广泛地关注。其实，波米拉猫的诞生源于一个美丽的结合——淡紫色缅甸猫和银色金吉拉长毛猫“非婚生子”，诞下的孩子便是最初的波米拉猫。最初的波米拉猫是英国米兰达·冯·克奇伯格男爵夫人培育的，而为了让波米拉猫拥有更好的品质，查尔斯和特瑞慈·克拉克夫妇则将另外一种斑纹优美的猫咪与最初的波米拉猫结合，最终演变成现在的波米拉猫。波米拉猫的祖先是金吉拉猫和缅甸猫，所以它们兼具这两种猫咪的优点，比如它们既有金吉拉猫的美貌，也有缅甸猫的友善，因此很多人在跟波米拉猫接触一段时间后都会对它们爱不释手。 环顾四周，你可能会发现并没有人在饲养这种猫咪，这是因为虽然波米拉猫在国外受到不少猫友的注目，但在国内还没有形成流行开的气候，所以如果你对这种猫咪有兴趣，那么你肯定需要花一番功夫才能亲眼看到一只波米拉猫。");
        itemFF12.setTime("2019-02-27");
        itemFF12.setImage("http://img.boqiicdn.com/Data/BK/P/img43941405671164.jpg");
        list.add(itemFF12);

        EsLFPO itemFF13 = new EsLFPO();
        itemFF13.setId("FF13");
        itemFF13.setTitle("家猫已找回");
        itemFF13.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF13.setContent("并不是来自东方的猫就会被称为东方猫，东方猫也是一个猫种，它们是繁殖学家在繁育另外一种猫咪时意外获得的产物。当时，专家们想繁育纯白色的暹罗猫，于是就用暹罗猫跟纯白猫咪配种，现在被我们熟知的东方猫就这样诞生了。跟其他猫咪一样，虽然它们在1962年就诞生，但真正被认定为是一种新品种的猫则是在1972年。");
        itemFF13.setTime("2019-03-05");
        itemFF13.setImage("http://img.boqiicdn.com/Data/BK/P/img14161406022511.jpg");
        list.add(itemFF13);

        EsLFPO itemFF14 = new EsLFPO();
        itemFF14.setId("FF14");
        itemFF14.setTitle("黑猫");
        itemFF14.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF14.setContent("就外观来看，孟买猫宛如一只小型黑豹，但其性格却与外表相反。孟买猫个性温驯柔和，稳重好静，然而它不怕生，感情丰富，很喜欢和人亲热，被人搂抱时喉咙会不停的发出满足的呼噜声。所以这种漂亮的黑色小猫深受人们喜爱，是人类的好伙伴。孟买猫非常好交际，易于和周围环境融为一体，但和其它的猫不一定能相安无事。孟买猫有爱心，特别温柔，爱依偎在主人身边，尽管它对感情是有所克制的。孟买猫喜欢与人作伴，所以不应长时间地冷落它。");
        itemFF14.setTime("2019-03-16");
        itemFF14.setImage("http://img.boqiicdn.com/Data/BK/P/20090529141017.jpg");
        list.add(itemFF14);

        EsLFPO itemFF15 = new EsLFPO();
        itemFF15.setId("FF15");
        itemFF15.setTitle("陆先生遗失的加菲猫");
        itemFF15.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF15.setContent("陆先生遗失的加菲猫已找回，请到招领办认领。");
        itemFF15.setTime("2019-03-11");
        itemFF15.setImage("http://img.boqiicdn.com/Data/BK/P/imagick11101537857806.jpg");
        list.add(itemFF15);

        EsLFPO itemFF16 = new EsLFPO();
        itemFF16.setId("FF16");
        itemFF16.setTitle("长毛小猫");
        itemFF16.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF16.setContent("挪威森林猫（Norwegian Forest Cat），以白话直译的说法，就是在挪威森林里面栖息的，生存的猫，这是斯堪地半岛特有的品种，其起源不明，常常以像妖精的猫出现在北欧的神话之中。挪威森林猫外观与缅因猫相似，与西伯利亚森林猫同列。森林猫生长的环境非常寒冷和恶劣，因此它长有比其它猫更厚密的被毛和强壮的体格。挪威森林猫体大肢壮，奔跑速度极快，不怕日晒雨淋，行走时颈毛和尾毛飘逸，非常美丽。但是，生长在挪威森林内地的猫，数目已逐年减少，一时有绝迹的危机。因而，在进入一九七零年代初期，努力保存、繁殖，就显得格外重要。挪威森林猫（Norwegian Forest Cat），以白话直译的说法，就是在挪威森林里面栖息的，生存的猫，这是斯堪地半岛特有的品种，其起源不明，常常以像妖精的猫出现在北欧的神话之中。挪威森林猫外观与缅因猫相似，与西伯利亚森林猫同列。森林猫生长的环境非常寒冷和恶劣，因此它长有比其它猫更厚密的被毛和强壮的体格。挪威森林猫体大肢壮，奔跑速度极快，不怕日晒雨淋，行走时颈毛和尾毛飘逸，非常美丽。但是，生长在挪威森林内地的猫，数目已逐年减少，一时有绝迹的危机。因而，在进入一九七零年代初期，努力保存、繁殖，就显得格外重要。挪威森林猫（Norwegian Forest Cat），以白话直译的说法，就是在挪威森林里面栖息的，生存的猫，这是斯堪地半岛特有的品种，其起源不明，常常以像妖精的猫出现在北欧的神话之中。挪威森林猫外观与缅因猫相似，与西伯利亚森林猫同列。森林猫生长的环境非常寒冷和恶劣，因此它长有比其它猫更厚密的被毛和强壮的体格。挪威森林猫体大肢壮，奔跑速度极快，不怕日晒雨淋，行走时颈毛和尾毛飘逸，非常美丽。但是，生长在挪威森林内地的猫，数目已逐年减少，一时有绝迹的危机。因而，在进入一九七零年代初期，努力保存、繁殖，就显得格外重要。");
        itemFF16.setTime("2019-03-13");
        itemFF16.setImage("http://img.boqiicdn.com/Data/BK/P/imagick76571533894282.png");
        list.add(itemFF16);

        EsLFPO itemFF17 = new EsLFPO();
        itemFF17.setId("FF17");
        itemFF17.setTitle("外形有些凶悍，个头偏大的猫");
        itemFF17.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF17.setContent("阿比西尼亚猫又称兔猫、埃塞俄比亚猫、阿比等，英文名为Abyssinian，现分布于全球各地。人们通常会通过猫咪的名字来判断猫咪的来源，但阿比西尼亚猫的起源却跟阿比西尼亚似乎有点距离。阿比西尼亚是埃塞俄比亚的旧称，但很多人认为阿比西尼亚猫来自古埃及。");
        itemFF17.setTime("2019-01-13");
        itemFF17.setImage("http://img.boqiicdn.com/Data/BK/P/img33571405663401.jpg");
        list.add(itemFF17);

        EsLFPO itemFF18 = new EsLFPO();
        itemFF18.setId("FF18");
        itemFF18.setTitle("招领卷毛大猫");
        itemFF18.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF18.setContent("德文卷毛猫，又叫德文帝王猫，英文名为Devon Rex，最初被人们发现于英国德文郡。在德文卷毛猫出现前，英国就在柯尼斯郡发现了柯尼斯猫，柯尼斯猫也是卷毛猫，因此人们一度以为它们是近亲。不过后来的试验证明，它们并无血缘关系，因此人们便用德文卷毛猫来命名这种新的卷毛猫。德文卷毛猫，又叫德文帝王猫，英文名为Devon Rex，最初被人们发现于英国德文郡。在德文卷毛猫出现前，英国就在柯尼斯郡发现了柯尼斯猫，柯尼斯猫也是卷毛猫，因此人们一度以为它们是近亲。不过后来的试验证明，它们并无血缘关系，因此人们便用德文卷毛猫来命名这种新的卷毛猫。");
        itemFF18.setTime("2019-03-09");
        itemFF18.setImage("http://img.boqiicdn.com/Data/BK/P/img81481405673729.jpg");
        list.add(itemFF18);

        EsLFPO itemFF19 = new EsLFPO();
        itemFF19.setId("FF19");
        itemFF19.setTitle("太湖邻岸的家猫");
        itemFF19.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF19.setContent("家猫会很好地控制力度,不会把主人咬伤,但可以起到良好的警告作用。如何阻止这一切发生? 不要“越界”抚摸。");
        itemFF19.setTime("2019-03-11");
        itemFF19.setImage("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1828695626,1771733300&fm=85&app=57&f=JPEG?w=121&h=75&s=1080D95D0C2076175DA0047C0300C062");
        list.add(itemFF19);

        EsLFPO itemFF20 = new EsLFPO();
        itemFF20.setId("FF20");
        itemFF20.setTitle("一只可爱的小猫咪");
        itemFF20.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFF20.setContent("特别喜欢把自己躲起来的动物，当家猫自己躲起来的时候，你作为它的主人千万不要强行把家猫拖拽出来。特别喜欢把自己躲起来的动物，当家猫自己躲起来的时候，你作为它的主人千万不要强行把家猫拖拽出来。特别喜欢把自己躲起来的动物，当家猫自己躲起来的时候，你作为它的主人千万不要强行把家猫拖拽出来。特别喜欢把自己躲起来的动物，当家猫自己躲起来的时候，你作为它的主人千万不要强行把家猫拖拽出来。特别喜欢把自己躲起来的动物，当家猫自己躲起来的时候,你作为它的主人千万不要强行把家猫拖拽出来。");
        itemFF20.setTime("2019-03-19");
        itemFF20.setImage("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=4204561101,372450854&fm=85&app=57&f=JPEG?w=121&h=75&s=01F05B955BC64743C455F8F60300C030");
        list.add(itemFF20);

        EsLFPO itemFFF1 = new EsLFPO();
        itemFFF1.setId("FFF1");
        itemFFF1.setTitle("李刚泉先生找到luncy猫");
        itemFFF1.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFFF1.setContent("孟买猫 埃及猫 土耳其梵猫 短腿猫 雪鞋猫 缅甸猫 缅因猫 伯曼猫 玳瑁猫 安哥拉猫 山东狮子猫 布偶猫 金吉拉 西伯利亚猫 马恩岛猫");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
        itemFFF1.setTime(sdf3.format(new Date()));
        itemFF1.setImage("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1102051843,416264147&fm=85&app=57&f=JPEG?w=121&h=75&s=F158A9775F224E1354CDA5CE0100E0B1");
        list.add(itemFFF1);

        EsLFPO itemFFF2 = new EsLFPO();
        itemFFF2.setId("FFF2");
        itemFFF2.setTitle("郝女士在古街闹区发现宠物猫Luncy");
        itemFFF2.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFFF2.setContent("郝女士在古街闹区发现宠物猫Luncy。");
        itemFFF2.setTime("2019-02-25");
        itemFFF2.setImage("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=4243275056,2400637208&fm=58");
        list.add(itemFFF2);

        EsLFPO itemFFF3 = new EsLFPO();
        itemFFF3.setId("FFF3");
        itemFFF3.setTitle("虎头脸猫");
        itemFFF3.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFFF3.setContent("虎头脸猫：体型，全部小型猫中型猫大型猫。毛长，花纹。");
        itemFFF3.setTime("2019-03-05");
        itemFFF3.setImage("https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=1220334305,3828257907&fm=85&s=41AB9355065153D65880B0DC030000313");
        list.add(itemFFF3);

        EsLFPO itemFFF4 = new EsLFPO();
        itemFFF4.setId("FFF4");
        itemFFF4.setTitle("酣睡小萌猫");
        itemFFF4.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFFF4.setContent("目前我国最普遍的猫咪,而且也是绝大多数人都喜欢饲养的,猫咪的性格虽然不那么粘人。");
        itemFFF4.setTime("2019-03-11");
        itemFFF4.setImage("https://ss0.baidu.com/73x1bjeh1BF3odCf/it/u=1736646433,2340119276&fm=85&s=7BA78D4316211117DC09118C0300E091");
        list.add(itemFFF4);

        EsLFPO itemFFF5 = new EsLFPO();
        itemFFF5.setId("FFF5");
        itemFFF5.setTitle("招领红小胖猫");
        itemFFF5.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFFF5.setContent("红小胖snoopy是一只红色梵纹异国短毛猫，最大的爱好就是吃吃睡睡，出名起因是该猫的主人给Snoopy摆弄出了各种造型，发到网上后让不少网友一下子就迷上了这只萌猫。这只名叫“Snoopy”的萌猫在微博上抢尽了风头。“2011年5月，偶在北京出生了。偶有一个哥哥一个妹妹。15天大的我和兄弟姐妹，还有偶滴世界冠军爸爸的合影。”2011年11月，“红小胖Snoopy”正式在新浪开设了自己的微博。每天在微博上晒各种萌照便成了这只加菲猫的主要生活。当然，作为一只萌猫，“猫生”的丰富活动远远不止这些。爱睡小胖登上杂志小胖登上杂志懒觉，爱舔毛，所有猫的爱好在这只Snoopy身上都能找到。");
        itemFFF5.setTime("2019-03-11");
        itemFFF5.setImage("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2627548196,3563163525&fm=58&bpow=429&bpoh=496");
        list.add(itemFFF5);

        EsLFPO itemFFF6 = new EsLFPO();
        itemFFF6.setId("FFF6");
        itemFFF6.setTitle("丑猫儿");
        itemFFF6.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFFF6.setContent("丑猫是出生于美国斯坦森埃克塞特动物医院的一只猫，全身光秃秃，看起来像胶皮，只有胸前长着一些粗糙的毛，而且尾巴跟老鼠尾巴一样，脸上皱巴巴的，因此得名“丑猫”。北京时间2009年3月6日消息，美国斯坦森埃克塞特动物医院的一只猫咪长的奇丑无比，号称最丑的猫。“丑猫”人气越来越旺。");
        itemFFF6.setTime("2019-02-03");
        itemFFF6.setImage("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1071731415,719947858&fm=58&bpow=504&bpoh=400");
        list.add(itemFFF6);

        EsLFPO itemFFF7 = new EsLFPO();
        itemFFF7.setId("FFF7");
        itemFFF7.setTitle("瞪着圆溜溜的大眼睛的小胖猫");
        itemFFF7.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFFF7.setContent("可爱,神秘,高冷这些词好像都是用来形容它们的。可爱,神秘,高冷这些词好像都是用来形容它们的,可爱,神秘,高冷这些词好像都是用来形容它们的,可爱,神秘,高冷这些词好像都是用来形容它们的,可爱,神秘,高冷这些词好像都是用来形容它们的,可爱,神秘,高冷这些词好像都是用来形容它们的。");
        itemFFF7.setTime("2019-03-03");
        itemFFF7.setImage("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=506520105,3710101440&fm=85&app=57&f=JPEG?w=121&h=75&s=CAAAA345267347982DA0A8C80300F022");
        list.add(itemFFF7);

        EsLFPO itemFFF8 = new EsLFPO();
        itemFFF8.setId("FFF8");
        itemFFF8.setTitle("无锡佛山景区内遗失的金吉拉猫");
        itemFFF8.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFFF8.setContent("金吉拉猫原意是一种绒鼠的名称，祖先是安哥拉猫与波斯猫。金吉拉是最早纯人工育种，经过多年精心繁育而成的一个特色猫种。原产国在英国。特征是毛尖颜色不如银色渐层猫的清楚，属颜色较浅的猫。毛型厚长、柔滑。个性温顺。由波斯猫经过人为刻意培育而成，养猫界俗称“人造猫”。金吉拉猫于1894年首次被作为一个独立的品种出现在英国水晶宫猫展上。它们是猫中贵族，其性情温文尔雅，聪明敏捷，善解人意，少动好静，叫声尖细柔美，爱撒娇，举止风度翩翩，天生一副娇生惯养之态，给人一种华丽高贵的感觉。历来深受世界各地爱猫人士的宠爱，是长毛猫的代表。金吉拉四肢较短，体态比波斯猫稍娇小但显得更灵巧。");
        itemFFF8.setTime("2019-03-01");
        itemFFF8.setImage("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3358496960,2854457931&fm=58&bpow=607&bpoh=516");
        list.add(itemFFF8);

        EsLFPO itemFFF9 = new EsLFPO();
        itemFFF9.setId("FFF9");
        itemFFF9.setTitle("招领美国美国短毛猫");
        itemFFF9.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFFF9.setContent("招领一只美国短毛猫");
        itemFFF9.setTime("2019-03-22");
        itemFFF9.setImage("");
        list.add(itemFFF9);

        EsLFPO itemFFF10 = new EsLFPO();
        itemFFF10.setId("FFF10");
        itemFFF10.setTitle("唐女士找到一只喜马拉雅猫");
        itemFFF10.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFFF10.setContent("喜马拉雅猫，这个名字是由于它和叫这个名字的兔子的长相十分相似的缘故，而和喜马拉雅山无关。喜马拉雅猫融合了波斯猫的轻柔、妩媚和反应灵敏，暹罗猫的聪明和温雅。它有波斯猫的体态和长毛，暹罗猫的毛色和眼睛");
        itemFFF10.setTime("2019-03-03");
        itemFFF10.setImage("");
        list.add(itemFFF10);

        EsLFPO itemFFF11 = new EsLFPO();
        itemFFF11.setId("FFF11");
        itemFFF11.setTitle("景茹在上海静安区找到萌宠小猫一只");
        itemFFF11.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFFF11.setContent("在养之前了解一个品种总是很好的,以确保它们适合你的家。如果你想了解更多。");
        itemFFF11.setTime("2019-01-15");
        itemFFF11.setImage("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=4122939090,3059265797&fm=85&app=57&f=JPEG?w=121&h=75&s=092BD9154CE2720330B89CE403004061");
        list.add(itemFFF11);

        EsLFPO itemFFF12 = new EsLFPO();
        itemFFF12.setId("FFF12");
        itemFFF12.setTitle("温顺安静的猫中小公主-蓝猫");
        itemFFF12.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFFF12.setContent("俄罗斯蓝猫（Russian Blue），历史上曾被称做阿契安吉蓝猫，曾有过三种不同的名字。最初是阿契安吉蓝猫，直到20世纪40年代才有现在的名字，另外有段时间它则叫做马耳他猫。证据显示，这种猫确实原产于俄罗斯，因为已在俄罗斯寒带地区发现了同种的猫。俄罗斯蓝猫体型细长，大而直立的尖耳朵，脚掌小而圆，走路像是用脚尖在走。身上披着银蓝色光泽的短被毛，配上修长苗条的体型和轻盈的步态，尽显一派猫中的贵族风度。祖先起源于寒冷的西伯利亚地带，很多地方称它为“冬天的精灵”。俄罗斯蓝猫的衍变历史较为悠久，它们的祖先“阿契安吉蓝猫”早在17世纪就从俄罗斯的港口被带往英国。二次大战以后，俄罗斯蓝猫的数量急剧减少，为了把其种群数量恢复，培育者用暹罗猫与其进行杂交，使得俄罗斯蓝猫的外形带有一些东方情调。最初一直到二战之后在英格兰与斯堪地那维亚地区之外才有其他地区开始培育俄国蓝猫；由于二战期间交通中断，造成纯种蓝猫血缘狭窄化， 所以有一些培育者开始进行俄国蓝猫与暹罗猫的混种交配。尽管这个混种猫在二战前已经存在于美国，不过也要到战后由培育者培育成今天在美国境内所谓的“现代俄国蓝猫”。尽管培育者已经在两次大战中以及战争期间以有限的基础上利用俄国蓝猫培育出新的猫种，例如哈瓦那棕猫(毛色接近哈瓦那雪茄，具有暹逻猫的血统) 或者是增加新的猫系，例如尼伯龙猫(其名源自于德文 “有如雾霜般迷蒙的生物”，可想见其外型优美程度)，俄国蓝猫的血统依旧维持在灰蓝色相间短毛的猫种上。");
        itemFFF12.setTime("2019-02-20");
        itemFFF12.setImage("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=4176102836,485168200&fm=179&app=42&f=JPEG?w=121&h=140");
        list.add(itemFFF12);

        EsLFPO itemFFF13 = new EsLFPO();
        itemFFF13.setId("FFF13");
        itemFFF13.setTitle("瘦小暹罗猫");
        itemFFF13.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFFF13.setContent("暹（xiān）罗猫是世界著名的短毛猫，也是短毛猫的代表品种。种族原产于暹罗（今泰国），故名暹罗猫。在200多年前，这种珍贵的猫仅在泰国的皇宫和大寺院中饲养，是足不出户的贵族。暹罗猫能够较好适应主人当地的气候，且性格刚烈好动，机智灵活，好奇心特强，善解人意。头细长呈楔形。头盖平坦，从侧面看，头顶部至鼻尖成直线。脸形尖而呈V字形，口吻尖突呈锐角,从吻端至耳尖形成V字形。鼻梁高而直，从鼻端到耳尖恰为等边三角形。两颊瘦削，齿为剪式咬合。耳朵大，基部宽，耳端尖、直立。眼睛大小适中，杏仁形，为蓝色，或深或浅。从内眼角至眼梢的延长线，与耳尖构成V字形。眼微凸。尾长而细，尾端尖略卷曲。长度与后肢相等。柔韧性好，肌肉发达，身材苗条，长得棱角分明，腿细而长。掌很小，呈椭圆形。尾巴长而美丽，尾端尖。");
        itemFFF13.setTime("2019-03-11");
        itemFFF13.setImage("");
        list.add(itemFFF13);

        EsLFPO itemFFF14 = new EsLFPO();
        itemFFF14.setId("FFF14");
        itemFFF14.setTitle("感谢王小姐找到的雪鞋猫");
        itemFFF14.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFFF14.setContent("它既有暹罗猫那样细长而柔软的身躯，又具有美国短毛猫健壮的体魄。有些雄猫甚至超过5公斤。刚出生的雪鞋猫全身洁白，须等两年后才出现班纹。本来以暹逻猫的毛色为主，但目前亦已培育出各种毛色的猫种来。雪鞋猫头部稍宽，呈楔子彡；高颊骨，双目的前端较尖削；一对大大的杏仁眼，呈深而鲜艳的蓝色。鼻梁挺直，很奇特的是，只有一个鼻孔，这个特征是独一无二的。雪鞋猫的体毛较短，脊背部分毛的颜色较多，有深有浅。嘴巴和胸腹是白色的，象是戴了口罩和围着围裙的厨师。四肢也是白色的，如同穿了雪鞋一般，其名称也由此而来。毛色：暗褐色，蓝色，巧克力色和淡紫色。肢端颜色应当和身体颜色有鲜明的反差，而身体颜色应较肢端颜色淡。眼睛颜色应该是蓝色的。前额有倒置V形斑纹，爪上有白色斑纹。理想状态的手套应为四只均匀。鼻头应为白色，没有任何着色，且颜色鲜明，或为多色。白色区重叠于传统暹罗猫图案之上，带有这种斑纹颜色的品种正越来越受到培育。前腿上\"白靴\"必须达到脚踝，并在后腿上正好延伸至后腿的跗关了下年龄较大些的猫往往颜色深一点，但关键是重点色和体色要形成对比。海豹色和白重点色猫。极其活泼，个性很强，是优秀的猎手。贪玩，是孩子的、佳伴。温柔，对主人很有感情。比暹罗猫更少一点专横。活泼聪明，感情丰富，不怕生人；温顺友好，渴求主人的宠爱，喜欢与主人玩耍；叫声甜蜜，举止得当；爱干净。波曼猫的性格开朗头脑聪慧，它有非常充实的感情，对人温顺态度友好，不怕陌生人；喜欢被主人宠爱，爱和主人嬉戏玩耍；有甜蜜的叫声，行为举止非常大方得体；波曼猫爱干净，愉快的生活在舒适的家中，在天气好时喜欢到花园庭院等地方散步。");
        itemFFF14.setTime("2019-03-15");
        itemFFF14.setImage("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=81184367,201167788&fm=58&bpow=422&bpoh=515");
        list.add(itemFFF14);

        EsLFPO itemFFF15 = new EsLFPO();
        itemFFF15.setId("FFF15");
        itemFFF15.setTitle("最古老的品种猫之一：安哥拉猫");
        itemFFF15.setUrl("https://mail.qq.com/cgi-bin/loginpage");
        itemFFF15.setContent("16世纪传入欧洲，进入意大利和法国，而后传入英国，主要分布在法国和英国，是当时最受欢迎的长毛品种。19世纪中叶，由于波斯猫的出现，安哥拉猫的地位逐渐降低。目前，安哥拉猫主要分布在土耳其，其他地方数量已很少 。 全身有丝般的长毛，有褐、红、黑、白四种毛色，通常认为白色的安哥拉猫最纯正。 该品种猫动作敏捷，性格特立独行，不喜欢人抚、抱。安哥拉猫会生仔，一窝有4仔，小猫出生后就能睁眼，而且顽皮可爱。该猫最逗人之处是特别喜欢水，能在小溪或浴池中畅游且憩态可掬。");
        itemFFF15.setTime("2019-03-20");
        itemFFF15.setImage("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2088123721,2503287690&fm=58&bpow=360&bpoh=500");
        list.add(itemFFF15);

        return list;
    }


    @Test
    public void testAnalyzer() throws IOException {
        String str = "寻找mate7手机一部";
        //String str = "遗失家猫";

        /*
        //Analyzer anlyzer = new WhitespaceAnalyzer();  //空格分词

        Analyzer anlyzer = new CJKAnalyzer();  //两两分词

        StringReader stringReader = new StringReader(str);
        //TokenStream tokenStream = anlyzer.tokenStream("",stringReader);
        TokenStream tokenStream = anlyzer.tokenStream("title",str);
        tokenStream.reset();
        CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
        while(tokenStream.incrementToken()){
            System.out.println(term.toString());
        }
        stringReader.close();
        tokenStream.close();
        */


        /*List<String> ss = new ArrayList<>();
        IKSegmenter ik = new IKSegmenter(new StringReader(str), false);
        try {
            Lexeme word = null;
            while((word=ik.next())!=null) {
                //result.append(word.getLexemeText()).append("@-@");
                ss.add(word.getLexemeText().toUpperCase());
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        System.out.println(ss.toString());*/

        AnalyzeRequestBuilder ikRequest = new AnalyzeRequestBuilder(client, AnalyzeAction.INSTANCE, ElasticSearchConstants.ES_DB, str);
        ikRequest.setTokenizer("ik_max_word");
        List<AnalyzeResponse.AnalyzeToken> ikTokenList = ikRequest.execute().actionGet().getTokens();
        //循环赋值
        List<String> ikList = new ArrayList<>();
        ikTokenList.forEach(ikToken -> {
            ikList.add(ikToken.getTerm());
            if (ikToken.getTerm().length() > 1) { //过滤串
                System.out.println(ikToken.getTerm());
            }
        });
        System.out.println("all ik phrase: " + ikList.toString());




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
                .startObject("properties") //设置自定义字段
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
                .field("analyzer", "ik_max_word") //索引时使用 ik_max_word
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
                .startObject("image")
                .field("type", "keyword")
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
                .startObject("properties") //设置自定义字段
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
                                        .field("image", item.getImage())
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
                                        .field("image", item.getImage())
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
