package cn.wolfcode.item.goods.domain.vo;

import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;

@ToString
@Getter
@Setter
public class GoodsVO extends BaseEntity
{
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

   /** 描述 */
   @Excel(name = "描述")
   private String goodsDesc;

   private MultipartFile pictureFile;


   public String getFileName(){
      return pictureFile.getOriginalFilename();
   }
   // 获取输入流的方法
   public InputStream getInputStream(){
      try {
         // 尝试获取bpmn文件的输入流
         return pictureFile.getInputStream();
      } catch (IOException e) {
         // 如果出现异常，抛出运行时异常
         throw new RuntimeException(e);
      }
   }
}
