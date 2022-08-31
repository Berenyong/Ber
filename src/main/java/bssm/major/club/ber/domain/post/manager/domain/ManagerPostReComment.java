package bssm.major.club.ber.domain.post.manager.domain;

import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.global.entity.BasePostEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "manager_post_re_comment")
@Entity
public class ManagerPostReComment extends BasePostEntity {

    private String comment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private ManagerPostComment parent;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    private String writerName;

    @Builder
    public ManagerPostReComment(String comment, ManagerPostComment parent, User writer) {
        this.comment = comment;
        this.parent = parent;
        this.writer = writer;
    }

    public void confirmParent(ManagerPostComment parent) {
        this.parent = parent;
        parent.addReComment(this);
    }

    public void confirmWriter(User user) {
        this.writer = user;
        user.addReComment(this);
    }

    public void setWriterName(String name) {
        this.writerName = name;
    }
}
