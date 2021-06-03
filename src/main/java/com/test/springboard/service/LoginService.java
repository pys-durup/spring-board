package com.test.springboard.service;

import com.test.springboard.model.MemberDTO;

public interface LoginService {
    void signUp(MemberDTO memberDTO);
    MemberDTO login(MemberDTO memberDTO);
}
