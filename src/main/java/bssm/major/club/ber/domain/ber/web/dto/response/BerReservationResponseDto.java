package bssm.major.club.ber.domain.ber.web.dto.response;

import bssm.major.club.ber.domain.ber.domain.Ber;
import bssm.major.club.ber.domain.ber.domain.type.BerNo;
import bssm.major.club.ber.domain.ber.domain.type.Gender;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BerReservationResponseDto {

    private final Long reservationId;
    private final Long userId;
    private final String student;
    private final int classNumber;
    private final String title;
    private final String berNo;
    private final String content;
    private final Gender gender;
    private final LocalDate disciplinePeriod;

    public BerReservationResponseDto(Ber ber) {
        this.reservationId = ber.getId();
        this.userId = ber.getUser().getId();
        this.berNo = ber.getBerNo();
        this.title = ber.getTitle();
        this.content = ber.getContent();
        this.gender = ber.getUser().getGender();
        this.student = ber.getUser().getName();
        this.classNumber = ber.getUser().getClassNumber();
        this.disciplinePeriod = ber.getUser().getDisciplinePeriod();
    }

}