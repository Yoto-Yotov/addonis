package com.addonis.demo;

import com.addonis.demo.models.Tag;

public class Factory {
    public static Tag createTag() {
        return Tag.builder().tagName("Horse").tagId(1).build();
    }
}
