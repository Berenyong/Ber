package bssm.major.club.ber.domain.post.domain.type;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum PostKind {
    FREE("자유게시판"),
    MANAGER("매니저게시판"),
    MAJOR("전고게시판"),
    PROJECT("프로젝트게시판");

    private String title;

    // PostKindnum의 타이틀에 찾아온 타이틀이 있는지 확인
    public static PostKind find(String title){
        return Arrays.stream(values())
                .filter(value -> value.title.equals(title))
                .findAny()
                .orElse(null);
    }
}
