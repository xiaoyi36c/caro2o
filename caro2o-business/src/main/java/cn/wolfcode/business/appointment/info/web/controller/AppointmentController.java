package cn.wolfcode.business.appointment.info.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.business.appointment.info.domain.vo.AppointmentVO;
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
import cn.wolfcode.business.appointment.info.domain.Appointment;
import cn.wolfcode.business.appointment.info.service.IAppointmentService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 养修信息预约Controller
 * 
 * @author li
 * @date 2023-10-31
 */
@RestController
@RequestMapping("/business/appointment")
public class AppointmentController extends BaseController
{
    @Autowired
    private IAppointmentService appointmentService;

    /**
     * 查询养修信息预约列表
     */
    @PreAuthorize("@ss.hasPermi('business:appointment:list')")
    @GetMapping("/list")
    public TableDataInfo list(Appointment appointment)
    {
        startPage();
        List<Appointment> list = appointmentService.selectAppointmentList(appointment);
        return getDataTable(list);
    }

    /**
     * 导出养修信息预约列表
     */
    @PreAuthorize("@ss.hasPermi('business:appointment:export')")
    @Log(title = "养修信息预约", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Appointment appointment)
    {
        List<Appointment> list = appointmentService.selectAppointmentList(appointment);
        ExcelUtil<Appointment> util = new ExcelUtil<Appointment>(Appointment.class);
        util.exportExcel(response, list, "养修信息预约数据");
    }

    /**
     * 获取养修信息预约详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:appointment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(appointmentService.selectAppointmentById(id));
    }

    /**
     * 新增养修信息预约
     */
    @PreAuthorize("@ss.hasPermi('business:appointment:add')")
    @Log(title = "养修信息预约", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated AppointmentVO appointmentVO)
    {
        return toAjax(appointmentService.insertAppointment(appointmentVO));
    }

    /**
     * 修改养修信息预约
     */
    @PreAuthorize("@ss.hasPermi('business:appointment:edit')")
    @Log(title = "养修信息预约", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated AppointmentVO appointmentVO)
    {
        return toAjax(appointmentService.updateAppointment(appointmentVO));
    }

    /**
     * 修改到店状态
     */
    @PreAuthorize("@ss.hasPermi('business:appointment:arrival')")
    @Log(title = "养修信息预约", businessType = BusinessType.UPDATE)
	@PutMapping("/arrival/{id}")
    public AjaxResult arrival(@PathVariable Long id)
    {
        return toAjax(appointmentService.updateArrival(id));
    }

    /**
     * 修改取消状态
     */
    @PreAuthorize("@ss.hasPermi('business:appointment:cancel')")
    @Log(title = "养修信息预约", businessType = BusinessType.UPDATE)
    @PutMapping("/cancel/{id}")
    public AjaxResult cancel(@PathVariable Long id)
    {
        return toAjax(appointmentService.updateCancel(id));
    }
}
