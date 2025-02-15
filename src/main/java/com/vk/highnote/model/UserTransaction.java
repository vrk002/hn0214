package com.vk.highnote.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usertransactions")
public class UserTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private Long userId;
    private String vendor;
    private String product;
    private Float unitPrice;
    private Float totalPrice;
    private Float quantity;
    private Timestamp invoiceDate;
    @Value("#{T(java.time.Instant).now()}")
    private Timestamp creationDate;
}
