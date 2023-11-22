package cn.wolfcode.client.customer.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 客户信息对象 customer
 * 
 * @author ruoyi
 * @date 2023-11-22
 */
public class Customer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String id;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String customerName;

    /** 法定代表人 */
    @Excel(name = "法定代表人")
    private String legalLeader;

    /** 成立时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "成立时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registerDate;

    /** 经营状态 */
    @Excel(name = "经营状态")
    private Integer openState;

    /** 所属地区 */
    @Excel(name = "所属地区")
    private String province;

    /** 注册资本 */
    private BigDecimal regCapital;

    /** 所属行业 */
    @Excel(name = "所属行业")
    private String industry;

    /** 经营范围 */
    @Excel(name = "经营范围")
    private String scope;

    /** 注册地址 */
    @Excel(name = "注册地址")
    private String regAddr;

    /** 录入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "录入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inputTime;

    /** 录入人 */
    @Excel(name = "录入人")
    private Long inputUser;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }
    public void setLegalLeader(String legalLeader) 
    {
        this.legalLeader = legalLeader;
    }

    public String getLegalLeader() 
    {
        return legalLeader;
    }
    public void setRegisterDate(Date registerDate) 
    {
        this.registerDate = registerDate;
    }

    public Date getRegisterDate() 
    {
        return registerDate;
    }
    public void setOpenState(Integer openState) 
    {
        this.openState = openState;
    }

    public Integer getOpenState() 
    {
        return openState;
    }
    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }
    public void setRegCapital(BigDecimal regCapital) 
    {
        this.regCapital = regCapital;
    }

    public BigDecimal getRegCapital() 
    {
        return regCapital;
    }
    public void setIndustry(String industry) 
    {
        this.industry = industry;
    }

    public String getIndustry() 
    {
        return industry;
    }
    public void setScope(String scope) 
    {
        this.scope = scope;
    }

    public String getScope() 
    {
        return scope;
    }
    public void setRegAddr(String regAddr) 
    {
        this.regAddr = regAddr;
    }

    public String getRegAddr() 
    {
        return regAddr;
    }
    public void setInputTime(Date inputTime) 
    {
        this.inputTime = inputTime;
    }

    public Date getInputTime() 
    {
        return inputTime;
    }
    public void setInputUser(Long inputUser) 
    {
        this.inputUser = inputUser;
    }

    public Long getInputUser() 
    {
        return inputUser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerName", getCustomerName())
            .append("legalLeader", getLegalLeader())
            .append("registerDate", getRegisterDate())
            .append("openState", getOpenState())
            .append("province", getProvince())
            .append("regCapital", getRegCapital())
            .append("industry", getIndustry())
            .append("scope", getScope())
            .append("regAddr", getRegAddr())
            .append("inputTime", getInputTime())
            .append("inputUser", getInputUser())
            .toString();
    }
}
