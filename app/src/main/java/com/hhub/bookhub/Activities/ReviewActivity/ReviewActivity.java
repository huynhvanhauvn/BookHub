package com.hhub.bookhub.Activities.ReviewActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hhub.bookhub.Activities.ReviewDetailActivity.ReviewDetailActivity;
import com.hhub.bookhub.Activities.WelcomeActivity.WelcomeActivity;
import com.hhub.bookhub.Models.Book;
import com.hhub.bookhub.Models.Review;
import com.hhub.bookhub.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ReviewActivity extends AppCompatActivity implements ReviewView {

    private ConstraintLayout layout, layoutIntro, layoutArt, layoutFeel;
    private ReviewPresenter presenter;
    private TextView txtTitle, txtIntro, txtStory, txtCharacter, txtArt, txtFeel, txtMsg, txtEnd;
    private ImageButton btnIntro, btnArt, btnFeel,
            btnImgStory, btnImgCharacter, btnImgArt, btnImgFeel,btnImgMsg;
    private EditText edtIntro, edtStory, edtCharacter, edtArt, edtFeel, edtMsg, edtEnd;
    private static final int CODE_STORY = 1, CODE_CHARACTER = 2, CODE_ART = 3, CODE_FEEL = 4, CODE_MSG = 5;
    private String storyPath="", characterPath="", artPath="", feelPath="", msgPath="";
    private String storyFile="",characterFile="",artFile="",feelFile="",msgFile="";
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 900;
    private CardView btnSend, btnUpdate;
    private int numUploadImg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getSupportActionBar().hide();

        init();

        presenter = new ReviewPresenter(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        final String idReview = intent.getStringExtra(ReviewDetailActivity.ID);
        String title = intent.getStringExtra(ReviewDetailActivity.TITLE);
        String poster = intent.getStringExtra(ReviewDetailActivity.POSTER);
        if(idReview != null) {
            txtTitle.setText(title);
            Glide.with(getApplicationContext()).load(poster).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    layout.setBackground(resource);
                }
            });
            presenter.getReview(idReview);
            btnUpdate.setVisibility(View.VISIBLE);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!idReview.equals("") &&
                            edtStory != null && !edtStory.getText().toString().equals("") &&
                            edtCharacter != null && !edtCharacter.getText().toString().equals("") &&
                            edtMsg!= null && !edtMsg.getText().toString().equals("") &&
                            edtEnd != null && !edtEnd.getText().toString().equals("")) {
                        presenter.updateReview(idReview,
                                edtIntro != null ? edtIntro.getText().toString() : "",
                                edtStory.getText().toString(),
                                edtCharacter.getText().toString(),
                                edtArt != null ? edtArt.getText().toString() : "",
                                edtFeel != null ? edtFeel.getText().toString() : "",
                                edtMsg.getText().toString(), edtEnd.getText().toString());
                    }
                }
            });
        }
        if(id != null) {
            presenter.showBookInfo(id);
        }

        toggleLayout();

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        } else {}

        pickImage();
    }

    @Override
    public void showBookInfo(final Book book) {
        Glide.with(getApplicationContext()).load(book.getCover()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }
        });
        txtTitle.setText(book.getTitle());
        btnSend.setVisibility(View.VISIBLE);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getApplicationContext().getSharedPreferences(WelcomeActivity.SHARED_DATA,
                        Context.MODE_PRIVATE);
                final String idUser = preferences.getString(WelcomeActivity.USER_ID,"");
                if(!idUser.equals("") && book.getId() != null && !book.getId().equals("") &&
                        edtStory != null && !edtStory.getText().toString().equals("") &&
                        edtCharacter != null && !edtCharacter.getText().toString().equals("") &&
                        edtMsg!= null && !edtMsg.getText().toString().equals("") &&
                        edtEnd != null && !edtEnd.getText().toString().equals("")) {
                    presenter.sendReview(idUser, book.getId(),
                            edtIntro != null ? edtIntro.getText().toString() : "",
                            edtStory.getText().toString(),
                            edtCharacter.getText().toString(),
                            edtArt != null ? edtArt.getText().toString() : "",
                            edtFeel != null ? edtFeel.getText().toString() : "",
                            edtMsg.getText().toString(), edtEnd.getText().toString());
                }
            }
        });
    }

    @Override
    public void showReview(Review review) {
        edtIntro.setText(review.getIntroduction());
        edtIntro.setSelection(edtIntro.getText().length());
        edtStory.setText(review.getStory());
        edtStory.setSelection(edtStory.getText().length());
        if(review.getStoryImage()!=null && !review.getStoryImage().equals("")){
            Glide.with(getApplicationContext()).load(review.getStoryImage()).centerCrop().into(btnImgStory);
            storyFile = review.getStoryImage().replace("http://huynhvanhaua.000webhostapp.com/movieimage/","");
        }
        edtCharacter.setText(review.getCharacter());
        edtCharacter.setSelection(edtCharacter.getText().length());
        if(review.getCharacterImage()!=null && !review.getCharacterImage().equals("")){
            Glide.with(getApplicationContext()).load(review.getCharacterImage()).centerCrop().into(btnImgCharacter);
            characterFile = review.getCharacterImage().replace("http://huynhvanhaua.000webhostapp.com/movieimage/","");
        }
        edtArt.setText(review.getArt());
        edtArt.setSelection(edtArt.getText().length());
        if(review.getArtImage()!=null && !review.getArtImage().equals("")){
            Glide.with(getApplicationContext()).load(review.getArtImage()).centerCrop().into(btnImgArt);
            artFile = review.getArtImage().replace("http://huynhvanhaua.000webhostapp.com/movieimage/","");
        }
        edtFeel.setText(review.getFeel());
        edtFeel.setSelection(edtFeel.getText().length());
        if(review.getFeelImage()!=null && !review.getFeelImage().equals("")){
            Glide.with(getApplicationContext()).load(review.getFeelImage()).centerCrop().into(btnImgFeel);
            feelFile = review.getFeelImage().replace("http://huynhvanhaua.000webhostapp.com/movieimage/","");
        }
        edtMsg.setText(review.getMessage());
        edtMsg.setSelection(edtMsg.getText().length());
        if(review.getMessageImage()!=null && !review.getMessageImage().equals("")){
            Glide.with(getApplicationContext()).load(review.getMessageImage()).centerCrop().into(btnImgMsg);
            msgFile = review.getMessageImage().replace("http://huynhvanhaua.000webhostapp.com/movieimage/","");
        }
        edtEnd.setText(review.getEnd());
        edtEnd.setSelection(edtEnd.getText().length());
    }

    @Override
    public void updateReviewTextSuccess(String idReview) {
        numUploadImg = 0;
        if(!storyPath.equals("")) {
            numUploadImg++;
            if(storyPath.equals("")) {
                pushImage(storyPath, idReview, "storyImage", false,"");
            } else {
                pushImage(storyPath, idReview, "storyImage", true, storyFile);
            }
        }
        if(!characterPath.equals("")) {
            numUploadImg++;
            if(characterFile.equals("")){
                pushImage(characterPath, idReview, "actingImage", false,"");
            } else {
                pushImage(characterPath, idReview, "storyImage", true, characterFile);
            }
        }
        if(!artPath.equals("")) {
            numUploadImg++;
            if(artFile.equals("")) {
                pushImage(artPath, idReview, "pictureImage", false,"");
            } else {
                pushImage(artPath, idReview, "pictureImage", true, artFile);
            }
        }
        if(!feelPath.equals("")) {
            numUploadImg++;
            if(feelFile.equals("")) {
                pushImage(feelPath, idReview, "feelImage", false,"");
            } else {
                pushImage(feelPath, idReview, "feelImage", true, feelFile);
            }
        }
        if(!msgPath.equals("")) {
            numUploadImg++;
            if(msgFile.equals("")) {
                pushImage(msgPath, idReview, "messageImage", false,"");
            } else {
                pushImage(msgPath, idReview, "messageImage", true, msgFile);
            }
        }
        if(numUploadImg<=0) {
            finish();
        }
    }

    @Override
    public void sendReviewSuccess(String idReview) {
        numUploadImg = 0;
        if(!storyPath.equals("")) {
            numUploadImg++;
            pushImage(storyPath, idReview, "storyImage", false,"");
        }
        if(!characterPath.equals("")) {
            numUploadImg++;
            pushImage(characterPath, idReview, "actingImage", false,"");
        }
        if(!artPath.equals("")) {
            numUploadImg++;
            pushImage(artPath, idReview, "pictureImage", false,"");
        }
        if(!feelPath.equals("")) {
            numUploadImg++;
            pushImage(feelPath, idReview, "feelImage", false,"");
        }
        if(!msgPath.equals("")) {
            numUploadImg++;
            pushImage(msgPath, idReview, "messageImage", false,"");
        }
        if(numUploadImg<=0) {
            finish();
        }
    }

    @Override
    public void uploadImageSuccess() {
        numUploadImg--;
        if(numUploadImg<=0) {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_STORY && data != null && data.getData() != null) {
            Uri uri = data.getData();
            storyPath = new File(getRealPathFromURI(uri)).getAbsolutePath();
            // Load image
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                btnImgStory.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == CODE_CHARACTER && data != null && data.getData() != null) {
            Uri uri = data.getData();
            characterPath = new File(getRealPathFromURI(uri)).getAbsolutePath();
            // Load image
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                btnImgCharacter.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == CODE_ART && data != null && data.getData() != null) {
            Uri uri = data.getData();
            artPath = new File(getRealPathFromURI(uri)).getAbsolutePath();
            // Load image
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                btnImgArt.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == CODE_FEEL && data != null && data.getData() != null) {
            Uri uri = data.getData();
            feelPath = new File(getRealPathFromURI(uri)).getAbsolutePath();
            // Load image
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                btnImgFeel.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == CODE_MSG && data != null && data.getData() != null) {
            Uri uri = data.getData();
            msgPath = new File(getRealPathFromURI(uri)).getAbsolutePath();
            // Load image
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                btnImgMsg.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    public void pushImage(String path, String id, String type, boolean isUpdate, String oldFile) {
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);
        if(isUpdate) {
            presenter.updateImage(body,id,type,oldFile);
        } else {
            presenter.uploadImage(body,id,type);
        }
    }

    private void pickImage() {
        btnImgStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, CODE_STORY);
            }
        });
        btnImgCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, CODE_CHARACTER);
            }
        });
        btnImgArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, CODE_ART);
            }
        });
        btnImgFeel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, CODE_FEEL);
            }
        });
        btnImgMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, CODE_MSG);
            }
        });
    }

    private void toggleLayout() {
        btnIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIntro.setVisibility(View.GONE);
                layoutIntro.setVisibility(View.GONE);
            }
        });
        txtIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIntro.setVisibility(View.VISIBLE);
                layoutIntro.setVisibility(View.VISIBLE);
            }
        });

        btnArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnArt.setVisibility(View.GONE);
                btnImgArt.setVisibility(View.GONE);
                layoutArt.setVisibility(View.GONE);
            }
        });
        txtArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnArt.setVisibility(View.VISIBLE);
                btnImgArt.setVisibility(View.VISIBLE);
                layoutArt.setVisibility(View.VISIBLE);
            }
        });

        btnFeel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFeel.setVisibility(View.GONE);
                btnImgFeel.setVisibility(View.GONE);
                layoutFeel.setVisibility(View.GONE);
            }
        });
        txtFeel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFeel.setVisibility(View.VISIBLE);
                btnImgFeel.setVisibility(View.VISIBLE);
                layoutFeel.setVisibility(View.VISIBLE);
            }
        });
    }

    private void init() {
        layout = (ConstraintLayout) findViewById(R.id.review_layout);
        layoutIntro = (ConstraintLayout) findViewById(R.id.review_layout_intro);
        layoutArt = (ConstraintLayout) findViewById(R.id.review_layout_art);
        layoutFeel = (ConstraintLayout) findViewById(R.id.review_layout_feel);
        txtTitle = (TextView) findViewById(R.id.review_txt_title);
        txtIntro = (TextView) findViewById(R.id.review_txt_intro);
        txtStory = (TextView) findViewById(R.id.review_txt_story);
        txtCharacter = (TextView) findViewById(R.id.review_txt_character);
        txtArt = (TextView) findViewById(R.id.review_txt_art);
        txtFeel = (TextView) findViewById(R.id.review_txt_feel);
        txtMsg = (TextView) findViewById(R.id.review_txt_msg);
        txtEnd = (TextView) findViewById(R.id.review_txt_end);
        btnIntro = (ImageButton) findViewById(R.id.review_btn_close_intro);
        btnArt = (ImageButton) findViewById(R.id.review_btn_close_art);
        btnFeel = (ImageButton) findViewById(R.id.review_btn_close_feel);
        btnImgStory = (ImageButton) findViewById(R.id.review_btn_image_story);
        btnImgCharacter = (ImageButton) findViewById(R.id.review_btn_image_character);
        btnImgArt = (ImageButton) findViewById(R.id.review_btn_image_art);
        btnImgFeel = (ImageButton) findViewById(R.id.review_btn_image_feel);
        btnImgMsg = (ImageButton) findViewById(R.id.review_btn_image_msg);
        edtIntro = (EditText) findViewById(R.id.review_edt_intro);
        edtStory = (EditText) findViewById(R.id.review_edt_story);
        edtCharacter = (EditText) findViewById(R.id.review_edt_character);
        edtArt = (EditText) findViewById(R.id.review_edt_art);
        edtFeel = (EditText) findViewById(R.id.review_edt_feel);
        edtMsg = (EditText) findViewById(R.id.review_edt_msg);
        edtEnd = (EditText) findViewById(R.id.review_edt_end);
        btnSend = (CardView) findViewById(R.id.review_card_send);
        btnUpdate = (CardView) findViewById(R.id.review_card_update);
    }
}
