package com.yogamurthy.deaddiction.launcher;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv=(findViewById(R.id.lv1));

        final ArrayList<String> appName_AL=new ArrayList<>();
        final HashMap<String,String> appName_packageName_HM=new HashMap<>();

        int count=0;
        List<PackageInfo> packageInfoS = getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo :packageInfoS){
            String appName=getPackageManager().getApplicationLabel(packageInfo.applicationInfo).toString();
            String packageName=packageInfo.packageName.toString();

            if(packageName.equals(appName)) {
//                System.out.println(packageName + ":" + appName);
            }
            else{
                int ab=checkValidIntent(packageName);
                if (ab==1)
                {
                    appName_AL.add(appName);
                    appName_packageName_HM.put(appName,packageName);
                    count++;
                    System.out.println(appName+"<<-->>"+packageName);
                }
            }


        }

        Collections.sort(appName_AL);

        final ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,appName_AL);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this,appName_AL.get(i),Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this,appName_packageName_HM.get(appName_AL.get(i)),Toast.LENGTH_SHORT).show();

                Intent intent=getPackageManager().getLaunchIntentForPackage(appName_packageName_HM.get(appName_AL.get(i)));
//                System.out.println("SOPL"+getPackageManager().getLaunchIntentForPackage(appName_packageName_HM.get(appName_AL.get(i))).toString());
                startActivity(intent);
            }
        });

        System.out.println("count of apps"+count);
    }

    public int checkValidIntent(String str)
    {
        try{
//            System.out.println(str);
            String abc=getPackageManager().getLaunchIntentForPackage(str).toString();

        }
        catch( Exception e)
        {
            System.out.println(str + "|"+ e );
            return 0;
        }
        return 1;
    }
}