package hello.todo.domain.member.presentation.controller;

import hello.todo.domain.member.application.AddRoutineService;
import hello.todo.domain.member.application.RemoveRoutineService;
import hello.todo.domain.member.presentation.dto.request.CreateRoutineReqDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routine")
public class RoutineController {

    private final AddRoutineService addRoutineService;
    private final RemoveRoutineService removeRoutineService;

    //TODO: 세션으로 멤버 ID 갖고 오기
    @PostMapping("/{memberId}")
    public void addRoutine(@RequestBody @Valid CreateRoutineReqDTO createRoutineReqDTO, @PathVariable Long memberId) {
        addRoutineService.addRoutine(createRoutineReqDTO, memberId);
    }

    @DeleteMapping("/{memberId}/{routineId}")
    public void removeRoutine(@PathVariable Long memberId, @PathVariable Long routineId){
        removeRoutineService.removeRoutine(memberId, routineId);
    }
}
