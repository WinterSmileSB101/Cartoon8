package UI.Animation;

import android.view.View;

/**
 * 项目名称：Cartoon8
 * 类描述：动画类
 * 创建人：SmileSB101
 * 创建时间：2017/3/12 0012 12:34
 * 修改人：Administrator
 * 修改时间：2017/3/12 0012 12:34
 * 修改备注：
 */

public final class animationStatic{
	/**
	 * 向一个方向移动自己长度
	 * @param obj 需要移动的对象
	 * @param dir 方向
	 *            0上 1 下 2 左 3右
	 */
	public static void translationAnimationTo(View obj,int dir)
	{
		int width,height;
		switch(dir)
		{
			case 0://上
				height = obj.getHeight();
				break;
			case 1://下
				height = obj.getHeight();
				break;
			case 2://左
				width = obj.getWidth();
				break;
			case 3://右
				width = obj.getWidth();
				break;
		}
	}
}
