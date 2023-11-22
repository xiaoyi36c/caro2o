package cn.wolfcode.client.record.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 拜访信息对象 customer_visit
 * 
 * @author li
 * @date 2023-11-22
 */
public class CustomerVisit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一id */
    private Long id;

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

    /** 录入人 */
    @Excel(name = "录入人")
    private Long inputUser;

    /** 录入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "录入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inputTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCustomerId(String customerId) 
    {
        this.customerId = customerId;
    }

    public String getCustomerId() 
    {
        return customerId;
    }
    public void setLinkmanId(String linkmanId) 
    {
        this.linkmanId = linkmanId;
    }

    public String getLinkmanId() 
    {
        return linkmanId;
    }
    public void setVisitType(Integer visitType) 
    {
        this.visitType = visitType;
    }

    public Integer getVisitType() 
    {
        return visitType;
    }
    public void setVisitReason(String visitReason) 
    {
        this.visitReason = visitReason;
    }

    public String getVisitReason() 
    {
        return visitReason;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setVisitDate(Date visitDate) 
    {
        this.visitDate = visitDate;
    }

    public Date getVisitDate() 
    {
        return visitDate;
    }
    public void setInputUser(Long inputUser) 
    {
        this.inputUser = inputUser;
    }

    public Long getInputUser() 
    {
        return inputUser;
    }
    public void setInputTime(Date inputTime) 
    {
        this.inputTime = inputTime;
    }

    public Date getInputTime() 
    {
        return inputTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerId", getCustomerId())
            .append("linkmanId", getLinkmanId())
            .append("visitType", getVisitType())
            .append("visitReason", getVisitReason())
            .append("content", getContent())
            .append("visitDate", getVisitDate())
            .append("inputUser", getInputUser())
            .append("inputTime", getInputTime())
            .toString();
    }
}
