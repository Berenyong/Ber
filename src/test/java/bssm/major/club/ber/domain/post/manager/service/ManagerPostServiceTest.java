package bssm.major.club.ber.domain.post.manager.service;

import bssm.major.club.ber.domain.ber.domain.type.Gender;
import bssm.major.club.ber.domain.post.domain.Post;
import bssm.major.club.ber.domain.post.domain.repository.PostRepository;
import bssm.major.club.ber.domain.post.domain.type.PostKind;
import bssm.major.club.ber.domain.post.service.PostService;
import bssm.major.club.ber.domain.post.web.dto.request.PostRequestDto;
import bssm.major.club.ber.domain.post.web.dto.response.PostResponseDto;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.repository.UserRepository;
import bssm.major.club.ber.domain.user.domain.type.Role;
import bssm.major.club.ber.domain.user.facade.UserFacade;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.testng.annotations.BeforeTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.*;

@DisplayName("Post Service")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ManagerPostServiceTest {

    @InjectMocks private PostService postService;
    @Mock private UserFacade userFacade;

    @Mock private UserRepository userRepository;
//    @Mock private PostFacade postFacade;
    @Mock private PostRepository postRepository;

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

    Post post = Post.builder()
            .title("게시글 제목")
            .content("게시글 내용")
            .postKind(PostKind.FREE)
            .build();

    PostRequestDto request = PostRequestDto.builder()
            .title(post.getTitle())
            .content(post.getContent())
            .postKind(post.getPostKind())
            .postCategories(post.getPostCategories())
            .build();

    String postKindTitle = "자유게시판";

    private Pageable pageable;

    @DisplayName("게시글 생성")
    @Order(1)
    @Test
    void createPost() {

        //given
        given(postRepository.save(any(Post.class))).willReturn(post);
        given(userFacade.getCurrentUser()).willReturn(user);
        given(userRepository.findByEmail(any(String.class))).willReturn(Optional.ofNullable(user));

        //when
        Long createdPostId = postService.createPost(request);

        //then
        assertEquals(createdPostId, post.getId());
    }

    /***
     * 어케 하는지 모르겠다... 담에 기회가 된다면 테스트를 해보죠...
     */
    /*
    @DisplayName("제목으로 게시판 찾기")
    @Order(3)
    @Test
    void findByTitle() {
        String postKindTitle = "자유게시판";
        List<PostResponseDto> foundPost = postService.findByTitle(pageable, postKindTitle, "게시글 제목");

        List<PostRequestDto> setPost = new ArrayList<>();
        setPost.add(request);

        assertEquals(setPost, foundPost);
    }
    */

    @DisplayName("게시글 업데이트")
    @Test
    @Order(4)
    void update() {
        //given
        this.post.confirmWriter(user);

        given(postRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(post));
        given(userFacade.getCurrentUser()).willReturn(user);

        PostRequestDto createdDto = PostRequestDto.builder()
                .title("월드컵 우승 합시다")
                .content("화이팅")
                .build();
        //when
        Long id = 1L;
        postService.update(id, createdDto);

        //then
        assertEquals(post.getTitle(), "월드컵 우승 합시다");
        assertEquals(post.getContent(), "화이팅");
    }

    @DisplayName("게시글 삭제")
    @Order(5)
    @Test
    void delete() {
        //given
        given(postRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(post));
        given(userFacade.getCurrentUser()).willReturn(user);

        this.post.confirmWriter(user);

        Long id = 1L;

        //when
        postService.delete(id);

        //then
        /**
         * 히히 실행 잘 됨
         */
    }
}