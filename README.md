# framework-base

>2019-11-13 整合了spring-boot,mybatis,Mymapper,Pagehelp，logback+sl4j日志和门面<br> 
>2019-11-14 整合了redis，开发了常用的数据字典功能<br> 
>2019-11-15 提交部分工具类<br> 
  >>* SnowFlakeIds 分布式主键生成<br> 
  >>* ReflectUtil  bean-map的转换<br> 
  >>* BeanUtil     继承BeanUtils的工具类<br> 
  >>* DateUtil     时间工具类<br> 
  >>* JsonMapper   json工具类，但一般建议优先使用fast-json<br> 
  >>* ObjectUtils  对象工具类<br> 
  >>* StringUtil   字符串工具类<br> 

>2019-11-18 整合jwt，对接口进行权限的控制<br> 
>2020-01-12 整合了uncode-schedule 集成了zookeeper
  >>* （1）确保所有任务在集群中不重复的执行。 
  >>* （2）单节点故障时，任务能够自动转移到其他节点继续执行。 
  >>* （3）支持动态启动、停止和删除任务。 
  >>* （4）支持添加机器ip黑名单。 
  >>* （5）简单的管理页面。

>2020-03-24 整合了微信支付 <br>
>2020-04-24 整合了kafka
