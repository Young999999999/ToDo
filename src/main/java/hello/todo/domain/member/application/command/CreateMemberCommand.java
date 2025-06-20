package hello.todo.domain.member.application.command;

public record CreateMemberCommand(
        String sub,
        String email,
        String name
) {
    public static CreateMemberCommand of(String sub, String email, String name) {
        return new CreateMemberCommand(sub, email, name);
    }
}
