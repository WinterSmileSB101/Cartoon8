package winter.zxb.smilesb101.cartoon8;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.DummyPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import ComicData.Comic;
import ComicData.ComicClass;
import ComicData.ComicRankList;
import RetailsWorm.HtmlAnalysisUtils;
import RetailsWorm.NetWorkUtils;
import UI.Adapter.contentMainSectionAdapter;

import static ComicData.comicStaticValue.CLASS_LINK;
import static ComicData.comicStaticValue.CLASS_NAME;
import static ComicData.comicStaticValue.RankList_TailString_COMIC_LASESTNAME;
import static ComicData.comicStaticValue.RankList_TailString_COMIC_NAME;
import static ComicData.comicStaticValue.RankList_TailString_COMIC_PIC;
import static ComicData.comicStaticValue.RankList_TailString_RANKNAME;
import static ComicData.comicStaticValue.RankList_patternNames;
import static ComicData.comicStaticValue.RankList_TailString_COMIC_LINK;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener{

	private static Context context;
	private static ImageView imageView;
	private ViewPager viewPager;
	private MagicIndicator magicIndicator;



	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				Snackbar.make(view,"Replace with your own action",Snackbar.LENGTH_LONG)
						.setAction("Action",null).show();
			}
		});

		DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		InitView();
		InitValue();

	}
	void InitView()
	{
		imageView = (ImageView)findViewById(R.id.loading);
		viewPager = (ViewPager)findViewById(R.id.content_main_viewPager);
		magicIndicator = (MagicIndicator)findViewById(R.id.toolbar_tablayout);
	}

//img[src~=(?i)\.(jpe?g)]
	static void setLoading()
	{
		Glide.with(context)
				.load(R.mipmap.loading)
				.into(imageView);
		//http://imgs.zhujios.com/manhuatuku/26219/20170228112339-b7367b.jpg
	}

	void InitValue()
	{
		contentMainSectionAdapter sectionAdapter = new contentMainSectionAdapter(getSupportFragmentManager());
		viewPager.setAdapter(sectionAdapter);

		CommonNavigator commonNavigator = new CommonNavigator(this);
		commonNavigator.setAdjustMode(true);
		commonNavigator.setAdapter(new CommonNavigatorAdapter(){
			@Override
			public int getCount(){
				return 2;
			}

			@Override
			public IPagerTitleView getTitleView(Context context,int index){
				ClipPagerTitleView titleView = new ClipPagerTitleView(context);
				switch(index) {
					case 0:
						titleView.setText(getString(R.string.main_viewPagerTitle_one));
						break;
					case 1:
						titleView.setText(getString(R.string.main_viewPagerTitle_two));
						break;
					default:
						break;
				}
				return titleView;
			}

			@Override
			public IPagerIndicator getIndicator(Context context){
				BezierPagerIndicator indicator = new BezierPagerIndicator(context);
				indicator.setColors(Color.YELLOW,Color.RED);
				return indicator;
			}
		});
		magicIndicator.setNavigator(commonNavigator);
		ViewPagerHelper.bind(magicIndicator,viewPager);
	}

	@Override
	public void onBackPressed(){
		DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
		if(drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if(id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item){
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if(id == R.id.nav_camera) {
			// Handle the camera action
		} else if(id == R.id.nav_gallery) {

		} else if(id == R.id.nav_slideshow) {

		} else if(id == R.id.nav_manage) {

		} else if(id == R.id.nav_share) {

		} else if(id == R.id.nav_send) {

		}

		DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
