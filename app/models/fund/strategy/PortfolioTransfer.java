package models.fund.strategy;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Model;

@Entity
@Table(name="t_portfolio_strategy_transfer")
public class PortfolioTransfer extends Model {
	
	/** 
	 * 交易日期
	 */
	@Column(name="tdate")
	public String date;  
	
	/** 
	 * 策略代码
	 */
	@Column(name="portfolio_strategy_code")
	public String strategyCode; 
	
	/** 
	 * 交易品种
	 */
	@Column(name="product_code")
	public String productCode; 
	
	/** 
	 * 交易品种名称
	 */
	@Column(name="product_name")
	public String productName; 
	
	/** 
	 * 买卖方向
	 */
	@Column(name="bs_flag")
	public String flag; 
	
	/** 
	 * 价格
	 */
	@Column(name="price")
	public BigDecimal price; 
	
	/** 
	 * 买卖比例
	 */
	@Column(name="trade_proportion")
	public Double proportion;  
	
	
	public static long transferTimes(String code){ 
		List<Object[]> ls = PortfolioTransfer.find("select date,count(*) from PortfolioTransfer v "
				+ "where v.strategyCode=? group by v.date", code).fetch();
		return ls.size(); 
	}
	
	public static List<PortfolioTransfer> allTransfer(String code){ 
		return PortfolioTransfer.find("strategyCode=? order by date desc", code).fetch(); 
	}
}
