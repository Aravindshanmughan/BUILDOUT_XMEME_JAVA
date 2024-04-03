package com.crio.starter.exchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.crio.starter.data.Meme;


@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class MemeDto {
    
    private String id;
}
