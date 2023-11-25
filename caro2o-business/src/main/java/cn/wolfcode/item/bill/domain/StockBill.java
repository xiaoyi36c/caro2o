package cn.wolfcode.item.bill.domain;

import java.math.BigDecimal;
import java.util.Date;

import cn.wolfcode.item.bills.domain.StockBillItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 出入库单据对象 stock_bill
 * 
 * @author ruoyi
 * @date 2023-11-23
 */

@Data
public class StockBill extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 单据流水号 */
    @Excel(name = "单据流水号")
    private Long id;
    //关联出入库明细表
    private StockBillItem stockBillItem;

    public StockBillItem getStockBillItem() {
        return stockBillItem;
    }

    public void setStockBillItem(StockBillItem stockBillItem) {
        this.stockBillItem = stockBillItem;
    }

    private Integer amounts;

    public Integer getAmounts() {
        return amounts;
    }

    public void setAmounts(Integer amounts) {
        this.amounts = amounts;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    private BigDecimal price;

    /** 类型 */
    @Excel(name = "类型")
    private Integer type;

    /** 仓库id */
    @Excel(name = "仓库id")
    private String storeId;

    /** 出入库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出入库时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date busiDate;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 录入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "录入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date operateDate;

    /** 录入人 */
    @Excel(name = "录入人")
    private Long operatorId;
}
