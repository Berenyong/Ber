package bssm.major.club.ber.domain.ber.web.dto.request;

import bssm.major.club.ber.domain.ber.domain.Ber;
import bssm.major.club.ber.domain.ber.domain.type.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BerReservationRequestDto {

    private final int number;
    private final String title;
    private final String content;

    @Builder
    public BerReservationRequestDto(int number, String title, String content) {
        this.number = number;
        this.title = title;
        this.content = content;
    }

    public Ber toEntity() {
        return Ber.builder()
                .number(number)
                .title(title)
                .content(content)
                .build();
    }
}
