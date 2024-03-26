package com.card_inventory.cards.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LOG_REQ_RES")
@ToString
public class LogRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ENDPOINTS")
    private String uri;
    @Column(name = "HTTP-METHOD")
    private String httpMethod;
    @Column(name = "REQUEST")
    private String request;
    @Column(name = "RESPONSE")
    private String response;
    @Column(name = "TIMESTAMP_REQ_RES")
    private LocalDateTime createdOn;
    // Impliment posted by, modified by, verified by
}
