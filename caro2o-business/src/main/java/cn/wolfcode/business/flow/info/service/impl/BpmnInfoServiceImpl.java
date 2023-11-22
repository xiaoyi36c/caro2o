package cn.wolfcode.business.flow.info.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import cn.wolfcode.business.flow.info.domain.vo.BpmnInfoVO;
import cn.wolfcode.business.flow.info.service.IProcessService;
import cn.wolfcode.common.utils.file.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.flow.info.mapper.BpmnInfoMapper;
import cn.wolfcode.business.flow.info.domain.BpmnInfo;
import cn.wolfcode.business.flow.info.service.IBpmnInfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 流程定义明细Service业务层处理
 *
 * @author li
 * @date 2023-11-09
 */
@Service
public class BpmnInfoServiceImpl implements IBpmnInfoService {
    @Autowired
    private BpmnInfoMapper bpmnInfoMapper;
    @Autowired
    private IProcessService processService;

    /**
     * 查询流程定义明细
     *
     * @param id 流程定义明细主键
     * @return 流程定义明细
     */
    @Override
    public BpmnInfo selectBpmnInfoById(Long id) {
        return bpmnInfoMapper.selectBpmnInfoById(id);
    }

    /**
     * 查询流程定义明细列表
     *
     * @param bpmnInfo 流程定义明细
     * @return 流程定义明细
     */
    @Override
    public List<BpmnInfo> selectBpmnInfoList(BpmnInfo bpmnInfo) {
        return bpmnInfoMapper.selectBpmnInfoList(bpmnInfo);
    }

    /**
     * 新增流程定义明细
     *
     * @param bpmnInfo 流程定义明细
     * @return 结果
     */
    @Override
    public int insertBpmnInfo(BpmnInfo bpmnInfo) {
        return bpmnInfoMapper.insertBpmnInfo(bpmnInfo);
    }

    /**
     * 修改流程定义明细
     *
     * @param bpmnInfo 流程定义明细
     * @return 结果
     */
    @Override
    public int updateBpmnInfo(BpmnInfo bpmnInfo) {
        return bpmnInfoMapper.updateBpmnInfo(bpmnInfo);
    }

    /**
     * 批量删除流程定义明细
     *
     * @param ids 需要删除的流程定义明细主键
     * @return 结果
     */
    @Override
    public int deleteBpmnInfoByIds(Long[] ids) {
        return bpmnInfoMapper.deleteBpmnInfoByIds(ids);
    }

    /**
     * 删除流程定义明细信息
     *
     * @param id 流程定义明细主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteBpmnInfoById(Long id) {
        //查询流程定义信息
        BpmnInfo bpmnInfo = bpmnInfoMapper.selectBpmnInfoById(id);
        Assert.notNull(bpmnInfo, "参数错误");
        //撤销流程定义
        processService.removeProcessDefinition(bpmnInfo.getProcessDefinitionKey(),bpmnInfo.getVersion());
        //删除流程定义
        return bpmnInfoMapper.deleteBpmnInfoById(id);
    }

    /**
     * 流程定义部署
     */
    @Override
    public int deploy(BpmnInfoVO bpmnInfoVO) {
        //校验文件类型
//        String[] types = {".xml", ".bpmn", ".zip", ".rar"};
//        int test = 0;
//        for (String type : types) {
//            if (bpmnInfoVO.getFileName().endsWith(type)){
//                test = 1;
//            }
//        }
//        Assert.state(test == 1, "文件类型错误");
        String fileName = bpmnInfoVO.getFileName();
        List<String> allowExts = Arrays.asList("zip", "xml", "bpmn");
        Assert.state(allowExts.contains(FileUtils.getSuffix(fileName)), "文件类型错误");
        // 部署流程定义
        BpmnInfo bpmnInfo = processService.deploy(bpmnInfoVO);
        // 将数据从activiti中取出封装到BpmnInfo中,保存到数据库
        return bpmnInfoMapper.insertBpmnInfo(bpmnInfo);
    }

    @Override
    public InputStream getResource(Long id ,String type){
        BpmnInfo bpmnInfo = bpmnInfoMapper.selectBpmnInfoById(id);
        Assert.notNull(bpmnInfo,"参数错误");
        return processService.getResource(bpmnInfo.getProcessDefinitionKey(),bpmnInfo.getVersion(),type);
    }

    @Override
    public InputStream getResourceSVG(Long id) {
        BpmnInfo bpmnInfo = bpmnInfoMapper.selectBpmnInfoById(id);
        Assert.notNull(bpmnInfo,"参数错误");
        return processService.getResourceSVG(bpmnInfo.getProcessDefinitionKey(),bpmnInfo.getVersion());
    }

    @Override
    public BpmnInfo selectLatestBpmnInfo(Integer bpmnType) {
        return bpmnInfoMapper.selectLatestBpmnInfo(bpmnType);
    }
}
