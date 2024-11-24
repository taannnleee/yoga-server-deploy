package org.example.yogabusinessmanagementweb.common.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Promotion")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Promotion  extends AbstractEntity<Long> implements Serializable {
    Date endDate;
    Date startDate;
    int percent;

    @OneToOne()
    Product product;
}
