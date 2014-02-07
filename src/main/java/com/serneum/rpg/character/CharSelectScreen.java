/***********************************************************************
 *Author: Chris Rees and Wilfredo Velasquez
 *Date: 12/1/08
 *File Name: CharSelectScreen.java
 *Purpose: Open up a GUI to select from one of four characters. Choosing
 *an empty slot will start a new game.
***********************************************************************/

package com.serneum.rpg.character;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.serneum.rpg.core.Inventory;
import com.serneum.rpg.core.Overworld;

public class CharSelectScreen extends JFrame
{
    JTextArea char1, char2, char3, char4;
    JButton choose1, choose2, choose3, choose4;
    JButton delete1, delete2, delete3, delete4, quit;
    String temp = "";
    static int line = 0;

    public CharSelectScreen()
    {
        //Set text areas and their sizes
        char1 = new JTextArea(15,20);
        char2 = new JTextArea(15,20);
        char3 = new JTextArea(15,20);
        char4 = new JTextArea(15,20);

        //Make text areas uneditable
        char1.setEditable(false);
        char2.setEditable(false);
        char3.setEditable(false);
        char4.setEditable(false);

        //Create all 4 create buttons. Changed based on data.
        choose1 = new JButton("Create");
        choose2 = new JButton("Create");
        choose3 = new JButton("Create");
        choose4 = new JButton("Create");

        //Create all 4 delete buttons
        delete1 = new JButton("Delete");
        delete2 = new JButton("Delete");
        delete3 = new JButton("Delete");
        delete4 = new JButton("Delete");

        //Create quit button
        quit = new JButton("Quit");

        //Create button handler
        buttonHandler bHandler;
        bHandler = new buttonHandler();
        choose1.addActionListener(bHandler);
        choose2.addActionListener(bHandler);
        choose3.addActionListener(bHandler);
        choose4.addActionListener(bHandler);
        delete1.addActionListener(bHandler);
        delete2.addActionListener(bHandler);
        delete3.addActionListener(bHandler);
        delete4.addActionListener(bHandler);

        //Add quit button handler
        quitHandler qHandler;
        qHandler = new quitHandler();
        quit.addActionListener(qHandler);

        //Pull data from all 4 standardized files
        for(int count = 1; count <= 4; count++)
        {
            InputStream charStream = CharSelectScreen.class.getResourceAsStream("/data/characters/character" +
                count + "/CharSheet.dat");
            Scanner inFile = new Scanner(charStream);

            //Reset the line value to 0 when a new file is being read
            line = 0;

            while(inFile.hasNext())
            {
                if(count == 1)
                {
                    //Adds each line from the file to the text area
                    temp = inFile.nextLine();
                    char1.append(temp + "\n");

                    //Checks if the character is "new" or not by comparing
                    //the first line of the file to the default first line
                    if(line == 0)
                    {
                        //If the lines do not match, change Create button
                        //to Continue
                        if(!temp.equals("Character: ------"))
                            choose1.setText("Continue");
                    }
                }

                if(count == 2)
                {
                    //Adds each line from the file to the text area
                    temp = inFile.nextLine();
                    char2.append(temp + "\n");

                    //Checks if the character is "new" or not by comparing
                    //the first line of the file to the default first line
                    if(line == 0)
                    {
                        //If the lines do not match, change Create button
                        //to Continue
                        if(!temp.equals("Character: ------"))
                            choose2.setText("Continue");
                    }
                }

                if(count == 3)
                {
                    //Adds each line from the file to the text area
                    temp = inFile.nextLine();
                    char3.append(temp + "\n");

                    //Checks if the character is "new" or not by comparing
                    //the first line of the file to the default first line
                    if(line == 0)
                    {
                        //If the lines do not match, change Create button
                        //to Continue
                        if(!temp.equals("Character: ------"))
                            choose3.setText("Continue");
                    }
                }

                if(count == 4)
                {
                    //Adds each line from the file to the text area
                    temp = inFile.nextLine();
                    char4.append(temp + "\n");

                    //Checks if the character is "new" or not by comparing
                    //the first line of the file to the default first line
                    if(line == 0)
                    {
                        //If the lines do not match, change Create button
                        //to Continue
                        if(!temp.equals("Character: ------"))
                            choose4.setText("Continue");
                    }
                }

                line++;
            }

            inFile.close();
        }

        //Create a content pane
        Container charPane = getContentPane();

        //Set layout to GridBagLayout
        charPane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Set constraints and locations of each individual element.
        //If some constraints are to be the same, do not repeat their
        //value inputs.
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 2;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        charPane.add(char1, c);

        c.gridx = 3;
        charPane.add(char2, c);

        c.gridx = 0;
        c.gridy = 4;
        charPane.add(char3, c);

        c.gridx = 3;
        c.gridy = 4;
        charPane.add(char4, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.1;
        c.fill = GridBagConstraints.NONE;
        charPane.add(choose1, c);

        c.gridx = 1;
        charPane.add(delete1, c);

        c.gridx = 3;
        c.gridy = 3;
        charPane.add(choose2, c);

        c.gridx = 4;
        charPane.add(delete2, c);

        c.gridx = 0;
        c.gridy = 6;
        charPane.add(choose3, c);

        c.gridx = 1;
        charPane.add(delete3, c);

        c.gridx = 3;
        c.gridy = 6;
        charPane.add(choose4, c);

        c.gridx = 4;
        charPane.add(delete4, c);

        c.gridx = 2;
        c.gridy = 7;
        c.gridwidth = 1;
        c.gridheight = 1;
        charPane.add(quit, c);

        //Set basics of GUI like size and title.
        setTitle("Character Select");
        setSize(425,675);
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private class buttonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == choose1 && choose1.getText() == "Create")
            {
                Stats.charNum = 1;
                RaceClass gen = new RaceClass();
                dispose();
            }

            if(e.getSource() == choose1 && choose1.getText() == "Continue")
            {
                try
                {
                    Stats.charNum = 1;
                    Stats.getGear();
                    Inventory.readIn();
                    Stats.statGen();
                    Stats.modGen();
                    Stats.printStats();
                }

                catch(FileNotFoundException FNFE)
                {
                    //Swallow
                }

                Overworld map = new Overworld();
                dispose();
            }

            if(e.getSource() == choose2 && choose2.getText() == "Create")
            {
                Stats.charNum = 2;
                RaceClass gen = new RaceClass();
                dispose();
            }

            if(e.getSource() == choose2 && choose2.getText() == "Continue")
            {
                try
                {
                    Stats.charNum = 2;
                    Stats.getGear();
                    Inventory.readIn();
                    Stats.statGen();
                    Stats.modGen();
                    Stats.printStats();
                }

                catch(FileNotFoundException FNFE)
                {
                    //Swallow
                }

                Overworld map = new Overworld();
                dispose();
            }

            if(e.getSource() == choose3 && choose3.getText() == "Create")
            {
                Stats.charNum = 3;
                RaceClass gen = new RaceClass();
                dispose();
            }

            if(e.getSource() == choose3 && choose3.getText() == "Continue")
            {
                try
                {
                    Stats.charNum = 3;
                    Stats.getGear();
                    Inventory.readIn();
                    Stats.statGen();
                    Stats.modGen();
                    Stats.printStats();
                }

                catch(FileNotFoundException FNFE)
                {
                    //Swallow
                }

                Overworld map = new Overworld();
                dispose();
            }

            if(e.getSource() == choose4 && choose4.getText() == "Create")
            {
                Stats.charNum = 4;
                RaceClass gen = new RaceClass();
                dispose();
            }

            if(e.getSource() == choose4 && choose4.getText() == "Continue")
            {
                try
                {
                    Stats.charNum = 4;
                    Stats.getGear();
                    Inventory.readIn();
                    Stats.statGen();
                    Stats.modGen();
                    Stats.printStats();
                }

                catch(FileNotFoundException FNFE)
                {
                    //Swallow
                }

                Overworld map = new Overworld();
                dispose();
            }

            if(e.getSource() == delete1 || e.getSource() == delete2 || e.getSource() == delete3 || e.getSource() == delete4)
            {
                //Placeholder frame
                Frame frame = new Frame();

                //Define button text
                Object[] options = {"Yes, delete it", "No! Don't do it!"};

                //Present user with option to delete character
                int delete = JOptionPane.showOptionDialog(frame, "Are you sure you want to delete this character?\n(This will reload the character list)",
                    "Character Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                //If user chooses to delete, checks which character is being deleted
                //and then sets charNum accordingly. printBlank() is called to make
                //the appropriate character file empty again
                if(delete == JOptionPane.YES_OPTION)
                {
                    if(e.getSource() == delete1)
                    {
                        Stats.charNum = 1;
                    }

                    if(e.getSource() == delete2)
                    {
                        Stats.charNum = 2;
                    }

                    if(e.getSource() == delete3)
                    {
                        Stats.charNum = 3;
                    }

                    if(e.getSource() == delete4)
                    {
                        Stats.charNum = 4;
                    }

                    //Print out a default character due to "deletion"
                    try
                    {
                        Stats.printBlank();
                    }
                    catch(FileNotFoundException FNFE)
                    {
                        //Swallow
                    }

                    //Reloads the character list after deletion by creating a
                    //new instance of the character selection screen.
                    CharSelectScreen newScreen = new CharSelectScreen();
                    dispose();
                }
            }
        }
    }

    private class quitHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //Placeholder frame
            Frame frame = new Frame();

            //Define button text
            Object[] options = {"Yes, I'm sure", "No, I don't want to"};

            //Present user with option to quit the game
            int delete = JOptionPane.showOptionDialog(frame, "Are you sure you want to quit the game?",
                "Quit Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if(delete == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        }
    }
}