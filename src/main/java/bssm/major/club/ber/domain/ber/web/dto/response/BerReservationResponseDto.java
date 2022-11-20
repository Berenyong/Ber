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
        System.out.println("실행됨1");

        this.userId = ber.getUser().getId();
        System.out.println("실행됨2");

        this.berNo = ber.getBerNo();
        System.out.println("실행됨3");

        this.title = ber.getTitle();
        System.out.println("실행됨4");

        this.content = ber.getContent();
        System.out.println("실행됨5");

        this.gender = ber.getUser().getGender();
        System.out.println("실행됨6");

        this.student = ber.getUser().getName();
        System.out.println("실행됨7");

        this.classNumber = ber.getUser().getClassNumber();
        System.out.println("실행됨8");

        this.disciplinePeriod = ber.getUser().getDisciplinePeriod();
        System.out.println("실행됨9");

    }

}