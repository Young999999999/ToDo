package hello.todo.domain.member.presentation.controller;

import hello.todo.domain.member.application.CreateMemberService;
import hello.todo.domain.member.presentation.dto.request.CreateMemberReqDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final CreateMemberService createMemberService;

    @PostMapping
    public void createMember(@RequestBody @Valid CreateMemberReqDTO dto){
        createMemberService.createMember(dto);
    }

}
