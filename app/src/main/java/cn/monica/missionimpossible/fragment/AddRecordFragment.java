package cn.monica.missionimpossible.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.activity.MainActivity;
import cn.monica.missionimpossible.bean.FileBean;
import cn.monica.missionimpossible.bean.RecordDatabase;
import cn.monica.missionimpossible.bean.TitleViewStruct;
import cn.monica.missionimpossible.bean.TitleViewType;
import cn.monica.missionimpossible.bean.ViewDatabase;
import cn.monica.missionimpossible.myinterface.OnMessageFragment;
import cn.monica.missionimpossible.util.CalenderUtil;
import cn.monica.missionimpossible.util.ContentValueUtil;
import cn.monica.missionimpossible.util.FileUtil;
import cn.monica.missionimpossible.util.ToastUtil;
import cn.monica.missionimpossible.view.TitleView;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


/**
 * Created by dream on 2018/7/1.
 */

public class AddRecordFragment extends Fragment implements ScreenShotable, View.OnClickListener, View.OnLongClickListener {
    private static ViewDatabase data;
    private static OnMessageFragment messageFragment;
    private View containerView;
    private ImageButton imageButton;
    private ImageButton tag_bt;
    private GridLayout gridLayout;
    private Button confirm;
    private TagContainerLayout mTagContainerLayout;
    private LinearLayout linearLayout;
    private EditText record_title;
    protected int res;
    private Bitmap bitmap;
    private float scale;
    private final int maxSize = 8;
    private TextView picture_describe;
    private TextView mark_tv;
    private String desText = "图片描述";
    private String markText = "标签选择";
    private final int maxTagSize = 3;
    private List<FileBean> fileBeanList;
    private List<MediaBean> mediaBeanList;
    private List<TitleView>titleViews;
    private EditText add_record_fragment_remarks;
    private TitleView add_record_fragment_remind_time;
    private TitleView add_record_fragment_deadline;
    private NavigationTabStrip add_record_fragment_step;
    private RatingBar add_record_fragment_rating;

    public static AddRecordFragment newInstance(int resId, ViewDatabase viewDatabase, OnMessageFragment onMessageFragment) {
        AddRecordFragment addRecordFragment = new AddRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        addRecordFragment.setArguments(bundle);
        data = viewDatabase;
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
        imageButton.setOnClickListener(this);
        tag_bt.setOnClickListener(this);
        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {

            }

            @Override
            public void onTagLongClick(int position, String text) {
                mTagContainerLayout.removeTag(position);
            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });
    }

    private void saveInfo() {
        String name = R.string.angel + CalenderUtil.getInstance().getDateName();
        String title = record_title.getText().toString().trim();
        savePicture(name);
        saveRemarks(name);
        saveTags(name);
        saveDIY(name);
        RecordDatabase recordDatabase = new RecordDatabase();//6
        recordDatabase.setName(name);
        recordDatabase.setTitle(title);
        recordDatabase.setDeadline(add_record_fragment_deadline.getInfo().getInfo());
        recordDatabase.setRemain_time(add_record_fragment_remind_time.getInfo().getInfo());
        recordDatabase.setStep(add_record_fragment_step.getTabIndex());
        recordDatabase.setPriority(add_record_fragment_rating.getNumStars());
        recordDatabase.save();
        ToastUtil.makeToast(getContext(),  "保存成功!");
        clearFragment();
        Message message = new Message();
        message.what = 0;
        messageFragment.setMassage(message);

    }

    private void saveDIY(String name) {
        name = name + ContentValueUtil.DIY;
        JSONArray jsonArray = new JSONArray();
        try {
            for(TitleView titleView:titleViews)
            {
                JSONObject jsonObject = new JSONObject();
                TitleViewStruct struct = titleView.getInfo();
                int type = struct.getType().ordinal();
                jsonObject.put("type",type);
                jsonObject.put("title",struct.getTitle());
                jsonObject.put("info",struct.getInfo());
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        File file = new File(getContext().getFilesDir(), name);
        FileUtil.writeFile(file, jsonArray.toString().trim());
    }

    private void clearFragment() {
        picture_describe.setText(desText + "(" + 0 + "/" + maxSize + ")");
        mark_tv.setText(markText + "(" + 0 + "/" + maxTagSize + ")");
        record_title.setText("");
        gridLayout.removeAllViews();
        mTagContainerLayout.removeAllTags();
        fileBeanList.clear();
        mediaBeanList.clear();
        titleViews.clear();
        linearLayout.removeAllViews();
        add_record_fragment_deadline.clear();
        add_record_fragment_remind_time.clear();
        add_record_fragment_step.setTabIndex(0);
        add_record_fragment_rating.setRating(1);
        add_record_fragment_remarks.setText("");
        createImageButton();

    }

    private void saveTags(String name) {
        String tagsName = name + ContentValueUtil.TAG;
        JSONArray array = new JSONArray();
        for (String info : mTagContainerLayout.getTags()) {
            array.put(info);
        }
        File file = new File(getContext().getFilesDir(), tagsName);
        FileUtil.writeFile(file, array.toString());
    }

    private void saveRemarks(String name) {
        String remarksName = name + ContentValueUtil.REMARKS;
        String info = add_record_fragment_remarks.getText().toString().trim();
        File file = new File(getContext().getFilesDir(), remarksName);
        FileUtil.writeFile(file, info);
    }

    private void savePicture(String name) {
        String thumbnailBigPath = name + ContentValueUtil.THUMBNAILPICTURE;
        String originalPath = name+ ContentValueUtil.ORIGINALPICTURE;
        JSONArray originalSB = new JSONArray();
        JSONArray thumbnailSB = new JSONArray();
        for (FileBean fileBean:fileBeanList)
        {
            originalSB.put(fileBean.getOriginalPath());
            thumbnailSB.put(fileBean.getThumbnailBigPath());
        }
        File thumbnailFile = new File(getContext().getFilesDir(), thumbnailBigPath);
        FileUtil.writeFile(thumbnailFile, thumbnailSB.toString().trim());
        File originalFile = new File(getContext().getFilesDir(), originalPath);
        FileUtil.writeFile(originalFile, originalSB.toString().trim());
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final AlertDialog alertDialog = builder.create();
        final View view = View.inflate(getContext(), R.layout.add_record_dailog, null);
        alertDialog.setView(view);
        alertDialog.show();
        Button comfirm = (Button) view.findViewById(R.id.dialog_confirm);
        final TagContainerLayout top = (TagContainerLayout) view.findViewById(R.id.dialog_tag_top);
        loadTop(top);
        final TagContainerLayout bottom = (TagContainerLayout) view.findViewById(R.id.dialog_tag_bottom);
        loadTags(bottom);
        ImageButton dialog_tag_add = (ImageButton) view.findViewById(R.id.dialog_tag_add);
        final EditText dialog_editext = (EditText) view.findViewById(R.id.dialog_editext);
        dialog_tag_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(dialog_editext.getText().toString().trim()))
                    return;
                else {
                    if (top.getTags().size() >= maxTagSize) {
                        ToastUtil.makeToast(getContext(), "朋友！最多三个");

                    } else {
                        if (top.getTags().size() > 0 && top.getTags().get(0).trim().equals("标签")) {
                            top.removeTag(0);
                        }
                        for (String info : top.getTags()) {
                            if (info.equals(dialog_editext.getText().toString().trim())) {
                                dialog_editext.setText("");
                                return;
                            }
                        }
                        top.addTag(dialog_editext.getText().toString().trim());
                        for (String info : bottom.getTags()) {
                            if (info.equals(dialog_editext.getText().toString().trim())) {
                                dialog_editext.setText("");
                                return;
                            }
                        }
                        bottom.addTag(dialog_editext.getText().toString().trim());
                    }

                }
                dialog_editext.setText("");
            }
        });
        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if (top.getTags().size() > 0 && top.getTags().get(0).trim().equals("标签"))
                    return;
                mark_tv.setText(markText + "(" + top.getTags().size() + "/" + maxTagSize + ")");
                mTagContainerLayout.setTags(top.getTags());
                addAndSaveTags(bottom.getTags());
            }
        });
        top.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {

            }

            @Override
            public void onTagLongClick(int position, String text) {
                top.removeTag(position);
            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });
        bottom.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                if (top.getTags().size() > 0 && top.getTags().get(0).trim().equals("标签")) {
                    top.removeTag(0);
                }
                if (top.getTags().size() >= maxTagSize) {
                    ToastUtil.makeToast(getContext(), "朋友！最多三个");
                } else {
                    for (String info : top.getTags()) {
                        if (info.equals(bottom.getTagText(position))) {
                            return;
                        }
                    }
                    top.addTag(bottom.getTagText(position));
                }
            }

            @Override
            public void onTagLongClick(int position, String text) {
                bottom.removeTag(position);
            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });
    }

    private void loadTop(TagContainerLayout top) {
        if (mTagContainerLayout.getTags().size() == 0) {
            top.setTags("标签");
        } else {
            for (String s : mTagContainerLayout.getTags()) {
                if (s.trim() != null) {
                    top.addTag(s);
                }
            }
        }
    }

    private void addAndSaveTags(List<String> bottom) {
        StringBuilder sb = new StringBuilder();
        for (String info : bottom) {
            sb.append(info);
            sb.append(ContentValueUtil.DIVIDE);
        }
        String info = sb.toString();
        File file = new File(getContext().getFilesDir(), "tags");
        FileUtil.writeFile(file, info);

    }

    private void loadTags(TagContainerLayout bottom) {
        judgeFileExists();
        File file = new File(getContext().getFilesDir(), "tags");
        String res = FileUtil.readFile(file);
        if (res != null) {
            String[] stings = res.split(ContentValueUtil.DIVIDE);
            for (String s : stings) {
                if (!TextUtils.isEmpty(s)) {
                    bottom.addTag(s);
                }
            }
        }
    }

    private void judgeFileExists() {
        File logFile = new File(getContext().getFilesDir(), "tags");
        if (FileUtil.isEmpty(logFile)) {
            FileUtil.createNew(logFile);
        }
    }

    private void initUI(View rootView) {
        record_title = (EditText) rootView.findViewById(R.id.record_title);
        confirm = (Button) rootView.findViewById(R.id.record_confirm);
        tag_bt = (ImageButton) rootView.findViewById(R.id.tag_bt);
        gridLayout = (GridLayout) rootView.findViewById(R.id.record_gridlayout);
        picture_describe = (TextView) rootView.findViewById(R.id.picture_describe);
        picture_describe.setText(desText + "(" + 0 + "/" + maxSize + ")");
        mark_tv = (TextView) rootView.findViewById(R.id.mark_tv);
        mark_tv.setText(markText + "(" + 0 + "/" + maxTagSize + ")");
        mTagContainerLayout = (TagContainerLayout) rootView.findViewById(R.id.record_tag);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.add_record_fragment_linearlayout);
        add_record_fragment_step = (NavigationTabStrip) rootView.findViewById(R.id.add_record_fragment_step);
        add_record_fragment_step.setTabIndex(0);
        add_record_fragment_remarks = (EditText)rootView.findViewById(R.id.add_record_fragment_remarks);
        add_record_fragment_remind_time = (TitleView)rootView.findViewById(R.id.add_record_fragment_remind_time);
        add_record_fragment_deadline = (TitleView)rootView.findViewById(R.id.add_record_fragment_deadline);
        add_record_fragment_rating = (RatingBar)rootView.findViewById(R.id.add_record_fragment_rating);
        createImageButton();
        setViewDatebase();
    }

    private void setViewDatebase() {
        try {
            JSONArray array = new JSONArray(data.getInfo());
            for(int i = 0 ;i<array.length(); i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);
                TitleViewType type= TitleViewType.values()[jsonObject.getInt("type")];
                String title = jsonObject.getString("title");
                TitleView view = new TitleView(getContext(),new TitleViewStruct(type,title,null));
                linearLayout.addView(view);
                titleViews.add(view);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_record_fragment, container, false);
        scale = getContext().getResources().getDisplayMetrics().density;
        initData();
        initUI(rootView);
        setClick();
        return rootView;
    }

    private void initData() {
        fileBeanList = new ArrayList<>();
        mediaBeanList = new ArrayList<>();
        titleViews = new ArrayList<>();
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
                AddRecordFragment.this.bitmap = bitmap;
            }
        };
        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tag_bt) {
            createDialog();

        } else {
            openImageSelectMultiMethod(maxSize);
        }
    }


    private void openImageSelectMultiMethod(int size) {
        RxGalleryFinal rxGalleryFinal = RxGalleryFinal
                .with(getActivity())
                .crop()
                .image()
                .multiple();
        if (mediaBeanList != null && !mediaBeanList.isEmpty()) {
            rxGalleryFinal
                    .selected(mediaBeanList);
        }
        rxGalleryFinal.maxSize(size)
                .imageLoader(ImageLoaderType.PICASSO)
                .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {

                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        mediaBeanList = imageMultipleResultEvent.getResult();
                        setImage(mediaBeanList);
                        picture_describe.setText(desText + "(" + imageMultipleResultEvent.getResult().size() + "/" + maxSize + ")");
                        ToastUtil.makeToast(getContext(), "已选择" + imageMultipleResultEvent.getResult().size() +
                                "张图片");

                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        ToastUtil.makeToast(getContext(), "OVER");

                    }
                })
                .openGallery();
    }

    private void setImage(List<MediaBean> image) {
        gridLayout.removeAllViews();
        ImageButton otherButton = null;
        FileBean fileBean = null;
        for (int i = 0; i < image.size(); i++) {
            otherButton = new ImageButton(getContext());
            GridLayout.LayoutParams paramsGl = new GridLayout.LayoutParams(
                    new ViewGroup.LayoutParams((int) (100 * scale + 0.5f), (int) (180 * scale + 0.5f)));
            paramsGl.setMargins((int) (2 * scale + 0.5f), 0, 0, 0);
            Picasso.with(getActivity()).load("file://" + image.get(i).getThumbnailBigPath()).into(otherButton);
            otherButton.setBackgroundResource(R.drawable.imagebutton_shape);
            otherButton.setOnClickListener(this);
            otherButton.setOnLongClickListener(this);
            otherButton.setScaleType(ImageButton.ScaleType.FIT_CENTER);
            gridLayout.addView(otherButton, paramsGl);
            fileBean = new FileBean(image.get(i).getThumbnailBigPath(), image.get(i).getOriginalPath(), otherButton);
            fileBeanList.add(fileBean);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        gridLayout.removeView(v);
        int i = 0;
        for (; i < fileBeanList.size(); ++i) {
            if (fileBeanList.get(i).getImageButton().equals(v)) {
                break;
            }
        }
        fileBeanList.remove(i);
        mediaBeanList.remove(i);
        picture_describe.setText(desText + "(" + gridLayout.getChildCount() + "/" + maxSize + ")");
        if (gridLayout.getChildCount() == 0) {
            createImageButton();
        }
        return true;
    }

    private void createImageButton() {
        imageButton = new ImageButton(getContext());
        GridLayout.LayoutParams paramsGl = new GridLayout.LayoutParams(
                new ViewGroup.LayoutParams((int) (100 * scale + 0.5f), (int) (180 * scale + 0.5f)));
        paramsGl.setMargins((int) (2 * scale + 0.5f), 0, 0, 0);
        imageButton.setOnClickListener(this);
        imageButton.setScaleType(ImageButton.ScaleType.FIT_XY);
        imageButton.setBackgroundResource(R.drawable.imagebutton_shape);
        gridLayout.addView(imageButton, paramsGl);
    }
}
