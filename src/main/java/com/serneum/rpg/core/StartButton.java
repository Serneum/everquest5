/***********************************************************************
 *Author: Chris Rees and Wilfredo Velasquez
 *Date: 12/1/08
 *File Name: StartButton.java
 *Purpose: Present the user with a button to start the game.
 *This is essentialy the driver program for the entire game
 *
 *All code that is 'commented out' is either for future implementation or
 *for testing purposes to assure data is being generated properly. These
 *'test comments' are usually 'System.out.print'
***********************************************************************/

package com.serneum.rpg.core;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import com.serneum.rpg.character.CharSelectScreen;
import com.serneum.rpg.character.CharSheet;

public class StartButton extends JFrame
{
    JButton BMStart;

    public StartButton() throws FileNotFoundException
    {
        //Retrieve image and insert it into a JButton
        URL bmUrl = CharSheet.class.getResource("/images/BMButton.png");
        ImageIcon BM = new ImageIcon(bmUrl);
        BMStart = new JButton(BM);

        //Define button listener
        buttonHandler bHandler;
        bHandler = new buttonHandler();
        BMStart.addActionListener(bHandler);

        //Define pane and add elements
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(0,1));
        pane.add(BMStart);

        //Set defaults
        setTitle("Welcome to Everquest5");
        setSize(420,300);
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private class buttonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            CharSelectScreen select = new CharSelectScreen();
            dispose();
        }
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        StartButton run = new StartButton();
    }
}