package bssm.major.club.ber.domain.user.domain;

import bssm.major.club.ber.domain.user.domain.type.Role;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private int age;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String gitLink;

    private String blogLink;

    @Builder
    public User(Long id, String email, String nickname, int age, String password, Role role, String gitLink, String blogLink) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.age = age;
        this.password = password;
        this.role = role;
        this.gitLink = gitLink;
        this.blogLink = blogLink;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void matchedPassword(PasswordEncoder passwordEncoder, User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }
    }

    public void addUserAuthority() {
        this.role = Role.USER;
    }


}
