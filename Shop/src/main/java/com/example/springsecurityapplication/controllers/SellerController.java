package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.repositories.OrderRepository;
import com.example.springsecurityapplication.security.PersonDetails;
import com.example.springsecurityapplication.services.OrderService;
import com.example.springsecurityapplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/seller")
public class SellerController {

    private final ProductService productService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Autowired
    public SellerController(ProductService productService,OrderService orderService,
                            OrderRepository orderRepository) {
        this.productService = productService;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    //метод по отмене заказа
    @GetMapping("/order/Cansel/{id}")
    public String updateOrderCansel(@ModelAttribute("orders") Order order, @PathVariable("id") int id){
        Order order_status = orderService.getOrderById(id);
        orderService.updateOrderCansel(order_status);
        return "redirect:/seller/ordersUsers";
    }

    //метод заказ принят
    @GetMapping("/order/Accept/{id}")
    public String updateOrderAccept(@ModelAttribute("orders") Order order, @PathVariable("id") int id){
        Order order_status = orderService.getOrderById(id);
        orderService.updateOrderAccept(order_status);
        return "redirect:/seller/ordersUsers";
    }
    //метод заказ оформлен
    @GetMapping("/order/Register/{id}")
    public String updateOrderRegister(@ModelAttribute("orders") Order order, @PathVariable("id") int id){
        Order order_status = orderService.getOrderById(id);
        orderService.updateOrderRegister(order_status);
        return "redirect:/seller/ordersUsers";
    }

    //метод заказ ожидает
    @GetMapping("/order/Expect/{id}")
    public String updateOrderExpect(@ModelAttribute("orders") Order order, @PathVariable("id") int id){
        Order order_status = orderService.getOrderById(id);
        orderService.updateOrderExpect(order_status);
        return "redirect:/seller/ordersUsers";
    }

    //метод заказ Получен
    @GetMapping("/order/Get/{id}")
    public String updateOrderGet(@ModelAttribute("orders") Order order, @PathVariable("id") int id){
        Order order_status = orderService.getOrderById(id);
        orderService.updateOrderGet(order_status);
        return "redirect:/seller/ordersUsers";
    }
    @GetMapping("/ordersUsers")
    public String ordersUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<Order> orderList = orderRepository.findByPerson(personDetails.getPerson());
        model.addAttribute("orders", orderList);
        return "/user/orders";
    }


    @GetMapping()
    public String seller(Model model){

        // Получае объект аутентификации - > c помощью SecurityContextHolder обращаемся к контексту и на нем вызываем метод аутентификации. По сути из потока для текущего пользователя мы получаем объект, который был положен в сессию после аутентификации пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Преобразовываем объект аутентификации в специальный объект класса по работе с пользователями
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        String role = personDetails.getPerson().getRole();
        if(role.equals("ROLE_ADMIN")){
            return "redirect:/admin";
        }
        else if (role.equals("ROLE_USER")) {
            return "redirect:/index";
        }
            model.addAttribute("products", productService.getAllProduct());
            return "seller/seller";
        }

}
