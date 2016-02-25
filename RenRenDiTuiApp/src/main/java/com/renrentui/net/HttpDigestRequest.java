package com.renrentui.net;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.util.Log;

import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.http.client.multipart.content.StringBody;
import com.renrentui.tools.Constants;

/**
 * Digest认证的网络请求
 * 
 * @author back
 * 
 */
public class HttpDigestRequest {

	private InputStream is = null;
	private String username = "back";
	private String password = "chokeback";
	private CookieStore cookieStore;
	private HttpGet get;
	private DefaultHttpClient client;
	private HttpPost post;
	private int connectTimeOut = 30000;// 连接超时
	private int soTimeout = 30000;// socket请求超时 数据流传输超时

	public int getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(int connectTimeOut) {
		//最低5秒 默认30s
		this.connectTimeOut = connectTimeOut>5000?connectTimeOut:30000;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		//最低5秒 默认30s
		this.soTimeout = soTimeout>5000?soTimeout:30000;
	}

	public  HttpDigestRequest() {
		this.client = getNewHttpClient();
	}

	/**
	 * 请求的HttpClient
	 */
	private DefaultHttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);

			//使用指定证书创建安全套接字
			SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();//组件参数
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			//协议集合
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	private void setHost(Context context, DefaultHttpClient client) {
		int netType = NetworkValidator.getConnectedType(context);
		if (netType == Constants.MOBILE) {
			HttpHost proxy = new HttpHost("10.0.0.172", 80);// 设置cmwap代理
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
			client.getParams()
					.setParameter(ConnRoutePNames.DEFAULT_PROXY, null);
		} else if (netType == Constants.WIFI) {

		}
	}

	

	/**
	 * httpclient设置
	 */
	private void setClient() {
		if (client == null) {
			// TODO Auto-generated method stub
			client = getNewHttpClient();
		}
		if (cookieStore != null) {
			client.setCookieStore(cookieStore);
		}
	}

	/**
	 * 超时设置
	 * 
	 * @param connectTimeOut
	 * @param soTimeout
	 */
	private void setTimeOut(int connectTimeOut, int soTimeout) {
		if (connectTimeOut <= 0) {
			// 连接超时
			HttpConnectionParams.setConnectionTimeout(client.getParams(),
					this.connectTimeOut);
		} else {
			HttpConnectionParams.setConnectionTimeout(client.getParams(),
					connectTimeOut);

		}
		if (soTimeout <= 0) {
			// 请求超时 soTimeout
			HttpConnectionParams.setSoTimeout(client.getParams(),
					this.soTimeout);
		} else {
			HttpConnectionParams.setSoTimeout(client.getParams(), soTimeout);
		}
	}

	/**
	 * digest验证
	 * 
	 * @param url
	 */
	private void doDigest(URI url) {
		// TODO Auto-generated method stub
		UsernamePasswordCredentials upc = new UsernamePasswordCredentials(
				username, password);
		String strRealm = "Digest";
		String strHost = url.getHost();
		int iPort = url.getPort();
		AuthScope as = new AuthScope(strHost, iPort, strRealm);
		CredentialsProvider cp = client.getCredentialsProvider();
		cp.setCredentials(as, upc);
	}

	/**
	 * post请求核心方法
	 * 
	 * @param context
	 * @param path
	 * @param httpEntity
	 */
	private void doPostRun(Context context, String path, HttpEntity httpEntity) {
		// TODO Auto-generated method stub
		int isrun = 0;
		while (isrun < 5) {
			try {
				isrun++;
				setClient();// httpclient设置
				if (isrun >= 3)
					setHost(context, client);
				setTimeOut(connectTimeOut, soTimeout);// 超时设置
				if (post==null) {
					post = new HttpPost(path);
				}else {
					post.setURI(URI.create(path));
				}
				doDigest(post.getURI());// digest验证
				post.setEntity(httpEntity);
				HttpResponse httpResponse = client.execute(post);
				int code = httpResponse.getStatusLine().getStatusCode();
				if (code == 200) {
					cookieStore = client.getCookieStore();
					is = httpResponse.getEntity().getContent();
					break;

				} else if (code == 401) {

				} else if (code == 500) {
					is = httpResponse.getEntity().getContent();
					Log.d("err", "错误500");
					break;
				}
			} catch (ConnectTimeoutException e) {
				// 捕获ConnectionTimeout
				Log.d("err", "与服务器建立连接超时");
			} catch (SocketTimeoutException e) {
				// 捕获SocketTimeout
				Log.d("err", "从服务器获取响应数据超时");
			} catch (NoHttpResponseException e) {
				// 无服务器响应
				Log.d("err", "无服务器响应");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * get请求基方法
	 * 
	 * @param context
	 * @param requestUrl
	 *            eg.https://192.168.1.101:4343/api?name=1&value=2
	 * @param connectTimeOut
	 * @param soTimeout
	 * @return
	 */
	public InputStream sendhttpGet(Context context, String requestUrl,
			int connectTimeOut, int soTimeout) {
		int isrun = 0;
		while (isrun < 5) {
			try {
				isrun++;
				setClient();
				if (isrun >= 3)
					setHost(context, client);
				setTimeOut(connectTimeOut, soTimeout);// 超时设置
				if (null == get) {
					get = new HttpGet(requestUrl);
				} else {
					get.setURI(URI.create(requestUrl));
				}
				doDigest(get.getURI());
				HttpResponse httpResponse = client.execute(get);
				int code = httpResponse.getStatusLine().getStatusCode();
				if (code == 200) {
					cookieStore = client.getCookieStore();
					is = httpResponse.getEntity().getContent();
					break;
				}
			} catch (ConnectTimeoutException e) {
				// 捕获ConnectionTimeout
				Log.d("err", "与服务器建立连接超时");
			} catch (SocketTimeoutException e) {
				// 捕获SocketTimeout
				Log.d("err", "从服务器获取响应数据超时");
			} catch (NoHttpResponseException e) {
				// 无服务器响应
				Log.d("err", "无服务器响应");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return is;
	}

	/**
	 * post请求基本方法
	 * 
	 * @param context
	 * @param path
	 *            api接口
	 * @param paramsJson
	 *            请求参数json序列
	 * @param connectTimeOut
	 * @param soTimeout
	 * @return
	 */
	public InputStream sendhttpPost(Context context, String path,
			String paramsJson, int connectTimeOut, int soTimeout) {
		if (paramsJson==null) {
			paramsJson = "";
		}
		
		StringEntity stringEntity = null;
		try {
			stringEntity = new StringEntity(paramsJson, HTTP.UTF_8);
			stringEntity.setContentType("application/json");
//			stringEntity.setChunked(true);// 没有的话 服务器不识别 400
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}
		doPostRun(context, path, stringEntity);
		return is;
	}

	/**
	 * post 请求基本方法
	 * 
	 * @param context
	 * @param path
	 *            api接口
	 * @param paramsJson
	 *            请求参数json序列
	 * @param files
	 *            上传文件本地路径
	 * @param connectTimeOut
	 * @param soTimeout
	 * @return
	 */
	public InputStream sendhttpPost(Context context, String path,
			String paramsJson, List<String> files, int connectTimeOut,
			int soTimeout) {
		if (paramsJson==null) {
			paramsJson = "";
		}
		MultipartEntity mpEntity = new MultipartEntity();
		StringBody info = null;
		try {
			info = new StringBody(paramsJson, Charset.forName("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		mpEntity.addPart("Info", info);
		for (String fileUrl : files) {
			if (fileUrl != null && !fileUrl.trim().equals("")) {
				File file = new File(fileUrl);
				if (file != null && file.exists()) {
					FileBody fileBody = new FileBody(file);
					mpEntity.addPart("File", fileBody);
				}
			}
		}
		doPostRun(context, path, mpEntity);
		return is;
	}

	/**
	 * 
	 * @param context
	 * @param path
	 *            api接口
	 * @param paramsJson
	 *            请求参数json序列
	 * @param files
	 *            上传文件本地路径
	 * @param connectTimeOut
	 * @param soTimeout
	 * @param progressListener
	 *            上传进度回调
	 * @return
	 */
	public InputStream sendhttpPost(Context context, String path,
			String paramsJson, List<String> files, int connectTimeOut,
			int soTimeout, ProgressListener progressListener) {
		if (paramsJson==null) {
			paramsJson = "";
		}
		ProgressMultipartEntity mpEntity;
		if (progressListener!=null) {
			mpEntity = new ProgressMultipartEntity(progressListener);
		}else {
			mpEntity = new ProgressMultipartEntity(new ProgressListener() {
				
				@Override
				public void transferred(long num, long totalSize) {
				}
			});
		}
		StringBody info = null;
		try {
			info = new StringBody(paramsJson, Charset.forName("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		mpEntity.addPart("Info", info);
		if (files!=null&&files.size()>0) {
			for (String fileUrl : files) {
				if (fileUrl != null && !fileUrl.trim().equals("")) {
					File file = new File(fileUrl);
					if (file != null && file.exists()) {
						FileBody fileBody = new FileBody(file);
						mpEntity.addPart("File", fileBody);
					}
				}
			}
		}
		doPostRun(context, path, mpEntity);
		return is;
	}

}
