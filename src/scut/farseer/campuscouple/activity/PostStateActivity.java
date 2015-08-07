package scut.farseer.campuscouple.activity;

import org.json.JSONException;
import org.json.JSONObject;

import scut.farseer.campuscouple.AppConsts;
import scut.farseer.campuscouple.HttpTask;
import scut.farseer.campuscouple.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PostStateActivity extends Activity implements OnClickListener
{
	ImageButton btn_return;
	ImageButton btn_post;
	ImageButton btn_picture;
	ImageButton btn_emoji;
	EditText content_input;
	
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.posting_state_layout);
		
		btn_return = (ImageButton) findViewById(R.id.btn_return);
		btn_post = (ImageButton) findViewById(R.id.btn_post);
		btn_picture = (ImageButton) findViewById(R.id.btn_picture);
		btn_emoji = (ImageButton) findViewById(R.id.btn_emoji);
		content_input = (EditText) findViewById(R.id.content);
		
		btn_return.setOnClickListener(this);
		btn_post.setOnClickListener(this);
		btn_picture.setOnClickListener(this);
		btn_emoji.setOnClickListener(this);
		
	}


	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.btn_return:
				finish();
				break;
				
			case R.id.btn_post:
				postNewState();
				break;
				
			case R.id.btn_picture:
				break;
				
			case R.id.btn_emoji:
				break;

			default:
				break;
		}
	}


	public void onBackPressed()
	{
		super.onBackPressed();
		finish();
	}
	
	private void postNewState()
	{
		SharedPreferences preferences = getSharedPreferences(AppConsts.PREFERENCE_NAME, MODE_PRIVATE);
		String content = content_input.getText().toString();
		String access_token = preferences.getString("access_token", "");
		int user_id = preferences.getInt("user_id", 0);
		
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
							int post_id = jo.getJSONObject("json").getInt("data");
							PostStateActivity.this.uploadPostImages(post_id);
						}
						
					}
					catch (JSONException e)
					{
					}
					
				}
			}
		};
		
		task.url("/user/post/add")
			.addParam("access_token", access_token)
			.addParam("user_id", "" + user_id)
			.addParam("content", content)
			.sendRequest();
	}
	
	private void uploadPostImages(int post_id)
	{
		//TODO: 上传文章图片
		Toast.makeText(PostStateActivity.this, "post id: " + post_id, Toast.LENGTH_SHORT).show();
		PostStateActivity.this.finish();
	}
}
