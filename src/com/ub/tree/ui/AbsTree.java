package com.ub.tree.ui;

//*********************************************************
//Binary Search Tree (of integers) with Duplicates in Java
//*********************************************************

public abstract class AbsTree implements Cloneable {
	public AbsTree(int n) {
		value = n;
		left = null;
		right = null;
	}

	public AbsTree clone() {

		// fill in code here

	}

	void print() {
		if (left != null)
			left.print();
		print_node();
		if (right != null)
			right.print();
	}

	public boolean insert(int n) {
		if (value == n)
			return count_duplicates();
		else if (value < n) {
			if (right == null)
				right = add_node(n);
			else
				return right.insert(n);
			return true;
		} else {
			if (left == null)
				left = add_node(n);
			else
				return left.insert(n);
			return true;
		}
	}

	public boolean delete(int n) { // assume > 1 nodes in tree
		AbsTree t = find(n);

		if (t == null) { // n is not in the tree
			return false;
		}

		if (t.get_count() > 1) {
			t.set_count(t.get_count() - 1);
			return true;
		}

		if (t.left == null && t.right == null) { // n is a leaf value
			if (t != this) {
				case1(t, this);
				return true;
			} else
				return false;
		}
		if (t.left == null || t.right == null) { // t has one subtree only
			if (t != this) { // check whether t is the root of the tree
				case2(t, this);
				return true;
			} else {
				if (t.right == null)
					case3(t, "left");
				else
					case3(t, "right");
				return true;
			}
		}
		// t has two subtrees; go with smallest in right subtree of t
		case3(t, "right");
		return true;
	}

	protected void case1(AbsTree t, AbsTree root) { // remove the leaf
		if (t.value > root.value)
			if (root.right == t)
				root.right = null;
			else
				case1(t, root.right);
		else if (root.left == t)
			root.left = null;
		else
			case1(t, root.left);
	}

	protected void case2(AbsTree t, AbsTree root) { // remove internal node
		if (t.value > root.value)
			if (root.right == t)
				if (t.right == null)
					root.right = t.left;
				else
					root.right = t.right;
			else
				case2(t, root.right);
		else if (root.left == t)
			if (t.right == null)
				root.left = t.left;
			else
				root.left = t.right;
		else
			case2(t, root.left);
	}

	protected void case3(AbsTree t, String side) { // replace t.value and t.count
		if (side == "right") {
			AbsTree min_right_t = t.right.min();
			if (min_right_t.left == null && min_right_t.right == null)
				case1(min_right_t, this); // min_right_t is a leaf node
			else
				case2(min_right_t, this); // min_right_t is a non-leaf node
			t.value = min_right_t.value;
			t.set_count(min_right_t.get_count());
		} else {
			AbsTree max_left_t = t.left.max();
			if (max_left_t.left == null && max_left_t.right == null)
				case1(max_left_t, this); // max_left_t is a leaf node
			else
				case2(max_left_t, this); // max_left_t is a non-leaf node
			t.value = max_left_t.value;
			t.set_count(max_left_t.get_count());

		}
	}

	private AbsTree find(int n) {
		if (value == n)
			return this;
		else if (value < n)
			if (right == null)
				return null;
			else
				return right.find(n);
		else if (left == null)
			return null;
		else
			return left.find(n);
	}

	public AbsTree min() {
		if (left != null)
			return left.min();
		else
			return this;
	}

	public AbsTree max() {
		if (right != null)
			return right.max();
		else
			return this;
	}

	protected int value;
	protected AbsTree left;
	protected AbsTree right;

// Protected Abstract Methods

	protected abstract AbsTree add_node(int n);

	protected abstract boolean count_duplicates();

	protected abstract void print_node();

	protected abstract String get_value();

	protected abstract int get_count();

	protected abstract void set_count(int v);
}