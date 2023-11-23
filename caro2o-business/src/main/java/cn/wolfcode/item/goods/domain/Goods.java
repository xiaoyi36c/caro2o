package cn.wolfcode.item.goods.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

import java.io.InputStream;

/**
 * 物品信息对象 goods
 * 
 * @author Baishuai
 * @date 2023-11-23
 */

@Data
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
    private InputStream goodsCover;

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


}
