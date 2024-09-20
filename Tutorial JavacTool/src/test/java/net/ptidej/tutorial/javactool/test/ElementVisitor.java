package net.ptidej.tutorial.javactool.test;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;

public class ElementVisitor
		implements javax.lang.model.element.ElementVisitor<Void, Void> {

	@Override
	public Void visit(final Element e, final Void p) {
		System.out.println(e);
		return null;
	}

	@Override
	public Void visitPackage(final PackageElement e, final Void p) {
		System.out.println(e);
		return null;
	}

	@Override
	public Void visitType(final TypeElement e, final Void p) {
		System.out.println(e);
		final List<? extends Element> elements = e.getEnclosedElements();
		for (final Element element : elements) {
			element.accept(this, null);
		}
		return null;
	}

	@Override
	public Void visitVariable(final VariableElement e, final Void p) {
		System.out.println(e);
		return null;
	}

	@Override
	public Void visitExecutable(final ExecutableElement e, final Void p) {
		System.out.println(e);
		return null;
	}

	@Override
	public Void visitTypeParameter(final TypeParameterElement e, final Void p) {
		System.out.println(e);
		return null;
	}

	@Override
	public Void visitUnknown(final Element e, final Void p) {
		System.out.println(e);
		return null;
	}
}
