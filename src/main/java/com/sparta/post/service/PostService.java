package com.sparta.post.service;

import com.sparta.post.dto.PostRequestDto;
import com.sparta.post.dto.PostResponseDto;
import com.sparta.post.entity.Post;
import org.springframework.stereotype.Service;
import com.sparta.post.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.InputMismatchException;
import java.util.List;

@Service
public class PostService {

    PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostResponseDto> getPosts(){
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).toList();

    }
    public PostResponseDto createPost(PostRequestDto postRequestDto){
        //Entity로 변환
        Post post = new Post(postRequestDto);

        Post savePost = postRepository.save(post);
        PostResponseDto postResponseDto = new PostResponseDto(savePost);
        return postResponseDto;
    }

    public Post getPostOne(Long id){
        return postRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("선택한 게시글이 없습니다.")
        );

    }

    public PostResponseDto deletePost(Long id,String password){
        Post post = getPostOne(id);

        if(post.getPassword().equals(password)){
            postRepository.delete(post);
            PostResponseDto postResponseDto = new PostResponseDto(post);
            postResponseDto.setMsg(id+"번 글 삭제가 완료되었습니다.");
            return postResponseDto;
        }else{
            throw new InputMismatchException("비밀번호가 올바루지 않습니다.");
        }
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto){
        Post post = getPostOne(id);
        if(post.getPassword().equals(postRequestDto.getPassword())){
            post.update(postRequestDto);
            PostResponseDto postResponseDto = new PostResponseDto(post);
            return postResponseDto;
        }else{
            throw new InputMismatchException("비밀번호가 올바루지 않습니다.");
        }
    }
}
