package hello.todo.domain.member.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateMemberReqDTO (
        @NotNull
        String email,
        @NotNull
        String password,
        @NotNull
        String nickname
){ }
