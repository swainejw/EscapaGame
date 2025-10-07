import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
import java.util.Date;

public class MyWorld extends World
{
    // Instance Variables //////////////////////////
    
    // timers
    SimpleTimer tToggleMode = new SimpleTimer();
    SimpleTimer tAlive = new SimpleTimer();
    
    // labels
    Label labelOnScreen = new Label("NORMAL MODE", 20);
    Label timeCount = new Label("", 20);
    
    // various integer variables
    int edgeBuffer = 50;
    int r, g, b, counter = 0;
    int step = 20;
    int sMax = 200;
    int colourMax = 255;
    
    // speed variables
    int b1sp = 10;
    int b2sp = 5;
    int b3sp = 7;
    int b4sp = 8;

    // direction variables
    int b1dir = 1;
    int b2dir = 2;
    int b3dir = 3;
    int b4dir = 4;
    
    // picture variables
    int b1pic = 2;
    int b2pic = 1;
    int b3pic = 3;
    int b4pic = 4;
    
    // Needed to save info for storing score
    String seconds, tenths, name;
    
    // booleans for game modes
    public static boolean started = false;
    boolean savedScore = false;
    boolean nameAsked = false;
    public static boolean gameOver = false;  
    
    // World Constructor //////////////////////////
    public MyWorld()
    {    
        super(800, 600, 1, false); 
        // get w and h - easier to type
        int w = getWidth();
        int h = getHeight();
        // add enemies (blocks) in random spots
        addObject(new Block(b1pic, b1dir, b1sp), getRndExclude(w, w/2 - 150, w/2 + 150), getRndExclude(h, h/2 - 150, h/2 + 150));
        addObject(new Block(b2pic, b2dir, b2sp), getRndExclude(w, w/2 - 150, w/2 + 150), getRndExclude(h, h/2 - 150, h/2 + 150));
        addObject(new Block(b3pic, b3dir, b3sp), getRndExclude(w, w/2 - 150, w/2 + 150), getRndExclude(h, h/2 - 150, h/2 + 150));
        addObject(new Block(b4pic, b4dir, b4sp), getRndExclude(w, w/2 - 150, w/2 + 150), getRndExclude(h, h/2 - 150, h/2 + 150));
        // add hero to the middle
        addObject(new Hero(), getWidth()/2, getHeight()/2);
        // add mode and time
        addObject(labelOnScreen, getWidth()/2, 10);
        addObject(timeCount, 30, 10);
        // initially, the game has not started, nor is the game over
        started = false;
        gameOver = false;
        nameAsked = false;
        
    }
    
    // Act method (loop)
    public void act()
    {
        // if the game has started, mark the time alive timer 
        // and switch the boolean to true so it only happens once
        if (!started)
        {
            tAlive.mark();
            // space bar starts everything
            if (Greenfoot.isKeyDown("space"))
            {
                started = true;
            }
        }
        else if (!gameOver)
        {
            // as long as the game isn't over, keep updating the timer in
            // the top corner with seconds/tenths
            seconds = String.valueOf(tAlive.millisElapsed()/1000);
            tenths = String.valueOf((tAlive.millisElapsed()/100)%10);
            timeCount.setValue(seconds + "." + tenths);
        }
        
        // only ask for name once
        if (!nameAsked)
        {
            name = Greenfoot.ask("What's your player name?");
            nameAsked = true;
        }
        
        // save score once when the game ends
        if (gameOver && !savedScore)
        {
            saveScoreToFile();
            savedScore = true;
        }
        
        // pressing 's' will toggle the flashing colours mode
        if (Greenfoot.isKeyDown("s") && tToggleMode.millisElapsed() > sMax)
        {
            counter++;       
            tToggleMode.mark();
        }
        
        // this if/else is just for the toggle of colours
        if (counter % 2 == 1)
        {
            colourStepping();
        
            GreenfootImage bg = getBackground();
            bg.setColor(new Color(r, g, b));
            bg.fill();
            
            labelOnScreen.setValue("MIND-BLOWING MODE");
        }
        else
        {
            GreenfootImage bg = getBackground();
            bg.setColor(new Color(0, 0, 0));
            bg.fill();
            
            labelOnScreen.setValue("NORMAL MODE");
        }
    }
    
    // User-created methods //////////////////////////
    
    /* this method chooses a random number from 0 up to maxVal, but
     * makes sure not to pick it between avoidMin and avoidMax
     * 
     * For example, if maxVal is 800, avoidMin is 200, and avoidMax
     * is 400, no number between 200-400 would be returned and it 
     * also can't be above 800
     */
    public int getRndExclude(int maxVal, int avoidMin, int avoidMax)
    {
        int value = Greenfoot.getRandomNumber(maxVal);
        while (value > avoidMin && value < avoidMax)
        {
            value = Greenfoot.getRandomNumber(maxVal);
        }
        return value;
    }
    
    /* this method is responsible for the colour flashing mode...
     * not sure why I did this. it's annoying.
     */
    public void colourStepping()
    {
        r += step;
        if (r > colourMax) 
        {
            r = 0;
            g += step;
        }
        if (g > colourMax) 
        {
            g = 0;
            b += step;
        }
        if (b > 255) 
        {
            b = 0;
        }
    }
    
    public void saveScoreToFile()
    {
        try 
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter("allScores.txt", true));
            Date d = new Date();
            bw.write(name + ";" + seconds + "." + tenths + ";" + d + "\n");
            bw.close();
        }
        catch (FileNotFoundException e) 
        {
            System.out.println("Input file not found: " + e.getMessage());
        } 
        catch (IOException e) {
            System.out.println("An IO error occurred: " + e.getMessage());
        }
    }
}
