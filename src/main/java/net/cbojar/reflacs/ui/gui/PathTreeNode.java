package net.cbojar.reflacs.ui.gui;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

final class PathTreeNode implements TreeNode {
	private PathTreeNode parent = null;
	private final Path path;
	private final boolean isFile;
	private final List<PathTreeNode> children = new ArrayList<>();

	private PathTreeNode(final Path path, final boolean isFile) {
		this.path = path;
		this.isFile = isFile;
	}

	public static PathTreeNode create(final Path path, final boolean isFile) {
		return create(path, isFile, Collections.emptyList());
	}

	public static PathTreeNode create(final Path path, final boolean isFile, final Collection<PathTreeNode> children) {
		return new PathTreeNode(path, isFile).addChildren(children);
	}

	private void setParent(final PathTreeNode parent) {
		this.parent = parent;
	}

	private PathTreeNode addChildren(final Collection<PathTreeNode> newChildren) {
		newChildren.forEach(child -> child.setParent(this));
		children.addAll(newChildren);
		return this;
	}

	public Path path() {
		return path;
	}

	@Override
	public TreeNode getChildAt(final int childIndex) {
		if (childIndex < 0 || childIndex >= children.size()) {
			throw new ArrayIndexOutOfBoundsException(childIndex);
		}

		return children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public int getIndex(final TreeNode node) {
		return children.indexOf(node);
	}

	@Override
	public boolean getAllowsChildren() {
		return !isFile;
	}

	@Override
	public boolean isLeaf() {
		return isFile;
	}

	@Override
	public Enumeration<? extends PathTreeNode> children() {
		return Collections.enumeration(children);
	}

	@Override
	public int hashCode() {
		return path.hashCode();
	}

	@Override
	public boolean equals(final Object other) {
		return this == other || (other instanceof PathTreeNode && path.equals(((PathTreeNode)other).path()));
	}

	@Override
	public String toString() {
		return path.toString();
	}
}
