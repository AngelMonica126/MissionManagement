package cn.monica.missionimpossible.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.adapter.RecordExpandableAdapter;
import cn.monica.missionimpossible.bean.FragmentType;
import cn.monica.missionimpossible.database.RecordDatabase;
import cn.monica.missionimpossible.bean.ResetTitleMessage;
import cn.monica.missionimpossible.myinterface.OnMessageFragment;
import cn.monica.missionimpossible.myinterface.OnRecordExpandableReplaceFragment;
import cn.monica.missionimpossible.util.Color;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class MainFragment extends Fragment implements ScreenShotable, OnRecordExpandableReplaceFragment {
    private static OnMessageFragment messageFragment;
    private View containerView;
    private ExpandableListView expandableListView;
    protected int res;
    private Bitmap bitmap;
    private static RecordExpandableAdapter recordExpandableAdapter;
    public static MainFragment newInstance(int resId, OnMessageFragment onMessageFragment) {
        MainFragment contentFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);
        if(recordExpandableAdapter!=null)
        recordExpandableAdapter.notifyDataSetChanged();
        messageFragment = onMessageFragment;
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
        recordExpandableAdapter =new RecordExpandableAdapter(getContext(),this);
        expandableListView.setAdapter(recordExpandableAdapter);
    }

    private void initUI(View rootView) {
        expandableListView = (ExpandableListView) rootView.findViewById(R.id.main_fragment_expand_listview);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if(expandableListView.isGroupExpanded(i))
                {
                    expandableListView.collapseGroup(i);
                    return true;
                }

                for(int j = 0 ;j<expandableListView.getChildCount();j++)
                    expandableListView.collapseGroup(j);
                expandableListView.expandGroup(i);
                return true;
            }
        });
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

    @Override
    public void onClick(RecordDatabase recordDatabase) {
        replaceInfoViewAndChangeFragment(recordDatabase);
        Log.e("monica","monica");
    }

    public ScreenShotable replaceInfoViewAndChangeFragment(RecordDatabase recordDatabase) {
        ResetTitleMessage titleMessage = new ResetTitleMessage(Color.VIEW_AND_CHANGE_COLOR,R.drawable.view_and_change_top,recordDatabase.getTitle(), FragmentType.InfoViewAndChangeFragment);
        Message message = new  Message();
        message.what = 1;
        message.obj = titleMessage;
        messageFragment.setMassage(message);
       InfoViewAndChangeFragment infoViewAndChangeFragment = InfoViewAndChangeFragment.newInstance(R.drawable.view_bk, recordDatabase,messageFragment);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, infoViewAndChangeFragment).commit();
        return infoViewAndChangeFragment;
    }
}

