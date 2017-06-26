package com.sx.takeaway.ui.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import com.sx.takeaway.model.dao.bean.AddressBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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

    private int id;
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

        //Dagger2注入
        DaggerAddressComponent
                .builder()
                .addressModule(new AddressModule(this))
                .build()
                .in(this);
        init();
    }

    private void init() {
        //将手机号填充上
        if (!TextUtils.isEmpty(MyApplication.phone)) {
            mEtPhone.setText(MyApplication.phone);
            //将叉号显示出来
            mIbDeletePhone.setVisibility(View.VISIBLE);
        }

        //判断是编辑还是新增
        id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            //显示删除按钮，是修改地址
            mTvTitle.setText("修改地址");
            mIbDeleteAddress.setVisibility(View.VISIBLE);

            //把原来的数据回显到EditText中
            mPresenter.finbDataById(id);
        } else {
            mTvTitle.setText("新增地址");
            mIbDeleteAddress.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick({R.id.ib_back, R.id.rb_man, R.id.rb_women, R.id.ib_select_label, R.id.bt_ok,R.id.ib_delete_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.rb_man:
                break;
            case R.id.rb_women:
                break;
            case R.id.ib_delete_address:
                //删除地址
                showDeleteAlert();
                break;
            case R.id.ib_select_label:
                showLabelAlert();//弹出标签
                break;
            case R.id.bt_ok:
                if (checkReceiptAddressInfo()) {
                  changeReceiptAddressInfo();
                }
                break;
        }
    }

    /**
     * 提示用户删除地址
     */
    private void showDeleteAlert() {
        new AlertDialog.Builder(this)
                .setTitle("删除地址")
                .setMessage("确定要删除该地址吗？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.delete(id);
                    }
                })
                .create()
                .show();
    }

    String[] addressLabels = new String[]{"无", "家", "公司", "学校"};
    int[] bgLabels = new int[]{
            0,
            Color.parseColor("#fc7251"),//家  橙色
            Color.parseColor("#468ade"),//公司 蓝色
            Color.parseColor("#02c14b"),//学校   绿色

    };

    private void showLabelAlert() {
        new AlertDialog.Builder(this)
                .setTitle("选择标签")
                .setItems(addressLabels, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which != 0) {
                            mTvLabel.setText(addressLabels[which]);
                            mTvLabel.setBackgroundColor(bgLabels[which]);
                        }
                    }
                })
                .create()
                .show();

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
            mPresenter.update(id, name, sex, phone, receiptAddress, detailAddress, label);
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
        //更新界面
        if (o instanceof AddressBean) {
            //修改界面控件信息
            AddressBean addressBean = (AddressBean) o;
            mEtName.setText(addressBean.name);
            mEtName.setSelection(addressBean.name.length());//把光标放在最后

            mEtPhone.setText(addressBean.phone);
            mEtReceiptAddress.setText(addressBean.receiptAddress);
            mEtDetailAddress.setText(addressBean.detailAddress);

            if (!TextUtils.isEmpty(addressBean.sex)) {
                if (addressBean.sex.equals("先生")) {
                    mRbMan.setChecked(true);
                } else {
                    mRbWomen.setChecked(true);
                }
            }

            if (!TextUtils.isEmpty(addressBean.label)) {
                int index = getLabelIndex(addressBean.label);
                mTvLabel.setText(addressLabels[index]);
                mTvLabel.setBackgroundColor(bgLabels[index]);
            }

        } else {
            finish();
        }
    }

    /**
     * 获取标签的下标
     *
     * @param label
     * @return
     */
    private int getLabelIndex(String label) {
        int index = 0;
        for (int i = 0; i < addressLabels.length; i++) {
            if (label.equals(addressLabels[i])) ;
            index = i;
            break;
        }
        return index;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: 销毁");
    }
}
