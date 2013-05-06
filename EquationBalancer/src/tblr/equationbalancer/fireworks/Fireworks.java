package tblr.equationbalancer.fireworks;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Fireworks {
		/**
	     * Maximum number of rockets.
	     */
	    public int MaxRocketNumber = 1;
	    /**
	     * Controls "energy" of firwork explosion. Default value 850.
	     */
	    public int MaxRocketExplosionEnergy = 950;
	    /**
	     * Controls the density of the firework burst. Larger numbers give higher density.
	     * Default value 90.
	     */
	    public int MaxRocketPatchNumber = 90;
	    /**
	     * Controls the radius of the firework burst. Larger numbers give larger radius.
	     * Default value 68.
	     */
	    public int MaxRocketPatchLength = 68;

	    /**
	     * Controls gravity of the firework simulation.
	     * Default value 400.
	     */
	    public int Gravity = 400;

	    transient private Rocket rocket;
	    transient private boolean rocketsCreated = false;

	    private int width;
	    private int height;

	    Fireworks( int width, int height )
	    {
	        this.width = width;
	        this.height = height;
	    }

	   /* void createRockets()
	    {
	        rocketsCreated = true;

	        Rocket tempRocket[] = new Rocket[MaxRocketNumber];

	        for ( int i = 0; i < MaxRocketNumber; i++ )
	            tempRocket[i] = new Rocket( width, height, Gravity );

	        rocket = tempRocket;
	    }*/

	    public synchronized void reshape( int width, int height )
	    {
	        this.width = width;
	        this.height = height;

	        rocketsCreated = false;
	    }

	    public void doDraw( Canvas canvas, Paint paint)
	    {
	        int i, e, p, l;
	        long s;

	        boolean sleep;

	        if ( ! rocketsCreated )
	        {
	           // createRockets();
	            rocket = new Rocket( width, height, Gravity );
	            rocketsCreated = true;
	        }

	        if ( rocketsCreated )
	        {
	            sleep = true;

	           // for ( i = 0; i < MaxRocketNumber; i++ )
	                sleep = sleep && rocket.sleep;
	                
	             // for ( i = 0; i < MaxRocketNumber; i++ )
	                e = ( int )( Math.random() * MaxRocketExplosionEnergy * 3 / 4 ) + MaxRocketExplosionEnergy / 4 + 1;
	                p = ( int )( Math.random() * MaxRocketPatchNumber * 3 / 4 ) + MaxRocketPatchNumber / 4 + 1;
	                l = ( int )( Math.random() * MaxRocketPatchLength * 3 / 4 ) + MaxRocketPatchLength / 4 + 1;
	                s = ( long )( Math.random() * 10000 );

	                Rocket r = rocket;//[i];
	                if ( r.sleep)// && Math.random() * MaxRocketNumber * l < 2 ) 
	                {
	                    r.init( e, p, l, s );
	                    r.start();
	                }

	                if ( rocketsCreated )
	                    r.doDraw( canvas, paint );
	        }
	    }
	}
