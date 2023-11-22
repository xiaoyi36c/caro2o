package cn.wolfcode.business.flow.info.service.impl;

import cn.wolfcode.business.audit.info.domain.CarPackageAudit;
import cn.wolfcode.business.audit.info.domain.vo.AuditHistoryVO;
import cn.wolfcode.business.audit.info.domain.vo.AuditVO;
import cn.wolfcode.business.flow.info.domain.BpmnInfo;
import cn.wolfcode.business.flow.info.domain.vo.BpmnInfoVO;
import cn.wolfcode.business.flow.info.service.IProcessService;
import cn.wolfcode.common.utils.DateUtils;
import cn.wolfcode.common.utils.SecurityUtils;
import cn.wolfcode.common.utils.file.FileUtils;
import io.jsonwebtoken.lang.Assert;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

@Service
public class ProcessServiceImpl implements IProcessService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Override
    public BpmnInfo deploy(BpmnInfoVO bpmnInfoVO) {
        // 创建部署构建器
        DeploymentBuilder deployment = repositoryService.createDeployment();
        // 获取文件名
        String fileName = bpmnInfoVO.getFileName();
        String nameNotSuffix = FileUtils.getNameNotSuffix(fileName);
        // 设置部署名称
        deployment.name(nameNotSuffix);
        // 如果文件名以.zip结尾
        if (fileName.endsWith(".zip")) {
            // 添加zip输入流
            deployment.addZipInputStream(new ZipInputStream(bpmnInfoVO.getInputStream()));
        } else {
            // 添加输入流
            deployment.addInputStream(fileName, bpmnInfoVO.getInputStream());
        }
        // 部署
        deployment.deploy();
        //查Deployment对象
        // 创建部署查询
        Deployment deploy = repositoryService.createDeploymentQuery()
                .deploymentName(nameNotSuffix)
                .orderByDeploymenTime().desc()
                .list().get(0);
        // 查询流程定义对象
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploy.getId())
                .singleResult();
        // 创建BpmnInfo对象
        BpmnInfo bpmnInfo = new BpmnInfo();
        // 设置Bpmn标签
        bpmnInfo.setBpmnLabel(processDefinition.getName());
        // 设置Bpmn类型
        bpmnInfo.setBpmnType(bpmnInfoVO.getBpmnType());
        // 设置信息
        bpmnInfo.setInfo(bpmnInfoVO.getInfo());
        // 设置部署时间
        bpmnInfo.setDeployTime(deploy.getDeploymentTime());
        // 设置流程定义键
        bpmnInfo.setProcessDefinitionKey(processDefinition.getKey());
        // 设置版本
        bpmnInfo.setVersion(processDefinition.getVersion());
        return bpmnInfo;
    }

    @Override
    public void removeProcessDefinition(String processDefinitionKey, Integer version) {
        // 创建流程定义查询
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .processDefinitionVersion(version)
                .singleResult();
        // 检查流程定义是否存在
        Assert.notNull(processDefinition, "当前流程定义不存在");
        // 级联删除，如果当前定义下有已经开启的实力，那么先将实例删除，在删除定义
        // 非级联删除，如果当前定义下有已经开启的实力，那么报错
        // 删除流程定义
        repositoryService.deleteDeployment(processDefinition.getDeploymentId());
    }

    @Override
    public InputStream getResource(String processDefinitionKey, Integer version, String type) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .processDefinitionVersion(version)
                .singleResult();
        if ("file".equals(type)) {
            return repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
        }
        //查图片
//        if (StringUtils.hasLength(processDefinition.getDiagramResourceName())) {
//        }
        return repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
       /* DefaultProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
        // 获取BpmnModal
        BpmnModel model = repositoryService.getBpmnModel(processDefinition.getId());
        //自动生成流程困
        return generator.generateDiagram(model, "宋体", "宋体", "宋体");*/
    }

    @Override
    public InputStream getResourceSVG(String processDefinitionKey, Integer version) {
        // 创建流程定义查询
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .processDefinitionVersion(version)
                .singleResult();
        DefaultProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
        // 获取BpmnModal
        BpmnModel model = repositoryService.getBpmnModel(processDefinition.getId());
        //自动生成流程困
        return generator.generateDiagram(model, "宋体", "宋体", "宋体");
    }

    @Override
    public String startProcessInstance(BpmnInfo bpmnInfo, Long id, Map<String, Object> variebles) {
        //查询最新的流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(bpmnInfo.getProcessDefinitionKey())
                .processDefinitionVersion(bpmnInfo.getVersion())
                .singleResult();
        //开启流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), id.toString(), variebles);
        //返回流程实例的id
        return processInstance.getId();
    }

    @Override
    public String getProcessDefinationId(BpmnInfo bpmnInfo) {
        //查询最新的流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(bpmnInfo.getProcessDefinitionKey())
                .processDefinitionVersion(bpmnInfo.getVersion())
                .singleResult();
        return processDefinition.getId();
    }

    @Override
    public void deleteProcessInstance(String instanceId, String reason) {
        runtimeService.deleteProcessInstance(instanceId, reason);
    }

    @Override
    public List<String> getBusinessKeys(BpmnInfo bpmnInfo) {
        // 创建任务查询
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey(bpmnInfo.getProcessDefinitionKey())
                .taskAssignee(SecurityUtils.getUserId().toString())
                .list();
        // 如果任务列表为空，返回空列表
        if (tasks.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        // 将tasks集合转换成存放实例id的集合
        Set<String> instanceIds = tasks.stream().map(TaskInfo::getProcessInstanceId).collect(Collectors.toSet());
        // 创建流程实例查询
        List<ProcessInstance> instances = runtimeService.createProcessInstanceQuery()
                .processInstanceIds(instanceIds)
                .list();
        // 返回业务键列表
        return instances.stream().map(ProcessInstance::getBusinessKey).collect(Collectors.toList());
    }

    @Override
    public List<String> getDoneBusinessKeys(BpmnInfo bpmnInfo) {
        List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(SecurityUtils.getUserId().toString())
                .finished()
                .list();
        if (tasks.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        Set<String> collect = tasks.stream().map(TaskInfo::getProcessInstanceId).collect(Collectors.toSet());
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery()
                .processInstanceIds(collect)
                .list();
        return list.stream().map(HistoricProcessInstance::getBusinessKey).collect(Collectors.toList());
    }

    @Override
    public boolean completeTask(String instanceId, AuditVO auditVO) {
        // 查询当前需要处理的任务
        Task task = taskService.createTaskQuery()
                .processInstanceId(instanceId)
                .taskAssignee(SecurityUtils.getUserId().toString())
                .singleResult();
        Assert.notNull(task,"当前任务不存在");
        Map<String, Object> variables = new HashMap<>();
        variables.put("passed",auditVO.getPassed());
        taskService.addComment(task.getId(), task.getProcessInstanceId(), auditVO.getInfo());
        taskService.complete(task.getId(),variables);
        // 判断当前审核实例是否结束
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();
        //如果processInstance为null,表示结束
        return processInstance == null;
    }
    //历史查询
    @Override
    public List<AuditHistoryVO> selectAuditHistory(Long instanceId) {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(instanceId.toString()).orderByProcessInstanceId().asc()
                .list();
        Assert.notEmpty(list, "参数错误");

        List<AuditHistoryVO> auditHistoryVOS = new ArrayList<>();
        for (HistoricTaskInstance task : list) {
            AuditHistoryVO auditHistoryVO = new AuditHistoryVO();
            auditHistoryVO.setName(task.getName());
            auditHistoryVO.setStartTime(task.getStartTime());
            if (task.getEndTime() != null) {
                auditHistoryVO.setEndTime(task.getEndTime());
                String elapsedTime = DateUtils.timeDistance(task.getEndTime(), task.getStartTime());
                auditHistoryVO.setElapsedTime(elapsedTime);
            }else {
                String elapsedTime = DateUtils.timeDistance(new Date(), task.getStartTime());
                auditHistoryVO.setElapsedTime(elapsedTime);
            }
            if (task.getDeleteReason() == null){
                List<Comment> taskComments = taskService.getTaskComments(task.getId());
                    if (taskComments.isEmpty()) {
                        auditHistoryVO.setInfo("审核正在进行中!");
                    }else {
                        auditHistoryVO.setInfo(taskComments.get(0).getFullMessage());
                    }

            }else{
                auditHistoryVO.setInfo(task.getDeleteReason());
            }
            auditHistoryVOS.add(auditHistoryVO);
        }
        return auditHistoryVOS;
    }
    //获取进度
    @Override
    public InputStream getProgress(Long instanceId) {
//        // 准备当前正在运行的任务id集合
//        List<String> activeActivityIds = null;
//
//        // 判断当前实例的结束时间
//        if(processInstance.getEndTime()== null){
//            // 如果为空---》当前实例没有结束---》获取到实例下正在运行的任务id---》getActiveActivityIds
//            activeActivityIds=runtimeService.getActiveActivityIds(carPackageAudit.getInstanceId());
//        }else {
//            // 已经结束返回空集合---》防止空指针异常
//            activeActivityIds=Collections.EMPTY_LIST;
//        }
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(instanceId.toString()).list();
        Assert.notEmpty(list, "参数错误");
        DefaultProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
        // 获取BpmnModal
        BpmnModel model = repositoryService.getBpmnModel(list.get(0).getProcessDefinitionId());
        if (list.get(list.size() - 1).getEndTime() == null) {
            List<String> activeActivityIds = runtimeService.getActiveActivityIds(instanceId.toString());
            //自动生成流程困
            return generator.generateDiagram(model,activeActivityIds,Collections.emptyList(), "宋体", "宋体", "宋体");
        }
        return generator.generateDiagram(model, "宋体", "宋体", "宋体");
    }
}
