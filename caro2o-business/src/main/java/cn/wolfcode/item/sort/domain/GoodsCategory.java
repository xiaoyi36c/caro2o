package cn.wolfcode.item.sort.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.TreeEntity;

/**
 * 物品分类信息对象 goods_category
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
public class GoodsCategory extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private String id;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String categoryName;

    /** 描述 */
    @Excel(name = "描述")
    private String categoryDesc;

    /** id层级结构 */
    private String busiPath;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
    {
        return categoryName;
    }
    public void setCategoryDesc(String categoryDesc) 
    {
        this.categoryDesc = categoryDesc;
    }

    public String getCategoryDesc() 
    {
        return categoryDesc;
    }
    public void setBusiPath(String busiPath) 
    {
        this.busiPath = busiPath;
    }

    public String getBusiPath() 
    {
        return busiPath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryName", getCategoryName())
            .append("categoryDesc", getCategoryDesc())
            .append("busiPath", getBusiPath())
            .append("parentId", getParentId())
            .toString();
    }
}
