package org.example.yogabusinessmanagementweb.repositories;

import org.example.yogabusinessmanagementweb.common.Enum.EStatusOrder;
import org.example.yogabusinessmanagementweb.common.entities.Order;
import org.example.yogabusinessmanagementweb.common.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);

    @Query(value = "SELECT * FROM Orders o WHERE o.status_order = :status AND o.user_id = :userId", nativeQuery = true)
    List<Order> findAllByStatusOrderAndUserId(@Param("status") String status, @Param("userId") Long userId);
}

