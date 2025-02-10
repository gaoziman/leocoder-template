package org.leocoder.template.common;

import lombok.Data;

/**
 * @author : 程序员Leo
 * @version 1.0
 * @date 2025-02-10 11:55
 * @description : 分页请求参数
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private int pageNum = 1;

    /**
     * 页面大小
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认降序）
     */
    private String sortOrder = "descend";
}

