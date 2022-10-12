package bssm.major.club.ber.domain.post.manager.service;

import bssm.major.club.ber.domain.ber.domain.type.Gender;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPostComment;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPostReComment;
import bssm.major.club.ber.domain.post.manager.facade.ManagerPostFacade;
import bssm.major.club.ber.domain.post.manager.repository.ManagerPostCommentRepository;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostCommentRequestDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@DisplayName("관리자 게시글 서비스")
@ExtendWith(MockitoExtension.class)
class ManagerPostCommentServiceTest {

    @InjectMocks
    private ManagerPostCommentService managerPostCommentService;

    @Mock
    ManagerPostFacade managerPostFacade;

    @Mock
    UserFacade userFacade;

    @Mock
    ManagerPostCommentRepository managerPostCommentRepository;

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

    ManagerPostComment managerPostComment = ManagerPostComment.builder()
            .managerPost(managerPost)
            .comment("댓글 내용")
            .writer(user)
            .build();

    ManagerPostReComment managerPostReComment = ManagerPostReComment.builder()
            .writer(user)
            .parent(managerPostComment)
            .comment("대댓글 내용")
            .build();

//    @DisplayName("댓글 생성")
//    @Test
//    void test() {
//        // given
//        managerPost.updatePkForTest(1L);
//
//        given(managerPostFacade.findById(any())).willReturn(managerPost);
//        given(userFacade.getCurrentUser()).willReturn(user);
//        given(managerPostCommentRepository.save(any(ManagerPostComment.class))).willReturn(managerPostComment);
//
//        ArgumentCaptor<ManagerPostComment> commentArgumentCaptor = ArgumentCaptor.forClass(ManagerPostComment.class);
//        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
//
//        // when
//        ManagerPostCommentRequestDto req = ManagerPostCommentRequestDto.builder()
//                .comment("댓글 내용")
//                .managerPost(managerPost)
//                .build();
//
//        managerPostCommentService.createComment(managerPost.getId(), req);
//
//        // then
//        verify(managerPostCommentRepository, times(1)).save(commentArgumentCaptor.capture());
//        verify(userFacade, times(1)).getCurrentUser();
//        verify(managerPostFacade, times(1)).findById(longArgumentCaptor.capture());
//
//        ManagerPostComment createdPost = commentArgumentCaptor.getValue();
//
//        assertEquals(req.getComment(), createdPost.getComment());
//        assertEquals(req.getManagerPost(), createdPost.getManagerPost());
    }
}