package hello.todo.domain.member.presentation.dto.request;

public record CreateMemberReqDTO (
        String email,
        String password,
        String nickname
){ }
