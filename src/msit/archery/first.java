package msit.archery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class first extends Activity
{

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        
	        TextView t1=(TextView)findViewById(R.id.textView2);
	        t1.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
					Intent intent=new Intent(first.this,ArcheryActivity.class);
					startActivity(intent);
					
					
				}
			});
	        
	        
	        
	        
	 }

}
