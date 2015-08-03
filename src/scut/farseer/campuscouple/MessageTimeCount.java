package scut.farseer.campuscouple;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 
 * 发送短信的倒计时显示
 * 
 * 
 * @author luo.mingnan
 *
 */

public class MessageTimeCount extends CountDownTimer
{

	private TextView v = null;

	public MessageTimeCount(long millisInFuture, long countDownInterval)
	{
		super(millisInFuture, countDownInterval);
	}

	public MessageTimeCount(long millisInFuture, long countDownInterval,
			TextView tv)
	{
		super(millisInFuture, countDownInterval);
		v = tv;
	}

	@Override
	public void onTick(long millisUntilFinished)
	{
		v.setClickable(false);
		v.setText(millisUntilFinished / 1000 + "s后可重发");
	}

	@Override
	public void onFinish()
	{
		v.setText("发送验证码");
		v.setClickable(true);
	}
}
