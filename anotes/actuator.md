[文档](https://docs.spring.io/spring-boot/docs/2.0.x/actuator-api/html/)
[教程](https://www.baeldung.com/spring-boot-actuators)

默认actuator只暴露 /health 和 /info，
本地开发环境中我们可以设置management.endpoints.web.exposure.include=*

## 原生端点

#### 应用配置类： 获取应用配置，环境变量，自动化配置报告等信息。
/actuator/autoconfig 自动化配置报告
/actuator/beans     查看beans
/actuator/env       应用所有可用的环境变量，包括环境变量，jvm属性，应用配置属性，命令行参数等等。
/actuator/mappings  返回controller映射关系报告。
/actuator/info      返回一些应用自定义的信息。默认为一个空的json，我们可以在application.properties中通过info前缀来设置一些属性。

#### 度量指标类： 比如内存，线程池，http请求统计等等。
/actuator/health  查看健康情况
/actuator/heapdump  dump堆内存
/actuator/metrics   可以查看的metrics （内存，线程，垃圾回收信息等等）
/actuator/metrics/jvm.threads.live   线程数
/actuator/trace    用来返回基本的http跟踪信息。

#### 操作控制类： 提供了对应用的关闭等操作功能。





### /actuator/info
默认是空的，你可以在application.yaml中写相关信息，也可以在代码中编写。

```$xslt
@Component
public class TotalUsersInfoContributor implements InfoContributor {
 
    @Autowired
    UserRepository userRepository;
 
    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Integer> userDetails = new HashMap<>();
        userDetails.put("active", userRepository.countByStatus(1));
        userDetails.put("inactive", userRepository.countByStatus(0));
 
        builder.withDetail("users", userDetails);
    }
}
```