package cn.wolfcode.business.appointment.serviceitem.service;

import java.util.List;
import java.util.Map;

import cn.wolfcode.business.appointment.serviceitem.domain.ServiceItem;
import cn.wolfcode.business.appointment.serviceitem.domain.vo.ServiceItemVO;

/**
 * 服务项Service接口
 *
 * @author li
 * @date 2023-11-03
 */
public interface IServiceItemService {
    /**
     * 查询服务项
     *
     * @param id 服务项主键
     * @return 服务项
     */
    public ServiceItem selectServiceItemById(Long id);

    /**
     * 查询服务项列表
     *
     * @param serviceItem 服务项
     * @return 服务项集合
     */
    public List<ServiceItem> selectServiceItemList(ServiceItem serviceItem);

    /**
     * 新增服务项
     *
     * @param serviceItemVO 服务项
     * @return 结果
     */
    public int insertServiceItem(ServiceItemVO serviceItemVO);

    /**
     * 修改服务项
     *
     * @param serviceItemVO 服务项
     * @return 结果
     */
    public int updateServiceItem(ServiceItemVO serviceItemVO);

    /**
     * 批量删除服务项
     *
     * @param ids 需要删除的服务项主键集合
     * @return 结果
     */
    public int deleteServiceItemByIds(Long[] ids);

    /**
     * 删除服务项信息
     *
     * @param id 服务项主键
     * @return 结果
     */
    public int deleteServiceItemById(Long id);

    int updateSaleStatus(Long id);

    /**
     * 获取审核信忘
     *
     * @param id 服务项d
     * @return 审核信息
     */
    Map<String, Object> getAuditInfo(Long id);

    void updateAuditStatus(Long id, int auditStatusAuditing);
}
