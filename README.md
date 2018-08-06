# VerificationInputView
使用方便的验证码输入框


## 使用方式

1.
` implementation 'com.hxb.verification:1.0.0'`
 
2.


    <com.hxb.verification.VerificationInputView
             android:id="@+id/verification_input_view_1"
             android:layout_width="wrap_content"
             android:layout_height="wrap_content"
             app:child_bg_focus="@drawable/layer_bottom_orange"
             app:child_bg_normal="@drawable/layer_bottom_gray"
             >
             
                        
 3.设置监听
 
     mVerificationInputView.setListener(new VerificationInputView.Listener() {
                  @Override
                  public void onChange(String[] strings) {
                      //文字改变就会回调
                      Log.d(mTag,"onChange: "+ Arrays.toString(strings));
                  }
      
                  @Override
                  public void onComplete(String string) {
                   //仅在完成后回调
                      Log.d(mTag,"onComplete: "+string);
                  }
              });
              
              
## 属性

|属性                |解释               |
|:--------------------|:-----------------|
|child_count           |输入框的数量                 |
|child_left_margin|输入框的左边距(默认为4dp,第一个为0,不可改变) |
|child_right_margin|输入框的右边距(默认为4dp,最后一个为0,不可改变)|
|child_width|输入框的宽度(默认为48dp)                 |
|child_height|输入框的右边距(默认为48dp)|
|child_bg_focus|输入框获取焦点时的背景或者有文字的背景|
|child_bg_normal|输入框没有文字时候的背景|

## 方法

1.  setChildInputType 设置输入框的输入类型,参考android.widget.TextView.setInputType(int)

2.  getEditTextList  获取所有的输入框

3.   setChildCanClickable  设置输入框是否可以点击

