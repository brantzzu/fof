package services;

import java.util.List;

import org.joda.time.DateTime;

import models.fund.FundPortfolio;
import models.fund.UserCollection;
import models.fund.UserProfile;
import play.Logger;

public class ProfileService {

	/** 
	 * 用户收藏的所有基金组合
	 * @param user
	 * @return
	 */
	public static List<UserCollection> userCollections(UserProfile user) { 
		return UserCollection.find("user=?", user).fetch();
	}
	
	public static boolean isUserCollected(UserProfile user,long id) { 
		return UserCollection.count("user=? and portfolio.id=?", user,id)>0; 
	}
	
	/** 
	 * 保存用户选择收藏的基金组合
	 * @param user
	 * @param id 基金组合id
	 * @return
	 */
	public static boolean saveUserCollection(UserProfile user , long id){ 
		if(isUserCollected(user, id)){ 
			return false; 
		}else { 
			UserCollection userCollection = new UserCollection(); 
			userCollection.user = user; 
			userCollection.portfolio = FundPortfolio.findById(id); 
			userCollection.collectionTime = DateTime.now().toDate(); 
			try{ 
				userCollection.save();
				return true; 
			}catch(Exception e){ 
				Logger.error(e.getMessage());
				return false; 
			}
		}
	}
}
