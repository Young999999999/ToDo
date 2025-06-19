package hello.todo.domain.routine.presentation.controller;

import hello.todo.domain.common.security.JwtUserDetails;
import hello.todo.domain.routine.application.ChangeRoutineService;
import hello.todo.domain.routine.application.CreateRoutineService;
import hello.todo.domain.routine.application.RemoveRoutineService;
import hello.todo.domain.routine.application.RoutineQueryService;
import hello.todo.domain.routine.presentation.dto.request.ChangeRoutineRequest;
import hello.todo.domain.routine.presentation.dto.request.CreateRoutineRequest;
import hello.todo.domain.routine.presentation.dto.response.GetRoutineResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/routine")
public class RoutineController {

    private final CreateRoutineService createRoutineService;
    private final ChangeRoutineService changeRoutineService;
    private final RemoveRoutineService removeRoutineService;
    private final RoutineQueryService routineQueryService;

    //TODO: 루틴 완료하기

    //루틴 단건 조회하기
    @GetMapping("/{routineId}")
    public GetRoutineResponse getRoutine(
            @AuthenticationPrincipal JwtUserDetails user,
            @PathVariable Long routineId
    ) {
        return routineQueryService.findRoutine(routineId, user.getUserId());
    }

    //루틴 전체 조회하기
    @GetMapping
    public List<GetRoutineResponse> getAllRoutines(@AuthenticationPrincipal JwtUserDetails user) {
        return routineQueryService.findAllRoutine(user.getUserId());
    }


    //루틴 생성하기
    @PostMapping
    public void createRoutine(
            @AuthenticationPrincipal JwtUserDetails user,
            @RequestBody @Valid CreateRoutineRequest request
    ) {
        createRoutineService.createRoutine(user.getUserId(), request.toCreateRoutineCommand());
    }

    //루틴 삭제하기
    @DeleteMapping("/{routineId}")
    public void removeRoutine(@AuthenticationPrincipal JwtUserDetails user, @PathVariable Long routineId) {
        removeRoutineService.removeRoutine(user.getUserId(), routineId);
    }

    //루틴 수정하기
    @PatchMapping("/{routineId}")
    public void changeRoutine(
            @AuthenticationPrincipal JwtUserDetails user,
            @PathVariable Long routineId,
            @RequestBody ChangeRoutineRequest request
    ) {
        changeRoutineService.changeRoutine(routineId, user.getUserId(), request.toChangeRoutineCommand());
    }
}
