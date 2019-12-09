package cn.monica.missionimpossible.fragment;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.activity.MainActivity;
import cn.monica.missionimpossible.adapter.RecordAdapter;
import cn.monica.missionimpossible.adapter.ViewAdapter;
import cn.monica.missionimpossible.engine.LockDialogHelper;
import cn.monica.missionimpossible.engine.RecordManager;
import cn.monica.missionimpossible.engine.ViewManager;
import cn.monica.missionimpossible.util.ImmerseUtil;
import cn.monica.missionimpossible.util.MyInterface;
import cn.monica.missionimpossible.util.ProgressDialogUtil;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class ViewBrowseFragment extends Fragment implements ScreenShotable {
    private View containerView;
    protected int res;
    private Bitmap bitmap;
    private ListView browse_lv;
    private ViewAdapter myAdapter;
    private AddRecordFragment addRecordFragment;
    public static ViewBrowseFragment newInstance(int resId) {
        ViewBrowseFragment hostPageFragment = new ViewBrowseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        hostPageFragment.setArguments(bundle);
        return hostPageFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_browse_fragment, container, false);
        initUI(rootView);
        initListview();
        return rootView;
    }

    private void initListview() {
        myAdapter = new ViewAdapter(getContext());
        browse_lv.setAdapter(myAdapter);
        browse_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                replaceAddInfoFragment(position);
            }
        });
    }

    public ScreenShotable replaceAddInfoFragment(int position) {
        MainActivity.res = R.drawable.view_bk;
        MainActivity.topId = R.drawable.view_top;
        MainActivity.color = "#122c24";
        MainActivity.topTitle.setText(R.string.add_record);
        ImmerseUtil.setImmerse(getActivity(), MainActivity.color);
        MainActivity.toolbar.setBackgroundResource(MainActivity.topId);
        addRecordFragment = AddRecordFragment.newInstance(this.res, ViewManager.getInstance().getViews().get(position));
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, addRecordFragment).commit();
        return addRecordFragment;
     }

    private void initUI(View rootView) {
        browse_lv = (ListView) rootView.findViewById(R.id.browse_lv);
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                ViewBrowseFragment.this.bitmap = bitmap;
            }
        };
        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}