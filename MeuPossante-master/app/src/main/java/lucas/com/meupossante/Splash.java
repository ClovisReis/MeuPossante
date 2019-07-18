package lucas.com.meupossante;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


public class Splash extends AppCompatActivity implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final ImageView animationImageView = (ImageView) findViewById(R.id.animacaosplash);
        animationImageView.setBackgroundResource(R.drawable.animacao);

        final AnimationDrawable frameAnimation = (AnimationDrawable) animationImageView.getBackground();
        animationImageView.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });

        android.os.Handler h = new android.os.Handler();
        h.postDelayed(this, 2800);
    }

    public void run() {
        startActivity(new Intent(this, Termos.class));
        finish();
    }
}
