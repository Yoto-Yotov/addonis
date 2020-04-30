package com.addonis.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddonTagDto {

    private int addonId;

    private String tagName;

    private String userName;
}
