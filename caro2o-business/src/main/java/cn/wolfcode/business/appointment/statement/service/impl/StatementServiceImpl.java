package cn.wolfcode.business.appointment.statement.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import cn.wolfcode.business.appointment.info.domain.Appointment;
import cn.wolfcode.business.appointment.info.enums.AppointmentStatusEnum;
import cn.wolfcode.business.appointment.info.service.IAppointmentService;
import cn.wolfcode.business.appointment.serviceitem.domain.ServiceItem;
import cn.wolfcode.business.appointment.serviceitem.service.IServiceItemService;
import cn.wolfcode.business.appointment.statement.domain.StatementItem;
import cn.wolfcode.business.appointment.statement.domain.vo.StatementItemVO;
import cn.wolfcode.business.appointment.statement.domain.vo.StatementVO;
import cn.wolfcode.business.appointment.statement.mapper.StatementItemMapper;
import cn.wolfcode.common.utils.DateUtils;
import cn.wolfcode.common.utils.SecurityUtils;
import cn.wolfcode.common.utils.ValidateUtils;
import cn.wolfcode.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.appointment.statement.mapper.StatementMapper;
import cn.wolfcode.business.appointment.statement.domain.Statement;
import cn.wolfcode.business.appointment.statement.service.IStatementService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 结算单Service业务层处理
 *
 * @author li
 * @date 2023-11-04
 */
@Service
public class StatementServiceImpl implements IStatementService {
    @Autowired
    private StatementMapper statementMapper;
    @Autowired
    private IServiceItemService serviceItemService;
    @Autowired
    private StatementItemMapper statementItemMapper;
    @Autowired
    private IAppointmentService appointmentService;

    /**
     * 查询结算单
     *
     * @param id 结算单主键
     * @return 结算单
     */
    @Override
    public Statement selectStatementById(Long id) {
        return statementMapper.selectStatementById(id);
    }

    /**
     * 查询结算单列表
     *
     * @param statement 结算单
     * @return 结算单
     */
    @Override
    public List<Statement> selectStatementList(Statement statement) {
        return statementMapper.selectStatementList(statement);
    }

    /**
     * 新增结算单
     *
     * @param statementVO 结算单
     * @return 结果
     */
    @Override
    public int insertStatement(StatementVO statementVO) {
        Assert.state(ValidateUtils.validPhone(statementVO.getCustomerPhone()), "联系方式格式不正确");
        Assert.state(ValidateUtils.validLicensePlate(statementVO.getLicensePlate()), "车牌号码格式不正确");

        Statement statement = new Statement();
        BeanUtils.copyBeanProp(statement, statementVO);
        statement.setCreateTime(DateUtils.getNowDate());
        return statementMapper.insertStatement(statement);
    }

    /**
     * 修改结算单
     *
     * @param statementVO 结算单
     * @return 结果
     */
    @Override
    public int updateStatement(StatementVO statementVO) {
        //检查状态
        Statement st = statementMapper.selectStatementById(statementVO.getId());
        Assert.notNull(st, "参数错误");
        Assert.state(st.getStatus() == Statement.CONSUMING, "当前状态不能修改");

        Assert.state(ValidateUtils.validPhone(statementVO.getCustomerPhone()), "联系方式格式不正确");
        Assert.state(ValidateUtils.validLicensePlate(statementVO.getLicensePlate()), "车牌号码格式不正确");

        Statement statement = new Statement();
        BeanUtils.copyBeanProp(statement, statementVO);
        return statementMapper.updateStatement(statement);
    }

    /**
     * 批量删除结算单
     *
     * @param ids 需要删除的结算单主键
     * @return 结果
     */
    @Override
    public int deleteStatementByIds(Long[] ids) {
        return statementMapper.deleteStatementByIds(ids);
    }

    /**
     * 删除结算单信息
     *
     * @param id 结算单主键
     * @return 结果
     */
    @Override
    public int deleteStatementById(Long id) {
        //检查状态
        Statement st = statementMapper.selectStatementById(id);
        Assert.notNull(st, "参数错误");
        Assert.state(st.getStatus() == Statement.CONSUMING, "当前状态不能修改");

        return statementMapper.updateIsDelete(id);
    }

    @Override
    @Transactional
    public int insertStatementItem(StatementItemVO statementItemVO) {
        //状态检查
        // 通过ID获取结算单
        Statement statement = statementMapper.selectStatementById(statementItemVO.getStatementId());
        // 确保结算单不为空
        Assert.notNull(statement, "参数错误");
        // 确保结算单的状态为消费中
        Assert.state(statement.getStatus() == Statement.CONSUMING, "当前结算单的状态不允许保存明细");
        //检查明细
        // 获取结算单的所有明细
        List<StatementItem> items = statementItemVO.getItems();
        // 确保明细不为空
        Assert.notEmpty(items, "请添加明细在保存");
        //更新结算单（总金额，总数量，优惠金额）
        // 初始化总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        // 初始化总数量
        long totalQuantity = 0;
        // 遍历所有明细
        for (StatementItem item : items) {
            // 获取明细的数量
            Long itemQuantity = item.getItemQuantity();
            // 价格不能直接使用前端传递，而应该从数据库查询真实的价格
            // 通过ID获取服务项
            ServiceItem serviceItem = serviceItemService.selectServiceItemById(item.getItemId());
            Assert.notNull(serviceItem, "当前服务项不存在");
            // 获取服务项的折扣价格
            BigDecimal discountPrice = serviceItem.getDiscountPrice();
            // 求每个明细的总金额，按照四舍五入保存两位小数
            // 计算明细的总金额
            BigDecimal total = discountPrice.multiply(new BigDecimal(itemQuantity + "").setScale(2, RoundingMode.HALF_UP));
            // 求和
            // 将明细的总金额加入到总金额中
            totalAmount = totalAmount.add(total);
            // 总数量求和
            // 将明细的数量加入到总数量中
            totalQuantity += itemQuantity;
        }
        // 创建新的结算单对象
        Statement st = new Statement();
        // 设置总金额
        st.setTotalAmount(totalAmount);
        // 设置总数量
        st.setTotalQuantity(new BigDecimal(totalQuantity + ""));
        // 设置优惠金额
        st.setDiscountAmount(statementItemVO.getDiscountAmount());
        // 设置ID
        st.setId(statementItemVO.getStatementId());
        // 更新结算单
        int rows = statementMapper.updateStatement(st);
        // 确保更新成功
        Assert.state(rows > 0, "结算单更新失败");
        //先删除明细，再添加新明细
        int deleteRows = statementItemMapper.deleteStatementItemBStatementId(statementItemVO.getStatementId());
        int row2 = statementItemMapper.insertStatementItem(items);
        Assert.state(row2 > 0, "添加明细失败");
        return 1;
    }

    @Override
    public List<StatementItem> selectStatementItemByStatementId(Long statementId) {
        return statementItemMapper.selectStatementItemByStatementId(statementId);
    }

    @Override
    public int paid(Long statementId) {
        //状态检查
        Statement statement = statementMapper.selectStatementById(statementId);
        Assert.notNull(statement,"参数错误");
        Assert.state(statement.getStatus()== Statement.CONSUMING,"当前结算单已支付");
        //检查有没有保存
        Assert.state(statement.getTotalAmount().compareTo(BigDecimal.ZERO) != 0,"请先保存");
        //更新结算单
        Statement st = new Statement();
        st.setId(statementId);
        st.setStatus(Statement.PAID);
        st.setPayeeId(SecurityUtils.getUserId());//当前系统的用户为收款人
        st.setPayTime(DateUtils.getNowDate());
        int stRows = statementMapper.updateStatement(st);
        Assert.state(stRows > 0 ,"结算单状态更新失败");
        //如果是预约生成的结算单，修改预约单的状态
        if (statement.getAppointmentId() != null){
            int appRows = appointmentService.updateStatus(statement.getAppointmentId(), AppointmentStatusEnum.PAID.ordinal());
            Assert.state(appRows > 0,"预约单状态更新失败");
        }
        return 1;
    }

    @Override
    @Transactional
    public Statement doCreateStatement(Long appointmentId) {
        //检查状态
        Appointment appointment = appointmentService.selectAppointmentById(appointmentId);
        Assert.notNull(appointment,"参数错误");
        List<Integer> allowStatus = Arrays.asList(AppointmentStatusEnum.ARRIVED.ordinal(), AppointmentStatusEnum.PAID.ordinal(), AppointmentStatusEnum.SETTLED.ordinal());
        Assert.state(allowStatus.contains(appointment.getStatus()), "当前状态不允许操作结算单");

        // 根据预约id查询结算单
        Statement statement = statementMapper.selectStatementByAppointmentId(appointmentId);
        if (statement == null) {
            statement = new Statement();
            // 如果没有，则创建新的结算单，反之直接返回
            BeanUtils.copyBeanProp(statement,appointment);
            statement.setInfo(null);
            statement.setStatus(Statement.CONSUMING);
            statement.setAppointmentId(appointmentId);
            statementMapper.insertStatement(statement);
            appointmentService.updateStatus(appointmentId,AppointmentStatusEnum.SETTLED.ordinal());
        }
        return statement;
    }
}
