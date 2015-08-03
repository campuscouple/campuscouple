package scut.farseer.campuscouple.activity;

import org.json.JSONObject;

import scut.farseer.campuscouple.HttpTask;
import scut.farseer.campuscouple.MessageTimeCount;
import scut.farseer.campuscouple.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class ForgetPassword_VerifyActivity extends Activity implements
		OnClickListener
{
	View go_back;
	EditText phone_number_input;
	EditText verification_code_input;
	TextView send_verification_code;
	Button btn_next;
	private MessageTimeCount timeCount = null;
	private String userMobile = "";
	private boolean flags4Verify = false;// 检查能否发送验证码给后台
	private String verifyCode = "";

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.retrieve_password_verify);

		go_back = findViewById(R.id.back);
		phone_number_input = (EditText) findViewById(R.id.phone_number_input);
		verification_code_input = (EditText) findViewById(R.id.verification_code_input);
		send_verification_code = (TextView) findViewById(R.id.send_verification_code);
		btn_next = (Button) findViewById(R.id.btn_next);
		go_back.setOnClickListener(this);
		send_verification_code.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		timeCount = new MessageTimeCount(20000, 1000, send_verification_code);
		// 将输入验证码框改成不可输入
		verification_code_input.setClickable(false);
		// 将下一步的按钮改成不可点击
		btn_next.setClickable(false);

	}

	public void onClick(View v)
	{
		Intent intent = null;
		switch (v.getId())
		{
			case R.id.back:
				intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				finish();
				break;

			case R.id.send_verification_code:

				// 获取用户输入的手机号码
				userMobile = phone_number_input.getText().toString();

				if (userMobile == null || userMobile.length() != 11)// 错误提示
					userMobileWrongInput(userMobile);
				else
				{// 控件不可点击，且倒计时显示“59s后可重发”
					timeCount.start();

					// 向uer/password/forget发送信息,传入usermobile
					sendVerifyCode(userMobile);

					// 将下一步的按钮改成可点击
					btn_next.setClickable(true);
				}
				break;

			case R.id.btn_next:
				// 检查mobile和verify是否填写
				checkMobileAndVerifyCode();
				if (flags4Verify)// 向后台发送信息并返回
				{
					// 储存Verify_code
					intent = new Intent(ForgetPassword_VerifyActivity.this,
							ForgetPassword_ResetActivity.class);
					intent.putExtra("verify_code", verifyCode);
					intent.putExtra("user_mobile", userMobile);
					startActivity(intent);
					finish();
				}
				break;
			default:
				break;
		}
	}

	// 检查手机号、验证码是否合法，非法则toast显示，否则falgs4Verify为true
	private void checkMobileAndVerifyCode()
	{
		String warningString = "";
		flags4Verify = false;
		verifyCode = send_verification_code.getText().toString();
		// 判断验证码
		if (verifyCode != null && verifyCode.length() < 20)
			flags4Verify = true;
		else
		{
			flags4Verify = false;
			warningString = "验证码格式错误.";
		}

		// 判断手机号
		if (userMobile == null || userMobile.length() != 11)
		{
			warningString = "手机号不正确.";
			flags4Verify = false;
		}
		else if (userMobile != phone_number_input.getText().toString())
			phone_number_input.setText(userMobile);

		// 非法则toast提示
		if (!flags4Verify)
			Toast.makeText(ForgetPassword_VerifyActivity.this, warningString,
					Toast.LENGTH_LONG).show();
	}

	private void userMobileWrongInput(String userMobile)
	{
		String warningString = "";
		if (userMobile == null)
			warningString = "未输入手机号码.";
		else
			warningString = "不存在该手机号，长度不对.";
		if (userMobile.length() != 11)
			Toast.makeText(ForgetPassword_VerifyActivity.this, warningString,
					Toast.LENGTH_LONG).show();
	}

	// 向uer/password/forget发送信息,传入usermobile
	private void sendVerifyCode(String userMobile)
	{
		HttpTask task = new HttpTask(this) {

			@Override
			public void callback(String apiUrl, JSONObject jo)
			{
				if (jo != null)
				{
					try
					{
						String warningString = "";
						int status = jo.getJSONObject("json").getInt("status");
						if (status != 200)
						{
							if (status == 421)
								warningString = "手机号不存在";
							else if (status == 700 || status != 200)
								warningString = "系统错误";
							Toast.makeText(ForgetPassword_VerifyActivity.this,
									warningString, Toast.LENGTH_SHORT).show();
						}
						// TODO:存储usermobile

					}
					catch (Exception e)
					{
						Toast.makeText(ForgetPassword_VerifyActivity.this,
								"系统异常", Toast.LENGTH_SHORT).show();
					}
				}
				else
					Toast.makeText(ForgetPassword_VerifyActivity.this,
							"json null", Toast.LENGTH_SHORT).show();
			}
		};

		task.url("/user/password/forget").addParam("mobile", userMobile)
				.sendRequest();
	}

	public void onBackPressed()
	{
		super.onBackPressed();
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

}
