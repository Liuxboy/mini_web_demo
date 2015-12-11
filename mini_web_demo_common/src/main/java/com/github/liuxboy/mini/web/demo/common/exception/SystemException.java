package com.github.liuxboy.mini.web.demo.common.exception;


import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("serial")
public class SystemException extends RuntimeException{

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 外部系统错误码
     */
    private Integer remoteCode;

    /**
     * log中的中文描述
     */
    private String logDescription;
    /**
     * 返回的中文描述
     */
    private String description;

    public SystemException(Integer code, Integer remoteCode, String logDescription, String description) {
        this.code = code;
        this.remoteCode = remoteCode;
        this.logDescription = logDescription;
        this.description = description;
    }

    public Integer getCode() {

        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getRemoteCode() {
        return remoteCode;
    }

    public void setRemoteCode(Integer remoteCode) {
        this.remoteCode = remoteCode;
    }

    public String getLogDescription() {
        return logDescription;
    }

    public void setLogDescription(String logDescription) {
        this.logDescription = logDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }

}
