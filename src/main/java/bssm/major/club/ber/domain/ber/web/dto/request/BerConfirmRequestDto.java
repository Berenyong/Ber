package bssm.major.club.ber.domain.ber.web.dto.request;

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

    @Builder
    public BerConfirmRequestDto(String status) {
        this.status = status;
    }
}
