package msit.archery;

import java.util.Random;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Matrix;
import android.util.FloatMath;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class sec extends SurfaceView implements Runnable {

	SurfaceHolder hold;
	float X=0,Y=0,arrow_x=100,arrow_y,arr_y=0,arr_x=0,fin_arrow_x,fin_arrow_y,t=0,t_prev=0 , use;
	double angle=0,slope=0,angle_new=0,slope_new=0;
	float velocity,t_stop;
	int temp_X=0,temp_Y=0,release=0,arrows=5,score=0;
	int alpha=255;
	Thread thread=null;
	boolean isRunning=false;
	boolean rotated=false;
	Canvas canvas;
	Paint ourblue=new Paint();
	Paint bak=new Paint();
	int i=0;
	Matrix matrix=new Matrix();
	Matrix arr_mat=new Matrix();
	 boolean text=false;
	int frame[]=new int [20];
	Bitmap bow;
	float[] front = new float[2];
	float[] bac = new float[2];
	float init_x,init_y,rotate_x,rotate_y,arrow_init_x,arrow_init_y;
	float canvas_width,canvas_height,arrow_width,arrow_height,bow_width,bow_height;
	float g=(float) 9.8;
	float arrow_top_right_x,arrow_top_right_y;
	
	
	
	Options options = new BitmapFactory.Options();
	
	Bitmap back=BitmapFactory.decodeResource(getResources(),0x7f020001,options);
	
	Bitmap unstretched_arrow=BitmapFactory.decodeResource(getResources(),0x7f020003,options);
	Bitmap unstretched=BitmapFactory.decodeResource(getResources(),0x7f020018,options);
	Bitmap arrow=BitmapFactory.decodeResource(getResources(),0x7f020000,options);
	Bitmap arrow_rot=BitmapFactory.decodeResource(getResources(),0x7f020000,options);
	Bitmap dart;
	
	
   
	
	
	Random rand_x=new Random();
	Random rand_y=new Random();
	boolean randomise=false;
	boolean hasScored=false;
	
	public sec(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		
		hold=getHolder();
		
	}

	public void run() {
		// TODO Auto-generated method stub
		while(!hold.getSurface().isValid());
		
		
		
		
		canvas=hold.lockCanvas();
		
		
		
		options.inScaled=false;
		dart=BitmapFactory.decodeResource(getResources(),0x7f020017,options);
		
		frame[0]=0x7f020003;
		frame[1]=0x7f020004;
		frame[2]=0x7f020005;
		frame[3]=0x7f020006;
		frame[4]=0x7f020007;
		frame[5]=0x7f020008;
		frame[6]=0x7f020009;
		frame[7]=0x7f02000a;
		frame[8]=0x7f02000b;
		frame[9]=0x7f02000c;
		frame[10]=0x7f02000d;
		frame[11]=0x7f02000e;
		frame[12]=0x7f02000f;
		frame[13]=0x7f020010;
		frame[14]=0x7f020011;
		frame[15]=0x7f020012;
		frame[16]=0x7f020013;
		frame[17]=0x7f020014;
		frame[18]=0x7f020015;
		frame[19]=0x7f020016;
		
	
		
		
		back=Bitmap.createScaledBitmap(back, canvas.getWidth(), canvas.getHeight(), true);
		dart=Bitmap.createScaledBitmap(dart, 34, 120, true);
		unstretched=Bitmap.createScaledBitmap(unstretched, 139, 120, true);
		unstretched_arrow=Bitmap.createScaledBitmap(unstretched_arrow, 139, 120, true);
		arrow=Bitmap.createScaledBitmap(arrow, 91, 15, true);
		arrow_y=canvas.getHeight()/2-arrow.getHeight()/2;
		
		
		
		ourblue.setColor(Color.BLACK);
		ourblue.setTextSize(30);
		ourblue.setAntiAlias(true);
	    ourblue.setFilterBitmap(true);
	    ourblue.setDither(true);
	    ourblue.setFakeBoldText(true);
	    bak.setColor(Color.argb(255, 112, 28, 28));
		bak.setTextSize(30);
		bak.setFakeBoldText(true);
		bak.setAntiAlias(true);
	    bak.setFilterBitmap(true);
	    bak.setDither(true);
		
	  
	    init_x=100;
	    init_y=330-unstretched_arrow.getHeight()/2;
	    rotate_x=172;
	    rotate_y=330;
	    arrow_init_x=100;
	    arrow_init_y=330-arrow.getHeight();
	    canvas_width=canvas.getWidth();
	    canvas_height=canvas.getHeight();
	    arrow_width=arrow.getWidth();
	    arrow_height=arrow.getHeight();
	    bow_width=unstretched.getWidth();
	    bow_height=unstretched.getHeight();
	    
	    
	    
		hold.unlockCanvasAndPost(canvas);
		
		
		
		
		while(isRunning)
		{			
			canvas=hold.lockCanvas();

			canvas.drawBitmap(back, 0, 0, ourblue);
				
			canvas.drawText("Score: "+score, 10, 40, ourblue);
			canvas.drawText("Arrows: "+arrows, canvas_width-150, 40, ourblue);
			
			 if(X==0&&Y==0)
			{
				 matrix.setTranslate(init_x,init_y);
				 matrix.postRotate( (float) (angle),rotate_x,rotate_y);
					
				 canvas.drawBitmap(unstretched_arrow, matrix, ourblue);
				
				if(!randomise)
				{
					temp_X=(int) (rand_x.nextInt((int) (canvas_width/2-dart.getWidth()))+canvas_width/2);
					temp_Y=rand_y.nextInt((int) (canvas_height-dart.getHeight()-70))+70;
					
					randomise=true;
				}
				
					
			}
			
			canvas.drawBitmap(dart,temp_X ,temp_Y, null);
			
			 
			if(X>0&&Y>0&&X<=130)
			{
							
					if(release==0)
					{
						
						i=0;
						
						slope=(rotate_y-Y)/(rotate_x-X);
						angle=Math.toDegrees(Math.atan(slope));
						
						if(angle>89)
							angle=89;
						if(angle<-89)
							angle=-89;
						
						angle_new=angle;
						
						matrix.setTranslate(init_x,init_y);
						matrix.postRotate( (float) (angle),rotate_x,rotate_y);
								
						front[0]=(21*(FloatMath.cos( (float) Math.toRadians(angle)))-(-arrow.getHeight()/2*(FloatMath.sin( (float) Math.toRadians(angle)))))+163;
						front[1]=(21*(FloatMath.sin( (float) Math.toRadians(angle)))+( -arrow.getHeight()/2* (FloatMath.cos( (float) Math.toRadians(angle)))))+canvas.getHeight()/2;
						
						bac[0]=((init_x-rotate_x)*(FloatMath.cos( (float) Math.toRadians(angle)))-((init_y-rotate_y)*(FloatMath.sin( (float) Math.toRadians(angle)))))+rotate_x;
						bac[1]=((init_x-rotate_x)*(FloatMath.sin( (float) Math.toRadians(angle)))+((init_y-rotate_y)* (FloatMath.cos( (float) Math.toRadians(angle)))))+rotate_y;
						
						
						
						if(X<=70)
							{
								bow=BitmapFactory.decodeResource(getResources(),frame[19],options);
								velocity=140;
								bac[0]=((init_x-rotate_x)*(FloatMath.cos( (float) Math.toRadians(angle)))-((init_y-rotate_y)*(FloatMath.sin( (float) Math.toRadians(angle)))))+rotate_x;
								bac[1]=((init_x-rotate_x)*(FloatMath.sin( (float) Math.toRadians(angle)))+((init_y-rotate_y)* (FloatMath.cos( (float) Math.toRadians(angle)))))+rotate_y;
								
							}
						
						else if(X>=130)
							{
								bow=BitmapFactory.decodeResource(getResources(),frame[0],options);
								velocity=20;
								
							}
						
						else if(X>70&&X<130)
							{
								bow=BitmapFactory.decodeResource(getResources(),frame[(int) ((int)(130-X)/3)],options);
								velocity=(float) (20+(130-X)*2.03);
								
								use=148-  (float) (((int) ((int)(130-X)/3))*2.52);
								

								bac[0]=((use-rotate_x)*(FloatMath.cos( (float) Math.toRadians(angle)))-((init_y-rotate_y)*(FloatMath.sin( (float) Math.toRadians(angle)))))+rotate_x;
								bac[1]=((use-rotate_x)*(FloatMath.sin( (float) Math.toRadians(angle)))+((init_y-rotate_y)* (FloatMath.cos( (float) Math.toRadians(angle)))))+rotate_y;
								
							}
							
					
						
						bow=Bitmap.createScaledBitmap(bow, 139, 120, true);
						canvas.drawBitmap(bow, matrix, ourblue);
						
						
					}
								
					if(release==1)
					{
						canvas.drawBitmap(unstretched, matrix, ourblue);
						
						try {
							hit_board();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						

						if(!hasScored)
						{
							
							
							
							
							t+=0.18;
							
							arrow_x=(float) (bac[0]+(velocity*(FloatMath.cos( (float) Math.toRadians(angle)))*t));
							arrow_y=(float) (bac[1]+((velocity*(FloatMath.sin( (float) Math.toRadians(angle)))*t)+(0.5*g*t*t)));
							
							
							
							front[0]=(float) (bac[0]+(velocity*(FloatMath.cos( (float) Math.toRadians(angle)))*t_prev));
							front[1]=(float) (bac[1]+((velocity*(FloatMath.sin( (float) Math.toRadians(angle)))*t_prev)+(0.5*g*t_prev*t_prev)));
							
						
									
							slope_new=(arrow_y-front[1])/(arrow_x-front[0]);
							angle_new=Math.toDegrees(Math.atan(slope_new));
						
									
							arr_mat.setTranslate(init_x, 330);
							arr_mat.postRotate((float)((angle_new)),rotate_x,rotate_y);
							arr_mat.postTranslate(arrow_x-bac[0],arrow_y-bac[1] );
							
							arrow_top_right_x=arrow_x+(arrow_width*(FloatMath.cos( (float) Math.toRadians(angle_new))));
							arrow_top_right_y=arrow_y+(arrow_width*(FloatMath.sin( (float) Math.toRadians(angle_new))));
												
							canvas.drawBitmap(arrow, arr_mat, ourblue);
							
							t_prev=(float) (t+0.18-0.0001);
							
							
							
								
						
						}
					
						else
						{
							t_stop=((temp_X+dart.getWidth()/2-5-91*(FloatMath.cos( (float) Math.toRadians(angle_new))) -  8*(FloatMath.cos( (float) Math.toRadians(90-angle_new)))) +arrow_width/2*(FloatMath.sin( (float) Math.toRadians(angle))) - bac[0])/(velocity*(FloatMath.cos( (float) Math.toRadians(angle))));
							
							
							
							
							arrow_x=(float) (bac[0]+(velocity*(FloatMath.cos( (float) Math.toRadians(angle)))*t_stop));
							arrow_y=(bac[1]+((float) ((velocity*(FloatMath.sin( (float) Math.toRadians(angle)))*t_stop)+(0.5*g*t_stop*t_stop))));
							
							
							arr_mat.setTranslate(arrow_init_x, arrow_init_y);
							arr_mat.postRotate((float)((angle_new)),rotate_x,rotate_y);
							arr_mat.postTranslate(arrow_x-bac[0],arrow_y-bac[1]);
							
							
							canvas.drawBitmap(arrow, arr_mat, bak);
							
						}
					
						
						if(i==50||(i==0&&(arrow_x>canvas_width||arrow_y>canvas_height+200||arrow_y<0-200)))
						{
							
									t=0;
									t_prev=0;
									release=0;
									X=0;
									Y=0;
									init_x=100;
									arrow_x=init_x;
									arrow_y=canvas_height/2-arrow_height/2;
									randomise=false;
									i=0;
									alpha=255;
									
									if(!hasScored)
									arrows--;
									
									canvas.drawText("Arrows: "+arrows, canvas.getWidth()-150, 40, ourblue);
									
									if(arrows==0)
									{
										arrows=5;
										score=0;
										canvas.drawText("Score: "+score, 10, 40, ourblue);
										
									}
									
									hasScored=false;
								
						}
						
						
						
						
			
					}
					
			
			
			
		}
			
			else if(X>130)
			{
				matrix.setTranslate(init_x,init_y);
				matrix.postRotate( (float) (angle),rotate_x,rotate_y);
				
				bow=BitmapFactory.decodeResource(getResources(),frame[0],options);
				bow=Bitmap.createScaledBitmap(bow, 139, 120, true); 
				canvas.drawBitmap(bow, matrix, ourblue);
			}
	
			hold.unlockCanvasAndPost(canvas);
	}
}
	
	
	public void values(float x, float y)
	{
		X=x;
		Y=y;
		
	}
	public void resume()
	{
		isRunning=true;
		thread=new Thread(this);
		thread.start();
	}
	
	public void pause() throws InterruptedException
	{
		isRunning=false;
		//thread.join();
		//a.finish();
	}
	public void released(int a)
	{
		release=a;
	}
	
	
	public void hit_board() throws InterruptedException
	{
		
		 float dart_top_left_x=temp_X;
		 float dart_top_right_x=temp_X+dart.getWidth();
		 float dart_top_left_y=temp_Y;
		 float dart_bottom_left_x=temp_X;
		 float dart_bottom_left_y=temp_Y+dart.getHeight();
		
		 float arrow_tip_right_x=(float) (arrow_x+(arrow_width*(FloatMath.cos( (float) Math.toRadians(angle_new))))  + 7.5*(FloatMath.cos( (float) Math.toRadians(90-angle_new))));//- arrow_width/2*(FloatMath.sin( (float) Math.toRadians(angle))) ;
		 float arrow_tip_right_y=(float) (arrow_y+(arrow_width*(FloatMath.sin( (float) Math.toRadians(angle_new))))  + 7.5*(FloatMath.sin( (float) Math.toRadians(90-angle_new))));//+ arrow_height*(FloatMath.sin( (float) Math.toRadians(angle)));
		
		 
		
		 
		 if(!hasScored)
		 {
				//if(arrow_tip_right_x>=canvas_width||arrow_tip_right_y>=canvas_height||arrow_tip_right_y<=0)

			 if(arrow_tip_right_x>=dart_top_left_x&&arrow_tip_right_y>dart_top_left_y&&arrow_tip_right_y<dart_bottom_left_y&&arrow_tip_right_x<dart_top_right_x)
			 {
			
				 hasScored=true;
				 score+=50;
				 canvas.drawText("Score: "+score, 10, 40, ourblue);
				 i++;
			
				 thread.join(1000);
				 return;
			
			
			 }
	
	
		 }
		
		 	if(hasScored)
		 	{
	
		 		canvas.drawText("+50", temp_X, temp_Y-i, bak);i++;
		 		bak.setAlpha(alpha);
		 		alpha-=5;
	
		
		 	}

		
	}
	
	
	
	
}
	
	
	
	
	
	
	
	
	
	
	
	

