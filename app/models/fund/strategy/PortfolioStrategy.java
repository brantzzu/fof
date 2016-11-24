package models.fund.strategy;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Model;
import play.libs.F.T2;
import play.libs.F.T5;

@Entity
@Table(name="t_portfolio_strategy")
public class PortfolioStrategy extends Model {
	
	/** 
	 * 策略代码
	 */
	@Column(name="portfolio_strategy_code")
	public String code;
	
	/** 
	 * 策略名称 
	 */
	@Column(name="portfolio_strategy_name")
	public String name; 
	
	/** 
	 * 策略描述
	 */
	@Column(name="strategy_memo")
	public String memo; 

	/** 
	 * 策略类型
	 */
	@Column(name="portfolio_strategy_type")
	public String type; 
	
	
	/** 
	 * 策略包含的产品代码
	 */
	@Column(name="product_code")
	public String productCode; 
	
	/** 
	 * 策略包含的产品名称
	 */
	@Column(name="product_name")
	public String productName; 
	
	
	/** 
	 * 策略提供者
	 */
	@Column(name="strategy_supplier_id")
	public String supplier; 
	
	/** 
	 * 对比指标
	 */
	@Column(name="benchmark_name")
	public String benchmark; 
	
	/**
	 * 在组合中的占比
	 */
	@Column(name="proportion")
	public Double proportion; 
	
	/** 
	 * 不同组合的前缀，股票策略组合为gp，基金组合为fund
	 */
	public static final String GP = "gp"; 
	public static final String FUND = "fund"; 
	
	public boolean isFundPortfolio(){
		return this.code.contains(FUND);  
	}
	
	/** 
	 * 返回 
	 * 1、组合策略代码、
	 * 2、组合策略名称
	 * 3、策略描述
	 * 4、提供人
	 * 5、比较基准
	 * 6、最新净值日期
	 * 7、最新净值
	 * 8、基准净值
	 * @return
	 */
	public static List<Object[]> fundPortfolio(){ 
		String sql = "select "
				+ "s.PORTFOLIO_STRATEGY_CODE,"
				+ "s.portfolio_strategy_name,"
				+ "s.strategy_memo,"
				+ "s.strategy_supplier_id,"
				+ "s.benchmark_name,"
				+ "nav.NAV_DATE,"
				+ "nav.NAV,"
				+ "nav.BENCHMARK_NAV"
				+ " from v_latest_portfolio_nav nav join v_portfolio_strategy s " 
				+ " on nav.PORTFOLIO_STRATEGY_CODE = s.portfolio_strategy_code "; 
		String where = " where s.portfolio_strategy_code like ?  " ; 
		
		Query query = JPA.em().createNativeQuery(sql+where);
		query.setParameter(1, FUND+"%"); 
		
		List<Object[]> portfolioList = query.getResultList();
		return portfolioList; 
	}
	
	/** 
	 * 返回 
	 * 1、组合策略代码、
	 * 2、组合策略名称
	 * 3、策略描述
	 * 4、提供人
	 * 5、比较基准
	 * 6、最新净值日期
	 * 7、最新净值
	 * 8、基准净值
	 * @return
	 */
	public static Object[] fundPortfolio(String code){ 
		String sql = "select "
				+ "s.PORTFOLIO_STRATEGY_CODE,"
				+ "s.portfolio_strategy_name,"
				+ "s.strategy_memo,"
				+ "s.strategy_supplier_id,"
				+ "s.benchmark_name,"
				+ "nav.NAV_DATE,"
				+ "nav.NAV,"
				+ "nav.BENCHMARK_NAV"
				+ " from v_latest_portfolio_nav nav join v_portfolio_strategy s " 
				+ " on nav.PORTFOLIO_STRATEGY_CODE = s.portfolio_strategy_code "; 
		String where = " where s.portfolio_strategy_code like ?  " ; 
		
		Query query = JPA.em().createNativeQuery(sql+where);
		query.setParameter(1, code+"%"); 
		
		Object[] portfolio = (Object[])query.getSingleResult();
		return portfolio; 
	}
	
	/** 
	 * 返回 
	 * 1、组合策略代码、
	 * 2、组合策略名称
	 * 3、策略描述
	 * 4、提供人
	 * 5、比较基准
	 * @return
	 */
	public static List<Object[]> stockPortfolio(){ 
		String sql = "select "
				+ "s.PORTFOLIO_STRATEGY_CODE,"
				+ "s.portfolio_strategy_name,"
				+ "s.strategy_memo,"
				+ "s.strategy_supplier_id,"
				+ "s.benchmark_name,"
				+ "nav.NAV_DATE,"
				+ "nav.NAV,"
				+ "nav.BENCHMARK_NAV"
				+ " from v_latest_portfolio_nav nav join v_portfolio_strategy s " 
				+ " on nav.PORTFOLIO_STRATEGY_CODE = s.portfolio_strategy_code "; 
		String where = " where s.portfolio_strategy_code like ?  " ; 
		
		Query query = JPA.em().createNativeQuery(sql+where);
		query.setParameter(1, GP+"%"); 
		
		List<Object[]> portfolioList = query.getResultList();
		return portfolioList; 
	}
}
