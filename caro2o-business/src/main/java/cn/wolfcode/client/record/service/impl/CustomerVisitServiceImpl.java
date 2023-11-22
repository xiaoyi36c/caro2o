package cn.wolfcode.client.record.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.client.record.mapper.CustomerVisitMapper;
import cn.wolfcode.client.record.domain.CustomerVisit;
import cn.wolfcode.client.record.service.ICustomerVisitService;

/**
 * 拜访信息Service业务层处理
 * 
 * @author li
 * @date 2023-11-22
 */
@Service
public class CustomerVisitServiceImpl implements ICustomerVisitService 
{
    @Autowired
    private CustomerVisitMapper customerVisitMapper;

    /**
     * 查询拜访信息
     * 
     * @param id 拜访信息主键
     * @return 拜访信息
     */
    @Override
    public CustomerVisit selectCustomerVisitById(String id)
    {
        return customerVisitMapper.selectCustomerVisitById(id);
    }

    /**
     * 查询拜访信息列表
     * 
     * @param customerVisit 拜访信息
     * @return 拜访信息
     */
    @Override
    public List<CustomerVisit> selectCustomerVisitList(CustomerVisit customerVisit)
    {
        return customerVisitMapper.selectCustomerVisitList(customerVisit);
    }

    /**
     * 新增拜访信息
     * 
     * @param customerVisit 拜访信息
     * @return 结果
     */
    @Override
    public int insertCustomerVisit(CustomerVisit customerVisit)
    {
        return customerVisitMapper.insertCustomerVisit(customerVisit);
    }

    /**
     * 修改拜访信息
     * 
     * @param customerVisit 拜访信息
     * @return 结果
     */
    @Override
    public int updateCustomerVisit(CustomerVisit customerVisit)
    {
        return customerVisitMapper.updateCustomerVisit(customerVisit);
    }

    /**
     * 批量删除拜访信息
     * 
     * @param ids 需要删除的拜访信息主键
     * @return 结果
     */
    @Override
    public int deleteCustomerVisitByIds(String[] ids)
    {
        return customerVisitMapper.deleteCustomerVisitByIds(ids);
    }

    /**
     * 删除拜访信息信息
     * 
     * @param id 拜访信息主键
     * @return 结果
     */
    @Override
    public int deleteCustomerVisitById(String id)
    {
        return customerVisitMapper.deleteCustomerVisitById(id);
    }
}
