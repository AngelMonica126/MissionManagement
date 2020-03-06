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
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.nantaphop.hovertouchview.HoverTouchHelper;
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
import cn.monica.missionimpossible.bean.FileBean;
import cn.monica.missionimpossible.database.RecordDatabase;
import cn.monica.missionimpossible.bean.TitleViewStruct;
import cn.monica.missionimpossible.bean.TitleViewType;
import cn.monica.missionimpossible.database.ViewDatabase;
import cn.monica.missionimpossible.myinterface.OnMessageFragment;
import cn.monica.missionimpossible.util.ContentValueUtil;
import cn.monica.missionimpossible.util.FileUtil;
import cn.monica.missionimpossible.util.ToastUtil;
import cn.monica.missionimpossible.view.MyRatingBar;
import cn.monica.missionimpossible.view.MyThumbnail;
import cn.monica.missionimpossible.view.TitleView;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class InfoViewAndChangeFragment extends Fragment implements ScreenShotable, View.OnClickListener, View.OnLongClickListener, MyThumbnail.OnHoveListener {
    private static ViewDatabase data;
    private static OnMessageFragment messageFragment;
    private static RecordDatabase record;
    private View containerView;
    private ImageView  imageButton;
    protected int res;
    private Bitmap bitmap;
    private float scale;
    private final int maxSize = 8;
    private String desText = "图片描述";
    private String markText = "标签选择";
    private final int maxTagSize = 3;
    private List<FileBean> fileBeanList;
    private List<MediaBean> mediaBeanList;
    private List<TitleView>titleViews;
    private EditText            view_and_change_fragment_title              ;
    private Button              view_and_change_fragment_confirm            ;
    private ImageButton         view_and_change_fragment_tag_bt             ;
    private GridLayout          view_and_change_fragment_gridLayout         ;
    private TextView            view_and_change_fragment_picture_describe   ;
    private TextView            view_and_change_fragment_mark_tv            ;
    private TagContainerLayout  view_and_change_fragment_mTagContainerLayout;
    private LinearLayout        view_and_change_fragment_linearLayout       ;
    private NavigationTabStrip  view_and_change_fragment_step               ;
    private EditText            view_and_change_fragment_remarks            ;
    private TitleView           view_and_change_fragment_remind_time        ;
    private TitleView           view_and_change_fragment_deadline           ;
    private MyRatingBar         view_and_change_fragment_rating             ;
    private MyThumbnail myThumbnail;
    private FrameLayout root;
    public static InfoViewAndChangeFragment newInstance(int resId, RecordDatabase recordDatabase, OnMessageFragment onMessageFragment) {
        InfoViewAndChangeFragment infoViewAndChangeFragment = new InfoViewAndChangeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        infoViewAndChangeFragment.setArguments(bundle);
        record =         recordDatabase;
        data = ViewDatabase.findById(ViewDatabase.class,recordDatabase.getView_id());
        messageFragment = onMessageFragment;
        return infoViewAndChangeFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    private void setClick() {
        view_and_change_fragment_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(view_and_change_fragment_title.getText().toString())) {
                    ToastUtil.makeToast(getContext(),  "求求你！输个标题");

                } else {
                    saveInfo();
                }
            }
        });
        imageButton.setOnClickListener(this);
        view_and_change_fragment_tag_bt.setOnClickListener(this);
        view_and_change_fragment_mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {

            }

            @Override
            public void onTagLongClick(int position, String text) {
                view_and_change_fragment_mTagContainerLayout.removeTag(position);
            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });
    }

    private void saveInfo() {
        String name = record.getName();
        String title = view_and_change_fragment_title.getText().toString().trim();
        savePicture(name);
        saveRemarks(name);
        saveTags(name);
        saveDIY(name);
        record.setName(name);
        record.setTitle(title);
        record.setDeadline(view_and_change_fragment_deadline.getInfo().getInfo());
        record.setRemain_time(view_and_change_fragment_remind_time.getInfo().getInfo());
        record.setStep(view_and_change_fragment_step.getTabIndex());
        record.setPriority((int) view_and_change_fragment_rating.getCount());
        record.setBegin_time(-1);
        record.save();
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
        view_and_change_fragment_picture_describe.setText(desText + "(" + 0 + "/" + maxSize + ")");
        view_and_change_fragment_mark_tv.setText(markText + "(" + 0 + "/" + maxTagSize + ")");
        view_and_change_fragment_title.setText("");
        view_and_change_fragment_gridLayout.removeAllViews();
        view_and_change_fragment_mTagContainerLayout.removeAllTags();
        fileBeanList.clear();
        mediaBeanList.clear();
        titleViews.clear();
        view_and_change_fragment_linearLayout.removeAllViews();
        view_and_change_fragment_deadline.clear();
        view_and_change_fragment_remind_time.clear();
        view_and_change_fragment_step.setTabIndex(0);
        view_and_change_fragment_rating.setStarCount(1);
        view_and_change_fragment_remarks.setText("");
        createImageButton();

    }

    private void saveTags(String name) {
        String tagsName = name + ContentValueUtil.TAG;
        JSONArray array = new JSONArray();
        for (String info : view_and_change_fragment_mTagContainerLayout.getTags()) {
            array.put(info);
        }
        File file = new File(getContext().getFilesDir(), tagsName);
        FileUtil.writeFile(file, array.toString());
    }

    private void saveRemarks(String name) {
        String remarksName = name + ContentValueUtil.REMARKS;
        String info = view_and_change_fragment_remarks.getText().toString().trim();
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
                view_and_change_fragment_mark_tv.setText(markText + "(" + top.getTags().size() + "/" + maxTagSize + ")");
                view_and_change_fragment_mTagContainerLayout.setTags(top.getTags());
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
        if (view_and_change_fragment_mTagContainerLayout.getTags().size() == 0) {
            top.setTags("标签");
        } else {
            for (String s : view_and_change_fragment_mTagContainerLayout.getTags()) {
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
        view_and_change_fragment_title = (EditText) rootView.findViewById(R.id.view_and_change_fragment_title);
        view_and_change_fragment_confirm = (Button) rootView.findViewById(R.id.view_and_change_fragment_confirm);
        view_and_change_fragment_tag_bt = (ImageButton) rootView.findViewById(R.id.view_and_change_fragment_tag_bt);
        view_and_change_fragment_gridLayout = (GridLayout) rootView.findViewById(R.id.view_and_change_fragment_gridlayout);
        view_and_change_fragment_picture_describe = (TextView) rootView.findViewById(R.id.view_and_change_fragment_picture_describe);
        view_and_change_fragment_picture_describe.setText(desText + "(" + 0 + "/" + maxSize + ")");
        view_and_change_fragment_mark_tv = (TextView) rootView.findViewById(R.id.view_and_change_fragment_tv);
        view_and_change_fragment_mark_tv.setText(markText + "(" + 0 + "/" + maxTagSize + ")");
        view_and_change_fragment_mTagContainerLayout = (TagContainerLayout) rootView.findViewById(R.id.view_and_change_fragment_tag);
        view_and_change_fragment_linearLayout = (LinearLayout) rootView.findViewById(R.id.view_and_change_fragment_linearlayout);
        view_and_change_fragment_step = (NavigationTabStrip) rootView.findViewById(R.id.view_and_change_fragment_step);
        view_and_change_fragment_step.setTabIndex(0);
        view_and_change_fragment_remarks = (EditText)rootView.findViewById(R.id.view_and_change_fragment_remarks);
        view_and_change_fragment_remind_time = (TitleView)rootView.findViewById(R.id.view_and_change_fragment_remind_time);
        view_and_change_fragment_deadline = (TitleView)rootView.findViewById(R.id.view_and_change_fragment_deadline);
        view_and_change_fragment_rating = (MyRatingBar) rootView.findViewById(R.id.view_and_change_fragment_rating_bar);
        view_and_change_fragment_rating.setStar(1);
        root = (FrameLayout) rootView.findViewById(R.id.container);
        createImageButton();
        setViewDatebase();
    }

    private void setViewDatebase() {
        try {
            JSONArray array = new JSONArray(data.getInfo());
            for(int i = 0 ; i < array.length(); i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);
                TitleViewType type= TitleViewType.values()[jsonObject.getInt("type")];
                String title = jsonObject.getString("title");
                TitleView view = new TitleView(getContext(),new TitleViewStruct(type,title,null));
                view_and_change_fragment_linearLayout.addView(view);
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
        View rootView = inflater.inflate(R.layout.info_view_and_change_fragment, container, false);
        scale = getContext().getResources().getDisplayMetrics().density;
        initData();
        initUI(rootView);
        setInfo();
        setClick();
        return rootView;
    }

    private void setInfo() {
        view_and_change_fragment_title.setText(record.getTitle());
        view_and_change_fragment_remind_time.setInfo(record.getRemain_time());
        view_and_change_fragment_deadline.setInfo(record.getDeadline());
        view_and_change_fragment_rating.setStar(record.getPriority());
        view_and_change_fragment_step.setTabIndex(record.getStep());
        setRemarks();
        setTag();
        setPicture();
        setDIYInfo();
    }

    private void setRemarks() {
        view_and_change_fragment_remarks.setText("");
        File file = new File(getContext().getFilesDir(), record.getName() + ContentValueUtil.REMARKS);
        String des = FileUtil.readFile(file);
        view_and_change_fragment_remarks.setText(des);
    }

    private void setDIYInfo() {
        File file = new File(getContext().getFilesDir(), record.getName() + ContentValueUtil.DIY);
        try {
            JSONArray jsonArray = new JSONArray(FileUtil.readFile(file));
            for(int i = 0 ;i <jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                titleViews.get(i).setInfo(jsonObject.getString("info"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setPicture() {
        view_and_change_fragment_gridLayout.removeAllViews();
        view_and_change_fragment_gridLayout.bringToFront();
        File thumbnailFile = new File(getContext().getFilesDir(), record.getName() + ContentValueUtil.THUMBNAILPICTURE);
        String thumbnailBigPath = FileUtil.readFile(thumbnailFile);
        if (TextUtils.isEmpty(thumbnailBigPath)) {
            createImageView();
        } else {
            File originalFile = new File(getContext().getFilesDir(), record.getName() + ContentValueUtil.ORIGINALPICTURE);
            String originalPath = FileUtil.readFile(originalFile);
            String[] thumbnailBigPaths = thumbnailBigPath.split(ContentValueUtil.DIVIDE);
            String[] originalPaths = originalPath.split(ContentValueUtil.DIVIDE);
            for (int i = 0; i < thumbnailBigPaths.length; ++i) {
                if (!TextUtils.isEmpty(thumbnailBigPaths[i]) && !TextUtils.isEmpty(originalPaths[i])) {
                    myThumbnail = new MyThumbnail(getContext());
                    GridLayout.LayoutParams paramsGl = new GridLayout.LayoutParams(
                            new ViewGroup.LayoutParams((int) (83 * scale + 0.5f), (int) (83 * scale + 0.5f)));
                    paramsGl.setMargins((int) (2 * scale + 0.5f), 0, 0, 0);
                    myThumbnail.setOriginalPath(originalPaths[i].trim());
                    Picasso.with(getActivity()).load("file://" + thumbnailBigPaths[i]).into(myThumbnail);
                    myThumbnail.setBackgroundResource(R.drawable.imagebutton_shape);
                    myThumbnail.setScaleType(ImageButton.ScaleType.FIT_CENTER);
                    myThumbnail.setOnHoveListener(this);
                    myThumbnail.setTag(false);
                    view_and_change_fragment_gridLayout.addView(myThumbnail, paramsGl);
                    HoverTouchHelper.make(root, myThumbnail);
                }

            }

        }
    }
    private void createImageView() {
        imageButton = new ImageView(getContext());
        GridLayout.LayoutParams paramsGl = new GridLayout.LayoutParams(
                new ViewGroup.LayoutParams((int) (100 * scale + 0.5f), (int) (180 * scale + 0.5f)));
        paramsGl.setMargins((int) (2 * scale + 0.5f), 0, 0, 0);
        imageButton.setScaleType(ImageButton.ScaleType.FIT_CENTER);
        imageButton.setBackgroundResource(R.drawable.imagebutton_shape);
        view_and_change_fragment_gridLayout.addView(imageButton, paramsGl);
    }
    private void setTag() {
        view_and_change_fragment_mTagContainerLayout.removeAllTags();
        File file = new File(getContext().getFilesDir(), record.getName() + ContentValueUtil.TAG);
        try {
            JSONArray jsonArray = new JSONArray(FileUtil.readFile(file));
            for(int i = 0 ;i <jsonArray.length();i++)
                view_and_change_fragment_mTagContainerLayout.addTag(String.valueOf(jsonArray.get(i)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                InfoViewAndChangeFragment.this.bitmap = bitmap;
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
        if (v.getId() == R.id.view_and_change_fragment_tag_bt) {
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
                        view_and_change_fragment_picture_describe.setText(desText + "(" + imageMultipleResultEvent.getResult().size() + "/" + maxSize + ")");
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
        view_and_change_fragment_gridLayout.removeAllViews();
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
            view_and_change_fragment_gridLayout.addView(otherButton, paramsGl);
            fileBean = new FileBean(image.get(i).getThumbnailBigPath(), image.get(i).getOriginalPath(), otherButton);
            fileBeanList.add(fileBean);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        view_and_change_fragment_gridLayout.removeView(v);
        int i = 0;
        for (; i < fileBeanList.size(); ++i) {
            if (fileBeanList.get(i).getImageButton().equals(v)) {
                break;
            }
        }
        fileBeanList.remove(i);
        mediaBeanList.remove(i);
        view_and_change_fragment_picture_describe.setText(desText + "(" + view_and_change_fragment_gridLayout.getChildCount() + "/" + maxSize + ")");
        if (view_and_change_fragment_gridLayout.getChildCount() == 0) {
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
        view_and_change_fragment_gridLayout.addView(imageButton, paramsGl);
    }

    @Override
    public void onStartHover() {

    }

    @Override
    public void onStopHover() {

    }
}
