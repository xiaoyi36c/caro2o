package cn.wolfcode.client.contact.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

import javax.validation.constraints.NotNull;

/**
 * 客户联系人对象 customer_linkmane
 * 
 * @author BaiShuai
 * @date 2023-11-22
 */
public class CustomerLinkmane extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 id */
    private Long id;

    /** 客户id */
    @NotNull(message = "客户不能为空")
    private Long customerId;

    /** 联系人名字 */
    @NotNull(message = "联系人名字不能为空")
    private String linkman;

    /** 性别 */
    @NotNull(message = "性别不能为空")
    private Integer gender;

    /** 年龄 */
    @NotNull(message = "年龄不能为空")
    private Integer age;

    /** 联系人电话 */
    @NotNull(message = "联系人电话不能为空")
    private String phone;

    /** 职位 */
    @NotNull(message = "职位不能为空")
    private String position;

    /** 任职状态 */
    @NotNull(message = "任职状态不能为空")
    private Integer positionState;

    /** 部门 */
    @NotNull(message = "部门不能为空")
    private String department;

    /** 录入人 */

    private String inputUser;

    /** 录入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date inputTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
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
