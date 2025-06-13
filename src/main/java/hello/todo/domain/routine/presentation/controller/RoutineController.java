package hello.todo.domain.routine.presentation.controller;

import hello.todo.domain.routine.application.CreateRoutineService;
import hello.todo.domain.routine.application.RemoveRoutineService;
import hello.todo.domain.routine.application.RoutineQueryService;
import hello.todo.domain.routine.presentation.dto.request.CreateRoutineRequest;
import hello.todo.domain.routine.presentation.dto.response.RoutineDetailResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/routine")
public class RoutineController {

    private final CreateRoutineService addRoutineService;
    private final RemoveRoutineService removeRoutineService;
    private final RoutineQueryService routineQueryService;

    //루틴 완료하기

    //루틴 수정하기

    //루틴 추가하기
    //TODO: 세션으로 멤버 ID 갖고 오기
    @PostMapping("/{memberId}")
    public void addRoutine(@RequestBody @Valid CreateRoutineRequest createRoutineRequest, @PathVariable Long memberId) {
        //addRoutineService.createRoutine(createRoutineRequest, memberId);
    }

    //루틴 삭제하기
    @DeleteMapping("/{memberId}/{routineId}")
    public void removeRoutine(@PathVariable Long memberId, @PathVariable Long routineId){
        removeRoutineService.removeRoutine(memberId, routineId);
    }

    //루틴 상세조회하기
    @GetMapping("/{routineId}")
    public RoutineDetailResponse getRoutineDetail(@PathVariable Long routineId){
        return routineQueryService.getRoutineDetail(1l,routineId);
    }


    //루틴 한 달 단위로 간단 조회하기

}
