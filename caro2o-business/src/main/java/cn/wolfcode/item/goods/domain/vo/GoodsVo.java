package cn.wolfcode.item.goods.domain.vo;

import cn.wolfcode.common.core.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 物品信息对象 goods
 *
 * @author Baishuai
 * @date 2023-11-24
 */

@Data
public class GoodsVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 物品id */
    private Long id;

    /** 物品名称 */
    @NotNull(message = "物品名称不能为空")
    private String goodsName;

    /** 数量 */

    private String amounts;

   //价格
    private String price;
    //单据id
    private Long stockBillId;
    //状态
    private int state;
}
