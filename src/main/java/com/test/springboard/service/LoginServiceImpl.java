package com.test.springboard.service;

import com.test.springboard.model.MemberDTO;
import com.test.springboard.model.MemberEntity;
import com.test.springboard.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

    private final MemberRepository memberRepository;

    @Autowired
    public LoginServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void signUp(MemberDTO memberDTO) {
        System.out.println("회원가입 로직.");
        memberRepository.save(memberDTO.toEntity());
    }

    @Override
    public MemberDTO login(MemberDTO memberDTO) {
        return null;
    }
}
