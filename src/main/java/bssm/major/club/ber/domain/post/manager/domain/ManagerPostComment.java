package bssm.major.club.ber.domain.post.manager.domain;

import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.global.entity.BasePostEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "manager_post_comment")
@Entity
public class ManagerPostComment extends BasePostEntity {

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manager_post_id")
    private ManagerPost managerPost;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User writer;

    @OneToMany(mappedBy = "parent")
    private final List<ManagerPostReComment> reComment = new ArrayList<>();

    @Builder
    public ManagerPostComment(String comment, ManagerPost managerPost, User writer) {
        this.comment = comment;
        this.managerPost = managerPost;
        this.writer = writer;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }

    //== 연관관계 편의 메서드 ==//
    public void confirmWriter(User writer) {
        this.writer = writer;
        writer.addComment(this);
    }

    public void confirmPost(ManagerPost post) {
        this.managerPost = post;
        post.confirmComment(this);
    }

    public void addReComment(ManagerPostReComment reComment){
        this.reComment.add(reComment);
    }
}
