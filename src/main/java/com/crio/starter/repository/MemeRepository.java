package com.crio.starter.repository;

import java.util.List;
import java.util.Optional;
import com.crio.starter.data.Meme;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends MongoRepository<Meme,String> {

   
    List<Meme> findTop100ByOrderByIdDesc();
    Optional<Meme> findByNameAndCaptionAndUrl(String name, String caption, String url);
    
}
