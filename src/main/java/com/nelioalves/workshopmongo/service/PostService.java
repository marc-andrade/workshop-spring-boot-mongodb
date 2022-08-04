package com.nelioalves.workshopmongo.service;

import com.nelioalves.workshopmongo.domain.Post;
import com.nelioalves.workshopmongo.repositories.PostRepository;
import com.nelioalves.workshopmongo.resources.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public Post findById(String id){
        return repository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<Post> findByTitle(String text){
        return repository.findByTitle(text);
    }

}