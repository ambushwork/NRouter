package com.netatmo.ylu.compiler;

import com.google.auto.service.AutoService;
import com.netatmo.ylu.annotation.NRouter;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;


@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.netatmo.ylu.annotation.NRouter"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedOptions("content")
public class NRouterProcessor extends AbstractProcessor {

    //Element operation tool class
    private Elements elementUtils;

    //type information tool class
    private Types typeUtils;

    //Output warning error log
    private Messager messager;

    //File generator
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();

        String content = processingEnv.getOptions().get("content");
        messager.printMessage(Diagnostic.Kind.NOTE, content);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if(annotations.isEmpty()) return false;

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(NRouter.class);
        for(Element element: elements){
            String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
            String className = element.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "Annotated class :" + className);

            //The class name that we want to generate
            String finalClassName = className + "$$NRouter";

            try {
                JavaFileObject sourceFile = filer.createSourceFile(packageName + "." + finalClassName);
                Writer writer = sourceFile.openWriter();

                writer.write("package " + packageName + ";\n");

                writer.write("public class " + finalClassName + " {\n");

                writer.write("public static Class<?> findTargetClass(String path) {\n");

                NRouter nRouter = element.getAnnotation(NRouter.class);

                writer.write("if (path.equals(\"" + nRouter.path() + "\")) {\n");

                writer.write("return " + className + ".class;\n}\n");

                writer.write("return null;\n");

                writer.write("}\n}");

                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
