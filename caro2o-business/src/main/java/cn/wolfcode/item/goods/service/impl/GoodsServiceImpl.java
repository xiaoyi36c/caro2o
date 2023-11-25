package cn.wolfcode.item.goods.service.impl;

import java.io.File;
import java.util.Collection;
import java.util.List;

import cn.wolfcode.common.config.RuoYiConfig;
import cn.wolfcode.item.goods.domain.Goods;
import cn.wolfcode.item.goods.domain.Subsidiary;
import cn.wolfcode.item.goods.mapper.GoodsMapper;
import cn.wolfcode.item.goods.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


/**
 * 物品信息Service业务层处理
 *
 * @author Baishuai
 * @date 2023-11-24
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
     * @param goods 物品信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertGoods(Goods goods)
    {
        // 参数校验
        // 1. 物品名称不能超过50个字符
        if (goods.getGoodsName()!=null){
            Assert.state(goods.getGoodsName().length() <= 50, "物品名称不能超过50个字符");
        }
        if (goods.getBrand()!=null){
            Assert.state(goods.getBrand().length() <= 50, "品牌不能超过50个字符");
        }
        if (goods.getSpec()!=null){
            Assert.state(goods.getSpec().length() <= 50, "规格不能超过50个字符");
        }
        if (goods.getGoodsDesc()!=null){
            Assert.state(goods.getGoodsDesc().length() <= 50, "描述不能超过50个字符");
        }


        goodsMapper.insertGoods(goods);
        Goods goods1 = selectGoodsById(goods.getId());

        String goodsCover = goods1.getGoodsCover();


        // 根据路径获取文件
        // 去掉相对路径中的 /profile/upload 部分
        File file = new File(RuoYiConfig.getUploadPath() + goodsCover.replace("/profile/upload", ""));
        // 判断文件是否存在
        Assert.state(file.exists(), "请上传图片");
        return 1;
    }

    /**
     * 修改物品信息
     *
     * @param goods 物品信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateGoods(Goods goods)
    {
        Goods goods1 = selectGoodsById(goods.getId());
        Assert.state(goods1 != null, "物品不存在");
        // 参数校验
        // 1. 物品名称不能超过50个字符
        if (goods.getGoodsName()!=null){
            Assert.state(goods.getGoodsName().length() <= 50, "物品名称不能超过50个字符");
        }
        if (goods.getBrand()!=null){
            Assert.state(goods.getBrand().length() <= 50, "品牌不能超过50个字符");
        }
        if (goods.getSpec()!=null){
            Assert.state(goods.getSpec().length() <= 50, "规格不能超过50个字符");
        }
        if (goods.getGoodsDesc()!=null){
            Assert.state(goods.getGoodsDesc().length() <= 50, "描述不能超过50个字符");
        }


        goodsMapper.updateGoods(goods);
        Goods goodsNew = selectGoodsById(goods1.getId());

        String goodsCover = goodsNew.getGoodsCover();
        // 根据路径获取文件
        File file = new File(RuoYiConfig.getUploadPath() + goodsCover.replace("/profile/upload", ""));
        // 判断文件是否存在
        Assert.state(file.exists(), "请上传图片");


        return 1 ;
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

    @Override
    public List<Subsidiary> selectSubsidiaryByGoodsId(Long id) {
                return goodsMapper.selectSubsidiaryByGoodsId(id);
    }

    @Override
    public Collection<Goods> selectGoodsByCategoryId(String s) {
                return goodsMapper.selectGoodsByCategoryId(s);
    }
}
