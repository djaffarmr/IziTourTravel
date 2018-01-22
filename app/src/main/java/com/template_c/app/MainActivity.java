package com.template_c.app;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.template_c.app.features.Agen;
import com.template_c.app.features.Bus;
import com.template_c.app.features.Hotel;
import com.template_c.app.features.Kereta;
import com.template_c.app.features.Kontak;
import com.template_c.app.features.PPOB;
import com.template_c.app.features.Pesawat;
import com.template_c.app.features.Pulsa;
import com.template_c.app.features.Travel;
import com.template_c.app.features.Tur;
import com.template_c.app.features.Umroh;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    // local variables
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    LinearLayout viewPagerIndicator;
    Timer timer;
    private int dotsCount;
    private ImageView[] dot;
    ImageButton btnPesawat,
            btnKereta,
            btnHotel,
            btnUmroh,
            btnBus,
            btnTravel,
            btnPPOB,
            btnPulsa,
            btnTur,
            btnAgen,
            btnKontak,
            btnKeluar;
    private static final int TIME_LIMIT = 1800;
    private static long backPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // state custom toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // disable toobar default title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        // disable home button
        ImageButton btnHome = (ImageButton) findViewById(R.id.btn_home);
        btnHome.setVisibility(View.GONE);

        // state the viewpager and set the adapter to viewpager adapter class
        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        // set the indicator for viewpager
        viewPagerIndicator = (LinearLayout) findViewById(R.id.main_viewpager_indicator);
        dotsCount = viewPagerAdapter.getCount();
        dot = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dot[i] = new ImageView(this);
            dot[i].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dot_inactive));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);
            viewPagerIndicator.addView(dot[i], params);
        }

        dot[0].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dot_active));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dot[i].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dot_inactive));
                }
                dot[position].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dot_active));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // automatically call the viewpager timer class after 6s
        timer = new Timer();
        timer.scheduleAtFixedRate(new ViewPagerTimer(), 3000, 6000);

        // state the buttons
        btnPesawat = (ImageButton) findViewById(R.id.btn_pesawat);
        btnKereta = (ImageButton) findViewById(R.id.btn_kereta);
        btnHotel = (ImageButton) findViewById(R.id.btn_hotel);
        btnUmroh = (ImageButton) findViewById(R.id.btn_umroh);
        btnBus = (ImageButton) findViewById(R.id.btn_bus);
        btnTravel = (ImageButton) findViewById(R.id.btn_travel);
        btnPPOB = (ImageButton) findViewById(R.id.btn_ppob);
        btnPulsa = (ImageButton) findViewById(R.id.btn_pulsa);
        btnTur = (ImageButton) findViewById(R.id.btn_tur);
        btnAgen = (ImageButton) findViewById(R.id.btn_agen);
        btnKontak = (ImageButton) findViewById(R.id.btn_kontak);
        btnKeluar = (ImageButton) findViewById(R.id.btn_keluar);
    }

    // viewpager adapter class that manage viewpager content
    private class ViewPagerAdapter extends PagerAdapter {
        // variables
        private Context context;
        private LayoutInflater layoutInflater;
        private int[] images =  {R.drawable.main_viewpager_one,
                R.drawable.main_viewpager_two,
                R.drawable.main_viewpager_three
        };

        private ViewPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.main_viewpager, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.img_viewpager);
            imageView.setImageResource(images[position]);

            ViewPager viewPager = (ViewPager) container;
            viewPager.addView(view, 0);
            return  view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    // viewpager timer class
    private class ViewPagerTimer extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    // inflate the menu. add item to toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_overflow, menu);
        return true;
    }

    // menu content
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.credits:
                Toast.makeText(MainActivity.this, "icons made by Freepik from\nwww.flaticon.com", Toast.LENGTH_LONG).show();
                return true;
        }

        return  super.onOptionsItemSelected(item);
    }

    public void PesawatBtn(View v) {
        Intent intent = new Intent(MainActivity.this, Pesawat.class);
        startActivity(intent);
    }

    public void KeretaBtn(View v) {
        Intent intent = new Intent(MainActivity.this, Kereta.class);
        startActivity(intent);
    }

    public void HotelBtn(View v) {
        Intent intent = new Intent(MainActivity.this, Hotel.class);
        startActivity(intent);
    }

    public void UmrohBtn(View v) {
        Intent intent = new Intent(MainActivity.this, Umroh.class);
        startActivity(intent);
    }

    public void BusBtn(View v) {
        Intent intent = new Intent(MainActivity.this, Bus.class);
        startActivity(intent);
    }

    public void TravelBtn(View v) {
        Intent intent = new Intent(MainActivity.this, Travel.class);
        startActivity(intent);
    }

    public void PPOBBtn(View v) {
        Intent intent = new Intent(MainActivity.this, PPOB.class);
        startActivity(intent);
    }

    public void PulsaBtn(View v) {
        Intent intent = new Intent(MainActivity.this, Pulsa.class);
        startActivity(intent);
    }

    public void TurBtn(View v) {
        Intent intent = new Intent(MainActivity.this, Tur.class);
        startActivity(intent);
    }

    public void AgenBtn(View v) {
        Intent intent = new Intent(MainActivity.this, Agen.class);
        startActivity(intent);
    }

    public void KontakBtn(View v) {
        Intent intent = new Intent(MainActivity.this, Kontak.class);
        startActivity(intent);
    }

    public void KeluarBtn(View v) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    // set that user must press back button twice to exit the app
    @Override
    public void onBackPressed() {

        if (TIME_LIMIT + backPressed > System.currentTimeMillis()) {
            // super.onBackPressed();
            moveTaskToBack(true);
        } else {
            Toast.makeText(MainActivity.this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }
}