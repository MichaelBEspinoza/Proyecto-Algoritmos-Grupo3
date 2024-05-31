/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures.queues;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A.
 * TDA Cola
 */
public interface Queue {
    public int size(); // devuelve el número de elementos en la cola
    public void clear(); //elimina / inicializa la Cola
    public boolean isEmpty(); // true si la Cola está vacía
    public int indexOf(Object element) throws QueueException; //devuelve la posicion de un elemento en la Cola
    public void enQueue(Object element) throws QueueException; // encola un elemento por el extremo posterior(final) de la cola
    public Object deQueue() throws QueueException; //suprime y retorna el elemento que está en la parte anterior(frente/inicio) de la cola
    public void enQueue(Object element, Integer priority) throws QueueException; //encola un elemento por el extremo posterior, de acuerdo al a prioridad indicada
    public boolean contains(Object element) throws QueueException; //true si el elemento fue encolado
    public Object peek() throws QueueException; //devuelve el elemento que está en el frente/inicio de la Cola
    public Object front() throws QueueException; //devuelve el elemento que está en el frente/inicio de la Cola
}
