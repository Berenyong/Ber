package bssm.major.club.ber.domain.ber.web.dto.response;

import bssm.major.club.ber.domain.ber.domain.Ber;
import lombok.Getter;

@Getter
public class BerConfirmReservationResponseDto {

    private final Long id;
    private final String status;
    private final String answer;

    public BerConfirmReservationResponseDto(Ber ber) {
        this.id = ber.getId();
        this.status = ber.getStatus().name();
        this.answer = ber.getAnswer();
    }
}
