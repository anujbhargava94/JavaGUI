package com.ub.tree.ui;

import java.util.Stack;

//**************** The TreeMemento class *************************
//(to be coded by you)

public class TreeMemento {
	private Stack<AbsTree> state = new Stack<AbsTree>();

	public void set_state(AbsTree t) {
// fill in code here
		try {
			state.push(t.clone());
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AbsTree get_state() {
// fill in code here
		AbsTree treeState = state.isEmpty() ? null : state.pop();
		return treeState;
	}

	public void clear() {
// fill in code here
		state.clear();
	}

	boolean is_empty() {
// fill in code here
		return state.isEmpty();

	}
}
