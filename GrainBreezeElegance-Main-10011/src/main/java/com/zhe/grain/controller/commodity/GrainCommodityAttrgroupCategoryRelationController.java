package com.zhe.grain.controller.commodity;

import com.zhe.grain.entity.GrainCategoryEntity;
import com.zhe.grain.entity.GrainCommodityAttrgroupCategoryRelation;
import com.zhe.grain.service.commodity.GrainCommodityAttrgroupCategoryRelationService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.AttrGroupRelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 属性分组与分类关联表 前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2024-05-25
 */
@RestController
@RequestMapping("/grain/CommodityAttrgroupCategoryRelation")
public class GrainCommodityAttrgroupCategoryRelationController {

    @Autowired
    private GrainCommodityAttrgroupCategoryRelationService attrgroupCategoryRelationService;

    /**
     * 通过关键词分页显示
     *
     * @param params
     * @return
     */
    @GetMapping("/list")
    public Result<PageUtils> list(@RequestParam Map<String, Object> params) {
        return attrgroupCategoryRelationService.queryByPage(params);
    }

    /**
     * 通过属性分组id查询所有关联
     *
     * @return
     */
    @GetMapping("/queryAllRelation")
    public Result<PageUtils> queryAllRelation(@RequestParam Map<String, Object> params) {
        return attrgroupCategoryRelationService.queryAllRelationByPage(params);
    }

    /**
     * 获取未关联的分类
     * @param attrGroupId
     * @return
     */
    @GetMapping("/getAllNotSelectedCategory/{attrGroupId}")
    public Result<List<GrainCategoryEntity>> getAllNotSelectedCategory(@PathVariable("attrGroupId")
                                                                      Long attrGroupId) {
        return attrgroupCategoryRelationService.queryCategoryNotSelected(attrGroupId);
    }

    /**
     * 添加关联信息
     * @param attrGroupRelationVO
     * @return
     */
    @PostMapping("/save")
    public Result<Object> save(@RequestBody AttrGroupRelationVO attrGroupRelationVO) {
        boolean flag = attrgroupCategoryRelationService.addRelation(attrGroupRelationVO);
        return flag ? Result.success("添加成功!", null) : Result.error();
    }

}
