package scut.farseer.campuscouple.activity;

import org.json.JSONException;
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

public class Signup_VerifyActivity extends Activity implements OnClickListener
{
	View go_back;
	EditText phone_number_input;
	EditText verification_code_input;
	TextView send_verification_code;
	Button btn_next;
	String phone_number = null;
	String verify_code = null;
	
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_verify_layout);
		
		go_back = findViewById(R.id.back);
		phone_number_input = (EditText) findViewById(R.id.phone_number_input);
		verification_code_input = (EditText) findViewById(R.id.verification_code_input);
		send_verification_code = (TextView) findViewById(R.id.send_verification_code);
		btn_next = (Button) findViewById(R.id.next);
		
		go_back.setOnClickListener(this);
		send_verification_code.setOnClickListener(this);
		btn_next.setOnClickListener(this);
	}


	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.back:
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				finish();
				break;
				
			case R.id.send_verification_code:
				phone_number = phone_number_input.getText().toString();
				if(phone_number.length() != 11)
				{
					Toast.makeText(this, "手机号码不正确", Toast.LENGTH_SHORT).show();
					return;
				}
				
				HttpTask task = new HttpTask(this) {
					
					public void callback(String apiUrl, JSONObject jo)
					{
						if(jo != null)
						{
							int status = 0;
							try
							{
								status = jo.getJSONObject("json").getInt("status");
							}
							catch (JSONException e)
							{
							}
							Toast.makeText(Signup_VerifyActivity.this, "" + status, 
									Toast.LENGTH_SHORT).show();
						}
						else
							Toast.makeText(Signup_VerifyActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
					}
				};
				
				task.url("/user/verify/send")
					.addParam("mobile", phone_number)
					.sendRequest();
				
				MessageTimeCount time = new MessageTimeCount(30000, 
						1000, send_verification_code);
				time.start();
				
				break;
				
			case R.id.next:
				verify_code = verification_code_input.getText().toString();
				if(verify_code.length() < 4)
				{
					Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(phone_number == null)
				{
					Toast.makeText(this, "请先发送验证码", Toast.LENGTH_SHORT).show();
					return;
				}
				
				HttpTask task1 = new HttpTask(this) {
					
					public void callback(String apiUrl, JSONObject jo)
					{
						if(jo != null)
						{
							int status = 0;
							try
							{
								status = jo.getJSONObject("json").getInt("status");
							}
							catch (JSONException e)
							{
							}
							if(status != 200)
								Toast.makeText(Signup_VerifyActivity.this, "验证码错误" + status, 
										Toast.LENGTH_SHORT).show();
							else
							{
								Intent intent = new Intent(Signup_VerifyActivity.this, 
										Signup_SetPasswordActivity.class);
								intent.putExtra("mobile", phone_number);
								intent.putExtra("verify_code", verify_code);
								Signup_VerifyActivity.this.startActivity(intent);
								Signup_VerifyActivity.this.finish();
							}
						}
						else
							Toast.makeText(Signup_VerifyActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
					}
				};
				
				task1.url("/user/verify/check")
					.addParam("mobile", phone_number)
					.addParam("verify_code", verify_code)
					.sendRequest();
				
				break;

			default:
				break;
		}
	}

	public void onBackPressed()
	{
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

}
