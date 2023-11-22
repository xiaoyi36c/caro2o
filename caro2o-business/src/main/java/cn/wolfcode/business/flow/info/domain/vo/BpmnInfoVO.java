package cn.wolfcode.business.flow.info.domain.vo;

import cn.wolfcode.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 流程定义明细对象 bus_bpmn_info
 * 
 * @author li
 * @date 2023-11-09
 */
@ToString
@Getter
@Setter
public class BpmnInfoVO extends BaseEntity
{
   @NotNull(message = "请选择流程类型")
   private Integer bpmnType;
   private MultipartFile bpmnFile;
   @NotNull(message = "请填写描述信息")
   private String info;

   public String getFileName(){
      return bpmnFile.getOriginalFilename();
   }
   // 获取输入流的方法
   public InputStream getInputStream(){
      try {
         // 尝试获取bpmn文件的输入流
         return bpmnFile.getInputStream();
      } catch (IOException e) {
         // 如果出现异常，抛出运行时异常
         throw new RuntimeException(e);
      }
   }
}
