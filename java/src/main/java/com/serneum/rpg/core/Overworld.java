/***********************************************************************
 *Author: Chris Rees and Wilfredo Velasquez
 *Date: 10/27/08
 *File Name: Overworld.java
 *Purpose: A basic 3x3 map with radio buttons and "Inventory" and "Move"
 *buttons. Each time a player moves, there is a chance to engage in battle.
***********************************************************************/

package com.serneum.rpg.core;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import com.serneum.rpg.battle.Battle;
import com.serneum.rpg.battle.BattleData;
import com.serneum.rpg.character.CharSelectScreen;
import com.serneum.rpg.character.CharSheet;
import com.serneum.rpg.character.Stats;

public class Overworld extends JFrame
{
    Random generator = new Random();
    public int randNum = 0;
    public String tempName = "";

    JLabel blank1, blank2, blank3, blank4, blank5, overworld;

    JButton move, inventory, charSheet, town, eButton;
    JRadioButton NW, N, NE, E, SE, S, SW, W, center;

    private static boolean moved = false;

    public Overworld()
    {
        //Define labels
        blank1 = new JLabel("", SwingConstants.LEFT);
        blank2 = new JLabel("", SwingConstants.LEFT);
        blank3 = new JLabel("", SwingConstants.LEFT);
        blank4 = new JLabel("", SwingConstants.LEFT);
        overworld = new JLabel("Overworld", SwingConstants.CENTER);

        //Declare what the buttons display
        NW = new JRadioButton("NW");
        N = new JRadioButton("N");
        NE = new JRadioButton("NE");
        E = new JRadioButton("E");
        SE = new JRadioButton("SE");
        S = new JRadioButton("S");
        SW = new JRadioButton("SW");
        W = new JRadioButton("W");
        center = new JRadioButton("");

        //Add Cardinal directions to a button group
        ButtonGroup directions = new ButtonGroup();
        directions.add(NW);
        directions.add(N);
        directions.add(NE);
        directions.add(E);
        directions.add(SE);
        directions.add(S);
        directions.add(SW);
        directions.add(W);
        directions.add(center);

        //Set Handlers
        mButtonHandler mbHandler;
        move = new JButton("Move");
        mbHandler = new mButtonHandler();
        move.addActionListener(mbHandler);

        iButtonHandler ibHandler;
        inventory = new JButton("Inventory");
        ibHandler = new iButtonHandler();
        inventory.addActionListener(ibHandler);

        ButtonHandler bHandler;
        charSheet = new JButton("Char Sheet");
        bHandler = new ButtonHandler();
        charSheet.addActionListener(bHandler);
        town = new JButton("Town");
        town.addActionListener(bHandler);

        eButtonHandler ebHandler;
        eButton = new JButton("Exit");
        ebHandler = new eButtonHandler();
        eButton.addActionListener(ebHandler);

        //Set Listeners
        listenHandler lHandler;
        lHandler = new listenHandler();
        NW.addItemListener(lHandler);
        N.addItemListener(lHandler);
        NE.addItemListener(lHandler);
        W.addItemListener(lHandler);
        center.addItemListener(lHandler);
        E.addItemListener(lHandler);
        SW.addItemListener(lHandler);
        S.addItemListener(lHandler);
        SE.addItemListener(lHandler);

        Container map = getContentPane();

        //Set layout
        map.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Place all buttons/labels
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 2;
        c.weightx = 0.2;
        c.weighty = 0.2;
        c.fill = GridBagConstraints.BOTH;
        map.add(move, c);
        c.gridy = 1;
        map.add(town, c);
        c.gridy = 2;
        map.add(inventory, c);
        c.gridy = 3;
        map.add(charSheet, c);
        c.gridy = 4;
        map.add(eButton, c);
        c.gridy = 0;
        c.gridx = 2;
        c.gridwidth = 1;
        map.add(blank1, c);
        c.gridx = 3;
        map.add(overworld, c);
        c.gridx = 4;
        map.add(blank2, c);
        c.gridy = 1;
        c.gridx = 2;
        map.add(NW, c);
        c.gridx = 3;
        map.add(N, c);
        c.gridx = 4;
        map.add(NE, c);
        c.gridy = 2;
        c.gridx = 2;
        map.add(W, c);
        c.gridx = 3;
        map.add(center, c);
        c.gridx = 4;
        map.add(E, c);
        c.gridy = 3;
        c.gridx = 2;
        map.add(SW, c);
        c.gridx = 3;
        map.add(S, c);
        c.gridx = 4;
        map.add(SE, c);

        //Set options of the window
        setTitle("Overworld Map");
        setSize(400,200);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    private class listenHandler implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            //Do a boolean initialized to false called moved, it's assigned true if any direction
            //button is clicked, before moving. Make the following statement check to make sure
            //moved == true before calculating chances for an encounter, that'll solve the
            //'still' encounter thing
            if(e.getSource() == NW || e.getSource() == N || e.getSource() == NE ||
                e.getSource() == W || e.getSource() == center || e.getSource() == E ||
                    e.getSource() == SW || e.getSource() == S || e.getSource() == SE)
                    {
                        moved = true;
                    }
        }
    }

    private class mButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //When a player moves, generate a number between 1 and 2 (for now)
            //which determines if a player encounters a mob or not. If they roll 1,
            //Battle() is called which creates a mob and starts the fight.
            randNum = (generator.nextInt(2) + 1);

            //If the random number is 1 and the player HP is > 0, display a message to let the player
            //know that battle has begun. Also resets moved to false so that the player has to move if
            //they want to get into another fight
            if(randNum == 1 && Stats.hp > 0 && moved == true)
            {
                try
                {
                    //Stores character name to send it to battle
                    tempName = Stats.charName.toString();
                    //Sets moved to false when entering battle
                    moved = false;
                    //Collects mob data
                    BattleData.collectEnemyData();

                    //Lines 196-210 are used to create a frame that tells the user they have entered combat
                      Frame frame = new Frame();
                      //Define button text
                      Object[] options = {"Let's do this"};
                      //Present user with encounter message
                      int fight = JOptionPane.showOptionDialog(frame, "You have encountered " + Battle.enName + " which lunges at you with " + Battle.enWeapon + "",
                          "Random Encounter", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                    //When user clicks OK button, start battle
                      if(fight == JOptionPane.OK_OPTION)
                      {
                          Battle battle = new Battle(tempName);
                        dispose();
                      }
                }

                catch(FileNotFoundException FNFE)
                {

                }
            }

            if(Stats.hp <= 0)
            {
                JOptionPane.showMessageDialog(null, "You are severely wounded and must heal before moving again.", "Unable to Fight", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class iButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
                try
                {
                    if (InvenScreen.invOpen == false)
                    {
                        InvenScreen.invOpen = true;
                        InvenScreen inv = new InvenScreen();
                    }
                }
                catch(FileNotFoundException FNFE)
                {
                }
        }
    }

    private class ButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == charSheet)
            {
                if (CharSheet.charOpen == false)
                {
                    CharSheet.charOpen = true;
                    CharSheet sheet = new CharSheet();
                }
            }

            if(e.getSource() == town)
            {
                Town.location = "Town";
                Town town = new Town();
                moved = false;
                dispose();
            }
        }
    }

    private class eButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //Placeholder frame
              Frame frame = new Frame();

              //Define button text
              Object[] options = {"Yes, I'm sure", "No, I don't want to"};

              //Present user with option to exit game
              int exit = JOptionPane.showOptionDialog(frame, "Are you sure you want to exit to the character screen?",
                  "Exit Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

              if(exit == JOptionPane.YES_OPTION)
              {
                  try
                  {
                      Stats.printStats();
                      Stats.printGear();
                      Inventory.printOut();
                  }
                  catch(FileNotFoundException FNFE)
                  {
                  }

                CharSelectScreen reload = new CharSelectScreen();
                dispose();
              }
        }
    }
}