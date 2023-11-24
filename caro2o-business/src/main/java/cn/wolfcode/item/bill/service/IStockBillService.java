package cn.wolfcode.item.bill.service;

import java.util.List;
import cn.wolfcode.item.bill.domain.StockBill;

/**
 * 出入库单据Service接口
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
public interface IStockBillService 
{
    /**
     * 查询出入库单据
     * 
     * @param id 出入库单据主键
     * @return 出入库单据
     */
    public StockBill selectStockBillById(Long id);

    /**
     * 查询出入库单据列表
     * 
     * @param stockBill 出入库单据
     * @return 出入库单据集合
     */
    public List<StockBill> selectStockBillList(StockBill stockBill);

    /**
     * 新增出入库单据
     * 
     * @param stockBill 出入库单据
     * @return 结果
     */
    public int insertStockBill(StockBill stockBill);

    /**
     * 修改出入库单据
     * 
     * @param stockBill 出入库单据
     * @return 结果
     */
    public int updateStockBill(StockBill stockBill);

    /**
     * 批量删除出入库单据
     * 
     * @param ids 需要删除的出入库单据主键集合
     * @return 结果
     */
    public int deleteStockBillByIds(Long[] ids);

    /**
     * 删除出入库单据信息
     * 
     * @param id 出入库单据主键
     * @return 结果
     */
    public int deleteStockBillById(Long id);

    int voidBill(Long id);
}
