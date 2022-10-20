package com.andrew.FinancialHelper.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class DigestService {

    public String hash(String password){
        return DigestUtils.md5Hex(password);
    }
}
