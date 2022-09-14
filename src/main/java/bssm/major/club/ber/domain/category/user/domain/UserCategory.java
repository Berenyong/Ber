package bssm.major.club.ber.domain.category.user.domain;

import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.global.entity.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USER_CATEGORY")
@Getter
@Entity
public class UserCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Builder
    public UserCategory(Long id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public void confirmUser(User user) {
        user.addCategories(this);
        this.user = user;
    }

}
