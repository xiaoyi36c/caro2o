package cn.wolfcode.business.audit.info.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuditVO {
    @NotNull(message = "请选择审批意见")
    private Boolean passed;
    @NotNull(message = "请写意见")
    private String info;
    private Long id;
}
