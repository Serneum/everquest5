/***********************************************************************
 *Author: Chris Rees and Wilfredo Velasquez
 *Date: 10/30/08
 *File Name: SpecialsList.java
 *Purpose: Determines which skills are granted for the player to use at
 *their current level and class. These skills are then assigned to the
 *buttons in the Battle menu. If a character is not high enough level to
 *unlock the skill, the button is left blank
***********************************************************************/

package com.serneum.rpg.character;

import com.serneum.rpg.battle.Battle;

public class SpecialsList
{
    //Checks  player level and class and assigns skills accordingly
    public static void LoadMenu(String proff, String type)
    {
        if (proff.equals("Cleric"))
        {
            if (type.equals("martial"))
            {
                ClericMartialMenu();
            }
            else if (type.equals("magical"))
            {
                ClericSpellMenu();
            }
            else System.out.print("Cleric Problem");
        }

        if (proff.equals("Fighter"))
        {
            if (type.equals("martial"))
            {
                FighterMartialMenu();
            }
            else if (type.equals("magical"))
            {
                FighterSpellMenu();
            }
            else System.out.print("Fighter Problem");
        }
        if (proff.equals("Mage"))
        {
            if (type.equals("martial"))
            {
                MageMartialMenu();
            }
            else if (type.equals("magical"))
            {
                MageSpellMenu();
            }
            else System.out.print("Mage Problem");
        }

           if (proff.equals("Ranger"))
        {
            if (type.equals("martial"))
            {
                RangerMartialMenu();
            }
            else if (type.equals("magical"))
            {
                RangerSpellMenu();
            }
            else System.out.print("Ranger Problem");
        }
    }

    //Cleric skills

    private static void ClericMartialMenu()
    {
            if (Stats.level >=8)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("Smite");
                Battle.item.setText("Blessed Strike");
                Battle.flee.setText("Holy Cross");
            }

            else if (Stats.level >=6)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("Smite");
                Battle.item.setText("Blessed Strike");
                Battle.flee.setText("");
            }

            else if (Stats.level >=4)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("Smite");
                Battle.item.setText("");
                Battle.flee.setText("");
            }

            else if (Stats.level >=1)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("");
                Battle.item.setText("");
                Battle.flee.setText("");
            }
    }
    public static void ClericSpellMenu()
    {
            if (Stats.level >=7)
            {
                Battle.martial.setText("Heal");
                Battle.magical.setText("Bless");
                Battle.item.setText("Shield");
                Battle.flee.setText("Divine Flash");
            }

            else if (Stats.level >=5)
            {
                Battle.martial.setText("Heal");
                Battle.magical.setText("Bless");
                Battle.item.setText("Shield");
                Battle.flee.setText("");
            }

            else if (Stats.level >=3)
            {
                Battle.martial.setText("Heal");
                Battle.magical.setText("Bless");
                Battle.item.setText("");
                Battle.flee.setText("");
            }

            else if (Stats.level >=1)
            {
                Battle.martial.setText("Heal");
                Battle.magical.setText("");
                Battle.item.setText("");
                Battle.flee.setText("");
            }
    }

    // Fighter skills

    private static void FighterMartialMenu()
    {
            if (Stats.level >=7)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("Power Attack");
                Battle.item.setText("Cleave");
                Battle.flee.setText("Eviscerate");
            }

            else if (Stats.level >=5)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("Power Attack");
                Battle.item.setText("Cleave");
                Battle.flee.setText("");
            }

            else if (Stats.level >=3)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("Power Attack");
                Battle.item.setText("");
                Battle.flee.setText("");
            }

            else if (Stats.level >=1)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("");
                Battle.item.setText("");
                Battle.flee.setText("");
            }
    }

    private static void FighterSpellMenu()
    {
        /*
             if (Stats.level >=8)
            {
                Battle.martial.setText("Endure Pain");
                Battle.magical.setText("Lion's Comfort");
                Battle.item.setText("Tiger Stance");
                Battle.flee.setText("Warcry"); Stuns enemy
            }
        */
            if (Stats.level >=6)
            {
                Battle.martial.setText("Endure Pain");
                Battle.magical.setText("Lion's Comfort");
                Battle.item.setText("Tiger Stance");
                Battle.flee.setText("");
            }

            else if (Stats.level >=4)
            {
                Battle.martial.setText("Endure Pain");
                Battle.magical.setText("Lion's Comfort");
                Battle.item.setText("");
                Battle.flee.setText("");
            }

            else if (Stats.level >=1)
            {
                Battle.martial.setText("Endure Pain");
                Battle.magical.setText("");
                Battle.item.setText("");
                Battle.flee.setText("");
            }
    }

    //Mage skills

    private static void MageMartialMenu()
    {
            if (Stats.level >=8)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("Frozen Strike");
                Battle.item.setText("Conjure Lightning");
                Battle.flee.setText("Amplify");
            }

            else if (Stats.level >=6)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("Frozen Strike");
                Battle.item.setText("Conjure Lightning");
                Battle.flee.setText("");
            }

            else if (Stats.level >=4)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("Frozen Strike");
                Battle.item.setText("");
                Battle.flee.setText("");
            }

            else if (Stats.level >=1)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("");
                Battle.item.setText("");
                Battle.flee.setText("");
            }
    }

    private static void MageSpellMenu()
    {
            if (Stats.level >=7)
            {
                Battle.martial.setText("Shock");
                Battle.magical.setText("Blades of Wind");
                Battle.item.setText("Maelstrom");
                Battle.flee.setText("Pure Blast");
            }

            else if (Stats.level >=5)
            {
                Battle.martial.setText("Shock");
                Battle.magical.setText("Blades of Wind");
                Battle.item.setText("Maelstrom");
                Battle.flee.setText("");
            }

            else if (Stats.level >=3)
            {
                Battle.martial.setText("Shock");
                Battle.magical.setText("Blades of Wind");
                Battle.item.setText("");
                Battle.flee.setText("");
            }

            else if (Stats.level >=1)
            {
                Battle.martial.setText("Shock");
                Battle.magical.setText("");
                Battle.item.setText("");
                Battle.flee.setText("");
            }
    }

    //Ranger skills

    private static void RangerMartialMenu()
    {
            if (Stats.level >= 7)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("Double Strike");
                Battle.item.setText("Whirling Strike");
                Battle.flee.setText("Flurry of Blows");
            }

            else if (Stats.level >= 5)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("Double Strike");
                Battle.item.setText("Whirling Strike");
                Battle.flee.setText("");
            }

            else if (Stats.level >= 3)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("Double Strike");
                Battle.item.setText("");
                Battle.flee.setText("");
            }

            else if (Stats.level >= 1)
            {
                Battle.martial.setText("Attack");
                Battle.magical.setText("");
                Battle.item.setText("");
                Battle.flee.setText("");
            }
    }

    private static void RangerSpellMenu()
    {
        /*
            if (Stats.level >=8)
            {
                Battle.martial.setText("Nature's Unguent");
                Battle.magical.setText("Flame Strike");
                Battle.item.setText("Grasping Vines");
                Battle.flee.setText("Keen Stride"); Double actions per round
            }
        */
            if (Stats.level >=6)
            {
                Battle.martial.setText("Nature's Cure");
                Battle.magical.setText("Flame Strike");
                Battle.item.setText("Grasping Vines");
                Battle.flee.setText("");
            }

            else if (Stats.level >=4)
            {
                Battle.martial.setText("Nature's Cure");
                Battle.magical.setText("Flame Strike");
                Battle.item.setText("");
                Battle.flee.setText("");
            }

            else if (Stats.level >=1)
            {
                Battle.martial.setText("Nature's Cure");
                Battle.magical.setText("");
                Battle.item.setText("");
                Battle.flee.setText("");
            }
    }
}