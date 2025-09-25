package com.jsun.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiPlatformModelOptions {
    private String platform;
    private String model;
    private Double temperature;
}
