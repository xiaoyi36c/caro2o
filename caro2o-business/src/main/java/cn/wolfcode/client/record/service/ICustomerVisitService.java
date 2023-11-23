package cn.wolfcode.client.record.service;

import java.util.List;
import cn.wolfcode.client.record.domain.CustomerVisit;
import cn.wolfcode.client.record.domain.vo.ClientAndLinkman;
import cn.wolfcode.client.record.domain.vo.CustomerVisitVo;

/**
 * 拜访信息Service接口
 * 
 * @author li
 * @date 2023-11-22
 */
public interface ICustomerVisitService 
{
    /**
     * 查询拜访信息
     * 
     * @param id 拜访信息主键
     * @return 拜访信息
     */
    public CustomerVisit selectCustomerVisitById(Long id);

    /**
     * 查询拜访信息列表
     * 
     * @param customerVisit 拜访信息
     * @return 拜访信息集合
     */
    public List<CustomerVisit> selectCustomerVisitList(CustomerVisit customerVisit);

    /**
     * 新增拜访信息
     * 
     * @param customerVisitVo 拜访信息
     * @return 结果
     */
    public int insertCustomerVisit(CustomerVisitVo customerVisitVo);

    /**
     * 修改拜访信息
     * 
     * @param customerVisitVo 拜访信息
     * @return 结果
     */
    public int updateCustomerVisit(CustomerVisitVo customerVisitVo);

    /**
     * 批量删除拜访信息
     * 
     * @param ids 需要删除的拜访信息主键集合
     * @return 结果
     */
    public int deleteCustomerVisitByIds(Long[] ids);

    /**
     * 删除拜访信息信息
     * 
     * @param id 拜访信息主键
     * @return 结果
     */
    public int deleteCustomerVisitById(Long id);

    ClientAndLinkman selectClientLinkman();

    List<CustomerVisit> selectCustomerrecById(Long id);
}
