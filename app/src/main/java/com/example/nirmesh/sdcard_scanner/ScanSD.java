package com.example.nirmesh.sdcard_scanner;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.SizeFileComparator;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class ScanSD extends AppCompatActivity {

    private File file;
    ArrayList<File> fileList = new ArrayList<File>();
    ListView listView;


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_sd);


        //1.ListView
        listView = (ListView) findViewById(R.id.listView);

        //2. For obtaining the root File
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        fileList = new ArrayList<File> (FileUtils.listFiles(file, new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY));


        Collection<File> topBiggestFiles =  Arrays.asList(getTop10(fileList));
        Collection<String> frequentlyUsedExtensions = getFrequentlyUsedExtn(fileList);

        //sending only the file names to the method
        List<String> fileNameList = new ArrayList<>();
        for(File f : topBiggestFiles){
            fileNameList.add(f.getName()+"---"+f.length());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                fileNameList);

        listView.setAdapter(arrayAdapter);
        //
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
    private static File[] getTop10(Collection<File> files){
        File[] flArr =files.toArray(new File[files.size()]);
        Arrays.sort(flArr ,SizeFileComparator.SIZE_COMPARATOR);
        if(flArr.length <= 10){
            return flArr;
        }else{
            return Arrays.asList(flArr).subList(flArr.length - 10, flArr.length).toArray(new File[10]);
        }
    }

    private static Collection<String> getFrequentlyUsedExtn(Collection<File> files){
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        for(File f : files){
           /* map.put(FilenameUtils.getExtension(f.getAbsolutePath()),
                    map.get(FilenameUtils.getExtension(f.getAbsolutePath())) == null ? 1 : map.get(FilenameUtils.getExtension(f.getAbsolutePath()) +1));*/
        }
        HashMap<String,Integer> sortedMap = (HashMap<String, Integer>) sortByComparator(map, false);
        return new ArrayList<String>(sortedMap.keySet()).subList(0, ((sortedMap.keySet().size() <5) ? sortedMap.keySet().size() : 5));
    }

    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,
                               Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}


