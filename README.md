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
> 
> 
> 2021-03-01 整合了elasticsearch 7.8 版本，提供了ESUtil工具类，List和page的查询<br>
> 后期自己也可以拓展 代码引入:
```java
    @Autowired
    @Qualifier("highLevelClient")
    private RestHighLevelClient client;
```
```java
 @TargetDataSource(value = "center-r")
    @Override
    public AjaxResult queryInterfaceConnectByType(DataStatisticsVo dataStatisticsVo) {
        Long loginUserId = UserUtil.getUserEntity().getUserId();
        if (ObjectUtils.isNullObj(loginUserId)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "用户ID不能为空");
        }
        DataStatisticsVo dataStatisticsVo1 = BeanCopy.objectCopy(dataStatisticsVo,DataStatisticsVo.class);
        dataStatisticsVo1.setStartAddDate(null);
        dataStatisticsVo1.setEndAddDate(null);
        List<Map<Object, Object>> list = dataStatisticsMapper.queryNewInterfaceByType(dataStatisticsVo1);
        if (CollectionUtil.isEmpty(list)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取数据成功", list);
        }
        // 查询条件
        BoolQueryBuilder boolQueryBuilder = getBoolQueryBuilder(dataStatisticsVo, loginUserId);
        // 分组
        TermsAggregationBuilder labelIdGroup = AggregationBuilders.terms("connectionTimeGroup").field("connectTimeStr")
                .subAggregation(AggregationBuilders.terms("labelIds").field("labelId"));

        // 分组排序
        labelIdGroup.order(Terms.Order.term(true));

        List<Map<String, Object>> dateMap = new ArrayList();
        try {
            Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));

            SearchRequest searchRequest = new SearchRequest(new String[]{DataCenterCollections.CONNECTION_INTERFACE_LIST.toLowerCase()});
            searchRequest.scroll(scroll);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(100);
            searchSourceBuilder.query(boolQueryBuilder);
            searchSourceBuilder.aggregation(labelIdGroup);
            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = this.client.search(searchRequest, new Header[0]);
            Aggregations aggregations = searchResponse.getAggregations();
            //获取主分组信息
            Terms sourceType = aggregations.get("connectionTimeGroup");
            Map<String, Object> result = null;
            //遍历主分组获取媒体名称信息以及文章个数
            for (Terms.Bucket bucket : sourceType.getBuckets()) {
                result = new HashMap<>();

                Aggregations temps = bucket.getAggregations();
                Map<String, Aggregation> source = temps.getAsMap();
                List<Map<String, Object>> mapList = new ArrayList();
                for (Map.Entry<String, Aggregation> aggregationEntry : source.entrySet()) {
                    ParsedLongTerms parsedLongTerms = (ParsedLongTerms) aggregationEntry.getValue();
                    List<ParsedLongTerms.ParsedBucket> parsedBuckets = (List<ParsedLongTerms.ParsedBucket>) parsedLongTerms.getBuckets();
                    for (ParsedLongTerms.ParsedBucket parsedBucket : parsedBuckets) {
                        Map<String, Object> map = new HashMap<>();
                        // label Id
                        map.put("labelId", parsedBucket.getKeyAsString());
                        // label name
                        InterfaceLabelInfo interfaceLabelInfo = iInterfaceLabelMapper.selectByPrimaryKey(parsedBucket.getKeyAsString());
                        String labelName = ObjectUtils.isNullObj(interfaceLabelInfo) ? "" : interfaceLabelInfo.getLabelName();
                        map.put("labelName", labelName);
                        // label count
                        map.put("labelCount", parsedBucket.getDocCount());
                        //加入到集合
                        mapList.add(map);
                    }
                }
                // 日期
                result.put("connectionStr", bucket.getKeyAsString());
                // 当天每个节点的统计 根据labelId
                result.put("mapList", mapList);

                dateMap.add(result);
            }
        } catch (IOException ioException) {

        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取数据成功", dateMap);
    }
```


