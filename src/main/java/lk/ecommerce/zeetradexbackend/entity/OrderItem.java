package lk.ecommerce.zeetradexbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double quantity;

    private Double buyPrice;

    private Double sellPrice;

    @ManyToOne
    @JoinColumn(name = "coin_id", nullable = false)
    private Coin coin;

    //Create requition problem
    @JsonIgnore
    @OneToOne
    private Order order;

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public Double getQuantity() {
  return quantity;
 }

 public void setQuantity(Double quantity) {
  this.quantity = quantity;
 }

 public Double getBuyPrice() {
  return buyPrice;
 }

 public void setBuyPrice(Double buyPrice) {
  this.buyPrice = buyPrice;
 }

 public Double getSellPrice() {
  return sellPrice;
 }

 public void setSellPrice(Double sellPrice) {
  this.sellPrice = sellPrice;
 }

 public Coin getCoin() {
  return coin;
 }

 public void setCoin(Coin coin) {
  this.coin = coin;
 }

 public Order getOrder() {
  return order;
 }

 public void setOrder(Order order) {
  this.order = order;
 }
}
