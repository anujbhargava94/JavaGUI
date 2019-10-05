package com.ub.tree.ui;

//************** Assignment 2 Part 2 **************

//This file has the following classes:

//1. TreeGUIDriver and TreeGUI -- the top-level classes
//	Coding required mainly for undoButton, and some code
//	also for clearButton, insertButton, and deleteButton.
//	OK to add some extra fields in TreeGUI to support coding.
//
//2. OutputPanel -- for drawing the tree/duptree.
//	No changes required here
//
//3. TreeMemento -- for saving previous trees/duptrees
//	To be coded by you
//
//4. AbsTree, Tree, and DupTree -- the foundation classes
//	Only code for the clone() method in AbsTree


import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

import javax.swing.*;

import com.ub.tree.AbsTree;
import com.ub.tree.DupTree;
import com.ub.tree.Tree;

class TreeGUIDriver {
	public static void main(String[] x) {
		new TreeGUI();
	}
}

public class TreeGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	TreeMemento tm = new TreeMemento();

	AbsTree tree;
		
	boolean is_new_tree = true; // set to false after first insert

	Choice tree_kind, element_kind;
	
	public static Font font = new Font("Comic Sans MS", Font.BOLD, 24);

	JTextField input_elem_text, input_elem_text2, min_text, max_text; 
	
	JButton insertButton, deleteButton, undoButton; 
	JButton minButton, maxButton, clearButton;
	
	JPanel inputPanel;  
	OutputPanel outputPanel; 

	public TreeGUI() {
		
		super("GUI for Tree Operations");
		Label tree_kind_label;
		JPanel input1, input2;
		
		//The JPanel input1 details follow

		input1 = new JPanel(new FlowLayout());   //FlowLayout default
		
		tree_kind_label = new Label("Tree Kind:");
		tree_kind_label.setFont(font);
		input1.add(tree_kind_label);
		tree_kind = new Choice();
		tree_kind.setFont(font);
		tree_kind.addItem("Normal Tree");
		tree_kind.setFont(font);
		tree_kind.addItem("Dup Tree");
		tree_kind.setFont(font);
		input1.add(tree_kind);

		input_elem_text = new JTextField("integer");
		input_elem_text.setFont(font);
		input_elem_text.requestFocus(true);
		input_elem_text.selectAll();
		input_elem_text.setEditable(true);
		insertButton = new JButton("Insert");
		insertButton.setFont(font);
		input1.add(insertButton);
		input1.add(input_elem_text);
		
		input_elem_text2 = new JTextField("integer");
		input_elem_text2.setFont(font);
		input_elem_text2.requestFocus(true);
		input_elem_text2.selectAll();
		input_elem_text2.setEditable(true);
		deleteButton = new JButton("Delete");
		deleteButton.setFont(font);
		input1.add(deleteButton);
		input1.add(input_elem_text2);

		undoButton = new JButton("Undo");
		undoButton.setFont(font);
		input1.add(undoButton);
		
		//The JPanel input2 details follow

		input2 = new JPanel(new FlowLayout());   //FlowLayout default

		minButton = new JButton("Minimum");
		minButton.setFont(font);
		input2.add(minButton);
		min_text = new JTextField(10);
		min_text.setFont(font);
		min_text.setEditable(false);
		input2.add(min_text);

		maxButton = new JButton("Maximum");
		maxButton.setFont(font);
		input2.add(maxButton);
		max_text = new JTextField(10);
		max_text.setFont(font);
		max_text.setEditable(false);
		input2.add(max_text);

		clearButton = new JButton("Clear");
		clearButton.setFont(font);
		input2.add(clearButton);
		
		// The JPanels inputPanel and outputPanel details follow
		
		inputPanel = new JPanel(new BorderLayout());
		outputPanel = new OutputPanel();
		
		inputPanel.add("North", input1);
		inputPanel.add("South", input2);
		
		// Add Button Listeners here for:
		// min, max, clear, insert, delete, undo
		
		minButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (is_new_tree || tree == null) {
					JOptionPane.showMessageDialog(null, "Cannot take min of empty tree");
					return;
				}
				min_text.setText(Integer.toString(tree.min().value));
			}
		});
		
		maxButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (is_new_tree || tree == null) {
					JOptionPane.showMessageDialog(null, "Cannot take max of empty tree");
					return;
				}
				max_text.setText(Integer.toString(tree.max().value));
			}
		});
		
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				is_new_tree = true;
				tree = null;
				input_elem_text.setText("");
				input_elem_text2.setText("");
				min_text.setText("");
				max_text.setText("");
				outputPanel.clearPanel();
			}
		});
		
		insertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = input_elem_text.getText();
				boolean b = false;  // whether insert changed state of tree
				
				if (is_new_tree) {
					try {
						if (tree_kind.getSelectedIndex() == 0)
							tree = new Tree(Integer.parseInt(s));
						else
							tree = new DupTree(Integer.parseInt(s));
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "Bad integer: " + s + ". Please re-enter.");
						return;
					}
	
				} // end of if is_new_tree
				else {
			         try {
			        	 if (tree == null) {
			        		 if (tree_kind.getSelectedIndex() == 0)
							 	tree = new Tree(Integer.parseInt(s));
			        		 else
							 	tree = new DupTree(Integer.parseInt(s));
			        		 b = true;
			        	 }
			        	 else 
			        		 b = tree.insert(Integer.parseInt(s)); 
			         } catch (NumberFormatException e2) {
							 	JOptionPane.showMessageDialog(null, "Bad integer: " + s + ". Please re-enter.");
							 	return;
						 }
				}
				is_new_tree = false;
				outputPanel.drawTree(tree);
				input_elem_text.selectAll();
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = input_elem_text2.getText();
				int n = 0;
				try {
					n = Integer.parseInt(s);
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "Number format error: " + s + ". Please re-enter.");
					return;
				}
				if (is_new_tree || tree == null) {
					JOptionPane.showMessageDialog(null, "Cannot delete from an empty tree.");
					return;
				}   
				
				boolean b = tree.delete(n); // note whether delete changed state of tree
				
				if (b)  {
					 outputPanel.drawTree(tree);
				}
				else // delete will not remove the last value, hence must check this:
					if (n == tree.value && tree.left==null && tree.right==null) {
						tree = null; // 
						outputPanel.clearPanel();
					}
					else {
						JOptionPane.showMessageDialog(null, "Cannot delete non-existent value " + n);
						outputPanel.drawTree(tree);
					}
				input_elem_text2.selectAll();
			}
		});
		
		
		undoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			// missing code to be filled in by you

			}	 
		});
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		add("North", inputPanel);
		add("Center", outputPanel);

		setSize(1400, 1000); // for the frame
		setVisible(true);
	}
}