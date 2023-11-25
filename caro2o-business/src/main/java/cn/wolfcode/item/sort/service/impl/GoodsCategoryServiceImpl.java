package cn.wolfcode.item.sort.service.impl;

import java.util.List;

import cn.wolfcode.item.goods.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.item.sort.mapper.GoodsCategoryMapper;
import cn.wolfcode.item.sort.domain.GoodsCategory;
import cn.wolfcode.item.sort.service.IGoodsCategoryService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 物品分类信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
@Service
public class GoodsCategoryServiceImpl implements IGoodsCategoryService 
{
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;


    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询物品分类信息
     * 
     * @param id 物品分类信息主键
     * @return 物品分类信息
     */
    @Override
    public GoodsCategory selectGoodsCategoryById(String id)
    {
        return goodsCategoryMapper.selectGoodsCategoryById(id);
    }

    /**
     * 查询物品分类信息列表
     * 
     * @param goodsCategory 物品分类信息
     * @return 物品分类信息
     */
    @Override
    public List<GoodsCategory> selectGoodsCategoryList(GoodsCategory goodsCategory)
    {
        return goodsCategoryMapper.selectGoodsCategoryList(goodsCategory);
    }

    /**
     * 新增物品分类信息
     * 
     * @param goodsCategory 物品分类信息
     * @return 结果
     */
    @Override
    public int insertGoodsCategory(GoodsCategory goodsCategory)
    {
        return goodsCategoryMapper.insertGoodsCategory(goodsCategory);
    }

    /**
     * 修改物品分类信息
     * 
     * @param goodsCategory 物品分类信息
     * @return 结果
     */
    @Override
    public int updateGoodsCategory(GoodsCategory goodsCategory)
    {
        return goodsCategoryMapper.updateGoodsCategory(goodsCategory);
    }

    /**
     * 批量删除物品分类信息
     *
     * @param id 需要删除的物品分类信息主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGoodsCategoryByIds(String id)
    {

        return goodsCategoryMapper.deleteGoodsCategoryById(id);
    }

    /**
     * 删除物品分类信息信息
     * 
     * @param id 物品分类信息主键
     * @return 结果
     */
    @Override
    public int deleteGoodsCategoryById(String id)
    {
        // 校验
        GoodsCategory goodsCategory = selectGoodsCategoryById(id);

        List<GoodsCategory> goodsCategories = goodsCategoryMapper.selectGoodsCategoryList(goodsCategory);
        goodsCategories.forEach(goodsCategory1 -> {
            int size = goodsService.selectGoodsByCategoryId(String.valueOf(goodsCategory1.getId())).size();
            if (size > 0){
                throw new RuntimeException("该分类下有物品,不能删除");
            }
            goodsCategoryMapper.deleteGoodsCategoryById(String.valueOf(goodsCategory1.getId()));
        });

        return 1;
    }

    /**
     * 迁移
     *
     * @param id1 需要移动的分类
     * @param id2 移动通道的id
     * @return 结束
     */
    @Override
    public int putPuIn(Long id1, Long id2) {
        goodsCategoryMapper.putPuIn(id1,id2);
        return 1;
    }
}
