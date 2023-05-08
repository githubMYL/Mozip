package org.mozip.controllers.admin;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.mozip.entities.BoardData;

import org.mozip.entities.Members;
import org.mozip.repositories.BoardDataRepository;
import org.mozip.repositories.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Log
@RequestMapping("/mozip")
@RequiredArgsConstructor
public class AdminController {

    private final BoardDataRepository boardDataRepository;

    private final MembersRepository membersRepository;
    @Autowired
    private EntityManager em;

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    /**
    @GetMapping("/admin/boardlist")
    public void boardList(){
        //회원이 작성한 게시글 목록 전체 조회
        List<BoardData> list = boardDataRepository.findAll();


        for (BoardData boardData : list) {
            System.out.println("::::::::::::::::: list ::::::::::::::::: " + boardData);
        }
        //list.stream().forEach(System.out::println);
    }

    @GetMapping("/admin/boardsearch")
    public void searchBoard() {
        // 게시물 번호로 게시물 조회
        BoardData data = boardDataRepository.findById(1L).orElse(null);
        log.info(data.toString());
        Members members= data.getMember();
        log.info(members.toString());
    }

//    @GetMapping("/admin/membersearch")
//    public void searchMember() {
//        // 멤버로 게시글 조회
//        Members members = membersRepository.findById(1L).orElse(null);
//        List<BoardData> boardDatas = members.getBoardData();
//
//        boardDatas.stream().forEach(System.out::println);
//    }
*/
}
