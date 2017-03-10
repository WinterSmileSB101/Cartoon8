package winter.zxb.smilesb101.cartoon8;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

import ComicData.Comic;
import UI.Adapter.contentMainSectionAdapter;
import UI.Adapter.main_recyclerViewAdapter;
import UI.StatusBarUtils.statusBarUtils;

public class MyLancherActivity extends AppCompatActivity{

	private TabLayout tabLayout;
	private ViewPager viewPager;
	private contentMainSectionAdapter viewAdpater;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		statusBarUtils.StatusBarLightMode(this);//设置状态栏字体图标风格为灰色
		setContentView(R.layout.activity_my_lancher);
		InitView();
		InitValue();
	}

	void InitView()
	{
		viewPager = (ViewPager)findViewById(R.id.activity_my_lancher_viewPager);
		tabLayout = (TabLayout)findViewById(R.id.activity_my_lancher_tablayout);
	}

	void InitValue()
	{
		viewAdpater = new contentMainSectionAdapter(getSupportFragmentManager());
		viewPager.setAdapter(viewAdpater);
		tabLayout.setupWithViewPager(viewPager);
		ArrayList<Integer> icons = new ArrayList<>();
		icons.add(R.drawable.book_case);
		icons.add(R.drawable.book_stack);
		icons.add(R.drawable.discoery_btn);
		icons.add(R.drawable.people_btn);

		ArrayList<String> titles = new ArrayList<>();
		titles.add(getString(R.string.main_viewPagerTitle_one));
		titles.add(getString(R.string.main_viewPagerTitle_two));
		titles.add(getString(R.string.main_viewPagerTitle_three));
		titles.add(getString(R.string.main_viewPagerTitle_four));
		for(int i = 0;i<tabLayout.getTabCount();i++)
		{
			TabLayout.Tab tab = tabLayout.getTabAt(i);
			if(tab!=null)
			{
				tab.setIcon(icons.get(i));
				tab.setText(titles.get(i));
			}
		}
	}
}
