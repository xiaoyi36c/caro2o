package cn.wolfcode.item.goods.service;

import java.util.List;
import cn.wolfcode.item.goods.domain.Goods;

/**
 * 物品信息Service接口
 * 
 * @author Baishuai
 * @date 2023-11-23
 */
public interface IGoodsService 
{
    /**
     * 查询物品信息
     * 
     * @param id 物品信息主键
     * @return 物品信息
     */
    public Goods selectGoodsById(Long id);

    /**
     * 查询物品信息列表
     * 
     * @param goods 物品信息
     * @return 物品信息集合
     */
    public List<Goods> selectGoodsList(Goods goods);

    /**
     * 新增物品信息
     * 
     * @param goods 物品信息
     * @return 结果
     */
    public int insertGoods(Goods goods);

    /**
     * 修改物品信息
     * 
     * @param goods 物品信息
     * @return 结果
     */
    public int updateGoods(Goods goods);

    /**
     * 批量删除物品信息
     * 
     * @param ids 需要删除的物品信息主键集合
     * @return 结果
     */
    public int deleteGoodsByIds(Long[] ids);

    /**
     * 删除物品信息信息
     * 
     * @param id 物品信息主键
     * @return 结果
     */
    public int deleteGoodsById(Long id);
}
