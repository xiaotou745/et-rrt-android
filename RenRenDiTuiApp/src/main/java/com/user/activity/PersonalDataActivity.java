package com.user.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import base.BaseActivity;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.renrentui.app.R;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQUpdateUserinfo;
import com.renrentui.requestmodel.RQUserId;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.RSBase;
import com.renrentui.resultmodel.RSUploadImage;
import com.renrentui.resultmodel.RSUserInfo;
import com.renrentui.tools.FileUtils;
import com.renrentui.tools.SDCardUtils;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiConstants;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.TimeUtils;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.renrentui.view.city.CityMode;
import com.user.model.CircleImageview;
import com.user.model.HttpRequest.HttpRequestListener;
import com.user.model.ImageUploadAsyncTask;
import com.user.service.ChooseSexDialog;
import com.user.service.EditDialog;
import com.user.service.EditDialog.ExitDialogListener;
import com.wheelUtils.TimePopwindow;

/**
 * 个人资料界面
 * 
 * @author llp
 * 
 */
public class PersonalDataActivity extends BaseActivity implements
		OnClickListener, HttpRequestListener {

	private RelativeLayout layout_user_icon;// 用户头像
	private CircleImageview iv_user_icon_show;
	private RelativeLayout layout_user_name;// 用户姓名
	private TextView tv_user_name_show;
	private RelativeLayout layout_user_phone;// 用户电话
	private TextView tv_user_phone_show;
	private RelativeLayout layout_user_sex;// 用户性别
	private TextView tv_user_sex_show;
	private RelativeLayout layout_user_age;// 用户年龄
	private TextView tv_user_age_show;
	private RelativeLayout layout_user_birthday;// 用户年龄
	private TextView tv_user_birthday_show;
	private CityMode area;// 地址实体类对象

	Button carema, album, give_up;// 点击用户头像弹出层按钮
	public static String fileName;
	private BitmapUtils bitmapUtils;
	public String str_pickPath = "";
	private boolean networknotvalide = true;// 判断网络是否正常

	private RQHandler<RSUserInfo> rqHandler_userinfo = new RQHandler<RSUserInfo>(
			new IRqHandlerMsg<RSUserInfo>() {

				@Override
				public void onBefore() {
					// TODO Auto-generated method stub
				}

				@Override
				public void onNetworknotvalide() {
					// TODO Auto-generated method stub
					networknotvalide = false;
				}

				@SuppressWarnings("deprecation")
				@Override
				public void onSuccess(RSUserInfo t) {
					if (Util.IsNotNUll(t.data.fullHeadImage)) {
						str_pickPath= t.data.headImage;
						ImageLoadManager.getLoaderInstace().disPlayNormalImg(
								t.data.fullHeadImage, iv_user_icon_show,
								R.drawable.icon_user_default);
					} else {
						iv_user_icon_show.setImageResource(R.drawable.icon_user_default);
					}
					tv_user_name_show.setText(t.data.clienterName);
					tv_user_phone_show.setText(t.data.phoneNo);
					// tv_user_address_show.setText(t.data.cityName);
					if (t.data.sex.equals("1"))
						tv_user_sex_show.setText("男");
					else if (t.data.sex.equals("2"))
						tv_user_sex_show.setText("女");
					else
						tv_user_sex_show.setText("保密");
					tv_user_age_show.setText(t.data.age);
					networknotvalide = true;
					if(!TextUtils.isEmpty(t.data.birthDay)){
						tv_user_birthday_show.setText(TimeUtils.StringPattern(t.data.birthDay,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"));
					}
				}

				@Override
				public void onSericeErr(RSUserInfo t) {
					// TODO Auto-generated method stub
					ToastUtil.show(context, t.msg);
					networknotvalide = false;
				}

				@Override
				public void onSericeExp() {
					// TODO Auto-generated method stub
					networknotvalide = false;
				}
			});

	private RQHandler<RSBase> rqHandler_updateUserInfo = new RQHandler<RSBase>(
			new IRqHandlerMsg<RSBase>() {

				@Override
				public void onBefore() {
					// TODO Auto-generated method stub
				}

				@Override
				public void onNetworknotvalide() {
					// TODO Auto-generated method stub
					ToastUtil.show(context, "网络错误");
				}

				@Override
				public void onSuccess(RSBase t) {
					ToastUtil.show(context, "保存成功！！");
					finish();
				}

				@Override
				public void onSericeErr(RSBase t) {
					// TODO Auto-generated method stub
					if (t != null)
						ToastUtil.show(context, t.msg);
					else
						ToastUtil.show(context, "网络错误");
				}

				@Override
				public void onSericeExp() {
					// TODO Auto-generated method stub
					ToastUtil.show(context, "网络错误");
				}
			});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_data);
		bitmapUtils = new BitmapUtils(this);
		super.init();
		initControl();
		//getData();
	}
	@Override
	protected void onStart() {
		super.onStart();
		getData();
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		ApiUtil.Request(new RQBaseModel<RQUserId, RSUserInfo>(context,
				new RQUserId(Utils.getUserDTO(context).data.userId),
				new RSUserInfo(), ApiNames.获取用户信息.getValue(), RequestType.POST,
				rqHandler_userinfo));
	}

	/**
	 * 初始化控件
	 */
	private void initControl() {

		if(mIV_title_left!=null){
			mIV_title_left.setVisibility(View.VISIBLE);
			mIV_title_left.setOnClickListener(this);
		}
		if(mTV_title_content!=null){
			mTV_title_content.setText("个人资料");
		}
		if(mTV_title_right!=null){
			mTV_title_right.setVisibility(View.VISIBLE);
			mTV_title_right.setText("保存");
			mTV_title_right.setOnClickListener(this);
		}

		layout_user_icon = (RelativeLayout) findViewById(R.id.layout_user_icon);
		layout_user_icon.setOnClickListener(this);
		// iv_user_icon_show = (CircleImageview)
		// findViewById(R.id.iv_user_icon_show);
		iv_user_icon_show = (CircleImageview) findViewById(R.id.iv_user_icon_show);
		layout_user_name = (RelativeLayout) findViewById(R.id.layout_user_name);
		layout_user_name.setOnClickListener(this);
		tv_user_name_show = (TextView) findViewById(R.id.tv_user_name_show);
		layout_user_phone = (RelativeLayout) findViewById(R.id.layout_user_phone);
		layout_user_phone.setOnClickListener(this);
		tv_user_phone_show = (TextView) findViewById(R.id.tv_user_phone_show);
		layout_user_sex = (RelativeLayout) findViewById(R.id.layout_user_sex);
		layout_user_sex.setOnClickListener(this);
		tv_user_sex_show = (TextView) findViewById(R.id.tv_user_sex_show);
		layout_user_age = (RelativeLayout) findViewById(R.id.layout_user_age);
		layout_user_age.setOnClickListener(this);
		tv_user_age_show = (TextView) findViewById(R.id.tv_user_age_show);
		layout_user_birthday = (RelativeLayout) findViewById(R.id.layout_user_birthday);
		layout_user_birthday.setOnClickListener(this);
		tv_user_birthday_show = (TextView) findViewById(R.id.tv_user_brithday_show);

	}

	@Override
	public void onClick(View v) {
		EditDialog dialog;
		InputMethodManager mInputManager;
		switch (v.getId()) {
			case R.id.iv_title_left:
				finish();
				break;
		case R.id.layout_user_icon:// 点击头像时
			showDialogs();
			break;
		case R.id.layout_user_name:// 点击姓名时
			dialog = new EditDialog(PersonalDataActivity.this,
					tv_user_name_show.getText().toString());
			dialog.addListener(new ExitDialogListener() {

				@Override
				public void clickCommit(String tv) {
					tv_user_name_show.setText(tv);
				}

				@Override
				public void clickCancel() {

				}
			});
			dialog.show();
			// 打开系统软键盘
			dialog.getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
							| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
			dialog.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			mInputManager = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
			mInputManager
					.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

			dialog.setCanceledOnTouchOutside(false);
			break;
		case R.id.layout_user_sex:// 点击性别时
			ChooseSexDialog d = new ChooseSexDialog(PersonalDataActivity.this,
					tv_user_sex_show.getText().toString());
			d.addListener(new com.user.service.ChooseSexDialog.ExitDialogListener() {

				@Override
				public void clickCommit(String tv) {
					tv_user_sex_show.setText(tv);
				}

				@Override
				public void clickCancel() {

				}
			});
			d.show();
			d.setCanceledOnTouchOutside(false);
			break;
		case R.id.layout_user_age:// 点击年龄时
			dialog = new EditDialog(PersonalDataActivity.this, tv_user_age_show
					.getText().toString(), InputType.TYPE_CLASS_NUMBER);
			dialog.addListener(new ExitDialogListener() {

				@Override
				public void clickCommit(String tv) {
					tv_user_age_show.setText(tv);
				}

				@Override
				public void clickCancel() {

				}
			});
			dialog.show();
			// 打开系统软键盘
			dialog.getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
							| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
			dialog.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			mInputManager = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
			mInputManager
					.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

			dialog.setCanceledOnTouchOutside(false);
			break;

		case R.id.tv_title_right:
			if (!networknotvalide) {
				ToastUtil.show(context, "网络异常，请先重新加载用户信息");
				return;
			}
			String userName = tv_user_name_show.getText().toString();
			String sex = tv_user_sex_show.getText().toString();
			if (sex.equals("男"))
				sex = "1";
			else
				sex = "2";
			String age = tv_user_age_show.getText().toString();
			if (!Util.IsNotNUll(userName)) {
				ToastUtil.show(context, "用户名不能为空");
				return;
			}
			if (!Util.IsNotNUll(sex)) {
				ToastUtil.show(context, "性别");
				return;
			}
			String strBirthday= tv_user_birthday_show.getText().toString().trim();
			if(TextUtils.isEmpty(strBirthday)){
				ToastUtil.show(context, "出生日期不能为空");
				return;
			}
			ApiUtil.Request(new RQBaseModel<RQUpdateUserinfo, RSBase>(context,
					new RQUpdateUserinfo(Utils.getUserDTO(context).data.userId,
							userName, sex, "", str_pickPath,strBirthday),
					new RSBase(), ApiNames.修改用户信息.getValue(), RequestType.POST,
					rqHandler_updateUserInfo));
			break;
			case R.id.layout_user_birthday:
			//出生日期
			final TimePopwindow popWindow = new TimePopwindow(PersonalDataActivity.this);
			popWindow.setmSelectTimeMsgObj(new TimePopwindow.SelectTimeMsgListener() {

				@Override
				public void selectTimeMsg(String strTime) {
					tv_user_birthday_show.setText(strTime);
					popWindow.dismiss();
				}
			});
			popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
				@Override
				public void onDismiss() {
					backgroundAlpha(1f);
				}
			});
			closeWindow();
			popWindow.showAtLocation(layout_user_birthday, Gravity.BOTTOM, 0, 0);
			backgroundAlpha(0.4f);
			break;
		default:
			break;
		}
	}
	// 隐藏键盘
	private void closeWindow() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(tv_user_birthday_show.getWindowToken(), 0); // 强制隐藏键盘
	}

	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		getWindow().setAttributes(lp);
	}
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	private String appDir;// 存储文件夹目录地址;
	private String newImagePath;// 拍照得到的新图片地址;
	public Uri uri;

	// 更换头像点击事件
	private void showDialogs() {
		View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog,
				null);
		carema = (Button) view.findViewById(R.id.camera);
		album = (Button) view.findViewById(R.id.album);
		give_up = (Button) view.findViewById(R.id.give_up);
		final Dialog dialog = new Dialog(this,
				R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画R.style.main_menu_animstyle
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = getWindowManager().getDefaultDisplay().getHeight();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		carema.setOnClickListener(new OnClickListener() {
			// 调用相机
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				if (SDCardUtils.isExistSDCard()) {
					appDir = SDCardUtils.createAppDir();
				} else {
					appDir = PersonalDataActivity.this.getFilesDir().getPath();
				}
				newImagePath = appDir + "/" + getPhotoFileName();
				Log.e("newImagePath", newImagePath);
				File file = new File(newImagePath);
				uri = Uri.fromFile(file);
				// 调用系统的拍照功能
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
			}
		});

		album.setOnClickListener(new OnClickListener() {
			// 扫描本地相册
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
			}
		});

		give_up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	// 使用系统当前日期加以调整作为照片的名称
	public String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO: // 拍照
			startPhotoZoom(uri, 150);
			break;

		case PHOTO_REQUEST_GALLERY: // 相册选择
			if (data != null)
				startPhotoZoom(data.getData(), 150);
			break;

		case PHOTO_REQUEST_CUT:
			if (data != null)
				setPicToView(data);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 头像裁切
	private void startPhotoZoom(Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "false");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);
		intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	// 将进行剪裁后的图片显示到UI界面上
	private void setPicToView(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap bitmap = bundle.getParcelable("data");
			// 将文件写入流中
			fileName = FileUtils.getSaveFilePath() + "/" + System.currentTimeMillis() + ".jpg";
			File file = new File(fileName);
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				FileOutputStream fos = new FileOutputStream(file);
				Map<String, String> params = new HashMap<String, String>();
				params.put("uploadFrom","2");
				Map<String, File> files = new HashMap<String, File>();
				files.put("file", file);
				ImageUploadAsyncTask iua = new ImageUploadAsyncTask(params,
						files, ApiConstants.uploadImgApiUrl
								+ "upload/fileupload/uploadimg?uploadFrom=2", this);
				iua.execute(3);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			iv_user_icon_show.setImageBitmap(bitmap);
		}
	}

	@Override
	public void httpError() {
		ToastUtil.show(context, "图片上传失败");
	}

	@Override
	public void httpSuccess(String msg) {
		Gson gson = new Gson();
		Log.e("---------",msg);
		RSUploadImage rs_new = gson.fromJson(msg, RSUploadImage.class);
		if(rs_new!=null && rs_new.getData()!=null){
			str_pickPath = rs_new.getData().getRelativePath();
		}
		ToastUtil.show(context, "图片上传成功");
	}

}
