package com.itheima.service;

import com.itheima.pojo.SMSCode;

public interface SMSCodeService {
    public String sendCodeToSMS(String tele);
    public boolean checkCode(SMSCode smsCode);
}
