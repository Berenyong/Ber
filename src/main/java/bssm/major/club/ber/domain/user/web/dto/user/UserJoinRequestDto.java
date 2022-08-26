package bssm.major.club.ber.domain.user.web.dto.user;

import bssm.major.club.ber.domain.ber.domain.type.Gender;
import bssm.major.club.ber.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class UserJoinRequestDto {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotNull(message = "학번은 필수 입력 값입니다.")
    private int classNumber;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "이메일 인증 코드는 필수 입력 값입니다.")
    private String checkEmailCode;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    private int age;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;
    private String checkPassword;

    @NotNull
    private String gender;

    @Builder
    public UserJoinRequestDto(String email, int classNumber, String name, String checkEmailCode, String nickname, int age, String password, String checkPassword, String gender) {
        this.email = email;
        this.classNumber = classNumber;
        this.name = name;
        this.checkEmailCode = checkEmailCode;
        this.nickname = nickname;
        this.age = age;
        this.password = password;
        this.checkPassword = checkPassword;
        this.gender = gender;
    }

    public User toEntity(){
        return User.builder()
                .email(email)
                .classNumber(classNumber)
                .name(name)
                .nickname(nickname)
                .age(age)
                .password(password)
                .build();
    }

}
