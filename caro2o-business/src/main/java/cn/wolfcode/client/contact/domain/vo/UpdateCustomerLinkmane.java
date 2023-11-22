package cn.wolfcode.client.contact.domain.vo;

import javax.validation.constraints.NotNull;

import cn.wolfcode.client.customer.domain.Customer;
import lombok.Data;

@Data
public class UpdateCustomerLinkmane {
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
}
