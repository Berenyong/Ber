package bssm.major.club.ber.domain.ber.web.dto.request;

import bssm.major.club.ber.domain.ber.domain.Ber;
import bssm.major.club.ber.domain.ber.domain.type.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class BerConfirmRequestDto {

    @NotBlank
    private String status;

    private String answer;

    @Builder
    public BerConfirmRequestDto(String status, String answer) {
        this.status = status;
        this.answer = answer;
    }

}