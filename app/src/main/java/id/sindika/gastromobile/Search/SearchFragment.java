package id.sindika.gastromobile.Search;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import id.sindika.gastromobile.Food.FoodRepository;
import id.sindika.gastromobile.MainActivity;
import id.sindika.gastromobile.Models.Food;
import id.sindika.gastromobile.Models.Request.PredictDTO;
import id.sindika.gastromobile.R;
import id.sindika.gastromobile.ResultPredict.ResultPredictActivity;
import id.sindika.gastromobile.Utils.StorageConstStatus;
import id.sindika.gastromobile.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment implements PredictListener {

    public final int CAMERA_PERM_CODE = 101;
    public final int GALLERY_PERM_CODE = 102;
    private final int REQUEST_OPEN_GALLERY = 201;
    private final int REQUEST_OPEN_CAMERA = 202;

    private AppCompatActivity activity;
    private FragmentSearchBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = ((AppCompatActivity)getActivity());
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        binding.imgBtnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermissionGallery();
            }
        });

        binding.imgBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermissionCamera();
            }
        });

        return view;
    }

    private void selectImage(){
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Picture!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    askPermissionCamera();
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    dialog.dismiss();
                    askPermissionGallery();
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void askPermissionGallery(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_PERM_CODE);
        }
        else{
            openGallery();
        }
    }
    private void askPermissionCamera(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }
        else{
            openCamera();
        }
    }
    private void openGallery(){
        Intent intent = new  Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_OPEN_GALLERY);
    }
    private void openCamera(){
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, REQUEST_OPEN_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PERM_CODE){
            if(grantResults.length<0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        showMessageOKCancel("You need to allow access permissions",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            ActivityCompat.requestPermissions(activity.getParent(), new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
                                        }
                                    }
                                });
                    }
                }
            }
        }
        if(requestCode==GALLERY_PERM_CODE){
            if(grantResults.length<0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_OPEN_GALLERY){
            try {
                if(data != null){
                    Uri uri = data.getData();
                    String base64 = convertToBase64(uri);
                    PredictDTO predictDTO = new PredictDTO(base64);
                    PredictRepository.predictImage(binding.getRoot(), predictDTO, this::onPredictImage);
                } else {
                    movePreviousPage();
                }


            } catch (FileNotFoundException e) {
                Toast.makeText(getContext(), "File not found!", Toast.LENGTH_SHORT);
            }
        }
        else if(requestCode==REQUEST_OPEN_CAMERA){

            try {
                if(data != null){
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                    String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "Title", null);
                    Uri uri = Uri.parse(path);
                    String base64 = convertToBase64(uri);
                    PredictDTO predictDTO = new PredictDTO(base64);
                    FoodRepository.predictImage(binding.getRoot(), predictDTO, this::onPredictImage);

                } else {
                    movePreviousPage();
                }
            } catch (FileNotFoundException e) {
                Toast.makeText(getContext(), "File not found!", Toast.LENGTH_SHORT);
            }

        }

    }

    private String convertToBase64(Uri uri) throws FileNotFoundException {
        final InputStream imageStream;
        imageStream = getContext().getContentResolver().openInputStream(uri);
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        String encodedImage = encodeImage(selectedImage);
        return encodedImage;
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    @Override
    public void onPredictImage(Food food) {
        ActivityOptions options = ActivityOptions.makeCustomAnimation(binding.getRoot().getContext(), R.anim.slide_in_left, R.anim.slide_out_right);
        Intent intent = new Intent(getContext(), ResultPredictActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(StorageConstStatus.EXTRA_FOOD, food);
        intent.putExtras(bundle);
        startActivity(intent, options.toBundle());
    }

    private void movePreviousPage(){
        ActivityOptions options = ActivityOptions.makeCustomAnimation(binding.getRoot().getContext(), R.anim.slide_in_left, R.anim.slide_out_right);
        Intent intent = new Intent(binding.getRoot().getContext(), MainActivity.class);
        intent.putExtra(StorageConstStatus.EXTRA_NAVIGATION, StorageConstStatus.EXTRA_SEARCH_FRAGMENT);
        startActivity(intent, options.toBundle());
    }

}