package tblr.equationbalancer.fireworks;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Rocket {
	enum AnimateState
	{
	    asReady, asRunning, asPause;
	}

		private float[][] xypoint;
	    public boolean sleep = true;

	    private float energy, length, gravity, ox, oy, x, y, t;
	    private float vx[], vy[];
	    private int patch, red, green, blue;
	    private Random random;
	    private static int ctr = 0;

	    public Rocket( int a, int b, int g )
	    {
	    	xypoint = new float[][] { 
	    							  {200, 300},
	    							  {150, 650},
	    							  {1100, 300},
	    							  {1000,600}
	    							};
	        gravity = g;
	    }

	    public void init( int e, int p, int l, long seed )
	    {
	        energy = e;
	        patch = p + 20;
	        length = l;

	        random = new Random( seed );

	        vx = new float[patch];
	        vy = new float[patch];

	        red = ( int )( random.nextFloat() * 128 ) + 128;
	        blue = ( int )( random.nextFloat() * 128 ) + 128;
	        green = ( int )( random.nextFloat() * 128 ) + 128;

	        int x = random.nextInt(xypoint.length);
	        ox =  xypoint[x][0];
	        oy =  xypoint[x][1];

	        for( int i = 0; i < patch; ++i )
	        {
	            vx[i] = ( ( random.nextFloat() + random.nextFloat() / 2 ) * energy ) - energy / ( random.nextInt( 2 ) + 1 );
	            vy[i] = ( ( random.nextFloat() + random.nextFloat() / 2 ) * energy * 7 / 8 ) - energy / ( random.nextInt( 5 ) + 4 );
	        }
	    }

	    public void start()
	    {
	        t = 0;
	        sleep = false;
	    }

	    public void doDraw( Canvas canvas, Paint paint )
	    {
	        if ( ! sleep )
	        {
	            if ( t < length )
	            {
	                int i, cr, cg, cb;
	                double s;

	                cr = ( int )( random.nextDouble() * 64 ) - 32 + red;
	                cg = ( int )( random.nextDouble() * 64 ) - 32 + green;
	                cb = ( int )( random.nextDouble() * 64 ) - 32 + blue;

	                if ( cr >= 0 && cr <= 256 )
	                    red = cr;
	                if ( cg >= 0 && cg <= 256 )
	                    green = cg;
	                if ( cb >= 0 && cb <= 256 )
	                    blue = cb;

	                int _red = red == 256 ? 255 : red;
	                int _green = green == 256 ? 255 : green;
	                int _blue = blue == 256 ? 255 : blue;

	                int color = Color.rgb( _red, _green, _blue );

	                paint.setColor( color );

	                for ( i = 0; i < patch; ++i )
	                {
	                    s = ( double )t / 100;
	                    x = ( int )( vx[i] * s );
	                    y = ( int )( vy[i] * s - gravity * s * s );

	                    if (canvas!=null)
	                    	canvas.drawCircle( ox + x, oy - y, 2f, paint );
	                }

	               // paint.setColor( Color.BLACK );

	                for ( i = 0; i < patch; ++i )
	                {
	                    if ( t >= length / 2 )
	                    {
	                        for ( int j = 0; j < 2; ++j )
	                        {
	                            s = ( double ) ( ( t - length / 2 ) * 2 + j ) / 100;
	                            x = ( int )( vx[i] * s );
	                            y = ( int )( vy[i] * s - gravity * s * s );

	                            if (canvas!=null)
	                            	canvas.drawCircle( ox + x, oy - y, 2f, paint );
	                        }
	                    }
	                }

	                ++t;
	            }
	            else
	                sleep = true;
	        }
	    }
	}

	

