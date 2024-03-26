package com.card_inventory.cards.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table
@Data
public class Cards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long cardId;
    @Column(length = 100, nullable = false)
    private int batchId;
    @Column(length = 100,nullable = false)
    private String cardRange;
    @Column(length = 100,nullable = false)
    private int quantityReceived;
    @Column(length = 100,nullable = false)
    private Date expiryDate;
    @Column(length = 100,nullable = false)
    private String vendorName;
    @Column(length = 100,nullable = false)
    @CreatedDate
    private LocalDateTime receivedDate;
    @Column(length = 100,nullable = false)
    private String stockStatus;
    @Column(length = 100,nullable = false)
    private String cardType;

}
