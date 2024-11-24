package org.example.yogabusinessmanagementweb.controller.admin.order;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.yogabusinessmanagementweb.common.entities.Order;
import org.example.yogabusinessmanagementweb.dto.request.lecture.LectureCreationRequest;
import org.example.yogabusinessmanagementweb.dto.request.order.OrderStatusUpdateRequest;
import org.example.yogabusinessmanagementweb.dto.response.ApiResponse;
import org.example.yogabusinessmanagementweb.dto.response.lecture.LectureResponse;
import org.example.yogabusinessmanagementweb.dto.response.order.OrderResponse;
import org.example.yogabusinessmanagementweb.service.LecturesService;
import org.example.yogabusinessmanagementweb.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/admin")
@Slf4j
public class AdminOrderController {
    OrderService orderService;

    @GetMapping("/get-all-order-of-user")
    public ApiResponse<?> getAllOrder(HttpServletRequest request) {
        List<Order> orderResponse = orderService.showOrderOfUser(request);
        return new ApiResponse<>(HttpStatus.OK.value(), "show order success",orderResponse);
    }
    @PatchMapping("/update-order-status/{orderId}")
    public ApiResponse<?> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusUpdateRequest request) {

        // Cập nhật trạng thái đơn hàng
        Order updatedOrder = orderService.updateOrderStatus(orderId, request.getStatus());

        // Trả về đơn hàng đã cập nhật
        return new ApiResponse<>(HttpStatus.OK.value(), "change status order success",updatedOrder);
    }
}
