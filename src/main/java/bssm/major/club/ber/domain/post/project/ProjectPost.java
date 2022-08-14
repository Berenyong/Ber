package bssm.major.club.ber.domain.post.project;

import bssm.major.club.ber.global.entity.BasePostEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_post")
@Entity
public class ProjectPost extends BasePostEntity {



}
