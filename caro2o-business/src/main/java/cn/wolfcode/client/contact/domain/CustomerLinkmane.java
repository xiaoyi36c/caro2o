package cn.wolfcode.client.contact.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 客户联系人对象 customer_linkmane
 * 
 * @author BaiShuai
 * @date 2023-11-22
 */
public class CustomerLinkmane extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private String id;

    /** 客户 */
    @Excel(name = "客户")
    private String customerId;

    /** 联系人名字 */
    @Excel(name = "联系人名字")
    private String linkman;

    /** 性别 */
    @Excel(name = "性别")
    private Integer gender;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 联系人电话 */
    @Excel(name = "联系人电话")
    private String phone;

    /** 职位 */
    @Excel(name = "职位")
    private String position;

    /** 任职状态 */
    @Excel(name = "任职状态")
    private Integer positionState;

    /** 部门 */
    @Excel(name = "部门")
    private String department;

    /** 录入人 */
    @Excel(name = "录入人")
    private Long inputUser;

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
    public void setCustomerId(String customerId) 
    {
        this.customerId = customerId;
    }

    public String getCustomerId() 
    {
        return customerId;
    }
    public void setLinkman(String linkman) 
    {
        this.linkman = linkman;
    }

    public String getLinkman() 
    {
        return linkman;
    }
    public void setGender(Integer gender) 
    {
        this.gender = gender;
    }

    public Integer getGender() 
    {
        return gender;
    }
    public void setAge(Integer age) 
    {
        this.age = age;
    }

    public Integer getAge() 
    {
        return age;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setPosition(String position) 
    {
        this.position = position;
    }

    public String getPosition() 
    {
        return position;
    }
    public void setPositionState(Integer positionState) 
    {
        this.positionState = positionState;
    }

    public Integer getPositionState() 
    {
        return positionState;
    }
    public void setDepartment(String department) 
    {
        this.department = department;
    }

    public String getDepartment() 
    {
        return department;
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
            .append("linkman", getLinkman())
            .append("gender", getGender())
            .append("age", getAge())
            .append("phone", getPhone())
            .append("position", getPosition())
            .append("positionState", getPositionState())
            .append("department", getDepartment())
            .append("inputUser", getInputUser())
            .append("inputTime", getInputTime())
            .toString();
    }
}
