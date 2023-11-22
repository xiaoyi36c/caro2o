package cn.wolfcode.business.appointment.serviceitem.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.business.appointment.serviceitem.domain.vo.ServiceItemVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.business.appointment.serviceitem.domain.ServiceItem;
import cn.wolfcode.business.appointment.serviceitem.service.IServiceItemService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 服务项Controller
 * 
 * @author li
 * @date 2023-11-03
 */
@RestController
@RequestMapping("/business/serviceitem")
public class ServiceItemController extends BaseController
{
    @Autowired
    private IServiceItemService serviceItemService;

    /**
     * 查询服务项列表
     */
    @PreAuthorize("@ss.hasPermi('business:serviceitem:list')")
    @GetMapping("/list")
    public TableDataInfo list(ServiceItem serviceItem)
    {
        startPage();
        List<ServiceItem> list = serviceItemService.selectServiceItemList(serviceItem);
        return getDataTable(list);
    }

    /**
     * 导出服务项列表
     */
    @PreAuthorize("@ss.hasPermi('business:serviceitem:export')")
    @Log(title = "服务项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ServiceItem serviceItem)
    {
        List<ServiceItem> list = serviceItemService.selectServiceItemList(serviceItem);
        ExcelUtil<ServiceItem> util = new ExcelUtil<ServiceItem>(ServiceItem.class);
        util.exportExcel(response, list, "服务项数据");
    }

    /**
     * 获取服务项详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:serviceitem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(serviceItemService.selectServiceItemById(id));
    }

    /**
     * 新增服务项
     */
    @PreAuthorize("@ss.hasPermi('business:serviceitem:add')")
    @Log(title = "服务项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated ServiceItemVO serviceItemVO)
    {
        return toAjax(serviceItemService.insertServiceItem(serviceItemVO));
    }

    /**
     * 修改服务项
     */
    @PreAuthorize("@ss.hasPermi('business:serviceitem:edit')")
    @Log(title = "服务项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated ServiceItemVO serviceItemVO)
    {
        return toAjax(serviceItemService.updateServiceItem(serviceItemVO));
    }

    /**
     * 删除服务项
     */
    @PreAuthorize("@ss.hasPermi('business:serviceitem:handlesalestatus')")
    @Log(title = "服务项", businessType = BusinessType.DELETE)
	@PutMapping("/{id}")
    public AjaxResult updateSaleStatus(@PathVariable Long id)
    {
        return toAjax(serviceItemService.updateSaleStatus(id));
    }
    /**
     * 查询审核信息
     */
    @PreAuthorize("@ss.hasPermi('business:serviceitem:getAuditInfo')")
    @GetMapping("/auditInfo/{id}")
    public AjaxResult getAuditInfo(@PathVariable("id") Long id)
    {
        return success(serviceItemService.getAuditInfo(id));
    }
}
