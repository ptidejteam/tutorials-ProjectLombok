// https://www.baeldung.com/lombok-custom-annotation
package net.ptidej.tutorial.lombok.singleton.javac;

import org.kohsuke.MetaInfServices;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;

import lombok.core.AnnotationValues;
import lombok.javac.Javac8BasedLombokOptions;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.JavacTreeMaker;
import lombok.javac.handlers.JavacHandlerUtil;
import net.ptidej.tutorial.lombok.singleton.Singleton;

@MetaInfServices(JavacAnnotationHandler.class)
public class SingletonHandler extends JavacAnnotationHandler<Singleton> {
	public void handle(final AnnotationValues<Singleton> annotation,
			final JCTree.JCAnnotation ast, final JavacNode annotationNode) {

		final Context context = annotationNode.getContext();

		final Javac8BasedLombokOptions options = Javac8BasedLombokOptions
				.replaceWithDelombokOptions(context);
		options.deleteLombokAnnotations();

		JavacHandlerUtil.deleteAnnotationIfNeccessary(annotationNode,
				Singleton.class);
		JavacHandlerUtil.deleteImportFromCompilationUnit(annotationNode,
				"lombok.AccessLevel");

		final JavacNode singletonClass = annotationNode.up();
		System.out.println("singletonClass=" + singletonClass);
		final JavacTreeMaker singletonClassTreeMaker = singletonClass
				.getTreeMaker();

		this.addPrivateConstructor(singletonClass, singletonClassTreeMaker);
		final JavacNode holderInnerClass = this.addInnerClass(singletonClass,
				singletonClassTreeMaker);
		this.addInstanceVar(singletonClass, singletonClassTreeMaker,
				holderInnerClass);
		this.addFactoryMethod(singletonClass, singletonClassTreeMaker,
				holderInnerClass);
	}

	private void addPrivateConstructor(final JavacNode singletonClass,
			final JavacTreeMaker singletonTM) {

		final JCTree.JCModifiers modifiers = singletonTM
				.Modifiers(Flags.PRIVATE);
		final JCTree.JCBlock block = singletonTM.Block(0L, List.nil());
		final JCTree.JCMethodDecl constructor = singletonTM.MethodDef(modifiers,
				singletonClass.toName("<init>"), null, List.nil(), List.nil(),
				List.nil(), block, null);
		System.out.println("singletonClass=" + singletonClass);
		System.out.println("constructor=" + constructor);
		JavacHandlerUtil.injectMethod(singletonClass, constructor);
	}

	private JavacNode addInnerClass(final JavacNode singletonClass,
			final JavacTreeMaker singletonTM) {

		JCTree.JCModifiers modifiers = singletonTM
				.Modifiers(Flags.PRIVATE | Flags.STATIC);
		final String innerClassName = singletonClass.getName() + "Holder";
		JCTree.JCClassDecl innerClassDecl = singletonTM.ClassDef(modifiers,
				singletonClass.toName(innerClassName), List.nil(), null,
				List.nil(), List.nil());
		return JavacHandlerUtil.injectType(singletonClass, innerClassDecl);
	}

	private void addInstanceVar(final JavacNode singletonClass,
			final JavacTreeMaker singletonClassTM,
			final JavacNode holderClass) {

		final JCTree.JCModifiers fieldMod = singletonClassTM
				.Modifiers(Flags.PRIVATE | Flags.STATIC | Flags.FINAL);
		final JCTree.JCClassDecl singletonClassDecl = (JCTree.JCClassDecl) singletonClass
				.get();
		final JCTree.JCIdent singletonClassType = singletonClassTM
				.Ident(singletonClassDecl.name);
		final JCTree.JCNewClass newKeyword = singletonClassTM.NewClass(null,
				List.nil(), singletonClassType, List.nil(), null);
		final JCTree.JCVariableDecl instanceVar = singletonClassTM.VarDef(
				fieldMod, singletonClass.toName("INSTANCE"), singletonClassType,
				newKeyword);
		JavacHandlerUtil.injectField(holderClass, instanceVar);
	}

	private void addFactoryMethod(final JavacNode singletonClass,
			final JavacTreeMaker singletonClassTreeMaker,
			final JavacNode holderInnerClass) {

		final JCTree.JCModifiers modifiers = singletonClassTreeMaker
				.Modifiers(Flags.PUBLIC | Flags.STATIC);
		final JCTree.JCClassDecl singletonClassDecl = (JCTree.JCClassDecl) singletonClass
				.get();
		final JCTree.JCIdent singletonClassType = singletonClassTreeMaker
				.Ident(singletonClassDecl.name);
		final JCTree.JCBlock block = this
				.addReturnBlock(singletonClassTreeMaker, holderInnerClass);
		final JCTree.JCMethodDecl factoryMethod = singletonClassTreeMaker
				.MethodDef(modifiers, singletonClass.toName("getInstance"),
						singletonClassType, List.nil(), List.nil(), List.nil(),
						block, null);
		JavacHandlerUtil.injectMethod(singletonClass, factoryMethod);
	}

	private JCTree.JCBlock addReturnBlock(
			final JavacTreeMaker singletonClassTreeMaker,
			final JavacNode holderInnerClass) {

		final JCTree.JCClassDecl holderInnerClassDecl = (JCTree.JCClassDecl) holderInnerClass
				.get();
		final JavacTreeMaker holderInnerClassTreeMaker = holderInnerClass
				.getTreeMaker();
		final JCTree.JCIdent holderInnerClassType = holderInnerClassTreeMaker
				.Ident(holderInnerClassDecl.name);

		final JCTree.JCFieldAccess instanceVarAccess = holderInnerClassTreeMaker
				.Select(holderInnerClassType,
						holderInnerClass.toName("INSTANCE"));
		final JCTree.JCReturn returnValue = singletonClassTreeMaker
				.Return(instanceVarAccess);

		ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
		statements.add(returnValue);

		return singletonClassTreeMaker.Block(0L, statements.toList());
	}
}