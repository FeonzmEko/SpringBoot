package com.itheima.controller;

import com.itheima.pojo.Book;
import com.itheima.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    private MsgService msgService;


    @GetMapping("/{tele}")
    public String get(@PathVariable String tele){
        return msgService.get(tele);
    }

    @PostMapping
    public Boolean check(String tele,String code){
        return msgService.check(tele,code);
    }
}
