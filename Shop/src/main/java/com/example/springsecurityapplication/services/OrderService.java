package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderById(int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    // Данный метод позволяет обновить инфо о заказе принят
    @Transactional
    public void updateOrderAccept(Order order ){
        order.setStatus(Status.Принят);
        orderRepository.save(order);
    }
    // Данный метод позволяет обновить инфо о заказе Оформлен
    @Transactional
    public void updateOrderRegister(Order order ){
        order.setStatus(Status.Оформлен);
        orderRepository.save(order);
    }
    // Данный метод позволяет обновить инфо о заказе Ожидает
    @Transactional
    public void updateOrderExpect(Order order ){
        order.setStatus(Status.Ожидает);
        orderRepository.save(order);
    }
    // Данный метод позволяет обновить инфо о заказе Получен
    @Transactional
    public void updateOrderGet(Order order ){
        order.setStatus(Status.Получен);
        orderRepository.save(order);
    }
    @Transactional
    public void updateOrderCansel(Order order ){
        order.setStatus(Status.Отменен);
        orderRepository.save(order);
    }
}