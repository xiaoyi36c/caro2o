package cn.wolfcode.item.sort.domain;

import lombok.Data;
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

@Data
public class GoodsCategory extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String categoryName;

    /** 描述 */
    @Excel(name = "描述")
    private String categoryDesc;

    /** id层级结构 */
    private String busiPath;






}
