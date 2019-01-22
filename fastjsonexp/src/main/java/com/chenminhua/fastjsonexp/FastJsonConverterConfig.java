package com.chenminhua.fastjsonexp;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FastJsonConverterConfig {
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConventers() {
        // 1.需要定义一个convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        //2.创建配置信息，加入配置信息：比如是否需要格式化返回的json
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);

        //3.converter中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);

        /**
         *  设置json 返回格式和编码方式 处理乱码问题
         */
        List<MediaType> mediaTypeList=new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(mediaTypeList);

        //4.convert添加到converters当中
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }
}
