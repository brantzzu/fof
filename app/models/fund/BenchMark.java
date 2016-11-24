package models.fund;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.libs.F.T2;

@Entity
@Table(name="t_benchmark_return")
public class BenchMark  extends GenericModel{
	/** 日期 **/
	@Column(name="nav_date")
	public String navDate; 
	
	@Column(name="price")
	public BigDecimal price ;

	/**较前一日增长率 **/
	@Column(name="daily_growth_rate")
	public BigDecimal dailyGrowthRate; 
	
	/**最近一周收益**/
	@Column(name="latest_week_return")
	public BigDecimal latest_week_return;
	
	/**最近一月收益**/
	@Column(name="latest_month_return")
	public BigDecimal latest_month_return;
	
	/**最近3月收益**/
	@Column(name="latest_3month_return")
	public BigDecimal latest_3month_return;
	
	/**最近6月收益**/
	@Column(name="latest_6month_return")
	public BigDecimal latest_6month_return;
	
	/**最近3月收益**/
	@Column(name="latest_year_return")
	public BigDecimal latest_year_return;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    public Long id;
	
	public String benchMarkReturn(String startDate,String endDate){ 
		
		BigDecimal startBenchMark = IndexPrice.find("select t.close from IndexPrice t where indexType = 'hs300' and date_format(t.tdate,'%Y-%m-%d') >=? order by t.tdate asc",startDate).first();
		List<BigDecimal> benchMarkRange = BenchMark.find("select t.close from IndexPrice t where indexType = 'hs300' and date_format(t.tdate,'%Y-%m-%d') >=? and date_format(t.tdate,'%Y-%m-%d') <= ? order by t.tdate asc",startDate,endDate).fetch();
		String benchMarkReturns ="";
		DecimalFormat df = new DecimalFormat("0.000");
		for (int i = 0 ; i< benchMarkRange.size() ; i++){
			String eachBenchMarkReturn = df.format((benchMarkRange.get(i).divide(startBenchMark,4,BigDecimal.ROUND_HALF_UP).doubleValue()-1)*100);
			benchMarkReturns +=  eachBenchMarkReturn + ",";
		}

	    return benchMarkReturns ;
	} 
}



