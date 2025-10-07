import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Hero here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hero extends Actor
{
    // Instance Variables ////////////////////////
    int speed = 6;
    
    // Constructor ////////////////////////
    public Hero()
    {
        getImage().scale(30, 40);
    }
    
    // Methods ////////////////////////
    public void act()
    {
        if (MyWorld.started)
        {
            keyControl();
            checkHitBlock();
        }
    }
    
    public void keyControl()
    {
        if (Greenfoot.isKeyDown("left"))
        {
            setLocation(getX() - speed, getY());
        }
        if (Greenfoot.isKeyDown("right"))
        {
            setLocation(getX() + speed, getY());
        }
        if (Greenfoot.isKeyDown("up"))
        {
            setLocation(getX(), getY() - speed);
        }
        if (Greenfoot.isKeyDown("down"))
        {
            setLocation(getX(), getY() + speed);
        }
    }
    
    public boolean checkHitBlock()
    {
        Block b = (Block) getOneIntersectingObject(Block.class);
        if (b != null)
        {
            getWorld().addObject(new Explosion(), getX(), getY());
            MyWorld.gameOver = true;
            getWorld().removeObject(this);
            MyWorld.started = false;
            return true;
        }
        return false;
    }
}
