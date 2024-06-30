package com.example.shopbackend.service.customer.wishlist;

import com.example.shopbackend.dto.WishlistDto;

import java.util.List;

public interface WishlistService {

    WishlistDto addProductToWishlist(WishlistDto wishlistDto);
    List<WishlistDto> getWishlistByUserId(Long userId);
}
