package com.kbstar.controller;

import com.kbstar.dto.Item;
import com.kbstar.dto.Member;
import com.kbstar.dto.Order;
import com.kbstar.service.CartService;
import com.kbstar.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;


    @GetMapping("/{userId}")
    public String order(@PathVariable int userId, Model model) {
        List<Item> items = cartService.myCart(userId);
        model.addAttribute("items", items);
        model.addAttribute("center", "checkout");
        return "index";
    }

    @RequestMapping("/orderImpl")
    public String orderPage(Order order, HttpSession session) {
        Member user = (Member) session.getAttribute("loginUser");
        order.setUserId(user.getId());
        orderService.insert(order);
        cartService.deleteCart(user.getId());
        return "redirect:/cart/" + user.getId();
    }

}
