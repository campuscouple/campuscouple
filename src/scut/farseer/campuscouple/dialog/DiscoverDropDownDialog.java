package scut.farseer.campuscouple.dialog;

import scut.farseer.campuscouple.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class DiscoverDropDownDialog extends Dialog
{
	public static final int ID_FOR_BTN_WHOLE_SCHOOL = R.id.btn_the_whole_school;
	public static final int ID_FOR_BTN_I_FOLLOWED = R.id.btn_who_i_followed;
	public static final int ID_FOR_BTN_ALL_GENDER = R.id.btn_all_gender;
	public static final int ID_FOR_BTN_MALE = R.id.btn_male;
	public static final int ID_FOR_BTN_FEMALE = R.id.btn_female;
	
	private android.view.View.OnClickListener listener;

	public DiscoverDropDownDialog(Context context, 
			android.view.View.OnClickListener listener)
	{
		super(context, R.style.no_title_frame_dialog);
		this.listener = listener;
	}

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discover_drop_down_list_layout);
		
		setCanceledOnTouchOutside(true);
		
		findViewById(R.id.btn_the_whole_school).setOnClickListener(listener);
		findViewById(R.id.btn_who_i_followed).setOnClickListener(listener);
		findViewById(R.id.btn_all_gender).setOnClickListener(listener);
		findViewById(R.id.btn_male).setOnClickListener(listener);
		findViewById(R.id.btn_female).setOnClickListener(listener);
	}
}
