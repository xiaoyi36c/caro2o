package cn.wolfcode.client.record.domain.vo;

import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 拜访信息对象 customer_visit
 * 
 * @author li
 * @date 2023-11-22
 */
@Data
public class CustomerVisitVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 客户 */
    @Excel(name = "客户")
    private String customerId;

    /** 联系人 */
    @Excel(name = "联系人")
    private String linkmanId;

    /** 拜访方式 */
    @Excel(name = "拜访方式")
    private Integer visitType;

    /** 拜访原因 */
    @Excel(name = "拜访原因")
    private String visitReason;

    /** 交流内容 */
    @Excel(name = "交流内容")
    private String content;

    /** 拜访时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "拜访时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date visitDate;
}
