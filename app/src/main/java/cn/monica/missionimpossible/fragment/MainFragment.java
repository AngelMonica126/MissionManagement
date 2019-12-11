package cn.monica.missionimpossible.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.adapter.MainListAdapter;
import cn.monica.missionimpossible.util.ProgressDialogUtil;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class MainFragment extends Fragment implements ScreenShotable {

    private View containerView;
    private ListView choose_lv;
    protected int res;
    private Bitmap bitmap;
    private MainListAdapter mainListAdapter;
    public static MainFragment newInstance(int resId) {
        MainFragment contentFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
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
        View rootView = inflater.inflate(R.layout.main_class_fragment, container, false);
        initUI(rootView);
        initListView();
        init();

        return rootView;
    }

    private void init() {
    }

    private void initListView() {
        mainListAdapter =new MainListAdapter(getContext());
        choose_lv.setAdapter(mainListAdapter);
        choose_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(getContext(),"稍等","正在整理数据!");
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void initUI(View rootView) {
        choose_lv = (ListView) rootView.findViewById(R.id.choose_lv);
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
                MainFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}

