package com.nelioalves.workshopmongo.resources;

import com.nelioalves.workshopmongo.domain.Post;
import com.nelioalves.workshopmongo.domain.User;
import com.nelioalves.workshopmongo.dto.UserDTO;
import com.nelioalves.workshopmongo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserDTO>>  findAll(){
        List<User> list = service.findAll();
        List<UserDTO> listDTO = list.stream().map(UserDTO::new).toList();
        return ResponseEntity.ok().body(listDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id){
        return ResponseEntity.ok().body(new UserDTO(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody UserDTO objDto){
        User obj = service.fromDTO(objDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        service.findById(id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody UserDTO objDto){
        User obj = service.fromDTO(objDto);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> findPosts(@PathVariable String id){
        return ResponseEntity.ok().body(service.findById(id).getPosts());
    }

}
