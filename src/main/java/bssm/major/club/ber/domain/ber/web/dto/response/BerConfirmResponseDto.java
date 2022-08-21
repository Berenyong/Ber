package bssm.major.club.ber.domain.ber.web.dto.response;

import bssm.major.club.ber.domain.ber.domain.Ber;
import lombok.Getter;

@Getter
public class BerConfirmResponseDto {

    private final String status;

    public BerConfirmResponseDto(Ber ber) {
        this.status = ber.getStatus().name();
    }
}
