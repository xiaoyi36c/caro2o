package cn.wolfcode.item.goods.mapper;

import cn.wolfcode.item.goods.domain.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 物品信息Mapper接口
 *
 * @author Baishuai
 * @date 2023-11-24
 */

@Mapper
public interface GoodsMapper
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
     * 删除物品信息
     *
     * @param id 物品信息主键
     * @return 结果
     */
    public int deleteGoodsById(Long id);

    /**
     * 批量删除物品信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsByIds(Long[] ids);
}
