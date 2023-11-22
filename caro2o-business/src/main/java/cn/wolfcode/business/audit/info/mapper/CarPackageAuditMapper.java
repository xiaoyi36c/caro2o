package cn.wolfcode.business.audit.info.mapper;

import java.util.List;
import cn.wolfcode.business.audit.info.domain.CarPackageAudit;
import org.apache.ibatis.annotations.Param;

/**
 * 审核信息Mapper接口
 * 
 * @author li
 * @date 2023-11-10
 */
public interface CarPackageAuditMapper 
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
     * @param carPackageAudit 审核信息
     * @return 结果
     */
    public int insertCarPackageAudit(CarPackageAudit carPackageAudit);

    /**
     * 修改审核信息
     * 
     * @param carPackageAudit 审核信息
     * @return 结果
     */
    public int updateCarPackageAudit(CarPackageAudit carPackageAudit);

    /**
     * 删除审核信息
     * 
     * @param id 审核信息主键
     * @return 结果
     */
    public int deleteCarPackageAuditById(Long id);

    /**
     * 批量删除审核信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCarPackageAuditByIds(Long[] ids);

    List<CarPackageAudit> selectCarPackageAuditByIds(List<String> auditIds);

    void updateCarPackageAuditStatus(@Param("id") Long id, @Param("packageAuditStatus") int packageAuditStatus);
}
