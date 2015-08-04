package scut.farseer.campuscouple.activity;

import scut.farseer.campuscouple.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Signup_SetPasswordActivity extends Activity
{
	EditText set_password;
	EditText confirm_password;
	Button set_password_next;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_set_password);
	}

}
