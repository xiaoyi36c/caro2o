package cn.wolfcode.business.audit.info.service.impl;

import java.io.InputStream;
import java.util.*;

import cn.wolfcode.business.appointment.serviceitem.domain.ServiceItem;
import cn.wolfcode.business.appointment.serviceitem.service.IServiceItemService;
import cn.wolfcode.business.audit.info.domain.vo.AuditHistoryVO;
import cn.wolfcode.business.audit.info.domain.vo.AuditVO;
import cn.wolfcode.business.audit.info.domain.vo.CarPackageAuditVO;
import cn.wolfcode.business.flow.info.domain.BpmnInfo;
import cn.wolfcode.business.flow.info.service.IBpmnInfoService;
import cn.wolfcode.business.flow.info.service.IProcessService;
import cn.wolfcode.common.utils.DateUtils;
import cn.wolfcode.common.utils.PageUtils;
import cn.wolfcode.common.utils.SecurityUtils;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.audit.info.mapper.CarPackageAuditMapper;
import cn.wolfcode.business.audit.info.domain.CarPackageAudit;
import cn.wolfcode.business.audit.info.service.ICarPackageAuditService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 审核信息Service业务层处理
 *
 * @author li
 * @date 2023-11-10
 */
@Service
public class CarPackageAuditServiceImpl implements ICarPackageAuditService {
    @Autowired
    private CarPackageAuditMapper carPackageAuditMapper;
    @Autowired
    private IServiceItemService serviceItemService;
    @Autowired
    private IBpmnInfoService bpmnInfoService;
    @Autowired
    private IProcessService processService;

    /**
     * 查询审核信息
     *
     * @param id 审核信息主键
     * @return 审核信息
     */
    @Override
    public CarPackageAudit selectCarPackageAuditById(Long id) {
        return carPackageAuditMapper.selectCarPackageAuditById(id);
    }

    /**
     * 查询审核信息列表
     *
     * @param carPackageAudit 审核信息
     * @return 审核信息
     */
    @Override
    public List<CarPackageAudit> selectCarPackageAuditList(CarPackageAudit carPackageAudit) {
        return carPackageAuditMapper.selectCarPackageAuditList(carPackageAudit);
    }

    /**
     * 新增审核信息
     *
     * @param carPackageAuditVO 审核信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertCarPackageAudit(CarPackageAuditVO carPackageAuditVO) {
        //查询服务项
        // 通过服务项ID查询服务项
        ServiceItem serviceItem = serviceItemService.selectServiceItemById(carPackageAuditVO.getServiceItemId());
        // 确保服务项不为空
        Assert.notNull(serviceItem, "参数错误");
        //状态检查(初始化,审核拒绝)
        org.springframework.util.Assert.state(serviceItem.getAuditStatus() == ServiceItem.AUDIT_STATUS_INITIALIZE ||
                serviceItem.getSaleStatus() == ServiceItem.AUDIT_STATUS_REFUSE, "当前状态不允许审核");
        //查询最新的流程定义信息
        // 通过流程定义类型查询最新的流程定义信息
        BpmnInfo bpmnInfo = bpmnInfoService.selectLatestBpmnInfo(BpmnInfo.CAR_PACKAGE_AUDIT);
        // 确保流程定义信息不为空
        Assert.notNull(bpmnInfo, "请先部署套餐审核流程定义");

        //保存核信息（生成审核信息的id，将其作为业务标识）
        // 创建新的套餐审核对象
        CarPackageAudit carPackageAudit = new CarPackageAudit();

        // 设置套餐审核对象的各个属性
        carPackageAudit.setInfo(carPackageAuditVO.getInfo());
        carPackageAudit.setServiceItemId(serviceItem.getId());
        carPackageAudit.setCreatorId(SecurityUtils.getUserId() + "");
        carPackageAudit.setServiceItemInfo(serviceItem.getInfo());
        carPackageAudit.setServiceItemName(serviceItem.getName());
        carPackageAudit.setServiceItemPrice(serviceItem.getDiscountPrice());
        carPackageAudit.setStatus(CarPackageAudit.AUDITING);
        carPackageAudit.setCreateTime(new Date());

        // 将套餐审核对象插入到数据库中
        carPackageAuditMapper.insertCarPackageAudit(carPackageAudit);

        // 创建流程变量Map
        Map<String, Object> variebles = new HashMap<>();
        // 设置流程变量
        variebles.put("managerId", carPackageAuditVO.getShopManagerId());
        variebles.put("financialId", carPackageAuditVO.getFinancialId());
        variebles.put("discountPrice", serviceItem.getDiscountPrice().intValue());

        //开启流程实例
        // 通过流程定义信息、业务标识和流程变量开启流程实例
        String processInstanceId = processService.startProcessInstance(bpmnInfo, carPackageAudit.getId(), variebles);

        //将流程实例id更新到审核信息表中
        // 创建新的套餐审核对象
        CarPackageAudit audit = new CarPackageAudit();
        // 设置套餐审核对象的id和流程实例id
        audit.setId(carPackageAudit.getId());
        audit.setInstanceId(processInstanceId);
        // 更新套餐审核对象的流程实例id
        carPackageAuditMapper.updateCarPackageAudit(audit);

        //更新服务项的额状态为审核中
        // 通过服务项ID更新服务项的审核状态为审核中
        serviceItemService.updateAuditStatus(serviceItem.getId(), ServiceItem.AUDIT_STATUS_AUDITING);
        return 1;
    }

    /**
     * 修改审核信息
     *
     * @param carPackageAudit 审核信息
     * @return 结果
     */
    @Override
    public int updateCarPackageAudit(CarPackageAudit carPackageAudit) {
        return carPackageAuditMapper.updateCarPackageAudit(carPackageAudit);
    }

    /**
     * 批量删除审核信息
     *
     * @param ids 需要删除的审核信息主键
     * @return 结果
     */
    @Override
    public int deleteCarPackageAuditByIds(Long[] ids) {
        return carPackageAuditMapper.deleteCarPackageAuditByIds(ids);
    }

    /**
     * 删除审核信息信息
     *
     * @param id 审核信息主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCarPackageAuditById(Long id) {
        //修改当前审核信息的状态为撤销审核
        CarPackageAudit carPackageAudit = carPackageAuditMapper.selectCarPackageAuditById(id);
        Assert.notNull(carPackageAudit, "参数错误");
        Assert.state(Objects.equals(carPackageAudit.getStatus(), CarPackageAudit.AUDITING), "当前状态不允许撤销");
        CarPackageAudit audit = new CarPackageAudit();
        audit.setId(id);
        audit.setStatus(CarPackageAudit.AUDIT_CANCEL);
        int updateCarPackageAuditRows = carPackageAuditMapper.updateCarPackageAudit(audit);
        Assert.state(updateCarPackageAuditRows > 0, "审核信息状态修改失败");
        //修改服务项的状态为初始化
        serviceItemService.updateAuditStatus(carPackageAudit.getServiceItemId(), ServiceItem.AUDIT_STATUS_INITIALIZE);
        //删除流程实例
        processService.deleteProcessInstance(carPackageAudit.getInstanceId(), SecurityUtils.getUsername() + "撤销了当前审核信息");
    }
    //查询我的已办
    @Override
    public List<CarPackageAudit> doneList() {
        //查询最新的流程定义信息
        BpmnInfo bpmnInfo = bpmnInfoService.selectLatestBpmnInfo(BpmnInfo.CAR_PACKAGE_AUDIT);
        Assert.notNull(bpmnInfo," 请先部署流程定义");
        //查询当前用户的任务（definationKey，当前用户的id）
        //根据任务查询到对应的流程实例
        //查询到流程实例中存储的业务标识(审核信息id)
        List<String> auditIds = processService.getDoneBusinessKeys(bpmnInfo);
        if (auditIds.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        //查询任务对应的审核信息
        PageUtils.startPage();
        return carPackageAuditMapper.selectCarPackageAuditByIds(auditIds);
    }
    //查询我的代办
    @Override
    public List<CarPackageAudit> todoList() {
        //查询最新的流程定义信息
        BpmnInfo bpmnInfo = bpmnInfoService.selectLatestBpmnInfo(BpmnInfo.CAR_PACKAGE_AUDIT);
        Assert.notNull(bpmnInfo," 请先部署流程定义");
        //查询当前用户的任务（definationKey，当前用户的id）
        //根据任务查询到对应的流程实例
        //查询到流程实例中存储的业务标识(审核信息id)
        List<String> auditIds = processService.getBusinessKeys(bpmnInfo);
        if (auditIds.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        //查询任务对应的审核信息
        PageUtils.startPage();
        return carPackageAuditMapper.selectCarPackageAuditByIds(auditIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void audit(AuditVO auditVO) {
        //从审核信息表中查询到实例id
        CarPackageAudit packageAudit = carPackageAuditMapper.selectCarPackageAuditById(auditVO.getId());
        Assert.notNull(packageAudit,"参数错误");
        //根据流程实例id+当前用户查询处理的当前任务
        //处理任务，根据前端传递的参数，确定passed变量的值
        boolean ended = processService.completeTask(packageAudit.getInstanceId(),auditVO);
        //如果没有下一个节点，修改审核信息和服务项的状态
        if (ended){
            int packageAuditStatus = auditVO.getPassed() ? CarPackageAudit.AUDIT_PASSED : CarPackageAudit.AUDIT_REFUSED;
            int serviceItemStatus = auditVO.getPassed() ? ServiceItem.AUDIT_STATUS_PASS : ServiceItem.AUDIT_STATUS_REFUSE;
            carPackageAuditMapper.updateCarPackageAuditStatus(packageAudit.getId(),packageAuditStatus);
            serviceItemService.updateAuditStatus(packageAudit.getServiceItemId(), serviceItemStatus);
        }
    }

    //审核历史
    @Override
    public List<AuditHistoryVO> selectAuditHistory(Long instanceId) {
        List<AuditHistoryVO> auditHistoryVOS = processService.selectAuditHistory(instanceId);
        return auditHistoryVOS;
    }

    @Override
    public InputStream getProgress(Long instanceId) {
        return processService.getProgress(instanceId);
    }

}
