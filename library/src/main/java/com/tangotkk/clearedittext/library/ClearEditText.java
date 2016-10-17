package com.tangotkk.clearedittext.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by kris on 16/10/17.
 */
public class ClearEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {
        /**
         * 删除按钮使用Drawable
         */
        private Drawable mClearDrawable;
        /**
         * 控件是否有焦点
         */
        private boolean hasFoucs;

        public ClearEditText(Context context) {
            this(context, null);
        }

        public ClearEditText(Context context, AttributeSet attrs) {
            this(context, attrs, android.R.attr.editTextStyle);
        }

        public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init(attrs);
        }


    private void init(AttributeSet attrs) {

        TypedArray ta = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ClearEditText, 0, 0);
        int delHeight = ta.getDimensionPixelSize(R.styleable.ClearEditText_del_height, -1);
        int delWidth = ta.getDimensionPixelSize(R.styleable.ClearEditText_del_width, -1);
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = ContextCompat.getDrawable(getContext(), R.drawable.del);
        }
        if(delHeight < 0){
            delHeight = mClearDrawable.getIntrinsicHeight();
        }
        if(delWidth < 0){
            delWidth = mClearDrawable.getIntrinsicWidth();
        }
        int top = 0;
        mClearDrawable.setBounds(0,top , delWidth, delHeight);
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }



    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        if(hasFoucs){
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }



}
