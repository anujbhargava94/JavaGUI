package com.ub.tree.ui;

public class Tree extends AbsTree {
	public Tree(int n) {
		super(n);
	}

	protected AbsTree add_node(int n) {
		return new Tree(n);
	}

	protected boolean count_duplicates() {
		return false;
	}

	protected int get_count() {
		return 1;
	}

	protected void set_count(int v) {

	}

	public String get_value() {
		return (value + " ");
	}

	protected void print_node() {
		System.out.print(value + "  ");
	}
}
