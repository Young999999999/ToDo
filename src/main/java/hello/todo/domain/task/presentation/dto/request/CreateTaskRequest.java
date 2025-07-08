package hello.todo.domain.task.presentation.dto.request;

import hello.todo.domain.task.application.command.CreateTaskCommand;

public record CreateTaskRequest(
        String title
) {
    public CreateTaskCommand toCommand(){
        return new CreateTaskCommand(this.title);
    }
}
