package scut.farseer.campuscouple.activity;

import scut.farseer.campuscouple.R;
import scut.farseer.campuscouple.fragment.DiscoverHomeFragment;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener
{
	ImageButton button_discover;
	ImageButton button_ordering;
	ImageButton button_me;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage_layout);
		
		button_discover = (ImageButton) findViewById(R.id.button_discover);
		button_ordering = (ImageButton) findViewById(R.id.button_ordering);
		button_me = (ImageButton) findViewById(R.id.button_me);
		
		button_discover.setOnClickListener(this);
		button_ordering.setOnClickListener(this);
		button_me.setOnClickListener(this);
		
		buttonDiscoverSelected();
	}

	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.button_discover:
				buttonDiscoverSelected();
				break;
				
			case R.id.button_ordering:
				buttonOrderingSelected();
				break;
				
			case R.id.button_me:
				buttonMeSelected();
				break;

			default:
				break;
		}
	}
	
	private void buttonDiscoverSelected()
	{
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Fragment fragment = new DiscoverHomeFragment();
		transaction.replace(R.id.homepage_content, fragment);
		transaction.commit();
	}
	
	private void buttonOrderingSelected()
	{
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Fragment fragment = new DiscoverHomeFragment();
		transaction.replace(R.id.homepage_content, fragment);
		transaction.commit();
	}
	
	private void buttonMeSelected()
	{
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Fragment fragment = new DiscoverHomeFragment();
		transaction.replace(R.id.homepage_content, fragment);
		transaction.commit();
	}
}
