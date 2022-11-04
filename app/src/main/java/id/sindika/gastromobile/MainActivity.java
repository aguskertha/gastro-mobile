package id.sindika.gastromobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;

import id.sindika.gastromobile.API.APIConfig;
import id.sindika.gastromobile.Listeners.PredictListener;
import id.sindika.gastromobile.Models.Food;
import id.sindika.gastromobile.Models.Request.PredictDTO;
import id.sindika.gastromobile.Repositories.FoodRepository;
import id.sindika.gastromobile.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements PredictListener {

    ActivityMainBinding binding;
    public final int CAMERA_PERM_CODE = 101;
    public final int GALLERY_PERM_CODE = 102;
    private final int REQUEST_OPEN_GALLERY = 201;
    private final int REQUEST_OPEN_CAMERA = 202;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

    }

    private void selectImage(){
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Tambahkan foto");
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
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_PERM_CODE);
        }
        else{
            openGallery();
        }
    }
    private void askPermissionCamera(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
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
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        showMessageOKCancel("You need to allow access permissions",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            ActivityCompat.requestPermissions(getParent(), new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
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
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_OPEN_GALLERY){
            Uri uri = data.getData();
            Glide.with(binding.imgFoodActual.getContext())
                    .load(uri)
                    .into(binding.imgFoodActual);
            try {
                String base64 = convertToBase64(uri);
                PredictDTO predictDTO = new PredictDTO(base64);
                FoodRepository.predictImage(binding.getRoot(), predictDTO, this::onPredictImage);
            } catch (FileNotFoundException e) {
                Toast.makeText(binding.getRoot().getContext(), "File not found!", Toast.LENGTH_SHORT);
            }
        }
        else if(requestCode==REQUEST_OPEN_CAMERA){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "Title", null);
            Uri uri = Uri.parse(path);
            Glide.with(binding.imgFoodActual.getContext())
                    .load(uri)
                    .into(binding.imgFoodActual);

            try {
                String base64 = convertToBase64(uri);
                PredictDTO predictDTO = new PredictDTO(base64);
                FoodRepository.predictImage(binding.getRoot(), predictDTO, this::onPredictImage);
            } catch (FileNotFoundException e) {
                Toast.makeText(binding.getRoot().getContext(), "File not found!", Toast.LENGTH_SHORT);
            }

        }

    }

    private String convertToBase64(Uri uri) throws FileNotFoundException {
        final InputStream imageStream;
        imageStream = getContentResolver().openInputStream(uri);
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
        float predict =(Float.parseFloat(food.getBase64())*100);
        binding.tvFoodName.setText(food.getName()+" - "+new DecimalFormat("##.##").format(predict)+"%");
        Glide.with(binding.imgFoodPredict.getContext())
                .load(APIConfig.BASE_IMAGE_URL+food.getPicture() )
                .into(binding.imgFoodPredict);
        Toast.makeText(binding.getRoot().getContext(), food.getName(), Toast.LENGTH_SHORT);
    }
}