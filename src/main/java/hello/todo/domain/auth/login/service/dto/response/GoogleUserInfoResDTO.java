package hello.todo.domain.auth.login.service.dto.response;

public record GoogleUserInfoResDTO(
        String id,
        String email,
        boolean verified_email,
        String name,
        String given_name,
        String family_name,
        String picture,
        String locale
) {
}
