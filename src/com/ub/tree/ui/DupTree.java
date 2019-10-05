package com.ub.tree.ui;

public class DupTree extends AbsTree {
	public DupTree(int n) {
		super(n);
		count = 1;
	};

	protected AbsTree add_node(int n) {
		return new DupTree(n);
	}

	protected boolean count_duplicates() {
		count++;
		return true;
	}

	protected void print_node() {
		System.out.print(value + "/" + count + " ");
	}

	public String get_value() {
		return (value + "/" + count);
	}

	protected int get_count() {
		return count;
	}

	protected void set_count(int v) {
		count = v;
	}

	protected int count;
}
