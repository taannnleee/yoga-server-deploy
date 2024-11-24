package org.example.yogabusinessmanagementweb.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.yogabusinessmanagementweb.common.entities.Order;
import org.example.yogabusinessmanagementweb.common.entities.OrderItem;
import org.example.yogabusinessmanagementweb.dto.request.order.OrderCreationRequest;
import org.example.yogabusinessmanagementweb.dto.response.order.OrderCommentResponse;
import org.example.yogabusinessmanagementweb.dto.response.order.OrderCreationResponse;
import org.example.yogabusinessmanagementweb.dto.response.order.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderCreationResponse createOrder(HttpServletRequest request, OrderCreationRequest orderRequest);

    OrderCommentResponse updateCommentInOrderItem(Long orderItemId, Long commentId);
    List<Order> showOrderOfUser(HttpServletRequest request);

    Order updateOrderStatus(Long orderId, String status);

    List<Order> getAllOrderByStatus(HttpServletRequest request, String status);
}
