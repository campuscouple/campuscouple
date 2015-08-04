package scut.farseer.campuscouple.activity;

import org.json.JSONException;
import org.json.JSONObject;

import scut.farseer.campuscouple.HttpTask;
import scut.farseer.campuscouple.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup_SetPasswordActivity extends Activity implements
		OnClickListener
{
	EditText set_password;
	EditText confirm_password;
	Button set_password_next;
	String phone_number = null;
	String verify_code = null;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_set_password);

		set_password = (EditText) findViewById(R.id.set_password);
		confirm_password = (EditText) findViewById(R.id.confirm_password);
		set_password_next = (Button) findViewById(R.id.set_password_next);

		set_password_next.setOnClickListener(this);
		Intent intent = getIntent();
		phone_number = intent.getStringExtra("mobile");
		verify_code = intent.getStringExtra("verify_code");
	}

	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.set_password_next:
				String passwordString = set_password.getText().toString();
				String confirmPasswordString = confirm_password.getText()
						.toString();
				if (!checkPassword(passwordString, confirmPasswordString))
					return;
				
				HttpTask task = new HttpTask(this) {
					
					public void callback(String apiUrl, JSONObject jo)
					{
						if(jo != null)
						{
							try
							{
								int status = jo.getJSONObject("json").getInt("status");
								switch (status)
								{
									case 200:
										Intent intent = new Intent(Signup_SetPasswordActivity.this,
												Signup_SetCampusActivity.class);
										Signup_SetPasswordActivity.this.startActivity(intent);
										Signup_SetPasswordActivity.this.finish();
										break;
									case 420:
										Toast.makeText(Signup_SetPasswordActivity.this, "该手机号已被注册",
												Toast.LENGTH_SHORT).show();
									case 132:
										Toast.makeText(Signup_SetPasswordActivity.this, "密码含有非法字符",
												Toast.LENGTH_SHORT).show();

									default:
										Toast.makeText(Signup_SetPasswordActivity.this, "注册失败",
												Toast.LENGTH_SHORT).show();
										break;
								}
							}
							catch (JSONException e)
							{
								Toast.makeText(Signup_SetPasswordActivity.this, "注册失败",
										Toast.LENGTH_SHORT).show();
							}
						}
						else
							Toast.makeText(Signup_SetPasswordActivity.this, "网络异常",
									Toast.LENGTH_SHORT).show();
					}
				};
				
				task.url("/user/register")
					.addParam("mobile", phone_number)
					.addParam("verify_code", verify_code)
					.addParam("password", passwordString)
					.sendRequest();

				break;

			default:
				break;
		}
	}

	private boolean checkPassword(String passwordString,
			String confirmPasswordString)
	{
		if (passwordString.length() < 6)
		{
			Toast.makeText(Signup_SetPasswordActivity.this, "密码过短",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (passwordString.length() > 19)
		{
			Toast.makeText(Signup_SetPasswordActivity.this, "密码过长",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (passwordString.equals(confirmPasswordString))
			return true;
		else
			Toast.makeText(Signup_SetPasswordActivity.this, "两次密码不一致",
					Toast.LENGTH_SHORT).show();
		return false;
	}

	public void onBackPressed()
	{
		super.onBackPressed();
		Intent intent = new Intent(Signup_SetPasswordActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
}
