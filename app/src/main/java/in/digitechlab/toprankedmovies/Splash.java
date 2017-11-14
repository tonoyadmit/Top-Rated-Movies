package in.digitechlab.toprankedmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.digitechlab.popularmovies1.R;

public class Splash extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		Thread t = new Thread(){
			
		public void run(){
		
			try{
				sleep(4000);
			}catch (InterruptedException e){
		}
			finally{
				Intent i = new Intent ("android.intent.action.MAINACTIVITY");
				startActivity(i);
				finish();
			}
	}
};
t.start();

}
}