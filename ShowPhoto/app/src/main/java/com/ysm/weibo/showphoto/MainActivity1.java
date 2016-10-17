package com.ysm.weibo.showphoto;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.gson.reflect.TypeToken;
import com.ysm.weibo.showphoto.bean.ImageContent;
import com.ysm.weibo.showphoto.bean.ImageType;
import com.ysm.weibo.showphoto.bean.ImageTypeBean;
import com.ysm.weibo.showphoto.photo.BitmapUtil;
import com.ysm.weibo.showphoto.photo.GetSimplePhotoHelper;
import com.ysm.weibo.showphoto.photo.SimplePhoto;
import com.ysm.weibo.showphoto.utils.ListUtils;
import com.ysm.weibo.showphoto.utils.ParserJson;
import com.ysm.weibo.showphoto.utils.ToastUtil;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class MainActivity1 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button mAlbumBtn;
    private Button mCommitBtn;//提交
    private EditText mImageNameEt;
    private EditText mImageRemarkEt;
    private ImageView mPhotoIv;
    private Spinner mTypeSpinner;

    private List<String> mTypeNames = new ArrayList<>();
    private List<ImageType> mImageTypeData;
    private int position;
    private  ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        mAlbumBtn = (Button) findViewById(R.id.album_btn);
        mCommitBtn = (Button) findViewById(R.id.commit_btn);
        mImageNameEt = (EditText) findViewById(R.id.image_name_et);
        mImageRemarkEt = (EditText) findViewById(R.id.image_remark_et);
        mPhotoIv = (ImageView) findViewById(R.id.photo_iv);
        mTypeSpinner = (Spinner) findViewById(R.id.type_spinner);

         progressDialog=new ProgressDialog(MainActivity1.this);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        initSpinner();
        initListener();
    }

    private void initListener() {
        mAlbumBtn.setOnClickListener(this);
        mCommitBtn.setOnClickListener(this);
        mTypeSpinner.setOnItemSelectedListener(this);
    }

    private void initSpinner() {
        BmobQuery<ImageType> queryAll = new BmobQuery<>();
        queryAll.findObjects(new FindListener<ImageType>() {
            @Override
            public void done(List<ImageType> list, BmobException e) {
                if (e == null) {
                    Log.i("TAG", "查询成功：list=" + list.toString());
                    if (!ListUtils.isEmpty(list)) {
                        mImageTypeData = list;
                        for (ImageType type : list) {
                            mTypeNames.add(type.getTypeName());
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity1.this, android.R.layout.simple_spinner_dropdown_item, mTypeNames);
                    mTypeSpinner.setAdapter(adapter);
                    mTypeSpinner.setSelection(0);
                } else {
                    Log.i("TAG", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private void upLoadImage(final String path) {
        progressDialog.show();
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (progressDialog!= null&&progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Log.d("TAG", "上传文件成功:" + bmobFile.getFileUrl());
                    ToastUtil.showToast(MainActivity1.this,"上传文件成功");

//                    insertObject(new ImageContent("测试2", bmobFile, bmobFile.getFileUrl()));
                    if (!ListUtils.isEmpty(mImageTypeData)) {
                        insertObject(new ImageContent(
                                mImageNameEt.getText().toString().trim(),
                                bmobFile,
                                bmobFile.getFileUrl(),
                                mImageTypeData.get(position).getTypeId(),
                                mImageTypeData.get(position).getTypeName(),
                                mImageRemarkEt.getText().toString().trim()
                        ));
                    } else {
                        ToastUtil.showToast(MainActivity1.this, "上传文件失败: mImageTypeData is null");
                    }
                } else {
                    ToastUtil.showToast(MainActivity1.this, "上传文件失败");
                    Log.d("TAG", "上传文件失败：msg=" + e.getMessage() + ",   errorCode=" + e.getErrorCode());
                }
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
                Log.d("TAG", "onProgress ：" + value);
                progressDialog.setProgress(value);
            }

            @Override
            public void doneError(int code, String msg) {
                super.doneError(code, msg);
                Log.d("TAG", "doneError msg：" + msg);
                Log.d("TAG", "doneError code：" + code);
                if (progressDialog!= null&&progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
//                ToastUtil.showToast(TestActivity.this,"doneError msg：" + msg);
//                ToastUtil.showToast(TestActivity.this,"doneError code：" + code);
            }
        });
    }

    private void insertObject(ImageContent imageFile) {
        Log.d("TAG", "保存文件内容 imageFile：" + imageFile.toString());
        imageFile.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.d("TAG", "保存文件内容成功 s：" + s);
                    ToastUtil.showToast(MainActivity1.this,"保存文件内容成功 s：");
                } else {
                    Log.d("TAG", "保存文件内容失败 msg=：" + e.getMessage() + ",   errorCode=" + e.getErrorCode());
                }
            }
        });
    }

    String path;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.album_btn:
                getImageLocation(GetSimplePhotoHelper.FROM_ALBUM, new GetSimplePhotoHelper.OnSelectedPhotoListener() {
                    @Override
                    public void onSelectedPhoto(int way, SimplePhoto photo) {
                        path = photo.uri.getPath();
                        Log.d("TAG", "photo=" + path);
                        mPhotoIv.setImageBitmap(BitmapUtil.decodeSampledBitmapFromFile(path, 300, 300));
                    }
                });
                break;
            case R.id.commit_btn:
                upLoadImage(path);
                break;
        }
    }

    private void getImageLocation(int photoFrom, GetSimplePhotoHelper.OnSelectedPhotoListener onSelectedPhotoListener) {
        GetSimplePhotoHelper photoHelper = GetSimplePhotoHelper.getInstance(this);
        photoHelper.choicePhoto(photoFrom, null, onSelectedPhotoListener);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
