package com.vk.highnote.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usertransactions")
public class UserTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionid")
    private Long transactionId;

    @Column(name = "userid")
    private Long userId;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "product")
    private String product;

    @Column(name = "unitprice")
    private Float unitPrice;

    @Column(name = "totalprice")
    private Float totalPrice;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "invoicedate")
    private Timestamp invoiceDate;

    @Column(name = "creationdate")
    private Timestamp creationDate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = Timestamp.from(Instant.now()); // Sets the default timestamp before persisting
    }
}
