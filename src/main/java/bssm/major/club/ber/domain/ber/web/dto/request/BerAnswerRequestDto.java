package bssm.major.club.ber.domain.ber.web.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BerAnswerRequestDto {

    private String answer;

    public BerAnswerRequestDto(String answer) {
        this.answer = answer;
    }

}
