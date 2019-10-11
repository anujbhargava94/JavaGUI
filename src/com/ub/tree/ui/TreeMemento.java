package com.ub.tree.ui;

import java.util.Stack;

//**************** The TreeMemento class *************************
//(to be coded by you)

public class TreeMemento {
	private Stack<AbsTree> state = new Stack<AbsTree>();

	/*
	 * Before any event is performed on a tree. The cloned previous state of the tree is pushed in the tree
	 */
	public void set_state(AbsTree t) {
// fill in code here
		try {
			state.push(t.clone());
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * When a state is fetched the top of the stack is popped. If the state is empty null is returned.
	 */
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
