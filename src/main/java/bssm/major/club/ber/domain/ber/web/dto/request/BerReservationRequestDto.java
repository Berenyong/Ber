package bssm.major.club.ber.domain.ber.web.dto.request;

import bssm.major.club.ber.domain.ber.domain.Ber;
import bssm.major.club.ber.domain.ber.domain.type.BerNo;
import bssm.major.club.ber.domain.ber.domain.type.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BerReservationRequestDto {

    private final String berNo;
    private final String title;
    private final String content;

    @Builder
    public BerReservationRequestDto(String berNo, String title, String content) {
        this.berNo = berNo;
        this.title = title;
        this.content = content;
    }

    public Ber toEntity() {
        return Ber.builder()
                .berNo(berNo)
                .title(title)
                .content(content)
                .build();
    }
}
