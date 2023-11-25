package cn.wolfcode.item.sort.mapper;

import java.util.List;
import cn.wolfcode.item.sort.domain.GoodsCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 物品分类信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-11-23
 */

@Mapper
public interface GoodsCategoryMapper 
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
     * 删除物品分类信息
     * 
     * @param id 物品分类信息主键
     * @return 结果
     */
    public int deleteGoodsCategoryById(String id);

    /**
     * 批量删除物品分类信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsCategoryByIds(String[] ids);

    void putPuIn(@Param("id") Long id, @Param("parentId") Long parentId);

    List<GoodsCategory> selectPathList(@Param("parentId") Long parentId);
}
