package scut.farseer.campuscouple.activity;

import org.json.JSONObject;

import scut.farseer.campuscouple.HttpTask;
import scut.farseer.campuscouple.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPassword_ResetActivity extends Activity implements
		android.view.View.OnClickListener
{
	private EditText et_password;
	private EditText et_password_confirm;
	private Button btn_next;
	private View sp_back;
	private boolean flags4Next = false;// 判断能否点击下一步
	private String verifyCode = "";
	private String userMobile = "";
	private String password = "";

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.retrieve_password_reset);

		et_password = (EditText) findViewById(R.id.set_password);
		et_password_confirm = (EditText) findViewById(R.id.confirm_password);
		btn_next = (Button) findViewById(R.id.next);
		sp_back = findViewById(R.id.sp_back);

		btn_next.setOnClickListener(this);
		sp_back.setOnClickListener(this);

		Intent intentFromVerify = getIntent();
		verifyCode = intentFromVerify.getStringExtra("verify_code");

	}

	@Override
	public void onClick(View v)
	{
		Intent intent = null;
		switch (v.getId())
		{
			case R.id.next:
				String passwordString = et_password.getText().toString();
				String confirmPasswordString = et_password_confirm.getText()
						.toString();
				android.util.Log.d("TAG", "passwordString:" + passwordString);
				android.util.Log.d("TAG", "confirmPasswordString:"
						+ confirmPasswordString);
				checkPassword(passwordString, confirmPasswordString);
				if (flags4Next)
				{
					passwordVerify2Server();
				}
				break;
			case R.id.sp_back:
				// 返回登陆页面？？
				// TODO: ？？
				intent = new Intent(ForgetPassword_ResetActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
				break;
			default:
				break;
		}

	}

	private void passwordVerify2Server()
	{
		Intent intent;
		// 发送数据到后台,veriry_code,mobile,password
		HttpTask task = new HttpTask(this) {

			@Override
			public void callback(String apiUrl, JSONObject jo)
			{

				android.util.Log.d("TAG", "apiUrl:" + apiUrl);

				if (jo != null)
				{
					android.util.Log.d("TAG", "jo:" + jo.toString());
					try
					{
						String warningString = "";
						int status = jo.getJSONObject("json").getInt("status");
						Intent intent = null;

						android.util.Log.d("TAG", "status:" + status);
						switch (status)
						{
							case 200:
								intent = new Intent(
										ForgetPassword_ResetActivity.this,
										LoginActivity.class);
								intent.putExtra("user_mobile", userMobile);
								startActivity(intent);
								finish();
								break;
							case 130:
								warningString = "密码长度过短";
								break;
							case 131:
								warningString = "密码长度过长";
								break;
							case 132:
								warningString = "密码字符类型不合法";
								break;
							case 111:
								warningString = "参数值类型非法";
								break;
							case 110:
								warningString = "缺少必填参数";
								break;
							case 120:
								warningString = "手机号长度或者类型不正确";
								break;
							case 431:
								warningString = "验证码错误";
								break;
							case 421:
								warningString = "手机号不存在";
								break;
							default:
								warningString = "系统错误";
								break;
						}

						if (status != 200)
							Toast.makeText(ForgetPassword_ResetActivity.this,
									warningString, Toast.LENGTH_SHORT).show();
					}
					// TODO:存储usermobile

					catch (Exception e)
					{
						Toast.makeText(ForgetPassword_ResetActivity.this,
								"系统异常", Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Toast.makeText(ForgetPassword_ResetActivity.this,
							"json null", Toast.LENGTH_SHORT).show();
				}
			}
		};

		task.url("/user/password/verify").addParam("mobile", userMobile)
				.addParam("verify_code", verifyCode)
				.addParam("new_password", password).sendRequest();
	}

	private void checkPassword(String passwordString,
			String confirmPasswordString)
	{
		String messageTaostString = "";
		if (passwordString == null || passwordString == null)
		{
			messageTaostString = "请全部填写";
			flags4Next = false;
		}
		else if (passwordString != passwordString)
		{
			messageTaostString = "两次密码不正确";
			flags4Next = false;
		}
		else if (passwordString == passwordString)
		{
			password = passwordString;
			flags4Next = true;
		}

		if (!flags4Next)
			Toast.makeText(ForgetPassword_ResetActivity.this,
					messageTaostString, Toast.LENGTH_SHORT).show();
	}
}
