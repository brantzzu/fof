package models.fund;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;

@Entity
@Table(name="t_fund_holding")
public class FundHolding extends Model{
	
	/** 基金 **/
	@Column(name="fund_code")
	public String fundCode; 
	
	/** 股票代码**/
	@Column(name="stock_code")
	public String stockCode; 
	
	/** 股票名称 **/
	@Column(name="stock_name")
	public String stockName; 
	
	/** 占净值比例 **/
	@Column(name="PROPORTION_OF_NAV")
	public BigDecimal proportionOfNav; 
	
	/** 持股数_万股 **/
	@Column(name="HOLDINGS_MILLION")
	public BigDecimal holdingsMillion; 
	
	/** 持仓市值_万元 **/
	@Column(name="HOLDING_MCAP_MILLION")
	public BigDecimal holdingsCapital; 

	/** 报告年份 **/
	@Column(name="report_year")
	public int reportYear ; 
	
	/** 报告季度 **/
	@Column(name="REPORT_QUARTER")
	public int reportQuarter; 
}
