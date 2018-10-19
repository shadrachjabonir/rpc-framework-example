package com.shadrachjabonir.rpcframeworkexample.service;

import com.shadrachjabonir.rpcframeworkexample.model.TestDto;

public interface TestQueueService {
    TestDto doQueue(String name, Integer age, Boolean status);
}
