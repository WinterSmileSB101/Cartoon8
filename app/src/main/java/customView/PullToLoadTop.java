package customView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 项目名称：Cartoon8
 * 类描述：下拉加载头布局
 * 创建人：SmileSB101
 * 创建时间：2017/3/2 0002 08:45
 * 修改人：Administrator
 * 修改时间：2017/3/2 0002 08:45
 * 修改备注：
 */

public class PullToLoadTop extends RelativeLayout{
	private Context context;

	public PullToLoadTop(Context context){
		super(context);
		InitView(context);
	}

	public PullToLoadTop(Context context,AttributeSet attrs){
		super(context,attrs);
		InitView(context);
	}

	public PullToLoadTop(Context context,AttributeSet attrs,int defStyleAttr){
		super(context,attrs,defStyleAttr);
		InitView(context);
	}

	//初始化View
	void InitView(Context context)
	{}
}
