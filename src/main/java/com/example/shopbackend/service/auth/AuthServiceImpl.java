package com.example.shopbackend.service.auth;

import com.example.shopbackend.dto.SignupRequest;
import com.example.shopbackend.dto.UserDto;
import com.example.shopbackend.enums.OrderStatus;
import com.example.shopbackend.enums.UserRole;
import com.example.shopbackend.model.Order;
import com.example.shopbackend.model.User;
import com.example.shopbackend.repository.OrderRepository;
import com.example.shopbackend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private OrderRepository orderRepository;


    public UserDto createUser(SignupRequest signupRequest){
        User user = new User();

        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        User createdUser = userRepository.save(user);

        Order order = new Order();
        order.setAmount(0L);
        order.setTotalAmount(0L);
        order.setDiscount(0L);
        order.setUser(createdUser);
        order.setOrderStatus(OrderStatus.Pending);
        orderRepository.save(order);

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());

        return userDto;
    }

    public Boolean hasUserWithEmail(String email){
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount(){
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if (adminAccount == null){
            User user = new User();
            user.setName(user.getEmail());
            user.setName(user.getName());
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setRole(UserRole.ADMIN);
            userRepository.save(user);
        }
    }
}
