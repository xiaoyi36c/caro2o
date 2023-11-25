package cn.wolfcode.item.bill.service.impl;

import java.util.List;

import cn.wolfcode.item.bill.domain.vo.StockBillVo;
import cn.wolfcode.item.goods.domain.Goods;
import cn.wolfcode.item.goods.domain.vo.GoodsVo;
import cn.wolfcode.item.goods.service.IGoodsService;
import org.springframework.beans.BeanUtils;

import cn.wolfcode.item.goods.domain.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.item.bill.mapper.StockBillMapper;
import cn.wolfcode.item.bill.domain.StockBill;
import cn.wolfcode.item.bill.service.IStockBillService;
import org.springframework.util.Assert;

/**
 * 出入库单据Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
@Service
public class StockBillServiceImpl implements IStockBillService 
{
    @Autowired
    private StockBillMapper stockBillMapper;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IStockBillService stockBillService;

    /**
     * 查询出入库单据
     * 
     * @param id 出入库单据主键
     * @return 出入库单据
     */
    @Override
    public StockBill selectStockBillById(Long id)
    {
        return stockBillMapper.selectStockBillById(id);
    }

    /**
     * 查询出入库单据列表
     * 
     * @param stockBill 出入库单据
     * @return 出入库单据
     */
    @Override
    public List<StockBill> selectStockBillList(StockBill stockBill)
    {
        return stockBillMapper.selectStockBillList(stockBill);
    }

    /**
     * 新增出入库单据
     * 
     * @param stockBill 出入库单据
     * @return 结果
     */
    @Override
    public int insertStockBill(StockBill stockBill)
    {
        return stockBillMapper.insertStockBill(stockBill);
    }

    /**
     * 修改出入库单据
     * 
     * @param stockBill 出入库单据
     * @return 结果
     */
    @Override
    public int updateStockBill(StockBill stockBill)
    {
        return stockBillMapper.updateStockBill(stockBill);
    }

    /**
     * 批量删除出入库单据
     * 
     * @param ids 需要删除的出入库单据主键
     * @return 结果
     */
    @Override
    public int deleteStockBillByIds(Long[] ids)
    {
        return stockBillMapper.deleteStockBillByIds(ids);
    }

    /**
     * 删除出入库单据信息
     * 
     * @param id 出入库单据主键
     * @return 结果
     */
    @Override
    public int deleteStockBillById(Long id)
    {
        return stockBillMapper.deleteStockBillById(id);
    }

    @Override
    public int voidBill(Long id) {
        //需减少对应的物品库存，当有物品库存数量不足的情况，提示有物品库存数量不足
        StockBill stockBill = stockBillMapper.selectStockBillById(id);
        //修改状态为-1（作废）
        stockBillMapper.voidBillitem(id);
        return stockBillMapper.voidBill(id);
    }

    //出库
    @Override
    public int Out(StockBillVo stockBillVo) {
        //查询出库存数量,如果库存数为0提示不能出库,否则从库存数量减少相应数量
        List<GoodsVo> goods = stockBillVo.getBillsList();
        for (GoodsVo good : goods) {
            Long id = good.getId();
            Goods goodsById = goodsService.selectGoodsById(id);
            Integer spec =Integer.valueOf(goodsById.getSpec());
            if (spec < 0) {
                continue;
            }
                int idSpec = Integer.valueOf(goodsById.getSpec());
                //剩下库存
                Integer num = spec - idSpec;
                Goods goods1 = new Goods();
                goods1.setId(id);
                //物品名
                goods1.setGoodsName(goodsById.getGoodsName());
                //新库存
                goods1.setSpec(String.valueOf(num));
                goodsService.updateGoods(goods1);
                //将
            StockBill stockBill = new StockBill();
            stockBill.setStoreId(stockBillVo.getStoreId());
            stockBill.setOperateDate(stockBillVo.getOperateDate());
            stockBill.setRemark(stockBillVo.getRemark());
            stockBillService.updateStockBill(stockBill);
        }
        return 1;
    }
}
