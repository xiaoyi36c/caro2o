package cn.wolfcode.item.goods.domain.vo;

import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@ToString
@Getter
@Setter
public class GoodsVO2 extends BaseEntity
{
   /** 主键 */
   private Long id;

   /** 物品名称 */
   @Excel(name = "物品名称")
   private String goodsName;


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

   private InputStream goodsCover;

}
