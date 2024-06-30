package com.example.shopbackend.model;


import com.example.shopbackend.dto.ReviewDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Long rating;

    @Lob
    private String description;


    @Lob
    private byte[] img;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_Id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

     @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_Id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    public ReviewDto getDto() {
        ReviewDto reviewDto = new ReviewDto();

        reviewDto.setId(id);
        reviewDto.setDescription(description);
        reviewDto.setRating(rating);
        reviewDto.setUsername(user.getName());
        reviewDto.setUserId(user.getId());
        reviewDto.setProductId(product.getId());
        reviewDto.setImg(reviewDto.getImg());
        return reviewDto;
    }

}
