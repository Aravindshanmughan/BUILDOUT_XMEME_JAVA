package com.crio.starter.service;

import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import com.crio.starter.data.Meme;
import com.crio.starter.exchange.MemeDto;
import com.crio.starter.exchange.ResponseDto;
import com.crio.starter.repository.MemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;


@Service
public class MemeService {

    @Autowired
    private MemeRepository memeRepository;

    public String createMeme(Meme memeRequest) {
        
        long uniqueId=0;
        if(memeRequest.getId()==null){
        uniqueId = generateUniqueId();
        }
        Meme meme = new Meme(String.valueOf(uniqueId), memeRequest.getName(), memeRequest.getUrl(), memeRequest.getCaption());
        memeRepository.save(meme);
       return String.valueOf(uniqueId);
    }
    
    private long generateUniqueId() {
        return memeRepository.count() + 1;
    }

    public List<Meme> get100Memes() {
        List<Meme> memes = memeRepository.findAll();

        // Sort the memes based on their ID values (assuming IDs are String)
        Collections.sort(memes, Comparator.comparingInt(meme -> Integer.parseInt(((Meme) meme).getId())).reversed());

        // Retrieve the last 100 memes, or all memes if there are less than 100
        List<Meme> last100Memes = new ArrayList<>();
        int count = Math.min(100, memes.size());
        for (int i = 0; i < count; i++) {
            last100Memes.add(memes.get(i));
        }
    
      
    
        return last100Memes;
    }

    public Optional<Meme> getMemeById(String id) {
        Optional<Meme> meme= memeRepository.findById(id);
        return meme != null ? meme : Optional.empty();
    }

    public List<Meme> getAllMemes(){
       List<Meme> memes= memeRepository.findAll();
        return memes!=null ?memes:Collections.emptyList();
    }
    
}
