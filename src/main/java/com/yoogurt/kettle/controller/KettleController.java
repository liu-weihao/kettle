package com.yoogurt.kettle.controller;

import com.yoogurt.kettle.beans.SyncRecord;
import com.yoogurt.kettle.enums.StatusCode;
import com.yoogurt.kettle.form.SpoonForm;
import com.yoogurt.kettle.service.KettleService;
import com.yoogurt.kettle.service.SyncRecordService;
import com.yoogurt.kettle.vo.ResponseObj;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Base64;

@Slf4j
@RestController
public class KettleController {

    @Autowired
    private KettleService kettleService;

    @Autowired
    private SyncRecordService recordService;

    @PostMapping(value = "/sync")
    public ResponseObj sync(@Valid SpoonForm form, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResponseObj.fail(StatusCode.FORM_INVALID, result.getAllErrors().get(0).getDefaultMessage());
        }
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isBlank(authorization)) {
            return ResponseObj.fail(StatusCode.NO_AUTHORITY, "校验码不存在");
        }
        if ("prod".equalsIgnoreCase(form.getTo())) {
            return ResponseObj.fail(StatusCode.FORM_INVALID, "目标数据源不存在");
        }
        form.setToken(authorization);
        form.setIpv4(getIpAddress(request));
        return kettleService.sync(form);
    }

    @GetMapping(value = "/record/{sync}")
    public ResponseObj getRecord(@PathVariable(name = "sync") String sync) {
        SyncRecord record = recordService.getRecord(sync);
        if (record != null) {
            return ResponseObj.success(record);
        }
        return ResponseObj.fail();
    }

    /**
     * 获取真实的ip地址
     */
    private String getIpAddress(HttpServletRequest request) {

        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                try {
                    ipAddress = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        // "***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

}
