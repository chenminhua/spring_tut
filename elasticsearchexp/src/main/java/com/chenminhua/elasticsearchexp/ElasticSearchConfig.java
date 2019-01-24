package com.chenminhua.elasticsearchexp;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;

@Configuration
public class ElasticSearchConfig {

    /**
     * elk集群地址
     */
    @Value("${elasticsearch.ip}")
    private String hostName;
    /**
     * 端口
     */
    @Value("${elasticsearch.port}")
    private String port;
    /**
     * 集群名称
     */
    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    /**
     * 连接池
     */
    @Value("${elasticsearch.pool}")
    private String poolSize;

    @Bean
    public Client client() throws Exception {

        Settings esSettings = Settings.builder().put("cluster.name", clusterName)
                .put("client.transport.sniff", false)
                // 如果为true则为增加嗅探机制，找到ES集群
                .put("thread_pool.search.size", Integer.parseInt(poolSize))//增加线程池个数，暂时设为5
                .build();

        return new PreBuiltTransportClient(esSettings).
                addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(hostName), Integer.parseInt(port)));
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }
}
