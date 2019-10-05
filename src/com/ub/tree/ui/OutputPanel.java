package com.ub.tree.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;

import com.ub.tree.AbsTree;

//**************** The OutputPanel class *************************
//(no changes required)

//The output area, where the tree is drawn
public class OutputPanel extends Panel {
	private static final long serialVersionUID = 1L;
	Image treeImage; // buffer to keep the latest tree representation
	int imageWidth, imageHeight;

// draw the tree on the output panel
	public void drawTree(AbsTree tree) {
		Graphics g;
		Dimension d = this.size();
		if (imageWidth != d.width || imageHeight != d.height) {
			treeImage = createImage(d.width, d.height);
			imageWidth = d.width;
			imageHeight = d.height;
		}
		;
// Clear the image
		g = treeImage.getGraphics();
		g.setFont(TreeGUI.font);
		g.setColor(getBackground());
		g.fillRect(0, 0, d.width, d.height);
		g.setColor(Color.black);

		drawNode(g, imageWidth / 2, tree, imageWidth / 2, 0);
		getGraphics().drawImage(treeImage, 0, 0, this);
	}

	private void drawNode(Graphics g, int subtreeW, AbsTree tree, int x, int y) {
		AbsTree left, right;
		g.drawString(tree.get_value(), x - 15, y + 15);
		left = tree.left;
		right = tree.right;
		if (left != null) {
			g.drawLine(x, y + 15, x - subtreeW / 2, y + 115);
			drawNode(g, subtreeW / 2, left, x - subtreeW / 2, y + 115);
		}
		;
		if (right != null) {
			g.drawLine(x, y + 15, x + subtreeW / 2, y + 115);
			drawNode(g, subtreeW / 2, right, x + subtreeW / 2, y + 115);
		}
	}

	public void clearPanel() {
		Graphics g;
// Clear the image
		g = treeImage.getGraphics();
		g.setColor(getBackground());
		g.fillRect(0, 0, imageWidth, imageHeight);
		g.setColor(Color.black);
		getGraphics().drawImage(treeImage, 0, 0, this);
	}

}
