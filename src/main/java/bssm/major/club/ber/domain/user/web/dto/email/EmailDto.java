package bssm.major.club.ber.domain.user.web.dto.email;

import bssm.major.club.ber.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDto {

    private String email;

    public User toEntity() {
        return User.builder()
                .email(email)
                .build();
    }

}
