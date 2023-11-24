package cn.wolfcode.item.goods.domain;

import cn.wolfcode.item.bill.domain.StockBill;
import cn.wolfcode.item.bills.domain.StockBillItem;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;

@Data
public class Subsidiary {

    private Long id;
    private String goodsName;
    private String brand;
    private BigDecimal price;
    private BigDecimal amounts;
    private String storeName;
    private String type;
    private String busiDate;

}
