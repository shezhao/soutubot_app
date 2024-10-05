package com.example.soutubot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ValueCallback<Uri[]> uploadMessage;
    private final static int FILE_CHOOSER_RESULT_CODE = 1;
    private String currentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 启用JavaScript
        webSettings.setLoadWithOverviewMode(true); // 缩放页面以适应WebView
        webSettings.setUseWideViewPort(true); // 支持viewport
        webSettings.setDomStorageEnabled(true); // 启用DOM存储
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setBuiltInZoomControls(true); // 启用内置缩放控件
        webSettings.setDisplayZoomControls(false); // 隐藏内置缩放控件

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 页面加载完成后执行的操作,跳出一个提示页面，显示一段文本
                if (url.equals("https://soutubot.moe/")) {
                    view.loadUrl("javascript:(function() { " +
                            "var overlay = document.createElement('div'); " +
                            "overlay.style.position = 'fixed'; " +
                            "overlay.style.top = '0'; " +
                            "overlay.style.left = '0'; " +
                            "overlay.style.width = '100%'; " +
                            "overlay.style.height = '100%'; " +
                            "overlay.style.backgroundColor = 'rgba(0, 0, 0, 0.5)'; " +
                            "overlay.style.zIndex = '1000'; " +
                            "overlay.style.display = 'flex'; " +
                            "overlay.style.justifyContent = 'center'; " +
                            "overlay.style.alignItems = 'center'; " +
                            "var box = document.createElement('div'); " +
                            "box.style.backgroundColor = '#fff'; " +
                            "box.style.padding = '20px'; " +
                            "box.style.borderRadius = '10px'; " +
                            "box.style.boxShadow = '0 0 10px rgba(0, 0, 0, 0.5)'; " +
                            "box.style.maxWidth = '80%'; " +
                            "box.style.textAlign = 'center'; " +
                            "var text = document.createElement('p'); " +
                            "text.style.fontSize = '16px'; " +
                            "text.style.color = '#333'; " +
                            "text.style.margin = '0'; " +
                            "text.innerHTML = '欢迎使用Soutubot安卓版！<br>这是一个基于soutubot.moe网站的套壳app，无任何盈利，仅供学习交流使用，下载后请于24小时内删除<br>如果你有任何问题或建议，请提交issue，。<br><br>网站作者的最后一段话：<br>累了，某些爬虫的想爬就爬吧，特意把CF安全关了这下不用特意用浏览器啥的破CF盾了，不过等会把网站爬到挤爆内存宕机时别找我好了，还有看看我还能坚持到多久这样天天为了这垃圾网站昂贵服务器来高强度加班加到什么时候摔死更好吧'; " +
                            "var buttonContainer = document.createElement('div'); " +
                            "buttonContainer.style.marginTop = '20px'; " +
                            "buttonContainer.style.display = 'flex'; " +
                            "buttonContainer.style.justifyContent = 'center'; " +
                            "var closeButton = document.createElement('button'); " +
                            "closeButton.style.padding = '10px 20px'; " +
                            "closeButton.style.backgroundColor = '#007bff'; " +
                            "closeButton.style.color = '#fff'; " +
                            "closeButton.style.border = 'none'; " +
                            "closeButton.style.borderRadius = '5px'; " +
                            "closeButton.style.cursor = 'pointer'; " +
                            "closeButton.style.marginRight = '10px'; " +
                            "closeButton.innerText = '关闭'; " +
                            "closeButton.onclick = function() { " +
                            "overlay.style.display = 'none'; " +
                            "}; " +
                            "var noShowButton = document.createElement('button'); " +
                            "noShowButton.style.padding = '10px 20px'; " +
                            "noShowButton.style.backgroundColor = '#6c757d'; " +
                            "noShowButton.style.color = '#fff'; " +
                            "noShowButton.style.border = 'none'; " +
                            "noShowButton.style.borderRadius = '5px'; " +
                            "noShowButton.style.cursor = 'pointer'; " +
                            "noShowButton.innerText = '不再提示'; " +
                            "noShowButton.onclick = function() { " +
                            "overlay.style.display = 'none'; " +
                            "localStorage.setItem('noShowWelcome', 'true'); " +
                            "}; " +
                            "buttonContainer.appendChild(closeButton); " +
                            "buttonContainer.appendChild(noShowButton); " +
                            "box.appendChild(text); " +
                            "box.appendChild(buttonContainer); " +
                            "overlay.appendChild(box); " +
                            "document.body.appendChild(overlay); " +
                            "if (localStorage.getItem('noShowWelcome') === 'true') { " +
                            "overlay.style.display = 'none'; " +
                            "} " +
                            "})()");
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 显示友好的错误提示
                Toast.makeText(MainActivity.this, "网络连接错误: " + description, Toast.LENGTH_LONG).show();
                // 提供使用浏览器打开的选项
                showOpenInBrowserDialog(failingUrl);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

                uploadMessage = filePathCallback;

                Intent intent = fileChooserParams.createIntent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "选择图片"), FILE_CHOOSER_RESULT_CODE);
                return true;
            }
        });

        // 请求文件读取权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        // 检测代理或 VPN
        if (isUsingProxyOrVPN()) {
            Toast.makeText(this, "检测到使用代理或 VPN，请注意网络连接", Toast.LENGTH_LONG).show();
        }

        // 获取用户的代理设置
        Proxy proxy = getUserProxy();
        if (proxy != null) {
            // 设置代理
            setProxy(proxy);
        }

        webView.loadUrl("https://soutubot.moe/"); // 替换为你的网址
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (uploadMessage == null) return;
            Uri[] result = data == null || resultCode != RESULT_OK ? null : new Uri[]{data.getData()};
            uploadMessage.onReceiveValue(result);
            uploadMessage = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "文件读取权限已授予", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "文件读取权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isUsingProxyOrVPN() {
        String proxyHost = System.getProperty("http.proxyHost");
        String proxyPort = System.getProperty("http.proxyPort");
        return proxyHost != null && proxyPort != null;
    }

    private Proxy getUserProxy() {
        try {
            List<Proxy> proxies = ProxySelector.getDefault().select(new URI("https://soutubot.moe/"));
            if (proxies != null && !proxies.isEmpty()) {
                return proxies.get(0);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setProxy(Proxy proxy) {
        try {
            ProxySelector.setDefault(new ProxySelector() {
                @Override
                public List<Proxy> select(URI uri) {
                    return java.util.Collections.singletonList(proxy);
                }

                @Override
                public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                    // 处理连接失败
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showOpenInBrowserDialog(String url) {
        currentUrl = url;
        new android.app.AlertDialog.Builder(this)
                .setTitle("无法加载网页")
                .setMessage("是否使用浏览器打开？")
                .setPositiveButton("是", (dialog, which) -> openInBrowser(url))
                .setNegativeButton("否", null)
                .show();
    }

    private void openInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}