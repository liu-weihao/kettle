package com.yoogurt.kettle.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoogurt.kettle.enums.StatusCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * 客户端响应返回值封装。
 *
 * @author Eric Lau
 * @Date 2017/8/28.
 */
@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObj implements Serializable {

    /**
     * 返回的状态码
     */
    private int status = StatusCode.INFO_SUCCESS.getStatus();

    /**
     * 提示信息
     */
    private String message = StatusCode.INFO_SUCCESS.getDetail();

    /**
     * 返回的数据主体内容
     */
    private Object body;

    /**
     * 返回的额外信息，无特殊情况，不使用
     */
    private Map<String, Object> extras;

    private ResponseObj() {
    }

    private ResponseObj(int status, String message, Object body) {
        this.status = status;
        this.message = message;
        this.body = body;
    }

    public static ResponseObj success() {
        return new ResponseBuilder().build();
    }

    public static ResponseObj success(Object body) {
        return new ResponseBuilder().body(body).build();
    }

    public static ResponseObj success(Object body, Map<String, Object> extras) {
        return new ResponseBuilder().body(body).extras(extras).build();
    }

    public static ResponseObj fail() {
        return new ResponseBuilder().status(StatusCode.BIZ_FAILED.getStatus()).message(StatusCode.BIZ_FAILED.getDetail()).build();
    }

    public static ResponseObj fail(StatusCode statusCode, String message) {
        int status = StatusCode.BIZ_FAILED.getStatus();
        if (statusCode != null) {
            status = statusCode.getStatus();
        }
        return fail(status, message);
    }

    public static ResponseObj fail(StatusCode statusCode) {
        int status = StatusCode.BIZ_FAILED.getStatus();
        String message = StatusCode.BIZ_FAILED.getDetail();
        if (statusCode != null) {
            status = statusCode.getStatus();
            message = statusCode.getDetail();
        }
        return fail(status, message);
    }

    public static ResponseObj fail(int status, String message) {
        ResponseBuilder builder = new ResponseBuilder().status(status);
        if (StringUtils.isBlank(message)) {
            message = StatusCode.BIZ_FAILED.getDetail();
        }
        return builder.message(message).build();
    }

    public static ResponseObj fail(StatusCode status, String message, Map<String, Object> extras) {
        ResponseObj obj = fail(status, message);
        obj.setExtras(extras);
        return obj;
    }

    /**
     * 将ResponseObj对象转换成JSON字符串。
     * 基于Jackson对象序列化技术。
     *
     * @return
     */
    public String toJSON() {
        try {
            ObjectMapper m = new ObjectMapper();
            m.setDateFormat(DateFormat.getDateTimeInstance());
            return m.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("JSON序列化异常,{}", e);
        }
        return StringUtils.EMPTY;
    }

    public Map<String, Object> getExtras() {
        if (extras == null) {
            extras = new HashMap<>();
        }
        return extras;
    }

    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }

    @Override
    public String toString() {
        return toJSON();
    }

    public boolean isSuccess() {
        return this.status == StatusCode.INFO_SUCCESS.getStatus();
    }

    public static class ResponseBuilder {

        /**
         * 返回的状态码
         */
        private int status = StatusCode.INFO_SUCCESS.getStatus();

        /**
         * 提示信息
         */
        private String message = StatusCode.INFO_SUCCESS.getDetail();

        /**
         * 返回的数据主体内容
         */
        private Object body;

        /**
         * 返回的额外信息，无特殊情况，不使用
         */
        private Map<String, Object> extras;

        public ResponseBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ResponseBuilder status(StatusCode status) {
            if (status != null) {
                this.status = status.getStatus();
            }
            return this;
        }

        public ResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ResponseBuilder body(Object body) {
            this.body = body;
            return this;
        }

        public ResponseBuilder extras(Map<String, Object> extras) {
            this.extras = extras;
            return this;
        }

        public ResponseObj build() {
            ResponseObj obj = new ResponseObj(this.status, this.message, this.body);
            if (this.extras != null && this.extras.size() > 0) {
                obj.setExtras(this.extras);
            }
            return obj;
        }
    }

}
