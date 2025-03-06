package com.coralinelee.code.remote.fact;

import com.coralinelee.code.remote.model.FactInfo;
import com.coralinelee.code.remote.model.FactResult;

/**
 * Check fact abstraction
 *
 * Author: CoralineLee Date: 3/6/2025
 */
public interface FactCheckRemoteService {

    FactResult diagnose(FactInfo factInfo);

    String getType();
}
