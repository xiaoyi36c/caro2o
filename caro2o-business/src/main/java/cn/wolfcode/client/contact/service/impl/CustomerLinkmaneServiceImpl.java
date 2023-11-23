package cn.wolfcode.client.contact.service.impl;

import java.beans.Transient;
import java.util.Date;
import java.util.List;

import cn.wolfcode.client.contact.domain.vo.UpdateCustomerLinkmane;
import cn.wolfcode.common.utils.SecurityUtils;
import cn.wolfcode.common.utils.ValidateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.client.contact.mapper.CustomerLinkmaneMapper;
import cn.wolfcode.client.contact.domain.CustomerLinkmane;
import cn.wolfcode.client.contact.service.ICustomerLinkmaneService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
    public CustomerLinkmane selectCustomerLinkmaneById(Long id)
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
     * @param updateCustomerLinkmane 新增客户联系人Vo
     * @return 结果
     */
    @Override
    @Transactional
    public int insertCustomerLinkmane(UpdateCustomerLinkmane updateCustomerLinkmane)
    {
        // 1.参数校验
        Assert.notNull(updateCustomerLinkmane,"客户联系人不能为空");
        if (updateCustomerLinkmane.getId() != null) {
            throw new IllegalArgumentException("参数错误");
        }
        // 2. 格式校验
        Assert.state(ValidateUtils.validPhone(updateCustomerLinkmane.getPhone()),"手机号格式不正确");
        Assert.state(updateCustomerLinkmane.getLinkman().length()<30 && updateCustomerLinkmane.getLinkman().length()>0,"名字太长");
        Assert.state(updateCustomerLinkmane.getDepartment().length()<20 && updateCustomerLinkmane.getDepartment().length()>0,"部门名字太长");
        Assert.state(updateCustomerLinkmane.getPosition().length()<20 && updateCustomerLinkmane.getPosition().length()>0,"职位名字太长");
        Assert.state(updateCustomerLinkmane.getAge() > 0 && updateCustomerLinkmane.getAge() < 100,"年龄不合法");


        CustomerLinkmane customerLinkmane = new CustomerLinkmane();
        // 3. 参数拷贝
        BeanUtils.copyProperties(updateCustomerLinkmane,customerLinkmane);

        // 设置录入人
        customerLinkmane.setInputUser(SecurityUtils.getUsername());
        // 设置录入时间
        customerLinkmane.setInputTime(new Date());
        return customerLinkmaneMapper.insertCustomerLinkmane(customerLinkmane);
    }

    /**
     * 修改客户联系人
     *
     * @param updateCustomerLinkmane 客户联系人
     * @return 结果
     */
    @Override
    @Transactional
    public int updateCustomerLinkmane(UpdateCustomerLinkmane updateCustomerLinkmane)
    {
        CustomerLinkmane customerLinkmane = customerLinkmaneMapper.selectCustomerLinkmaneById(updateCustomerLinkmane.getId());
        Assert.notNull(customerLinkmane,"参数错误");
        // 2. 格式校验
        Assert.state(ValidateUtils.validPhone(updateCustomerLinkmane.getPhone()),"手机号格式不正确");
        Assert.state(updateCustomerLinkmane.getLinkman().length()<30 && updateCustomerLinkmane.getLinkman().length()>0,"名字太长");
        Assert.state(updateCustomerLinkmane.getDepartment().length()<20 && updateCustomerLinkmane.getDepartment().length()>0,"部门名字太长");
        Assert.state(updateCustomerLinkmane.getPosition().length()<20 && updateCustomerLinkmane.getPosition().length()>0,"职位名字太长");
        Assert.state(updateCustomerLinkmane.getAge() > 0 && updateCustomerLinkmane.getAge() < 100,"年龄不合法");

        // 3. 参数拷贝
        CustomerLinkmane cus = new CustomerLinkmane();
        BeanUtils.copyProperties(updateCustomerLinkmane,cus);
        cus.setInputTime(customerLinkmane.getInputTime());
        cus.setInputUser(customerLinkmane.getInputUser());
        return customerLinkmaneMapper.updateCustomerLinkmane(cus);
    }

    /**
     * 批量删除客户联系人
     * 
     * @param ids 需要删除的客户联系人主键
     * @return 结果
     */
    @Override
    public int deleteCustomerLinkmaneByIds(Long[] ids)
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
    public int deleteCustomerLinkmaneById(Long id)
    {
        return customerLinkmaneMapper.deleteCustomerLinkmaneById(id);
    }
}
