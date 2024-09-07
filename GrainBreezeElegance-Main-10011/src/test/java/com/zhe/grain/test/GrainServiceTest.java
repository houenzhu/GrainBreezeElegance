package com.zhe.grain.test;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhe.grain.domain.commodity.GrainCommodityAttrAttrgroupRelation;
import com.zhe.grain.domain.SysUser;
import com.zhe.grain.mapper.commodity.GrainCategoryMapper;
import com.zhe.grain.mapper.commodity.GrainCommodityAttrAttrgroupRelationMapper;
import com.zhe.grain.mapper.commodity.GrainCommodityAttrMapper;
import com.zhe.grain.mapper.commodity.GrainCommodityAttrgroupCategoryRelationMapper;
import com.zhe.grain.mapper.user.MenuMapper;
import com.zhe.grain.mapper.user.UserMapper;
import com.zhe.grain.service.commodity.GrainCategoryBrainRelationService;
import com.zhe.grain.service.commodity.GrainCategoryService;
import com.zhe.grain.service.commodity.GrainCommodityAttrgroupCategoryRelationService;
import com.zhe.grain.utils.CodeGeneration;
import com.zhe.grain.utils.RedisCache;
import com.zhe.grain.utils.ReflectUtils;
import com.zhe.grain.vo.commodity.GrainCommodityAttrVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@SpringBootTest
public class GrainServiceTest {

    @Autowired
    private GrainCategoryService grainCategoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GrainCategoryBrainRelationService grainCategoryBrainRelationService;

    @Autowired
    private GrainCommodityAttrgroupCategoryRelationMapper grainCommodityAttrgroupCategoryRelationMapper;

    @Autowired
    private GrainCommodityAttrgroupCategoryRelationService grainCommodityAttrgroupCategoryRelationService;

    @Autowired
    private GrainCategoryMapper grainCategoryMapper;

    @Autowired
    private GrainCommodityAttrMapper grainCommodityAttrMapper;

    @Autowired
    private GrainCommodityAttrAttrgroupRelationMapper attrAttrgroupRelationMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String USER_AGENT = "Java-HttpURLConnection/1.0";

    @Test
    public void encodePassword() {
        System.out.println(passwordEncoder.matches("admin", "$2a$10$kpMeGE/JuZOUey2B5vJ/3eCje3keBgighTOgFjLsPZP4DI91jW/XK"));
    }
    @Test
    public void test() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String format1 = format.format(new Date());
        System.out.println(format1);
    }

    @Test
    public void delete() {
        grainCategoryService.removeBatchByIds(Arrays.asList(11L, 12L));
    }

    @Test
    public void generator() {
        CodeGeneration.GenerateCode("order_detail");
    }

    @Test
    public void ai() throws Exception{
        Object cacheObject = redisCache.getCacheObject("1");
        System.out.println(cacheObject);
    }

    @Test
    public void getPermissions() {
        List<String> perms = menuMapper.selectPermsByUserId(1L);
        System.out.println("perms = " + perms);
    }

    @Test
    public void updateData() {
        userMapper.update(new SysUser().setId(1L),
                new UpdateWrapper<SysUser>().set("update_time", new Date()));
    }

    @Test
    public void getNotSelectedCategory() {
        System.out.println(grainCategoryMapper.getNotSelectedCategoryByAttrGroupId(1L));
    }

    @Test
    public void batchDelete() {
        Long[] ids = {2L};
        grainCommodityAttrgroupCategoryRelationMapper.batchDelete(ids);
    }

    @Test
    public void queryAllRelation() {
        System.out.println(grainCommodityAttrgroupCategoryRelationService.queryAllRelation(1L));
    }

    @Test
    public void reflect() {
        HashMap<String, Object> values = new HashMap<>();
        values.put("attrId", 1L);
        values.put("attrName", "时间");
        values.put("valueSelect", "1,2,3");
        values.put("enable", 1L);
        values.put("attrType", "销售属性");
        values.put("categoryName", "泰国大米");
        try {
            Object object = ReflectUtils.reflectObject(GrainCommodityAttrVO.class, values);
            GrainCommodityAttrVO grainCommodityAttrVO = (GrainCommodityAttrVO) object;
            System.out.println("grainCommodityAttrVO = " + grainCommodityAttrVO.getAttrName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void selectNotInAttrIds() {
        List<Long> ids = Arrays.asList(14L, 19L);
        System.out.println(grainCommodityAttrMapper.selectNotInAttrIds(ids));
    }

    @Test
    public void batchDeleteAttrAttrGroupRelation() {
        List<GrainCommodityAttrAttrgroupRelation> relations = new ArrayList<>();
        GrainCommodityAttrAttrgroupRelation relation1 = new GrainCommodityAttrAttrgroupRelation();
        relation1.setAttrGroupId(1L)
                .setAttrId(14L);
        GrainCommodityAttrAttrgroupRelation relation2 = new GrainCommodityAttrAttrgroupRelation();
        relation2.setAttrGroupId(1L)
                .setAttrId(20L);
        relations.add(relation1);
        relations.add(relation2);
        attrAttrgroupRelationMapper.batchDeleteRelation(relations);
    }

    @Test
    public void getKeysByPattern() {
        Collection<String> keys = redisCache.keys("test:user:*", 100);
    }

}
