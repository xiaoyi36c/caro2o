package cn.wolfcode.item.goods.service.impl;

import java.io.SequenceInputStream;
import java.util.List;

import cn.wolfcode.common.utils.bean.BeanUtils;
import cn.wolfcode.item.goods.domain.vo.GoodsVO;
import cn.wolfcode.item.goods.domain.vo.GoodsVO2;
import org.apache.commons.collections4.BagUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.item.goods.mapper.GoodsMapper;
import cn.wolfcode.item.goods.domain.Goods;
import cn.wolfcode.item.goods.service.IGoodsService;

/**
 * 物品信息Service业务层处理
 * 
 * @author Baishuai
 * @date 2023-11-23
 */
@Service
public class GoodsServiceImpl implements IGoodsService 
{
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 查询物品信息
     * 
     * @param id 物品信息主键
     * @return 物品信息
     */
    @Override
    public Goods selectGoodsById(Long id)
    {
        return goodsMapper.selectGoodsById(id);
    }

    /**
     * 查询物品信息列表
     * 
     * @param goods 物品信息
     * @return 物品信息
     */
    @Override
    public List<Goods> selectGoodsList(Goods goods)
    {
        return goodsMapper.selectGoodsList(goods);
    }

    /**
     * 新增物品信息
     * 
     * @param goodsVO 物品信息
     * @return 结果
     */
    @Override
    public int insertGoods(GoodsVO goodsVO)
    {
        GoodsVO2 goodsVO2 = new GoodsVO2();
        BeanUtils.copyBeanProp(goodsVO2, goodsVO);
        goodsVO2.setGoodsCover(goodsVO.getInputStream());
        if ( goodsVO.getId() == -1){
            return goodsMapper.insertGoods(goodsVO2);
        }else {
            return goodsMapper.updateGoods(goodsVO2);
        }
    }

    /**
     * 修改物品信息
     * 
     * @param goods 物品信息
     * @return 结果
     */
    @Override
    public int updateGoods(Goods goods)
    {
        return 0;
    }

    /**
     * 批量删除物品信息
     * 
     * @param ids 需要删除的物品信息主键
     * @return 结果
     */
    @Override
    public int deleteGoodsByIds(Long[] ids)
    {
        return goodsMapper.deleteGoodsByIds(ids);
    }

    /**
     * 删除物品信息信息
     * 
     * @param id 物品信息主键
     * @return 结果
     */
    @Override
    public int deleteGoodsById(Long id)
    {
        return goodsMapper.deleteGoodsById(id);
    }
}
