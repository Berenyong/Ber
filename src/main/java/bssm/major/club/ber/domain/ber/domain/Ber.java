package bssm.major.club.ber.domain.ber.domain;

import bssm.major.club.ber.domain.ber.domain.type.Gender;
import bssm.major.club.ber.domain.ber.domain.type.Status;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ber")
@Entity
public class Ber extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    private String title;

    private String content;

    private Status status;
    private String answer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Ber(Long id, int number, String title, String content, Status status, String answer, User user) {
        this.id = id;
        this.number = number;
        this.title = title;
        this.content = content;
        this.status = status;
        this.answer = answer;
        this.user = user;
    }

    public void updateNumber(int number) {
        this.number = number;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }
    public void updateAnswer(String answer) {
        this.answer = answer;
    }

    public void confirmUser(User user) {
        this.user = user;
        user.addBer(this);
    }

    public void addStatusWaiting() {
        this.status = Status.WAITING;
    }

    public void addStatusAccept() {
        this.status = Status.APPROVAL;
    }

    public void addStatusRefusal() {
        this.status = Status.REFUSAL;
    }
}
