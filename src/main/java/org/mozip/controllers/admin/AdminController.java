package org.mozip.controllers.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Member;

@Controller
@RequestMapping("/mozip")
public class AdminController {

    @Autowired
    private EntityManager em;

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

//    @GetMapping("/admin/member")
//    public void ex02(){
//        Member member = em.find(Member.class, 1L);
//        List<BoardData> list = member.getBoardDatas();	//회원이 작성한 게시글 목록
//        list.stream().forEach(System.out::println);
//    }
}
