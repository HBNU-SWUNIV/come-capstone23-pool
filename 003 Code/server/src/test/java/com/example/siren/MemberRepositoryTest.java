package com.example.siren;

import com.example.siren.domain.chat.ChatService;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.repository.MemberRepository;
import com.example.siren.domain.post.Post;
import com.example.siren.domain.post.service.JpaPostService;
import com.example.siren.domain.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
 class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostService postService;

    @Autowired
    ChatService chatService;

    @Test
    void save() {
        //given
        Member item = new Member("user3","12dd","kangㅇㄴㅁㄹㄴ");

        //when
        Member savedItem = memberRepository.save(item);

        //then
        Member findItem = memberRepository.findById(item.getId()).get();
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAllItems() {
        List<Member> findall = memberRepository.findAll();
        for(Member a : findall){
            System.out.print(a.getId()+"/");
            System.out.print(a.getLoginId()+"/");
            System.out.print(a.getPassword()+"/");
            System.out.print(a.getName()+"/");
            System.out.println();
        }
    }
    @Test
    void findByLoginId() {
        Optional<Member> user1 = memberRepository.findByLoginId("user1");
        String loginId = user1.get().getLoginId();
        System.out.println(loginId);
    }
    @Test
    void loginTest(){
        String loginId ="user4";
        String password ="5ㅇㄴㅁㄹ5";
        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
        Member member = findMemberOptional.get();
        if(member.getPassword().equals(password)){
            System.out.println("로그인성공");
        }else{
            System.out.println("로그인 실패");
        }
    }

    @Test
    void getContentTest(){
        Optional<Post> post = postService.findById(33L);
        System.out.println(post.get().getName());

    }

    @Test
    void MacCheck(){
        List<Long> list = new ArrayList<>();
        list.add(34L);
        list.add(70L);
        chatService.getRoomsByMemberId(34L);
    }
}
