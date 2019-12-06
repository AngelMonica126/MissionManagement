package cn.monica.missionimpossible.engine;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.monica.missionimpossible.RecordDatabase;
import cn.monica.missionimpossible.bean.ViewDatabase;
import cn.monica.missionimpossible.util.CalenderUtil;
import cn.monica.missionimpossible.util.ContentValueUtil;
import cn.monica.missionimpossible.util.FileUtil;
import cn.monica.missionimpossible.util.MyInterface;
import cn.monica.missionimpossible.util.SpUtil;

/**
 * Created by dream on 2018/7/5.
 */

public class ViewManager {
    private static ViewManager viewManager = new ViewManager();
    private Context context;
    private int nowday= CalenderUtil.getInstance().getDayFromOriginal();;

    public List<ViewDatabase> getViews() {
        return ViewDatabase.listAll(ViewDatabase.class);
    }

    public static ViewManager getInstance() {
        return viewManager;
    }

}
