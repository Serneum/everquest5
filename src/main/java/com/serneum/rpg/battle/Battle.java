/***********************************************************************
 *Author: Chris Rees and Wilfredo Velasquez
 *Date: 10/27/08
 *File Name: Battle.java
 *Purpose: A battle menu connected to a variety of other classes for
 *determining all actions taken during the battle sequence
***********************************************************************/

package com.serneum.rpg.battle;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.serneum.rpg.character.SpecialsList;
import com.serneum.rpg.character.Stats;
import com.serneum.rpg.core.InvenScreen;
import com.serneum.rpg.core.Overworld;

public class Battle extends JFrame
{
    private static final long serialVersionUID = 1L;

    public static JLabel playerHealth, playerName, enemyHealth, enemyName, playerAP, turnLabel,
        blank, blank2, blank3, blank4, blank5;

    public static JButton martial, magical, item, flee, back, scrollup, scrolldown, eholder;

    private final actionHandler aHandler;

    private static final int WIDTH = 420;
    private static final int HEIGHT = 315;

    private static int turnCount = 1;
    public static int plHealth = Stats.hp, plMaxHealth = 0, plMaxAP = 0, plAP = Stats.AP;
    public static int enHealth = 10;
    public static String plName = "", plClass = "", enName = "", enWeapon = "", enDeath = "";

    private static boolean  first = true;

    public Battle(String plName) throws FileNotFoundException
    {
        //Constructor loads the most recent character status, and assigns enemy data to labels
        //to be used in the battle display. Also informs the inventory screen that battle
        //is active, in order for it to change its action types accordingly
        Stats.modGen();
        InvenScreen.battOpen = true;

        plClass = Stats.classChoice.toString();
        plHealth = Stats.hp;
        plAP = Stats.AP;

        playerHealth = new JLabel("Hp: " + plHealth, SwingConstants.CENTER);
        playerName = new JLabel("" + plName, SwingConstants.CENTER);

        enemyHealth = new JLabel("Hp: " + enHealth, SwingConstants.CENTER);
        enemyName = new JLabel("" + enName, SwingConstants.CENTER);

        blank = new JLabel("", SwingConstants.CENTER);
        blank2 = new JLabel("", SwingConstants.CENTER);
        blank3 = new JLabel("", SwingConstants.CENTER);
        blank4 = new JLabel("", SwingConstants.CENTER);
        blank5 = new JLabel("", SwingConstants.CENTER);

        turnLabel = new JLabel("Turn: " + turnCount, SwingConstants.CENTER);

        //Single button handler format used for multiple options
        //with less scripting and generating
        martial = new JButton("Martial");
        aHandler = new actionHandler();
        martial.addActionListener(aHandler);

        magical = new JButton("Magical");
        magical.addActionListener(aHandler);

        back = new JButton("Back");
        back.addActionListener(aHandler);

        playerAP = new JLabel("Ap: " + plAP, SwingConstants.CENTER);

        scrollup = new JButton("Scroll Up");
        scrolldown = new JButton("Scroll Down");

        item = new JButton("Item");
        item.addActionListener(aHandler);
        flee = new JButton("Flee");
        flee.addActionListener(aHandler);

        setTitle("Battle");

        Container pane = getContentPane();

            pane.setLayout(new GridLayout(6,3));

            pane.add(enemyName);
            pane.add(enemyHealth);
            pane.add(blank);
            pane.add(blank2);
            pane.add(blank3);
            pane.add(blank4);
            pane.add(blank5);
            pane.add(playerHealth);
            pane.add(playerName);
            pane.add(playerAP);
            pane.add(martial);
            pane.add(magical);
            pane.add(turnLabel);
            pane.add(item);
            pane.add(flee);
            pane.add(back);
            pane.add(scrollup);
            pane.add(scrolldown);

        setSize (WIDTH, HEIGHT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    private class actionHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //Verifies health of both participants is above zero before
            //allowing action. Should health be zero or bellow, none
            //of the buttons except "back" will function
            if (enHealth > 0 && plHealth > 0)
            {
                //Determines if the user is in the original menu and
                //defines the button actions should that be the case.
                //The boolean variable "first" is set to true should
                //the user be in the 'first' menu, and false if the
                //user is located in one of the ability sub-menu's
                if (first == true)
                {
                    if (e.getSource() == martial)
                    {
                        SpecialsList.LoadMenu(plClass, "martial");
                        BattleCalc.action = "Special";
                        first = false;
                    }
                    if (e.getSource() == magical)
                    {
                        SpecialsList.LoadMenu(plClass, "magical");
                        BattleCalc.action = "Spell";
                        first = false;
                    }

                    //If the user wishes to use an item, the item menu
                    //opens, and informs BattleCalc of the action taken
                    //for the turn as "Item"
                    if (e.getSource() == item)
                    {
                        try
                        {
                            if (InvenScreen.invOpen == false)
                            {
                                BattleCalc.action = "Item";
                                InvenScreen inv = new InvenScreen();
                                InvenScreen.invOpen = true;
                            }
                        }
                        catch(FileNotFoundException FNFE)
                        {
                        }
                    }
                    if (e.getSource() == flee)
                    {
                        //Should user attempt to flee, their action for the turn is set as
                        //"None" in the case that they fail their flight'o'foot
                        BattleCalc.action = "None";

                        //Formula for determining flee chance, more Dexterity and Higher level
                        //warant a higher flee chance, weakened enemies are also easier to escape
                        int temp = (int) (Math.random()*20) + 1 + (Stats.level * Stats.dexMod);
                        if (temp > enHealth)
                        {
                            //Placeholder frame
                              Frame frame = new Frame();

                              //Define button text
                              Object[] option = {"Run!"};

                              //Present user with window informing him of his successful flee
                              //before going to the map screen
                              int fof = JOptionPane.showOptionDialog(frame, "You successfully flee from battle!",
                                  "Escape", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);

                            if(fof == JOptionPane.YES_OPTION)
                              {
                                  Overworld map = new Overworld();
                                  InvenScreen.battOpen = false;
                                dispose();
                            }
                            else;
                        }

                        else
                        {
                            blank3.setText("You fail to flee!");
                            endTurn();
                        }
                    }
                }

                //If the user has gone into one of the ability sub-menu's (Martial or Magical)
                //the buttons are now used to display each ability as an option. The button
                //also verifies that it has an ability stored within it before it continues
                //the action
                else
                {
                    if (e.getSource() == martial)
                    {
                        BattleCalc.name = martial.getText();
                        if (!BattleCalc.name.equals(""))
                            endTurn();
                    }
                    if (e.getSource() == magical)
                    {    BattleCalc.name = magical.getText();
                        if (!BattleCalc.name.equals(""))
                            endTurn();
                    }
                    if (e.getSource() == item)
                    {
                        BattleCalc.name = item.getText();
                        if (!BattleCalc.name.equals(""))
                            endTurn();
                    }
                    if (e.getSource() == flee)
                    {
                        BattleCalc.name = flee.getText();
                        if (!BattleCalc.name.equals(""))
                            endTurn();
                    }
                }
            }

            //Back button calls to reset the buttons to their first state
            //during battle, should a user re-think his action.
            //Also functions as a return-to-overworld button should the user
            //win or lose the battle
            if (e.getSource() == back)
            {
                resetButtons();
                if (enHealth <= 0 || plHealth <= 0)
                {
                    turnCount = 1;
                    Overworld map = new Overworld();
                    dispose();
                }
            }
        }
    }

    //Method for reseting the Battle menu buttons and the
    //variable "first", thereby returning the button function
    //to its menu purpose
    private static void resetButtons()
    {
        martial.setText("Martial");
        magical.setText("Magical");
        item.setText("Item");
        flee.setText("Flee");
        BattleCalc.action = "";
        first = true;
    }

    //Method for ending the turn. It verifies that both character's
    //health is above 0 before Battle calculations in order to increase
    //turn count, and after calculations in order to terminate battle
    //as necessary. It calls upon BattleCalc in order to do all
    //calculations, then changes labels according to the new status
    //as well as adjusting character status in the outside character file
    //and finally returns buttons to their first state
    public static void endTurn()
    {
        try
        {
            if (enHealth > 0 && plHealth > 0)
                turnCount++;

            BattleCalc.CalcRoundDamage();

            plAP++;
            if(plAP > Stats.maxAP)
                plAP = Stats.maxAP;

            playerHealth.setText("Hp: " + plHealth);
            playerAP.setText("Ap: " + plAP);
            enemyHealth.setText("Hp: " + enHealth);
            turnLabel.setText("Turn: " + turnCount);
            Stats.hp = plHealth;
            Stats.AP = plAP;
            Stats.printStats();
            resetButtons();

            //Actions taken should the player be defeated
            if (plHealth <= 0)
            {
                playerHealth.setText("DEAD");
                blank3.setText("You're dead..");
                Stats.modGen();
                Stats.hp = 0;
                Stats.AP = 0;
                Stats.printStats();
            }

            //Actions taken should the enemy be defeated
            else if (enHealth <= 0)
            {
                enemyHealth.setText("DEAD");
                blank3.setText("You win!");
                BattleCalc.enMin = 0;
                BattleCalc.enMax = 0;
                Stats.modGen();
                Stats.hp = plHealth;
                Stats.AP = plAP;
                Battle.blank.setText("");
                Battle.blank5.setText("");
                Stats.EXP += Stats.expGain;
                Stats.printStats();
                bWin();
                InvenScreen.battOpen = false;
            }
        }
        catch(FileNotFoundException FNFE)
        {
            System.out.print("Error processing Stats.modGen or Stats.printStats");
        }
    }

   //JOptionPane message showing exp gained upon battle success
    private static void bWin() throws FileNotFoundException
    {
        if(Stats.expGain < 0)
            Stats.expGain = 0;

        JOptionPane.showMessageDialog(null, "" + Battle.enDeath + "\n\nYou gained " + Stats.expGain + " EXP.",
            "Battle Won", JOptionPane.INFORMATION_MESSAGE);

        Stats.levelUp();
    }

  /*
  public static void main(String[] args)
    {
          Battle menu = new Battle();
    }
*/
}