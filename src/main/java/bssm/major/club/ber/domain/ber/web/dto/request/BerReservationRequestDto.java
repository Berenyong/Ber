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
    private final Gender gender;

    @Builder
    public BerReservationRequestDto(int number, String title, String content, Gender gender) {
        this.number = number;
        this.title = title;
        this.content = content;
        this.gender = gender;
    }

    public Ber toEntity() {
        return Ber.builder()
                .number(number)
                .title(title)
                .content(content)
                .gender(gender)
                .build();
    }
}
