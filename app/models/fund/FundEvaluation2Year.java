package models.fund;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;

/** 
 * 基金评价信息，2年数据
 * @author sunqw
 *
 */
@Entity
@Table(name="t_fund_evaluation_result_2year")
public class FundEvaluation2Year extends Model{
	
	/** 
	 * 开始时间
	 */
	@Column(name="start_date")
	public String startDate; 
	
	/**
	 * 结束时间
	 */
	@Column(name="end_date")
	public String endDate; 
	
	/** 
	 * 基金
	 */
	@Column(name="fund_code")
	public String fundCode; 
	
	/** 
	 * 总收益
	 */
	@Column(name="total_profit")
	public double totalProfit; 
	
	/** 
	 * 选股收益
	 */
	@Column(name="selectivity")
	public double selectivity;
	
	/** 
	 * 择时收益
	 */
	@Column(name="timing")
	public double timing;
	
	/** 
	 * 被动持有收益
	 */
	@Column(name="passive_holding")
	public double passiveHolding; 
	
	
    /** 
     * 总风险
     */
	@Column(name="total_risk")
	public double totalRisk; 
	
	/**
	 *  非系统性风险
	 */
	@Column(name="unsystematic_risk")
	public double nonSystematicRisk; 
	
	/** 
	 * 系统性风险
	 */
	@Column(name="systematic_risk")
	public double systematicRisk; 
	
	/** 
	 * 被动持有风险
	 */
	@Column(name="passive_holding_risk")
	public double passiveHoldingRisk; 
	
	/** 
	 * 择时风险
	 */
	@Column(name="timing_risk")
	public double timingRisk; 
	
	/** 
	 * 其他风险_95%VAR
	 */
	@Column(name="other_risk_var")
	public double otherRisk; 
	
	/**
	 * 其他风险_最大回撤比例
	 */
	@Column(name="other_risk_maxdown")
	public double otherRiskMaxdown; 
	
	/** 
	 * 风险调整后收益_alpha收益
	 */
	@Column(name="alpha_profit")
	public double alphaProfit; 
	
	/** 
	 * 风险调整后收益_夏普比
	 */
	@Column(name="sharpe_ratio")
	public double sharpeRatio; 
	
	/** 
	 * 风险调整后收益_信息比
	 */
	@Column(name="inform_ratio")
	public double inforRatio; 
	
	/** 
	 * 风险调整后收益_收益回撤比
	 */
	@Column(name="ret_to_drawndown")
	public double drawnDown; 
}
