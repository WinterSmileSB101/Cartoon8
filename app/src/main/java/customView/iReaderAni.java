package customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 项目名称：Cartoon8
 * 类描述：掌阅的动画
 * 创建人：SmileSB101
 * 创建时间：2017/3/2 0002 08:52
 * 修改人：Administrator
 * 修改时间：2017/3/2 0002 08:52
 * 修改备注：
 */

public class iReaderAni extends View{

	private Context context;
	private float time;
	private Paint paint;

	public iReaderAni(Context context){
		super(context);
	}

	public iReaderAni(Context context,AttributeSet attrs){
		super(context,attrs);
	}

	public iReaderAni(Context context,AttributeSet attrs,int defStyleAttr){
		super(context,attrs,defStyleAttr);
	}

	void InitView(Context context)
	{}

	/**
	 * 绘画
	 * @param canvas
	 */
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
	}

	/**
	 * 画书
	 * @param canvas
	 */
	void drawBook(Canvas canvas){
		paint.setColor(Color.GRAY);
		paint.setAntiAlias(true);//取消锯齿
		canvas.drawArc(new RectF(40,40,40,40),0,120,true,paint);
	}

	/**
	 * 动画进度控制
	 * @param time
	 */
	public void setTime(float time)
	{
		this.time = time;
	}
}
