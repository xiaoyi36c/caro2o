package cn.wolfcode.business.flow.info.service;

import java.io.InputStream;
import java.util.List;
import cn.wolfcode.business.flow.info.domain.BpmnInfo;
import cn.wolfcode.business.flow.info.domain.vo.BpmnInfoVO;

/**
 * 流程定义明细Service接口
 * 
 * @author li
 * @date 2023-11-09
 */
public interface IBpmnInfoService 
{
    /**
     * 查询流程定义明细
     * 
     * @param id 流程定义明细主键
     * @return 流程定义明细
     */
    public BpmnInfo selectBpmnInfoById(Long id);

    /**
     * 查询流程定义明细列表
     * 
     * @param bpmnInfo 流程定义明细
     * @return 流程定义明细集合
     */
    public List<BpmnInfo> selectBpmnInfoList(BpmnInfo bpmnInfo);

    /**
     * 新增流程定义明细
     * 
     * @param bpmnInfo 流程定义明细
     * @return 结果
     */
    public int insertBpmnInfo(BpmnInfo bpmnInfo);

    /**
     * 修改流程定义明细
     * 
     * @param bpmnInfo 流程定义明细
     * @return 结果
     */
    public int updateBpmnInfo(BpmnInfo bpmnInfo);

    /**
     * 批量删除流程定义明细
     * 
     * @param ids 需要删除的流程定义明细主键集合
     * @return 结果
     */
    public int deleteBpmnInfoByIds(Long[] ids);

    /**
     * 删除流程定义明细信息
     * 
     * @param id 流程定义明细主键
     * @return 结果
     */
    public int deleteBpmnInfoById(Long id);
    /**
     * 流程定义部署
     */
    int deploy(BpmnInfoVO bpmnInfoVO);

    InputStream getResource(Long id,String  type);

    InputStream getResourceSVG(Long id);

    BpmnInfo selectLatestBpmnInfo(Integer bpmnType);
}
