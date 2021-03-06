package com.lbs.nettyserver.service.functionsevice.my;

import java.lang.reflect.Field;
import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.my.MyOptionDAO;
import com.lbs.nettyserver.model.request.my.MyGetPutCommentTodayTotalRequest;
import com.lbs.nettyserver.model.response.my.MyGetPutCommentTodayTotalResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 我的-获取用户今天发表评论的总数接口
 * @author visual
 *
 */
@Service("myGetPutCommentTodayTotalService")
public class MyGetPutCommentTodayTotalService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(MyGetPutCommentTodayTotalService.class);
	
	@Autowired
	private MyOptionDAO myOptionDAO;
	
	private  MyGetPutCommentTodayTotalRequest myGetPutCommentTodayTotalRequest;
	
	private  MyGetPutCommentTodayTotalResponse myGetPutCommentTodayTotalResponse;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.myGetPutCommentTodayTotalRequest = (MyGetPutCommentTodayTotalRequest)JSONObject.toBean(data, MyGetPutCommentTodayTotalRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.myGetPutCommentTodayTotalRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.myGetPutCommentTodayTotalRequest)==null || "".equals(field.get(this.myGetPutCommentTodayTotalRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("我的-获取用户今天发表评论的总数接口checkBizData错误："+e.getMessage());
			resultflag = false;
		}
		return resultflag;
	}

	@Override
	public JSONObject handleBiz(JSONObject data) {
		JSONObject result = new JSONObject();
		ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
		result=responseBodyResultUtil.getParam_error_result("");
		this.myGetPutCommentTodayTotalResponse=new MyGetPutCommentTodayTotalResponse();
		if(checkBizData(data)){
			try{
				this.myGetPutCommentTodayTotalResponse.setPutCommentTodayTotal((BigInteger)myOptionDAO.getPutCommentTodayTotalByUserId(this.myGetPutCommentTodayTotalRequest.getUserId()));	
				result=responseBodyResultUtil.getSuccess_result(this.myGetPutCommentTodayTotalResponse, "获取成功");
			}catch(Exception e){
				result=responseBodyResultUtil.getSys_error_default_result();
				e.printStackTrace();
				logger.error("我的-获取用户今天发表评论的总数接口handleBiz错误："+e.getMessage());
			}
		}
		return result;
	}
}
