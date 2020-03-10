# schedule

>2020 准备集成zookeeper 和 un-code 主要目的是想学习下zookeeper 

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
