package com.restaurant.entity;

import com.restaurant.enums.BookingStatus;
import com.restaurant.enums.Slot;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
public class BookingTransaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long userId;

    @ManyToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

    private int capacity;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BookingStatus bookingStatus = BookingStatus.BOOKED;

    private LocalDateTime bookingDate;

    @Enumerated(EnumType.STRING)
    private Slot slot;
}
