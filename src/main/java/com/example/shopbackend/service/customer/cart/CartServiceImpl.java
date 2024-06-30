package com.example.shopbackend.service.customer.cart;

import com.example.shopbackend.dto.AddProductInCartDto;
import com.example.shopbackend.dto.CartItemsDto;
import com.example.shopbackend.dto.OrderDto;
import com.example.shopbackend.dto.PlaceOrderDto;
import com.example.shopbackend.enums.OrderStatus;
import com.example.shopbackend.exceptions.ValidationException;
import com.example.shopbackend.model.*;
import com.example.shopbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CouponRepository couponRepository;


    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
        );

        if (optionalCartItems.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getProductId());

            if (optionalProduct.isPresent() && optionalUser.isPresent()) {
                CartItems cartItems = new CartItems();
                cartItems.setProduct(optionalProduct.get());
                cartItems.setPrice(optionalProduct.get().getPrice());
                cartItems.setQuantity(1L);
                cartItems.setUser(optionalUser.get());
                cartItems.setOrder(activeOrder);

                CartItems updatedCart = cartItemsRepository.save(cartItems);
                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItems.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + cartItems.getPrice());
                activeOrder.getCartItems().add(cartItems);

                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cartItems);

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
            }
        }
    }

    public OrderDto getCartByUserId(Long userId) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CartItemsDto> cartItemsDtos = activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());
        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtos);
        if (activeOrder.getCoupon() != null) {
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }

        return orderDto;
    }

    public OrderDto applyCoupon(Long userId, String code) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> new ValidationException("Coupon not found"));
        if (couponIsExpired(coupon)) {
            throw new ValidationException("Coupon has expired");
        }
        double discountAmount = ((coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount());
        double totalAmount = activeOrder.getTotalAmount() - discountAmount;

        activeOrder.setAmount((long) totalAmount);
        activeOrder.setDiscount((long) discountAmount);
        activeOrder.setCoupon(coupon);

        orderRepository.save(activeOrder);
        return activeOrder.getOrderDto();

    }

    public boolean couponIsExpired(Coupon coupon) {
        Date currentdate = new Date();
        Date expirationdate = coupon.getExpirationDate();

        return expirationdate != null && currentdate.after(expirationdate);
    }

    public OrderDto increaseProductQuality(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getUserId());

        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());

        if (optionalProduct.isPresent() && optionalCartItems.isPresent()) {
            CartItems cartItems = optionalCartItems.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
            activeOrder.setTotalAmount((activeOrder.getTotalAmount() + product.getPrice()));

            cartItems.setQuantity(cartItems.getQuantity() + 1);

            if (activeOrder.getCoupon() != null) {
                double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double totalAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount((long) totalAmount);
                activeOrder.setDiscount((long) discountAmount);
            }

            cartItemsRepository.save(cartItems);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

    public OrderDto decreaseProductQuality(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getUserId());
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(addProductInCartDto.getUserId(), activeOrder.getId(), addProductInCartDto.getUserId());
        if (optionalProduct.isPresent() && optionalCartItems.isPresent()) {

            CartItems cartItems = optionalCartItems.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());

            cartItems.setQuantity(cartItems.getQuantity() - 1);
            if (activeOrder.getCoupon() != null) {

                double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount((long) netAmount);
                activeOrder.setTotalAmount((long) discountAmount);
            }
            cartItemsRepository.save(cartItems);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
        Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());
        if (optionalUser.isPresent()){
            activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            activeOrder.setAddress(placeOrderDto.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.Placed);
            activeOrder.setTrackingId(UUID.randomUUID());
            orderRepository.save(activeOrder);

            Order order = new Order();
            order.setAmount(0L);
            order.setTotalAmount(0L);
            order.setDiscount(0L);
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(order);
            return order.getOrderDto();
        }
        return null;
    }

    public List<OrderDto> getMyPlacedOrders(Long userId){
        return orderRepository.findByUserIdAndOrderStatusIn(userId, List.of(OrderStatus.Placed, OrderStatus.Shipper, OrderStatus.Delivered)).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    public OrderDto searchOrderByTrackingId(UUID trackingId){
        Optional<Order> optionalOrder = orderRepository.findByTrackingId(trackingId);
        if (optionalOrder.isPresent()){
            return optionalOrder.get().getOrderDto();
        }
        return null;
    }
}
