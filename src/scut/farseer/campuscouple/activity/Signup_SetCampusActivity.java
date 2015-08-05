package scut.farseer.campuscouple.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import scut.farseer.campuscouple.AppConsts;
import scut.farseer.campuscouple.HttpTask;
import scut.farseer.campuscouple.R;
import scut.farseer.campuscouple.adapter.SchoolListAdapter;
import scut.farseer.campuscouple.model.Campus;
import scut.farseer.campuscouple.model.School;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.camera2.CameraCaptureSession;
import android.media.CamcorderProfile;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Signup_SetCampusActivity extends Activity implements
		OnClickListener
{
	Spinner select_school;
	Spinner select_campus;
	Button select_campus_next;
	ArrayList<School> schoolList = new ArrayList<School>();
	ArrayList<Campus> campusList = new ArrayList<Campus>();
	int selected_school_id = 0;
	int selected_campus_id = 0;

	String access_token;
	int user_id;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_set_campus);

		select_school = (Spinner) findViewById(R.id.select_school);
		select_campus = (Spinner) findViewById(R.id.select_campus);
		select_campus_next = (Button) findViewById(R.id.select_campus_next);

		SharedPreferences preferences = getSharedPreferences(
				AppConsts.PREFERENCE_NAME, MODE_PRIVATE);
		if (preferences.getBoolean("isLogined", false))
		{
			access_token = preferences.getString("access_token", "");
			user_id = preferences.getInt("user_id", 0);
		}
		else
		{
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}

		select_campus_next.setOnClickListener(this);

		select_school.setOnItemSelectedListener(new OnSchoolListItemSelected());
		select_campus.setOnItemSelectedListener(new OnCampusListItemSelected());

		HttpTask task = new HttpTask(this) {

			public void callback(String apiUrl, JSONObject jo)
			{
				if (jo != null)
				{
					try
					{
						int status = jo.getJSONObject("json").getInt("status");
						if (status == 200)
						{
							JSONArray list = jo.getJSONObject("json")
									.getJSONArray("data");

							schoolList.clear();

							ArrayList<String> schoolNames = new ArrayList<String>();

							int length = list.length();
							for (int i = 0; i < length; i++)
							{
								School data = new School();
								data.fromJson(list.getJSONObject(i));
								schoolList.add(data);
								schoolNames.add(schoolList.get(i).name);
							}

							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									Signup_SetCampusActivity.this,
									android.R.layout.simple_spinner_dropdown_item,
									schoolNames);

							select_school.setAdapter(adapter);
						}

					}
					catch (JSONException e)
					{
					}
				}
			}
		};

		task.url("/school/list").sendRequest();
	}

	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.select_campus_next:
				onSelectCampusNext();
				break;

			default:
				break;
		}
	}
	
	public void onSelectCampusNext()
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
							Intent intent = new Intent(Signup_SetCampusActivity.this, 
									Signup_SetInfoActivity.class);
							Signup_SetCampusActivity.this.startActivity(intent);
							Signup_SetCampusActivity.this.finish();
						}
						else if(status == 410)
						{
							Intent intent = new Intent(Signup_SetCampusActivity.this, 
									LoginActivity.class);
							Signup_SetCampusActivity.this.startActivity(intent);
							Signup_SetCampusActivity.this.finish();
						}
					}
					catch (JSONException e)
					{
					}
				}
			}
		};
		
		task.url("/user/campus/set")
			.addParam("access_token", access_token)
			.addParam("user_id", "" + user_id)
			.addParam("campus_id", selected_campus_id + "")
			.sendRequest();
	}

	public class OnSchoolListItemSelected implements OnItemSelectedListener
	{
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id)
		{
			selected_school_id = schoolList.get(position).id;
			HttpTask task = new HttpTask(Signup_SetCampusActivity.this) {

				public void callback(String apiUrl, JSONObject jo)
				{
					if (jo != null)
					{
						try
						{
							int status = jo.getJSONObject("json").getInt(
									"status");
							if (status == 200)
							{
								JSONArray list = jo.getJSONObject("json")
										.getJSONArray("data");

								campusList.clear();

								ArrayList<String> campusNames = new ArrayList<String>();

								int length = list.length();
								for (int i = 0; i < length; i++)
								{
									Campus data = new Campus();
									data.fromJson(list.getJSONObject(i));
									campusList.add(data);
									campusNames.add(schoolList.get(i).name);
								}

								ArrayAdapter<String> adapter = new ArrayAdapter<String>(
										Signup_SetCampusActivity.this,
										android.R.layout.simple_spinner_dropdown_item,
										campusNames);

								select_campus.setAdapter(adapter);
							}
						}
						catch (JSONException e)
						{
						}
					}
				}
			};
			
			selected_campus_id = 0;
			task.url("/campus/list")
				.addParam("school_id", selected_school_id + "")
				.sendRequest();
		}

		public void onNothingSelected(AdapterView<?> parent)
		{
		}
	}

	public class OnCampusListItemSelected implements OnItemSelectedListener
	{
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id)
		{
			selected_campus_id = campusList.get(position).id;
		}

		public void onNothingSelected(AdapterView<?> parent)
		{
		}
	}

	public void onBackPressed()
	{
		super.onBackPressed();
		Intent intent = new Intent(Signup_SetCampusActivity.this, 
				LoginActivity.class);
		Signup_SetCampusActivity.this.startActivity(intent);
		Signup_SetCampusActivity.this.finish();
	}
}
