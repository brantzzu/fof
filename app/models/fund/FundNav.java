package models.fund;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;

/** 基金净值 **/
@Entity
@Table(name="t_fund_nav")
public class FundNav extends Model {

	/** 基金 **/
	@Column(name="FUND_CODE")
	public String fundCode; 
	
	/** 净值日期 **/
	@Column(name="nav_date")
	public String navDate; 
	
	/** 净值 **/
	@Column(name="nav")
	public BigDecimal nav;
	
	/** 累积净值 **/
	@Column(name="ACCUMULATED_NAV")
	public BigDecimal accumulatedNav; 
	
	/**较前一日增长率 **/
	@Column(name="daily_growth_rate")
	public BigDecimal dailyGrowthRate; 
	
	/** 基金份额 **/
	@Column(name="fund_size")
	public BigDecimal fundSize; 
	
	/** 申购状态 **/
	@Column(name="purchase_status")
	public String purchaseStatus; 
	
	/** 赎回状态 **/
	@Column(name="redeem_status")
	public String redeemStatus ; 
	
	/** 是否年化收益 **/
	@Column(name="IS_ANNUALIZED")
	public Boolean annual; 
}
