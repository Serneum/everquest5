/***********************************************************************
 *Author: Chris Rees and Wilfredo Velasquez
 *Date: 12/1/08
 *File Name: InvenScreen.java
 *Purpose: Similar to CharSheet.java but shows items
***********************************************************************/

package com.serneum.rpg.core;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.serneum.rpg.battle.Battle;
import com.serneum.rpg.battle.BattleCalc;

public class InvenScreen extends JFrame
{
    JLabel slot1, slot2, slot3, slot4, slot5, inven;
    JButton s1, s2, s3, s4, s5, ok;
    public static boolean invOpen = false, battOpen = false;

    public InvenScreen() throws FileNotFoundException
    {
        Inventory.readIn();
        slot1 = new JLabel("Slot1: ");
        slot2 = new JLabel("Slot2: ");
        slot3 = new JLabel("Slot3: ");
        slot4 = new JLabel("Slot4: ");
        slot5 = new JLabel("Slot5: ");
        inven = new JLabel("Inventory");

        s1 = new JButton(Inventory.items.elementAt(0));
        s2 = new JButton(Inventory.items.elementAt(1));
        s3 = new JButton(Inventory.items.elementAt(2));
        s4 = new JButton(Inventory.items.elementAt(3));
        s5 = new JButton(Inventory.items.elementAt(4));
        ok = new JButton("Ok");

        ButtonHandler sHandler;
        sHandler = new ButtonHandler();
        s1.addActionListener(sHandler);
        s2.addActionListener(sHandler);
        s3.addActionListener(sHandler);
        s4.addActionListener(sHandler);
        s5.addActionListener(sHandler);
        ok.addActionListener(sHandler);

        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridheight = 1;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.2;
        c.weighty = 0.2;
        c.fill = GridBagConstraints.BOTH;
        pane.add(inven, c);
        c.gridy = 1;
        c.gridx = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.LINE_START;
        pane.add(slot1, c);
        c.gridx = 2;
        pane.add(s1, c);
        c.gridy = 2;
        c.gridx = 1;
        pane.add(slot2, c);
        c.gridx = 2;
        pane.add(s2, c);
        c.gridy = 3;
        c.gridx = 1;
        pane.add(slot3, c);
        c.gridx = 2;
        pane.add(s3, c);
        c.gridy = 4;
        c.gridx = 1;
        pane.add(slot4, c);
        c.gridx = 2;
        pane.add(s4, c);
        c.gridy = 5;
        c.gridx = 1;
        pane.add(slot5, c);
        c.gridx = 2;
        pane.add(s5, c);
        c.gridy = 6;
        c.gridx = 1;
        c.gridwidth = 4;
        pane.add(ok, c);

        setVisible(true);
        setTitle("Inventory");
        setSize(200,300);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private class ButtonHandler implements ActionListener
      {
          @Override
        public void actionPerformed(ActionEvent e)
          {
              if(e.getSource() == ok)
              {
                  InvenScreen.invOpen = false;
                  dispose();
              }

              try
              {
                  if (battOpen == false)
                  {
                      if(e.getSource() == s1 && !s1.getText().equals("------"))
                      {
                          //Commented out, its a future feature to use items out of combat
                          //Inventory.Use(1);
                          //InvenScreen reload = new InvenScreen();
                          //dispose();
                      }

                      else if(e.getSource() == s2 && !s2.getText().equals("------"))
                      {
                          //Commented out, its a future feature to use items out of combat
                          //Inventory.Use(2);
                          //InvenScreen reload = new InvenScreen();
                          //dispose();
                      }

                      else if(e.getSource() == s3 && !s3.getText().equals("------"))
                      {
                          //Commented out, its a future feature to use items out of combat
                          //Inventory.Use(3);
                          //InvenScreen reload = new InvenScreen();
                          //dispose();
                      }

                      else if(e.getSource() == s4 && !s4.getText().equals("------"))
                      {
                          //Commented out, its a future feature to use items out of combat
                          //Inventory.Use(4);
                          //InvenScreen reload = new InvenScreen();
                          //dispose();
                      }

                      else if(e.getSource() == s5 && !s5.getText().equals("------"))
                      {
                          //Commented out, its a future feature to use items out of combat
                          //Inventory.Use(5);
                          //InvenScreen reload = new InvenScreen();
                          //dispose();
                      }
                  }

                  else if (battOpen == true)
                  {
                      if(e.getSource() == s1 && !s1.getText().equals("------"))
                      {
                          BattleCalc.name = s1.getText();
                          BattleCalc.action = "Item";
                          Inventory.Use(1);
                          //InvenScreen reload = new InvenScreen();
                          invOpen = false;

                          Battle.endTurn();
                          dispose();
                      }

                      else if(e.getSource() == s2 && !s2.getText().equals("------"))
                      {
                          BattleCalc.name = s2.getText();
                          BattleCalc.action = "Item";
                          Inventory.Use(2);
                          //InvenScreen reload = new InvenScreen();
                          invOpen = false;

                          Battle.endTurn();
                          dispose();
                      }

                      else if(e.getSource() == s3 && !s3.getText().equals("------"))
                      {
                          BattleCalc.name = s3.getText();
                          BattleCalc.action = "Item";
                          Inventory.Use(3);
                          //InvenScreen reload = new InvenScreen();
                          invOpen = false;

                          Battle.endTurn();
                          dispose();
                      }

                      else if(e.getSource() == s4 && !s4.getText().equals("------"))
                      {
                          BattleCalc.name = s4.getText();
                          BattleCalc.action = "Item";
                          Inventory.Use(4);
                          //InvenScreen reload = new InvenScreen();
                          invOpen = false;

                          Battle.endTurn();
                          dispose();
                      }

                      else if(e.getSource() == s5 && !s5.getText().equals("------"))
                      {
                          BattleCalc.name = s5.getText();
                          BattleCalc.action = "Item";
                          Inventory.Use(5);
                          //InvenScreen reload = new InvenScreen();
                          invOpen = false;

                          Battle.endTurn();
                          dispose();
                      }
                  }
              }
              catch(FileNotFoundException FNFE)
              {
              }

          }
      }

/*
    public static void main(String[] args) throws FileNotFoundException
    {
        InvenScreen inv = new InvenScreen();
    }
*/
}