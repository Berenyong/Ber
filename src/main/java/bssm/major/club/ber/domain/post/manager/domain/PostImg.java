package bssm.major.club.ber.domain.post.manager.domain;

import bssm.major.club.ber.global.entity.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PostImg extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgPath;

    private String imgUrl;

    @ManyToOne(fetch = LAZY)
    @JsonIgnore
    @JoinColumn(name = "manager_post_id")
    private ManagerPost managerPost;

    @Builder
    public PostImg(String imgPath, String imgUrl, ManagerPost managerPost) {
        this.imgPath = imgPath;
        this.imgUrl = imgUrl;
        this.managerPost = managerPost;
    }
}
