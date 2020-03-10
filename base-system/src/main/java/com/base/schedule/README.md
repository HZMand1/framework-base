# schedule

>2020 准备集成zookeeper 和 un-code 主要目的是想学习下zookeeper  <br/>
>分布式定时任务框架Uncode-Schedule <br/>
>它的主要功能包括： <br/>
 （1）确保所有任务在集群中不重复的执行。 <br/>
 （2）单节点故障时，任务能够自动转移到其他节点继续执行。 <br/>
 （3）支持动态启动、停止和删除任务。 <br/>
 （4）支持添加机器ip黑名单。 <br/>
 （5）简单的管理页面。 <br/>

单机的配置很简单，就不写了。

这里是windows下的zookeeper 的config配置 <br/>
机器1：<br/>
    # The number of milliseconds of each tick <br/>
    tickTime=2000 <br/>
    # The number of ticks that the initial <br/> 
    # synchronization phase can take <br/>
    initLimit=10 <br/>
    # The number of ticks that can pass between <br/> 
    # sending a request and getting an acknowledgement <br/>
    syncLimit=5 <br/>
    # the directory where the snapshot is stored. <br/>
    dataDir=c:/tmp/zk1/data <br/>
    # the port at which the clients will connect <br/>
    clientPort=2181 <br/>
    server.1=localhost:2887:3887 <br/>
    server.2=localhost:2888:3888 <br/>
    
机器2：<br/>
    # The number of milliseconds of each tick <br/>
    tickTime=2000 <br/>
    # The number of ticks that the initial <br/> 
    # synchronization phase can take <br/>
    initLimit=10 <br/>
    # The number of ticks that can pass between <br/> 
    # sending a request and getting an acknowledgement <br/>
    syncLimit=5 <br/> 
    # the directory where the snapshot is stored. <br/>
    dataDir=c:/tmp/zk2/data <br/>
    # the port at which the clients will connect <br/>
    clientPort=2182 <br/>
    server.1=localhost:2887:3887 <br/>
    server.2=localhost:2888:3888 <br/>
    
另外记得在机器中创建myid的文件夹
里面填写的内容就是对应service后面的.1 和 .2
