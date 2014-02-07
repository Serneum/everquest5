/***********************************************************************
 *Author: Chris Rees and Wilfredo Velasquez
 *Date: 10/6/08
 *File Name: RaceClass.java
 *Purpose: Take in a characters name, a race and class choice, and
 *determine starting stats. The stats are printed to a file that
 *is named according to the character's name for easier retrieval
 *later on in the program as a whole.
***********************************************************************/

package com.serneum.rpg.character;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.serneum.rpg.core.Overworld;

public class RaceClass extends JFrame
{
    JLabel raceSelect, classSelect, blankLabel1, blankLabel2, blankLabel3, blankLabel4,
                blankLabel5, blankLabel6, blankLabel7, blankLabel8, nameLabel;
    //Declare all buttons
    JButton cButton, xButton;
    JRadioButton orc, elf, dwarf, human;
    JRadioButton mage, ranger, cleric, fighter;
    ButtonGroup raceGroup, classGroup;
    JTextField nameEntry = new JTextField(20);
    //Declare storage variables for use with Buttons
    String tempName = "";

    public RaceClass()
    {
        //Set text of main labels
        raceSelect = new JLabel("Choose one of the races to the right.", SwingConstants.LEFT);
        classSelect = new JLabel("Choose one of the classes to the right.", SwingConstants.LEFT);
        nameLabel = new JLabel("Enter your character's name.", SwingConstants.LEFT);

        //Declare all of the place-holding labels (for alignment)
        blankLabel1 = new JLabel("", SwingConstants.LEFT);
        blankLabel2 = new JLabel("", SwingConstants.LEFT);
        blankLabel3 = new JLabel("", SwingConstants.LEFT);
        blankLabel4 = new JLabel("", SwingConstants.LEFT);
        blankLabel5 = new JLabel("", SwingConstants.LEFT);
        blankLabel6 = new JLabel("", SwingConstants.LEFT);
        blankLabel7 = new JLabel("", SwingConstants.LEFT);
        blankLabel8 = new JLabel("", SwingConstants.LEFT);

        //Create radio buttons for races and classes
        orc = new JRadioButton("Orc");
        elf = new JRadioButton("Elf");
        dwarf = new JRadioButton("Dwarf");
        human = new JRadioButton("Human");

        mage = new JRadioButton("Mage");
        ranger = new JRadioButton("Ranger");
        cleric = new JRadioButton("Cleric");
        fighter = new JRadioButton("Fighter");

        //Add Race/Class buttons to appropriate groups
        raceGroup = new ButtonGroup();
        raceGroup.add(orc);
        raceGroup.add(elf);
        raceGroup.add(dwarf);
        raceGroup.add(human);

        classGroup = new ButtonGroup();
        classGroup.add(mage);
        classGroup.add(ranger);
        classGroup.add(cleric);
        classGroup.add(fighter);

        //Set Handlers
        cButtonHandler cbHandler;
        cButton = new JButton("Confirm");
        cbHandler = new cButtonHandler();
        cButton.addActionListener(cbHandler);

        xButtonHandler xbHandler;
        xButton = new JButton("Exit");
        xbHandler = new xButtonHandler();
        xButton.addActionListener(xbHandler);

        //Set Listeners
        listenHandler lHandler;
        lHandler = new listenHandler();
        orc.addItemListener(lHandler);
        human.addItemListener(lHandler);
        elf.addItemListener(lHandler);
        dwarf.addItemListener(lHandler);
        mage.addItemListener(lHandler);
        ranger.addItemListener(lHandler);
        cleric.addItemListener(lHandler);
        fighter.addItemListener(lHandler);


        //Set container
        Container windowPane = getContentPane();

        //Set layout
        windowPane.setLayout(new GridLayout (0,2));

        //Place all buttons/labels
        windowPane.add(nameLabel);
        windowPane.add(nameEntry);
        windowPane.add(raceSelect);
        windowPane.add(orc);
        windowPane.add(blankLabel1);
        windowPane.add(elf);
        windowPane.add(blankLabel2);
        windowPane.add(dwarf);
        windowPane.add(blankLabel3);
        windowPane.add(human);
        windowPane.add(classSelect);
        windowPane.add(cleric);
        windowPane.add(blankLabel4);
        windowPane.add(fighter);
        windowPane.add(blankLabel5);
        windowPane.add(mage);
        windowPane.add(blankLabel6);
        windowPane.add(ranger);
        windowPane.add(blankLabel7);
        windowPane.add(blankLabel8);
        windowPane.add(cButton);
        windowPane.add(xButton);

        //Set options of the window
        setTitle("Character Info");
        setSize(500,300);
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    }

    private class listenHandler implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            //Set global variable Race
            if(e.getSource() == orc)
                Stats.setRace("Orc");
            if(e.getSource() == elf)
                Stats.setRace("Elf");
            if(e.getSource() == dwarf)
                Stats.setRace("Dwarf");
            if(e.getSource() == human)
                Stats.setRace("Human");
            //Set global variable classChoice
            if(e.getSource() == cleric)
                Stats.setClass("Cleric");
            if(e.getSource() == fighter)
                Stats.setClass("Fighter");
            if(e.getSource() == mage)
                Stats.setClass("Mage");
            if(e.getSource() == ranger)
                Stats.setClass("Ranger");
        }
    }

    private class cButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            tempName = nameEntry.getText();

            //Verifies that the user has entered a Name, Race, and Class before going to the Overworld screen
            if (!(Stats.race.toString().equals("") || Stats.classChoice.toString().equals("") || tempName.equals("")))
            {
                //Retrieve text and send it to the charSheet function
                //Data sent will be the character's name, race, and class
                Stats.setCharName(tempName);

                Stats.statInitialGen();

                Overworld map = new Overworld();
                dispose();
            }

            else if(Stats.race.toString().equals("") || Stats.classChoice.toString().equals("") || tempName.equals(""))
            {
                //Placeholder frame
                  Frame frame = new Frame();

                  //Define button text
                  Object[] options = {"Ok"};

                  //Present user with option to exit game
                  int enter = JOptionPane.showOptionDialog(frame, "You did not fill in all of the fields.",
                      "Incomplete Entry", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

                  if(enter == JOptionPane.YES_OPTION)
                  {
                }
            }
        }
    }

    private class xButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //Causes an error-checking message to appear when exit is clicked
            //Placeholder frame
                  Frame frame = new Frame();

                  //Define button text
                  Object[] options = {"Yes I am", "No I don't want to"};

                  //Present user with option to exit game
                  int enter = JOptionPane.showOptionDialog(frame, "Are you sure you want to exit?",
                      "Exit Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                  if(enter == JOptionPane.YES_OPTION)
                  {
                      System.exit(0);
                }
        }
    }
/*
    public static void main(String[] args)
    {
        RaceClass race = new RaceClass();
    }
*/
}