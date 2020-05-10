# 实验四：Intent

自定义WebView验证隐式Intent的使用

本实验通过自定义WebView加载URL来验证隐式Intent的使用。

一、获取URL地址并启动隐式Intent的调用

二、自定义WebView来加载URL

------

### 一、获取URL地址并启动隐式Intent的调用

#### 1.布局

定义一个文本框和图片：

![](http://m.qpic.cn/psc?/V12PiJBW3ypWPZ/8YUQ4vKPKp.vxIKbDZcdtnG3*nHYj1GoC3xdpOjGjS7VcdxK.Q7qkQcUkAGTgLsKAblODaGMnlQ5gdO6AKJpJQ!!/b&bo=PAIyAQAAAAADBy8!&rf=viewer_4)

#### 2.新建一个工程用来获取URL地址并启动Intent

![](http://m.qpic.cn/psc?/V12PiJBW3ypWPZ/yCLjTthScCcjc0qcPSGYBnpBDFp.HWNzkjas65HZQW5lyU2qSKUyO0njwxkT4Br3e5dJ8ttJq89yXcml8T.haSAuAWFp8Kcljzjfem2oslY!/b&bo=tQJuAQAAAAADF.o!&rf=viewer_4)

#### 实验结果：

![](http://m.qpic.cn/psc?/V12PiJBW3ypWPZ/yCLjTthScCcjc0qcPSGYBq1AX8LRnn*nmq7Dx6sUvHjcYalNX.o6GA*2w8DuJvs989E.T*jpxvRRKjNFZ*N3Rrf51IXg8pFfRnXXAihkKfs!/b&bo=pAIwBAAAAAADF6A!&rf=viewer_4)

![](http://m.qpic.cn/psc?/V12PiJBW3ypWPZ/yCLjTthScCcjc0qcPSGYBhgwR7DbqyDVOtkrhQcGUk2*wWsb1.UNa4vljcLNDpdCxHv69ZZk13tEtWlA3LP7n5kGTzDUvLYET256kBOJk0E!/b&bo=pAIwBAAAAAADJ5A!&rf=viewer_4)



### 二、自定义WebView来加载URL

实现步骤：
① 创建一个Intent；
② 将EditText中获取的文本转换成Uri；
③ 设置Intent的动作为Intent.ACTION_VIEW；
④ 然后利用该Intent启动Activity；
⑤ 将Action设置为VIEW；
⑥ Intent的数据传递一个http协议的URI，系统去寻找相关能够浏览网页的应用。



#### 1.布局

activity_main.xml

```java
    <EditText
        android:id="@+id/url"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="请输入网址 https://..."
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
    <Button
        android:onClick="click"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="浏览该网页"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toBottomOf="@id/url"/>

```

activity_webview.xml

```
    <WebView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/web">
    </WebView>
```

#### 2.实现

Mainactivity

```java
public class MainActivity extends AppCompatActivity {
    private EditText url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url= (EditText) findViewById(R.id.url);

    }
    public void click(View source){
        Intent intent=new Intent();
        //跳一个系统的视图
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url.getText().toString()));
        Intent choose=Intent.createChooser(intent,"请选择一个浏览器");
        startActivity(choose);
    }


```

web

```java
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        web = (WebView) findViewById(R.id.web);
        //接收main页面界面传过来的网址
        String url=getIntent().getDataString();

        WebSettings webSettings=web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        //加载网址
        web.loadUrl(url);
        //使用web客户端打开网址
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                web.loadUrl(url);
                return true;
            }
        });
    }
}
```

#### 3.注意点

AndroidManifest.xml，注意其中的intent-filter：

```
            <intent-filter tools:ignore="AppLinkUrlError">
                <action android:name="android.intent.action.VIEW"></action>
                <data android:scheme="http"/>
                <data android:scheme="https"/>
                <category android:name="android.intent.category.DEFAULT"/>
            </intent-filter>
```



#### 实验结果：

输入一个网址，点击按钮开始浏览网页。

![](http://m.qpic.cn/psc?/V12PiJBW3ypWPZ/yCLjTthScCcjc0qcPSGYBraijaSMnMWGPjWseqn7m63ikr*cOJ8y4PxYEYD7ztm4q88ZLpSHbhIZW65DQ2iqbRcjeV4d0EWoq5yToFLyvzM!/b&bo=.AEdAwAAAAADF9U!&rf=viewer_4)

当点击“浏览该网页”按钮之后，模拟器会弹出如下画面：

![](http://m.qpic.cn/psc?/V12PiJBW3ypWPZ/yCLjTthScCcjc0qcPSGYBkD8iICkv84gZkOR534AHpCo6H3awZIdt7eMzA4JA.sk7pmr*d4V0LD1AVPeixe3LibWBzOg3W60ZXOKonczqDg!/b&bo=.AEdAwAAAAADF9U!&rf=viewer_4)



选择“webview browser”打开

![](http://m.qpic.cn/psc?/V12PiJBW3ypWPZ/yCLjTthScCcjc0qcPSGYBsTBhqf9OuNJXDOMv04c6MW5It3.ibFCqnxY5mE9gUDGItZ.1EMzmprTx0F1G2D49p9qzdv.1N0f5FW*18pG84E!/b&bo=.AEdAwAAAAADF9U!&rf=viewer_4)