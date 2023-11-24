package cn.wolfcode.item.goods.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

import javax.validation.constraints.NotNull;

/**
 * 物品信息对象 goods
 *
 * @author Baishuai
 * @date 2023-11-24
 */

@Data
public class Goods extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 物品名称 */
    @NotNull(message = "物品名称不能为空")
    private String goodsName;

    /** 封面 */
    private String goodsCover;

    /** 分类 */
    @NotNull(message = "分类不能为空")
    private Long categoryId;

    /** 品牌 */

    private String brand;

    /** 数量 */

    private String spec;

    /** 描述 */

    private String goodsDesc;
}
