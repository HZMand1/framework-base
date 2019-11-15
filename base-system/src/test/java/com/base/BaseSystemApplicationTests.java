package com.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.dict.rest.RestDictInfoController;
import com.base.model.DictInfoEntity;
import com.base.utils.key.SnowFlakeIds;
import com.base.utils.type.JsonMapper;
import com.base.vo.AjaxResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO 数据字典管理单元测试
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/10/22 13:48
 * @copyright XXX Copyright (c) 2019
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseSystemApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class BaseSystemApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private RestDictInfoController dictInfoController;

    private static final int SUCCESS = 1;

    @Before
    public void setUp() throws Exception {
        //初始化
        mockMvc = MockMvcBuilders.standaloneSetup(dictInfoController).build();
    }

    @Test
    public void findFirstServiceInfoAllListTest() throws Exception {
        DictInfoEntity dictInfoEntity = new DictInfoEntity();
        String param = JsonMapper.toJsonString(dictInfoEntity);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rest/sys/dict/findDictInfoAllList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String responseStr = mvcResult.getResponse().getContentAsString();
        AjaxResult ajaxResult = JSON.parseObject(responseStr, AjaxResult.class);
        Assert.assertEquals(SUCCESS, ajaxResult.getRetcode());
    }

    @Test
    public void findFirstServiceInfoListPageTest() throws Exception {
        DictInfoEntity firstServiceInfoEntity = new DictInfoEntity();
        firstServiceInfoEntity.setStart(1);
        firstServiceInfoEntity.setPageSize(5);
        String param = JsonMapper.toJsonString(firstServiceInfoEntity);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rest/sys/dict/findDictInfoPageList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String responseStr = mvcResult.getResponse().getContentAsString();
        AjaxResult ajaxResult = JSON.parseObject(responseStr, AjaxResult.class);
        Assert.assertEquals(SUCCESS, ajaxResult.getRetcode());
    }

    @Test
    public void insertFirstServiceInfoTest() throws Exception {
        DictInfoEntity dictInfoEntity = createAdNewsEntityTestVOs(1).get(0);
        String param = JsonMapper.toJsonString(dictInfoEntity);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rest/sys/dict/insertDictInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String responseStr = mvcResult.getResponse().getContentAsString();
        AjaxResult ajaxResult = JSON.parseObject(responseStr, AjaxResult.class);
        Assert.assertEquals(SUCCESS, ajaxResult.getRetcode());
        deleteTestData(1);
    }

    @Test
    public void updateFirstServiceInfoByIdTest() throws Exception {
        createTestData(1);
        DictInfoEntity dictInfoEntity = getTestDataList(1).get(0);
        dictInfoEntity.setStatus(0);
        String param = JsonMapper.toJsonString(dictInfoEntity);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rest/sys/dict/updateDictInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String responseStr = mvcResult.getResponse().getContentAsString();
        AjaxResult ajaxResult = JSON.parseObject(responseStr, AjaxResult.class);
        Assert.assertEquals(SUCCESS, ajaxResult.getRetcode());
        deleteTestData(1);
    }

    @Test
    public void deleteDictInfoTest() throws Exception {
        createTestData(1);
        DictInfoEntity dictInfoEntity = getTestDataList(1).get(0);
        String param = JsonMapper.toJsonString(dictInfoEntity);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rest/sys/dict/deleteDictInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String responseStr = mvcResult.getResponse().getContentAsString();
        AjaxResult ajaxResult = JSON.parseObject(responseStr, AjaxResult.class);
        Assert.assertEquals(SUCCESS, ajaxResult.getRetcode());
    }

    private List<DictInfoEntity> createAdNewsEntityTestVOs(int count) {
        DictInfoEntity dictInfoEntity = null;
        List<DictInfoEntity> result = new ArrayList<DictInfoEntity>();
        for (int i = 0; i < count; i++) {
            dictInfoEntity = new DictInfoEntity();
            dictInfoEntity.setId(SnowFlakeIds.get().nextId() + "");
            dictInfoEntity.setType("类别");
            dictInfoEntity.setCode("编码");
            dictInfoEntity.setValue("值");
            dictInfoEntity.setSortNo(i);
            dictInfoEntity.setStatus(i);
            dictInfoEntity.setDescription("描述");
            dictInfoEntity.setString1("预留1");
            dictInfoEntity.setString2("预留2");
            dictInfoEntity.setString3("预留3");
            dictInfoEntity.setUpdateTime(new Date());
            result.add(dictInfoEntity);
        }
        return result;
    }

    private List<DictInfoEntity> getTestDataList(int count) throws Exception {
        DictInfoEntity firstServiceInfoEntity = new DictInfoEntity();
        String param = JsonMapper.toJsonString(firstServiceInfoEntity);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rest/sys/dict/findDictInfoAllList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        JSONObject json = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
        List<DictInfoEntity> list = JSONArray.parseArray(json.getString("data"), DictInfoEntity.class);
        List<DictInfoEntity> result = new ArrayList<DictInfoEntity>();
        int temp = 0;
        for (DictInfoEntity infoEntity : list) {
            if (temp < count) {
                result.add(infoEntity);
            }
            temp++;
        }
        return result;
    }

    private void createTestData(int count) throws Exception {
        List<DictInfoEntity> list = createAdNewsEntityTestVOs(count);
        for (DictInfoEntity firstServiceInfoEntity : list) {
            String param = JsonMapper.toJsonString(firstServiceInfoEntity);
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/rest/sys/dict/insertDictInfo")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(param))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        }
    }

    private void deleteTestData(int count) throws Exception {
        List<DictInfoEntity> list = getTestDataList(count);
        for (DictInfoEntity firstServiceInfoEntity : list) {
            String param = JsonMapper.toJsonString(firstServiceInfoEntity);
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/rest/sys/dict/deleteDictInfo")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(param))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        }
    }
}

