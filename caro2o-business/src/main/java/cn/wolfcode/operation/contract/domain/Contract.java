package cn.wolfcode.operation.contract.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 合同信息对象 contract
 * 
 * @author ruoyi
 * @date 2023-11-22
 */
public class Contract extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    // 在用
    public static final Integer IN_USE = 0;
    // 作废
    public static final Integer CANCELLATION = -1;
    // 未盖章
    public static final Integer  UNSEALED = 1;
    // 盖章
    public static final Integer STAMP = 1;
    // 未审批
    public static final Integer UNREVIEWED = 0;
    // 审批通过
    public static final Integer PASS_THE_AUDIT = 1;
    // 审批不通过
    public static final Integer FAIL_THE_AUDIT = 2;

    /** $column.columnComment */
    private String id;

    /** 客户id */
    @Excel(name = "客户id")
    private Long customerId;

    /** 合同名称 */
    @Excel(name = "合同名称")
    private String contractName;

    /** 合同编码 */
    @Excel(name = "合同编码")
    private String contractCode;

    /** 合同金额 */
    @Excel(name = "合同金额")
    private BigDecimal amounts;

    /** 合同生效开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "合同生效开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 合同生效结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "合同生效结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 电子附件 */
    @Excel(name = "电子附件")
    private String appendix;

    /** 是否盖章 */
    @Excel(name = "是否盖章")
    private Integer affixSealState;

    /** 审核状态  */
    @Excel(name = "审核状态 ")
    private Integer auditState;

    /** 是否作废 */
    @Excel(name = "是否作废")
    private Integer nullifyState;

    /** 录入人 */
    @Excel(name = "录入人")
    private String inputUser;

    /** 录入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "录入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inputTime;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public Long getCustomerId()
    {
        return customerId;
    }
    public void setContractName(String contractName) 
    {
        this.contractName = contractName;
    }

    public String getContractName() 
    {
        return contractName;
    }
    public void setContractCode(String contractCode) 
    {
        this.contractCode = contractCode;
    }

    public String getContractCode() 
    {
        return contractCode;
    }
    public void setAmounts(BigDecimal amounts) 
    {
        this.amounts = amounts;
    }

    public BigDecimal getAmounts() 
    {
        return amounts;
    }
    public void setStartDate(Date startDate) 
    {
        this.startDate = startDate;
    }

    public Date getStartDate() 
    {
        return startDate;
    }
    public void setEndDate(Date endDate) 
    {
        this.endDate = endDate;
    }

    public Date getEndDate() 
    {
        return endDate;
    }
    public void setAppendix(String appendix) 
    {
        this.appendix = appendix;
    }

    public String getAppendix() 
    {
        return appendix;
    }
    public void setAffixSealState(Integer affixSealState) 
    {
        this.affixSealState = affixSealState;
    }

    public Integer getAffixSealState() 
    {
        return affixSealState;
    }
    public void setAuditState(Integer auditState) 
    {
        this.auditState = auditState;
    }

    public Integer getAuditState() 
    {
        return auditState;
    }
    public void setNullifyState(Integer nullifyState) 
    {
        this.nullifyState = nullifyState;
    }

    public Integer getNullifyState() 
    {
        return nullifyState;
    }
    public void setInputUser(String inputUser)
    {
        this.inputUser = inputUser;
    }

    public String getInputUser()
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
            .append("contractName", getContractName())
            .append("contractCode", getContractCode())
            .append("amounts", getAmounts())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("appendix", getAppendix())
            .append("affixSealState", getAffixSealState())
            .append("auditState", getAuditState())
            .append("nullifyState", getNullifyState())
            .append("inputUser", getInputUser())
            .append("inputTime", getInputTime())
            .toString();
    }
}
