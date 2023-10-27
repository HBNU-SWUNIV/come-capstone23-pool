package com.example.capston_pj;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.capston_pj.models.Car;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.retrofitInterface.RetrofitFile;
import com.example.capston_pj.retrofitInterface.RetrofitMacInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterCarActivity extends AppCompatActivity {
    private Button add;
    private Button register;
    private Button opOk,check;
    private String imageFileName;
    private ImageView carProfile;
    EditText carNum,carMaker,carModel;
    private Dialog dia;
    private String makerResult="",typeResult="";
    private Spinner carType;
    private ActivityResultLauncher<Intent> imageLauncher;
    private Member member;

    boolean iCheck1=false,iCheck2=false,iCheck3=false,iCheck4=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_register);
        carNum = findViewById(R.id.regi_carNum);
        carMaker =findViewById(R.id.regi_carMaker);
        carModel=findViewById(R.id.regi_carModel);
        register=findViewById(R.id.regi_save);
        carType=findViewById(R.id.regi_type);
        dia = new Dialog(this);
        dia.setContentView(R.layout.dialog_car_maker);
        check = findViewById(R.id.check);
        add = findViewById(R.id.add_car_profile);
        carProfile = findViewById(R.id.car_set_profile);
        carMaker.setFocusable(false);
        carMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionDia();
            }
        });

        Intent intent = getIntent();
        member = (Member)intent.getSerializableExtra("member");
        Log.d("memberCheck","member:"+member.getId());

        imageFileName = member.getName()+"carImage";
        String imageUrl = "url"+member.getId();

        imageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    carProfile.setImageURI(uri);
                    saveProfile(uri , member.getId());
                }
            }
        });

       add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(RegisterCarActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // 권한이 없음
                    // ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                } else {
                    // 권한 있음
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    imageLauncher.launch(intent);
                }
            }
        });
        carType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeResult = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        carNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String message = carNum.getText().toString();
                if(message.length()==0){
                    iCheck1= false;
                    register.setEnabled(false);
                    register.setBackgroundResource(R.drawable.round_corner_gray);
                }else{
                    iCheck1 = true;
                    if(iCheck1==true&&iCheck2==true&&iCheck3==true){
                        register.setEnabled(true);
                        register.setBackgroundResource(R.drawable.round_corner_blue);
                    }

                }
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("check","check1:"+iCheck1+"check2:"+iCheck2+"check3:"+iCheck3);
            }
        });
        carModel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String message = carModel.getText().toString();
                if(message.length()==0){
                    iCheck3= false;
                    register.setEnabled(false);
                    register.setBackgroundResource(R.drawable.round_corner_gray);
                }else{
                    iCheck3 = true;
                    if(iCheck1==true&&iCheck2==true&&iCheck3==true){
                        register.setEnabled(true);
                        register.setBackgroundResource(R.drawable.round_corner_blue);
                    }

                }
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Car car = new Car(member.getId(),carNum.getText().toString(),makerResult,carModel.getText().toString(),typeResult,member.getId());
                saveCar(car);
            }
        });


    }
    private void saveCar(Car car){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("carinto","id:"+car.getId()+" num:"+car.getNum()+" maker:"+car.getMaker()+" model:"+car.getModel()+" type"+car.getType());
        RetrofitMacInterface service = retrofit.create(RetrofitMacInterface.class);
        Call<Car> call = service.saveCarInfo(car);
        call.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                if (response.isSuccessful()) {
                    Car data = response.body();
                   /* Intent intent = new Intent(RegisterCarActivity.this,MainActivity.class);
                    startActivity(intent);*/
                    Intent backIntent = new Intent(RegisterCarActivity.this,MainActivity.class);
                    member.setCar(member.getId());
                    backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    backIntent.putExtra("member",member);
                    backIntent.putExtra("info",true);
                    startActivity(backIntent);
                }
            }
            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }
    private void saveProfile(Uri uri, long id) {
        Log.d("이미지 전송 호출","1" +uri);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        String filePath = getRealPathFromURI(uri);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        String resizeFilePath= saveBitmapToJpeg(this,bitmap,imageFileName+"temp");
        File file = new File(resizeFilePath);

        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/jpeg"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("files", imageFileName, requestFile);
        Log.d("이미지 전송 호출","2" +requestFile);
        RetrofitFile appInterface = retrofit.create(RetrofitFile.class);
        Call<String> call = appInterface.getCarImage(body,id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String data = response.body();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }
    private String getRealPathFromURI(Uri contentUri) {
        if (contentUri.getPath().startsWith("/storage")) {
            return contentUri.getPath();
        }
        String id = DocumentsContract.getDocumentId(contentUri).split(":")[1];
        String[] columns = { MediaStore.Files.FileColumns.DATA };
        String selection = MediaStore.Files.FileColumns._ID + " = " + id;
        Cursor cursor = getContentResolver().query(MediaStore.Files.getContentUri("external"), columns, selection, null, null);
        try {
            int columnIndex = cursor.getColumnIndex(columns[0]);
            if (cursor.moveToFirst()) {
                return cursor.getString(columnIndex);
            }
        } finally {
            cursor.close();
        }
        return null;
    }
    public static String saveBitmapToJpeg(Context context, Bitmap bitmap, String name){
        int maximagesize = 50 * 10000; // 저용량 변환중 최대 사이즈
        int realimagesize = maximagesize;
        int quality = 100; //사진퀄리티는 처음 100부터 줄여나가면서 용량을 맞춥니다.

        File storage = context.getCacheDir(); //임시파일(캐시라 적혀잇죠?)

        String fileName = name + ".jpg";  // 어짜피 임시파일이기 때문에 알맞게 적어주세요.

        File tempFile = new File(storage,fileName);

        try{
            tempFile.createNewFile();  // 파일을 생성해주고


            //아래 부분이 가장 중요한 부분이에요.
            while(realimagesize >= maximagesize) {
                if(quality < 0){
                    return "tobig";
                }
                FileOutputStream out = new FileOutputStream(tempFile);

                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
                realimagesize = (int)tempFile.length(); //작아진 본 파일의 크기를 저장하여 다시 비교합니다.

                quality -= 20; //이부분으 줄면서 용량이 작아닙니다.

                out.close(); // 마무리로 닫아줍니다.

            }

            Log.d("resize","imagelocation resizefilesize result: " + realimagesize);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile.getAbsolutePath();   //임시파일 경로로 리턴.
    }


    private void optionDia(){
        WindowManager.LayoutParams params = dia.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dia.getWindow().setAttributes((android.view.WindowManager.LayoutParams)params);
        dia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dia.show();
        opOk=dia.findViewById(R.id.car_op_ok);
        List<ToggleButton> toggleButtons = new ArrayList<>();

        toggleButtons.add(dia.findViewById(R.id.c1));
        toggleButtons.add(dia.findViewById(R.id.c2));
        toggleButtons.add(dia.findViewById(R.id.c3));
        toggleButtons.add(dia.findViewById(R.id.c4));
        toggleButtons.add(dia.findViewById(R.id.c5));
        toggleButtons.add(dia.findViewById(R.id.c6));
        toggleButtons.add(dia.findViewById(R.id.c7));
        toggleButtons.add(dia.findViewById(R.id.c8));
        toggleButtons.add(dia.findViewById(R.id.c9));
        toggleButtons.add(dia.findViewById(R.id.c10));
        toggleButtons.add(dia.findViewById(R.id.c11));
        toggleButtons.add(dia.findViewById(R.id.c12));
        toggleButtons.add(dia.findViewById(R.id.c13));
        toggleButtons.add(dia.findViewById(R.id.c14));
        toggleButtons.add(dia.findViewById(R.id.c15));
        toggleButtons.add(dia.findViewById(R.id.c16));
        toggleButtons.add(dia.findViewById(R.id.c17));
        toggleButtons.add(dia.findViewById(R.id.c18));
        toggleButtons.add(dia.findViewById(R.id.c19));
        toggleButtons.add(dia.findViewById(R.id.c20));
        toggleButtons.add(dia.findViewById(R.id.c21));
        toggleButtons.add(dia.findViewById(R.id.c22));
        toggleButtons.add(dia.findViewById(R.id.c23));
        toggleButtons.add(dia.findViewById(R.id.c24));
        toggleButtons.add(dia.findViewById(R.id.c25));
        toggleButtons.add(dia.findViewById(R.id.c26));
        toggleButtons.add(dia.findViewById(R.id.c27));
        toggleButtons.add(dia.findViewById(R.id.c28));
        toggleButtons.add(dia.findViewById(R.id.c29));
        toggleButtons.add(dia.findViewById(R.id.c30));
        toggleButtons.add(dia.findViewById(R.id.c31));
        toggleButtons.add(dia.findViewById(R.id.c32));
        toggleButtons.add(dia.findViewById(R.id.c33));


        for (ToggleButton toggleButton : toggleButtons) {
            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        makerResult = toggleButton.getText().toString();
                        for (ToggleButton toggleButton : toggleButtons) {
                            toggleButton.setBackgroundResource(R.drawable.round_corner);
                            toggleButton.setTextColor(Color.parseColor("#000000"));
                        }
                        toggleButton.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                        toggleButton.setTextColor(Color.parseColor("#4946E7"));
                    } else {
                        // 버튼이 선택되지 않은 상태일 때의 동작
                        toggleButton.setBackgroundResource(R.drawable.round_corner);
                        toggleButton.setTextColor(Color.parseColor("#000000"));
                    }
                }
            });
        }

        opOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carMaker.setText(makerResult);
                iCheck2 =true;
                if(iCheck1==true&&iCheck2==true&&iCheck3==true){
                    register.setEnabled(true);
                    register.setBackgroundResource(R.drawable.round_corner_blue);
                }
                dia.dismiss();
            }
        });
    }
}
