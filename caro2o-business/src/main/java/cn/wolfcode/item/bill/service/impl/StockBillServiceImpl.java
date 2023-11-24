package cn.wolfcode.item.bill.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.item.bill.mapper.StockBillMapper;
import cn.wolfcode.item.bill.domain.StockBill;
import cn.wolfcode.item.bill.service.IStockBillService;

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
        //修改状态为-1（作废）
        stockBillMapper.voidBillitem(id);
        return stockBillMapper.voidBill(id);
    }
}
