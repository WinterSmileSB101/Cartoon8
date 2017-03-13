package RetailsWorm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 项目名称：Cartoon8
 * 类描述：网页捕获类
 * 创建人：SmileSB101
 * 创建时间：2017/2/28 0028 12:09
 * 修改人：Administrator
 * 修改时间：2017/2/28 0028 12:09
 * 修改备注：
 */

public class NetWorkUtils{
	private static final String TAG = "NetWorkUtils";

	/**
	 * 空网页
	 */
	public static final String HTML_EMPTY = "<html><head></head><body></body></html>";


	public static final int MSG_IMAGE_SRC = 0;
	public static final int MSG_TITLE_TEXT = 1;
	public static final int MSG_HTML = 2;
	public static final int MSG_ERROR = -1;

	private static WebView webView;

	private static Handler handler;

	@SuppressLint("JavascriptInterface")
	public static void getHtmlPage(Activity activity,String url,Handler Hanlder)
	{
		handler = Hanlder;
		try {
			webView = new WebView(activity);
			WebSettings settings = webView.getSettings();
			//settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不缓存
			//settings.setBlockNetworkLoads(false);//阻止网络数据加载，提高速度
			settings.setLoadsImagesAutomatically(false);//不自动加载图片
			settings.setBlockNetworkImage(true);//阻止图片数据，提高速度
			settings.setJavaScriptEnabled(true);
			settings.setAppCacheEnabled(true);
			settings.setDatabaseEnabled(true);
			settings.setDomStorageEnabled(true);//开启DOM缓存，关闭的话H5自身的一些操作是无效的

			//根据网络情况来获取数据
			ConnectivityManager cm = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			if(info.isAvailable())
			{
				settings.setCacheMode(WebSettings.LOAD_DEFAULT);//自行决定是否使用缓存
			}else
			{
				settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);//不使用网络，只加载缓存
			}

			webView.addJavascriptInterface(new pageInterface(),"PageInterface");
			webView.setWebViewClient(new WebViewClient(){
				@Override
				public void onPageFinished(WebView view,String url){
					//网页加载完成
					Log.i(TAG,"onPageFinished: 网页加载完成");
					view.loadUrl("javascript:window.PageInterface.showPageHtml('<html>'+" +
							"document.getElementsByTagName('html')[0].innerHTML+'</html>');");
				}

				@Override
				public void onReceivedError(WebView view,WebResourceRequest request,WebResourceError error){
					Log.i(TAG,"onReceivedError: 网页获取错误"+error.toString());
					dispatchMessage(handler,MSG_ERROR,error.toString());
				}
			});
			webView.loadUrl(url);
		}
		catch(Exception e)
		{
			Log.i(TAG,"getHtmlPage: ERROR"+e.getMessage());
			dispatchMessage(handler,MSG_ERROR,"ERROR");
		}
	}

	static class pageInterface{
		@JavascriptInterface
		public void showPageHtml(String html)
		{
			//Log.i(TAG,"showPageHtml: 获取到网页数据："+html);
			dispatchMessage(handler,MSG_HTML,html);
		}
	}

	/**
	 * 分发消息
	 * @param handler
	 * @param what
	 * @param content
	 */
	private static void dispatchMessage(Handler handler,int what,String content)
	{
		Message msg = new Message();
		msg.what = what;
		msg.obj = content;
		handler.sendMessage(msg);
	}

}
