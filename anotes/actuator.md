<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>


[文档](https://docs.spring.io/spring-boot/docs/2.0.x/actuator-api/html/)
[教程](https://www.baeldung.com/spring-boot-actuators)

默认actuator只暴露 /health 和 /info，本地开发环境中我们可以设置management.endpoints.web.exposure.include=*


/actuator/health  查看健康情况
/actuator/heapdump  dump堆内存
/actuator/beans     查看beans
/actuator/metrics   可以查看的metrics
/actuator/metrics/jvm.threads.live   线程数

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