package models.fund;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Model;
import play.libs.F.T2;

@Entity
@Table(name="t_index_price")
public class IndexPrice extends Model {
	
	@Column(name="index_type")
	public String indexType; 
	
	@Column(name="tdate")
	public String tdate; 
	
	@Column(name="open")
	public BigDecimal open; 
	
	@Column(name="high")
	public BigDecimal high; 
	
	@Column(name="close")
	public BigDecimal close; 
	
	@Column(name="low")
	public BigDecimal low; 
	
	@Column(name="trade_outstanding")
	public Long tradeOutStanding; 
	
	@Column(name="trade_amount")
	public Long tradeAmount; 
}
