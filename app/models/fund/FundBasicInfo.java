package models.fund;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import org.joda.time.DateTime;
import org.joda.time.JodaTimePermission;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import play.libs.F.T2;
import utils.DateUtil;


@Entity
@Table(name="t_fund_basic_info")
public class FundBasicInfo extends GenericModel{
	
	/** 基金名称 **/
	@Column(name="fund_name")
	public String name; 
	
	/** 基金简称 **/
	@Column(name="fund_abbr")
	public String abbr; 
	
	/** 基金代码 **/
	@Column(name="fund_code")
	public String code; 
	
	/** 基金类型代码**/
	@Column(name="fund_type_code")
	public String fundTypeCode; 
	
	/** 基金投资类型, 一级分类  **/
	@Column(name="FUND_INVERSTMENT_TYPE_1")
	public String investmentType1 ;
	
	/** 基金投资类型 2，二级分类  **/
	@Column(name="FUND_INVERSTMENT_TYPE_2")
	public String investmentType2 ; 
	
	/** 发行日期 **/
	@Column(name="PUBLISH_DATE")
	public String publishDate; 
	
	/** 成立日期 **/
	@Column(name="ESTABLE_DATE")
	public String establishDate; 
	
	/** 成立规模 **/
	@Column(name="ESTABLE_SCALE")
	public String establishScale; 
	
	/** 基金状态 **/
	@Column(name="FUND_STATUS")
	public String status; 
	
	/** 发行日期 **/
	@Column(name="START_DATE")
	public String startDate; 
	
	/** 成立日期 **/
	@Column(name="END_DATE")
	public String endDate; 
	
	/** 最高申购费率 **/
	@Column(name="MAXIMUM_PURCHASE_RATE",precision=30,scale=2)
	public BigDecimal maximumPurchaseRate; 
	
	/** 最高赎回费率 **/
	@Column(name="MAXIMUM_REDEMPTION_RATE",precision=30,scale=2)
	public BigDecimal maximumRedemptionRate; 
	
	/** 最低申购折扣 **/
	@Column(name="FUND_MINPURCHASEDDISCOUNT",precision=30,scale=2)
	public BigDecimal minPurchaseDiscount; 
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="fund_id")
    public Long id;
	
	/** 
	 * 现任基金经理
	 * @return
	 */
	public List<FundManager> currentManagers(){ 
		return ManagerTenure.find("select manager from ManagerTenure "
				+ " where fund=? and tenureEnd like ?", this,"至今%").fetch(); 
	}
	
	/** 
	 * 收益率计算方法：
	 * nav2*(1-赎回费率)/nav1*(1+申购费率) - 1 
	 */
	private double returnRate(BigDecimal startNav,BigDecimal endNav){
		BigDecimal purcharsRate = this.maximumPurchaseRate; 
		BigDecimal redemptionRate = this.maximumRedemptionRate; 
		
		BigDecimal y2 = new BigDecimal(100).subtract(redemptionRate).divide(new BigDecimal(100));
		BigDecimal y1 = new BigDecimal(100).add(purcharsRate).divide(new BigDecimal(100));
		
		BigDecimal rate = endNav.multiply(y2).divide(startNav,2).divide(y1,2).subtract(new BigDecimal(1));
		return rate.doubleValue(); 
	}

	private T2<FundNav,FundNav> getStartAndEndNav(String startDate,String endDate){ 
		FundNav endNav = FundNav.find("fundCode=? and navDate<=? and navDate>=? order by navDate desc",
				this.code,endDate,startDate)
				.first();
				
		FundNav startNav = FundNav.find("fundCode=? and navDate<=? and navDate>=? order by navDate asc",
				this.code,endDate,startDate)
						.first();
		return new T2(startNav,endNav); 
	}
	
	/** 
	 * 该基金近一月的收益率
	 * @return
	 */
	public double latestMonthProfit(){
		T2<String,String> range = DateUtil.latestOneMonth(); 
		
		T2<FundNav,FundNav> t2 = getStartAndEndNav(range._1,range._2); 
		
		return returnRate(t2._1.nav, t2._2.nav); 
	}
	
	/** 
	 * 最近三个月的收益率 
	 * @return
	 */
	public double latestQuaterProfit() { 
		T2<String,String> range = DateUtil.latestQuarter(); 
		
		T2<FundNav,FundNav> t2 = getStartAndEndNav(range._1,range._2); 
		
		return returnRate(t2._1.nav, t2._2.nav); 
	}

	/** 
	 * 近半年的收益率
	 */
	public double latestHalfYearProfit() { 
		T2<String,String> range = DateUtil.latestHalfYear(); 
		
		T2<FundNav,FundNav> t2 = getStartAndEndNav(range._1,range._2); 
		
		return returnRate(t2._1.nav, t2._2.nav); 
	}
	
	/** 
	 * 近一年的收益率
	 * @return
	 */
	public double latestYearProfit() { 
		
		T2<String,String> year = DateUtil.latestYear(); 
		
		T2<FundNav,FundNav> t2 = getStartAndEndNav(year._1,year._2); 
		
		return returnRate(t2._1.nav, t2._2.nav); 
	}
	
	/** 
	 * 近1年的净值信息
	 * @return
	 */
	public T2<String,String> latestYearNav() {
		T2<String,String> range = DateUtil.latestYear(); 
		
		return rangeNav(range._1,range._2); 
	}
	/** 
	 * 近半年的净值信息
	 * @return
	 */
	public T2<String,String> latestHalfYearNav(){ 
		T2<String,String> range = DateUtil.latestHalfYear(); 
		
		return rangeNav(range._1,range._2); 
	}
	
	/** 
	 * 近3月的净值数据
	 * @return
	 */
	public T2<String,String> latestQuaterNav(){ 
		T2<String,String> range = DateUtil.latestQuarter(); 
		
		return rangeNav(range._1,range._2); 
	}
	
	/** 
	 * 近一月的净值列表数据
	 * @return
	 */
	public T2<String,String> latestOneMonthNav(){ 
		T2<String,String> range = DateUtil.latestOneMonth(); 
		
		return rangeNav(range._1,range._2); 
	}
	
	private T2<String,String> rangeNav(String startDate,String endDate){ 
		Query query = JPA.em().createNativeQuery("select nav_date,accumulated_nav from t_fund_nav where FUND_CODE=? and "
				+ " nav_date<=? and nav_date>=? order by nav_date asc");
		query.setParameter(1, this.code); 
		query.setParameter(2, endDate); 
		query.setParameter(3, startDate); 
		
		List<Object[]> navList = query.getResultList();
		int size = navList.size(); 
		String dates="",navs = ""; 
		for(int i=0;i<size;i++){
			Object[] t2= navList.get(i); 

			dates += "'"+t2[0]+"',"; 
			navs += t2[1]+",";
		}
		
		T2<String,String> t2 = new T2(dates,navs); 
		return t2; 
	}
	
	/** 
	 * 基金最新报告的持仓情况
	 * @return
	 */
	public T2<FundHolding,List<FundHolding>> latestFundHolding(){ 
		FundHolding t =  FundHolding.find("fundCode=? order by reportYear desc,reportQuarter desc", this.code).first();
		List<FundHolding> list= FundHolding.find("fundCode=? and reportYear=? and reportQuarter=? order by reportYear desc,reportQuarter desc",
				this.code,t.reportYear,t.reportQuarter).fetch(); 
		return new T2(t,list); 
	}
	
	/** 
	 * 基金的最新净值
	 * @return
	 */
	public FundNav latestNav() { 
	  Query query = JPA.em().createNativeQuery("select * from t_fund_nav t where t.fund_code=?1 order by NAV_DATE desc limit 1", FundNav.class);
	  query.setParameter(1, this.code); 
	  List<FundNav> list = query.getResultList(); 
	  if(list.size()>0){ 
		  return (FundNav)query.getSingleResult();
	  }
	  return null; 
	}
	
	
	/** 
	 * 基金评价1年
	 * @return
	 */
	public FundEvaluation1Year evaluation1Year(){ 
		return FundEvaluation1Year.find("fundCode=?", this.code).first(); 
	}
	
	/** 
	 * 基金评价2年
	 * @return
	 */
	public FundEvaluation2Year evaluation2Year(){ 
		return FundEvaluation2Year.find("fundCode=?", this.code).first(); 
	}
	
	/** 
	 * 基金评价3年
	 * @return
	 */
	public FundEvaluation3Year evaluation3Year(){ 
		return FundEvaluation3Year.find("fundCode=?", this.code).first(); 
	}
	
	public FundStar fundStar(){ 
		return FundStar.find("fundCode=?", this.code).first(); 
	}
}
