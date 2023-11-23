package cn.wolfcode.item.bills.service;

import java.util.List;
import cn.wolfcode.item.bills.domain.StockBillItem;

/**
 * 出入库单据明细Service接口
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
public interface IStockBillItemService 
{
    /**
     * 查询出入库单据明细
     * 
     * @param id 出入库单据明细主键
     * @return 出入库单据明细
     */
    public StockBillItem selectStockBillItemById(String id);

    /**
     * 查询出入库单据明细列表
     * 
     * @param stockBillItem 出入库单据明细
     * @return 出入库单据明细集合
     */
    public List<StockBillItem> selectStockBillItemList(StockBillItem stockBillItem);

    /**
     * 新增出入库单据明细
     * 
     * @param stockBillItem 出入库单据明细
     * @return 结果
     */
    public int insertStockBillItem(StockBillItem stockBillItem);

    /**
     * 修改出入库单据明细
     * 
     * @param stockBillItem 出入库单据明细
     * @return 结果
     */
    public int updateStockBillItem(StockBillItem stockBillItem);

    /**
     * 批量删除出入库单据明细
     * 
     * @param ids 需要删除的出入库单据明细主键集合
     * @return 结果
     */
    public int deleteStockBillItemByIds(String[] ids);

    /**
     * 删除出入库单据明细信息
     * 
     * @param id 出入库单据明细主键
     * @return 结果
     */
    public int deleteStockBillItemById(String id);
}
