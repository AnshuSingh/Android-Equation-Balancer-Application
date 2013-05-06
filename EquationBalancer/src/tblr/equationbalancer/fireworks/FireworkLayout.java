package tblr.equationbalancer.fireworks;

import tblr.equationbalancer.fireworks.Rocket.AnimateState;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class FireworkLayout extends SurfaceView implements SurfaceHolder.Callback {

		    class GameThread extends Thread
		    {
		        private boolean mRun = false;

		        private SurfaceHolder surfaceHolder;
		        private AnimateState state;
		        private Paint paint;
		        Fireworks fireworks;
		        private int ctr1 = 0;

		        GameThread( SurfaceHolder surfaceHolder)
		        {
		            this.surfaceHolder = surfaceHolder;
		            fireworks = new Fireworks( getWidth(), getHeight() );

		            paint = new Paint();
		            paint.setStrokeWidth( 2 / getResources().getDisplayMetrics().density );
		            paint.setColor( Color.BLACK );
		            paint.setAntiAlias( true ); 
		        }

		        public void doStart()
		        {
		            synchronized ( surfaceHolder )
		            {
		                setState( AnimateState.asRunning );
		            }
		        }

		        public void pause()
		        {
		            synchronized ( surfaceHolder )
		            {
		                if ( state == AnimateState.asRunning )
		                    setState( AnimateState.asPause );
		            }
		        }

		        public void unpause()
		        {
		            setState( AnimateState.asRunning );
		        }

		        @Override
		        public void run()
		        {
		            while ( mRun && ctr1 < 2000)
		            {
		                Canvas c = null;
		                try
		                {
		                    c = surfaceHolder.lockCanvas( null );

		                    synchronized ( surfaceHolder )
		                    {
		                        if ( state == AnimateState.asRunning )
		                            doDraw(c);
		                        }
		                }
		                finally
		                {
		                    if ( c != null )
		                    {
		                        surfaceHolder.unlockCanvasAndPost( c );
		                    }
		                }
		            }
		        }

		        public void setRunning( boolean b )
		        {
		            mRun = b;
		        }

		        public void setState( AnimateState state )
		        {
		            synchronized ( surfaceHolder )
		            {
		                this.state = state;
		            }
		        }

		        public void doDraw( Canvas canvas)
		        {
		            fireworks.doDraw( canvas, paint );
		        }

		        public void setSurfaceSize( int width, int height )
		        {
		            synchronized ( surfaceHolder )
		            {
		                fireworks.reshape( width, height );
		            }
		            
		        }
		    }

		    private GameThread thread;
		    
		    public FireworkLayout(Context context, AttributeSet attrs, int defStyle) {
		        super(context, attrs, defStyle);
		        getHolder().addCallback(this);
		    }

		    public FireworkLayout(Context context, AttributeSet attrs) {
		        super(context, attrs);
		        getHolder().addCallback(this);
		    }

		    public FireworkLayout( Context context)
		    {
		        super( context );
		        getHolder().addCallback( this );
		    }

		    @Override
		    public void surfaceChanged( SurfaceHolder holder, int format, int width, int height )
		    {
		        thread.setSurfaceSize( width, height );
		    }

		    @Override
		    public void surfaceCreated( SurfaceHolder holder )
		    {
		        thread = new GameThread( getHolder());
		        thread.setRunning( true );
		        thread.doStart();
		        thread.start();
		    }

		    @Override
		    public void surfaceDestroyed( SurfaceHolder holder )
		    {
		        boolean retry = true;
		        thread.setRunning( false );

		        while ( retry )
		        {
		            try
		            {
		                thread.join();
		                retry = false;
		            }
		            catch ( InterruptedException e )
		            {
		            	
		            }
		        }
		   
		    }



			public void stopFireworks() {
				 thread.setRunning( false );
			}
		}



