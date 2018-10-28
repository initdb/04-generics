package de.thro.inf.prg3.a04.collections;

import java.util.function.Function;

public interface SimpleList<T> extends Iterable<T> {
	/**
	 * Add a given object to the back of the list.
	 */
	void add(T o);

	/**
	 * add a default instance to the list
	 */
	default void addDefault(Class<T> item) throws IllegalAccessException, InstantiationException
	{
		add(item.newInstance());
	}

	/**
	 * @return current size of the list
	 */
	int size();

	/**
	 * Generate a new list using the given filter instance.
	 * @return a new, filtered list
	 */
	@SuppressWarnings("unchecked")
	default SimpleList<T> filter(SimpleFilter<T> filter)
	{
		SimpleList<T> result;// = new SimpleListImpl();
		try
		{
			result = /*(SimpleList<T>)*/getClass().newInstance();
		}
		catch(InstantiationException | IllegalAccessException e)
		{
			result = new SimpleListImpl<>();
		}
		for(T o : this)
		{
			if(filter.include(o))
			{
				result.add(o);
			}
		}
		return result;
	}

	/**
	 *
	 * @param transform
	 * @param <R>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default <R> SimpleList<R> map(Function<T, R> transform)
	{
		SimpleList<R> result;
		try
		{
			result = (SimpleList<R>) getClass().newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			result = new SimpleListImpl<>();
		}
		for(T t : this)
		{
			result.add(transform.apply(t));
		}
		return result;
	}

	/* Java 9 and onwards
	private <V> SimpleList<V> createNew(Class<SimpleList<V>> clazz){
		try {
			return clazz.getConstructor().newInstance()
		} catch (Exception e) {
			return new SimpleListImpl<>();
		}
	}*/
}
