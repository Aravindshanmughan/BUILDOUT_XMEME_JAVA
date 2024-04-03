package com.crio.starter.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import com.crio.starter.data.Meme;
import com.crio.starter.exchange.MemeDto;
import com.crio.starter.exchange.ResponseDto;
import com.crio.starter.repository.MemeRepository;
import com.crio.starter.service.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/memes")
public class MemeController {
    @Autowired
    private MemeService memeService; 
    @Autowired
    private MemeRepository memeRepository;
    
    @PostMapping("/")
    public ResponseEntity<MemeDto> createMeme(@Validated @RequestBody Meme memeRequest){
    if(memeRepository.findByNameAndCaptionAndUrl(memeRequest.getName(), memeRequest.getCaption(),memeRequest.getUrl()).isPresent()){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); 
}
if (memeRequest == null || memeRequest.getName() == null || memeRequest.getCaption() == null || memeRequest.getUrl() == null) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
}
        String memeId = memeService.createMeme(memeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MemeDto(memeId));
    } 

    @GetMapping("/")
    public ResponseEntity<List<Meme>> getLatestMemes() {
         List<Meme> memes;
        memes = memeService.get100Memes();
        if(memes!=null){
         return ResponseEntity.ok(memes);
        }else
        return ResponseEntity.ok(Collections.emptyList());
}


@GetMapping("/all")
public ResponseEntity<List<Meme>> getAllMemes() {
    List<Meme> memes;
    memes = memeService.getAllMemes();
    if(memes!=null){
     return ResponseEntity.ok(memes);
    }else
    return ResponseEntity.ok(Collections.emptyList());
}

    
     @GetMapping("/{id}")
    public ResponseEntity<Optional<Meme>> getMemeById(@PathVariable String id) {
        Optional<Meme> meme = memeService.getMemeById(id);
        if (meme != null&& memeRepository.findById(id).isPresent()) {
            return ResponseEntity.ok(meme);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
   
}
