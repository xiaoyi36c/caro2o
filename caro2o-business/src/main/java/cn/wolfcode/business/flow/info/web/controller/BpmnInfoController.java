package cn.wolfcode.business.flow.info.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.business.flow.info.domain.vo.BpmnInfoVO;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.business.flow.info.domain.BpmnInfo;
import cn.wolfcode.business.flow.info.service.IBpmnInfoService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 流程定义明细Controller
 * 
 * @author li
 * @date 2023-11-09
 */
@RestController
@RequestMapping("/flow/info")
public class BpmnInfoController extends BaseController
{
    @Autowired
    private IBpmnInfoService bpmnInfoService;

    /**
     * 查询流程定义明细列表
     */
    @PreAuthorize("@ss.hasPermi('flow:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(BpmnInfo bpmnInfo)
    {
        startPage();
        List<BpmnInfo> list = bpmnInfoService.selectBpmnInfoList(bpmnInfo);
        return getDataTable(list);
    }

    /**
     * 导出流程定义明细列表
     */
    @PreAuthorize("@ss.hasPermi('flow:info:export')")
    @Log(title = "流程定义明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BpmnInfo bpmnInfo)
    {
        List<BpmnInfo> list = bpmnInfoService.selectBpmnInfoList(bpmnInfo);
        ExcelUtil<BpmnInfo> util = new ExcelUtil<BpmnInfo>(BpmnInfo.class);
        util.exportExcel(response, list, "流程定义明细数据");
    }

    /**
     * 获取流程定义明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('flow:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bpmnInfoService.selectBpmnInfoById(id));
    }

    /**
     * 新增流程定义明细
     */
    @PreAuthorize("@ss.hasPermi('flow:info:add')")
    @Log(title = "流程定义明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BpmnInfo bpmnInfo)
    {
        return toAjax(bpmnInfoService.insertBpmnInfo(bpmnInfo));
    }

    /**
     * 修改流程定义明细
     */
    @PreAuthorize("@ss.hasPermi('flow:info:edit')")
    @Log(title = "流程定义明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BpmnInfo bpmnInfo)
    {
        return toAjax(bpmnInfoService.updateBpmnInfo(bpmnInfo));
    }

    /**
     * 删除流程定义明细
     */
    @PreAuthorize("@ss.hasPermi('flow:info:remove')")
    @Log(title = "流程定义明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(bpmnInfoService.deleteBpmnInfoById(id));
    }

    /**
     * 流程定义部署
     */
    @Log(title = "流程定义部署", businessType = BusinessType.INSERT)
    @PutMapping("/deploy")
    public AjaxResult deploy(@Validated BpmnInfoVO bpmnInfoVO)
    {
        return toAjax(bpmnInfoService.deploy(bpmnInfoVO));
    }

    /**
     * 流程定义文件查看
     */
    @Log(title = "流程定义")
    @GetMapping("/resources/{id}/{type}")
    public void getResource(@PathVariable("id") Long id, HttpServletResponse response,@PathVariable("type") String type)
    {
        // 将查询到的文件数据写出到响应流中
        try (InputStream is = bpmnInfoService.getResource(id,type)){
            Assert.notNull(is,"当前文件不存在");
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }/**
     * 流程定义文件查看(svg)
     */
    @Log(title = "流程定义")
    @GetMapping("/getResourceDiagramSVG/{id}")
    public void getResourceDiagramSVG(@PathVariable("id") Long id, HttpServletResponse response)
    {
        // 将查询到的文件数据写出到响应流中
        try (InputStream is = bpmnInfoService.getResourceSVG(id)){
            Assert.notNull(is,"动态生成图片出错");
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
