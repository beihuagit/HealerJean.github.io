package com.hlj.proj.controller;

import com.hlj.proj.api.demo.DemoEntityService;
import com.hlj.proj.dto.Demo.DemoDTO;
import com.hlj.proj.dto.ResponseBean;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author HealerJean
 * @version 1.0v
 * @ClassName DemoController
 * @date 2019/6/13  20:01.
 * @Description
 */
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "访问正常"),
        @ApiResponse(code = 301, message = "逻辑错误"),
        @ApiResponse(code = 500, message = "系统错误"),
        @ApiResponse(code = 401, message = "未认证"),
        @ApiResponse(code = 403, message = "禁止访问"),
        @ApiResponse(code = 404, message = "url错误")
})
@Api(description = "demo控制器", value = "DEMO")
@Controller
@RequestMapping("hlj/demo")
@Slf4j
public class DemoController {

    @Autowired
    private DemoEntityService demoEntityService;



    @ApiOperation(value = "get",
            notes = "get",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            response = DemoDTO.class)
    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseBean get(DemoDTO demoDTO) {
        log.info("样例--------GET请求------请求参数{}", demoDTO);
        return ResponseBean.buildSuccess(demoEntityService.getMmethod(demoDTO));
    }

    @ApiOperation(value = "postJson",
            notes = "postJson",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            response = DemoDTO.class)
    @PostMapping(value = "postJson", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseBean postJson(@RequestBody DemoDTO demoDTO) {
        log.info("样例--------postJson请求------请求参数{}", demoDTO);
        return ResponseBean.buildSuccess(demoDTO);
    }

    @ApiOperation(value = "postForm",
            notes = "postForm",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            response = DemoDTO.class)
    @PostMapping(value = "postForm",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseBean postForm(DemoDTO demoDTO, HttpServletRequest request) throws IOException {
        request.getInputStream();
        log.info("样例--------postForm请求------请求参数{}", demoDTO);
        return ResponseBean.buildSuccess(demoDTO);
    }


    @ApiOperation(value = "无参数GET",
            notes = "无参数GET",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            response = DemoDTO.class)
    @GetMapping(value = "demo/noparam", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseBean noparam() {
        log.info("样例--------GET请求------请求参数{}");
        return ResponseBean.buildSuccess("无参数");
    }


}
