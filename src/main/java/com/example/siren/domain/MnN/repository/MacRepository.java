package com.example.siren.domain.MnN.repository;

import com.example.siren.domain.MnN.MemberAndChat;
import com.example.siren.domain.chat.ChatRoom;
import com.example.siren.domain.member.Member;

public interface MacRepository {

    MemberAndChat save(MemberAndChat memberAndChat);
    MemberAndChat delete(MemberAndChat memberAndChat);
}
