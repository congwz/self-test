package com.viverselftest.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Congwz on 2019/3/25.
 */
@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.host}")
    private String esHost;

    @Value("${elasticsearch.port}")
    private int esPort;

    @Value("${elasticsearch.cluster.name}")
    private String esClusterName;

    @Bean
    public TransportClient esClient() throws UnknownHostException {
        //Settings settings = Settings.builder().put("cluster.name", "my-application").build();// 集群名

        //创建client
        /*client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));*/

        Settings settings = Settings.builder()
                .put("cluster.name", esClusterName)
                //.put("client.transport.sniff", true)
                .build();

        InetSocketTransportAddress master = new InetSocketTransportAddress(   //节点
                InetAddress.getByName(esHost), esPort);

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(master);

        return client;

    }
}
