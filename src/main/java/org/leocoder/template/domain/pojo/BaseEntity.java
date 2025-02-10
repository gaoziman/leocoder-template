package org.leocoder.template.domain.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * @author : 程序员Leo
 * @version 1.0
 * @date 2025-02-10 16:22
 * @description :
 */
@Data
public class BaseEntity {
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 编辑时间
     */
    @TableField(value = "edit_time")
    private Date editTime;


    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    @TableLogic
    private Integer isDelete;
}
