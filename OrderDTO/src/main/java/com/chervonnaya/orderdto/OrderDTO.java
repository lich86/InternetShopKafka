package com.chervonnaya.orderdto;

import java.math.BigDecimal;
import java.util.List;

public class OrderDTO {
    private Long id;
    private List<String> products;
    private Long customerId;
    private String email;
    private BigDecimal sum;
    private String address;
    private String status;

    public OrderDTO(){

    }

    public OrderDTO(Long id, List<String> products, Long customerId, String email, BigDecimal sum, String address, String status) {
        this.id = id;
        this.products = products;
        this.customerId = customerId;
        this.email = email;
        this.sum = sum;
        this.address = address;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + id +
            ", products=" + products +
            ", customerId=" + customerId +
            ", email='" + email + '\'' +
            ", sum=" + sum +
            ", address='" + address + '\'' +
            ", status='" + status + '\'' +
            '}';
    }
}


