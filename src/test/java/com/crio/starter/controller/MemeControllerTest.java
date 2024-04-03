package com.crio.starter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import com.crio.starter.App;
import com.crio.starter.data.Meme;
import com.crio.starter.exchange.MemeDto;
import com.crio.starter.repository.MemeRepository;
import com.crio.starter.service.MemeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.mockito.quality.Strictness;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.is;





@SpringBootTest(classes = {App.class})
@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class MemeControllerTest {

    private MockMvc mockMvc;
    @Mock
    MemeRepository memeRepository;

    @Mock
    private MemeService memeService;

    @InjectMocks
    private MemeController memeController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(memeController).build();
    }
    @Test
    public void testCreateMeme() throws Exception {
        String requestBody = "{\"name\": \"Test Meme\", \"url\": \"https://example.com/test.jpg\", \"caption\": \"This is a test meme\"}";

    // Mock service method
    when(memeService.createMeme(any(Meme.class))).thenReturn("1");

    // Perform POST request
    mockMvc.perform(MockMvcRequestBuilders.post("/memes/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            // Verify response status and content
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
public void testGetAllMemes() throws Exception {
    // Mock data
    List<Meme> memes = Arrays.asList(new Meme("1", "MS Dhoni", "https://images.pexels.com/photos/3573382/pexels-photo-3573382.jpeg", "Meme for my place"),
    new Meme("2", "Viral Kohli", "https://images.pexels.com/photos/1078983/pexels-photo-1078983.jpeg", "Another home meme"));
    // Mock service method
    when(memeService.getAllMemes()).thenReturn(memes);

    // Perform GET request
    mockMvc.perform(get("/memes/"))
            // Verify response status and content
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
           .andExpect(content().json(objectMapper.writeValueAsString(memes)));
}

@Test
public void testGetMemeById() throws Exception {
    // Mock data
    String memeId = "1";
    Meme meme = new Meme(memeId, "Test Meme", "https://example.com/1.jpg", "Caption");

    // Mock service method
    when(memeService.getMemeById(memeId)).thenReturn(Optional.of(meme));

    // Perform GET request
    mockMvc.perform(get("/memes/{id}", memeId))//{id} is the placeholder
            // Verify response status and content
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(memeId)); 
}

    @Test
    public void testGetAllMemesEmptyDatabase() throws Exception {
    
        mockMvc.perform(get("/memes/"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().json("[]")); // Expecting empty JSON array as response
    }


}
