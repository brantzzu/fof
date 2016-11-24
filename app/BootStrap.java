import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;

import models.fund.BuySaleEnum;
import models.fund.FundBasicInfo;
import models.fund.FundPortfolio;
import models.fund.FundPortfolioResult;
import models.fund.FundPortfolioType;
import models.fund.UserType;
import models.fund.strategy.FundPortfolioOptNav;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class BootStrap extends Job{
	
	/*public void doJob() { 
		if(FundType.count()==0) { 
			addFundType(); 
		}
		
		FundType radical = FundType.find("code=?", "radical").first();
		FundType conservative = FundType.find("code=?", "conservative").first();
		FundType sound = FundType.find("code=?", "sound").first();
		
		if(FundPortfolio.count()==0){ 
			add10Portfolios(radical); 
			add10Portfolios(conservative); 
			add10Portfolios(sound); 
		}
		
//		if(UserType.count()==0){ 
//			add3UserType(); 
//		}
	}

	private void addFundType() {
		FundType radical = new FundType(); 
		radical.code = "radical"; 
		radical.name = "激进型"; 
		radical.status = "正常"; 
		radical.save(); 
		
		FundType conservative = new FundType(); 
		conservative.code = "conservative"; 
		conservative.name = "保守型"; 
		conservative.status = "正常"; 
		conservative.save(); 
		
		FundType sound = new FundType(); 
		sound.code = "sound"; 
		sound.name = "稳健型"; 
		sound.status = "正常"; 
		sound.save(); 
	}

	private void add10Portfolios(FundType type) {
		
		for(int i=0;i<10;i++) { 
			FundPortfolio portfolio = new FundPortfolio(); 
			portfolio.name = type.name+RandomUtils.nextInt(100); 
			portfolio.supplierName = "吴先斌"+i;
			portfolio.supplierCode = "WXB"+i; 
			portfolio.intro = "该基金组合为激进型组合，主要配置为股票"; 
			portfolio.type = type;
			portfolio.save(); 
			add10PortfolioResult(portfolio); 
			add30PortfolioNav(portfolio); 
		}
		
	}

	private void add30PortfolioNav(FundPortfolio portfolio) {
		DateTime now = DateTime.now(); 
		
		for(int i=0;i<30;i++) { 
			FundPortfolioOptNav nav = new FundPortfolioOptNav(); 
			nav.nav = new BigDecimal(RandomUtils.nextDouble());
			nav.portfolio = portfolio; 
			nav.navDate = now.minusDays(i).toString("yyyy-MM-dd"); 
			nav.dailyGrowthRate = new BigDecimal(RandomUtils.nextDouble()); 
			nav.save(); 
		}
	}

	private void add10PortfolioResult(FundPortfolio portfolio) {
		
		int start = RandomUtils.nextInt(300); 
		
		List<FundBasicInfo> infos = FundBasicInfo.all().from(start).fetch(10);
		for(FundBasicInfo info : infos){ 
			FundPortfolioResult result = new FundPortfolioResult(); 
			result.fund = info; 
			result.optDate = DateTime.now().toString("yyyyMMdd"); 
			result.buySaleFlag = RandomUtils.nextBoolean()?BuySaleEnum.BUY:BuySaleEnum.SALE;
			result.proportionNav = new BigDecimal(RandomUtils.nextDouble());
			result.portfolio = portfolio; 
			result.save(); 
		}
	}

	private void add3UserType() {
		UserType radical = new UserType(); 
		radical.name = "激进型";
		radical.code = "Radical"; 
		radical.save(); 
		
		UserType conservative = new UserType(); 
		conservative.name = "保守型";
		conservative.code = "Conservative"; 
		conservative.save(); 
		
		UserType sound = new UserType(); 
		sound.name = "稳健型";
		sound.code = "Sound"; 
		sound.save(); 
		
	}*/
}
