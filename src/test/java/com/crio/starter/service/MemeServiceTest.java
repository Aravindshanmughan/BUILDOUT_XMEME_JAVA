package com.crio.starter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.crio.starter.data.Meme;
import com.crio.starter.repository.MemeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MemeServiceTest {

    @Mock
    private MemeRepository memeRepository;

    @InjectMocks
    private MemeService memeService;

    @Test
    void testCreateMeme() {
        Meme meme = new Meme();
        //meme.setId("1");
        meme.setName("Test User");
        meme.setImageUrl("https://images.app.goo.gl/VG15ABrHGibQkZJr9");
        meme.setCaption("Test Caption");

        when(memeRepository.count()).thenReturn(0L);

        String id = memeService.createMeme(meme);

        assertEquals("1", id);
       

        verify(memeRepository, times(1)).save(any(Meme.class));
    }

    @Test
    void testGetAllMemes() {
        List<Meme> memeList = new ArrayList<>();
        Meme meme1 = new Meme();
        meme1.setId("1");
        meme1.setName("Test User 1");
        meme1.setImageUrl("http://example.com/image1.jpg");
        meme1.setCaption("Test Caption 1");
        memeList.add(meme1);

        Meme meme2 = new Meme();
        meme2.setId("2");
        meme2.setName("Test User 2");
        meme2.setImageUrl("http://example.com/image2.jpg");
        meme2.setCaption("Test Caption 2");
        memeList.add(meme2);

        when(memeRepository.findAll()).thenReturn(memeList);

        List<Meme> retrievedMemes = memeService.getAllMemes();

        assertEquals(memeList.size(), retrievedMemes.size());
        assertEquals(memeList.get(0).getId(), retrievedMemes.get(0).getId());
        assertEquals(memeList.get(1).getId(), retrievedMemes.get(1).getId());

        verify(memeRepository, times(1)).findAll();
    }

    @Test
    void testGetMemeById() {
        String id = "1";
        Meme meme = new Meme();
        meme.setId(id);
        meme.setName("Test User");
        meme.setImageUrl("http://example.com/image.jpg");
        meme.setCaption("Test Caption");

        when(memeRepository.findById(id)).thenReturn(Optional.of(meme));

        Optional<Meme> retrievedMeme = memeService.getMemeById(id);

        assertEquals(id, retrievedMeme.get().getId());
        assertEquals(meme.getName(), retrievedMeme.get().getName());
        assertEquals(meme.getImageUrl(), retrievedMeme.get().getImageUrl());
        assertEquals(meme.getCaption(), retrievedMeme.get().getCaption());

        verify(memeRepository, times(1)).findById(id);
    }

    


    
}
