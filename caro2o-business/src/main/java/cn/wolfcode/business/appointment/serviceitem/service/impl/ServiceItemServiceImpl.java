package cn.wolfcode.business.appointment.serviceitem.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wolfcode.business.appointment.serviceitem.constant.Constants;
import cn.wolfcode.business.appointment.serviceitem.domain.vo.ServiceItemVO;
import cn.wolfcode.business.appointment.serviceitem.utils.BpmnModelUtils;
import cn.wolfcode.business.flow.info.domain.BpmnInfo;
import cn.wolfcode.business.flow.info.service.IBpmnInfoService;
import cn.wolfcode.business.flow.info.service.IProcessService;
import cn.wolfcode.common.core.domain.entity.SysUser;
import cn.wolfcode.common.utils.DateUtils;
import cn.wolfcode.common.utils.bean.BeanUtils;
import cn.wolfcode.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.appointment.serviceitem.mapper.ServiceItemMapper;
import cn.wolfcode.business.appointment.serviceitem.domain.ServiceItem;
import cn.wolfcode.business.appointment.serviceitem.service.IServiceItemService;
import org.springframework.util.Assert;

/**
 * 服务项Service业务层处理
 *
 * @author li
 * @date 2023-11-03
 */
@Service
public class ServiceItemServiceImpl implements IServiceItemService {
    @Autowired
    private ServiceItemMapper serviceItemMapper;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private BpmnModelUtils bpmnModelUtils;
    @Autowired
    private IBpmnInfoService bpmnInfoService;
    @Autowired
    private IProcessService processService;

    /**
     * 查询服务项
     *
     * @param id 服务项主键
     * @return 服务项
     */
    @Override
    public ServiceItem selectServiceItemById(Long id) {
        return serviceItemMapper.selectServiceItemById(id);
    }

    /**
     * 查询服务项列表
     *
     * @param serviceItem 服务项
     * @return 服务项
     */
    @Override
    public List<ServiceItem> selectServiceItemList(ServiceItem serviceItem) {
        return serviceItemMapper.selectServiceItemList(serviceItem);
    }

    /**
     * 新增服务项
     *
     * @param serviceItemVO 服务项
     * @return 结果
     */
    @Override
    public int insertServiceItem(ServiceItemVO serviceItemVO) {
        // 折扣价不能大于原价
        // 因为价格是BigDecimal类型，所以在比较大小时建议是compareTo方法实现
        BigDecimal discountPrice = serviceItemVO.getDiscountPrice();
        BigDecimal originalPrice = serviceItemVO.getOriginalPrice();
        Assert.state(discountPrice.compareTo(originalPrice) <= 0, "折扣价不能大于原价");

        // V0中没有审核状态，应该如何封装审核状态呢?
        //可以将数据全部封装到实体类中
        ServiceItem serviceItem = new ServiceItem();
        BeanUtils.copyBeanProp(serviceItem, serviceItemVO);
        // 如果是套餐，将审核状态设置为初始化
        if (serviceItemVO.getCarPackage() == ServiceItem.CAR_PACKAGE_YES) {
            serviceItem.setAuditStatus(ServiceItem.AUDIT_STATUS_INITIALIZE);
        }
        serviceItem.setCreateTime(DateUtils.getNowDate());
        int i = serviceItemMapper.insertServiceItem(serviceItem);
        System.out.println(i);
        return i;
    }

    /**
     * 修改服务项
     *
     * @param serviceItemVO 服务项
     * @return 结果
     */
    @Override
    public int updateServiceItem(ServiceItemVO serviceItemVO) {
        ServiceItem serviceItemOld = serviceItemMapper.selectServiceItemById(serviceItemVO.getId());
        Assert.notNull(serviceItemOld, "参数错误");
        // 状态校验
        // 确保服务项的审核状态不是已上架或审核中
        Assert.state(serviceItemOld.getAuditStatus() != ServiceItem.SALE_STATUS_YES &&
                serviceItemOld.getAuditStatus() != ServiceItem.AUDIT_STATUS_AUDITING, "已上架和审核中不能修改");
        // 折扣价不能大于原价
        Assert.state(serviceItemVO.getDiscountPrice().compareTo(serviceItemVO.getOriginalPrice()) <= 0, "折扣价不能大于原价");
        // 创建新的服务项对象
        ServiceItem serviceItemNew = new ServiceItem();
        // 将旧的服务项属性复制到新的服务项对象中
        BeanUtils.copyBeanProp(serviceItemNew, serviceItemVO);
        // 根据是否套餐，修改审核状态
        if (serviceItemVO.getCarPackage() == ServiceItem.CAR_PACKAGE_YES) {
            // 如果是套餐，设置审核状态为初始化
            serviceItemNew.setAuditStatus(ServiceItem.AUDIT_STATUS_INITIALIZE);
        } else {
            // 如果不是套餐，设置审核状态为无
            serviceItemNew.setAuditStatus(ServiceItem.AUDIT_STATUS_NONE);
        }
        // 更新服务项并返回结果
        int i = serviceItemMapper.updateServiceItem(serviceItemNew);
        System.out.println(i);
        return i;
    }

    /**
     * 批量删除服务项
     *
     * @param ids 需要删除的服务项主键
     * @return 结果
     */
    @Override
    public int deleteServiceItemByIds(Long[] ids) {
        return serviceItemMapper.deleteServiceItemByIds(ids);
    }

    /**
     * 删除服务项信息
     *
     * @param id 服务项主键
     * @return 结果
     */
    @Override
    public int deleteServiceItemById(Long id) {
        return serviceItemMapper.deleteServiceItemById(id);
    }

    @Override
    public int updateSaleStatus(Long id) {
        ServiceItem serviceItem = serviceItemMapper.selectServiceItemById(id);
        Assert.notNull(serviceItem, "参数错误");

        if (
                serviceItem.getSaleStatus() == ServiceItem.SALE_STATUS_NO
                        && serviceItem.getAuditStatus() != ServiceItem.AUDIT_STATUS_PASS
                        && serviceItem.getAuditStatus() != ServiceItem.AUDIT_STATUS_NONE

        ) {
            throw new RuntimeException("未审核通过");
        }
        int saleStatus = ServiceItem.SALE_STATUS_NO;
        //如果当前是已上架，则直接下架
        //如果当前是未上架，且是未审核通过的状态，则不允许上架
        //如果当前是未上架，且是无需审核状态，则直接上架
        if (serviceItem.getSaleStatus() == ServiceItem.SALE_STATUS_NO) {
            saleStatus = ServiceItem.SALE_STATUS_YES;
        }
        return serviceItemMapper.updateSaleStatus(id, saleStatus);
    }

    @Override
    public Map<String, Object> getAuditInfo(Long id) {
        //查询服务项
        ServiceItem serviceItem = serviceItemMapper.selectServiceItemById(id);
        Assert.notNull(serviceItem, "参数错误");
        //查询店长
        List<SysUser> shopManagers = sysUserService.selectUserByRole(Constants.SHOP_MANAGER);
        //从流程模型中获取到限定折扣价
        //查询最新的流程定义信息
        BpmnInfo bpmnInfo = bpmnInfoService.selectLatestBpmnInfo(BpmnInfo.CAR_PACKAGE_AUDIT);
        //查询最新的流程定义
        String processDefinationId=processService.getProcessDefinationId(bpmnInfo);
        BigDecimal limitDiscountPrice = bpmnModelUtils.getLimitDiscountPrice(processDefinationId, bpmnInfo.getProcessDefinitionKey());
        //如果服务项的折扣价大于等于限定价（来自于流程模型）查询财务
        List<SysUser> financials = new ArrayList<>();
        if (serviceItem.getDiscountPrice().compareTo(limitDiscountPrice) >= 0) {
            financials = sysUserService.selectUserByRole(Constants.FINANCIAL);
        }
        //将查询到的数据封装到Map集合中
        HashMap<String, Object> auditInfo = new HashMap<>();
        auditInfo.put("serviceItem", serviceItem);
        auditInfo.put("shopManagers", shopManagers);
        auditInfo.put("financials", financials);
        auditInfo.put("LimitDiscountPrice",limitDiscountPrice);
        return auditInfo;
    }

    @Override
    public void updateAuditStatus(Long id, int auditStatusAuditing) {
        serviceItemMapper.updateAuditStatus(id, auditStatusAuditing);
    }
}
