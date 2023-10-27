package com.example.capston_pj;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.capston_pj.models.Car;
import com.example.capston_pj.models.GetSchedule;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.retrofitInterface.RetrofitFile;
import com.example.capston_pj.retrofitInterface.RetrofitMacInterface;
import com.example.capston_pj.retrofitInterface.RetrofitPostContent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInfoActivity extends Fragment {
    private View view;
    private TextView updateBtn, userName,userId,logout,mon1,mon2,tues1,tues2,wen1,wen2,thurs1,thurs2,fri1,fri2,carNum,carMaker,carModel,cartype,txMoney,minusError,sendError,plusM,minusM,sendM,genderTx;
    private Button add,car,btnPMOk,btnPMNo,btnMMOk,btnMMNo,btnSMOk,btnSMNo,menu,charge;
    private LinearLayout getCar,menuValue;
    private EditText etPMoney,etMMoney,etSMoney,etS;
    private FrameLayout frame;

    private String imageFileName;
    private Context context;
    private ActivityResultLauncher<Intent> imageLauncher;
    private ImageView ivImage,carImage;
    private Member member;
    private Boolean menuFlag = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.activity_user_info,container,false);
        Intent intent = getActivity().getIntent();
        member = (Member)intent.getSerializableExtra("member");
        userName = (TextView) view.findViewById(R.id.info_user_name);
        updateBtn = (TextView) view.findViewById(R.id.info_update);
        add = (Button)view.findViewById(R.id.add_profile);
        ivImage = view.findViewById(R.id.profile);
        logout = view.findViewById(R.id.info_logout);
        context = getActivity();
        mon1 = view.findViewById(R.id.info_mon1);
        mon2 = view.findViewById(R.id.info_mon2);
        tues1 = view.findViewById(R.id.info_tues1);
        tues2 = view.findViewById(R.id.info_tues2);
        wen1 = view.findViewById(R.id.info_wen1);
        wen2 = view.findViewById(R.id.info_wen2);
        thurs1 = view.findViewById(R.id.info_thurs1);
        thurs2 = view.findViewById(R.id.info_thurs2);
        fri1 = view.findViewById(R.id.info_fri1);
        fri2 = view.findViewById(R.id.info_fri2);
        car = view.findViewById(R.id.info_set_car);
        getCar = view.findViewById(R.id.info_getCar);
        carNum = view.findViewById(R.id.info_carnum);
        carMaker = view.findViewById(R.id.info_carMaker);
        carModel = view.findViewById(R.id.info_carModel);
        cartype = view.findViewById(R.id.info_car_type);
        carImage = view.findViewById(R.id.info_carImage);
        menuValue = view.findViewById(R.id.info_menu_value);
        txMoney = view.findViewById(R.id.info_money);
        genderTx = view.findViewById(R.id.gender);
        frame = view.findViewById(R.id.FirstFrame);
        menu= view.findViewById(R.id.info_menu);
        charge = view.findViewById(R.id.info_charge);


        carInfo(member.getId());
        String genderInfo = member.getGender().toString();
        if(genderInfo.equals("m")){
            genderTx.setText("남");
            frame.setBackgroundColor(Color.parseColor("#bbceff"));
        } else if (genderInfo.equals("w")) {
            genderTx.setText("여");
            frame.setBackgroundColor(Color.parseColor("#EED7FA"));
        }

        String packageName = context.getPackageName();
        MainActivity activity = (MainActivity)getActivity();
        imageFileName = member.getName()+"profile";
        Log.d("memberid체크","memberid" +member.getMoney());
        String imageUrl = "url"+member.getId();
        String CarImageUrl = "url"+member.getId();
        loadProfileImage(imageUrl,ivImage);
        loadCarImage(CarImageUrl,carImage);

        loadSchedule(member.getId());

        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,moneyActivity.class);
                intent.putExtra("member",member);
                intent.putExtra("money",txMoney.getText().toString().substring(2));
                startActivity(intent);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(menuFlag==false){
                    menuFlag=true;
                    menuValue.setVisibility(View.VISIBLE);
                } else if (menuFlag==true) {
                    menuFlag=false;
                    menuValue.setVisibility(View.GONE);
                }
            }
        });

        txMoney.setText("₩ "+member.getMoney());
        imageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    ivImage.setImageURI(uri);
                    saveProfile(uri , member.getId());
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                } else {
                    // 권한 있음
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    imageLauncher.launch(intent);
                }
            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,RegisterCarActivity.class);
                intent.putExtra("member",member);
                startActivity(intent);
            }
        });
        userName.setText(member.getName()+"님");
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setIntent = new Intent(getActivity(),UpdateActivity.class);
                setIntent.putExtra("member",member);
                startActivity(setIntent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Toast.makeText(getActivity(), "로그아웃", Toast.LENGTH_SHORT).show();

                getActivity().finish();
            }
        });


       return view;
    }

    private void carInfo(Long id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitMacInterface service = retrofit.create(RetrofitMacInterface.class);
        Car memberId = new Car(id);
        Call<Car> call = service.getCarInfo(memberId);
        call.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                if (response.isSuccessful()) {
                    Car data = response.body();
                    Log.d("carData","data:"+data.getNum() );
                    if(data.getNum().equals("x")){
                        getCar.setVisibility(View.GONE);
                        car.setVisibility(View.VISIBLE);
                    }else {
                        getCar.setVisibility(View.VISIBLE);
                        car.setVisibility(View.GONE);

                        carNum.setText(data.getNum().toString());
                        carMaker.setText(data.getMaker().toString());
                        carModel.setText(data.getModel().toString());
                        cartype.setText(data.getType().toString());
                    }

                }
            }
            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }

    private void loadSchedule(Long id){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RetrofitPostContent service = retrofit.create(RetrofitPostContent.class);
        GetSchedule param = new GetSchedule(id);
        Call<GetSchedule> call = service.getSchedule(param);
        call.enqueue(new Callback<GetSchedule>() {
            @Override
            public void onResponse(Call<GetSchedule> call, Response<GetSchedule> response) {
                if (!response.isSuccessful()) {
                    Log.d("error","code:"+response.code());
                    return;
                }
                GetSchedule data = response.body();
                String[] result = data.getResult();
                String[] arr;
                if(!result[0].equals("x")){
                    arr = result[0].split(",");
                    if(arr.length==1){
                        if(arr[0].charAt(0)=='A'){
                            mon1.setText(arr[0].substring(2));
                        } else if (arr[0].charAt(0)=='P') {
                            mon2.setText(arr[0].substring(2));
                        }
                    } else if (arr.length==2) {
                        mon1.setText(arr[0].substring(2));
                        mon2.setText(arr[1].substring(2));
                    }
                }
                if(!result[1].equals("x")){
                    arr = result[1].split(",");
                    if(arr.length==1){
                        if(arr[0].charAt(0)=='A'){
                            tues1.setText(arr[0].substring(2));
                        } else if (arr[0].charAt(0)=='P') {
                            tues2.setText(arr[0].substring(2));
                        }
                    } else if (arr.length==2) {
                        tues1.setText(arr[0].substring(2));
                        tues2.setText(arr[1].substring(2));
                    }
                }
                if(!result[2].equals("x")){
                    arr = result[2].split(",");
                    if(arr.length==1){
                        if(arr[0].charAt(0)=='A'){
                            wen1.setText(arr[0].substring(2));
                        } else if (arr[0].charAt(0)=='P') {
                            wen2.setText(arr[0].substring(2));
                        }
                    } else if (arr.length==2) {
                        wen1.setText(arr[0].substring(2));
                        wen2.setText(arr[1].substring(2));
                    }
                }
                if(!result[3].equals("x")){
                    arr = result[3].split(",");
                    if(arr.length==1){
                        if(arr[0].charAt(0)=='A'){
                            thurs1.setText(arr[0].substring(2));
                        } else if (arr[0].charAt(0)=='P') {
                            thurs2.setText(arr[0].substring(2));
                        }
                    } else if (arr.length==2) {
                        thurs1.setText(arr[0].substring(2));
                        thurs2.setText(arr[1].substring(2));
                    }
                }
                if(!result[4].equals("x")){
                    arr = result[4].split(",");
                    if(arr.length==1){
                        if(arr[0].charAt(0)=='A'){
                            fri1.setText(arr[0].substring(2));
                        } else if (arr[0].charAt(0)=='P') {
                            fri2.setText(arr[0].substring(2));
                        }
                    } else if (arr.length==2) {
                        fri1.setText(arr[0].substring(2));
                        fri2.setText(arr[1].substring(2));
                    }
                }
            }

            @Override
            public void onFailure(Call<GetSchedule> call, Throwable t) {
                Log.d("fail","error:"+t);
            }
        });
    }

    private void saveProfile(Uri uri , long id){
        Log.d("이미지 전송 호출","1" +uri);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        String filePath = getRealPathFromURI(uri);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        String resizeFilePath= saveBitmapToJpeg(context,bitmap,imageFileName+"temp");
        File file = new File(resizeFilePath);

        RequestBody requestFile = RequestBody.create(file,MediaType.parse("image/jpeg"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("files", imageFileName, requestFile);
        Log.d("이미지 전송 호출","2" +requestFile);
        RetrofitFile appInterface = retrofit.create(RetrofitFile.class);
        Call<String> call = appInterface.request(body,id);
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
        Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), columns, selection, null, null);
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
    public void loadProfileImage(String imageUrl, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.basic) // 로딩 중에 표시할 이미지
                .error(R.drawable.basic) // 로딩 실패 시 표시할 이미지
                .diskCacheStrategy(DiskCacheStrategy.NONE); // 디스크 캐시 전략 설정

        Glide.with(this)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }
    public void loadCarImage(String imageUrl, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.basic) // 로딩 중에 표시할 이미지
                .error(R.drawable.basic) // 로딩 실패 시 표시할 이미지
                .diskCacheStrategy(DiskCacheStrategy.NONE); // 디스크 캐시 전략 설정

        Glide.with(this)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
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

}
