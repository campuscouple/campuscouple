package scut.farseer.campuscouple;

import java.io.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

import android.app.Activity;
import android.content.*;
import android.os.AsyncTask;
import android.widget.Toast;

public abstract class HttpTask
{
	public static String DOMAIN_NAME = "http://120.24.97.202";

	private String mApiUrl;
	private Map<String, String> mStringParam = null;
	private Map<String, File> mFileParam = null;
	private Activity mActivity;

	public HttpTask(Activity activity)
	{
		this.mActivity = activity;
		this.mStringParam = new HashMap<String, String>();
		this.mFileParam = new HashMap<String, File>();
	}

	public void clearParams()
	{
		this.mStringParam.clear();
		this.mFileParam.clear();
	}

	public HttpTask url(String apiUrl)
	{
		this.mApiUrl = apiUrl;
		return this;
	}

	public HttpTask addParam(String key, String value)
	{
		this.mStringParam.put(key, value);
		return this;
	}

	public HttpTask addParam(String key, File file)
	{
		this.mFileParam.put(key, file);
		return this;
	}

	public void sendRequest()
	{
		_HttpTask task = new _HttpTask(this, this.mStringParam, this.mFileParam);
		task.execute(this.mApiUrl);
	}

	private void _callback(JSONObject jo)
	{
		if(jo == null)
			Toast.makeText(this.mActivity, "网络异常", Toast.LENGTH_SHORT).show();
		
		if(this.mApiUrl.equals("/user/login"))
		{
			if(jo != null)
			{
				try
				{
					jo = jo.getJSONObject("json");
					int status = jo.getInt("status");
					if(status == 200)
					{
						jo = jo.getJSONObject("data");
						String access_token = jo.getString("access_token");
						int user_id = jo.getInt("user_id");
						
						SharedPreferences preferences = mActivity.getSharedPreferences(
								AppConsts.PREFERENCE_NAME, mActivity.MODE_PRIVATE);
						SharedPreferences.Editor editor = preferences.edit();
						
						editor.putBoolean("isLogined", true);
						editor.putString("access_token", access_token);
						editor.putInt("user_id", user_id);
						editor.commit();
						
					}
				}
				catch (JSONException e)
				{
				}
			}
		}
		
		
		
		this.callback(this.mApiUrl, jo);
	}

	public abstract void callback(String apiUrl, JSONObject jo);

	private class _HttpTask extends AsyncTask<String, Integer, String>
	{
		private Map<String, String> mStringParam = null;
		private Map<String, File> mFileParam = null;
		private HttpTask mTask = null;

		public _HttpTask(HttpTask task, Map<String, String> stringParam,
				Map<String, File> fileParam)
		{
			this.mTask = task;
			this.mFileParam = fileParam;
			this.mStringParam = stringParam;
		}

		protected String doInBackground(String... params)
		{
			String result = null;
			String url = null;
			if (params.length > 0)
				url = params[0];

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(HttpTask.DOMAIN_NAME + url);
			try
			{

				if (this.mFileParam.size() > 0)
				{
					MultipartEntityBuilder builder = MultipartEntityBuilder
							.create();
					for (String key : this.mFileParam.keySet())
					{
						builder.addBinaryBody(key,
								(File) this.mFileParam.get(key));
					}

					for (String key : this.mStringParam.keySet())
					{
						builder.addTextBody(key,
								(String) this.mStringParam.get(key));
					}

					post.setEntity(builder.build());
				}
				else
				{
					List<NameValuePair> parameters = new ArrayList<NameValuePair>();

					for (String key : this.mStringParam.keySet())
					{
						parameters.add(new BasicNameValuePair(key,
								(String) this.mStringParam.get(key)));
					}

					UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
							parameters);
					post.setEntity(formEntity);
				}

				HttpResponse response = httpClient.execute(post);

				if (HttpStatus.SC_OK == response.getStatusLine()
						.getStatusCode())
				{
					HttpEntity entity = response.getEntity();

					if (entity != null)
					{
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(entity.getContent()));
						String line = null;
						StringBuilder builder = new StringBuilder();
						while ((line = reader.readLine()) != null)
							builder.append(line);
						result = builder.toString();
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			return result;
		}

		protected void onPreExecute()
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		protected void onPostExecute(String result)
		{
			JSONObject jo;
			try
			{
				jo = new JSONObject(result);
			}
			catch (Exception e)
			{
				jo = null;
			}

			this.mTask._callback(jo);
		}

		protected void onCancelled(String result)
		{
			// TODO Auto-generated method stub
			super.onCancelled(result);
		}

		protected void onCancelled()
		{
			// TODO Auto-generated method stub
			super.onCancelled();
		}

	}

}
