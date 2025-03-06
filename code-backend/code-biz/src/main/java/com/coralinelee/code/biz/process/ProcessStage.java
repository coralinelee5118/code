package com.coralinelee.code.biz.process;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: CoralineLee Date: 3/6/2025
 */
@Data
public class ProcessStage {
    private List<String> actions = new ArrayList();
    private String router = "defaultRouter";
}