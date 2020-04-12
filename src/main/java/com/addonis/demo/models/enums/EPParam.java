package com.addonis.demo.models.enums;

public enum EPParam {
    COMMITS("commits"), PULLS("pulls"), ISSUES("issues");

    private String param;

    EPParam(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}
