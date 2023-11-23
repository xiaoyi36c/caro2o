package cn.wolfcode.item.goods.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 物品信息对象 goods
 * 
 * @author Baishuai
 * @date 2023-11-23
 */
public class Goods extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 物品名称 */
    @Excel(name = "物品名称")
    private String goodsName;

    /** 封面 */
    @Excel(name = "封面")
    private String goodsCover;

    /** 分类 */
    @Excel(name = "分类")
    private Long categoryId;

    /** 品牌 */
    @Excel(name = "品牌")
    private String brand;

    /** 数量 */
    @Excel(name = "数量")
    private String spec;

    /** 描述 */
    @Excel(name = "描述")
    private String goodsDesc;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGoodsName(String goodsName) 
    {
        this.goodsName = goodsName;
    }

    public String getGoodsName() 
    {
        return goodsName;
    }
    public void setGoodsCover(String goodsCover) 
    {
        this.goodsCover = goodsCover;
    }

    public String getGoodsCover() 
    {
        return goodsCover;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setBrand(String brand) 
    {
        this.brand = brand;
    }

    public String getBrand() 
    {
        return brand;
    }
    public void setSpec(String spec) 
    {
        this.spec = spec;
    }

    public String getSpec() 
    {
        return spec;
    }
    public void setGoodsDesc(String goodsDesc) 
    {
        this.goodsDesc = goodsDesc;
    }

    public String getGoodsDesc() 
    {
        return goodsDesc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("goodsName", getGoodsName())
            .append("goodsCover", getGoodsCover())
            .append("categoryId", getCategoryId())
            .append("brand", getBrand())
            .append("spec", getSpec())
            .append("goodsDesc", getGoodsDesc())
            .toString();
    }
}
