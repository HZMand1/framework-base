package com.base.utils.key;

import java.util.Random;


/**TODO  雪花算法生成唯一主键ID
 * @author:      吕观林
 * @date:        2019年10月18日 上午9:15:18 
 * @version      V1.0   
 * @copyright    广东跑合中药材有限公司 Copyright (c) 2019
 */
@SuppressWarnings("all")
public class SnowFlakeIds {
	
	private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long twepoch = 1288834974657L;
     //Thu, 04 Nov 2010 01:42:54 GMT
    private long workerIdBits = 0L;
     //节点ID长度
    private long datacenterIdBits = 0L;
     //数据中心ID长度
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
     //最大支持机器节点数0~31，一共32个
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
     //最大支持数据中心节点数0~31，一共32个
    private long sequenceBits = 1L;
     //序列号12位
    private long workerIdShift = sequenceBits;
     //机器节点左移12位
    private long datacenterIdShift = sequenceBits + workerIdBits;
     //数据中心节点左移17位
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
     //时间毫秒数左移22位
    private long sequenceMask = -1L ^ (-1L << sequenceBits);
     //最大为4095
    private long lastTimestamp = -1L;
    
    private static class SnowFlakeHolder {
        private static final SnowFlakeIds instance = new SnowFlakeIds();
    }
    
    public static SnowFlakeIds get(){
        return SnowFlakeHolder.instance;
    }

    public SnowFlakeIds() {
        this(0L, 0L);
    }

    public SnowFlakeIds(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }
    
    public synchronized long nextId() {
        long timestamp = timeGen();
        //获取当前毫秒数
        //如果服务器时间有问题(时钟后退) 报错。
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //如果上次生成时间和当前时间相同,在同一毫秒内
        if (lastTimestamp == timestamp) {
            //sequence自增，因为sequence只有12bit，所以和sequenceMask相与一下，去掉高位
            sequence = (sequence + 1) & sequenceMask;
            //判断是否溢出,也就是每毫秒内超过4095，当为4096时，与sequenceMask相与，sequence就等于0
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
                 //自旋等待到下一毫秒
            }
        } else {
            sequence = 0L;
             //如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加
        }
        lastTimestamp = timestamp;
        // 最后按照规则拼出ID。
        // 000000000000000000000000000000000000000000  00000            00000       000000000000
        // time                                      datacenterId      workerId     sequence
         // return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
         //        | (workerId << workerIdShift) | sequence;
         
         long longStr= ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
         // System.out.println(longStr);
         return longStr;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
    
    /**TODO 生成随机数当作getItemID
     * @param n
     * @return String
     * @author:    吕观林
     * @date:      2019年11月4日 下午2:06:29 
     * @throws   
     */ 
    public String getItemID(int n ){
        String val = "";
        Random random = new Random();
        for ( int i = 0; i < n; i++ )
        {
            String str = random.nextInt( 2 ) % 2 == 0 ? "num" : "char";
            if ( "char".equalsIgnoreCase( str ) )
            { // 产生字母
                int nextInt = random.nextInt( 2 ) % 2 == 0 ? 65 : 97;
                // System.out.println(nextInt + "!!!!"); 1,0,1,1,1,0,0
                val += (char) ( nextInt + random.nextInt( 26 ) );
            }
            else if ( "num".equalsIgnoreCase( str ) )
            { // 产生数字
                val += String.valueOf( random.nextInt( 10 ) );
            }
        }
        return val;
    }
    
}
