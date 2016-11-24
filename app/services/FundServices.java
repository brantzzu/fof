package services;

import java.util.List;

import javax.persistence.Query;

import models.fund.FundBasicInfo;
import models.fund.FundManager;
import models.fund.ManagerTenure;
import play.Play;
import play.db.jpa.JPA;
import play.libs.F.T3;
import play.templates.JavaExtensions;

public class FundServices {
	
	/** 
	 * 所有的基金类型
	 * @return
	 */
	public static List<String> fundInvestmentTypes(){ 
		Query query = JPA.em().createNativeQuery("select distinct FUND_INVERSTMENT_TYPE_1 from t_fund_basic_info"); 
		List<String> types = query.getResultList();
		return types; 
	}
	
	
	/**
	 * 获取指定类型的，从指定页开始的基金信息
	 * @param type
	 * @param page
	 * @return T3<数据、总条数、总页数>
	 */
	public static T3<List<FundBasicInfo>, Long, Integer> fundDatas(String type,int page) { 
		int pageSize = utils.PlayConfigurations.pageSize; 
		
		List<FundBasicInfo> infos = FundBasicInfo.find("from FundBasicInfo t where t.investmentType1=? ",type)
				.from((page)*pageSize).fetch(pageSize);
    	long count = FundBasicInfo.count(); 
    	
    	int pages = JavaExtensions.page(count, pageSize); 
    	
    	return new T3<List<FundBasicInfo>,Long,Integer>(infos,count,pages); 
	}
	
	public static FundBasicInfo getFundByCode(String fundCode) { 
		return FundBasicInfo.find("code=?", fundCode).first(); 
	}
	
	public static List<FundManager> getManagerByCode(String fundCode) {
		return ManagerTenure.find("select t.manager from ManagerTenure t "
				+ "where t.fundCode=? and t.tenureEnd=?",fundCode,"至今").fetch();
		
	}
	
}
