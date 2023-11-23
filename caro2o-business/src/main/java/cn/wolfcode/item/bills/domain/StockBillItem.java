package cn.wolfcode.item.bills.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 出入库单据明细对象 stock_bill_item
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
public class StockBillItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 物品 */
    @Excel(name = "物品")
    private String goodsId;

    /** 数量 */
    @Excel(name = "数量")
    private Long amounts;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal price;

    /** 单据 */
    @Excel(name = "单据")
    private String billId;

    /** 0 正常, -1 作废 */
    private Integer state;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setGoodsId(String goodsId) 
    {
        this.goodsId = goodsId;
    }

    public String getGoodsId() 
    {
        return goodsId;
    }
    public void setAmounts(Long amounts) 
    {
        this.amounts = amounts;
    }

    public Long getAmounts() 
    {
        return amounts;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setBillId(String billId) 
    {
        this.billId = billId;
    }

    public String getBillId() 
    {
        return billId;
    }
    public void setState(Integer state) 
    {
        this.state = state;
    }

    public Integer getState() 
    {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("goodsId", getGoodsId())
            .append("amounts", getAmounts())
            .append("price", getPrice())
            .append("billId", getBillId())
            .append("state", getState())
            .toString();
    }
}
