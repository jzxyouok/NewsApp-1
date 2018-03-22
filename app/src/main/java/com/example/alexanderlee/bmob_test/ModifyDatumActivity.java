package com.example.alexanderlee.bmob_test;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetCallback;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class ModifyDatumActivity extends AppCompatActivity {

    @BindView(R.id.btn_feedback_modify)
    ImageButton btnFeedbackModify;
    @BindView(R.id.user_modify)
    LinearLayout userModify;
    @BindView(R.id.username_modify)
    LinearLayout usernameModify;
    @BindView(R.id.intro_modify)
    LinearLayout introModify;
    @BindView(R.id.sex_modify)
    LinearLayout sexModify;
    @BindView(R.id.birthday_modify)
    LinearLayout birthdayModify;
    @BindView(R.id.place_modify)
    LinearLayout placeModify;
    @BindView(R.id.circle_iamgeview)
    CircleImageView circleIamgeview;
    @BindView(R.id.tv_username_modify)
    TextView tvUsernameModify;
    @BindView(R.id.tv_intro_modify)
    TextView tvIntroModify;
    @BindView(R.id.tv_sex_modify)
    TextView tvSexModify;
    @BindView(R.id.tv_birthday_modify)
    TextView tvBirthdayModify;
    @BindView(R.id.tv_place_modify)
    TextView tvPlaceModify;
    private String AppID = "c29260b3d28f78d157e89fb6625a451c";
    private BottomSheetBehavior behavior;
    BottomSheetDialog mbottomSheetDialog;
    BottomSheetDialog mbottomSheetDialogusername;
    BottomSheetDialog mbottomSheetDialogsex;
    BottomSheetDialog mbottomSheetDialogintronduction;
    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int FROM_PIC = 2;
    final int maxNum = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fresco.initialize(this);
        setContentView(R.layout.activity_modify_datum);
        ButterKnife.bind(this);
        Bmob.initialize(this, AppID);
        init();
    }

    private void init() {
        MyUser myuser = BmobUser.getCurrentUser(this, MyUser.class);
        if (myuser.getImgview() != null) {
            String imagePath = myuser.getImgview().getUrl();
            Glide.with(this).load(imagePath).into(circleIamgeview);
        } else {
            circleIamgeview.setImageResource(R.drawable.bird3);
//            Glide.with(this).load("http://bmob-cdn-14975.b0.upaiyun.com/2017/11/19/d59c26d40a0d4a63b88358d8b2fb0349.jpg").into(circleIamgeview);
        }
        tvUsernameModify.setText(myuser.getUsername());
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putString("userName", myuser.getUsername());
        editor.apply();
        tvIntroModify.setText(myuser.getIntroduction());
        SharedPreferences.Editor editor1 = getSharedPreferences("user", MODE_PRIVATE).edit();
        editor1.putString("userIntro", myuser.getIntroduction());
        editor1.apply();
        if (myuser.getGender() == true) {
            tvSexModify.setText("男");
        } else {
            tvSexModify.setText("女");
        }
    }


    @OnClick({R.id.btn_feedback_modify, R.id.user_modify, R.id.username_modify, R.id.intro_modify, R.id.sex_modify, R.id.birthday_modify, R.id.place_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_feedback_modify:
                Intent intent = new Intent(ModifyDatumActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.user_modify:
                userModify();
                break;
            case R.id.username_modify:
                usernameModify();
                break;
            case R.id.intro_modify:
                introModify();
                break;
            case R.id.sex_modify:
                sexModify();
                break;
            case R.id.birthday_modify:
                birthdayModify();
                break;
            case R.id.place_modify:
                placeModify();
                break;
        }
    }

    private void placeModify() {
    }

    private void birthdayModify() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Toast.makeText(ModifyDatumActivity.this, "设置的时间：" + i + "年" + (i1 + 1) + "月" + i2 + "日", Toast.LENGTH_SHORT).show();
            }
        };
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_LIGHT, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void introModify() {
        mbottomSheetDialogintronduction = new BottomSheetDialog(this, R.style.BottomDialog);
        View dialogView = LayoutInflater.from(ModifyDatumActivity.this).inflate(R.layout.fragment_modify_introduction, null);
        mbottomSheetDialogintronduction.setContentView(dialogView);
        Window window = mbottomSheetDialogintronduction.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = getWindowManager().getDefaultDisplay().getWidth();
//        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        mbottomSheetDialogintronduction.onWindowAttributesChanged(wl);
        final EditText etxIntro = (EditText) mbottomSheetDialogintronduction.findViewById(R.id.etx_fgmodify_intro);
        final TextView tvIntro = (TextView) mbottomSheetDialogintronduction.findViewById(R.id.tv_fgmodify_intro);
        etxIntro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvIntro.setText("20");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                etxIntro.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxNum)});
                tvIntro.setText(String.valueOf(maxNum - editable.length()));
            }
        });
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        final String userIntro = preferences.getString("userIntro", "");
        etxIntro.setText(userIntro);
        etxIntro.setSelection(etxIntro.getText().length());
        mbottomSheetDialogintronduction.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etxIntroduction = etxIntro.getText().toString();
                modifyIntro(etxIntroduction);
//                SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
//                editor.putString("userIntro", etxIntroduction);
//                editor.apply();
                mbottomSheetDialogintronduction.dismiss();
            }
        });
        mbottomSheetDialogintronduction.show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                showKeyboard(etxIntro);
            }
        }, 300);
    }


    private void modifyIntro(final String etxIntronduction) {
        MyUser myuser = new MyUser();
        myuser.setIntroduction(etxIntronduction);
        MyUser cur = BmobUser.getCurrentUser(ModifyDatumActivity.this, MyUser.class);
        myuser.update(this, cur.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.i("ModifyDatumActivity", "介绍更改成功");
                tvIntroModify.setText(etxIntronduction);
                SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                editor.putString("userIntro", etxIntronduction);
                editor.apply();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("ModifyDatumActivity", "介绍更改失败");
            }
        });
    }


    private void sexModify() {
        mbottomSheetDialogsex = new BottomSheetDialog(this, R.style.BottomDialog);
        View dialogView = LayoutInflater.from(ModifyDatumActivity.this).inflate(R.layout.fragment_modify_sex, null);
        mbottomSheetDialogsex.setContentView(dialogView);
        Window window = mbottomSheetDialogsex.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = getWindowManager().getDefaultDisplay().getWidth();
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        mbottomSheetDialogsex.onWindowAttributesChanged(wl);
        mbottomSheetDialogsex.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbottomSheetDialogsex.dismiss();
            }
        });
        mbottomSheetDialogsex.findViewById(R.id.male).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male();
                mbottomSheetDialogsex.dismiss();
            }
        });
        mbottomSheetDialogsex.findViewById(R.id.female).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                female();
                mbottomSheetDialogsex.dismiss();
            }
        });
        mbottomSheetDialogsex.show();

    }

    private void male() {
        MyUser myuser = new MyUser();
        myuser.setGender(true);
        MyUser cur = BmobUser.getCurrentUser(ModifyDatumActivity.this, MyUser.class);
        myuser.update(this, cur.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.i("ModifyDatumActivity", "男");
                tvSexModify.setText("男");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("ModifyDatumActivity", "男失败");
            }
        });
    }

    private void female() {
        MyUser myuser = new MyUser();
        myuser.setGender(false);
        MyUser cur = BmobUser.getCurrentUser(ModifyDatumActivity.this, MyUser.class);
        myuser.update(this, cur.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.i("ModifyDatumActivity", "女");
                tvSexModify.setText("女");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("ModifyDatumActivity", "女失败");
            }
        });
    }

    private void usernameModify() {
        mbottomSheetDialogusername = new BottomSheetDialog(this, R.style.BottomDialog);
        View dialogView = LayoutInflater.from(ModifyDatumActivity.this).inflate(R.layout.fragment_modify_username, null);
        mbottomSheetDialogusername.setContentView(dialogView);
        Window window = mbottomSheetDialogusername.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = getWindowManager().getDefaultDisplay().getWidth();
//        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        mbottomSheetDialogusername.onWindowAttributesChanged(wl);
        final EditText etxUsername = (EditText) mbottomSheetDialogusername.findViewById(R.id.etx_fgmodify_username);
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        String userName = preferences.getString("userName", "");
        etxUsername.setText(userName);
        etxUsername.setSelection(etxUsername.getText().length());
        mbottomSheetDialogusername.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etxUsername.getText().toString();
                modifyusername(username);
                mbottomSheetDialogusername.dismiss();
            }
        });
        mbottomSheetDialogusername.show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                showKeyboard(etxUsername);
            }
        }, 300);
    }


    public void showKeyboard(EditText editText) {
        if (editText != null) {
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) editText
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }
    }

    private void modifyusername(final String username) {
        MyUser myuser = new MyUser();
        myuser.setUsername(username);
        MyUser cur = BmobUser.getCurrentUser(ModifyDatumActivity.this, MyUser.class);
        myuser.update(this, cur.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.i("ModifyDatumActivity", "用户名更改成功");
                tvUsernameModify.setText(username);
                SharedPreferences.Editor editor = getSharedPreferences("data2", MODE_PRIVATE).edit();
                editor.putString("username", username);
                editor.commit();
                SharedPreferences.Editor editor2 = getSharedPreferences("user", MODE_PRIVATE).edit();
                editor2.putString("userName", username);
                editor2.apply();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("ModifyDatumActivity", "用户名更改失败");
            }
        });
    }

    private void userModify() {
        mbottomSheetDialog = new BottomSheetDialog(this, R.style.BottomDialog);
        View dialogView = LayoutInflater.from(ModifyDatumActivity.this).inflate(R.layout.fragment_modify_user, null);
        mbottomSheetDialog.setContentView(dialogView);
        Window window = mbottomSheetDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = getWindowManager().getDefaultDisplay().getWidth();
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        mbottomSheetDialog.onWindowAttributesChanged(wl);
        mbottomSheetDialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbottomSheetDialog.dismiss();
            }
        });
        mbottomSheetDialog.findViewById(R.id.take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
                mbottomSheetDialog.dismiss();
            }
        });
        mbottomSheetDialog.findViewById(R.id.from_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromPic();
                mbottomSheetDialog.dismiss();
            }
        });
        mbottomSheetDialog.show();
    }

    private void fromPic() {
        if (ContextCompat.checkSelfPermission(ModifyDatumActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ModifyDatumActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, FROM_PIC);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch ((requestCode)) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "you denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void takePhoto() {
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(ModifyDatumActivity.this, "com.example.alexanderlee.bmob_test.fileprovider", outputImage);

        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
//                    startPhotoZoom(Uri.fromFile(mFile));
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        circleIamgeview.setImageBitmap(bitmap);
//                        SharedPreferences.Editor editor =getSharedPreferences("data2", MODE_PRIVATE).edit();
//                        editor.putString("imagePath", String.valueOf(imageUri));
//                        editor.commit();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case FROM_PIC:
                if (requestCode == FROM_PIC) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        upLoad(imagePath);
        displayImage(imagePath);
    }


    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        if (data != null) {
            Uri uri = data.getData();
            if (DocumentsContract.isDocumentUri(this, uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                imagePath = uri.getPath();
            }
            upLoad(imagePath);
            displayImage(imagePath);
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            SharedPreferences.Editor editor = getSharedPreferences("userimage", MODE_PRIVATE).edit();
            editor.putString("userImageUrl", imagePath);
            editor.commit();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            circleIamgeview.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }


    }

    private void upLoad(final String imagePath) {
        final BmobFile imgview = new BmobFile(new File(imagePath));
        imgview.upload(this, new UploadFileListener() {
            @Override
            public void onSuccess() {
                Log.i("ModifyDatumActivity", "上传成功2");
                MyUser myuser = new MyUser();
                myuser.setImgview(imgview);
                MyUser cur = BmobUser.getCurrentUser(ModifyDatumActivity.this, MyUser.class);
                myuser.update(ModifyDatumActivity.this, cur.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Log.i("ModifyDatumActivity", "上传成功");
//                        SharedPreferences.Editor editor =getSharedPreferences("data2", MODE_PRIVATE).edit();
//                        editor.putString("imagePath",imagePath);
//                        editor.commit();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.i("ModifyDatumActivity", "上传失败");
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("ModifyDatumActivity", "上传失败2");
                Toast.makeText(ModifyDatumActivity.this, "头像上传失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ModifyDatumActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ModifyDatumActivity", "onPause");
    }

}























