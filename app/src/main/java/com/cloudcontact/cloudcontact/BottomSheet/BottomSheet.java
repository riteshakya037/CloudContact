package com.cloudcontact.cloudcontact.BottomSheet;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cloudcontact.cloudcontact.Parse.ParseRow;
import com.cloudcontact.cloudcontact.R;

/**
 * Created by Ritesh on 7/8/2015.
 */
public class BottomSheet extends Dialog {
    RecyclerView recyclerView;
    BottomSheetAdaptor bottomSheetAdaptor;
    ParseRow parseRow;
    TextView title;

    public BottomSheet(Context context, ParseRow parseRow) {
        super(context, R.style.BottomSheet_Dialog);
        this.parseRow = parseRow;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //-------------------Set requires parameters of dialog-------------/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        getWindow().setAttributes(params);
        setCanceledOnTouchOutside(true);
        //-------------------------END-------------------------------------/

        setContentView(R.layout.slide_out_menu);
        recyclerView = (RecyclerView) findViewById(R.id.slideOutMenu);
        title = (TextView) findViewById(R.id.bottomSheetTitleName);
        initialize(parseRow);
    }

    public void initialize(ParseRow parseRow) {
        bottomSheetAdaptor = new BottomSheetAdaptor(getContext(), parseRow, title);
        recyclerView.setAdapter(bottomSheetAdaptor);
        recyclerView.setLayoutManager(new MyLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }


    /**
     * Custom LinerLayoutManager because the default one DOESNT support wrap_content
     */
    public class MyLinearLayoutManager extends LinearLayoutManager {
        Context context;

        public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
            this.context = context;
        }

        private int mMeasuredDimension = 0;

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state,
                              int widthSpec, int heightSpec) {
            int width;
            int height = 0;

            //get width of Screen
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            width = display.getWidth();

            //cumulative sum of each view/element of recycler View
            for (int i = 0; i < getItemCount(); i++) {
                mMeasuredDimension = measureScrapChildHeight(recycler, i,
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED));
                height = height + mMeasuredDimension;
            }
            setMeasuredDimension(width, height);
        }

        /**
         * returns the height of individual rows
         */
        private int measureScrapChildHeight(RecyclerView.Recycler recycler, int position, int widthSpec,
                                            int heightSpec) {
            View view = recycler.getViewForPosition(position);
            int measuredDimension = 0;
            if (view != null) {
                RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
                int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                        getPaddingLeft() + getPaddingRight(), p.width);
                int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                        getPaddingTop() + getPaddingBottom(), p.height);
                view.measure(childWidthSpec, childHeightSpec);
                measuredDimension = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                recycler.recycleView(view);
            }
            return measuredDimension;
        }
    }
}
