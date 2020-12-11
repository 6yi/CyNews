# CyNews
课程设计,基于SpringCloud Alibaba Dubbo解决方案的新闻+社区**(目前只有新闻,没有社区 - .-)**

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





