package com.shadrachjabonir.rpcframeworkexample.service.impl;

import com.shadrachjabonir.rpcframeworkexample.model.TestDto;
import com.shadrachjabonir.rpcframeworkexample.service.TestQueueService;
import org.springframework.stereotype.Service;

@Service
public class TestQueueServiceImpl implements TestQueueService {
    @Override
    public TestDto doQueue(String name, Integer age, Boolean status) {
        TestDto testDto = new TestDto();
        testDto.setName(name);
        testDto.setAge(age);
        testDto.setActive(status);
        System.out.println(" do queue : " + testDto);
        return testDto;
    }
}
