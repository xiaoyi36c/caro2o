package cn.wolfcode.business.audit.info.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.business.audit.info.domain.vo.AuditVO;
import cn.wolfcode.business.audit.info.domain.vo.CarPackageAuditVO;
import org.apache.poi.util.IOUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.business.audit.info.domain.CarPackageAudit;
import cn.wolfcode.business.audit.info.service.ICarPackageAuditService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 审核信息Controller
 * 
 * @author li
 * @date 2023-11-10
 */
@RestController
@RequestMapping("/audit/info")
public class CarPackageAuditController extends BaseController
{
    @Autowired
    private ICarPackageAuditService carPackageAuditService;

    /**
     * 查询审核信息列表
     */
    //@PreAuthorize("@ss.hasPermi('audit:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(CarPackageAudit carPackageAudit)
    {
        startPage();
        List<CarPackageAudit> list = carPackageAuditService.selectCarPackageAuditList(carPackageAudit);
        return getDataTable(list);
    }

    /**
     * 导出审核信息列表
     */
    @PreAuthorize("@ss.hasPermi('audit:info:export')")
    @Log(title = "审核信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CarPackageAudit carPackageAudit)
    {
        List<CarPackageAudit> list = carPackageAuditService.selectCarPackageAuditList(carPackageAudit);
        ExcelUtil<CarPackageAudit> util = new ExcelUtil<CarPackageAudit>(CarPackageAudit.class);
        util.exportExcel(response, list, "审核信息数据");
    }

    /**
     * 获取审核信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('audit:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(carPackageAuditService.selectCarPackageAuditById(id));
    }

    /**
     * 新增审核信息
     */
    @PreAuthorize("@ss.hasPermi('audit:info:add')")
    @Log(title = "审核信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CarPackageAuditVO carPackageAuditVO)
    {
        return toAjax(carPackageAuditService.insertCarPackageAudit(carPackageAuditVO));
    }

    /**
     * 修改审核信息
     */
    @PreAuthorize("@ss.hasPermi('audit:info:edit')")
    @Log(title = "审核信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CarPackageAudit carPackageAudit)
    {
        return toAjax(carPackageAuditService.updateCarPackageAudit(carPackageAudit));
    }

    /**
     * 撤销审核信息
     */
    @PreAuthorize("@ss.hasPermi('audit:info:remove')")
    @Log(title = "审核信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        carPackageAuditService.deleteCarPackageAuditById(id);
        return success();
    }
    /**
     * 查询我的已办
     */
    @PreAuthorize("@ss.hasPermi('audit:info:todo')")
    @GetMapping("/doneList")
    public TableDataInfo doneList()
    {
        List<CarPackageAudit> list = carPackageAuditService.doneList();
        return getDataTable(list);
    }
    /**
     * 查询我的代办
     */
    @PreAuthorize("@ss.hasPermi('audit:info:todo')")
    @GetMapping("/todoList")
    public TableDataInfo todoList()
    {
        List<CarPackageAudit> list = carPackageAuditService.todoList();
        return getDataTable(list);
    }
    /**
     * 审批
     */
    @Log(title = "审核信息", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@Validated @RequestBody AuditVO auditVO)
    {
        carPackageAuditService.audit(auditVO);
        return success();
    }
    /**
     * 获取审核历史
     */
    @PreAuthorize("@ss.hasPermi('audit:info:edit')")
    @GetMapping(value = "/auditHistory/{id}")
    public AjaxResult getHistory(@PathVariable("id") Long instanceId)
    {
        return success(carPackageAuditService.selectAuditHistory(instanceId));
    }
    /**
     * 获取进度
     */
    @PreAuthorize("@ss.hasPermi('audit:info:edit')")
    @GetMapping(value = "/getProgress/{id}")
    public void getProgress(@PathVariable("id") Long instanceId,HttpServletResponse response)
    {
        // 将查询到的文件数据写出到响应流中
        try (InputStream is = carPackageAuditService.getProgress(instanceId)){
            Assert.notNull(is,"当前文件不存在");
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
