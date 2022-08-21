package bssm.major.club.ber.domain.ber.web.dto.response;

import bssm.major.club.ber.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BerWarningResponseDto {

    private String warning;

    public BerWarningResponseDto(User user) {
        if (user.getWarning() == 1) {
            this.warning = "현재 경고 횟수 1번 누적입니다.";
        } else {
            this.warning = "현재 경고 횟수 2번 누적입니다. 베르실 사용이 이틀간 정지되었습니다.";
        }
    }
}
