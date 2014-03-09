/***********************************************************************
 *Author: Chris Rees and Wilfredo Velasquez
 *Date: 11/14/08
 *File Name: BattleCalc.java
 *Purpose: Calculations for actions taken during battle. These include:
 *player ability use, item use, dodge, accuracy, and damage, it then
 *sends the information to both the battle screen and the Stats class
 *to be saved to the character file
***********************************************************************/

package com.serneum.rpg.battle;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import com.serneum.rpg.character.Stats;

public class BattleCalc

{
    public static int plAC = 0, plMin = 0, plMax = 0, pDamage = 0, bDam = 0, bAcc = 0, plDR = 0, APcost = 0;
    public static int enMin = 0, enMax = 0, eDamage = 0, enDR = 0, enAcc = 0, enAC = 0;
    public static String name = "", action = "", plRace = Stats.race.toString();

    private static int durS = 0, durB = 0, durT = 0, durEP = 0;
    private static boolean plHit = false, enHit = false, offensive = false, itemUse = false;
    private static boolean shield = false, endure = false, bless = false, tiger = false, flash = false;

    public static void CalcRoundDamage()
    {
        try
        {
            plAC = Stats.AC;
            plMin = Stats.wMin;
            plMax = Stats.wMax;
            int temp = 0;
            //Calculates accuracy and damage should a special attack be used
            if (action.equals("Special"))
            {
                CalcSpecialDamage();

                //Though not in the game yet, offensive is still checked
                //to be true for future editions where the martial line
                //shall have support abilities
                if (offensive == true)
                {
                    pDamage = (int) (Math.random()*plMax)+ plMin + bDam;
                    temp = (int) (Math.random()*20) + 1 + bAcc;
                    if (temp + Stats.wAcc - enAC >= 0)
                        plHit = true;
                    else plHit = false;
                }
                else
                {
                    plHit = false;
                    pDamage = 0;
                }
            }
            if (action.equals("Spell"))
            {
                CalcSpellDamage();

                //Makes sure the spell used is offensive, if it
                //is not, all accuracy and damage values are skipped
                if (offensive == true)
                {
                    pDamage = (int) (Math.random()*plMax)+ plMin;
                    temp = (int) (Math.random()*20) + 1 + bAcc;
                    if (temp - enAC >= 0)
                        plHit = true;
                    else plHit = false;
                }
                else
                {
                    plHit = false;
                    pDamage = 0;
                }
            }

            //Calculates effect should an item be used
            if (action.equals("Item"))
            {
                offensive = false;
                itemUse = true;
                CalcItemEffect();
            }

            //Calculates enemy hit/miss chance, as well as damage
            temp = (int) (Math.random()*20) + 1 + enAcc;

            if (temp - plAC >= 0)
                enHit = true;
            else enHit = false;

            eDamage = (int) (Math.random()*enMax)+ enMin;

            //Calculate end of round hit/miss and damage, modified
            //by Damage Resistance (DR)
            if (enHit == true)
            {
                if(eDamage - plDR >= 0)
                    Battle.plHealth -= eDamage - plDR;
                Battle.blank.setText("");
            }

            else Battle.blank.setText("The enemy missed!");

            if (plHit == true && offensive == true)
            {
                if (pDamage - enDR >= 0)
                    Battle.enHealth-= pDamage - enDR;
                Battle.blank5.setText("");
            }
            else if (plHit == false && offensive == true)
                Battle.blank5.setText("You missed!");
            else if (offensive == false)
                Battle.blank5.setText("");

            //Sets offensive to false at the end of the battle calculations
            offensive = false;

            //Informs the user that AP is not enough to preform selected action
            //or subtracts the AP cost from user AP, respectively
            if (APcost > Battle.plAP)
                Battle.blank5.setText("Not enough AP!");
            else Battle.plAP -= APcost;

            //Checks buffs and removes when appropriate
            if (shield == true)
                durS--;
            if (bless == true)
                durB--;
            if (shield == true)
                durS--;
            if (endure == true)
                durEP--;
            if (tiger == true)
                durT--;

            if (durS <= 0 && durEP <=0 || Battle.enHealth <= 0)
            {
                shield = false;
                plDR = 0;
            }
            if (durEP <= 0 && durS <=0 || Battle.enHealth <= 0)
            {
                endure = false;
                plDR = 0;
            }

            if (durT <= 0 || Battle.enHealth <=0)
            {
                tiger = false;
                durT = 0;
            }
            if (durB <= 0 || Battle.enHealth <= 0)
            {
                bless = false;
                durB = 0;
            }
        }
        catch (FileNotFoundException FNFE)
        {
            System.out.print("Couldn't load Specials/Spells file in Data");
        }
    }

    //Method for doing calculations should a spell (magical) ability
    //be used. For 'normal' spells it will look for the spell name in the
    //data file, and load spell stats.
    public static void CalcSpellDamage()
        throws FileNotFoundException
    {
            String temp = "";
            InputStream spellStream = BattleCalc.class.getResourceAsStream("/data/Spells.dat");
            Scanner spellData = new Scanner(spellStream);

            //This long line of if else statements cover all of the 'support' spells used by
            //the player, or those which have a secondary or special effect
            if (name.equals("Heal") || name.equals("Lion's Comfort")|| name.equals("Nature's Cure"))
            {
                offensive = false;
                APcost = 3;
                if (Battle.plAP >= APcost)
                    Battle.plHealth += (int) (Math.random()*14) + 3 + Stats.wisMod + Stats.endMod;
                if (Battle.plHealth > Stats.maxHP)
                    Battle.plHealth -= Battle.plHealth - Stats.maxHP;
            }

            else if (name.equals("Bless"))
            {
                offensive = false;
                APcost = 7;
                if (Battle.plAP >= APcost)
                {
                    durB = 2;
                    bDam += 2 + (Stats.wisMod * 2);
                    bAcc += 21;
                    bless = true;
                }
            }

            else if (name.equals("Shield"))
            {
                offensive = false;
                APcost = 10;
                if (Battle.plAP >= APcost)
                {
                    if (shield == false)
                        plDR += 10 + Stats.wisMod;
                    durS += Stats.intMod/2 + Stats.wisMod;
                    shield = true;
                }
            }

            else if (name.equals("Divine Flash"))
            {
                offensive = true;
                APcost = 15;
                if (Battle.plAP >= APcost)
                {
                    plMin = 10 + Stats.wisMod;
                    plMax = 20 + Stats.intMod;
                    bAcc = Stats.intMod/2 + Stats.wisMod;
                    if (flash == false)
                    {
                        enAcc -= Stats.intMod/2 + Stats.wisMod;
                        flash = true;
                    }
                }
                else offensive = false;
            }

            else if (name.equals("Endure Pain"))
            {
                offensive = false;
                APcost = 5;
                if (Battle.plAP >= APcost)
                {
                    if (endure == false)
                        plDR += 4 + Stats.endMod/2;
                    durEP += 2 + Stats.endMod;
                    endure = true;
                }

            }

            else if (name.equals("Tiger Stance"))
            {
                offensive = false;
                APcost = 10;
                if (Battle.plAP >= APcost && tiger == false)
                {
                    bDam += 4 + Stats.strMod;
                    bAcc += 4 + Stats.strMod;
                }

                durT += 1 + Stats.strMod + Stats.endMod;
                tiger = true;

            }

            else if (name.equals("Flame Strike"))
            {
                offensive = true;
                APcost = 10;
                if (Battle.plAP >= APcost)
                {
                    plMin += 6 + Stats.intMod + Stats.strMod;
                    plMax += 14 + Stats.intMod + Stats.strMod;
                    bAcc += 6 + Stats.intMod + Stats.strMod;
                }
                else offensive = false;
            }

            //'Normal' spells are scanned in the Spells data file, and their stats loaded
            else while (spellData.hasNext())
            {
                temp = spellData.nextLine();

                if (temp.equals(name))
                {
                    offensive = true;
                    plMin = spellData.nextInt() + Stats.intMod;
                    plMax = spellData.nextInt();
                    bAcc = spellData.nextInt() + Stats.wisMod + Stats.intMod;
                    APcost = spellData.nextInt();
                }
            }
            if (Battle.plAP < APcost)
                offensive = false;

            spellData.close();
    }

    //Method for doing calculations should a special attack (martial ability) be used
    public static void CalcSpecialDamage()
        throws FileNotFoundException
    {
        offensive = true;
        String temp = "";
        InputStream specialStream = BattleCalc.class.getResourceAsStream("/data/Specials.dat");
        Scanner specialData = new Scanner(specialStream);

        while (specialData.hasNext())
        {
            temp = specialData.nextLine();

            if (temp.equals(name))
            {
                if (tiger == false && bless == false)
                {
                    bDam = specialData.nextInt() + Stats.strMod;
                    bAcc = specialData.nextInt() + Stats.dexMod + (Stats.strMod/2);
                    APcost = specialData.nextInt();
                }
                else
                {
                    bDam += specialData.nextInt() + Stats.strMod;
                    bAcc += specialData.nextInt() + Stats.dexMod + (Stats.strMod/2);
                    APcost = specialData.nextInt();
                }
            }
        }
        if (Battle.plAP < APcost)
            offensive = false;
        specialData.close();
    }

    //Method for calculating item effects
    public static void CalcItemEffect()
    {
        if (name.equals("HP Potion"))
        {
            Battle.plHealth += (int) (Math.random()*(Stats.maxHP/2)) + 1 + (Stats.maxHP/4);

            if (Battle.plHealth > Stats.maxHP)
                Battle.plHealth -= Battle.plHealth - Stats.maxHP;
        }

        if (name.equals("AP Potion"))
        {
            Battle.plAP += (int)(Math.random()*(Stats.maxAP/2)) + 1 + (Stats.maxAP/4);

            if (Battle.plAP > Stats.maxAP)
                Battle.plAP -= Battle.plAP - Stats.maxAP;
        }

        if(name.equals("HP/AP Potion"))
        {
            Battle.plHealth += (int) (Math.random()*(Stats.maxHP/2)) + 1 + (Stats.maxHP/4);

            if (Battle.plHealth > Stats.maxHP)
                Battle.plHealth -= Battle.plHealth - Stats.maxHP;

            Battle.plAP += (int)(Math.random()*(Stats.maxAP/2)) + 1 + (Stats.maxAP/4);

            if (Battle.plAP > Stats.maxAP)
                Battle.plAP -= Battle.plAP - Stats.maxAP;
        }
    }
}