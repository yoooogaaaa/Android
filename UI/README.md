# 实验三：UI组件

一、Android ListView的用法

二、创建自定义布局的AlertDialog

三、使用XML定义菜单

四、创建上下文操作模式(ActionMode)的上下文菜单

所涉及到的相关知识：[官方文档](https://developer.android.google.cn/guide/topics/ui/menus.html)

------

### 一、Android ListView的用法

#### 1.布局

在主页面的布局文件里，定义一个listview

```java
    <!-- 定义一个ListView -->
    <ListView
        android:id="@+id/mylist"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

定义一个文本框和图片：

```
    <TextView
        android:id="@+id/name"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:textSize="16dp"
        app:layout_constraintRight_toLeftOf="@id/name"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <ImageView
        android:id="@+id/img"
        android:layout_width="36dp"
        android:layout_height="36dp"
        app:layout_constraintHorizontal_bias="1.0"
        app:layout_constraintLeft_toRightOf="@id/name"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

#### 2.在drawable中存入图片

#### 3.实现内容均在Activity内实现

```java
public class MainActivity extends AppCompatActivity {
    private String[] names = new String[]
            {"Lion","Tiger","Monkey","Dog","Cat","Elephant"};
    private int[] imgIds = new int[]
            {R.drawable.lion,R.drawable.tiger,R.drawable.monkey,R.drawable.dog,R.drawable.cat,R.drawable.elephant};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 创建一个List集合，元素为Map类型
        List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
        for (int i = 0;i < names.length;i++){
            Map<String,Object> listItem = new HashMap<String,Object>();
            listItem.put("img",imgIds[i]);
            listItem.put("name",names[i]);
            listItems.add(listItem);
        }

        // 创建simpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,listItems,
                R.layout.adapter_content,
                new String[] {"name","img"},
                new int[] {R.id.name,R.id.img});
        ListView list = findViewById(R.id.mylist);

        // 为ListView设置Adapter
        list.setAdapter(simpleAdapter);

        // 为ListView的列表项的单击事件绑定事件监听器
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(MainActivity.this,names[position],Toast.LENGTH_SHORT);
                toast.show();
            }
        });


    }
}
```

#### 实验结果：

![](https://github.com/yoooogaaaa/Android/blob/master/UI/image/SimpleAdapter.png?raw=true)





### 二、创建自定义布局的AlertDialog

实现步骤：
① 创建AlertDialog.Builder对象。
② 调用AlertDialog.Builder的setTitle()方法设置标题。
③ 调用AlertDialog.Builder的SetIcon()方法设置图标。
④ 调用AlertDialog.Builder的相关设置方法设置对话框内容。
⑤ 调用AlertDialog.Builder的set来添加按钮。
⑥ 调用AlertDialog.Builder的create()方法创建对象后show()出该对话框。



#### 1.布局

```java
    <TextView
        android:id="@+id/title"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Android APP"
        android:textSize="36sp"
        android:textColor="@android:color/white"
        android:textStyle="bold"
        android:gravity="center"
        android:padding="6dp"
        android:background="@android:color/holo_orange_light"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"/>

    <EditText
        android:id="@+id/userName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:textSize="20sp"
        android:hint=" Username"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@id/title" />

    <EditText
        android:id="@+id/password"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint=" Password"
        android:textSize="20sp"
        android:inputType="textPassword"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@id/userName" />

    <Button
        android:id="@+id/bt_1"
        android:layout_width="0dp"
        app:layout_constraintHorizontal_weight="1"
        android:text="取消"
        android:background="@android:color/white"
        android:textColor="#14BFB1"
        android:textSize="20sp"
        android:layout_height="wrap_content"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toLeftOf="@id/bt_confirm"
        app:layout_constraintTop_toBottomOf="@id/password" />

    <Button
        android:id="@+id/bt_confirm"
        android:layout_width="0dp"
        app:layout_constraintHorizontal_weight="1"
        android:text="登录"
        android:background="@android:color/white"
        android:textColor="#14BFB1"
        android:textSize="20sp"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toBottomOf="@id/password"
        app:layout_constraintLeft_toRightOf="@id/bt_1"
        app:layout_constraintRight_toRightOf="parent"/>
```

#### 2.实现

activity

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomDialog customDialog = new CustomDialog(MainActivity.this);
        customDialog.setCancel("取消", new CustomDialog.IOnCancelListener() {
            @Override
            public void onCancel(CustomDialog dialog) {
                Toast.makeText(MainActivity.this, "已取消！",Toast.LENGTH_SHORT).show();
            }
        });
        customDialog.setConfirm("登录", new CustomDialog.IOnConfirmListener(){
            @Override
            public void onConfirm(CustomDialog dialog) {
                Toast.makeText(MainActivity.this, "已登录！",Toast.LENGTH_SHORT).show();
            }
        });
        customDialog.show();

    }
}

```



CustomDialog

```java
public class CustomDialog extends Dialog implements View.OnClickListener {
    private TextView tv_title;
    private EditText et_userName,et_password;
    private Button bt_1,bt_confirm;
    private String cancel,confirm;
    private IOnCancelListener cancelListener;
    private IOnConfirmListener confirmListener;

    public void setCancel(String cancel,IOnCancelListener cancelListener) {
        this.cancel = cancel;
        this.cancelListener = cancelListener;
    }
    public void setConfirm(String confirm,IOnConfirmListener confirmListener){
        this.confirm = confirm;
        this.confirmListener = confirmListener;
    }

    public CustomDialog(@NonNull Context context) {
        super(context);
    }
    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = size.x;
        getWindow().setAttributes(p);

        tv_title = findViewById(R.id.title);
        bt_1 = findViewById(R.id.bt_1);
        bt_confirm = findViewById(R.id.bt_confirm);

        bt_confirm.setOnClickListener(this);
        bt_1.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_1:
                if(cancelListener!=null){
                    cancelListener.onCancel(this);
                }
                dismiss();
                break;
            case R.id.bt_confirm:
                if(confirmListener!=null){
                    confirmListener.onConfirm(this);
                }
                dismiss();
                break;
        }
    }

    public interface IOnCancelListener{
        void onCancel(CustomDialog dialog);
    }
    public interface IOnConfirmListener{
        void onConfirm(CustomDialog dialog);
    }

}

```

#### 实验结果：

![](http://m.qpic.cn/psc?/V12PiJBW3ypWPZ/8YUQ4vKPKp.vxIKbDZcdtsMSblbxq3fFvHSzgPzocltnoeAkf*sGazzaFT1wbcFz7zjba4zABiWhqMyf57JFpg!!/b&bo=KAJZAwAAAAADB1I!&rf=viewer_4)





### 三、使用XML定义菜单

#### 1.布局

```java
    <TextView
        android:id="@+id/tv_test"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="用于测试的内容"
        android:textSize="16dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

#### 2.mainactivity

```java
public class MainActivity extends AppCompatActivity {
    private TextView tv_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_test = findViewById(R.id.tv_test);
        registerForContextMenu(tv_test);}
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle item selection
            switch (item.getItemId()) {
                case R.id.menu1_1:
                    tv_test.setTextSize(10);
                    return true;
                case R.id.menu1_2:
                    tv_test.setTextSize(16);
                    return true;
                case R.id.menu1_3:
                    tv_test.setTextSize(20);
                    return true;
                case R.id.menu2:
                    Toast toast = Toast.makeText(MainActivity.this,"点击了普通菜单项",Toast.LENGTH_SHORT);
                    toast.show();
                    return true;
                case R.id.menu3_1:
                    tv_test.setTextColor(Color.BLACK);
                    return true;
                case R.id.menu3_2:
                    tv_test.setTextColor(Color.RED);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
    }
}

```

#### 3.menu.xml

```java

    <item
        android:id="@+id/menu_1"
        android:title="字体大小">

        <menu xmlns:android="http://schemas.android.com/apk/res/android">

            <item
                android:id="@+id/menu1_1"
                android:title="小" />

            <item
                android:id="@+id/menu1_2"
                android:title="中" />

            <item
                android:id="@+id/menu1_3"
                android:title="大" />

        </menu>
    </item>

    <item
        android:id="@+id/menu2"
        android:title="普通菜单项" />

    <item
        android:id="@+id/menu3"
        android:title="字体颜色">

        <menu xmlns:android="http://schemas.android.com/apk/res/android">

            <item
                android:id="@+id/menu3_1"
                android:title="黑色" />

            <item
                android:id="@+id/menu3_2"
                android:title="红色" />

        </menu>
    </item>
```

#### 实验结果：

![](http://m.qpic.cn/psc?/V12PiJBW3ypWPZ/yCLjTthScCcjc0qcPSGYBuag0cPwnrZdVO.7nqEvfK.urIH72tpNWfhJhZE1EzywFq1*9MgvhqreCmGZVRNg7zOH61BNRC3*ikom9dZBtUs!/b&bo=LgJdAwAAAAADF0A!&rf=viewer_4)





### 四、创建上下文操作模式(ActionMode)的上下文菜单

参考教程（笔者写的非常好，非常清晰）：[点击这里](https://blog.csdn.net/qq_39824472/article/details/90549797)

#### 1.布局

主界面，值得一提的是要将choiceMode设置为multipleChoiceModal，也就是可以为多选。

```java

    <LinearLayout android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:layout_editor_absoluteX="16dp"
        tools:layout_editor_absoluteY="0dp">

        <ListView
            android:id="@+id/list_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:choiceMode="multipleChoiceModal"/>

    </LinearLayout>
```

创建一个布局文件用来实现每个ListView的选项，每个选项都包括一个ImageView和TextView，分别用来显示每个项前面的图片以及后面的文本

```java
    <TextView
        android:id="@+id/text_view"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:gravity="center_vertical"
        android:paddingLeft="60dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <ImageView
        android:id="@+id/image"
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:src="@drawable/robot"
        app:layout_constraintEnd_toStartOf="@+id/text_view"
        app:layout_constraintHorizontal_bias="0.029"
        app:layout_constraintLeft_toLeftOf="@id/text_view"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

#### 2.menu文件

用来实现上方的选项菜单的有关设置 垃圾桶和√的文件

```java
    <item
        android:id="@+id/menu_all"
        app:showAsAction="always"
        android:icon="@drawable/select"
        android:title="item_all"/>

    <item
        android:id="@+id/menu_delete"
        app:showAsAction="always"
        android:icon="@drawable/dustbin"
        android:title="item_delete"/>
```

#### 3.实现部分

item类

用来记录是否被选中，默认为不选择

```java
public class Item {
    private String name;//显示的选项名
    private boolean bo;//记录是否被选中

    //构造函数
    public Item(){
        super();
    }

    //带两个参数的构造函数
    public Item(String name, boolean bo){
        super();
        this.name = name;
        this.bo = bo;
    }

    //相应的set、get和toString方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isBo() {
        return bo;
    }
    public void setBo(boolean bo) {
        this.bo = bo;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ",bo=" + bo +
                '}';
    }
}

```

自定义的适配器

这里值得注意的是适配器中的notifyDataSetChanged()方法，一定记得要再改变之后加上，不然列表不会实时更新。同时要记得传入的list和更改之后的list一定要是同一个list哦，不然这个方法不会起作用的。

```java
public class AdapterCur extends BaseAdapter {

    List<Item> list;//item的list对象
    Context context;//上下文对象

    //初始化
    public AdapterCur(List<Item> list, Context context) {
        this.context = context;
        this.list = list;
        //列表同步方法
        notifyDataSetChanged();
    }

    //得到当前列表的选项数量
    public int getCount() {
        return list.size();
    }

    //根据下标得到列表项
    public Item getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        //如果还没加载
        if(convertView==null){
            //加载布局文件，并将各个选项以及每个选项中的内容一一对应
            convertView=View.inflate(context, R.layout.activity_content, null);
            viewHolder=new ViewHolder();
            viewHolder.imageView=(ImageView) convertView.findViewById(R.id.image);
            viewHolder.textView=(TextView) convertView.findViewById(R.id.text_view);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        //得到十六进制的颜色的int值
        int PaleTurquoise = Color.parseColor("#AFEEEE");
        int white = Color.parseColor("#FFFFFF");
        viewHolder.textView.setText(list.get(position).getName());
        //如果被选中，那么改变选中颜色
        if(list.get(position).isBo() == true){
            viewHolder.textView.setBackgroundColor(PaleTurquoise);
            viewHolder.imageView.setBackgroundColor(PaleTurquoise);
        }
        else {
            viewHolder.textView.setBackgroundColor(white);
            viewHolder.imageView.setBackgroundColor(white);
        }
        return convertView;

    }

    //创建内部类，定义每一个列表项所包含的东西，这里是每个列表项都有一个imageView和textView。
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
```

main_activity

重点要注意的就是onItemCheckedStateChanged()方法，改变选中状态要改变相应item的是否选中属性，**<u>然后一定要调用notifyDataSetChanged()方法刷新！</u>**

```java
public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<Item> list;

    private BaseAdapter adapter;
    private String [] name = {"One","Two","Three","Four","Five","Six"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        list = new ArrayList<Item>();
        //定义item并且加入list中
        for(int i = 0; i < 6; i++){
            list.add(new Item(name[i], false));
        }
        //对listview进行适配器适配
        adapter = new AdapterCur(list,MainActivity.this);
        listView.setAdapter(adapter);

        //设置listview允许多选模式
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            //选中数量
            int num = 0;

            /*
             * 参数：ActionMode是长按后出现的标题栏
             * 		positon是当前选中的item的序号
             *		id 是当前选中的item的id
             *		checked 如果是选中事件则为true，如果是取消事件则为false
             */
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                // 调整选定条目
                if (checked == true) {
                    list.get(position).setBo(true);
                    //实时刷新
                    adapter.notifyDataSetChanged();
                    num++;
                } else {
                    list.get(position).setBo(false);
                    //实时刷新
                    adapter.notifyDataSetChanged();
                    num--;
                }
                // 用TextView显示
                mode.setTitle("  " + num + " Selected");
            }


            /*
             * 参数：ActionMode是长按后出现的标题栏
             * 		Menu是标题栏的菜单内容
             */
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // 设置长按后所要显示的标题栏的内容
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.activity_action_mode, menu);
                num = 0;
                adapter.notifyDataSetChanged();
                return true;

            }


            /*
             * 可在此方法中进行标题栏UI的创建和更新
             */
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

                adapter.notifyDataSetChanged();
                return false;
            }

            public void refresh(){
                for(int i = 0; i < 6; i++){
                    list.get(i).setBo(false);
                }
            }

            /*
             * 可在此方法中监听标题栏Menu的监听，从而进行相应操作
             * 设置actionMode菜单每个按钮的点击事件
             */
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    //全选
                    case R.id.menu_all:
                        num = 0;
                        refresh();
                        adapter.notifyDataSetChanged();
                        mode.finish(); // 偷了个懒，每个菜单按钮都设置返回，结束多选模式
                        return true;
                    //删除
                    case R.id.menu_delete:
                        adapter.notifyDataSetChanged();
                        num = 0;
                        refresh();
                        mode.finish();// 偷了个懒，每个菜单按钮都设置返回，结束多选模式
                        return true;
                    default:
                        refresh();
                        adapter.notifyDataSetChanged();
                        num = 0;
                        return false;
                }

            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                refresh();
                adapter.notifyDataSetChanged();

            }

        });
    }
}

```

#### 实验结果：

![](http://m.qpic.cn/psc?/V12PiJBW3ypWPZ/8YUQ4vKPKp.vxIKbDZcdthaG8nVOy0ciMKk8t2D7j68IfFJYiZxzFHz*j5PiMS4hNWY3xyWvkyh0GwCPxtzecQ!!/b&bo=IgJTAwAAAAADB1I!&rf=viewer_4)