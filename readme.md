# CyNews

![](https://img.shields.io/badge/SpringBoot-2.3.4.RELEASE-green)![](https://img.shields.io/badge/SpringCloud%20Gateway-Hoxton.SR5-green) ![](https://img.shields.io/badge/Apache%20Dubbo-2.7.7-blue)![](https://img.shields.io/badge/Alibaba%20Nacos-1.4.0-orange)![](https://img.shields.io/badge/Alibaba%20RocketMQ-%204.5.2-orange)![](https://img.shields.io/badge/Alibaba%20Sentinel-1.8.0-blue)



课程设计,基于SpringCloud Alibaba Dubbo解决方案的新闻+社区 **(目前只有新闻,没有社区 - .-)**

#### 小组成员

前端: **fakeou@https://github.com/fakeou**

后端: **6yi@https://github.com/6yi  & yj@https://github.com/979399417**

### 后端组件选用

| 组件       | 方案                                        |
| ---------- | ------------------------------------------- |
| 注册中心   | Aliababa Nacos                              |
| 熔断器     | Aliababa Sentinel                           |
| 远程调用   | Apache Dubbo                                |
| 服务网关   | SpringCloud Gateway                         |
| 分布式配置 | Aliababa Nacos                              |
| 消息队列   | Alibaba RocketMQ                            |
| 对象储存   | 腾讯云Cos                                   |
| 数据库     | Mysql:5.7                                   |
| 缓存数据库 | Redis                                       |
| 鉴权       | 自实现Fliter,后面部分模块换成SpringSecurity |



## 架构图

![](https://gitee.com/lzhengycy/Pic/raw/master/img/绘图1.jpg)



### 功能

- [x] 用户注册登录

- [x] 热点新闻

- [x] 点赞

- [x] 评论

- [x] 后台管理

- [ ] 社区

- [ ] 积分商城

- [ ] 第三方应用登录

- [ ] 即时通讯

- [ ] .........

  

### 后端模块功能

| 模块名                | 功能             |
| --------------------- | ---------------- |
| cy-news-api_gateway   | 网关             |
| cy-news-dependencies  | 依赖             |
| cy-news-MQ-server     | 统一消息处理模块 |
| cy-news-api           | dubbo接口        |
| cy-news-news-provider | 新闻服务提供模块 |
| cy-news-news-server   | 新闻服务消费模块 |
| cy-news-user-provider | 用户服务提供模块 |
| cy-news-user-server   | 用户消费提供模块 |
| cy-news-common        | 公共类           |
| cy-news-admin         | 后台管理模块     |



