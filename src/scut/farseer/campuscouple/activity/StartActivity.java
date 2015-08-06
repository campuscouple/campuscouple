package scut.farseer.campuscouple.activity;

import scut.farseer.campuscouple.AppConsts;
import scut.farseer.campuscouple.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class StartActivity extends Activity
{
	SharedPreferences preferences;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		final View startView = View.inflate(this, R.layout.activity_start, null);
		setContentView(startView);
		
		preferences = getSharedPreferences(AppConsts.PREFERENCE_NAME, MODE_PRIVATE);
		
		// 渐变
		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		aa.setDuration(50);
		startView.setAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation)
			{
			}

			public void onAnimationRepeat(Animation animation)
			{
			}

			public void onAnimationEnd(Animation animation)
			{
				if(preferences.getBoolean("isLogined", false))
				{
					Intent intent = new Intent(StartActivity.this, MainActivity.class);
					startActivity(intent);
					
				}
				else
				{
					Intent intent = new Intent(StartActivity.this, LoginActivity.class);
					startActivity(intent);
				}
				
				StartActivity.this.finish();
			}
		});
	}
}
