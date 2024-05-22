package ule.edi.tree;


import static org.junit.Assert.assertEquals;

import java.text.CollationElementIterator;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;





public class BinarySearchTreeTests {

   
	/*
	* 10
	* |  5
	* |  |  2
	* |  |  |	∅
	* |  |  |	∅
	* |  |	 ∅
	* |  20
	* |  |  15
	* |  |  |	∅
	* |  |  | 	∅
	* |  |  30
	* |  |  |  	∅
	* |  |  |  	∅
    */	
	private BinarySearchTreeImpl<Integer> ejemplo = null;
	
	
	/*
	* 10
	* |  5
	* |  |  2
	* |  |  |  	∅
	* |  |  |  	∅
	* |  | 	 ∅
	* |  20
	* |  |  15
	* |  |  |  12
	* |  |  |  |  	∅
	* |  |  |  |  	∅
	* |  | 	 ∅
  */
	private BinarySearchTreeImpl<Integer> other=null;
	
	@Before
	public void setupBSTs() {
		
			
		ejemplo = new BinarySearchTreeImpl<Integer>();
		ejemplo.insert(10, 20, 5, 2, 15, 30);
		Assert.assertEquals(ejemplo.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}");
		
		
		other =new BinarySearchTreeImpl<Integer>();
		other.insert(10, 20, 5, 2, 15, 12);
		Assert.assertEquals(other.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, {12, ∅, ∅}, ∅}, ∅}}");
		
	}

	@Test 
	public void insert_Collections_test() {
		Collection<Integer> elementos = new LinkedList<>();
		elementos.add(3);
		elementos.add(7);
		elementos.add(null);
		elementos.add(17);
		int insertadors = ejemplo.insert(elementos);
		Assert.assertEquals(3, insertadors);
		Assert.assertEquals("{10, {5, {2, ∅, {3, ∅, ∅}}, {7, ∅, ∅}}, {20, {15, ∅, {17, ∅, ∅}}, {30, ∅, ∅}}}", ejemplo.toString());
	}

	@Test
	public void insert_T_test() {
		Assert.assertEquals(4, ejemplo.insert(1, 6, null, 4, 13));
		Assert.assertEquals("{10, {5, {2, {1, ∅, ∅}, {4, ∅, ∅}}, {6, ∅, ∅}}, {20, {15, {13, ∅, ∅}, ∅}, {30, ∅, ∅}}}", ejemplo.toString());
	}

	@Test
	public void insert_test() {
		Assert.assertTrue(ejemplo.insert(7));
		Assert.assertFalse(ejemplo.insert(20));
	}
	
	@Test
	public void contains_test() {
		Assert.assertTrue(ejemplo.insert(7));
		Assert.assertFalse(ejemplo.insert(20));
	}

	@Test(expected = IllegalArgumentException.class)
	public void contains_Exception_test() {
		ejemplo.contains(null);
	}

	@Test
	public void size_test() {
		Assert.assertEquals(6, ejemplo.size());
		ejemplo.insert(2, 10, 30);
		Assert.assertEquals(6, ejemplo.size());
	}

	@Test
	public void instancesCount_test() {
		Assert.assertEquals(6, ejemplo.instancesCount());
		ejemplo.insert(2, 10, 30);
		Assert.assertEquals(9, ejemplo.instancesCount());
	}

	@Test
	public void remove_T_test() {
		ejemplo.remove(15);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}", ejemplo.toString());
	}
	
	@Test
	public void testRemoveCountMayor1() {
		ejemplo.insert(20);
		ejemplo.insert(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20(3), {15, ∅, ∅}, {30, ∅, ∅}}}", ejemplo.toString());
		ejemplo.remove(20);
	    Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20(2), {15, ∅, ∅}, {30, ∅, ∅}}}", ejemplo.toString());
	}
	
	@Test
	public void testRemoveCountMayor1HastaVaciar() {
		ejemplo.insert(20);
		ejemplo.insert(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20(3), {15, ∅, ∅}, {30, ∅, ∅}}}",ejemplo.toString());
		ejemplo.remove(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20(2), {15, ∅, ∅}, {30, ∅, ∅}}}",ejemplo.toString());
		ejemplo.remove(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}",ejemplo.toString());
		ejemplo.remove(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {30, {15, ∅, ∅}, ∅}}",ejemplo.toString());
	}
	
	@Test
	public void testRemoveHoja() {
		ejemplo.remove(30);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, ∅}}",ejemplo.toString());
	}
	
	@Test
	public void testRemove1Hijo() {
		ejemplo.remove(2);
		Assert.assertEquals("{10, {5, ∅, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}",ejemplo.toString());
	}
	
	@Test
	public void testRemove2Hijos() {
		ejemplo.remove(10);
		Assert.assertEquals("{15, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}",ejemplo.toString());
	}
	
	@Test
	public void removeAll_test() {
		ejemplo.insert(20);
		ejemplo.insert(20);
		ejemplo.removeAll(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {30, {15, ∅, ∅}, ∅}}",ejemplo.toString());
	}	
				
	@Test(expected = IllegalArgumentException.class)
	public void testInsertException() {
		Integer i = null;
		other.insert(i);	
	}
		
	
	@Test(expected = IllegalArgumentException.class)
	public void testContainsNull() {
		other.contains(null);
	}
		
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveNullElement() {
		Integer i = null;
		other.remove(i);
	}
		
	@Test(expected = NoSuchElementException.class)
	public void testRemoveNoSuchElement() {
		other.remove(11);
	}

	@Test
	public void getSubtreeWithPath_test() {
		BinarySearchTreeImpl<Integer> subtree = ejemplo.getSubtreeWithPath("00");
		assertEquals(Integer.valueOf(2), subtree.getContent());
		BinarySearchTreeImpl<Integer> subtree2 = ejemplo.getSubtreeWithPath("10");
		assertEquals(Integer.valueOf(15), subtree2.getContent());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getSubtreeWithPath_exception_test() {
		ejemplo.getSubtreeWithPath(null);
		ejemplo.getSubtreeWithPath("02");
	}

	@Test(expected = NoSuchElementException.class)
	public void getSubtreeWithPath_Nosuch_test() {
		ejemplo.getSubtreeWithPath("000");
		ejemplo.getSubtreeWithPath("001");
	}

	@Test
	public void getContentWithPath_test() {
		assertEquals(Integer.valueOf(2), ejemplo.getContentWithPath("00"));
		assertEquals(Integer.valueOf(15), ejemplo.getContentWithPath("10"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getContentWithPath_exception_test() {
		ejemplo.getContentWithPath(null);
		ejemplo.getContentWithPath("02");
	}

	@Test(expected = NoSuchElementException.class)
	public void getContentWithPath_Nosuch_test() {
		ejemplo.getContentWithPath("000");
		ejemplo.getContentWithPath("001");
	}

}


