package cn.wolfcode.business.appointment.serviceitem.domain.vo;

import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 服务项对象 bus_service_item
 * 
 * @author li
 * @date 2023-11-03
 */
public class ServiceItemVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 服务项名称 */
    @Excel(name = "服务项名称")
    @NotNull(message = "服务项不能为空")
    private String name;

    /** 服务项原价 */
    @Excel(name = "服务项原价")
    @NotNull(message = "服务项原价不能为空")
    private BigDecimal originalPrice;

    /** 服务项折扣价 */
    @Excel(name = "服务项折扣价")
    @NotNull(message = "服务项折扣价不能为空")
    private BigDecimal discountPrice;

    /** 是否套餐 */
    @Excel(name = "是否套餐")
    @NotNull(message = "是否套餐不能为空")
    private Integer carPackage;

    /** 备注信息 */
    @Excel(name = "备注信息")
    @NotNull(message = "备注信息不能为空")
    private String info;

    /** 服务分类 */
    @Excel(name = "服务分类")
    @NotNull(message = "服务分类不能为空")
    private Integer serviceCatalog;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setOriginalPrice(BigDecimal originalPrice) 
    {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPrice() 
    {
        return originalPrice;
    }
    public void setDiscountPrice(BigDecimal discountPrice) 
    {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getDiscountPrice() 
    {
        return discountPrice;
    }
    public void setCarPackage(Integer carPackage) 
    {
        this.carPackage = carPackage;
    }

    public Integer getCarPackage() 
    {
        return carPackage;
    }
    public void setInfo(String info) 
    {
        this.info = info;
    }

    public String getInfo() 
    {
        return info;
    }
    public void setServiceCatalog(Integer serviceCatalog) 
    {
        this.serviceCatalog = serviceCatalog;
    }

    public Integer getServiceCatalog() 
    {
        return serviceCatalog;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("originalPrice", getOriginalPrice())
            .append("discountPrice", getDiscountPrice())
            .append("carPackage", getCarPackage())
            .append("info", getInfo())
            .append("createTime", getCreateTime())
            .append("serviceCatalog", getServiceCatalog())
            .toString();
    }
}
