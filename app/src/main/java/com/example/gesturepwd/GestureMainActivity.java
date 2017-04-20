package com.example.gesturepwd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 手势密码设置主界面
 *
 * @author lzh
 *
 */
public class GestureMainActivity extends AppCompatActivity
		implements OnClickListener {

	private Button btn_Back; //公共头部标题返回键
	private TextView tv_Title; //公共头部标题文本内容

	private LinearLayout ll_setgesturepwd; //设置手势密码
	private LinearLayout ll_showgesturelocus; //显示手势轨迹
	private LinearLayout ll_changegesturepwd; //修改手势密码

	private GridDataSharedPreferences mPreferences;
	private CheckBox setgestureCheckbox; // 设置手势密码开关
	private CheckBox showlocusCheckBox; // 显示手势轨迹开关
	private Boolean isShowlocus; //是否设置显示轨迹
	private String isShowLocus="";
	private Boolean isSetGesture; //是否设置手势密码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesturemain);
		//初始化界面布局
		layoutUI();
	}

	/**
	 * 初始化布局
	 */
	protected void layoutUI() {
		ll_setgesturepwd = (LinearLayout) findViewById
				(R.id.ll_setgesturepwd);
		ll_setgesturepwd.setOnClickListener(this);
		ll_showgesturelocus = (LinearLayout) findViewById
				(R.id.ll_showgesturelocus);
		ll_showgesturelocus.setOnClickListener(this);
		ll_changegesturepwd = (LinearLayout) findViewById
				(R.id.ll_changegesturepwd);
		ll_changegesturepwd.setOnClickListener(this);

		setgestureCheckbox = (CheckBox) findViewById
				(R.id.setgesture_checkbox);
		showlocusCheckBox = (CheckBox) findViewById
				(R.id.showlocus_checkbox);
		mPreferences = GridDataSharedPreferences.getInstance(this);
		isShowlocus = (Boolean) mPreferences.getValue(GridDataContants.SHOW_LOCUS, Boolean.class,false);
		isSetGesture = (Boolean) mPreferences.getValue(GridDataContants.SET_GESTURE, Boolean.class,false);
		showlocusCheckBox.setChecked(isShowlocus);
		setgestureCheckbox.setChecked(isSetGesture);

		//如何设置了手势密码，则修改密码按钮可见，否则不可见
		isShowChangePwd();
	}

	private void isShowChangePwd(){
		if(isSetGesture){
			ll_changegesturepwd.setVisibility(View.VISIBLE);
		}else{
			ll_changegesturepwd.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		Bundle extras  = null;
		switch (v.getId()) {
			case R.id.ll_setgesturepwd:	// 设置手势密码
				if(isShowlocus){
					isShowLocus="isTrue";
				}else{
					isShowLocus="isFalse";
				}
				extras = new Bundle();
				extras.putString(GridDataContants.SET_CHANGE_PWD, "setpwd");
				extras.putString(GridDataContants.IS_SHOW_LOCUS, isShowLocus);
				if(isSetGesture){
					mPreferences.clearGesturePreferences();
				}else{
					//showActivity(SettingGesturePwdActivity.class,extras);
				}
				isSetGesture=!(setgestureCheckbox.isChecked());
				setgestureCheckbox.setChecked(isSetGesture);
				mPreferences.putValue(GridDataContants.SET_GESTURE, isSetGesture);
				isShowChangePwd();
				break;
			case R.id.ll_showgesturelocus:	// 显示手势轨迹
				isShowlocus=!(showlocusCheckBox.isChecked());
				showlocusCheckBox.setChecked(isShowlocus);
				mPreferences.putValue(GridDataContants.SHOW_LOCUS, isShowlocus);
				break;
			case R.id.ll_changegesturepwd:	// 修改手势密码
				if(!setgestureCheckbox.isChecked()){
					Toast.makeText(this, "您还未设置密码，请先设置密码！",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if(isShowlocus){
					isShowLocus="isTrue";
				}else{
					isShowLocus="isFalse";
				}
				extras = new Bundle();
				extras.putString(GridDataContants.SET_CHANGE_PWD, "changepwd");
				extras.putString(GridDataContants.IS_SHOW_LOCUS, isShowLocus);
				//showActivity(SettingGesturePwdActivity.class,extras);
				break;

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		String pwd = (String)mPreferences.
				getValue(GridDataContants.GUE_PWD, String.class, null);
		if("".equals(pwd)||pwd==null){
			setgestureCheckbox.setChecked(false);
			mPreferences.putValue(GridDataContants.SET_GESTURE, false);
			isSetGesture = false;
			mPreferences.clearGesturePreferences();
		}
		isShowChangePwd();
	}

}