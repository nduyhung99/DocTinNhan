package com.example.doctromtinnhan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    Button btnSettingPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSettingPermission=findViewById(R.id.btnSettingPermission);
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return;
        }else {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED&&
                    ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "You have already granted this permission!",
                        Toast.LENGTH_SHORT).show();
            } else {
                requestStoragePermission();
//                requestPermissionWithTedLibrary();
            }
        }
        btnSettingPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingPermission();
            }
        });
    }
// //Check permission với thư viện Tedpermission link github:"https://github.com/ParkSangGwon/TedPermission"
//    private void requestPermissionWithTedLibrary() {
//        TedPermission.with(this).setPermissionListener(new PermissionListener() {
//            @Override
//            public void onPermissionGranted() {
//                Toast.makeText(MainActivity.this,"Permission Granted!",Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onPermissionDenied(List<String> deniedPermissions) {
//                Toast.makeText(MainActivity.this,"Permission Denied!",Toast.LENGTH_LONG).show();
//            }
//        })
//                .setPermissions(Manifest.permission.RECEIVE_SMS,Manifest.permission.CALL_PHONE)
//                .setDeniedTitle("Open setting permission.")
//                .setDeniedMessage("Please turn on permissions at [Setting] > [Permission]")
//                .check();
//    }

    private void openSettingPermission() {
        Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri=Uri.fromParts("package",getPackageName(),null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECEIVE_SMS)&&
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CALL_PHONE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {Manifest.permission.RECEIVE_SMS,Manifest.permission.CALL_PHONE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.RECEIVE_SMS,Manifest.permission.CALL_PHONE}, STORAGE_PERMISSION_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}