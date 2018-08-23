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
import com.mylhyl.circledialog.params.PopupParams;
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
                        .setPopupArrow(PopupParams.DIRECTION_TOP, PopupParams.GRAVITY_LEFT)
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
                        .setPopupArrow(PopupParams.DIRECTION_TOP, PopupParams.GRAVITY_LEFT)
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
                        .setPopupArrow(PopupParams.DIRECTION_TOP, PopupParams.GRAVITY_CENTER_HORIZONTAL)
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
                        .setPopupArrow(PopupParams.DIRECTION_TOP, PopupParams.GRAVITY_CENTER_HORIZONTAL)
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
                        .setPopupArrow(PopupParams.DIRECTION_TOP, PopupParams.GRAVITY_RIGHT)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.bottom_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopupAnchor(v)
                        .setPopupArrow(PopupParams.DIRECTION_BOTTOM, PopupParams.GRAVITY_LEFT)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });
        findViewById(R.id.bottom_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopupAnchor(v)
                        .setPopupArrow(PopupParams.DIRECTION_BOTTOM, PopupParams.GRAVITY_CENTER_HORIZONTAL)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.bottom_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopupAnchor(v)
                        .setPopupArrow(PopupParams.DIRECTION_BOTTOM, PopupParams.GRAVITY_RIGHT)
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
                                .setPopupAnchor(tabItemHK)
                                .setPopupArrow(PopupParams.DIRECTION_BOTTOM, PopupParams.GRAVITY_CENTER_HORIZONTAL)
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
                                .setPopupAnchor(tabItemZH)
                                .setPopupArrow(PopupParams.DIRECTION_BOTTOM, PopupParams.GRAVITY_CENTER_HORIZONTAL)
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
                                .setPopupAnchor(tabItemHN)
                                .setPopupArrow(PopupParams.DIRECTION_BOTTOM, PopupParams.GRAVITY_CENTER_HORIZONTAL)
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
                        .setPopupArrow(PopupParams.DIRECTION_LEFT, PopupParams.GRAVITY_TOP)
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
                        .setPopupArrow(PopupParams.DIRECTION_LEFT, PopupParams.GRAVITY_CENTER_VERTICAL)
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
                        .setPopupArrow(PopupParams.DIRECTION_LEFT, PopupParams.GRAVITY_BOTTOM)
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
                        .setPopupArrow(PopupParams.DIRECTION_RIGHT, PopupParams.GRAVITY_TOP)
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
                        .setPopupArrow(PopupParams.DIRECTION_RIGHT, PopupParams.GRAVITY_CENTER_VERTICAL)
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
                        .setPopupArrow(PopupParams.DIRECTION_RIGHT, PopupParams.GRAVITY_CENTER_VERTICAL)
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
                        .setPopupArrow(PopupParams.DIRECTION_RIGHT, PopupParams.GRAVITY_BOTTOM)
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

    public void showMyDialog(View anchor) {
        new CircleDialog.Builder()
                .setPopupAnchor(anchor)
                .setPopupArrow(PopupParams.DIRECTION_TOP, PopupParams.GRAVITY_CENTER_HORIZONTAL)
                .setPopup(new String[]{"1", "2", "3", "4"}
                        , new OnRvItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }
                        })
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
            textView.setOnClickListener(v -> activity.showMyDialog(view));
            return view;
        }
    }
}
