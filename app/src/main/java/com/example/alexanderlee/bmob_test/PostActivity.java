package com.example.alexanderlee.bmob_test;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
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
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

public class PostActivity extends AppCompatActivity {

    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    //    @BindView(R.id.send_btn)
//    Button sendBtn;
    @BindView(R.id.etx_share)
    EditText etxShare;
    @BindView(R.id.gridview)
    GridView gridview;
    private GridViewAdater gridViewAdater;
    private List<Map<String, Object>> datas;
    private Dialog dialog;
    private File tempFile;
    BottomSheetDialog mbottomSheetDialog;
    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int FROM_PIC = 2;
    final int maxNum = 20;
    String imagePath = null;
    List<BmobObject> images = new ArrayList<BmobObject>();
    List<String> imagepath = new ArrayList<>();
    //    String[] imagepaths = new String[9];
//    int i=0;
    private static final String TAG = "MainActivty2222";
    public static Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        datas = new ArrayList<>();
        gridViewAdater = new GridViewAdater(datas, this);
        gridview.setAdapter(gridViewAdater);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fromAlbumOrTakephoto();
            }
        });
        sendBtn = (Button) findViewById(R.id.send_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Post post = new Post();
                for (Map<String, Object> map1 : datas) {
                    for (String k : map1.keySet()) {
                        imagepath.add(map1.get(k).toString());
                    }
                }

                final String[] imagepaths = imagepath.toArray(new String[imagepath.size()]);
                final String etxContent = etxShare.getText().toString();
                Log.i("Imagepath", "imagepaths.length" + imagepaths.length);
                BmobFile.uploadBatch(PostActivity.this, imagepaths, new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> list, List<String> list1) {
                        Log.i("bmob", "上传成功2");
                        if (list.size() == imagepaths.length) {
                            MyUser user = BmobUser.getCurrentUser(PostActivity.this, MyUser.class);
                            post.setContent(etxContent);
                            post.setAuthor(user);
                            post.setImages(list1);
                            post.save(PostActivity.this, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    Log.i("bmob", "保存成功");
                                    finish();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Log.i("bmob", "保存失败");
                                }
                            });
                        }
                    }

                    @Override
                    public void onProgress(int i, int i1, int i2, int i3) {

                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.i("bmob", "上传失败2");
                    }
                });
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private void fromAlbumOrTakephoto() {
        mbottomSheetDialog = new BottomSheetDialog(this, R.style.BottomDialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.fragment_modify_user, null);
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
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
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
            imageUri = FileProvider.getUriForFile(this, "com.example.alexanderlee.bmob_test.fileprovider", outputImage);

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
//            case TAKE_PHOTO:
//                if (resultCode == RESULT_OK) {
//                    try {
//                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//                break;
            case FROM_PIC:
                if (requestCode == FROM_PIC) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                    photoPath();
                }
                break;
            default:
                break;
        }
    }

    private void photoPath() {
        Map<String, Object> map = new HashMap<>();
        map.put("path", imagePath);
        datas.add(map);
        gridViewAdater.notifyDataSetChanged();
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        imagePath = getImagePath(uri, null);
//        upLoad(imagePath);
//        displayImage(imagePath);
    }


    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
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
//        upLoad(imagePath);
//        displayImage(imagePath);
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

//    private void displayImage(String imagePath) {
//        if (imagePath != null) {
////            SharedPreferences.Editor editor = getSharedPreferences("userimage", MODE_PRIVATE).edit();
////            editor.putString("userImageUrl", imagePath);
////            editor.commit();
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
////            circleIamgeview.setImageBitmap(bitmap);
//        } else {
//            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }

//    private void upLoad(String imagePath) {
//        final BmobFile imgview = new BmobFile(new File(imagePath));
//        imgview.upload(this, new UploadFileListener() {
//            @Override
//            public void onSuccess() {
//                Log.i("ModifyDatumActivity", "上传成功2");
//                MyUser myuser = new MyUser();
//                myuser.setImgview(imgview);
//                MyUser cur = BmobUser.getCurrentUser(PostActivity.this, MyUser.class);
//                myuser.update(PostActivity.this, cur.getObjectId(), new UpdateListener() {
//                    @Override
//                    public void onSuccess() {
//                        Log.i("ModifyDatumActivity", "上传成功");
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//                        Log.i("ModifyDatumActivity", "上传失败");
//                    }
//                });
//            }
//            @Override
//            public void onFailure(int i, String s) {
//                Log.i("ModifyDatumActivity", "上传失败2");
//            }
//        });
//    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (datas.size() != 0) {
            sendBtn.setClickable(true);
            sendBtn.setTextColor(getResources().getColor(R.color.colorDarkGreen));
        } else {
            sendBtn.setClickable(false);
            sendBtn.setTextColor(getResources().getColor(R.color.colorLightGreen));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onResatrt");
    }
}
