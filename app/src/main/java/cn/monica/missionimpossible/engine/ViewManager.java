package cn.monica.missionimpossible.engine;

import android.content.Context;

import java.util.List;

import cn.monica.missionimpossible.database.ViewDatabase;
import cn.monica.missionimpossible.util.CalenderUtil;

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
