package cn.wolfcode.operation.contract.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import cn.wolfcode.operation.contract.domain.Contract;
import cn.wolfcode.operation.contract.mapper.ContractMapper;
import cn.wolfcode.operation.contract.service.IContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 合同信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-22
 */
@Service
public class ContractServiceImpl implements IContractService
{
    @Autowired
    private ContractMapper contractMapper;

    /**
     * 查询合同信息
     * 
     * @param id 合同信息主键
     * @return 合同信息
     */
    @Override
    public Contract selectContractById(String id)
    {
        return contractMapper.selectContractById(id);
    }

    /**
     * 查询合同信息列表
     * 
     * @param contract 合同信息
     * @return 合同信息
     */
    @Override
    public List<Contract> selectContractList(Contract contract)
    {
        return contractMapper.selectContractList(contract);
    }

    /**
     * 新增合同信息
     * 
     * @param contract 合同信息
     * @return 结果
     */
    @Override
    public int insertContract(Contract contract)
    {
        return contractMapper.insertContract(contract);
    }

    /**
     * 修改合同信息
     * 
     * @param contract 合同信息
     * @return 结果
     */
    @Override
    public int updateContract(Contract contract)
    {
        return contractMapper.updateContract(contract);
    }

    /**
     * 批量删除合同信息
     * 
     * @param ids 需要删除的合同信息主键
     * @return 结果
     */
    @Override
    public int deleteContractByIds(String[] ids)
    {
        return contractMapper.deleteContractByIds(ids);
    }

    /**
     * 删除合同信息信息
     * 
     * @param id 合同信息主键
     * @return 结果
     */
    @Override
    public int deleteContractById(String id)
    {
        return contractMapper.deleteContractById(id);
    }

    @Override
    public int putAudit(Long id, String type) {
        if ("1".equals(type)){
            contractMapper.updateApprove(id,Contract.PASS_THE_AUDIT);
        }else {
            contractMapper.updateApprove(id,Contract.FAIL_THE_AUDIT);
        }
        return 1;
    }

    @Override
    public int putContract(Long id) {
        contractMapper.updateCancellation(id,Contract.CANCELLATION);
        return 1;
    }

    /**
     * 下载合同
     * @param id
     * @return InputStream
     */
    @Override
    @Transactional
    public InputStream download(String id) {
        Contract contract = contractMapper.selectContractById(id);
        Assert.notNull(contract,"参数错误");
        // 获取文本内容
        String appendix = contract.getAppendix();
        if (appendix == null){
            appendix = "";
        }
        // 生成txt文件流
        // 通过ByteArrayInputStream创建输入流
        return new ByteArrayInputStream(appendix.getBytes());
    }
}
