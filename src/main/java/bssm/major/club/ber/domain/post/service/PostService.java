package bssm.major.club.ber.domain.post.service;

import bssm.major.club.ber.domain.post.domain.Post;
import bssm.major.club.ber.domain.post.domain.repository.PostRepository;
import bssm.major.club.ber.domain.post.domain.type.PostKind;
import bssm.major.club.ber.domain.post.web.dto.request.PostRequestDto;
import bssm.major.club.ber.domain.post.web.dto.response.PostResponseDto;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.repository.UserRepository;
import bssm.major.club.ber.domain.user.domain.type.Role;
import bssm.major.club.ber.domain.user.facade.UserFacade;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import bssm.major.club.ber.global.exception.PostException;
import bssm.major.club.ber.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public Long createPost(PostRequestDto request){
        User user = userRepository.findByEmail(userFacade.getCurrentUser().getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN));

        if(request.getPostKind() == PostKind.MANAGER && !user.getRole().equals(Role.ROLE_MANAGER))
            throw new CustomException(ErrorCode.YOUR_NOT_MANAGER);

        Post post = postRepository.save(request.toEntity());
        post.confirmWriter(user);

        return post.getId();
    }


    public List<PostResponseDto> findByPostKind(Pageable pageable, String postKindTitle){

        if(PostKind.find(postKindTitle) == null)
            throw new PostException(ErrorCode.POSTKIND_NOT_EXIST);

        PostKind postKind = PostKind.find(postKindTitle);

        return  postRepository.findByPostKind(pageable, postKind).stream()
                .map(PostResponseDto :: new)
                .collect(Collectors.toList());
    }


    public List<PostResponseDto> findByTitle(Pageable pageable, String postKindTitle, String title){

        if(PostKind.find(postKindTitle) == null)
            throw new PostException(ErrorCode.POSTKIND_NOT_EXIST);

        PostKind postKind = PostKind.find(postKindTitle);
        return postRepository.findByTitle(pageable, postKind, title).stream()
                .map(PostResponseDto :: new)
                .collect(Collectors.toList());
    }

    public PostResponseDto findById(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        PostResponseDto postDto = new PostResponseDto(post);

        return postDto;
    }

    @Transactional
    public Long update(Long id,PostRequestDto request){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(ErrorCode.POSTS_NOT_FOUND));

        if(!post.getWriter().equals(userFacade.getCurrentUser())){
            throw new CustomException(ErrorCode.DONT_ACCESS_OTHER);
        }

        post.update(request);

        return post.getId();
    }

    @Transactional
    public Long delete(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(ErrorCode.POSTS_NOT_FOUND));

        if(!post.getWriter().equals(userFacade.getCurrentUser())){
            throw new CustomException(ErrorCode.DONT_ACCESS_OTHER);
        }

        postRepository.delete(post);

        return id;
    }

}
