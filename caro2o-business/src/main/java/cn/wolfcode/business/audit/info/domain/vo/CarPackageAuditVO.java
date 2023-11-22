package cn.wolfcode.business.audit.info.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CarPackageAuditVO {
    private Long serviceItemId;
    private Long shopManagerId;
    private Long financialId;
    private String info;
}
