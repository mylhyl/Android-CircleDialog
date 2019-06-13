package com.mylhyl.circledialog.sample.list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.callback.ConfigPopup;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.PopupParams;
import com.mylhyl.circledialog.sample.R;
import com.mylhyl.circledialog.sample.entities.NavItemEntity;
import com.mylhyl.circledialog.view.listener.OnRvItemClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ListViewActivity extends AppCompatActivity {

    ActionMenuView mActionMenuView;
    private Toolbar mToolbar;

    public static void gotoActivity(Activity activity) {
        activity.startActivity(new Intent(activity, ListViewActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mActionMenuView = findViewById(R.id.toolbar_actionMenuView);
        getMenuInflater().inflate(R.menu.work, mActionMenuView.getMenu());
        mActionMenuView.setOnMenuItemClickListener(item -> onOptionsItemSelected(item));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new CircleDialog.Builder()
                .setPopup(mActionMenuView, PopupParams.TRIANGLE_TOP_RIGHT)
                .setPopupItems(new String[]{"全部", "广东省", "香港", "湖南", "广西壮族自治区"}
                        , new OnRvItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position) {
                                return true;
                            }
                        })
                .show(getSupportFragmentManager());
        return true;
    }

    public void showMyDialog(View anchor) {
        new CircleDialog.Builder()
                .setPopup(anchor, PopupParams.TRIANGLE_TOP_CENTER)
                .setPopupItems(new String[]{"1", "2", "3", "4"}
                        , new OnRvItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position) {
                                return true;
                            }
                        })
                .show(getSupportFragmentManager());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_list_view);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(new MyAdapter(this));

        findViewById(R.id.top_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setWidth(1f)
                        .setRadius(0)
                        .configDialog(new ConfigDialog() {
                            @Override
                            public void onConfig(DialogParams params) {
                                params.backgroundColor = Color.parseColor("#8f8f8f");
                                params.isDimEnabled = false;
                            }
                        })
                        .setPopup(v, PopupParams.TRIANGLE_TOP_LEFT)
                        .setPopupTriangleSize(50, 50)
                        .setPopupTriangleShow(false)
                        .configPopup(new ConfigPopup() {
                            @Override
                            public void onConfig(PopupParams params) {
                                params.textGravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                                params.padding = new int[]{19, 0, 8, 0};
                                params.textColor = Color.WHITE;
                            }
                        })
                        .setPopupItems(new String[]{"全部", "广东省", "湖南省", "香港"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position) {
                                        return true;
                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });
        findViewById(R.id.top_left1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopup(v, PopupParams.TRIANGLE_TOP_LEFT)
                        .setPopupItems(new String[]{"11111111111111111", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position) {
                                        return true;
                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });
        findViewById(R.id.top_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopup(v, PopupParams.TRIANGLE_TOP_CENTER)
//                        .setPopupTriangleShow(false)
                        .setPopupItems(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position) {
                                        return true;
                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        List<NavItemEntity> list = new ArrayList<>();
        list.add(new NavItemEntity("添加异常", R.mipmap.ic_popup_abnormal));
        list.add(new NavItemEntity("添加备添加备注注", R.mipmap.ic_popup_note));
        list.add(new NavItemEntity("申请延迟", R.mipmap.ic_popup_delay));

        BaseQuickAdapter<NavItemEntity, BaseViewHolder> adapter
                = new BaseQuickAdapter<NavItemEntity, BaseViewHolder>(R.layout.item_rv_icon, list) {
            @Override
            protected void convert(BaseViewHolder helper, NavItemEntity item) {
                helper.setImageResource(R.id.imageView, item.getTextResId())
                        .setText(R.id.textView, item.getName());
            }
        };
        CircleDialog.Builder builder = new CircleDialog.Builder();

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        findViewById(R.id.top_center1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setPopupItems(adapter, new LinearLayoutManager(ListViewActivity.this));
                builder.setPopup(v, PopupParams.TRIANGLE_TOP_CENTER)
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.top_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setMaxHeight(0.6f)
                        .setPopup(v, PopupParams.TRIANGLE_TOP_RIGHT)
//                        .setPopupTriangleShow(false)
                        .setPopupItems(new String[]{"功能1", "功能2", "功能3", "功能4", "功能5"
                                        , "功能6", "功能7", "功能8"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position) {
                                        return true;
                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.bottom_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopup(v, PopupParams.TRIANGLE_BOTTOM_LEFT)
                        .setPopupTriangleSize(60, 60)
                        .setPopupItems(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position) {
                                        return true;
                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });
        findViewById(R.id.bottom_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopup(v, PopupParams.TRIANGLE_BOTTOM_CENTER)
                        .setPopupTriangleSize(60, 60)
                        .setPopupItems(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position) {
                                        return true;
                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.bottom_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopup(v, PopupParams.TRIANGLE_BOTTOM_RIGHT)
                        .setPopupTriangleSize(60, 60)
                        .setPopupItems(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position) {
                                        return true;
                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        TabLayout tabLayout = findViewById(R.id.tab);
        TextView tabItemHK = new TextView(this);
        tabItemHK.setLayoutParams(new TabLayout.LayoutParams(TabLayout.LayoutParams.MATCH_PARENT
                , TabLayout.LayoutParams.MATCH_PARENT));
        tabItemHK.setGravity(Gravity.CENTER);
        tabItemHK.setText("香港");
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabItemHK));

        TextView tabItemZH = new TextView(this);
        tabItemZH.setLayoutParams(new TabLayout.LayoutParams(TabLayout.LayoutParams.MATCH_PARENT
                , TabLayout.LayoutParams.MATCH_PARENT));
        tabItemZH.setGravity(Gravity.CENTER);
        tabItemZH.setText("珠海");
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabItemZH));

        TextView tabItemHN = new TextView(this);
        tabItemHN.setLayoutParams(new TabLayout.LayoutParams(TabLayout.LayoutParams.MATCH_PARENT
                , TabLayout.LayoutParams.MATCH_PARENT));
        tabItemHN.setGravity(Gravity.CENTER);
        tabItemHN.setText("湖南");
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabItemHN));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        new CircleDialog.Builder()
                                .setPopup(tabItemHK, PopupParams.TRIANGLE_BOTTOM_CENTER)
                                .setPopupItems(new String[]{"1", "2", "3", "4"}
                                        , new OnRvItemClickListener() {
                                            @Override
                                            public boolean onItemClick(View view, int position) {
                                                return true;
                                            }
                                        })
                                .show(getSupportFragmentManager());
                        break;
                    case 1:
                        new CircleDialog.Builder()
                                .setPopup(tabItemZH, PopupParams.TRIANGLE_BOTTOM_CENTER)
                                .setPopupItems(new String[]{"1", "2", "3", "4"}
                                        , new OnRvItemClickListener() {
                                            @Override
                                            public boolean onItemClick(View view, int position) {
                                                return true;
                                            }
                                        })
                                .show(getSupportFragmentManager());
                        break;
                    case 2:
                        new CircleDialog.Builder()
                                .setPopup(tabItemHN, PopupParams.TRIANGLE_BOTTOM_CENTER)
                                .setPopupItems(new String[]{"1", "2", "3", "4"}
                                        , new OnRvItemClickListener() {
                                            @Override
                                            public boolean onItemClick(View view, int position) {
                                                return true;
                                            }
                                        })
                                .show(getSupportFragmentManager());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        findViewById(R.id.left_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopup(v, PopupParams.TRIANGLE_LEFT_TOP)
                        .setPopupItems(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position) {
                                        return true;
                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.left_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopup(v, PopupParams.TRIANGLE_LEFT_CENTER)
                        .setPopupItems(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position) {
                                        return true;
                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });
        findViewById(R.id.left_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopup(v, PopupParams.TRIANGLE_LEFT_BOTTOM)
                        .setPopupItems(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position) {
                                        return true;
                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.right_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopup(v, PopupParams.TRIANGLE_RIGHT_TOP)
                        .setPopupTriangleSize(60, 60)
                        .setPopupItems(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position) {
                                        return true;
                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.right_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopup(v, PopupParams.TRIANGLE_RIGHT_CENTER)
                        .setPopupTriangleSize(60, 60)
                        .setPopupItems(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position) {
                                        return true;
                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.right_center1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopup(v, PopupParams.TRIANGLE_RIGHT_CENTER)
                        .setPopupTriangleSize(60, 60)
                        .setPopupItems(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position) {
                                        return true;
                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.right_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopup(v, PopupParams.TRIANGLE_RIGHT_BOTTOM)
                        .setPopupTriangleSize(60, 60)
                        .setPopupItems(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position) {
                                        return true;
                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });
    }

    static class MyAdapter extends BaseAdapter {

        private ListViewActivity activity;
        private String[] items = new String[20];

        public MyAdapter(ListViewActivity activity) {
            this.activity = activity;
            for (int i = 0; i < 20; i++) {
                items[i] = "item" + i;
            }
        }

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public String getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(activity).inflate(android.R.layout.simple_list_item_1,
                    parent, false);
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));
            textView.setOnClickListener(v -> activity.showMyDialog(view));
            return view;
        }
    }
}
