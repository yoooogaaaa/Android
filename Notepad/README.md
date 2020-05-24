# 期中测验：NotePad功能的简单拓展
### 一、时间戳

（1）在noteslist_item.xml的布局文件中增加一个TextView来显示时间戳

```java
    <!--添加显示时间的TextView-->
    <TextView
        android:id="@+id/text2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textAppearance="?android:attr/textAppearanceSmall"
        android:paddingLeft="5dip"
        android:textColor="@color/colorBlack"/>
```

（2）数据库中已有文本创建时间和修改时间连个字段，在NodeEditor.java中,找到updateNode()这个函数，选取修改时间这一字段，并将其格式化存入数据库

```java
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, retStrFormatNowDate);
```

（3）然后在NotesList的数据定义中增加修改时间

```java
private static final String[] PROJECTION = new String[] {
        NotePad.Notes._ID, // 0
        NotePad.Notes.COLUMN_NAME_TITLE, // 1
        NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,//在这里加入了修改时间的显示
};

```

效果截图：

![](http://m.qpic.cn/psc?/V12PiJBW3ypWPZ/8YUQ4vKPKp.vxIKbDZcdtgNs0gh60JlZVap5QZI7qrm5BZkVTPKl3u4pt4zdk47e1OrQuD3vT2G9TbkVWg.bDg!!/b&bo=UQKkAwAAAAADB9Y!&rf=viewer_4)

### 二、搜索功能

（1）新建一个布局文件list_options_menu.xml，添加一个搜索的item，搜索图标用安卓自带的图标

```javascript
<item
    android:id="@+id/menu_search"
    android:title="@string/menu_search"
    android:icon="@android:drawable/ic_search_category_default"
    android:showAsAction="always">
</item>
```

（2）在NoteList中找到onOptionsItemSelected方法，在switch中添加搜索的case语句:

```java
 //添加搜素
    case R.id.menu_search:
    Intent intent = new Intent();
    intent.setClass(NotesList.this,NoteSearch.class);
    NotesList.this.startActivity(intent);
    return true;
```

（3）新建布局文件note_search_list.xml

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent">
    <SearchView
        android:id="@+id/search_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:iconifiedByDefault="false"
        android:queryHint="输入搜索内容..."
        android:layout_alignParentTop="true">
    </SearchView>
    <ListView
        android:id="@android:id/list"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    </ListView>
</LinearLayout>
```

（4）新建一个名为NoteSearch,java

```java
package com.example.android.notepad;


import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SearchView;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class NoteSearch extends ListActivity implements SearchView.OnQueryTextListener {
    private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_search_list);
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(NotePad.Notes.CONTENT_URI);
        }
        SearchView searchview = (SearchView)findViewById(R.id.search_view);
        //为查询文本框注册监听器
        searchview.setOnQueryTextListener(NoteSearch.this);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        SearchView searchview = (SearchView)findViewById(R.id.search_view);
        searchview.setOnQueryTextListener(NoteSearch.this);
        String selection = NotePad.Notes.COLUMN_NAME_TITLE + " Like ? ";
        String[] selectionArgs = { "%"+newText+"%" };
        Cursor cursor = managedQuery(
                getIntent().getData(),            // Use the default content URI for the provider.
                PROJECTION,                       // Return the note ID and title for each note. and modifcation date
                selection,                        // 条件左边
                selectionArgs,                    // 条件右边
                NotePad.Notes.DEFAULT_SORT_ORDER  // Use the default sort order.
        );
        String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE ,  NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE };
        int[] viewIDs = { android.R.id.text1 , R.id.text2 };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.noteslist_item,
                cursor,
                dataColumns,
                viewIDs
        );
        setListAdapter(adapter);
        return true;
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // Constructs a new URI from the incoming URI and the row ID
        Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);
        // Gets the action from the incoming Intent
        String action = getIntent().getAction();
        // Handles requests for note data
        if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {
            // Sets the result to return to the component that called this Activity. The
            // result contains the new URI
            setResult(RESULT_OK, new Intent().setData(uri));
        } else {
            // Sends out an Intent to start an Activity that can handle ACTION_EDIT. The
            // Intent's data is the note ID URI. The effect is to call NoteEdit.
            startActivity(new Intent(Intent.ACTION_EDIT, uri));
        }
    }
}
```

（5）在AndroidManifest.xml注册NoteSearch

```java
<!--添加搜索activity-->
    <activity
        android:name="NoteSearch"
        android:label="@string/title_notes_search">
    </activity>
```

结果实现

![](http://a1.qpic.cn/psc?/V12PiJBW3ypWPZ/8YUQ4vKPKp.vxIKbDZcdtrnp6qXkEByYMK4L5N4Wvrjy0pPvcaXetNknzROM8xx9Q7iyle1mNSi7lwNExv594Q!!/b&ek=1&kp=1&pt=0&bo=EwLsAAAAAAADF88!&tl=1&vuin=758420984&tm=1590325200&sce=60-1-1&rf=viewer_4)

![](http://m.qpic.cn/psc?/V12PiJBW3ypWPZ/yCLjTthScCcjc0qcPSGYBnECLOpAImmh*TK*kQkRvBLiepdDSIlxpPAsCeF9mOUBB8*bjjYf4Kw9gmPZU3T9ijR919wCESC02z734PSA9lk!/b&bo=UQKkAwAAAAADF8Y!&rf=viewer_4)