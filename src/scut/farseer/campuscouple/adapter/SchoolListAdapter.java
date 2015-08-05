package scut.farseer.campuscouple.adapter;

import java.util.*;

import scut.farseer.campuscouple.R;
import scut.farseer.campuscouple.model.School;
import android.content.Context;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SchoolListAdapter extends BaseAdapter
{
	
	private List<School> mSchoolList;
	private Context mContext;
	private LayoutInflater mInflater;
	
	public SchoolListAdapter(Context context, List<School> schoolList)
	{
		this.mContext = context;
		this.mSchoolList = schoolList;
		this.mInflater = LayoutInflater.from(context);
	}

	public int getCount()
	{
		return this.mSchoolList.size();
	}

	public Object getItem(int position)
	{
		return mSchoolList.get(position);
	}

	public long getItemId(int position)
	{
		return mSchoolList.get(position).id;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		
		if(convertView == null)
		{
			convertView = this.mInflater.inflate(R.layout.spinner_item, null);
			holder = new ViewHolder();
			
			holder.text = (TextView) convertView.findViewById(R.id.text);
			
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		holder.text.setText(mSchoolList.get(position).name);
		
		return convertView;
	}
	
	public final class ViewHolder
	{
		public TextView text;
	}
}
