package UI.LayoutManager;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * 项目名称：Cartoon8
 * 类描述：自定义的LayoutManager(实现recycler不能滚动)
 * 创建人：SmileSB101
 * 创建时间：2017/3/7 0007 20:49
 * 修改人：Administrator
 * 修改时间：2017/3/7 0007 20:49
 * 修改备注：
 */

public class CustomStraggerLayoutManager extends StaggeredGridLayoutManager{
	public CustomStraggerLayoutManager(Context context,AttributeSet attrs,int defStyleAttr,int defStyleRes){
		super(context,attrs,defStyleAttr,defStyleRes);
	}

	public CustomStraggerLayoutManager(int spanCount,int orientation){
		super(spanCount,orientation);
	}

	@Override
	public boolean canScrollVertically(){
		return false;
	}

	@Override
	public boolean canScrollHorizontally(){
		return false;
	}
}
