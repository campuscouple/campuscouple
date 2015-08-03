package scut.farseer.campuscouple.activity;

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
				String userMobile = phone_number_input.getText().toString();

				if (userMobile == null || userMobile.length() != 11)// 错误提示
					userMobileWrongInput(userMobile);
				else
				{// 控件不可点击，且倒计时显示“59s后可重发”
					timeCount.start();
					// 向uer/password/forget发送信息,传入usermobile
					sendVerifyCode(userMobile);

					// 将输入验证码框改成可输入
					verification_code_input.setClickable(true);
					// 将下一步的按钮改成可点击
					btn_next.setClickable(true);
				}
				break;

			case R.id.btn_next:
				intent = new Intent(this, ForgetPassword_ResetActivity.class);
				startActivity(intent);
				finish();
				break;
			default:
				break;
		}
	}

	private void userMobileWrongInput(String userMobile)
	{
		String warningString = "";
		if (userMobile == null)
			warningString = "未输入用户手机号码.";
		else
			warningString = "不存在该手机号，长度不对.";
		Toast.makeText(ForgetPassword_VerifyActivity.this, warningString,
				Toast.LENGTH_LONG).show();
	}

	// 向uer/password/forget发送信息,传入usermobile
	private void sendVerifyCode(String userMobile)
	{

	}

	public void onBackPressed()
	{
		super.onBackPressed();
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

}
