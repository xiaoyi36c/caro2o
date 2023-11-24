package cn.wolfcode.item.goods.domain;

import cn.wolfcode.item.bill.domain.StockBill;
import cn.wolfcode.item.bills.domain.StockBillItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Subsidiary {

    private Long id;

    private String goodsName;

    private String brand;

    private BigDecimal price;

    private BigDecimal amounts;

    private String storeName;

    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date busiDate;

}
