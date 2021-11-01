package management;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;

public class CommandQueue<T> extends AbstractQueue<T> {

  private final LinkedList<T> elements;

  public CommandQueue() {
    this.elements = new LinkedList<>();
  }

  @Override
  public Iterator<T> iterator() {
    return elements.iterator();
  }

  @Override
  public int size() {
    return elements.size();
  }

  @Override
  public boolean offer(T t) {
    if (t == null) return false;
    elements.add(t);
    return true;
  }

  @Override
  public T poll() {
    Iterator<T> iter = elements.iterator();
    T t = iter.next();
    if (t != null) {
      iter.remove();
      return t;
    }
    return null;
  }

  @Override
  public T peek() {
    return elements.getFirst();
  }
}
