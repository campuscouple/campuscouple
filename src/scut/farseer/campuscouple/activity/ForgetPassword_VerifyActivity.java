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
				final MessageTimeCount time = new MessageTimeCount(20000, 1000,
						send_verification_code);

				send_verification_code
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v)
							{
								time.start();
								android.util.Log.d("TAT", "123789");
							}
						});

				break;

			case R.id.btn_next:
				break;

			default:
				break;
		}
	}

	public void onBackPressed()
	{
		super.onBackPressed();
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

}
