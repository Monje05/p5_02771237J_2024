package ule.edi.tree;

import java.util.Iterator;

/**
 * arbol binario.
 * 
 * @author profesor
 *
 * @param <T>
 */
public abstract class AbstractBinaryTreeADT<T> extends AbstractTreeADT<T> {
	
	//	Como arbol binario, tiene dos sub-arboles binarios
	//	"izquierdo" y "derecho"
	//
	//	Podrian ser vacios
	protected AbstractBinaryTreeADT<T> left;
	protected AbstractBinaryTreeADT<T> right;
	
	
	@Override
	public int getMaxDegree() {
		return 2;
	}

	@Override
	public TreeADT<T> getSubtree(int n) {
		//	El sub-arbol izquierdo es el "0"
		switch (n) {
		case 0:
			return left;
		case 1:
			return right;
		}
		
		
		throw new IllegalStateException("getSubtree(n) on a binary tree needs n in {0,1}");
	}

// TODO
	
	
	
	
		
}
