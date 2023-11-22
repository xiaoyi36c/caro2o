package cn.wolfcode.client.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.client.mapper.CustomerLinkmaneMapper;
import cn.wolfcode.client.domain.CustomerLinkmane;
import cn.wolfcode.client.service.ICustomerLinkmaneService;

/**
 * 客户联系人Service业务层处理
 * 
 * @author BaiShuai
 * @date 2023-11-22
 */
@Service
public class CustomerLinkmaneServiceImpl implements ICustomerLinkmaneService 
{
    @Autowired
    private CustomerLinkmaneMapper customerLinkmaneMapper;

    /**
     * 查询客户联系人
     * 
     * @param id 客户联系人主键
     * @return 客户联系人
     */
    @Override
    public CustomerLinkmane selectCustomerLinkmaneById(String id)
    {
        return customerLinkmaneMapper.selectCustomerLinkmaneById(id);
    }

    /**
     * 查询客户联系人列表
     * 
     * @param customerLinkmane 客户联系人
     * @return 客户联系人
     */
    @Override
    public List<CustomerLinkmane> selectCustomerLinkmaneList(CustomerLinkmane customerLinkmane)
    {
        return customerLinkmaneMapper.selectCustomerLinkmaneList(customerLinkmane);
    }

    /**
     * 新增客户联系人
     * 
     * @param customerLinkmane 客户联系人
     * @return 结果
     */
    @Override
    public int insertCustomerLinkmane(CustomerLinkmane customerLinkmane)
    {
        return customerLinkmaneMapper.insertCustomerLinkmane(customerLinkmane);
    }

    /**
     * 修改客户联系人
     * 
     * @param customerLinkmane 客户联系人
     * @return 结果
     */
    @Override
    public int updateCustomerLinkmane(CustomerLinkmane customerLinkmane)
    {
        return customerLinkmaneMapper.updateCustomerLinkmane(customerLinkmane);
    }

    /**
     * 批量删除客户联系人
     * 
     * @param ids 需要删除的客户联系人主键
     * @return 结果
     */
    @Override
    public int deleteCustomerLinkmaneByIds(String[] ids)
    {
        return customerLinkmaneMapper.deleteCustomerLinkmaneByIds(ids);
    }

    /**
     * 删除客户联系人信息
     * 
     * @param id 客户联系人主键
     * @return 结果
     */
    @Override
    public int deleteCustomerLinkmaneById(String id)
    {
        return customerLinkmaneMapper.deleteCustomerLinkmaneById(id);
    }
}
