package models.fund;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;;

/** 
 * 用户购买的基金组合
 *
 */
@Entity
@Table(name="t_user_capital")
public class UserCapital extends Model {
	
	/**
	 *  用户
	 */
	@ManyToOne
	@JoinColumn(name="user_id")
	public UserProfile user; 
	
	/** 购买的基金组合 **/
	@ManyToOne
	@JoinColumn(name="portfolio_id")
	public FundPortfolio portfolio; 
	
	/** 
	 * 购买时间
	 */
	@Column(name="buy_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date buyTime; 
	
	/** 
	 * 购买时净值
	 */
	@Column(name="buy_nav")
	public BigDecimal buyNav; 
}
