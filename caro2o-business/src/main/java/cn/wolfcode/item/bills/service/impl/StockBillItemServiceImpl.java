package cn.wolfcode.item.bills.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.item.bills.mapper.StockBillItemMapper;
import cn.wolfcode.item.bills.domain.StockBillItem;
import cn.wolfcode.item.bills.service.IStockBillItemService;

/**
 * 出入库单据明细Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
@Service
public class StockBillItemServiceImpl implements IStockBillItemService 
{
    @Autowired
    private StockBillItemMapper stockBillItemMapper;

    /**
     * 查询出入库单据明细
     * 
     * @param id 出入库单据明细主键
     * @return 出入库单据明细
     */
    @Override
    public StockBillItem selectStockBillItemById(String id)
    {
        return stockBillItemMapper.selectStockBillItemById(id);
    }

    /**
     * 查询出入库单据明细列表
     * 
     * @param stockBillItem 出入库单据明细
     * @return 出入库单据明细
     */
    @Override
    public List<StockBillItem> selectStockBillItemList(StockBillItem stockBillItem)
    {
        return stockBillItemMapper.selectStockBillItemList(stockBillItem);
    }

    /**
     * 新增出入库单据明细
     * 
     * @param stockBillItem 出入库单据明细
     * @return 结果
     */
    @Override
    public int insertStockBillItem(StockBillItem stockBillItem)
    {
        return stockBillItemMapper.insertStockBillItem(stockBillItem);
    }

    /**
     * 修改出入库单据明细
     * 
     * @param stockBillItem 出入库单据明细
     * @return 结果
     */
    @Override
    public int updateStockBillItem(StockBillItem stockBillItem)
    {
        return stockBillItemMapper.updateStockBillItem(stockBillItem);
    }

    /**
     * 批量删除出入库单据明细
     * 
     * @param ids 需要删除的出入库单据明细主键
     * @return 结果
     */
    @Override
    public int deleteStockBillItemByIds(String[] ids)
    {
        return stockBillItemMapper.deleteStockBillItemByIds(ids);
    }

    /**
     * 删除出入库单据明细信息
     * 
     * @param id 出入库单据明细主键
     * @return 结果
     */
    @Override
    public int deleteStockBillItemById(String id)
    {
        return stockBillItemMapper.deleteStockBillItemById(id);
    }

    @Override
    public List<StockBillItem> selectStockBillItemByBillIdList(Long id) {
        return stockBillItemMapper.selectStockBillItemByBillIdList(id);
    }
}
