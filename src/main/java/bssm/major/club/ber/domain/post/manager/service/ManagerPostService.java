package bssm.major.club.ber.domain.post.manager.service;

import bssm.major.club.ber.domain.category.post.service.PostCategoryService;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.post.manager.facade.ManagerPostFacade;
import bssm.major.club.ber.domain.post.manager.repository.ManagerPostRepository;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostCreateRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.response.ManagerPostResponseDto;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.facade.UserFacade;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class ManagerPostService {

    private final UserFacade userFacade;
    private final ManagerPostRepository managerPostRepository;
    private final PostCategoryService postCategoryService;
    private final PostImgService postImgService;
    private final ManagerPostFacade managerPostFacade;


    @Transactional
    public void createPost(ManagerPostCreateRequestDto request) {
        ManagerPost manager = request.toEntity(userFacade.getCurrentUser());
        managerPostRepository.save(manager);
    }

    @Transactional
    public ManagerPostResponseDto detail(Long id) {
        ManagerPost managerPost = managerPostFacade.findById(id);

        managerPost.upView();
        log.warn("Title to ManagerPost : {}", managerPost.getTitle());

        return new ManagerPostResponseDto(managerPost);
    }

    public List<ManagerPostResponseDto> findByTitle(String title, Pageable pageable) {
        return managerPostRepository.findByTitle(title, pageable)
                .stream()
                .map(ManagerPostResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ManagerPostResponseDto> popularPosts(Pageable pageable) {
        return managerPostRepository.findAllByOrderByLikesDesc(pageable)
                .stream()
                // 최근 일주일 인기 게시글
                .filter(p -> ChronoUnit.MINUTES.between(p.getCreatedAt(), LocalDateTime.now()) < 10080)
                .map(ManagerPostResponseDto::new)
                .collect(Collectors.toList());
    }

    // 최근에 올라온 게시글 순서
    public List<ManagerPostResponseDto> allPosts(Pageable pageable) {
        return managerPostRepository.findAll(pageable)
                .stream()
                .map(ManagerPostResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long id, ManagerPostCreateRequestDto request) {
        ManagerPost managerPost = managerPostFacade.findById(id);

        managerPost.update(request.getTitle(), request.getContent());
    }

    @Transactional
    public void delete(Long id) {
        ManagerPost managerPosts = managerPostFacade.findById(id);

        if (!managerPosts.getWriter().getEmail().equals(userFacade.getCurrentUser().getEmail())) {
            throw new CustomException(ErrorCode.DONT_ACCESS_OTHER);
        }

        managerPostRepository.delete(managerPosts);
    }

    @Transactional
    public void uploadImg(Long id, MultipartFile multipartFile) throws IOException {
        ManagerPost managerPost = managerPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        postImgService.updatePostImg(managerPost, multipartFile);
    }

}