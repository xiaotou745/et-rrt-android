package com.task.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import base.BaseActivity;

import com.google.gson.Gson;
import com.renrentui.app.R;
import com.renrentui.interfaces.IBack;
import com.renrentui.interfaces.INodata;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQGetTaskDetailInfo;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQSubmitData;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.requestmodel.ValueInfo;
import com.renrentui.resultmodel.ControlInfo;
import com.renrentui.resultmodel.RQUploadImage;
import com.renrentui.resultmodel.RSBase;
import com.renrentui.resultmodel.RSGetTaskDetailInfo;
import com.renrentui.resultmodel.TaskDetailInfo;
import com.renrentui.tools.ExitApplication;
import com.renrentui.tools.FileUtils;
import com.renrentui.tools.SDCardUtils;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiConstants;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.ToMainPage;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.service.QuitSubmitDialog;
import com.task.service.SubmitSuccessDialog;
import com.task.service.SubmitSuccessDialog.ExitDialogListener;
import com.user.activity.PersonalCenterActivity;
import com.user.model.HttpRequest.HttpRequestListener;
import com.user.model.ImageUploadAsyncTask;

/**
 * 提交合同信息界面
 * 
 * @author llp
 * 
 */
public class SubmitDataActivity extends BaseActivity implements
		OnClickListener, INodata, HttpRequestListener {

	private Context context;
	private LinearLayout edit_text_layout;
	private View textView;
	private LinearLayout image_view_layout;
	private Button btn_submit;
	private ScrollView layout_data;
	private String taskId;
	private int auditStatus;
	private String myReceivedTaskId;
	private TaskDetailInfo data;
	private ImageView icon_pusher;// 商家icon
	private TextView tv_taskName;// 任务名称
	private TextView tv_taskGeneralInfo;// 任务公告
	private TextView tv_Amount;// 任务单价

	private RQHandler<RSGetTaskDetailInfo> rqHandler_getTaskDetailInfo = new RQHandler<>(
			new IRqHandlerMsg<RSGetTaskDetailInfo>() {

				@Override
				public void onBefore() {
					hideProgressDialog();
				}

				@Override
				public void onNetworknotvalide() {
					layout_data.setVisibility(View.GONE);
					SubmitDataActivity.this.onNodata(
							ResultMsgType.NetworkNotValide, null, null, null);
				}

				@Override
				public void onSuccess(RSGetTaskDetailInfo t) {
					data = t.data;
					tv_taskName.setText(data.taskTitle);
					tv_taskGeneralInfo.setText("审核"+data.auditCycle+"天");
					tv_Amount.setText(data.getAmount());
					ImageLoadManager.getLoaderInstace().disPlayNormalImg(
							data.logo, icon_pusher,
							R.drawable.pusher_logo);
					initUI(data.controlInfo);
					hideProgressDialog();
				}

				@Override
				public void onSericeErr(RSGetTaskDetailInfo t) {
					layout_data.setVisibility(View.GONE);
					SubmitDataActivity.this.onNodata(ResultMsgType.ServiceErr,
							"刷新", "数据加载失败！", SubmitDataActivity.this);
				}

				@Override
				public void onSericeExp() {
					layout_data.setVisibility(View.GONE);
					SubmitDataActivity.this.onNodata(ResultMsgType.ServiceExp,
							"刷新", "数据加载失败！", SubmitDataActivity.this);
				}
			});
	private RQHandler<RSBase> rqHandler_submitData = new RQHandler<>(
			new IRqHandlerMsg<RSBase>() {

				@Override
				public void onBefore() {
					hideProgressDialog();
				}

				@Override
				public void onNetworknotvalide() {
					SubmitDataActivity.this.onNodata(
							ResultMsgType.NetworkNotValide, null, null, null);
				}

				@Override
				public void onSuccess(RSBase t) {
					hideProgressDialog();
					SubmitSuccessDialog ssd = new SubmitSuccessDialog(context,
							"审核时间" + data.auditCycle + "天，情耐心等待");
					ssd.addListener(new ExitDialogListener() {

						@Override
						public void clickCommit() {
							ExitApplication.getInstance().lastExit();
							Intent intent = new Intent(SubmitDataActivity.this,
									TaskDetailInfoActivity.class);
							intent.putExtra("TaskId", data.id);
							startActivity(intent);
							finish();
						}

						@Override
						public void clickCancel() {
							Intent intent = new Intent(SubmitDataActivity.this,
									MyTaskMainActivity.class);
							intent.putExtra("topage", ToMainPage.已领取.getValue());
							startActivity(intent);
							finish();

						}
					});
					ssd.show();
					ssd.setCancelable(false);
				}

				@Override
				public void onSericeErr(RSBase t) {
					ToastUtil.show(context, t.msg);
				}

				@Override
				public void onSericeExp() {

				}
			});

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_data);
		taskId = getIntent().getStringExtra("taskId");
		auditStatus = getIntent().getIntExtra("auditStatus", 0);
		myReceivedTaskId = getIntent().getStringExtra("myReceivedTaskId");
		super.init();
		initControl();
		getInitData(taskId);
	}

	/**
	 * 获取数据
	 */
	private void getInitData(String taskId) {
		showProgressDialog();
		ApiUtil.Request(new RQBaseModel<RQGetTaskDetailInfo, RSGetTaskDetailInfo>(
				context, new RQGetTaskDetailInfo(
						Utils.getUserDTO(context).data.userId, taskId,
						myReceivedTaskId), new RSGetTaskDetailInfo(),
				ApiNames.获取任务详细信息.getValue(), RequestType.POST,
				rqHandler_getTaskDetailInfo));
	}

	/**
	 * 初始化控件
	 */
	private void initControl() {
		context = this;
		layout_data = (ScrollView) findViewById(R.id.layout_data);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);
		edit_text_layout = (LinearLayout) findViewById(R.id.edit_text_layout);
		image_view_layout = (LinearLayout) findViewById(R.id.image_view_layout);
		icon_pusher = (ImageView) findViewById(R.id.icon_pusher);
		tv_taskName = (TextView) findViewById(R.id.tv_taskName);
		tv_taskGeneralInfo = (TextView) findViewById(R.id.tv_taskGeneralInfo);
		tv_Amount = (TextView) findViewById(R.id.tv_Amount);
	}

	@SuppressWarnings("deprecation")
	private void initUI(List<ControlInfo> controlInfo) {
		int tindex = 3;
		int iindex = 3;

		if (auditStatus == 2) {
			for (int i = 0; i < controlInfo.size(); i++) {
				ControlInfo control = controlInfo.get(i);
				if (control.controlType.equals("Text")) {
					TextView tv = new TextView(context);
					LinearLayout.LayoutParams edit_text_layout_param = (LayoutParams) edit_text_layout
							.getLayoutParams();
					tv.setText(control.hadValue);
					tv.setLayoutParams(edit_text_layout_param);
					edit_text_layout.addView(tv, tindex);
					tindex++;

				} else if (control.controlType.equals("Select")) {
					LinearLayout.LayoutParams edit_text_layout_param = (LayoutParams) edit_text_layout
							.getLayoutParams();

				} else if (control.controlType.equals("FileUpload")) {
					TextView tv = new TextView(context);
					final ImageView iv = new ImageView(context);
					LinearLayout layout = new LinearLayout(context);

					LinearLayout.LayoutParams image_view_layout_param = (LayoutParams) image_view_layout
							.getLayoutParams();
					image_view_layout_param.width = LayoutParams.MATCH_PARENT;
					image_view_layout_param.height = LayoutParams.WRAP_CONTENT;
					image_view_layout_param.bottomMargin = 20;

					layout.setLayoutParams(image_view_layout_param);
					image_view_layout.addView(layout, iindex);// Integer.parseInt(control.orderNum)

					tv.setText(control.title);
					layout.addView(tv, 0);

					if (Util.IsNotNUll(control.hadValue)) {
						ImageLoadManager.getLoaderInstace().disPlayNormalImg(
								control.hadValue, iv,
								R.drawable.add_image);
					} else {
						iv.setImageResource(R.drawable.add_image);
					}
					iv.setX(200);
					layout.addView(iv, 1);
					iindex++;
				}
			}
			btn_submit.setVisibility(View.GONE);
		} else {
			for (int i = 0; i < controlInfo.size(); i++) {
				final ControlInfo control = controlInfo.get(i);
				if (control.controlType.equals("Text")) {
					EditText et = new EditText(context);
					LinearLayout.LayoutParams edit_text_layout_param = (LayoutParams) edit_text_layout
							.getLayoutParams();
					et.setTag(control.name);
					et.setId(Integer.parseInt(control.orderNum));
					if (Util.IsNotNUll(control.hadValue)) {
						et.setText(control.hadValue);
					} else {
						et.setHint(control.title);
					}
					et.setLayoutParams(edit_text_layout_param);
					edit_text_layout.addView(et, tindex);
					tindex++;

				} else if (control.controlType.equals("Select")) {
					LinearLayout.LayoutParams edit_text_layout_param = (LayoutParams) edit_text_layout
							.getLayoutParams();

				} else if (control.controlType.equals("FileUpload")) {
					final TextView tv = new TextView(context);
					final ImageView iv = new ImageView(context);
					LinearLayout layout = new LinearLayout(context);

					LinearLayout.LayoutParams image_view_layout_param = (LayoutParams) image_view_layout
							.getLayoutParams();
					image_view_layout_param.width = LayoutParams.MATCH_PARENT;
					image_view_layout_param.height = LayoutParams.WRAP_CONTENT;
					image_view_layout_param.bottomMargin = 20;

					layout.setLayoutParams(image_view_layout_param);
					image_view_layout.addView(layout, iindex);

					tv.setWidth(100);
					tv.setText(control.title);
					layout.addView(tv, 0);

					if (Util.IsNotNUll(control.hadValue)) {
						ImageLoadManager.getLoaderInstace().disPlayNormalImg(
								control.hadValue, iv,
								R.drawable.add_image);
						iv.setTag(control.name + " "
								+ control.controlValue);
					} else {
						iv.setTag(control.name);
						iv.setImageResource(R.drawable.add_image);
					}
					iv.setId(Integer.parseInt(control.orderNum));
					iv.setX(200);
					iv.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							imageView = iv;
							textView = tv;
							View view = getLayoutInflater().inflate(
									R.layout.photo_choose_dialog, null);
							carema = (Button) view.findViewById(R.id.camera);
							album = (Button) view.findViewById(R.id.album);
							give_up = (Button) view.findViewById(R.id.give_up);
							final Dialog dialog = new Dialog(context,
									R.style.transparentFrameWindowStyle);
							dialog.setContentView(view, new LayoutParams(
									LayoutParams.FILL_PARENT,
									LayoutParams.WRAP_CONTENT));
							Window window = dialog.getWindow();
							// 设置显示动画R.style.main_menu_animstyle
							window.setWindowAnimations(R.style.main_menu_animstyle);
							WindowManager.LayoutParams wl = window
									.getAttributes();
							wl.x = 0;
							wl.y = getWindowManager().getDefaultDisplay()
									.getHeight();
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
										appDir = context.getFilesDir()
												.getPath();
									}
									newImagePath = appDir + "/"
											+ getPhotoFileName();
									File file = new File(newImagePath);
									uri = Uri.fromFile(file);
									// // 调用系统的拍照功能
									Intent intent = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									intent.putExtra(MediaStore.EXTRA_OUTPUT,
											uri);
									startActivityForResult(intent,
											PHOTO_REQUEST_TAKEPHOTO);
								}
							});

							album.setOnClickListener(new OnClickListener() {
								// 扫描本地相册
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									Intent intent = new Intent(
											Intent.ACTION_PICK, null);
									intent.setDataAndType(
											MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
											"image/*");
									startActivityForResult(intent,
											PHOTO_REQUEST_GALLERY);
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
					});
					layout.addView(iv, 1);

					iindex++;
				}
			}
		}

		if (tindex == 3) {
			edit_text_layout.setVisibility(View.GONE);
		}
		if (iindex == 3) {
			image_view_layout.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_submit:// 提交审核按钮
			List<ValueInfo> valueInfos = new ArrayList<ValueInfo>();
			for (int i = 0; i < data.controlInfo.size(); i++) {
				ControlInfo control = data.controlInfo.get(i);
				ValueInfo vi = new ValueInfo();
				if (control.controlType.equals("Text")) {
					TextView tv = (TextView) findViewById(Integer
							.parseInt(control.orderNum));
					vi.controlName = tv.getTag().toString();
					vi.controlValue = tv.getText().toString();
				} else if (control.controlType.equals("FileUpload")) {
					ImageView iv = (ImageView) findViewById(Integer
							.parseInt(control.orderNum));
					if (iv.getTag().toString().split(" ").length == 2) {
						vi.controlName = iv.getTag().toString().split(" ")[0];
						vi.controlValue = iv.getTag().toString().split(" ")[1];
					} else {
						vi.controlName = iv.getTag().toString();
					}
				}
				valueInfos.add(vi);
			}
			ApiUtil.Request(new RQBaseModel<RQSubmitData, RSBase>(context,
					new RQSubmitData(Utils.getUserDTO(context).data.userId,
							data.orderId, data.templateId, valueInfos),
					new RSBase(), ApiNames.提交任务.getValue(), RequestType.POST,
					rqHandler_submitData));
			break;

		default:
			break;
		}
	}

	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	private String appDir;// 存储文件夹目录地址;
	private String newImagePath;// 拍照得到的新图片地址;
	public Uri uri;
	Button carema, album, give_up;// 点击用户头像弹出层按钮
	public static String fileName;
	private ImageView imageView;

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
		intent.putExtra("crop", false);
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);
		intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        intent.putExtra("scaleboolean", true);
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				FileOutputStream fos = new FileOutputStream(file);
				Map<String, String> params = new HashMap<String, String>();
				Map<String, File> files = new HashMap<String, File>();
				files.put("file", file);
				ImageUploadAsyncTask iua = new ImageUploadAsyncTask(params,
						files, ApiConstants.uploadImgApiUrl
								+ "upload/uploadimg?uploadfrom=3", this);
				iua.execute(3);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			imageView.setImageBitmap(bitmap);
		}
	}

	@Override
	public void onNoData() {
		getInitData(taskId);
	}

	@Override
	public void httpError() {
		// TODO Auto-generated method stub
		ToastUtil.show(context, "图片上传失败,请重新上传");
		imageView.setImageResource(R.drawable.add_image);
		if (imageView.getTag().toString().split(" ").length == 1)
			imageView.setTag(imageView.getTag());
	}

	@Override
	public void httpSuccess(String msg) {
		Gson gson = new Gson();
		RQUploadImage rq = gson.fromJson(msg, RQUploadImage.class);
		imageView.setTag(imageView.getTag().toString().split(" ")[0] + " "
				+ rq.Result.RelativePath);
		ToastUtil.show(context, "图片上传成功");
	}

	private IBack iback = new IBack() {

		@Override
		public void onClick(View v) {
			showDialog();
		}

	};

	private void showDialog() {
		if (btn_submit.getVisibility() == View.VISIBLE) {
			QuitSubmitDialog qsd = new QuitSubmitDialog(context);
			qsd.addListener(new QuitSubmitDialog.ExitDialogListener() {

				@Override
				public void clickCommit() {
					// TODO Auto-generated method stub

				}

				@Override
				public void clickCancel() {
					// TODO Auto-generated method stub
					finish();
				}
			});
			qsd.show();
			qsd.setCancelable(false);
		} else {
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			showDialog();
		}
		return true;
	}

}
