package com.example.david.foodtest;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity{

    private EditText name;
    private RadioGroup sex;
    private CheckBox hot,fish,sour;
    private SeekBar seekBar;
    private Button find;
    private ToggleButton toggleButton;
    private List<Food> list_food;
    private List<Food> list_get;
    private Person person;
    private RadioGroupListener radioGroupListener;
    private CheckBoxListenet checkBoxListenet;
    private SeekBarListener seekBarListener;
    private ButtonListener buttonListener;
    private boolean isFish;
    private boolean isHot;
    private boolean isSour;
    private int price = 30;
    private ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        initView();
        initData();
        setLitener();
    }

    private void setLitener() {
        radioGroupListener = new RadioGroupListener();
        sex.setOnCheckedChangeListener(radioGroupListener);
        checkBoxListenet = new CheckBoxListenet();
        fish.setOnCheckedChangeListener(checkBoxListenet);
        hot.setOnCheckedChangeListener(checkBoxListenet);
        sour.setOnCheckedChangeListener(checkBoxListenet);
        seekBarListener = new SeekBarListener();
        seekBar.setOnSeekBarChangeListener(seekBarListener);
        buttonListener = new ButtonListener();
        find.setOnClickListener(buttonListener);
        toggleButton.setOnClickListener(buttonListener);
    }

    private void initData() {
        list_food = new ArrayList<Food>();
        list_get = new ArrayList<Food>();
        person = new Person();
        list_food.add(new Food("麻辣香锅", 55, R.drawable.malaxiangguo, true,false, false));
        list_food.add(new Food("水煮鱼", 48, R.drawable.shuizhuyu, true, true,false));
        list_food.add(new Food("麻辣火锅", 80, R.drawable.malahuoguo, true, true,false));
        list_food.add(new Food("清蒸鲈鱼", 68, R.drawable.qingzhengluyu, false,true, false));
        list_food.add(new Food("桂林米粉", 15, R.drawable.guilin, false, false,false));
        list_food.add(new Food("上汤娃娃菜", 28, R.drawable.wawacai, false, false,false));
        list_food.add(new Food("红烧肉", 60, R.drawable.hongshaorou, false,false, false));
        list_food.add(new Food("木须肉", 40, R.drawable.muxurou, false, false,false));
        list_food.add(new Food("酸菜牛肉面", 35, R.drawable.suncainiuroumian,false, false, true));
        list_food.add(new Food("西芹炒百合", 38, R.drawable.xiqin, false, false,false));
        list_food.add(new Food("酸辣汤", 40, R.drawable.suanlatang, true, false, true));


    }

    private void initView() {
        name = findViewById(R.id.et_name);
        sex = findViewById(R.id.rg_sex);
        hot = findViewById(R.id.cb_hot);
        fish = findViewById(R.id.cb_fish);
        sour = findViewById(R.id.cb_sour);
        seekBar = findViewById(R.id.sb_price);
        seekBar.setProgress(price);
        find = findViewById(R.id.btn_find);
        toggleButton = findViewById(R.id.tb_click);
        pic = findViewById(R.id.iv_pic);
    }

    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_man:
                    person.setSex("男");
                    break;
                case R.id.rb_women:
                    person.setSex("女");
                    break;
            }
            Log.e("zhaichen", "Sex:"+ person.getSex());
        }
    }

    class CheckBoxListenet implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            CheckBox cbBox = (CheckBox) buttonView;
            switch (cbBox.getId()) {
                case R.id.cb_fish:
                    if (isChecked) {
                        isFish = true;
                    } else {
                        isFish = false;
                    }
                    break;
                case R.id.cb_hot:
                    if (isChecked) {
                        isHot = true;
                    } else {
                        isHot = false;
                    }
                    break;
                case R.id.cb_sour:
                    if (isChecked) {
                        isSour = true;
                    } else {
                        isSour = false;
                    }
                    break;
            }
            Log.e("zhaichen", "Fish:" + isFish + " Hot:" + isHot + " Sour:" + isSour);
        }
    }

    class SeekBarListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            price = progress;
            Log.e("zhaichen", "price:" + price);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class ButtonListener implements View.OnClickListener{

        int picNum = 0;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_find:
                    checkData();
                    if (list_get.size() != 0) {
                        picNum = 0;
                        showPic(picNum);
                    }
                    break;
                case R.id.tb_click:
                    if (list_get.size() > 0) {
                        if (toggleButton.isChecked()) {
                            picNum++;
                            if (list_get.size() > picNum) {
                                showPic(picNum);
                            } else {
                                Toast.makeText(MainActivity.this, "因为已经到末尾，所以从头显示", Toast.LENGTH_SHORT).show();
                                picNum = 0;
                                showPic(picNum);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, list_get.get(picNum).toString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "搜索为空", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

        private void checkData() {
            list_get.clear();
            for (int i = 0; i < list_food.size(); i++) {
                Food food = list_food.get(i);
                if ((food.getPrice() <= price) && (food.isFish() == isFish) && (food.isHot() == isHot) && (food.isSour() == isSour)) {
                    list_get.add(food);
                }
            }
            Log.e("zhaichen", "listsize:" + list_get.size());
        }

        private void showPic(int Count) {
            pic.setImageDrawable(getDrawable(list_get.get(Count).getPic()));
        }
    }
}
