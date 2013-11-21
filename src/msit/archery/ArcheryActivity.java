package msit.archery;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ArcheryActivity extends Activity implements OnTouchListener{
    /** Called when the activity is first created. */
	
	sec object;
	float x,y;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        object=new sec(this);
        object.setOnTouchListener(this);
        setContentView(object); 
        
    }

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		x=event.getX();
		y=event.getY();
		
		if(object.release==0)
		object.values(x,y);
		
		if((event.getAction() == KeyEvent.ACTION_UP))
		{
			if(x<130)
			object.released(1);
		}
		
		
		return true;
		
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		object.resume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			object.pause();
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}