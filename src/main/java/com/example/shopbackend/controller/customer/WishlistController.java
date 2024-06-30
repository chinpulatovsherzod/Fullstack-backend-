package com.example.shopbackend.controller.customer;


import com.example.shopbackend.dto.WishlistDto;
import com.example.shopbackend.service.customer.wishlist.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;


    @PostMapping("/wishlist")
    public ResponseEntity<?> addProductToWishlist(@RequestBody WishlistDto wishlistDto){
        WishlistDto wishlistDto1 = wishlistService.addProductToWishlist(wishlistDto);
        if (wishlistDto1 == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(wishlistDto1);
    }

    @GetMapping("/wishlist/{userId}")
    public ResponseEntity<List<WishlistDto>> getWishlistByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(wishlistService.getWishlistByUserId(userId));
    }
}
