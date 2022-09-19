package bssm.major.club.ber.domain.post.manager.service;

import bssm.major.club.ber.domain.category.post.service.PostCategoryService;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.post.manager.repository.ManagerPostRepository;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostCreateRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.response.ManagerPostResponseDto;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.repository.UserRepository;
import bssm.major.club.ber.global.config.file.FileResponseDto;
import bssm.major.club.ber.global.config.file.FileService;
import bssm.major.club.ber.global.config.security.SecurityUtil;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final UserRepository userRepository;
    private final ManagerPostRepository managerPostRepository;
    private final PostCategoryService postCategoryService;
    private final FileService fileService;

    @Transactional
    public Long createPost(ManagerPostCreateRequestDto request) throws IOException {
        log.info("카테고리 = " + request.getCategories());
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN));

        ManagerPost managerPost = managerPostRepository.save(request.toEntity());
        managerPost.confirmWriter(user);

        request.getCategories()
                .forEach(c -> postCategoryService.createCategory(managerPost, c));

        FileResponseDto fileResponseDto = fileService.saveFile(request.getFile());
        managerPost.updateFile(fileResponseDto.getImgPath(), fileResponseDto.getImgUrl());

        return managerPost.getId();
    }

    @Transactional
    public ManagerPostResponseDto detail(Long id) {
        ManagerPost managerPost = managerPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        managerPost.upView();
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
    public ManagerPostResponseDto update(Long id, ManagerPostCreateRequestDto request) throws IOException {
        ManagerPost managerPost = managerPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        if(!managerPost.getImgPath().isEmpty()) {
            fileService.deleteFile(managerPost.getImgPath());
        }

        managerPost.update(request.getTitle(), request.getContent());
        FileResponseDto fileResponseDto = fileService.saveFile(request.getFile());
        managerPost.updateFile(fileResponseDto.getImgPath(), fileResponseDto.getImgUrl());
        return new ManagerPostResponseDto(managerPost);
    }

    @Transactional
    public String delete(Long id) {
        ManagerPost managerPosts = managerPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        if (!managerPosts.getWriter().getEmail().equals(SecurityUtil.getLoginUserEmail())) {
            throw new CustomException(ErrorCode.DONT_ACCESS_OTHER);
        }

        fileService.deleteFile(managerPosts.getImgPath());
        managerPostRepository.delete(managerPosts);

        return "정상적으로 삭제되었습니다.";
    }

}