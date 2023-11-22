package cn.wolfcode.business.flow.info.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 流程定义明细对象 bus_bpmn_info
 * 
 * @author li
 * @date 2023-11-09
 */
public class BpmnInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    public static final Integer CAR_PACKAGE_AUDIT = 0;
    /** 主键 */
    private Long id;

    /** 流程名称 */
    private String bpmnLabel;

    /** 流程类型 */
    private Integer bpmnType;

    /** 流程定义key */
    private String processDefinitionKey;

    /** 部署时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deployTime;

    /** 版本号 */
    private Integer version;

    /** 描述信息 */
    private String info;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBpmnLabel(String bpmnLabel) 
    {
        this.bpmnLabel = bpmnLabel;
    }

    public String getBpmnLabel() 
    {
        return bpmnLabel;
    }
    public void setBpmnType(Integer bpmnType) 
    {
        this.bpmnType = bpmnType;
    }

    public Integer getBpmnType() 
    {
        return bpmnType;
    }
    public void setProcessDefinitionKey(String processDefinitionKey) 
    {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getProcessDefinitionKey() 
    {
        return processDefinitionKey;
    }
    public void setDeployTime(Date deployTime) 
    {
        this.deployTime = deployTime;
    }

    public Date getDeployTime() 
    {
        return deployTime;
    }
    public void setVersion(Integer version) 
    {
        this.version = version;
    }

    public Integer getVersion() 
    {
        return version;
    }
    public void setInfo(String info) 
    {
        this.info = info;
    }

    public String getInfo() 
    {
        return info;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bpmnLabel", getBpmnLabel())
            .append("bpmnType", getBpmnType())
            .append("processDefinitionKey", getProcessDefinitionKey())
            .append("deployTime", getDeployTime())
            .append("version", getVersion())
            .append("info", getInfo())
            .toString();
    }
}
