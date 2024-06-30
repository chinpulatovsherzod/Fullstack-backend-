package com.example.shopbackend.service.customer.wishlist;

import com.example.shopbackend.dto.WishlistDto;
import com.example.shopbackend.model.Product;
import com.example.shopbackend.model.User;
import com.example.shopbackend.model.Wishlist;
import com.example.shopbackend.repository.ProductRepository;
import com.example.shopbackend.repository.UserRepository;
import com.example.shopbackend.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistImpl implements WishlistService{

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WishlistRepository wishlistRepository;

    public WishlistDto addProductToWishlist(WishlistDto wishlistDto){
        Optional<Product> productOptional = productRepository.findById(wishlistDto.getProductId());
        Optional<User> userOptional = userRepository.findById(wishlistDto.getUserId());

        if (productOptional.isPresent() && userOptional.isPresent()){
            Wishlist wishlist = new Wishlist();
            wishlist.setProduct(productOptional.get());
            wishlist.setUser(userOptional.get());

            return wishlistRepository.save(wishlist).getWishlistDto();

        }
        return null;
    }

    public List<WishlistDto> getWishlistByUserId(Long userId){
        return wishlistRepository.findAllByUserId(userId).stream().map(Wishlist::getWishlistDto).collect(Collectors.toList());
    }

}
