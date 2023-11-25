package cn.wolfcode.item.bill.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.wolfcode.client.customer.mapper.CustomerMapper;
import cn.wolfcode.client.customer.service.ICustomerService;
import cn.wolfcode.common.utils.SecurityUtils;
import cn.wolfcode.item.bill.domain.vo.StockBillVo;
import cn.wolfcode.item.bills.domain.StockBillItem;
import cn.wolfcode.item.bills.service.IStockBillItemService;
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
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private IStockBillItemService stockBillItemService;

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
        //如果是入库状态,作废减少入库增加的数量,
        //如果是出库状态,作废恢复出库减少的数量
        StockBill stockBill = stockBillMapper.selectStockBillById(id);

        //修改状态为-1（作废）
        stockBillMapper.voidBillitem(id);
        return stockBillMapper.voidBill(id);
    }

    //出库
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int Out(StockBillVo stockBillVo) {
        //查询出库存数量,如果库存数为0提示不能出库,否则从库存数量减少相应数量
        List<GoodsVo> goods = stockBillVo.getBillsList();
        for (GoodsVo good : goods) {
            Long id = good.getId();
            Goods goodsById = goodsService.selectGoodsById(id);
            //物品表中的库存数
            Integer spec =Integer.valueOf(goodsById.getSpec());
            //如果没有库存不能操作
            if (spec <= 0) {
                continue;
            }
            //前端传入的数量
                int idSpec = Integer.valueOf(good.getAmounts());
                //剩下库存
                Integer num = spec - idSpec;
                if (num<0){
                    continue;
                }
                Goods goods1 = new Goods();
                goods1.setId(id);
                //物品名
                goods1.setGoodsName(goodsById.getGoodsName());
                //新库存
                goods1.setSpec(String.valueOf(num));
                goodsService.updateGoods(goods1);
                //将前端传入的值添加到出入库管理表中
            StockBill stockBill = new StockBill();
            stockBill.setStoreId(stockBillVo.getStoreId());//仓库id
            stockBill.setOperateDate(new Date());//录入时间
            stockBill.setBusiDate(stockBillVo.getBusiDate());//出入库时间
            stockBill.setType(1);//状态设置为出库
            stockBill.setStatus(0);//状态默认正常
            stockBill.setOperatorId(SecurityUtils.getUserId());//录入人
            stockBill.setRemark(stockBillVo.getRemark());//备注
            stockBillService.insertStockBill(stockBill);
            //出入库明细
            StockBillItem stockBillItem = new StockBillItem();
            stockBillItem.setAmounts(Long.valueOf(good.getAmounts()));//设置数量
            stockBillItem.setPrice(new BigDecimal(good.getPrice()));//价格
            stockBillItem.setGoodsId(String.valueOf(id));//设置物品id
            stockBillItem.setBillId(String.valueOf(stockBill.getId()));
            stockBillItem.setState(0);
            stockBillItemService.insertStockBillItem(stockBillItem);
        }
        return 1;
    }
}
