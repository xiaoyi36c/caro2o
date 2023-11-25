package cn.wolfcode.item.sort.service;

import java.util.List;
import cn.wolfcode.item.sort.domain.GoodsCategory;

/**
 * 物品分类信息Service接口
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
public interface IGoodsCategoryService 
{
    /**
     * 查询物品分类信息
     * 
     * @param id 物品分类信息主键
     * @return 物品分类信息
     */
    public GoodsCategory selectGoodsCategoryById(String id);

    /**
     * 查询物品分类信息列表
     * 
     * @param goodsCategory 物品分类信息
     * @return 物品分类信息集合
     */
    public List<GoodsCategory> selectGoodsCategoryList(GoodsCategory goodsCategory);

    /**
     * 新增物品分类信息
     * 
     * @param goodsCategory 物品分类信息
     * @return 结果
     */
    public int insertGoodsCategory(GoodsCategory goodsCategory);

    /**
     * 修改物品分类信息
     * 
     * @param goodsCategory 物品分类信息
     * @return 结果
     */
    public int updateGoodsCategory(GoodsCategory goodsCategory);

    /**
     * 批量删除物品分类信息
     *
     * @param id 需要删除的物品分类信息主键集合
     * @return 结果
     */
    public int deleteGoodsCategoryByIds(String id);

    /**
     * 删除物品分类信息信息
     * 
     * @param id 物品分类信息主键
     * @return 结果
     */
    public int deleteGoodsCategoryById(String id);

    int putPuIn(Long id1, Long id2, Boolean checked);
}
