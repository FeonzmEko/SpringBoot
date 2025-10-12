package com.itheima.service.Impl;

import com.itheima.pojo.SMSCode;
import com.itheima.service.SMSCodeService;
import com.itheima.utils.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SMSCodeServiceImpl implements SMSCodeService {

    @Autowired
    private CodeUtils codeUtils;

    @Override
    @CachePut(value = "smsCode",key = "#tele")
    public String sendCodeToSMS(String tele) {
        String code = codeUtils.generator(tele);
        return code;
    }

    @Override
    public boolean checkCode(SMSCode smsCode) {
        //取出内存验证码并比对
        String code = smsCode.getCode();
        String cacheCode = codeUtils.get(smsCode.getTele());
        return code.equals(cacheCode);
    }

}
