package ule.edi.tree;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;


/**
 * arbol binario de busqueda (binary search tree, BST).
 * 
 * El codigo fuente esta en UTF-8, y la constante EMPTY_TREE_MARK definida en
 * AbstractTreeADT del proyecto API deberia ser el simbolo de conjunto vacio:
 * ∅
 * 
 * Si aparecen caracteres "raros", es porque el proyecto no esta bien
 * configurado en Eclipse para usar esa codificacion de caracteres.
 *
 * En el toString() que esta ya implementado en AbstractTreeADT se usa el
 * formato:
 * 
 * Un arbol vaci­o se representa como "∅". Un Ã¡rbol no vacio como
 * "{(informacion rai­z), sub-arbol 1, sub-arbol 2, ...}".
 * 
 * Por ejemplo, {A, {B, ∅, ∅}, ∅} es un arbol binario con rai­z "A" y un
 * unico sub-arbol, a su izquierda, con rai­z "B".
 * 
 * El metodo render() tambien representa un arbol, pero con otro formato; por
 * ejemplo, un arbol {M, {E, ∅, ∅}, {S, ∅, ∅}} se muestra como:
 * 
 * M 
 * | E
 * | | ∅
 * | | ∅ 
 * | S 
 * | | ∅
 * | | ∅
 * 
 * Cualquier nodo puede llevar asociados pares (clave,valor) para adjuntar
 * informacion extra. Si es el caso, tanto toString() como render() mostraran
 * los pares asociados a cada nodo.
 * 
 * Con {@link #setTag(String, Object)} se inserta un par (clave,valor) y con
 * {@link #getTag(String)} se consulta.
 * 
 * 
 * Con <T extends Comparable<? super T>> se pide que exista un orden en los
 * elementos. Se necesita para poder comparar elementos al insertar.
 * 
 * Si se usara <T extends Comparable<T>> seria muy restrictivo; en su lugar se
 * permiten tipos que sean comparables no solo con exactamente T sino tambien
 * con tipos por encima de T en la herencia.
 * 
 * @param <T> tipo de la informacion en cada nodo, comparable.
 */
public class BinarySearchTreeImpl<T extends Comparable<? super T>> extends AbstractBinaryTreeADT<T> {

	BinarySearchTreeImpl<T> father; // referencia a su nodo padre)
	int count;  // contador de instancias 

	/**
	 * Devuelve el arbol binario de busqueda izquierdo.
	 */
	protected BinarySearchTreeImpl<T> getLeftBST() {
		// El atributo leftSubtree es de tipo AbstractBinaryTreeADT<T> pero
		// aqui­ se sabe que es ademas BST (binario de busqueda)
		//
		return (BinarySearchTreeImpl<T>) left;
	}

	protected void setLeftBST(BinarySearchTreeImpl<T> left) {
		this.left = left;
	}

	/**
	 * Devuelve el arbol binario de busqueda derecho.
     */
	protected BinarySearchTreeImpl<T> getRightBST() {
		return (BinarySearchTreeImpl<T>) right;
	}

	protected void setRightBST(BinarySearchTreeImpl<T> right) {
		this.right = right;
	}

	/**
	 * arbol BST vaci­o
	 */
	public BinarySearchTreeImpl() {
		// TODO HACER QUE THIS SEA EL NODO VACIO
		this.right = null;
		this.left = null;
		this.content = null;
		this.count = 0;
		this.father = null;
	
	}

	public BinarySearchTreeImpl(BinarySearchTreeImpl<T> father) {
		// TODO HACER QUE THIS SEA EL NODO VACIO, asignando como padre el parametro
		// recibido
		this.right = null;
		this.left = null;
		this.content = null;
		this.count = 0;
		this.father = father;
	
	}

	private BinarySearchTreeImpl<T> emptyBST(BinarySearchTreeImpl<T> father) {
		//Devuelve un nodo vacío
		return new BinarySearchTreeImpl<T>(father);
	}

	
	
	/**
	 * Inserta los elementos que no sean null, de una coleccion en el arbol. 
	 * (si alguno es 'null', no lo inserta)
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param elements valores a insertar.
	 * @return numero de elementos insertados en el arbol (elementos diferentes de null)
	 */
	public int insert(Collection<T> elements) {
		int insertados = 0;
		for(T element : elements) {
			if(element != null) {
				insert(element);
				insertados++;
			}
		}
		return insertados;
	}

	/**
	 * Inserta los elementos que no sean null, de un array en el arbol. 
	 * (si alguno es 'null', no lo inserta)
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param elements elementos a insertar.
	 * @return  	numero de elementos insertados en el arbol (elementos diferentes de null)
	 */
	@SuppressWarnings("unchecked")
	public int insert(T... elements) {
		int insertados = 0;
		for(T element : elements) {
			if(element != null) {
				insert(element);
				insertados++;
			}
		}
		return insertados;
	}

	/**
	 * Inserta (como hoja) un nuevo elemento en el arbol de busqueda.
	 * 
	 * Debe asignarse valor a su atributo father (referencia a su nodo padre o null
	 * si es la rai­z)
	 * 
	 * No se permiten elementos null. Si element es null dispara excepcion:IllegalArgumentException 
	 * Si el elemento ya existe en el arbol
	 *  no inserta un nodo nuevo, sino que incrementa el atributo count del nodo que tiene igual contenido.
	 * 
	 * @param element valor a insertar.
	 * @return true si se insertó en un nuevo nodo (no existia ese elemento en el arbol),
	 *         false en caso contrario
	 * @throws IllegalArgumentException si element es null
	 */
	public boolean insert(T element) {
		if(element == null) {
			throw new IllegalArgumentException();
		}
		if(content == null) {
			this.content = element;
			this.count = 1;
			setRightBST(new BinarySearchTreeImpl<>(this));
			setLeftBST(new BinarySearchTreeImpl<>(this));
			return true;
		}
		int comparison = element.compareTo(this.content);
		if(comparison == 0) {
			this.count++;
			return false;
		} else if(comparison < 0) {
			if(this.left == null) {
				this.left = new BinarySearchTreeImpl<>(this);
			}
			return this.getLeftBST().insert(element);
		} else {
			if(this.right == null) {
				this.right = new BinarySearchTreeImpl<>(this);
			}
			return this.getRightBST().insert(element);
		}
	}

	/**
	 * Busca el elemento en el arbol.
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param element valor a buscar.
	 * @return true si el elemento esta en el arbol, false en caso contrario
	 * @throws IllegalArgumentException si element es null
	 *
	 */
	public boolean contains(T element) {
		if(element == null) {
			throw new IllegalArgumentException();
		}
		if(this.content == null) {
			return false;
		}
		int comparision = element.compareTo(this.content);
		if(comparision == 0) {
			return true;
		} else if(comparision < 0 && this.left != null) {
			return this.getLeftBST().contains(element);
		} else if(comparision > 0 && this.right != null) {
			return this.getRightBST().contains(element);
		} else {
			return false;
		}
	}
	
	/**
	 *  devuelve la cadena formada por el contenido del árbol teniendo en cuenta que 
	 *  si un nodo tiene su atributo count>1 pone entre paréntesis su valor justo detrás del atributo elem
	 *  También debe mostrar las etiquetas que tenga el nodo (si las tiene)
	 *  
	 *  CONSEJO: REVISAR LA IMPLEMENTACIÓN DE TOSTRING DE LA CLASE AbstractTreeADT 
	 * 
	 * Por ejemplo: {M, {E(2), ∅, ∅}, {K(5), ∅, ∅}}
	 * 
	 * @return cadena con el contenido del árbol incluyendo su atributo count entre paréntesis si elemento tiene más de 1 instancia
	 */
	public String toString() {
		if (! isEmpty()) {
			//	Construye el resultado de forma eficiente
			StringBuffer result = new StringBuffer();
			//	Raíz
			result.append("{" + content.toString());
			if(this.count > 1) {
				result.append("(" + this.count + ")");
			}
			if (! tags.isEmpty()) {
				result.append(" [");
				List<String> sk = new LinkedList<String>(tags.keySet());
				Collections.sort(sk);
				for (String k : sk) {
					result.append("(" + k + ", " + tags.get(k) + "), ");
				}
				result.delete(result.length() - 2, result.length());
				result.append("]");
			}
			//	Y cada sub-árbol
			for (int i = 0; i < getMaxDegree(); i++) {
				if (getSubtree(i) != null) {
					result.append(", " + getSubtree(i).toString());
				} else {
					result.append(", ∅");
				}
			}
			//	Cierra la "}" de este árbol
			result.append("}");
			return result.toString();
		} else {
			return AbstractTreeADT.EMPTY_TREE_MARK;
		}
	}


	/**
	 * Devuelve un iterador que recorre los elementos (sin tener en cuenta el número de instancias)del arbol por niveles segun
	 * el recorrido en anchura
	 * 
	 * Por ejemplo, con el arbol
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40, ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * y devolvera el iterador que recorrera los nodos en el orden: 50, 30, 80, 10, 40, 60
	 * 
	 * 
	 * 
	 * @return iterador para el recorrido en anchura
	 */
    public Iterator<T> iteratorWidth() {
	 	List<T> elements = new LinkedList<>();
		if (!isEmpty()) {
			Queue<BinarySearchTreeImpl<T>> queue = new LinkedList<>();
			queue.add(this);
			while (!queue.isEmpty()) {
				BinarySearchTreeImpl<T> current = queue.poll();
				elements.add(current.getContent());
				if (current.getLeftBST() != null) {
					queue.add(current.getLeftBST());
				}
				if (current.getRightBST() != null) {
					queue.add(current.getRightBST());
				}
			}
		}
		return elements.iterator();
	}

	/**
	 * Devuelve un iterador que recorre los elementos (teniendo en cuenta el número de instancias)del arbol por niveles segun
	 * el recorrido en anchura
	 * 
	 * Por ejemplo, con el arbol
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40, ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * y devolvera el iterador que recorrera los nodos en el orden: 50, 30, 30, 80, 80, 10, 40, 60
	 *  
	 * @return iterador para el recorrido en anchura
	 */
     public Iterator<T> iteratorWidthInstances() {
		List<T> elements = new LinkedList<>();
		if (!isEmpty()) {
			Queue<BinarySearchTreeImpl<T>> queue = new LinkedList<>();
			queue.add(this);
			while (!queue.isEmpty()) {
				BinarySearchTreeImpl<T> current = queue.poll();
				for (int i = 0; i < current.count; i++) {
					elements.add(current.getContent());
				}
				if (current.getLeftBST() != null) {
					queue.add(current.getLeftBST());
				}
				if (current.getRightBST() != null) {
					queue.add(current.getRightBST());
				}
			}
		}
		return elements.iterator();
	 }
	
		
	/**
	 * Cuenta el número de elementos diferentes del arbol (no tiene en cuenta las instancias)
	 * 
	 * Por ejemplo, con el arbol
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * la llamada a ejemplo.instancesCount() devolvera 6
	 * 
	 * @return el numero de elementos diferentes del arbol 
	 */
    public int size() {
		return sizeRecursive(this);
	}

	private int sizeRecursive(BinarySearchTreeImpl<T> tree) {
		if(tree == null || tree.isEmpty()) {
			return 0;
		}
		int count = 1;
		int leftCount = sizeRecursive(tree.getLeftBST());
		int rightCount = sizeRecursive(tree.getRightBST());
		return count + leftCount + rightCount;
	}
	
    /**
	 * Cuenta el número de instancias de elementos diferentes del arbol 
	 * 
	 * Por ejemplo, con el arbol ejemplo=
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * la llamada a ejemplo.instancesCount() devolvera 11
	 * 
	 * @return el número de instancias de elementos del arbol 
	 */
	public int instancesCount() {
		return instancesCountRecursive(this);
	}

	private int instancesCountRecursive(BinarySearchTreeImpl<T> tree) {
		if(tree == null || tree.isEmpty()) {
			return 0;
		}
		int count = tree.count;
		int leftCount = instancesCountRecursive(tree.getLeftBST());
		int rightCount = instancesCountRecursive(tree.getRightBST());
		return count + leftCount + rightCount;
	}
	

	
	/**
	 * Elimina los valores en un array del Arbol.
	 * Devuelve el número de elementos que pudo eliminar del árbol
	 *  (no podrá eliminar los elemenots 'null' o que no los contiene el arbol)
	 * 
	 * return numero de elementos eliminados del arbol
	 */
	@SuppressWarnings("unchecked")
	public int  remove(T... elements) {
		int removed = 0;
		for(T element : elements) {
			if(element != null && contains(element)) {
				remove(element, 1);
				removed++;
			}
		}
		return removed;
	}

	/**
	 * Elimina un elemento del arbol. Si el atributo count del nodo que contiene el elemento es >1, simplemente se decrementará este valor en una unidad
	 * 
	 * Si hay que eliminar el nodo, y tiene dos hijos, se tomara el criterio de sustituir el
	 * elemento por el menor de sus mayores y eliminar el menor de los mayores.
	 * 
	 * @throws NoSuchElementException si el elemento a eliminar no esta en el arbol
	 * @throws IllegalArgumentException si element es null
     *
	 */
	public void remove(T element) {
		if(element == null) {
			throw new IllegalArgumentException();
		}
		if(!contains(element)) {
			throw new NoSuchElementException();
		}
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		removeRec(element);

	}

	private void removeRec(T element) {
		if(!isEmpty()) {
			if (content.compareTo(element) == 0) {
				if (count > 1) {
					count--;
				} else {
					if (isLeaf()) {
						this.content = null;
						this.count = 0;
						this.right = null;
						this.left = null;
					} else if (getRightBST().isEmpty()) {
						copySubtree(getLeftBST());
					} else if (getLeftBST().isEmpty()) {
						copySubtree(getRightBST());
					} else {
						BinarySearchTreeImpl<T> aux = getRightBST().mM();
						this.content = aux.content;
						this.count = aux.count;
						getRightBST().removeAll(aux.content);
						if (this.getRightBST() != null && !this.getRightBST().isEmpty()) {
							this.getRightBST().father = this;
						}
						if (this.getLeftBST() != null && !this.getLeftBST().isEmpty()) {
							this.getLeftBST().father = this;
						}
					}
				}
			} else if (content.compareTo(element) > 0) {
				if (getLeftBST() != null && !getLeftBST().isEmpty()) {
					getLeftBST().removeRec(element);
				}
			} else {
				if (getRightBST() != null && !getRightBST().isEmpty()) {
					getRightBST().removeRec(element);
				}
			}
		}
	}

	private BinarySearchTreeImpl<T> mM() {
		if (getLeftBST().isEmpty()) {
			return this;
		} else {
			return getLeftBST().mM();
		}
	}

	private void copySubtree(BinarySearchTreeImpl<T> subtree) {
		if (subtree == null) {
			this.content = null;
			this.count = 0;
			this.left = null;
			this.right = null;
		} else {
			this.content = subtree.content;
			this.count = subtree.count;
			this.left = subtree.left;
			this.right = subtree.right;
		}
	
		if (getLeftBST()!=null && !getLeftBST().isEmpty()) {
			getLeftBST().father = this;
		}
		if (getRightBST()!=null && !getRightBST().isEmpty()) {
			getRightBST().father = this;
		}
	}


	
	/**
	 * Decrementa el número de instancias del elemento en num unidades.
	 * Si count queda en cero o negativo, se elimina el elemento del arbol. 
	 * Devuelve el número de instancias que pudo eliminar
	 * 
	 * 
	 * Si hay que eliminar el nodo, y tiene dos hijos, se tomara el criterio de sustituir el
	 * elemento por el menor de sus mayores y eliminar el menor de los mayores.
	 * 
	 * @throws NoSuchElementException si el elemento a eliminar no esta en el arbol	
	 * @throws IllegalArgumentException si element es null
	 * @return numero de instancias eliminadas
	 * 
	 */
	public int remove(T element, int num) {
		if (element == null) {
			throw new IllegalArgumentException();
		}
		if (!contains(element)) {
			throw new NoSuchElementException();
		}
		if(isEmpty()) {
			throw new NoSuchElementException();
		}

		return removeRec(element, num);
	}

	private int removeRec(T element, int num) {
		if (isEmpty()) {
			return 0;
		}
		int deleted = 0;
		if (content.compareTo(element) > 0) {
			deleted = getLeftBST().removeRec(element, num);
		} else if (content.compareTo(element) < 0) {
			deleted = getRightBST().removeRec(element, num);
		} else {
			num = Math.min(count, num);
			count -= num;
			deleted = num;
			if(count==0){
				remove(element);
			}
		}
		return deleted;
	}
	
	/**
	 * Elimina todas las instancias del elemento en el árbol 
	 * eliminando del arbol el nodo que contiene el elemento .
	 * 
	 * 
	 * Se tomara el criterio de sustituir el elemento por el menor de sus mayores 
	 * y eliminar el menor de los mayores.
	 * 
	 * @throws NoSuchElementException si el elemento a eliminar no esta en el arbol	
	 * @throws IllegalArgumentException si element es null
	 */
	public int removeAll(T element) {
		if (element == null) {
			throw new IllegalArgumentException();
		}
		if (!contains(element)) {
			throw new NoSuchElementException();
		}
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		int deleted = 0;
		if (content.compareTo(element) > 0) {
			deleted =  deleted + getLeftBST().removeAll(element);
		} else if (content.compareTo(element) < 0) {
			deleted =  deleted + getRightBST().removeAll(element);
		} else {
			deleted = count;
			remove(element, count);
		}
		return deleted;
	}

	/**
	* Devuelve el sub-árbol indicado. (para tests)
	* path será el camino para obtener el sub-arbol. Está formado por 0 y 1.
	* Si se codifica "bajar por la izquierda" como "0" y
	* "bajar por la derecha" como "1", el camino desde un 
	* nodo N hasta un nodo M (en uno de sus sub-árboles) será          * la cadena de 0s y 1s que indica cómo llegar desde N hasta M.
	*
	* Se define también el camino vacío desde un nodo N hasta
	* él mismo, como cadena vacía.
	* 
	* Si el subarbol no existe lanzará la excepción NoSuchElementException.
	* 
	* @param path
	* @return el nodo no vacío que se alcanza con ese camino
	* @throws NoSuchElementException si el camino no alcanza un nodo no vacío en el árbol
	* @throws IllegalArgumentException si el camino no contiene sólamente 0s y 1s
*/
	public BinarySearchTreeImpl<T> getSubtreeWithPath(String path) {
		if(path == null) {
			throw new IllegalArgumentException();
		}

		BinarySearchTreeImpl<T> current = this;

		for(char c : path.toCharArray()) {
			if(c == '0') {
				if(current.getLeftBST() == null) {
					throw new NoSuchElementException();
				}
				current = current.getLeftBST();
			}else if(c == '1') {
				if(current.getRightBST() == null) {
					throw new NoSuchElementException();
				}
				current = current.getRightBST();
			} else {
				throw new IllegalArgumentException();
			}
		}
		return current;
	}




/**
 * Devuelve el contenido del nodo alcanzado desde la raíz
 * de éste árbol, con el camino dado.
 * 
 * Por ejemplo, sea un árbol "A" {10, {5, ∅, ∅}, {20, ∅, {30, ∅, ∅}}}:
 * 
 * 10
 * |  5
 * |  |  ∅
 * |  |  ∅
 * |  20
 * |  |  ∅ 
 * |  |  30
 * |  |  |  ∅
 * |  |  |  ∅
 * 
 * Entonces se tiene que A.getContentWithPath("1") es 20 y 
 * que A.getContentWithPath("") es 10.
 * 
 * @param path camino a seguir desde la raíz.
 * @return contenido del nodo alcanzado.
 * @throws NoSuchElementException si el camino no alcanza un nodo no vacío en el árbol
 * @throws IllegalArgumentException si el camino no contiene sólamente 0s y 1s
*/
	public T getContentWithPath(String path) {
		if(path == null) {
			throw new IllegalArgumentException();
		}

		BinarySearchTreeImpl<T> current = this;

		for(char c : path.toCharArray()) {
			if(c == '0') {
				if(current.getLeftBST() == null) {
					throw new NoSuchElementException();
				}
				current = current.getLeftBST();
			}else if(c == '1') {
				if(current.getRightBST() == null) {
					throw new NoSuchElementException();
				}
				current = current.getRightBST();
			} else {
				throw new IllegalArgumentException();
			}
		}

		if(current == null || current.isEmpty()) {
			throw new NoSuchElementException();
		}
		return current.getContent();
	}


	/**
	* Etiqueta los nodos con su posición en el recorrido descendente.
	* Por ejemplo, sea un árbol "A":
	* 
	* {10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}
	* 
	* 10
	* |  5
	* |  |  2
	* |  |  |  ∅
	* |  |  |  ∅
	* |  |  ∅
	* |  20
	* |  |  ∅
	* |  |  30
	* |  |  |  ∅
	* |  |  |  ∅
	* 
	* y el árbol quedaría etiquetado como:
	* 
	*  {10 [(descend, 3)], 
	*       {5 [(descend, 4)], {2 [(descend, 5)], ∅, ∅}, ∅}, 
	*       {20 [(descend, 2)], ∅, {30 [(descend, 1)], ∅, ∅}}}
	* 
   */
	public void tagDescendent() {
		tagDescendentAux(this, new int []{1});
	}

	private void tagDescendentAux(BinarySearchTreeImpl<T> node, int[] counter) {
		if(node != null && !node.isEmpty()) {
			tagDescendentAux(node.getRightBST(), counter);
			node.setTag("descend", counter[0]);
			counter[0]++;
			tagDescendentAux(node.getLeftBST(), counter);
		}
	}


	/**
	 * Acumula en orden preorden, una lista con los pares 'padre-hijo' en este árbol.
	 * 
	 * Por ejemplo, sea un árbol "A":
	 * 
	 * {10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}
	 * 
	 * 10
	 * |  5
	 * |  |  2
	 * |  |  |  ∅
	 * |  |  |  ∅
	 * |  |  ∅
	 * |  20
	 * |  |  ∅
	 * |  |  30
	 * |  |  |  ∅
	 * |  |  |  ∅
	 * 
	 * el resultado sería una lista de cadenas:
	 * 
	 *         [(10,5), (5,2), (10,20), (20,30), ]
	 * 
	 * y además quedaría etiquetado como:
	 * 
	 *  {10 [(preorder, 1)], 
	 *       {5 [(preorder, 2)], {2 [(preorder, 3)], ∅, ∅}, ∅}, 
	 *       {20 [(preorder, 4)], ∅, {30 [(preorder, 5)], ∅, ∅}}}
	 * 
	 * @retur lista con el resultado.
	 */
	public List<String> parentChildPairsTagPreorder() {
		List<String> pairs = new LinkedList<>();
		tagPreorder(this, new int[]{1}, pairs, null);
		return pairs;
	}

	private void tagPreorder(BinarySearchTreeImpl<T> node, int[] counter, List<String> pairs, BinarySearchTreeImpl<T> parent) {
		if(node != null && !node.isEmpty()) {
			node.setTag("preorder", counter[0]);
			if(parent != null) {
				pairs.add("(" + parent.getContent() + "," + node.getContent() + ")");
			}
			counter[0]++;
			tagPreorder(node.getLeftBST(), counter, pairs, node);
        	tagPreorder(node.getRightBST(), counter, pairs, node);
		}
	}


	/**
	 * Etiqueta solamente los nodos que son hijos izquierdos 
	 *  de su padre con el valor de su posición en postorden, 
	 * devolviendo el número de nodos que son hijos izquierdos.
	 *
	 *   Por ejemplo, sea un árbol "A":
	 * 
	 * {10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}
	 * 
	 * 10
	 * |  5
	 * |  |  2
	 * |  |  |  ∅
	 * |  |  |  ∅
	 * |  |  ∅
	 * |  20
	 * |  |  ∅
	 * |  |  30
	 * |  |  |  ∅
	 * |  |  |  ∅
	 * 
	 * devolverá 2
	 * y el árbol quedaría etiquetado como:
	 * Solo etiqueta el 5 y el 2, porque son los únicos nodos
	 * que son hijos izquierdos
	 *  {10, 
	 *       {5 [(postorder, 2)] , {2 [(postorder, 1)], ∅, ∅}, ∅}, 
	 *       {20, ∅, {30, ∅, ∅}}}
	 * 
	 * @return numero de nodos hijos izquierdos
	 */
	public int tagLeftChildrenPostorder() {
		return tagLeftChildrenPostorderAux(this, new int[]{1});
	}

	private int tagLeftChildrenPostorderAux(BinarySearchTreeImpl<T> node, int[] counter) {
		if(node == null || node.isEmpty()) {
			return 0;
		}

		int leftCount = tagLeftChildrenPostorderAux(node.getLeftBST(), counter);
		int rightCount = tagLeftChildrenPostorderAux(node.getRightBST(), counter);
		if(node.father != null && node == node.father.getLeftBST()) {
			node.setTag("postorder", counter[0]);
			counter[0]++;
			return leftCount + rightCount + 1;
		}
		return leftCount + rightCount;
	}


	/**
	* Comprueba si el nodo actual (this) tiene un hermano 
	*  en el mismo nivel, que tiene el mismo nº de instancias que él
	*
	* Por ejemplo, sea un árbol "A":
	* 
	* {10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}
	* la llamada a A.hasBrotherSameCount() devolverá false,
	*  porque al ser la raiz no tiene hermanos
	* 
	* la llamda a A.getSubtreeWithPath("0").hasBrotherSameCount() devolverá true, 
	* porque el 5 tiene hermano con el mismo count
	
	* @return true si su hermano tiene el mismo nº de instancias que él, 
	*    false  en caso contrario (si no tiene hermano o si lo tiene, este
	*    no tiene el mismo nº de instancias)
	*/
	public boolean hasBrotherSameCount() {
		BinarySearchTreeImpl<T> parent = getParent(father);
		if(parent == null) {
			return false;
		}

		BinarySearchTreeImpl<T> brother = null;
		if(parent.getLeftBST() != null && parent.getLeftBST() != this) {
			brother = parent.getLeftBST();
		} else if(parent.getRightBST() != null && parent.getRightBST() != this) {
			brother = parent.getRightBST();
		}

		if(brother == null) {
			return false;
		}

		return this.instancesCount() == brother.instancesCount();
	}

	private BinarySearchTreeImpl<T> getParent(BinarySearchTreeImpl<T> root) {
		if(root == null || root.isEmpty() || root == this) {
			return null;
		}

		if(root.getLeftBST() == this || root.getRightBST() == this) {
			return root;
		}

		BinarySearchTreeImpl<T> parent = getParent(root.getLeftBST());
		if(parent != null) {
			return parent;
		}

		return getParent(root.getRightBST());
	}
	
	/**
	* Devuelve el toString del nodo simétrico al (this)  
	* o la cadena vacía si no tiene nodo simétrico
	*
	** Por ejemplo, sea un árbol "A":
	* 
	* {10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}
	* la llamada a a.toStringSimetric() devolverá "",
	*  porque al ser la raiz no tiene simétrico
	* 
	* la llamada a a.getSubtreeWithPath("0").toStringSimetric() devolverá , 
	* "{20, ∅, {30, ∅, ∅}}" 
    * la llamada a a.getSubtreeWithPath("00").toStringSimetric() devolverá , 
	* "{30, ∅, ∅}" 
		
	* @return la cadena vacía si no tiene nodo simétrico o el toString del nodo simétrico.
	*    
	*/
	public String toStringSimetric() {
		if(father ==null){
			return "";
		}
		return toStringS("");
	}
	private String toStringS(String ruta){
		if(father == null){
			try{
				return getSubtreeWithPath(ruta).toString();
			}catch(Exception e){
				return "";
			}
		}
		if(father.content.compareTo(content) > 0){
			return father.toStringS("1" + ruta);
		}else{
			return father.toStringS("0" + ruta);
		}
		
	}
	
	/**
	 * Busca y devuelve a partir del nodo que contiene el elemento pasado como parámetro 
	 * el elemento que está up posiciones hacia arriba y right hacia abajo bajando por la rama derecha. 
	 * Primero debe encontrar el elemento y despues comprueba si el nodo que contiene ese elemento
	 * tiene nodo a través del camino indicado por los otros dos parámetros.
     * Debe etiquetar desde el nodo que contiene el elemento,  hasta su objetivo,  los nodos del camino 
     * con un número consecutivo empezando por el 1 en el elemento buscado. 
     * 
     * Por ejemplo: para el árbol ejemplo= {10, {5, {2, ∅, ∅}, {7,∅, ∅},}, {20, {15, {12, ∅, ∅}, ∅ },{30, ∅, ∅}}}. 
     * 
     * Si se hace ejemplo.getRoadUpRight("7",2,2) devolverá el elemento 30 y etiquetará los nodos 7, 5, 10, 20, 30 con numeros consecutivos
     *  y la etiqueta road. 
     *  
     * Así el árbol quedaría etiquetado: 10 [(road, 3)],{5[(road, 2)], {2, ∅, ∅}, {7 [(road, 1)],∅, ∅},}, {20 [(road, 4)], {15, {12, ∅, ∅}, ∅},{30 [(road, 5)], ∅, ∅}}}
     *  siendo el nodo que contiene el 30 el nodo que devuelve.
	 * 
	 * @throws NoSuchElementException si el elemento a comprobar no esta en el arbol	
	 * @throws IllegalArgumentException si element es null
	 */
	public T getRoadUpRight(T elem, int up, int right) {
		if (elem == null) {
			throw new IllegalArgumentException("El elemento no puede ser nulo");
		}
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		if(content.compareTo(elem) > 0) {
			return getLeftBST().getRoadUpRight(elem, up, right);
		}
		if(content.compareTo(elem) < 0) {
			return getRightBST().getRoadUpRight(elem, up, right);
		}
		BinarySearchTreeImpl<T> aux = this;
		int pos = 1;
		while(up > 0) {
			if(aux.father == null) {
				throw new NoSuchElementException();
			}
			aux.setTag("road", pos++);
			aux = aux.father;
			up--;
		}

		while(right > 0) {
			if(aux.isEmpty()) {
				throw new NoSuchElementException();
			}
			aux.setTag("road", pos++);
			aux = aux.getRightBST();
			right--;
		}
		if(aux.isEmpty()) {
			aux.setTag("road", pos++);
		}
		return aux.content; 
	}

	
	/**
	 * Importante: Solamente se puede recorrer el arbol una vez
	 * 
	 * Calcula y devuelve el numero de nodos que son hijos unicos y etiqueta cada
	 * nodo que sea hijo unico (no tenga hermano hijo del mismo padre) con la
	 * etiqueta "onlySon" y el valor correspondiente a su posicion segun el
	 * recorrido preorden en este arbol.
	 * 
	 * La rai­z no se considera hijo unico.
	 * 
	 * Por ejemplo, sea un arbol ejemplo, que tiene 3 hijos unicos, 
	 * la llamada a ejemplo.tagOnlySonPreorder() devuelve 3 y los va etiquetando
	 * segun su recorrido en preorden.
	 * 
	 * {30, {10, {5, {2, ∅, ∅}, ∅}, {20, {15, {12, ∅, ∅}, ∅}, ∅}, ∅}
	 * 
	 *
	 * el arbol quedari­a etiquetado:
	 * 
	 * {30, {10 [(onlySon, 2)], {5, {2 [(onlySon, 4)], ∅, ∅}, ∅}, {20, {15 [(onlySon, 6)], {12
	 * [(onlySon, 7)], ∅, ∅}, ∅}, ∅}, ∅}
	 * 
	 */
	public int tagOnlySonPreorder() {
		int a = tagOnlySonPreorderAux(this, new int[]{1});
		return a;
	}

	private int tagOnlySonPreorderAux(BinarySearchTreeImpl<T> node, int[] counter) {
		if(node == null) {
			return 0;
		}

		int count = 0;

		if(node.father != null && (node.father.getLeftBST() == null || node.father.getRightBST() == null)) {
			node.setTag("onlySon", counter[0]++);
			count++;
		} else {
			counter[0]++;
		}

		count += tagOnlySonPreorderAux(node.getLeftBST(), counter);
		count += tagOnlySonPreorderAux(node.getRightBST(), counter);

		return count;
	} 
		
}
	
	