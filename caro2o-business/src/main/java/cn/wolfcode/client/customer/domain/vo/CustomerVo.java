package cn.wolfcode.client.customer.domain.vo;

import cn.wolfcode.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class CustomerVo {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /** 企业名称 */
    @Excel(name = "企业名称")
    @NotNull(message = "企业名称不能为空")
    private String customerName;

    /** 法定代表人 */
    @Excel(name = "法定代表人")
    @NotNull(message = "法定代表人不能为空")
    private String legalLeader;

    /** 成立时间 */
    @NotNull(message = "成立时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "成立时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registerDate;

    /** 经营状态 */
    @Excel(name = "经营状态")
    @NotNull(message = "经营状态不能为空")
    private Integer openState;

    /** 所属地区 */
    @Excel(name = "所属地区")
    @NotNull(message = "所属省份不能为空")
    private String province;

    /** 注册资本 */
    @NotNull(message = "注册资本不能为空")
    private BigDecimal regCapital;

    /** 所属行业 */
    @Excel(name = "所属行业")
    @NotNull(message = "所属行业不能为空")
    private String industry;

    /** 经营范围 */
    @Excel(name = "经营范围")
    @NotNull(message = "经营范围不能为空")
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
