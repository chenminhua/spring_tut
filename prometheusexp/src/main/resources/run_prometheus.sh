docker run -d --name=prometheus -p 9090:9090 -v $(pwd)/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml'

或者下载一个 prometheus 包，然后本地启动

prometheus 查看 qps 的函数 rate(http_server_requests_seconds_count[10s])

教程 https://www.callicoder.com/spring-boot-actuator-metrics-monitoring-dashboard-prometheus-grafana/



docker run -d --name=grafana -p 3000:3000 grafana/grafana
grafana 默认用户名 admin 默认密码 admin