package Utils;

import android.support.design.widget.AppBarLayout;

/**
 * 项目名称：Cartoon8
 * 类描述：
 * 创建人：SmileSB101
 * 创建时间：2017/3/10 0010 08:56
 * 修改人：Administrator
 * 修改时间：2017/3/10 0010 08:56
 * 修改备注：
 */

public abstract class AppBarStateChangeLinstener implements AppBarLayout.OnOffsetChangedListener {

	public enum State {
		EXPANDED,
		COLLAPSED,
		IDLE
	}

	private State mCurrentState = State.IDLE;

	@Override
	public final void onOffsetChanged(AppBarLayout appBarLayout,int i) {
		if (i == 0) {
			if (mCurrentState != State.EXPANDED) {
				onStateChanged(appBarLayout, State.EXPANDED);
			}
			mCurrentState = State.EXPANDED;
		} else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
			if (mCurrentState != State.COLLAPSED) {
				onStateChanged(appBarLayout, State.COLLAPSED);
			}
			mCurrentState = State.COLLAPSED;
		} else {
			if (mCurrentState != State.IDLE) {
				onStateChanged(appBarLayout, State.IDLE);
			}
			mCurrentState = State.IDLE;
		}
	}

	public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
}
