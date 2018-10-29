package maful.id.web.ohmovies.activity;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import maful.id.web.ohmovies.R;

public class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting_language:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
            case R.id.about_app:
                Toast.makeText(getApplicationContext(), "by Maful Prayoga Arnandi - 16.11.0046", Toast.LENGTH_LONG).show();
                break;
            case R.id.move:
                Intent i = new Intent(this, TopRatedActivity.class);
                startActivity(i);
                break;
            case R.id.now_playing:
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
