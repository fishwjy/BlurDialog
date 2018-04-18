package com.vincent.blurdialog.sample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.vincent.blurdialog.BlurDialog;
import com.vincent.blurdialog.listener.OnItemClick;
import com.vincent.blurdialog.listener.OnNegativeClick;
import com.vincent.blurdialog.listener.OnPositiveClick;

import java.util.ArrayList;
import java.util.List;

import static com.vincent.blurdialog.BlurDialog.TYPE_DELETE;
import static com.vincent.blurdialog.BlurDialog.TYPE_PROGRESS;
import static com.vincent.blurdialog.BlurDialog.TYPE_SINGLE_OPTION;
import static com.vincent.blurdialog.BlurDialog.TYPE_SINGLE_SELECT;

public class MainActivity extends AppCompatActivity {
    private BlurDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.def_confirm:
                dialog = new BlurDialog.Builder()
                        .isCancelable(true)
                        .isOutsideCancelable(true)
                        .message("Messi is the best football player")
                        .positiveMsg("Yes")
                        .negativeMsg("No")
                        .positiveClick(new OnPositiveClick() {
                            @Override
                            public void onClick() {
                                Toast.makeText(MainActivity.this, "Yes, definitely!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .negativeClick(new OnNegativeClick() {
                            @Override
                            public void onClick() {
                                Toast.makeText(MainActivity.this, "No, CR7 is the best!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                Toast.makeText(MainActivity.this, "I have no idea about it!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .type(BlurDialog.TYPE_DOUBLE_OPTION)
                        .build(MainActivity.this);
                dialog.show();
                break;
            case R.id.def_single:
                dialog = new BlurDialog.Builder()
                        .isCancelable(true)
                        .isOutsideCancelable(true)
                        .message("Messi is the best football player")
                        .positiveMsg("Yes")
                        .positiveClick(new OnPositiveClick() {
                            @Override
                            public void onClick() {
                                Toast.makeText(MainActivity.this, "Yes, definitely!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                Toast.makeText(MainActivity.this, "I have no idea about it!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .type(TYPE_SINGLE_OPTION)
                        .build(MainActivity.this);
                dialog.show();
                break;
            case R.id.def_none:
                ImageView iv = new ImageView(this);
                iv.setImageResource(R.drawable.ic_fingerprint);
                dialog = new BlurDialog.Builder()
                        .isCancelable(true)
                        .isOutsideCancelable(true)
                        .message("Please input your fingerprint")
                        .centerView(iv,
                                new FrameLayout.LayoutParams(
                                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()),
                                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics())))
                        .type(BlurDialog.TYPE_NONE_OPTION)
                        .build(MainActivity.this);
                dialog.show();
                break;
            case R.id.def_del:
                dialog = new BlurDialog.Builder()
                        .radius(10)//dp
                        .isCancelable(true)
                        .isOutsideCancelable(true)
                        .message("Do you want delete this project?")
                        .positiveClick(new OnPositiveClick() {
                            @Override
                            public void onClick() {
                                Toast.makeText(MainActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .negativeClick(new OnNegativeClick() {
                            @Override
                            public void onClick() {
                                Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                Toast.makeText(MainActivity.this, "I have no idea about it!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .type(TYPE_DELETE)
                        .build(MainActivity.this);
                dialog.show();
                break;
            case R.id.def_list:
                final List<CharSequence> list = new ArrayList<>();
                list.add(Html.fromHtml("R10"));
                list.add(Html.fromHtml("KUN"));
                list.add(Html.fromHtml("Suarez"));
                list.add(Html.fromHtml("Neymar"));

                dialog = new BlurDialog.Builder()
                        .isCancelable(true)
                        .isOutsideCancelable(true)
                        .message("Messi the best football player")
                        .singleList(list)
                        .itemClick(new OnItemClick() {
                            @Override
                            public void onClick(CharSequence item) {
                                if (item.equals(list.get(0))) {
                                    Toast.makeText(MainActivity.this, "Ronaldinho", Toast.LENGTH_SHORT).show();
                                }

                                if (item.equals(list.get(1))) {
                                    Toast.makeText(MainActivity.this, "Sergio Agüero", Toast.LENGTH_SHORT).show();
                                }

                                if (item.equals(list.get(2))) {
                                    Toast.makeText(MainActivity.this, "Luis Suarez", Toast.LENGTH_SHORT).show();
                                }

                                if (item.equals(list.get(3))) {
                                    Toast.makeText(MainActivity.this, "Neymar JR", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .negativeClick(new OnNegativeClick() {
                            @Override
                            public void onClick() {
                                Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                            }
                        })
                        .type(TYPE_SINGLE_SELECT)
                        .build(MainActivity.this);
                dialog.show();
                break;
            case R.id.waiting:
                dialog = new BlurDialog.Builder()
                        .isCancelable(true)
                        .isOutsideCancelable(true)
                        .message("Please wait...")
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                Toast.makeText(MainActivity.this, "I have no idea about it!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .type(BlurDialog.TYPE_WAIT)
                        .build(MainActivity.this);
                dialog.show();
                break;
            case R.id.progress:
                dialog = new BlurDialog.Builder()
                        .isCancelable(true)
                        .isOutsideCancelable(true)
                        .message("正在下载中...")
                        .positiveMsg("取消")
                        .positiveClick(new OnPositiveClick() {
                            @Override
                            public void onClick() {
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this, "Download cancel", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onProgressFinishedListener(new BlurDialog.OnProgressFinishedListener() {
                            @Override
                            public void onProgressFinished() {
                                Toast.makeText(MainActivity.this, "Download finished", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .type(TYPE_PROGRESS)
                        .build(MainActivity.this);
                dialog.show();
                percent = 0;
                doProgress();
                break;
            case R.id.custom_confirm:
                dialog = new BlurDialog.Builder()
                        .isCancelable(true)
                        .isOutsideCancelable(true)
                        .message(Html.fromHtml("<h2><font color=\"#FF8C00\">Messi</font></h2>The best football player"))
                        .positiveMsg(Html.fromHtml("<font color=\"#EC4E4F\">Yes</font>")) //You can change color by Html
                        .negativeMsg("No")
                        .positiveClick(new OnPositiveClick() {
                            @Override
                            public void onClick() {
                                Toast.makeText(MainActivity.this, "Yes, definitely!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .negativeClick(new OnNegativeClick() {
                            @Override
                            public void onClick() {
                                Toast.makeText(MainActivity.this, "No, CR7 is the best!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                Toast.makeText(MainActivity.this, "I have no idea about it!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .type(BlurDialog.TYPE_DOUBLE_OPTION)
                        .build(MainActivity.this);
                dialog.show();
                break;
            case R.id.custom_list:
                final List<CharSequence> li = new ArrayList<>();
                li.add(Html.fromHtml("<font color=\"#FF8C00\">R10</font>"));
                li.add(Html.fromHtml("<font color=\"#00FFFF\">KUN</font>"));
                li.add(Html.fromHtml("<font color=\"#DC143C\">Suarez</font>"));
                li.add(Html.fromHtml("<font color=\"#7CFC00\">Neymar</font>"));
                dialog = new BlurDialog.Builder()
                        .isCancelable(true)
                        .isOutsideCancelable(true)
                        .message(Html.fromHtml("<h2><font color=\"#FF8C00\">Messi</font></h2>The best football player"))
                        .singleList(li)
                        .singleListCancelMsg(Html.fromHtml("<font color=\"#EC4E4F\">Exit</font>"))//default is blue cancel
                        .itemClick(new OnItemClick() {
                            @Override
                            public void onClick(CharSequence item) {
                                if (item.equals(li.get(0))) {
                                    Toast.makeText(MainActivity.this, "Ronaldinho", Toast.LENGTH_SHORT).show();
                                }

                                if (item.equals(li.get(1))) {
                                    Toast.makeText(MainActivity.this, "Sergio Agüero", Toast.LENGTH_SHORT).show();
                                }

                                if (item.equals(li.get(2))) {
                                    Toast.makeText(MainActivity.this, "Luis Suarez", Toast.LENGTH_SHORT).show();
                                }

                                if (item.equals(li.get(3))) {
                                    Toast.makeText(MainActivity.this, "Neymar JR", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .negativeClick(new OnNegativeClick() {
                            @Override
                            public void onClick() {
                                Toast.makeText(MainActivity.this, "exit the best friend list of Messi!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                            }
                        })
                        .type(TYPE_SINGLE_SELECT)
                        .build(MainActivity.this);
                dialog.show();
                break;
        }
        return true;
    }

    private float percent = 0;
    private void doProgress() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.setProgress(percent += 6);
                doProgress();
            }
        }, 1000);
    }
}
