package bssm.major.club.ber.domain.user.web.dto.user;

import bssm.major.club.ber.domain.manager_post.manager.web.dto.response.ManagerPostResponseDto;
import bssm.major.club.ber.domain.user.domain.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String nickname;
    private final int age;
    private final String role;
    private final String gitLink;
    private final String blogLink;
    private final String img;
    private final int warning;
    private final String gender;
    private final Integer classNumber;
    private final String statusMessage;
//    private final List<ManagerPostResponseDto> managerPosts;
//    private final List<UserCategory> userCategories;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.age = user.getAge();
        this.role = user.getRole().name();
        this.gitLink = user.getGitLink();
        this.blogLink = user.getBlogLink();
        this.gender = user.getGender().name();
        this.classNumber = user.getClassNumber();
        this.statusMessage = user.getStatusMessage();
        this.img = user.getImgUrl();
        this.warning = user.getWarning();
//        this.managerPosts = user.getManagerPost().stream()
//                .map(ManagerPostResponseDto::new)
//                .collect(Collectors.toList());

//        this.userCategories = user.getCategories();
    }
}
