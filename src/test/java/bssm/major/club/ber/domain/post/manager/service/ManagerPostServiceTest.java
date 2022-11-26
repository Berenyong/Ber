package bssm.major.club.ber.domain.post.manager.service;

import bssm.major.club.ber.domain.ber.domain.type.Gender;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.type.Role;
import bssm.major.club.ber.domain.user.facade.UserFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.*;

@DisplayName("Post Service")
@ExtendWith(MockitoExtension.class)
class ManagerPostServiceTest {

    @InjectMocks private ManagerPostService managerPostService;
    @Mock private UserFacade userFacade;
    @Mock private ManagerPostFacade managerPostFacade;
    @Mock private ManagerPostRepository managerPostRepository;

    User user = User.builder()
            .email("rltgjqmduftlagl")
            .gender(Gender.MAN)
            .name("최OneDragon")
            .classNumber(14)
            .age(18)
            .blogLink("asdfasf.com")
            .disciplinePeriod(LocalDate.ofEpochDay(0))
            .gitLink("asdf.com")
            .nickname("nyong")
            .password("13242134")
            .role(Role.ROLE_ADMIN)
            .warning(1)
            .build();

    ManagerPost managerPost = ManagerPost.builder()
            .title("게시글 제목")
            .content("게시글 내용")
            .writer(user)
            .view(1010)
            .build();

    @DisplayName("게시글 생성")
    @Test
    void createManagerPost() {
    }

    @DisplayName("게시글 상세 조회")
    @Test
    void findByManagerPostOfDetails() {
    }

    @DisplayName("게시글 수정")
    @Test
    void updateManagerPost() {
    }

    @DisplayName("게시글 삭제")
    @Test
    void test() {

    }
}