package com.sx.takeaway.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sx.takeaway.MyApplication;
import com.sx.takeaway.R;
import com.sx.takeaway.dagger2.component.DaggerAddressComponent;
import com.sx.takeaway.dagger2.module.AddressModule;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.id;


/**
 * @Author sunxin
 * @Date 2017/6/23 16:59
 * @Description 地址编辑界面
 */

public class EditReceiptAddressActivity extends BaseActivity {

    private static final String TAG = "EditReceiptActivity";
    @BindView(R.id.ib_back)
    ImageButton mIbBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ib_delete_address)
    ImageButton mIbDeleteAddress;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.rb_man)
    RadioButton mRbMan;
    @BindView(R.id.rb_women)
    RadioButton mRbWomen;
    @BindView(R.id.rg_sex)
    RadioGroup mRgSex;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.ib_delete_phone)
    ImageButton mIbDeletePhone;
    @BindView(R.id.et_receipt_address)
    EditText mEtReceiptAddress;
    @BindView(R.id.et_detail_address)
    EditText mEtDetailAddress;
    @BindView(R.id.tv_label)
    TextView mTvLabel;
    @BindView(R.id.ib_select_label)
    ImageView mIbSelectLabel;
    @BindView(R.id.bt_ok)
    Button mBtOk;

    /*
        * 工作列表：
        *   1，添加地址
        *   2，删除地址
        *   3，修改地址
        *   4，输入信息校验
        * */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_receipt_address);
        Log.e(TAG, "onCreate: 创建");
        ButterKnife.bind(this);

        init();

        //Dagger2注入
        DaggerAddressComponent
                .builder()
                .addressModule(new AddressModule(this))
                .build()
                .in(this);
    }

    private void init() {
        //将手机号填充上
        if (!TextUtils.isEmpty(MyApplication.phone)){
            mEtPhone.setText(MyApplication.phone);
            //将叉号显示出来
            mIbDeletePhone.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.ib_back, R.id.rb_man, R.id.rb_women, R.id.ib_select_label, R.id.bt_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.rb_man:
                break;
            case R.id.rb_women:
                break;
            case R.id.ib_select_label:
                break;
            case R.id.bt_ok:
                if (checkReceiptAddressInfo()){
                    //数据的存储
                    //获取界面数据
                    String name = mEtName.getText().toString().trim();
                    String sex = "";
                    int checkedRadioButtonId = mRgSex.getCheckedRadioButtonId();
                    switch (checkedRadioButtonId) {
                        case R.id.rb_man:
                            sex = "先生";
                            break;
                        case R.id.rb_women:
                            sex = "女士";
                            break;
                    }
                    String phone = mEtPhone.getText().toString().trim();
                    String receiptAddress = mEtReceiptAddress.getText().toString().trim();
                    String detailAddress = mEtDetailAddress.getText().toString().trim();
                    String label = mTvLabel.getText().toString();

                    mPresenter.create(name,sex,phone,receiptAddress,detailAddress,label);
                }
                break;
        }
    }

    private void changeReceiptAddressInfo() {
        //获取界面数据
        String name = mEtName.getText().toString().trim();
        String sex = "";
        int checkedRadioButtonId = mRgSex.getCheckedRadioButtonId();
        switch (checkedRadioButtonId) {
            case R.id.rb_man:
                sex = "先生";
                break;
            case R.id.rb_women:
                sex = "女士";
                break;
        }
        String phone = mEtPhone.getText().toString().trim();
        String receiptAddress = mEtReceiptAddress.getText().toString().trim();
        String detailAddress = mEtDetailAddress.getText().toString().trim();
        String label = mTvLabel.getText().toString();

        if (id == -1) {
            mPresenter.create(name, sex, phone, receiptAddress, detailAddress, label);
        } else {
//            mPresenter.update(id, name, sex, phone, receiptAddress, detailAddress, label);
        }
        finish();
    }

    //校验数据
    public boolean checkReceiptAddressInfo() {
        String name = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请填写联系人", Toast.LENGTH_SHORT).show();
            return false;
        }
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请填写手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isMobileNO(phone)) {
            Toast.makeText(this, "请填写合法的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        String receiptAddress = mEtReceiptAddress.getText().toString().trim();
        if (TextUtils.isEmpty(receiptAddress)) {
            Toast.makeText(this, "请填写收获地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        String address = mEtDetailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isMobileNO(String phone) {
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return phone.matches(telRegex);
    }

    @Override
    public void success(Object o) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: 销毁");
    }
}
