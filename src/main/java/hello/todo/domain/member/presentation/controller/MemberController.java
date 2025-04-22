package hello.todo.domain.member.presentation.controller;

import hello.todo.domain.member.application.CreateMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final CreateMemberService createMemberService;


}
