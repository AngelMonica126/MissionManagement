package cn.monica.missionimpossible.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.monica.missionimpossible.R;

public class TitleView extends RelativeLayout {
    TextView title;
    ImageButton delete;
    GridLayout gridLayout;
    EditText   editText;
    public TitleView(Context context) {
        this(context,null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = (View) View.inflate(context, R.layout.titleview, this);
        title = (TextView) view.findViewById(R.id.title);
        delete = (ImageButton) view.findViewById(R.id.item_delete);
        gridLayout = (GridLayout) view.findViewById(R.id.gridlayout);
        editText = (EditText) view.findViewById(R.id.edittext);
    }
    public void setType(int type)
    {
        switch (type)
        {
            case 0:
                editText.setVisibility(VISIBLE);
                break;
            case 1:
                gridLayout.setVisibility(VISIBLE);
                break;
        }
    }
}
