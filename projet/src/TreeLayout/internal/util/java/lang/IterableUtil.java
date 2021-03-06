/*
 * [The "BSD license"]
 * Copyright (c) 2011, abego Software GmbH, Germany (http://www.abego.org)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the abego Software GmbH nor the names of its 
 *    contributors may be used to endorse or promote products derived from this 
 *    software without specific prior written permission.
 *    
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package TreeLayout.internal.util.java.lang;


import java.util.Iterator;
import java.util.List;

import TreeLayout.internal.util.java.util.IteratorUtil;

/**
 * Util (general purpose) methods dealing with {@link Iterable}.
 * 
 * @author Udo Borkowski (ub@abego.org)
 * 
 * 
 */
public class IterableUtil {
	private static class ReverseIterable<T> implements Iterable<T> {
		private List<T> list;

		public ReverseIterable(List<T> list) {
			this.list = list;
		}

		@Override
		public Iterator<T> iterator() {
			return IteratorUtil.createReverseIterator(list);
		}
	};

	/**
	 * Returns an {@link Iterable} with an iterator iterating the given list
	 * from the end to the start.
	 * <p>
	 * I.e. the iterator does the reverse of the {@link List#iterator()}.
	 * 
	 * @param <T> Type of elements in the list
	 * @param list  &nbsp;
	 * @return a reverse {@link Iterable} of the list
	 */
	public static <T> Iterable<T> createReverseIterable(List<T> list) {
		// When the list is empty we can use the "forward" iterable (i.e. the
		// list itself) also as the reverseIterable as it will do nothing.
		if (list.size() == 0) {
			return list;
		}

		return new ReverseIterable<T>(list);
	}
}
