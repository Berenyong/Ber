package bssm.major.club.ber.domain.ber.domain;

import bssm.major.club.ber.domain.ber.domain.type.Status;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Ber extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String berNo;

    @Setter
    private int max;
    private int current = 0;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Status status;
    private String answer;

    @ManyToOne(fetch = LAZY)
    @Setter
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Ber(Long id, String berNo, int max, String title, String content, Status status, String answer, User user) {
        this.id = id;
        this.berNo = berNo;
        this.max = max;
        this.title = title;
        this.content = content;
        this.status = status;
        this.answer = answer;
        this.user = user;
    }

    public void updateNumber(String berNo) {
        this.berNo = berNo;
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
    public void addStatusWaiting() {
        this.status = Status.WAITING;
    }

    public void addStatusAccept() {
        this.status = Status.APPROVAL;
    }

    public void addStatusRefusal() {
        this.status = Status.REFUSAL;
    }

    public void increaseCurrent() {
        this.current++;
    }

}
