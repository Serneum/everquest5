/***********************************************************************
 *Author: Chris Rees and Wilfredo Velasquez
 *Date: 11/01/08
 *File Name: BattleData.java
 *Purpose: This class generates monsters appropriate for the level of
 *the character and loads their stats according to the outside data file
 *it also contains methods for loading equipment data and modifiers used
 *in BattleCalc
***********************************************************************/

package com.serneum.rpg.battle;

import static java.lang.Math.ceil;
import static java.lang.Math.round;

import java.io.InputStream;
import java.util.Scanner;

import com.serneum.rpg.character.Stats;

public class BattleData
{
    //Max ID reffers to the number of monsters currently implimented in the
    //game, and is used further down to prevent generating a blank state
    public static int maxID = 37;

    //Method which generates an ID number corresponding to an enemy. It is called by
    //the Overworld upon entering an encounter. The enemy chosen for battle is adjusted
    //by player level, higher levels obtain more variability in their encounters

    public static void collectEnemyData()
    {
        String temp = "";
        double mult = 0.0;
        String multS = "";

        InputStream enStream = BattleData.class.getResourceAsStream("/data/Monsters.dat");
        Scanner enData = new Scanner(enStream);

        //Randomized formula, modified further by an adjuster formula, to prevent
        //encounters of too low a level
        int random = (int) (Math.random()*(4*Stats.level))+1;
        if (Stats.level >= 3)
            random += 4 * (Stats.level - (Stats.level/2 + 1));

        String ID = "ID" + random;

        mult = random;

        //Mult generates an experience adjuster based on enemy level and player
        //level. Each level difference will add or subtract 25% of the base EXP
        mult = ((ceil(mult/3) - Stats.level) * .25) + 1;
        //System.out.print("\n" + ID);

        while (enData.hasNext())
        {
            temp = enData.next();

            if (temp.equals(ID))
            {
                //System.out.println(ID);
                Battle.enName = enData.next();
                Battle.enHealth = enData.nextInt();
                BattleCalc.enMin = enData.nextInt();
                BattleCalc.enMax = enData.nextInt();
                BattleCalc.enAcc = enData.nextInt();
                BattleCalc.enAC  = enData.nextInt();
                BattleCalc.enDR  = enData.nextInt();
                multS = "" + round(enData.nextInt() * mult);
                Stats.expGain = Integer.parseInt(multS);
                Battle.enWeapon = enData.next();
                Battle.enDeath = enData.next();


                String find = "_";
                String replace = " ";
                int index = 0;

                index = Battle.enName.indexOf(find);
                if(index != -1)
                {
                    Battle.enName = Battle.enName.replace(find, replace);
                }

                index = Battle.enWeapon.indexOf(find);
                while(index != -1)
                {
                    Battle.enWeapon = Battle.enWeapon.replace(find, replace);
                    index = Battle.enWeapon.indexOf(find);
                }

                index = Battle.enDeath.indexOf(find);
                while(index != -1)
                {
                    Battle.enDeath = Battle.enDeath.replace(find, replace);

                    index = Battle.enDeath.indexOf(find);
                }

            }

            //Should the number generated be too large for the set amount
            //of monsters located in the data file, a monster of uber powerz
            //will spawn, destroying the player and reucing his will to play
            //to negible proportions
            if(random > maxID)
            {
                //System.out.println(ID);
                Battle.enName = "Missing No.";
                Battle.enHealth = 2500;
                BattleCalc.enMin = 100;
                BattleCalc.enMax = 250;
                BattleCalc.enAcc = 125;
                BattleCalc.enAC  = 100;
                BattleCalc.enDR  = 75;
                multS = "" + round(7500 * mult);
                Stats.expGain = Integer.parseInt(multS);
                Battle.enWeapon = "Glitched Image";
                Battle.enDeath = "How did you beat a monster that isn't even in the game?";
            }
        }
        enData.close();
    }

    //Method to collect equipment data and process it for battle calculations
    //relating to damage, accuracy, and dodge. The type of item is determined
    //by the item parameter, and the name is the paramete name.
    //This method is called from Stats.java primarily
    public static void collectEquipmentData(String item, String name)
    {
        String temp = "";

        if(item.equals("Weapon"))
        {
            InputStream wepStream = BattleData.class.getResourceAsStream("/data/Weapons.dat");
               Scanner wepData = new Scanner(wepStream);

            while (wepData.hasNext())
            {
                temp = wepData.nextLine();

                if (temp.equals("" + name))
                {
                    Stats.wMin = wepData.nextInt();
                    Stats.wMax = wepData.nextInt();
                    Stats.wAcc = wepData.nextInt();
                }
            }
            wepData.close();
        }

        else if (item.equals("Armour"))
        {
            InputStream armStream = BattleData.class.getResourceAsStream("/data/Armours.dat");
            Scanner armData = new Scanner(armStream);

            while (armData.hasNext())
            {
                temp = armData.nextLine();

                if (temp.equals("" + name))
                    Stats.armMod = armData.nextInt();
            }
            armData.close();
        }

        else if (item.equals("Shield"))
        {
            InputStream armStream = BattleData.class.getResourceAsStream("/data/Armours.dat");
            Scanner armData = new Scanner(armStream);

            while (armData.hasNext())
            {
                temp = armData.nextLine();

                if (temp.equals("" + name))
                    Stats.shlMod = armData.nextInt();
            }
            armData.close();
        }
    }
}