package scut.farseer.campuscouple.activity;

import scut.farseer.campuscouple.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class LoginActivity extends Activity implements OnClickListener
{
	EditText username_input;
	EditText password_input;
	ImageButton username_delete_all;
	ImageButton password_delete_all;
	ImageButton btn_sign_up;
	Button btn_login;
	TextView forgot_password;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		
		username_input = (EditText) findViewById(R.id.username_input);
		password_input = (EditText) findViewById(R.id.password_input);
		username_delete_all = (ImageButton) findViewById(R.id.username_delete_all);
		password_delete_all = (ImageButton) findViewById(R.id.password_delete_all);
		btn_sign_up = (ImageButton) findViewById(R.id.btn_sign_up);
		btn_login = (Button) findViewById(R.id.btn_login);
		forgot_password = (TextView) findViewById(R.id.forgot_password);
		
		username_delete_all.setOnClickListener(this);
		password_delete_all.setOnClickListener(this);
		btn_login.setOnClickListener(this);
		btn_sign_up.setOnClickListener(this);
		forgot_password.setOnClickListener(this);
		
	}

	public void onClick(View v)
	{
		Intent intent = null;
		switch (v.getId())
		{
			case R.id.username_delete_all:
				username_input.setText("");
				break;
			
			case R.id.password_delete_all:
				password_input.setText("");
				break;
				
			case R.id.btn_login:
				break;
				
			case R.id.btn_sign_up:
				intent = new Intent(this, Signup_VerifyActivity.class);
				startActivity(intent);
				finish();
				break;
				
			case R.id.forgot_password:
				intent = new Intent(this, ForgetPassword_VerifyActivity.class);
				startActivity(intent);
				finish();
				break;

			default:
				break;
		}
	}
}
