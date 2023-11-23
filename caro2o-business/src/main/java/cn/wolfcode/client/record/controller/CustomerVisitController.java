package cn.wolfcode.client.record.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.client.record.domain.vo.CustomerVisitVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import cn.wolfcode.client.record.domain.CustomerVisit;
import cn.wolfcode.client.record.service.ICustomerVisitService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 拜访信息Controller
 * 
 * @author li
 * @date 2023-11-22
 */
@RestController
@RequestMapping("/client/record")
public class CustomerVisitController extends BaseController
{
    @Autowired
    private ICustomerVisitService customerVisitService;

    /**
     * 查询拜访信息列表
     */
    @PreAuthorize("@ss.hasPermi('client:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(CustomerVisit customerVisit)
    {
        startPage();
        List<CustomerVisit> list = customerVisitService.selectCustomerVisitList(customerVisit);
        return getDataTable(list);
    }

    /**
     * 导出拜访信息列表
     */
    @PreAuthorize("@ss.hasPermi('client:record:export')")
    @Log(title = "拜访信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CustomerVisit customerVisit)
    {
        List<CustomerVisit> list = customerVisitService.selectCustomerVisitList(customerVisit);
        ExcelUtil<CustomerVisit> util = new ExcelUtil<CustomerVisit>(CustomerVisit.class);
        util.exportExcel(response, list, "拜访信息数据");
    }

    /**
     * 获取拜访信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('client:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(customerVisitService.selectCustomerVisitById(id));
    }
    /**
     * 获取拜访信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('client:record:query')")
    @GetMapping(value = "/rec/{id}")
    public AjaxResult getRec(@PathVariable("id") Long id)
    {
        return success(customerVisitService.selectCustomerrecById(id));
    }


    /**
     * 查询客户和联系人
     */
    @PreAuthorize("@ss.hasPermi('client:record:query')")
    @GetMapping("/ClientLinkman")
    public AjaxResult getClientLinkman()
    {
        return success(customerVisitService.selectClientLinkman());
    }

    /**
     * 新增拜访信息
     */
    @PreAuthorize("@ss.hasPermi('client:record:add')")
    @Log(title = "拜访信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated CustomerVisitVo customerVisitVo)
    {
        return toAjax(customerVisitService.insertCustomerVisit(customerVisitVo));
    }

    /**
     * 修改拜访信息
     */
    @PreAuthorize("@ss.hasPermi('client:record:edit')")
    @Log(title = "拜访信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated CustomerVisitVo customerVisitVo)
    {
        return toAjax(customerVisitService.updateCustomerVisit(customerVisitVo));
    }

    /**
     * 删除拜访信息
     */
    @PreAuthorize("@ss.hasPermi('client:record:remove')")
    @Log(title = "拜访信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(customerVisitService.deleteCustomerVisitByIds(ids));
    }
}
