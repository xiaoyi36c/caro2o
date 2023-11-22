package cn.wolfcode.business.appointment.info.service.impl;

import java.util.Date;
import java.util.List;

import cn.wolfcode.business.appointment.info.domain.vo.AppointmentVO;
import cn.wolfcode.business.appointment.info.enums.AppointmentStatusEnum;
import cn.wolfcode.common.core.redis.RedisCache;
import cn.wolfcode.common.utils.DateUtils;
import cn.wolfcode.common.utils.ValidateUtils;
import cn.wolfcode.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.appointment.info.mapper.AppointmentMapper;
import cn.wolfcode.business.appointment.info.domain.Appointment;
import cn.wolfcode.business.appointment.info.service.IAppointmentService;
import org.springframework.util.Assert;

/**
 * 养修信息预约Service业务层处理
 *
 * @author li
 * @date 2023-10-31
 */
@Service
public class AppointmentServiceImpl implements IAppointmentService {
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询养修信息预约
     *
     * @param id 养修信息预约主键
     * @return 养修信息预约
     */
    @Override
    public Appointment selectAppointmentById(Long id) {
        return appointmentMapper.selectAppointmentById(id);
    }

    /**
     * 查询养修信息预约列表
     *
     * @param appointment 养修信息预约
     * @return 养修信息预约
     */
    @Override
    public List<Appointment> selectAppointmentList(Appointment appointment) {
        return appointmentMapper.selectAppointmentList(appointment);
    }

    /**
     * 新增养修信息预约
     *
     * @param appointmentVO 养修信息预约
     * @return 结果
     */
    @Override
    public int insertAppointment(AppointmentVO appointmentVO) {
        //数据校验
        //非空校验1.可以使用Assert中的方法完成2.JSR303的数据校验
        //Assert.notNull(appointmentVO.getCustomerName(),"客户姓名不能为空");

        //格式校验
        chrvkDate(appointmentVO);
        //同一个手机号码一天最多下单5次
        //获取用户当前下单的次数：
        //1.直接从数据库中根据phone和creatTime查询
        //int appointmentCount = appointmentMapper.selectAppointmentCount(appointmentVO.getCustomerPhone());
        //2.用户每次下单，都将其下单的次数存储到redis中
        redisCache.increment("appointment:count:"+appointmentVO.getCustomerPhone(),DateUtils.timeDistanceMilSeconds());
        int appointmentCount = redisCache.getCacheObject("appointment:count:" + appointmentVO.getCustomerPhone());
        //如何设置下单次数的上限
        //在参数设置中添加下单次数上限的设置，用户可以直接修改
        String count = sysConfigService.selectConfigByKey("bus_appointment_count");
        if(count != null){
            Assert.state(appointmentCount <= Integer.parseInt(count),"同一手机号码最多下单 "+count+" 次");
        }

        appointmentVO.setCreateTime(DateUtils.getNowDate());
        return appointmentMapper.insertAppointment(appointmentVO);
    }

    private static void chrvkDate(AppointmentVO appointmentVO) {
        //联系方式
        Assert.state(ValidateUtils.validPhone(appointmentVO.getCustomerPhone()), "联系方式格式不正确");
        //车牌号码
        Assert.state(ValidateUtils.validLicensePlate(appointmentVO.getLicensePlate()), "车牌号码格式不正确");
        //预约时间不能小于当前时间
        Assert.state(appointmentVO.getAppointmentTime().getTime() > System.currentTimeMillis(), "预约时间不能小于当前时间");
    }

    /**
     * 修改养修信息预约
     *
     * @param appointmentVO 养修信息预约
     * @return 结果
     */
    @Override
    public int updateAppointment(AppointmentVO appointmentVO) {
        //判断预约状态是不是预约中
        checkStatus(appointmentVO.getId(), "当前状态无法修改");
        //数据格式校验
        chrvkDate(appointmentVO);
        return appointmentMapper.updateAppointment(appointmentVO);
    }

    /**
     * 批量删除养修信息预约
     *
     * @param ids 需要删除的养修信息预约主键
     * @return 结果
     */
    @Override
    public int deleteAppointmentByIds(Long[] ids) {
        return appointmentMapper.deleteAppointmentByIds(ids);
    }

    /**
     * 删除养修信息预约信息
     *
     * @param id 养修信息预约主键
     * @return 结果
     */
    @Override
    public int deleteAppointmentById(Long id) {
        return appointmentMapper.deleteAppointmentById(id);
    }

    @Override
    public int updateArrival(Long id) {
        checkStatus(id, "当前状态不能设置到店");
        //修改状态和到店时间
        return appointmentMapper.updateArrival(id);
    }

    @Override
    public int updateCancel(Long id) {
        //状态的检查
        checkStatus(id, "当前状态不能设置到店");
        //修改状态
        return appointmentMapper.updateStatus(id,AppointmentStatusEnum.USER_CANCLE.ordinal());
    }

    @Override
    public int updateStatus(Long appointmentId, int status) {
        return appointmentMapper.updateStatus(appointmentId,status);
    }

    private void checkStatus(Long id, String msg) {
        //状态的检查
        Appointment appointment = appointmentMapper.selectAppointmentById(id);
        Assert.notNull(appointment, "参数错误");
        Assert.state(appointment.getStatus() == AppointmentStatusEnum.APPOINTING.ordinal(), msg);
    }
}
