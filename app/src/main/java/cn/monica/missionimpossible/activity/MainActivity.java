package cn.monica.missionimpossible.activity;


import android.Manifest;
import android.animation.Animator;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;

import android.os.Build;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import cn.monica.missionimpossible.R;

import cn.monica.missionimpossible.bean.FragmentType;
import cn.monica.missionimpossible.bean.ResetTitleMessage;
import cn.monica.missionimpossible.engine.LockDialogHelper;
import cn.monica.missionimpossible.engine.RecordManager;
import cn.monica.missionimpossible.engine.SimpleRxGalleryFinal;
import cn.monica.missionimpossible.fragment.AddViewFragment;
import cn.monica.missionimpossible.fragment.MainFragment;
import cn.monica.missionimpossible.fragment.RecordBrowseFragment;
import cn.monica.missionimpossible.fragment.RecordInfoFragment;
import cn.monica.missionimpossible.fragment.ViewBrowseFragment;
import cn.monica.missionimpossible.myinterface.OnFinishLoadRecord;
import cn.monica.missionimpossible.myinterface.OnMessageFragment;
import cn.monica.missionimpossible.service.NotifyService;
import cn.monica.missionimpossible.util.ContentValueUtil;
import cn.monica.missionimpossible.util.EmailUtil;
import cn.monica.missionimpossible.util.ImmerseUtil;
import cn.monica.missionimpossible.util.RemindUtil;
import cn.monica.missionimpossible.util.SpUtil;
import cn.monica.missionimpossible.util.UesrUtil;
import yalantis.com.sidemenu.model.SlideMenuItem;

import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;


import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.util.ViewAnimator;

public class MainActivity extends ActionBarActivity implements ViewAnimator.ViewAnimatorListener, Animator.AnimatorListener, OnMessageFragment, OnFinishLoadRecord {
    private FragmentType type;
    private yalantis.com.sidemenu.util.ViewAnimator viewAnimator;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    public static int res = R.drawable.main_bk;
    private GridLayout gridLayout;
    public static Toolbar toolbar;
    public static int topId = R.drawable.main_top;
    public static String color = "#d23b20";
    public static TextView topTitle;
    private ViewBrowseFragment viewBrowseFragment;
    private AddViewFragment addViewFragment;
    private MainFragment mainFragment;
    private RecordBrowseFragment recordBrowseFragment;
    private Toast toast = null;
    private TextView textView = null;
    private long firstTime = 0;
    private float scale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPower();
        initData();
        initNotifycation();
        setImmsere();
        initToast();
        initIconChooser();
        initUI();
        createMenuList();
        viewAnimator = new yalantis.com.sidemenu.util.ViewAnimator<>(this, list, mainFragment, drawerLayout, this);
        RecordManager.getInstance().Update(this);
    }

    private void initServeice() {
       startService(new Intent(getBaseContext(), NotifyService.class));
    }

    private void initNotifycation() {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("mua~~~~~~~");
        mBuilder.setAutoCancel(false);
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        mBuilder.setOngoing(true);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mBuilder.setContentIntent(pIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(970126, mBuilder.build());
    }

    public void requestPower() {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA,}, 1);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET,}, 1);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE,}, 1);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,}, 1);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,}, 1);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.VIBRATE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.VIBRATE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.VIBRATE,}, 1);
            }
        }
    }

    private void initData() {
        type = FragmentType.MainFragment;
        LockDialogHelper.getInstance().init(this);
        SugarContext.init(getApplicationContext());
        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());
        scale = this.getResources().getDisplayMetrics().density;
        RemindUtil.getInstance().init(getApplicationContext());
    }

    private void initToast() {
        toast = new Toast(this);
        textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setTextSize(18f);
        textView.setBackgroundResource(R.drawable.imagebutton_shape);
        textView.setTextColor(0xffd1d2d1);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(textView);
    }

    private void initIconChooser() {
    }

    private void setImmsere() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);// SDK21
        }
        ImmerseUtil.setImmerse(this, color);
    }

    private void initUI() {
        if (TextUtils.isEmpty(SpUtil.getString(this, ContentValueUtil.LOCK, null)))
            LockDialogHelper.getInstance().createLockDialog();
        mainFragment = MainFragment.newInstance(R.drawable.main_bk, this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mainFragment)
                .commit();
        viewBrowseFragment = ViewBrowseFragment.newInstance(this.res, this);
        recordBrowseFragment = RecordBrowseFragment.newInstance(this.res);
        addViewFragment = AddViewFragment.newInstance(this.res, this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        gridLayout = (GridLayout) findViewById(R.id.left_drawer);
        gridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });
        topTitle = (TextView) findViewById(R.id.top_title);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                gridLayout.removeAllViews();
                gridLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && gridLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }


    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentValueUtil.CLOSE, R.drawable.bk_transplant);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(ContentValueUtil.HOSTPAGE, R.drawable.host_page);
        list.add(menuItem);
        SlideMenuItem menuItem1 = new SlideMenuItem(ContentValueUtil.VIEWBROWSE, R.drawable.add_record);
        list.add(menuItem1);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentValueUtil.ADDVIEW, R.drawable.add_view);
        list.add(menuItem3);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (drawerToggle != null)
            drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (drawerToggle != null)
            drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle != null)
            if (drawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case ContentValueUtil.CLOSE:
                return screenShotable;
            case ContentValueUtil.HOSTPAGE:
                return replaceChooseClassFragment(screenShotable, position);
            case ContentValueUtil.VIEWBROWSE:
                return replaceViewBrowseFragment(screenShotable, position);
            case ContentValueUtil.ADDVIEW:
                return replaceAddViewFragment(screenShotable, position);
            default:
                return screenShotable;
        }
    }

    private ScreenShotable replaceRecordBrowseFragment(ScreenShotable screenShotable, int position) {
        if (this.type == FragmentType.RecordBrowseFragment)
            return recordBrowseFragment;
        resetTitle(cn.monica.missionimpossible.util.Color.RECORD_BROWSE_COLOR, R.drawable.browse_top, ContentValueUtil.record_browse, FragmentType.RecordBrowseFragment);

        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        recordBrowseFragment = recordBrowseFragment.newInstance(R.drawable.browse_bk);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, recordBrowseFragment).commit();
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, position, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(yalantis.com.sidemenu.util.ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        animator.addListener(this);
        animator.start();
        return recordBrowseFragment;
    }

    private ScreenShotable replaceAddViewFragment(ScreenShotable screenShotable, int position) {
        if (this.type == FragmentType.AddViewFragment)
            return addViewFragment;
        resetTitle(cn.monica.missionimpossible.util.Color.ADD_VIEW_COLOR, R.drawable.view_top, ContentValueUtil.add_view, FragmentType.AddViewFragment);

        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        addViewFragment = AddViewFragment.newInstance(R.drawable.view_bk, this);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, addViewFragment).commit();
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, position, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(yalantis.com.sidemenu.util.ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        animator.addListener(this);
        animator.start();
        return addViewFragment;
    }

    private ScreenShotable replaceViewBrowseFragment(ScreenShotable screenShotable, int position) {
        if (this.type == FragmentType.ViewBrowseFragment)
            return viewBrowseFragment;
        resetTitle(cn.monica.missionimpossible.util.Color.VIEW_BROWSE_COLOR, R.drawable.view_browse_top, ContentValueUtil.browse_view, FragmentType.ViewBrowseFragment);
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        viewBrowseFragment = ViewBrowseFragment.newInstance(R.drawable.view_browse_bk, this);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, viewBrowseFragment).commit();

        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, position, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(yalantis.com.sidemenu.util.ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        animator.addListener(this);
        animator.start();
        return viewBrowseFragment;
    }

    private ScreenShotable replaceChooseClassFragment(ScreenShotable screenShotable, int position) {
        if (this.type == FragmentType.MainFragment)
            return mainFragment;
        return changeToHost(0, position);

    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (this.res != R.drawable.main_bk) {
            backToMain();

        } else {
            if (secondTime - firstTime > 2000) {
                textView.setText("再按一次退出程序");
                toast.show();
                firstTime = secondTime;
            } else {
                SugarContext.terminate();
                toast.cancel();
                super.onBackPressed();
            }
        }

    }

    private void backToMain() {
        WindowManager wm1 = this.getWindowManager();
        int width = wm1.getDefaultDisplay().getWidth();
        int height = wm1.getDefaultDisplay().getHeight();
        changeToHost(width, height);
    }

    private void resetTitle(String color, int topId, String title, FragmentType type) {
        this.type = type;
        ImmerseUtil.setImmerse(this, color);
        toolbar.setBackgroundResource(topId);
        topTitle.setText(title);
    }

    private MainFragment changeToHost(final int width, final int height) {
        RecordManager.getInstance().Update(new OnFinishLoadRecord() {
            @Override
            public void onFinish() {
                resetTitle(cn.monica.missionimpossible.util.Color.MAIN_COLOR, R.drawable.main_top, ContentValueUtil.host_page, FragmentType.MainFragment);
                View view = findViewById(R.id.content_frame);
                int finalRadius = Math.max(view.getWidth(), view.getHeight());
                mainFragment = MainFragment.newInstance(R.drawable.main_bk, MainActivity.this);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mainFragment).commit();
                Animator animator = ViewAnimationUtils.createCircularReveal(view, width / 2, height, 0, finalRadius);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(yalantis.com.sidemenu.util.ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
                animator.addListener(MainActivity.this);
                animator.start();
            }
        });
        return mainFragment;
    }


    @Override
    public void addViewToContainer(View view) {
        view.setBackgroundResource(R.drawable.bk_b);
        GridLayout.LayoutParams paramsGl = new GridLayout.LayoutParams(
                new ViewGroup.LayoutParams((int) (60 * scale + 0.5f), (int) (60 * scale + 0.5f)));
        gridLayout.addView(view, paramsGl);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SimpleRxGalleryFinal.get().onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onAnimationStart(Animator animator) {
        toolbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    @Override
    public void setMassage(Message massage) {
        switch (massage.what) {
            case 0:
                backToMain();
                break;
            case 1:
                ResetTitleMessage titleMessage = (ResetTitleMessage) massage.obj;
                resetTitle(titleMessage.getColor(), titleMessage.getTopId(), titleMessage.getTitle(), titleMessage.getType());
                break;
        }
    }

    @Override
    public void onFinish() {
        resetTitle(cn.monica.missionimpossible.util.Color.MAIN_COLOR, R.drawable.main_top, ContentValueUtil.host_page, FragmentType.MainFragment);
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        mainFragment = MainFragment.newInstance(R.drawable.main_bk, this);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mainFragment).commit();
        initServeice();
    }
}
