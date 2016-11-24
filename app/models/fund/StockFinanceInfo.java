package models.fund;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;

/** 股票财务信息 **/
@Entity
@Table(name="t_stock_finance_info")
public class StockFinanceInfo extends Model {

	/** 股票代码 **/
	@Column(name="stock_code")
	public String code; 
	
	/** 美股净资产 **/
	@Column(name="NET_ASSET_VALUE_PER_SHARE")
	public String netAssetValue;
	
	/** 净资产收益率  **/
	@Column(name="ROE")
	public String roe; 
	
	/** 净利润 _万元 **/
	@Column(name="NET_PROFIT_MILLION")
	public BigDecimal netProfitMillion; 
	
	/** 每股收益 **/
	@Column(name="EPS")
	public BigDecimal eps; 
	
	/** 总股本 **/
	@Column(name="tcap")
	public BigDecimal tcap; 
	
	/** 流通股本 **/
	@Column(name="fcap")
	public BigDecimal fcap; 
}
