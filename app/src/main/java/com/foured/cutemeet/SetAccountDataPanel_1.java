package com.foured.cutemeet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.foured.cutemeet.models.UserAccountData;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetAccountDataPanel_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetAccountDataPanel_1 extends Fragment {

    ImageView imageView, avatarPlaceImage;
    private float dX, dY;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView logText;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SetAccountDataPanel_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetAccountDataPanel_1.
     */
    // TODO: Rename and change types and number of parameters
    public static SetAccountDataPanel_1 newInstance(String param1, String param2) {
        SetAccountDataPanel_1 fragment = new SetAccountDataPanel_1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_account_data_panel_1, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        avatarPlaceImage = view.findViewById(R.id.setAccountDataPanel_1_avatarPlaceView);
        imageView = view.findViewById(R.id.setAccountDataPanel_1_avatarImageView);
        logText = view.findViewById(R.id.setAccountDataPanel_1_logText);
        logText.setText("");

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        v.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        break;
                }
                return true;
            }
        });


        view.findViewById(R.id.setAccountDataPanel_1_pickImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });

        view.findViewById(R.id.setAccountDataPanel_1_nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UserAccountData uad = new UserAccountData();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap bitmap = getCroppedImageBitmap();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    uad.photoData = stream.toByteArray();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user_account_data_2", uad);

                    Navigation.findNavController(view).navigate(R.id.action_setAccountDataPanel_1_to_setAccountDataPanel_2, bundle);
                }
                catch (Exception e){
                    logText.setText("Выберите фото!!!");
                }
            }
        });
    }


    private void showImagePickerDialog() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);
        }
    }

    private Bitmap getCroppedImageBitmap() {
        Drawable drawable = imageView.getDrawable();
        Bitmap croppedBitmap = null;
        if (drawable instanceof BitmapDrawable) {
            Bitmap originalBitmap = ((BitmapDrawable) drawable).getBitmap();

            int dp150 = (int) (150 * getResources().getDisplayMetrics().density);

            float X = imageView.getX();
            float Y = imageView.getY();

            float DX = avatarPlaceImage.getX() - X;
            float DY = avatarPlaceImage.getY() - Y;

            System.out.println(DX + " " + DY);

            if (DX < 0) DX = 0;
            if (DY < 0) DY = 0;
            if (DX + dp150 > originalBitmap.getWidth()) DX = originalBitmap.getWidth() - dp150;
            if (DY + dp150 > originalBitmap.getHeight()) DY = originalBitmap.getHeight() - dp150;

            croppedBitmap = Bitmap.createBitmap(originalBitmap, (int) DX, (int) DY, dp150, dp150);
        }
        return croppedBitmap;
    }

    private void saveBitmapToFile(Bitmap bitmap) {
        String fileName = "edited_image_" + System.currentTimeMillis() + ".png";
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(directory, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            Toast.makeText(getContext(), "Image saved to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }

}