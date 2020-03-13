package cn.monica.missionimpossible.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.bean.TitleViewStruct;
import cn.monica.missionimpossible.bean.TitleViewType;
import cn.monica.missionimpossible.database.ViewDatabase;
import cn.monica.missionimpossible.myinterface.OnMessageFragment;
import cn.monica.missionimpossible.myinterface.OnTitleViewDeletelistener;
import cn.monica.missionimpossible.myinterface.OnViewChooseListener;
import cn.monica.missionimpossible.util.CalenderUtil;
import cn.monica.missionimpossible.util.DialogHelper;
import cn.monica.missionimpossible.util.ToastUtil;
import cn.monica.missionimpossible.view.TitleShowView;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class AddViewFragment extends Fragment implements ScreenShotable {
    private static OnMessageFragment messageFragment;
    private View containerView;
    private ImageButton imageButton;
    private ImageButton add_bt;
    private Button confirm;
    protected int res;
    private Bitmap bitmap;
    private float scale;
    private List<TitleShowView>titleViews;
    private LinearLayout view;
    private GridLayout gridLayout;
    private EditText record_title;
    public static AddViewFragment newInstance(int resId, OnMessageFragment onMessageFragment) {
        AddViewFragment addRecordFragment = new AddViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        addRecordFragment.setArguments(bundle);
        messageFragment = onMessageFragment;
        return addRecordFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    private void setClick() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(record_title.getText().toString())) {
                    ToastUtil.makeToast(getContext(),  "求求你！输个标题");

                } else {
                    saveInfo();
                }

            }
        });
        add_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.getInstance().createViewChooseDialog(getContext(), new OnViewChooseListener() {
                    @Override
                    public void save(TitleViewType type, String title) {
                        AddView(type,title);
                    }
                });
            }
        });
    }

    private void AddView(TitleViewType type, String title) {
        TitleShowView titleShowView = new TitleShowView(getContext(),new TitleViewStruct(type,title,null), new OnTitleViewDeletelistener() {
            @Override
            public void delete(TitleShowView v) {
                view.removeView(v);
                titleViews.remove(view);
            }
        });
        view.addView(titleShowView);
        titleViews.add(titleShowView);
    }

    private void saveInfo() {
        String name = R.string.angel + CalenderUtil.getInstance().getDateName();
        ViewDatabase viewDatabase = new ViewDatabase(name,getViews(),record_title.getText().toString().trim());
        viewDatabase.save();
        ToastUtil.makeToast(getContext(),  "保存成功!");
        clearFragment();
        Message message = new Message();
        message.what = 0;
        messageFragment.setMassage(message);
    }

    private String getViews() {
        JSONArray array = new JSONArray();
        try {
            for(TitleShowView titleView:titleViews)
            {
                TitleViewStruct struct = titleView.getInfo();
                JSONObject object = new JSONObject();
                TitleViewType msgType = struct.getType();
                int type = msgType.ordinal();
                object.put("type",type);
                object.put("title",struct.getTitle());
                array.put(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array.toString();
    }

    private void clearFragment() {
        gridLayout.removeAllViews();
        createImageButton();
        titleViews.clear();
        view.removeAllViews();
        record_title.setText("");
    }


    private void initUI(View rootView) {
        confirm = (Button) rootView.findViewById(R.id.record_confirm);
        add_bt = (ImageButton) rootView.findViewById(R.id.add_bt);
        view = (LinearLayout) rootView.findViewById(R.id.view);
        gridLayout = (GridLayout) rootView.findViewById(R.id.record_gridlayout);
        record_title = (EditText) rootView.findViewById(R.id.record_title);
        createImageButton();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_view_fragment, container, false);
        scale = getContext().getResources().getDisplayMetrics().density;
        initData();
        initUI(rootView);
        setClick();
        return rootView;
    }

    private void initData() {
        titleViews = new ArrayList<>();
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
//                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
//                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                containerView.draw(canvas);
//                AddViewFragment.this.bitmap = bitmap;
            }
        };
        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    private void createImageButton() {
        imageButton = new ImageButton(getContext());
        GridLayout.LayoutParams paramsGl = new GridLayout.LayoutParams(
                new ViewGroup.LayoutParams((int) (100 * scale + 0.5f), (int) (180 * scale + 0.5f)));
        paramsGl.setMargins((int) (2 * scale + 0.5f), 0, 0, 0);
        imageButton.setScaleType(ImageButton.ScaleType.FIT_XY);
        imageButton.setBackgroundResource(R.drawable.imagebutton_shape);
        gridLayout.addView(imageButton, paramsGl);
    }
}
