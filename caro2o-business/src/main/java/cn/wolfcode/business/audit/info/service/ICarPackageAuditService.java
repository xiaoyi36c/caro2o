package cn.wolfcode.business.audit.info.service;

import java.io.InputStream;
import java.util.List;
import cn.wolfcode.business.audit.info.domain.CarPackageAudit;
import cn.wolfcode.business.audit.info.domain.vo.AuditHistoryVO;
import cn.wolfcode.business.audit.info.domain.vo.AuditVO;
import cn.wolfcode.business.audit.info.domain.vo.CarPackageAuditVO;

/**
 * 审核信息Service接口
 * 
 * @author li
 * @date 2023-11-10
 */
public interface ICarPackageAuditService 
{
    /**
     * 查询审核信息
     * 
     * @param id 审核信息主键
     * @return 审核信息
     */
    public CarPackageAudit selectCarPackageAuditById(Long id);

    /**
     * 查询审核信息列表
     * 
     * @param carPackageAudit 审核信息
     * @return 审核信息集合
     */
    public List<CarPackageAudit> selectCarPackageAuditList(CarPackageAudit carPackageAudit);

    /**
     * 新增审核信息
     * 
     * @param carPackageAuditVO 审核信息
     * @return 结果
     */
    public int insertCarPackageAudit(CarPackageAuditVO carPackageAuditVO);

    /**
     * 修改审核信息
     * 
     * @param carPackageAudit 审核信息
     * @return 结果
     */
    public int updateCarPackageAudit(CarPackageAudit carPackageAudit);

    /**
     * 批量删除审核信息
     * 
     * @param ids 需要删除的审核信息主键集合
     * @return 结果
     */
    public int deleteCarPackageAuditByIds(Long[] ids);

    /**
     * 删除审核信息信息
     * 
     * @param id 审核信息主键
     * @return 结果
     */
    public void deleteCarPackageAuditById(Long id);

    List<CarPackageAudit> todoList();

    void audit(AuditVO auditVO);

    List<AuditHistoryVO> selectAuditHistory(Long instanceId);

    InputStream getProgress(Long instanceId);

    List<CarPackageAudit> doneList();
}
