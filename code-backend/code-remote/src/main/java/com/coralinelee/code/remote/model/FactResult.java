package com.coralinelee.code.remote.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Author: CoralineLee Date: 3/6/2025
 */
@Getter
@Setter
public class FactResult {
    private String type;

    private boolean fact;

    private String originInfo;

    private Map<String, String> diagnosisInfo;

}
