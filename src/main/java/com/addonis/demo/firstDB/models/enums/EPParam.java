package com.addonis.demo.models.enums;

/**
 * EPParam
 * Enumerator for getting the link for commits, pulls or issues.
 */
public enum EPParam {
    COMMITS("commits"), PULLS("pulls"), ISSUES("issues"), README("readme");

    private String param;

    EPParam(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}
