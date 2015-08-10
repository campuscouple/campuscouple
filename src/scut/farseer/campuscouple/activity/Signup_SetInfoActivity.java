package scut.farseer.campuscouple.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import scut.farseer.campuscouple.AppConsts;
import scut.farseer.campuscouple.HttpTask;
import scut.farseer.campuscouple.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import lib.ripple.photo.*;



public class Signup_SetInfoActivity extends ImageActivity implements OnClickListener
{
	ImageView head_picture;
	Button btn_later;
	Button btn_done;
	Spinner time_of_enrollment;
	EditText profession;
	Spinner gender;
	EditText nickname;
	List<String> enroll_list;
	String access_token;
	int user_id;
	String genderValue;
	String enroll_year;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	
	String[] genderValues = new String[]{"m", "f"};
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_set_info);
		
		head_picture = (ImageView) findViewById(R.id.head_picture);
		btn_done = (Button) findViewById(R.id.btn_done);
		btn_later = (Button) findViewById(R.id.btn_later);
		time_of_enrollment = (Spinner) findViewById(R.id.time_of_enrollment);
		profession = (EditText) findViewById(R.id.profession);
		gender = (Spinner) findViewById(R.id.gender);
		nickname = (EditText) findViewById(R.id.nickname);
		
		String[] genderList = getResources().getStringArray(R.array.gender);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				R.layout.spinner_item, genderList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		gender.setAdapter(adapter);
		
		
		enroll_list = new ArrayList<String>();
		int current_year = Calendar.getInstance().get(Calendar.YEAR);
		for(int i = current_year; i >= 2000; i--)
			enroll_list.add(String.valueOf(i));
		adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, enroll_list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		time_of_enrollment.setAdapter(adapter);
		
		preferences = getSharedPreferences(AppConsts.PREFERENCE_NAME, MODE_PRIVATE);
		editor = preferences.edit();
		
		access_token = preferences.getString("access_token", "");
		user_id = preferences.getInt("user_id", 0);
		
		
		head_picture.setOnClickListener(this);
		btn_done.setOnClickListener(this);
		btn_later.setOnClickListener(this);
		
		
		gender.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				genderValue = genderValues[position];
			}

			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
		
		
		time_of_enrollment.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				enroll_year = enroll_list.get(position);
			}

			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
	}

	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.head_picture:
				setHeadPicture();
				break;
				
			case R.id.btn_later:
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
				finish();
				break;
				
			case R.id.btn_done:
				setUserInfo();
				break;

			default:
				break;
		}
	}
	
	private void setHeadPicture()
	{
		Dialog dialog = new PictureDialog(this);
		dialog.show();
	}
	
	private void setUserInfo()
	{
		HttpTask task = new HttpTask(this) {
			
			public void callback(String apiUrl, JSONObject jo)
			{
				if(jo != null)
				{
					try
					{
						int status = jo.getJSONObject("json").getInt("status");
						if(status == 200)
						{
							Intent intent = new Intent(Signup_SetInfoActivity.this, MainActivity.class);
							Signup_SetInfoActivity.this.startActivity(intent);
							Signup_SetInfoActivity.this.finish();
						}
						else
							Toast.makeText(Signup_SetInfoActivity.this, "error" + status, Toast.LENGTH_SHORT).show();
					}
					catch (JSONException e)
					{
						Toast.makeText(Signup_SetInfoActivity.this, "exception", Toast.LENGTH_SHORT).show();
					}
				}
			}
		};
		
		task.url("/userinfo/set")
			.addParam("access_token", access_token)
			.addParam("user_id", user_id + "")
			.addParam("sex", genderValue)
			.addParam("enroll_year", enroll_year);
		
		String nickname_str = nickname.getText().toString();
		if(nickname_str.length() >= 1 && nickname_str.length() <= 10)
			task.addParam("nickname", nickname_str);
		
		String profession_str = profession.getText().toString();
		if(profession_str.length() > 0 && profession_str.length() < 20)
			task.addParam("profession", profession_str);
		
		task.sendRequest();
	}

	public void onDeterminePhoto(final String pathName)
	{
		HttpTask task = new HttpTask(this) {
			
			public void callback(String apiUrl, JSONObject jo)
			{
				if(jo != null)
				{
					try
					{
						int status = jo.getJSONObject("json").getInt("status");
						if(status == 200)
							displayPhoto(head_picture, pathName);
						else
						{
							String message = "status: " + status + "\n" + "data: "
										+ jo.getJSONObject("json").getString("data");
							Toast.makeText(Signup_SetInfoActivity.this, 
									message, Toast.LENGTH_SHORT).show();
						}
					}
					catch (JSONException e)
					{
					}
				}
			}
		};
		
		task.url("/user/upload")
			.addParam("access_token", access_token)
			.addParam("user_id", user_id + "")
			.addParam("img_file", new File(pathName))
			.sendRequest();
	}

	public void onBackPressed()
	{
		super.onBackPressed();
		HttpTask task = new HttpTask(this) {
			
			public void callback(String apiUrl, JSONObject jo)
			{
				editor.remove("access_token");
				editor.remove("user_id");
				editor.commit();
				Intent intent = new Intent(Signup_SetInfoActivity.this, LoginActivity.class);
				Signup_SetInfoActivity.this.startActivity(intent);
				Signup_SetInfoActivity.this.finish();
			}
		};
		
		task.url("/user/logout")
			.addParam("access_token", access_token)
			.addParam("user_id", user_id + "")
			.sendRequest();
	}

}
