package com.addonis.demo.models.enums;

public enum Sortby {
    NAME("name"), DOWNLOADS("downloadsCount"), ID("id"), DATE("lastCommit.date");

    private String param;

    Sortby(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }

    public static Sortby getByParam(String param) {
        Sortby result = null;

        for (Sortby entity : values()) {
            if(entity.getParam().equals(param)) {
                result = entity;
                break;
            }
        }

        if(result == null) {
            result = Sortby.NAME;
        }
        return result;
    }
}
