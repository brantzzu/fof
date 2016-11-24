-- 创建策略组合视图
create view v_portfolio_strategy as select distinct portfolio_strategy_code,portfolio_strategy_name,
strategy_memo,strategy_supplier_id,benchmark_name from t_portfolio_strategy order by id asc; 

-- 创建最后的净值信息数据
create view v_portfolio_latestdate as select PORTFOLIO_STRATEGY_CODE,max(nav_date) d 
from t_portfolio_strategy_nav group by PORTFOLIO_STRATEGY_CODE;

create view v_latest_portfolio_nav as select v.* from t_portfolio_strategy_nav v ,
 v_portfolio_latestdate u 
 where v.PORTFOLIO_STRATEGY_CODE = u.PORTFOLIO_STRATEGY_CODE and v.NAV_DATE = u.d;
