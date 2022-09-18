package bssm.major.club.ber.domain.school.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TimeTable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long grade;

    @Column
    private Long classNo;

    @Column
    private Long day;

    @Column
    private Long idx;

    @Column
    private String className;

    @Builder
    public TimeTable(Long grade, Long classNo, Long day, Long idx, String className) {
        this.grade = grade;
        this.classNo = classNo;
        this.day = day;
        this.idx = idx;
        this.className = className;
    }

}
