package customView.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 项目名称：Cartoon8
 * 类描述：
 * 创建人：SmileSB101
 * 创建时间：2017/3/2 0002 12:44
 * 修改人：Administrator
 * 修改时间：2017/3/2 0002 12:44
 * 修改备注：
 */

public class main_top_behavior extends CoordinatorLayout.Behavior{
	public main_top_behavior(Context context,AttributeSet attrs){
		super(context,attrs);
	}

	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent,View child,View dependency){
		return dependency instanceof LinearLayout;
	}

	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent,View child,View dependency){
		return super.onDependentViewChanged(parent,child,dependency);
	}
}
