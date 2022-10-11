package bssm.major.club.ber.domain.post.manager.service;

import bssm.major.club.ber.domain.ber.domain.type.Gender;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.post.manager.facade.ManagerPostFacade;
import bssm.major.club.ber.domain.post.manager.repository.ManagerPostRepository;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostCreateRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.response.ManagerPostResponseDto;
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
        // given
        given(managerPostRepository.save(any(ManagerPost.class))).willReturn(managerPost);
        given(userFacade.getCurrentUser()).willReturn(user);

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

    @DisplayName("게시글 상세 조회")
    @Test
    void findByManagerPostOfDetails() {
        // given
        given(managerPostFacade.findById(managerPost.getId())).willReturn(managerPost);
        managerPost.updateCreatedAt(LocalDateTime.now());

        // when
        ManagerPostResponseDto res = managerPostService.detail(managerPost.getId());

        // then
        verify(managerPostFacade, times(1)).findById(managerPost.getId());

        assertEquals(res.getContent(), managerPost.getContent());
        assertEquals(res.getTitle(), managerPost.getTitle());
        assertEquals(res.getWriter().getNickname(), managerPost.getWriter().getNickname());
    }

    @DisplayName("게시글 수정")
    @Test
    void updateManagerPost() {
        // given
        given(managerPostFacade.findById(managerPost.getId())).willReturn(managerPost);

        // when
        ManagerPostCreateRequestDto dto = ManagerPostCreateRequestDto.builder()
                .title("게시글 제목111")
                .content("게시글 내용222")
                .build();

        managerPostService.update(managerPost.getId(), dto);

        // then
        assertEquals("게시글 제목111", managerPost.getTitle());
        assertEquals("게시글 내용222", managerPost.getContent());
    }
}