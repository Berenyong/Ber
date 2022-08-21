package bssm.major.club.ber.domain.ber.web.dto.response;

import bssm.major.club.ber.domain.ber.domain.Ber;
import bssm.major.club.ber.domain.ber.domain.type.Gender;
import lombok.Getter;

@Getter
public class BerReservationResponseDto {

    private final Long id;
    private final String student;
    private final int classNumber;
    private final String title;
    private final int number;
    private final String content;
    private final Gender gender;

    public BerReservationResponseDto(Ber ber) {
        this.id = ber.getId();
        this.number = ber.getNumber();
        this.title = ber.getTitle();
        this.content = ber.getContent();
        this.gender = ber.getUser().getGender();
        this.student = ber.getUser().getName();
        this.classNumber = ber.getUser().getClassNumber();
    }

}