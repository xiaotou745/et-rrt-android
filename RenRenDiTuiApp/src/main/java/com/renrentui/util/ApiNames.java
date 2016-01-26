package com.renrentui.util;

/**
 * api接口名称
 * 
 * @author llp
 * 
 */
public enum ApiNames {
	/**
	 * java后台接口名称
	 */
	获取手机验证码("userc/sendcode"), 
	用户注册("userc/signup"), 
	用户登录("userc/signin"), 
	忘记密码("userc/forgotpwd"), 
	修改密码("userc/modifypwd"), 
	修改用户信息("userc/modifyuserc"), 
	获取所有可领取任务("task/getnewtasklist"),
	获取所有已领取任务("task/getmyreceivedtasklist"), 
	获取所有已提交的任务("task/getsubmittedtasklist"), 
	获取任务详细信息("task/taskdetail"), 
	领取任务("task/gettask"), 
	放弃任务("task/canceltask"), 
	提交任务("task/submittask"), 
	获取用户信息("userc/getuserc"), 
	版本检查更新("common/versioncheck"),
	申请提现("userc/withdraw"),
	获取资料审核列表("taskdatum/getmytaskdatumlist"),
	获取资料模板或模板详情("taskdatum/gettaskdatumdetail"),
	提交任务模板接口("task/submittask"),
	获取城市信息列表("region/gethotregionandall"),
	获取消息列表("msg/getmymsglist"),
	绑定支付宝("userc/bindalipay"),
	删除或将消息已读("msg/updatemsg"),
	获取合伙人分红信息("userc/getpartnerinfo"),
	获取资金明细列表("userc/getbalancerecordlist"),
	资料审核详情分组后列表("taskdatum/getmytaskdatumgrouplist"),
	获取任务参与人列表("userc/getclienterlistbytaskid"),
	获取未读信息数量("msg/getmymsgcount");
	/**
	 * PHP接口名称
	 */
	// 获取手机验证码("User/SendValidateCode"),
	// 用户注册("User/SignUp"),
	// 用户登录("User/SignIn"),
	// 忘记密码("User/ForgetPwd"),
	// 修改密码("User/ModifyPwd"),
	// 获取所有未领取任务("TaskManage/GetNewTaskList"),
	// 获取所有已领取任务("TaskManage/GetMyReceivedTaskList"),
	// 获取所有已提交的任务("TaskManage/GetSubmittedTaskList"),
	// 获取任务详细信息("TaskManage/GetTaskDetail"),
	// 领取任务("TaskManage/ReceiveTask"),
	// 放弃任务("TaskManage/GiveUpTask"),
	// 提交任务("TaskManage/SubmitTask"),
	// 获取用户收入信息("User/MyIncome"),
	// 申请提现("User/Withdraw");
	private final String value;

	ApiNames(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
