package edu.fiusac.coecys.coecys_info.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.fiusac.coecys.R;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class COECYSinfoActivity extends AppCompatActivity {
    @BindView(R.id.image_paralax)
    ImageView ivParalax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coecysinfo);
        ButterKnife.bind(this);
        setupToolbar();
        Glide.with(this)
                .load(R.drawable.team_coecys)
                .centerCrop()
                .into(ivParalax);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
