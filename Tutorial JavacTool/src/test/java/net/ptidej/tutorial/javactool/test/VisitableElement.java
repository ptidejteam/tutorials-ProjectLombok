package net.ptidej.tutorial.javactool.test;

import java.util.List;

import javax.lang.model.element.Element;

// I know that javax.lang.model.element.ElementVisitor<R, P> exits,
// this is an illustration of creating a Visitor-like interface on any
// whole-part hierarchy (that implements the Composite design pattern).
public class VisitableElement {
	@FunctionalInterface
	public static interface Function2<T1, T2, R> {
		public R apply(final T1 t1, final T2 t2);
	}

	private final Element rootElement;

	public VisitableElement(final Element element) {
		this.rootElement = element;
	}

	private void accept(final Element element,
			final VisitableElement.Function2<Element, Integer, Void> aVisitor) {

		this.accept(element, 0, aVisitor);
	}

	private void accept(final Element element, final int depth,
			final VisitableElement.Function2<Element, Integer, Void> aVisitor) {

		aVisitor.apply(element, depth);
		final List<? extends Element> enclosedElements = element
				.getEnclosedElements();
		for (final Element newElement : enclosedElements) {
			this.accept(newElement, depth + 1, aVisitor);
		}
	}

	public void accept(final Function2<Element, Integer, Void> aVisitor) {
		this.accept(this.rootElement, aVisitor);
	}
}
