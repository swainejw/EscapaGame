import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Explosion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Explosion extends Actor
{
    // Instance Variables ////////////////////////
    GreenfootImage exImages[] = new GreenfootImage[32];
    SimpleTimer t = new SimpleTimer();
    int timeMultiple = 30;
    int frame = 0;
    
    // Constructor /////////////////////////
    public Explosion()
    {
        for (int x = 0; x < 32; x++)
        {
            if (x < 10)
            {
                exImages[x] = new GreenfootImage("explosions/ex00"+ x + ".png");
            }
            else
            {
                exImages[x] = new GreenfootImage("explosions/ex0"+ x + ".png");
            }
        }
    }
    
    // Methods //////////////////////////////////
    public void act()
    {
        frame = t.millisElapsed()/timeMultiple;
        if (frame < 32)
        {
            setImage(exImages[frame]);
        }
        else
        {
            getWorld().removeObject(this);   
        }
       
    }
}
