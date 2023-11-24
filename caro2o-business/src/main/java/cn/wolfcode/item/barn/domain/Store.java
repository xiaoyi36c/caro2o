package cn.wolfcode.item.barn.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

import javax.validation.constraints.NotNull;

/**
 * 仓库信息对象 store
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
public class Store extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    public static final Integer STATE_NORMAL = 0; //正常
    public static final Integer STATE_DELETE = -1; //删除

    /** 序号 */
    private Long id;

    /** 仓库名字 */
    @NotNull(message = "仓库名字不能为空")
    private String storeName;

    /** 地址 */
    @NotNull(message = "地址不能为空")
    private String storeAddress;

    /** 状态 */
    private Integer state;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setStoreName(String storeName) 
    {
        this.storeName = storeName;
    }

    public String getStoreName() 
    {
        return storeName;
    }
    public void setStoreAddress(String storeAddress) 
    {
        this.storeAddress = storeAddress;
    }

    public String getStoreAddress() 
    {
        return storeAddress;
    }
    public void setState(Integer state) 
    {
        this.state = state;
    }

    public Integer getState() 
    {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("storeName", getStoreName())
            .append("storeAddress", getStoreAddress())
            .append("state", getState())
            .toString();
    }
}
