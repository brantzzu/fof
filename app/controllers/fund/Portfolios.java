package controllers.fund;

import java.util.List;

import models.fund.FundPortfolio;
import models.fund.FundPortfolioType;
import models.fund.strategy.FundPortfolioOptNav;
import models.fund.strategy.PortfolioStrategy;
import models.fund.strategy.PortfolioTransfer;
import play.modules.router.Get;
import play.mvc.Controller;
import services.FundPortfolioService;

/** 
 * 展示基金组合信息
 */
public class Portfolios extends Controller{

	@Get("/portfolio/list")
	public static void list(){ 
		List<Object[]> portofolios = PortfolioStrategy.fundPortfolio();
		render(portofolios); 
	}
	
	@Get("/gpPortfolio/list")
	public static void gpList(){ 
		List<Object[]> portofolios = PortfolioStrategy.stockPortfolio();
		render(portofolios); 
	}
	
	@Get("/portfolio/detail/{code}")
	public static void detail(String code){ 
		Object[] portfolio = PortfolioStrategy.fundPortfolio(code);
		Double[] growth = FundPortfolioService.strategyPortfolioGrowth(code); 
		List<PortfolioStrategy> products = PortfolioStrategy.find("code=?", code).fetch(); 
		List<FundPortfolioOptNav> navs = FundPortfolioOptNav.allNavs(code); 
		
		render(products,portfolio,growth,navs); 
	}
	
	@Get("/portfolio/transfer/{code}")
	public static void transfer(String code){ 
		long times = PortfolioTransfer.transferTimes(code); 
		List<PortfolioTransfer> transfers = PortfolioTransfer.allTransfer(code); 
		render(times,transfers); 
	}
	
}
