package cn.wolfcode.item.bill.domain.vo;

import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;
import cn.wolfcode.item.bills.domain.StockBillItem;
import cn.wolfcode.item.goods.domain.Goods;
import cn.wolfcode.item.goods.domain.vo.GoodsVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 出入库单据对象 stock_bill
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
@Getter
@Setter
@ToString
public class StockBillVo extends BaseEntity
{
    /** 仓库id */
    @Excel(name = "仓库id")
    private String storeId;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date busiDate;

    private String remark;

    private List<GoodsVo> billsList;

}
