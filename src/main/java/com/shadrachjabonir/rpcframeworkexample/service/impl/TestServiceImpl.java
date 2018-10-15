package com.shadrachjabonir.rpcframeworkexample.service.impl;

import com.shadrachjabonir.rpcframeworkexample.model.TestDto;
import com.shadrachjabonir.rpcframeworkexample.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {


    @Override
    public TestDto makeTest(String name, Integer age, Boolean active) {
        TestDto res = new TestDto();
        res.setName(name);
        res.setAge(age);
        res.setActive(active);
        System.out.println("Test has been made : " + res);
        return res;
    }

    @Override
    public TestDto printTest(TestDto testDto) {
        System.out.println(testDto.toString());
        return testDto;
    }
}
