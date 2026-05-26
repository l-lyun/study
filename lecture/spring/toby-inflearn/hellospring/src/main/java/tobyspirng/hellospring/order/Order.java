package tobyspirng.hellospring.order;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	// Id는 전체 하나 구별하도록 식별자
	@Id @GeneratedValue
	private Long id;

	@Column(unique = true)
	private String no;

	private BigDecimal total;

	public Order() {
	}

	public Order(String no, BigDecimal total) {
		this.no = no;
		this.total = total;
	}

	public Long getId() {
		return id;
	}

	public String getNo() {
		return no;
	}

	public BigDecimal getTotal() {
		return total;
	}

	@Override
	public String toString() {
		return "Order{" +
			"id=" + id +
			", no='" + no + '\'' +
			", total=" + total +
			'}';
	}
}
