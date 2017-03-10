package RetailsWorm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.util.Calendar;
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
	public static void getHtmlPage(Context context,String url,Handler Hanlder)
	{
		handler = Hanlder;
		try {
			webView = new WebView(context);
			webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不缓存
			webView.getSettings().setJavaScriptEnabled(true);
			webView.addJavascriptInterface(new pageInterface(),"PageInterface");
			webView.setWebViewClient(new WebViewClient(){
				@Override
				public void onPageFinished(WebView view,String url){
					//网页加载完成
					view.loadUrl("javascript:window.PageInterface.showPageHtml('<html>'+"+"document.getElementsByTagName('html')[0].innerHTML+'</html>');");
				}

				@Override
				public void onReceivedError(WebView view,WebResourceRequest request,WebResourceError error){
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
