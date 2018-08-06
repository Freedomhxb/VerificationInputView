package com.hxb.verification;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2018/8/6 0006.
 */
public class VerificationInputView extends LinearLayout {

    private int mChildLeftMargin;
    private int mChildRightMargin;
    private int mChildWidth;
    private int mChildHeight;
    private Drawable mChildBgFocus;
    private Drawable mChildBgNormal;
    private int mChildCount;
    private Listener listener;
    private List<EditText> mEditTextList = new ArrayList<>();
    private boolean mChildCanClickable = true;

    public interface Listener {
        public void onChange(String[] strings);

        public void onComplete(String string);
    }

    public VerificationInputView(Context context) {
        this(context, null);

    }

    public VerificationInputView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public VerificationInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.VerificationInputView);
        a.getInt(R.styleable.VerificationInputView_child_count, 4);

        mChildLeftMargin = (int) a.getDimension(R.styleable.VerificationInputView_child_left_margin, Utils.dp2px(getContext(), 4));
        mChildRightMargin = (int) a.getDimension(R.styleable.VerificationInputView_child_left_margin, Utils.dp2px(getContext(), 4));
        mChildWidth = (int) a.getDimension(R.styleable.VerificationInputView_child_width, Utils.dp2px(getContext(), 48));
        mChildHeight = (int) a.getDimension(R.styleable.VerificationInputView_child_height, Utils.dp2px(getContext(), 48));
        mChildBgFocus = a.getDrawable(R.styleable.VerificationInputView_child_bg_focus);
        mChildBgNormal = a.getDrawable(R.styleable.VerificationInputView_child_bg_normal);
        mChildCount = a.getInt(R.styleable.VerificationInputView_child_count, 4);

        a.recycle();
        addView();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public List<EditText> getEditTextList() {
        return mEditTextList;
    }

    public void setChildCanClickable(boolean childCanClickable) {
        mChildCanClickable = childCanClickable;
        if (!childCanClickable) {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 0; i < mEditTextList.size(); i++) {
                        EditText editText = mEditTextList.get(i);
                        String string = editText.getText().toString();
                        if (string.length() == 0 || i == mEditTextList.size() - 1) {
                            editText.requestFocus();
                            break;
                        }
                    }
                    Utils.showSoftInput((Activity) getContext());
                }
            });
        } else {
            setOnClickListener(null);
        }

    }

    public void setChildInputType(int inputType) {
        for (EditText editText : mEditTextList) {
            editText.setInputType(inputType);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mChildCanClickable) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return true;
        }
    }


    private void addView() {
        for (int i = 0; i < mChildCount; i++) {
            final EditText editText = new EditText(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mChildWidth, mChildHeight);
            if (i == 0) {
                layoutParams.leftMargin = 0;
            } else {
                layoutParams.leftMargin = mChildLeftMargin;
            }
            if (i == mChildCount - 1) {
                layoutParams.rightMargin = 0;
            } else {
                layoutParams.rightMargin = mChildRightMargin;
            }
            editText.setLayoutParams(layoutParams);
            if (i == 0) {
                setBg(editText, true);
            } else {
                setBg(editText, false);
            }

            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setGravity(Gravity.CENTER);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            editText.setTextColor(Color.BLACK);
            editText.addTextChangedListener(new MyTextWatcher(editText));
            editText.setOnKeyListener(new MyKeyListener(editText));
            editText.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    setBg(editText, hasFocus);
                    editText.setSelection(editText.getText().toString().length());

                }
            });
            addView(editText, i);
            mEditTextList.add(editText);
        }
    }

    private void setBg(EditText editText, boolean focus) {
        if (mChildBgNormal != null && !focus) {
            if (mChildBgFocus != null && editText.getText().toString().length() == 1) {
                editText.setBackgroundDrawable(mChildBgFocus);
            } else {
                editText.setBackgroundDrawable(mChildBgNormal);
            }
        } else if (mChildBgFocus != null && focus) {
            editText.setBackgroundDrawable(mChildBgFocus);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private EditText mEditText;


        public MyTextWatcher(EditText editText) {
            mEditText = editText;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 1) {
                int index = mEditTextList.indexOf(mEditText);
                if (index != mEditTextList.size() - 1) {
                    EditText editText = mEditTextList.get(index + 1);
                    editText.requestFocus();
                    setBg(editText, true);
                }
            }
            notifyDataChanged();
        }
    }

    private class MyKeyListener implements OnKeyListener {

        private EditText mEditText;


        public MyKeyListener(EditText editText) {
            mEditText = editText;

        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                int length = mEditText.getText().toString().length();
                if (length == 1) {
                    mEditText.setText("");
                } else {
                    //删除前面的
                    int index = mEditTextList.indexOf(mEditText);
                    if (index != 0) {
                        EditText editText = mEditTextList.get(index - 1);
                        editText.requestFocus();
                        editText.setText("");

                    }
                }
                notifyDataChanged();
                return true;
            }
            return false;
        }
    }

    private void notifyDataChanged() {

        if (listener == null) {
            return;
        }
        String[] strings = new String[mChildCount];
        boolean complete = true;
        for (int i = 0; i < mEditTextList.size(); i++) {
            EditText editText = mEditTextList.get(i);
            String content = editText.getText().toString();
            strings[i] = content;
            if (content.length() == 0) {
                complete = false;
            }
        }

        listener.onChange(strings);
        if (complete) {
            StringBuilder sb = new StringBuilder();
            for (String string : strings) {
                sb.append(string);
            }
            listener.onComplete(sb.toString());
        }
    }


}
