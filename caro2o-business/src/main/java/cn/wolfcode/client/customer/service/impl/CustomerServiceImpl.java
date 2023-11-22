package cn.wolfcode.client.customer.service.impl;

import java.util.Date;
import java.util.List;

import cn.wolfcode.client.customer.domain.vo.CustomerVo;
import cn.wolfcode.common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.client.customer.mapper.CustomerMapper;
import cn.wolfcode.client.customer.domain.Customer;
import cn.wolfcode.client.customer.service.ICustomerService;
import org.springframework.util.Assert;

/**
 * 客户信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-22
 */
@Service
public class CustomerServiceImpl implements ICustomerService
{
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 查询客户信息
     *
     * @param id 客户信息主键
     * @return 客户信息
     */
    @Override
    public Customer selectCustomerById(String id)
    {
        return customerMapper.selectCustomerById(id);
    }

    /**
     * 查询客户信息列表
     *
     * @param customer 客户信息
     * @return 客户信息
     */
    @Override
    public List<Customer> selectCustomerList(Customer customer)
    {
        return customerMapper.selectCustomerList(customer);
    }

    /**
     * 新增客户信息
     *
     * @param customerVo 客户信息
     * @return 结果
     */
    @Override
    public int insertCustomer(CustomerVo customerVo)
    {
        Assert.notNull(customerVo,"参数错误");

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerVo,customer);
        customer.setInputTime(new Date());
        Long userId = SecurityUtils.getUserId();
        String LoginUsername = customerMapper.selelctLoginUsername(userId);
        customer.setInputUser(LoginUsername);
        return customerMapper.insertCustomer(customer);
    }

    /**
     * 修改客户信息
     *
     * @param customer 客户信息
     * @return 结果
     */
    @Override
    public int updateCustomer(Customer customer)
    {
        return customerMapper.updateCustomer(customer);
    }

    /**
     * 批量删除客户信息
     *
     * @param ids 需要删除的客户信息主键
     * @return 结果
     */
    @Override
    public int deleteCustomerByIds(String[] ids)
    {
        return customerMapper.deleteCustomerByIds(ids);
    }

    /**
     * 删除客户信息信息
     *
     * @param id 客户信息主键
     * @return 结果
     */
    @Override
    public int deleteCustomerById(String id)
    {
        return customerMapper.deleteCustomerById(id);
    }
}
