package org.leocoder.template.controller;

import org.leocoder.template.common.Result;
import org.leocoder.template.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 程序员Leo
 * @version 1.0
 * @date 2025-02-10 13:51
 * @description :
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public Result<String> hello() {
        return ResultUtils.success("Hello World!");
    }
}
