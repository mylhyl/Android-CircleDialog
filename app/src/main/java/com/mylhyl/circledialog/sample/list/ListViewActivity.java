package com.mylhyl.circledialog.sample.list;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
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

import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.sample.R;
import com.mylhyl.circledialog.view.listener.OnRvItemClickListener;

public class ListViewActivity extends AppCompatActivity {

    ActionMenuView mActionMenuView;
    private Toolbar mToolbar;

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
                        .setPopupAnchor(v)
                        .setPopupArrow(Gravity.TOP, Gravity.LEFT)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });
        findViewById(R.id.top_left1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopupAnchor(v)
                        .setPopupArrow(Gravity.TOP, Gravity.LEFT)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });
        findViewById(R.id.top_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopupAnchor(v)
                        .setPopupArrow(Gravity.TOP, Gravity.CENTER_HORIZONTAL)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.top_center1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopupAnchor(v)
                        .setPopupArrow(Gravity.TOP, Gravity.CENTER_HORIZONTAL)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.top_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopupAnchor(v)
                        .setPopupArrow(Gravity.TOP, Gravity.RIGHT)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        new CircleDialog.Builder()
                                .setPopupAnchor(findViewById(R.id.tab_hk))
                                .setPopupArrow(Gravity.BOTTOM, Gravity.LEFT)
                                .setPopup(new String[]{"1", "2", "3", "4"}
                                        , new OnRvItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

                                            }
                                        })
                                .show(getSupportFragmentManager());
                        break;
                    case 1:
                        new CircleDialog.Builder()
                                .setPopupAnchor(findViewById(R.id.tab_zh))
                                .setPopupArrow(Gravity.BOTTOM, Gravity.CENTER_HORIZONTAL)
                                .setPopup(new String[]{"1", "2", "3", "4"}
                                        , new OnRvItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

                                            }
                                        })
                                .show(getSupportFragmentManager());
                        break;
                    case 2:
                        new CircleDialog.Builder()
                                .setPopupAnchor(findViewById(R.id.tab_hn))
                                .setPopupArrow(Gravity.BOTTOM, Gravity.RIGHT)
                                .setPopup(new String[]{"1", "2", "3", "4"}
                                        , new OnRvItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

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
                        .setPopupAnchor(v)
                        .setPopupArrow(Gravity.LEFT, Gravity.TOP)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.left_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopupAnchor(v)
                        .setPopupArrow(Gravity.LEFT, Gravity.CENTER_VERTICAL)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });
        findViewById(R.id.left_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopupAnchor(v)
                        .setPopupArrow(Gravity.LEFT, Gravity.BOTTOM)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.right_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopupAnchor(v)
                        .setPopupArrow(Gravity.RIGHT, Gravity.TOP)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.right_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopupAnchor(v)
                        .setPopupArrow(Gravity.RIGHT, Gravity.CENTER_VERTICAL)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.right_center1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopupAnchor(v)
                        .setPopupArrow(Gravity.RIGHT, Gravity.CENTER_VERTICAL)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.right_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopupAnchor(v)
                        .setPopupArrow(Gravity.RIGHT, Gravity.BOTTOM)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });
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
        return true;
    }

    public void showMyDialog(String string) {
        new CircleDialog.Builder()
                .setTitle("标题")
                .setText(string)
                .setPositive("确定", null)
                .show(getSupportFragmentManager());
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
            textView.setOnClickListener(v -> activity.showMyDialog(((TextView) v).getText().toString()));
            return view;
        }
    }
}
