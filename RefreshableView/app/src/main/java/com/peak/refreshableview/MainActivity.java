package com.peak.refreshableview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.peak.refreshableview.view.RefreshableView;

public class MainActivity extends AppCompatActivity {
    private RefreshableView refresh;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private String[] items = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //AppCompatActivity 使用该方法去除标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        refresh = (RefreshableView) findViewById(R.id.refresh_view);
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        refresh.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refresh.finishRefreshing();
            }

        }, 0);
    }
}
