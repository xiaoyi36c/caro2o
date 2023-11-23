package cn.wolfcode.client.record.domain.vo;

import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
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
    @NotNull(message = "客户不能为空")
    @Excel(name = "客户")
    private String customerId;

    /** 联系人 */
    @NotNull(message = "联系人不能为空")
    @Excel(name = "联系人")
    private String linkmanId;

    /** 拜访方式 */
    @NotNull(message = "拜访方式不能为空")
    @Excel(name = "拜访方式")
    private Integer visitType;

    /** 拜访原因 */
    @NotNull(message = "拜访原因不能为空")
    @Excel(name = "拜访原因")
    private String visitReason;

    /** 交流内容 */
    @NotNull(message = "交流内容不能为空")
    @Excel(name = "交流内容")
    private String content;

    /** 拜访时间 */
    @NotNull(message = "拜访时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "拜访时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date visitDate;
}
