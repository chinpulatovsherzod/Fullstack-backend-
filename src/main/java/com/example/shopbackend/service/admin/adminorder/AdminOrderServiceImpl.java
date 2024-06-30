package com.example.shopbackend.service.admin.adminorder;

import com.example.shopbackend.dto.AnalyticsResponse;
import com.example.shopbackend.dto.OrderDto;
import com.example.shopbackend.enums.OrderStatus;
import com.example.shopbackend.model.Order;
import com.example.shopbackend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService{

    private final OrderRepository orderRepository;



    public List<OrderDto> getAllPlacedOrders(){
        List<Order> orders  = orderRepository.findAllByStatusIn(List.of(OrderStatus.Placed, OrderStatus.Delivered, OrderStatus.Pending));

        return orders.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    public OrderDto changeOrderStatus(Long orderId, String status){
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()){
            Order order1 = order.get();

            if (Objects.equals(status, "Shipped")){
                order1.setStatus(OrderStatus.Shipper);//Shipped
            }else if (Objects.equals(status, "Delivered")){
                order1.setStatus(OrderStatus.Delivered);
            }
            return orderRepository.save(order1).getOrderDto();
        }
        return null;
    }

    public AnalyticsResponse calculateAnalytics(Long orderId){
        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonthDate = currentDate.minusMonths(1);

        Long currentMonthOrders = getTotalOrdersForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthOrders = getTotalOrdersForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());
        Long currentMonthEarnings = getTotalEarningsForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthEarnings = getTotalEarningsForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());

        Long placed = orderRepository.countByOrderStatus(OrderStatus.Placed);
        Long shipper = orderRepository.countByOrderStatus(OrderStatus.Shipper);
        Long delivered = orderRepository.countByOrderStatus(OrderStatus.Delivered);

        return new AnalyticsResponse(placed, shipper, delivered, currentMonthOrders, previousMonthOrders,
                currentMonthEarnings, previousMonthEarnings);
    }

    public Long getTotalOrdersForMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);


        Date startMonth = calendar.getTime();

        //переместить календарь на конец указанного месяца
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonth = calendar.getTime();

        List<Order> orders = orderRepository.findByDateBetweenAndStatus(startMonth, endOfMonth, OrderStatus.Delivered);
        return (long) orders.size();
    }

    public Long getTotalEarningsForMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);


        Date startMonth = calendar.getTime();

        //переместить календарь на конец указанного месяца
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonth = calendar.getTime();

        List<Order> orders = orderRepository.findByDateBetweenAndStatus(startMonth, endOfMonth, OrderStatus.Delivered);

        Long sum = 0L;
        for (Order order: orders){
            sum += order.getAmount();
        }
        return sum;

    }

}
