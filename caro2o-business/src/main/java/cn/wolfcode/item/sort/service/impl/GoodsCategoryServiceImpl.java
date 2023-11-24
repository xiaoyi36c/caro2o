package cn.wolfcode.item.sort.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.item.sort.mapper.GoodsCategoryMapper;
import cn.wolfcode.item.sort.domain.GoodsCategory;
import cn.wolfcode.item.sort.service.IGoodsCategoryService;

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
     * @param ids 需要删除的物品分类信息主键
     * @return 结果
     */
    @Override
    public int deleteGoodsCategoryByIds(String[] ids)
    {
        return goodsCategoryMapper.deleteGoodsCategoryByIds(ids);
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
        return goodsCategoryMapper.deleteGoodsCategoryById(id);
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
