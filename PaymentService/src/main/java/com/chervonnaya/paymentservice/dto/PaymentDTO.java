package com.chervonnaya.paymentservice.dto;

import com.chervonnaya.paymentservice.model.Payment.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long orderId;
    private Status status;
}
