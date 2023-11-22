package cn.wolfcode.client.contact.domain;

import java.util.Date;

import cn.wolfcode.client.customer.domain.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
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


@Data
public class CustomerLinkmane extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 id */
    private Long id;

    /** 客户id */
    @NotNull(message = "客户不能为空")
    private Customer customer;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inputTime;

}
