package com.scwang.refreshlayout.activity.style;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;

import com.scwang.refreshlayout.R;
import com.scwang.refreshlayout.adapter.BaseRecyclerAdapter;
import com.scwang.refreshlayout.adapter.SmartViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.scwang.refreshlayout.R.layout.item_style_delivery;

public class DeliveryStyleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private enum Item {
        默认主题(R.string.item_style_theme_default_abstract),
        橙色主题(R.string.item_style_theme_orange_abstract),
        红色主题(R.string.item_style_theme_red_abstract),
        绿色主题(R.string.item_style_theme_green_abstract),
        蓝色主题(R.string.item_style_theme_blue_abstract),
        ;
        public int nameId;
        Item(@StringRes int nameId) {
            this.nameId = nameId;
        }
    }

    private Toolbar mToolbar;
    private RefreshLayout mRefreshLayout;
    private static boolean isFirstEnter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_delivery);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(0xfff0f0f0);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = getWindow();
            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.getDecorView().setSystemUiVisibility(systemUiVisibility);
        }

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(v -> finish());

        mRefreshLayout = findViewById(R.id.refreshLayout);
        if (isFirstEnter) {
            isFirstEnter = false;
            mRefreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果
        }

        View view = findViewById(R.id.recyclerView);
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            List<Item> items = new ArrayList<>();
            items.addAll(Arrays.asList(Item.values()));
            items.addAll(Arrays.asList(Item.values()));
            recyclerView.setAdapter(new BaseRecyclerAdapter<Item>(items, item_style_delivery,this) {
                @Override
                protected void onBindViewHolder(SmartViewHolder holder, Item model, int position) {
                }
            });
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (Item.values()[position % Item.values().length]) {
            case 默认主题:
                mToolbar.setBackgroundResource(android.R.color.white);
                mToolbar.setTitleTextColor(0xffbbbbbb);
                mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_gray_24dp);
                mRefreshLayout.setPrimaryColors(0xfff0f0f0, 0xffffffff);
                if (Build.VERSION.SDK_INT >= 21) {
                    getWindow().setStatusBarColor(0xfff0f0f0);
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    Window window = getWindow();
                    int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
                    systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    window.getDecorView().setSystemUiVisibility(systemUiVisibility);
                }
                break;
            case 蓝色主题:
                setThemeColor(R.color.colorPrimary, R.color.colorPrimaryDark);
                break;
            case 绿色主题:
                setThemeColor(android.R.color.holo_green_light, android.R.color.holo_green_dark);
                break;
            case 红色主题:
                setThemeColor(android.R.color.holo_red_light, android.R.color.holo_red_dark);
                break;
            case 橙色主题:
                setThemeColor(android.R.color.holo_orange_light, android.R.color.holo_orange_dark);
                break;
        }
        mRefreshLayout.autoRefresh();
    }

    private void setThemeColor(int colorPrimary, int colorPrimaryDark) {
        mToolbar.setBackgroundResource(colorPrimary);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mRefreshLayout.setPrimaryColorsId(colorPrimary, android.R.color.white);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, colorPrimaryDark));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = getWindow();
            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.getDecorView().setSystemUiVisibility(systemUiVisibility);
        }
    }
}
