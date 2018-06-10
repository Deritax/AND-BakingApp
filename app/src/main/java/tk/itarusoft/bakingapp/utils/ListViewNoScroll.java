package tk.itarusoft.bakingapp.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 *  I take this code to solve the scrolling problem on the listview
 * source: https://www.journaldev.com/10444/android-custom-listview-non-scrollable
 * */
public class ListViewNoScroll extends ListView{

    public ListViewNoScroll(Context context) {
        super(context);
    }

    public ListViewNoScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewNoScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec_custom = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }
}