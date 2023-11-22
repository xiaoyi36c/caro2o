package cn.wolfcode.client.customer.domain.vo;

import cn.wolfcode.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerVo {
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLegalLeader() {
        return legalLeader;
    }

    public void setLegalLeader(String legalLeader) {
        this.legalLeader = legalLeader;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Integer getOpenState() {
        return openState;
    }

    public void setOpenState(Integer openState) {
        this.openState = openState;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public BigDecimal getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(BigDecimal regCapital) {
        this.regCapital = regCapital;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRegAddr() {
        return regAddr;
    }

    public void setRegAddr(String regAddr) {
        this.regAddr = regAddr;
    }
}
