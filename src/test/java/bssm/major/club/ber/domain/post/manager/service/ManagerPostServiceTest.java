package bssm.major.club.ber.domain.post.manager.service;

import bssm.major.club.ber.domain.ber.domain.type.Gender;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.post.manager.repository.ManagerPostRepository;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostCreateRequestDto;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.type.Role;
import bssm.major.club.ber.domain.user.facade.UserFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@DisplayName("Post Service")
@ExtendWith(MockitoExtension.class)
class ManagerPostServiceTest {

    @InjectMocks
    private ManagerPostService managerPostService;

    @Mock
    private UserFacade userFacade;

    @Mock
    private ManagerPostRepository managerPostRepository;

    User user = User.builder()
            .id(1L)
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

    @BeforeEach
    public void stubbing() {
        given(userFacade.getCurrentUser()).willReturn(user);
        given(managerPostRepository.save(any(ManagerPost.class))).willReturn(managerPost);
    }

    @DisplayName("게시글 생성")
    @Test
    void createManagerPost() {
        // given
        ArgumentCaptor<ManagerPost> managerPostCaptor = ArgumentCaptor.forClass(ManagerPost.class);

        // when
        ManagerPostCreateRequestDto req = ManagerPostCreateRequestDto.builder()
                .title("게시글 제목")
                .content("게시글 내용")
                .build();

        managerPostService.createPost(req);

        // then
        verify(userFacade, times(1)).getCurrentUser();
        verify(managerPostRepository, times(1)).save(managerPostCaptor.capture());

        ManagerPost post = managerPostCaptor.getValue();

        assertEquals(req.getTitle(), post.getTitle());
        assertEquals(req.getContent(), post.getContent());
    }


}