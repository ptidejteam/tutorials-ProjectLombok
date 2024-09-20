package net.ptidej.tutorial.javactool.test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

import org.junit.Assert;
import org.junit.Test;

import com.sun.source.tree.AnnotatedTypeTree;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.AnyPatternTree;
import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.ArrayTypeTree;
import com.sun.source.tree.AssertTree;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.BindingPatternTree;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.BreakTree;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.CompoundAssignmentTree;
import com.sun.source.tree.ConditionalExpressionTree;
import com.sun.source.tree.ConstantCaseLabelTree;
import com.sun.source.tree.ContinueTree;
import com.sun.source.tree.DeconstructionPatternTree;
import com.sun.source.tree.DefaultCaseLabelTree;
import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.EmptyStatementTree;
import com.sun.source.tree.EnhancedForLoopTree;
import com.sun.source.tree.ErroneousTree;
import com.sun.source.tree.ExportsTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.InstanceOfTree;
import com.sun.source.tree.IntersectionTypeTree;
import com.sun.source.tree.LabeledStatementTree;
import com.sun.source.tree.LambdaExpressionTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MemberReferenceTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.ModuleTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.OpensTree;
import com.sun.source.tree.PackageTree;
import com.sun.source.tree.ParameterizedTypeTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.PatternCaseLabelTree;
import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.tree.ProvidesTree;
import com.sun.source.tree.RequiresTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.StringTemplateTree;
import com.sun.source.tree.SwitchExpressionTree;
import com.sun.source.tree.SwitchTree;
import com.sun.source.tree.SynchronizedTree;
import com.sun.source.tree.ThrowTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.TryTree;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.TypeParameterTree;
import com.sun.source.tree.UnaryTree;
import com.sun.source.tree.UnionTypeTree;
import com.sun.source.tree.UsesTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.tree.WhileLoopTree;
import com.sun.source.tree.WildcardTree;
import com.sun.source.tree.YieldTree;
import com.sun.source.util.JavacTask;
import com.sun.tools.javac.api.JavacTool;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.tree.JCTree;

public class TestMain {
	@Test
	public void test1() throws IOException {
		final JavacTool compiler = JavacTool.create();

		final StandardJavaFileManager fileManager = compiler
				.getStandardFileManager(null, null, null);
		final Iterable<? extends JavaFileObject> fileObjects = fileManager
				.getJavaFileObjects(new File(
						"src/main/java/net/ptidej/tutorial/javactool/Main.java"),
						new File(
								"src/test/java/net/ptidej/tutorial/javactool/test/TestMain.java"),
						new File(
								"src/test/java/net/ptidej/tutorial/javactool/test/VisitableElement.java"));

		final Iterable<String> options = Arrays.asList(new String[] {
				"--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED",
				"--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED" });
		final JavacTask task = compiler.getTask(null, fileManager, null,
				options, null, fileObjects);

		// final Elements elements = task.getElements();
		// final JavacTrees trees = JavacTrees.instance(task);
		final Iterable<? extends Element> elements = task.analyze();

		boolean hasElements = elements.iterator().hasNext();
		for (final Element element : elements) {
			final VisitableElement visitableElement = new VisitableElement(
					element);
			visitableElement.accept(
					new VisitableElement.Function2<Element, Integer, Void>() {
						@Override
						public Void apply(final Element element,
								final Integer depth) {

							for (int i = 0; i < depth.intValue(); i++) {
								System.out.print("\t");
							}
							System.out.println(element);
							return null;
						}
					});
		}
		Assert.assertTrue(hasElements);
	}

	@Test
	public void test2() throws IOException {
		final JavacTool compiler = JavacTool.create();

		final StandardJavaFileManager fileManager = compiler
				.getStandardFileManager(null, null, null);
		final Iterable<? extends JavaFileObject> fileObjects = fileManager
				.getJavaFileObjects(new File(
						"src/main/java/net/ptidej/tutorial/javactool/Main.java"),
						new File(
								"src/test/java/net/ptidej/tutorial/javactool/test/TestMain.java"),
						new File(
								"src/test/java/net/ptidej/tutorial/javactool/test/VisitableElement.java"));

		final Iterable<String> options = Arrays.asList(new String[] {
				"--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED",
				"--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED" });
		final JavacTask task = compiler.getTask(null, fileManager, null,
				options, null, fileObjects);

		final Iterable<? extends Element> elements = task.analyze();
		elements.forEach(x -> {
			x.accept(new ElementVisitor(), null);
		});

		Assert.assertTrue(elements.iterator().hasNext());
	}

	@Test
	public void test3() throws IOException {
		final JavacTool compiler = JavacTool.create();

		final StandardJavaFileManager fileManager = compiler
				.getStandardFileManager(null, null, null);
		final Iterable<? extends JavaFileObject> fileObjects = fileManager
				.getJavaFileObjects(new File(
						"src/main/java/net/ptidej/tutorial/javactool/Main.java"),
						new File(
								"src/test/java/net/ptidej/tutorial/javactool/test/TestMain.java"),
						new File(
								"src/test/java/net/ptidej/tutorial/javactool/test/VisitableElement.java"));

		final Iterable<String> options = Arrays.asList(new String[] {
				"--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED",
				"--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED" });
		final JavacTask task = compiler.getTask(null, fileManager, null,
				options, null, fileObjects);

		// final Elements elements = task.getElements();
		final JavacTrees trees = JavacTrees.instance(task);
		final Iterable<? extends Element> elements = task.analyze();
		boolean hasElements = elements.iterator().hasNext();
		for (final Element element : elements) {
			final JCTree jcTree = trees.getTree(element);
			jcTree.accept(new TreeVisitor<Void, Void>() {
				@Override
				public Void visitAnnotatedType(AnnotatedTypeTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitAnnotation(AnnotationTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitMethodInvocation(MethodInvocationTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitAssert(AssertTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitAssignment(AssignmentTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitCompoundAssignment(CompoundAssignmentTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitBinary(BinaryTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitBlock(BlockTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitBreak(BreakTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitCase(CaseTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitCatch(CatchTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitClass(ClassTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitConditionalExpression(
						ConditionalExpressionTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitContinue(ContinueTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitDoWhileLoop(DoWhileLoopTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitErroneous(ErroneousTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitExpressionStatement(
						ExpressionStatementTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitEnhancedForLoop(EnhancedForLoopTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitForLoop(ForLoopTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitIdentifier(IdentifierTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitIf(IfTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitImport(ImportTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitArrayAccess(ArrayAccessTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitLabeledStatement(LabeledStatementTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitLiteral(LiteralTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitStringTemplate(StringTemplateTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitAnyPattern(AnyPatternTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitBindingPattern(BindingPatternTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitDefaultCaseLabel(DefaultCaseLabelTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitConstantCaseLabel(ConstantCaseLabelTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitPatternCaseLabel(PatternCaseLabelTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitDeconstructionPattern(
						DeconstructionPatternTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitMethod(MethodTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitModifiers(ModifiersTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitNewArray(NewArrayTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitNewClass(NewClassTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitLambdaExpression(LambdaExpressionTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitPackage(PackageTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitParenthesized(ParenthesizedTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitReturn(ReturnTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitMemberSelect(MemberSelectTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitMemberReference(MemberReferenceTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitEmptyStatement(EmptyStatementTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitSwitch(SwitchTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitSwitchExpression(SwitchExpressionTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitSynchronized(SynchronizedTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitThrow(ThrowTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitCompilationUnit(CompilationUnitTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitTry(TryTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitParameterizedType(ParameterizedTypeTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitUnionType(UnionTypeTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitIntersectionType(IntersectionTypeTree node,
						Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitArrayType(ArrayTypeTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitTypeCast(TypeCastTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitPrimitiveType(PrimitiveTypeTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitTypeParameter(TypeParameterTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitInstanceOf(InstanceOfTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitUnary(UnaryTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitVariable(VariableTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitWhileLoop(WhileLoopTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitWildcard(WildcardTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitModule(ModuleTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitExports(ExportsTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitOpens(OpensTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitProvides(ProvidesTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitRequires(RequiresTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitUses(UsesTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitOther(Tree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Void visitYield(YieldTree node, Void p) {
					// TODO Auto-generated method stub
					return null;
				}
			}, null);
		}

		Assert.assertTrue(hasElements);
	}
}
