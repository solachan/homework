package com.xiepuxin.incident.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    OPEN(0, "Open"),
    CLOSED(1, "Closed"),
    RESOLVED(2, "Resolved");

    private Integer code;
    private String desc;
    public static boolean containsCode(Integer code){
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if(statusEnum.getCode().equals(code)){
                return true;
            }
        }
        return false;
    }
}
