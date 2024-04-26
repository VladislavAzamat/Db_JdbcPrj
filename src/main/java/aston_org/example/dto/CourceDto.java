package aston_org.example.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourceDto {

    private String title;
    private Float duration;
    private Integer price;
    private List<StudentDto> studentDtoList;

    public CourceDto(String title, Float duration, Integer price) {
        this.title = title;
        this.duration = duration;
        this.price = price;
    }
}
