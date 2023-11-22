package cn.wolfcode.business.flow.info.service;

import cn.wolfcode.business.audit.info.domain.CarPackageAudit;
import cn.wolfcode.business.audit.info.domain.vo.AuditHistoryVO;
import cn.wolfcode.business.audit.info.domain.vo.AuditVO;
import cn.wolfcode.business.flow.info.domain.BpmnInfo;
import cn.wolfcode.business.flow.info.domain.vo.BpmnInfoVO;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IProcessService {
    BpmnInfo deploy(BpmnInfoVO bpmnInfoVO);

    void removeProcessDefinition(String processDefinitionKey, Integer version);

    InputStream getResource(String processDefinitionKey, Integer version, String type);

    InputStream getResourceSVG(String processDefinitionKey, Integer version);

    String startProcessInstance(BpmnInfo bpmnInfo, Long id, Map<String, Object> variebles);

    String getProcessDefinationId(BpmnInfo bpmnInfo);

    void deleteProcessInstance(String instanceId, String reason);

    List<String> getBusinessKeys(BpmnInfo bpmnInfo);
    List<String> getDoneBusinessKeys(BpmnInfo bpmnInfo);


    boolean completeTask(String instanceId, AuditVO auditVO);

    List<AuditHistoryVO> selectAuditHistory(Long instanceId);

    InputStream getProgress(Long instanceId);


}
