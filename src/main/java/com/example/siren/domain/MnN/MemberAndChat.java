package com.example.siren.domain.MnN;

import com.example.siren.domain.chat.ChatRoom;
import com.example.siren.domain.member.Member;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "M_C")
public class MemberAndChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatRoom chatRoom;

    public MemberAndChat() {
    }

    public MemberAndChat(Member member, ChatRoom chatRoom) {
        this.member = member;
        this.chatRoom = chatRoom;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
