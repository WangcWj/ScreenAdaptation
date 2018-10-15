package cn.screen.adaptation.process;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import cn.screen.adaptation.annotation.IdentificationEnum;
import cn.screen.adaptation.annotation.ScreenAdaptation;

/**
 * Created to :注解处理器.
 *
 * @author WANG
 * @date 2018/10/10
 */

@AutoService(Processor.class)
public class ScreenAnnotationProcess extends AbstractProcessor {

    private Messager mMessager;
    private Filer mFiler;
    private Types mTypeUtils;
    private ClassName xScreenType = ClassName.get("cn.screen.adaptation.screen_lib", "ScreenAdaptation");
    private ClassName arrlyListType = ClassName.get("java.util", "ArrayList");
    private ClassName hashMapType = ClassName.get("java.util", "HashMap");

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mFiler = processingEnvironment.getFiler();
        mTypeUtils = processingEnvironment.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "处理开始--------------------");
        try {
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ScreenAdaptation.class);
            if (CollectionUtils.isNotEmpty(elements)) {
                processBaseWidth(elements);
            }
        } catch (Exception e) {
            return false;
        }

        mMessager.printMessage(Diagnostic.Kind.NOTE, "处理结束--------------------");
        return true;
    }

    private void processBaseWidth(Set<? extends Element> elements) throws IOException {
        FieldSpec fieldSpec = FieldSpec.builder(HashMap.class, Options.WIDTH_FIELD_LIST)
                .addModifiers(Modifier.PRIVATE)
                .initializer("new HashMap<String,IdentificationEnum>()")
                .addJavadoc(Options.WIDTH_FIELD_DOC)
                .build();

        MethodSpec.Builder methodBuild = MethodSpec.methodBuilder(Options.WIDTH_METHOD_NAME)
                .addModifiers(Modifier.PRIVATE)
                .returns(hashMapType)
                .addJavadoc(Options.WIDTH_METHOD_DOC);

        for (Element element : elements) {
            if (ElementKind.CLASS == element.getKind()) {
                TypeElement typeElement = (TypeElement) element;
                String classPath = typeElement.getQualifiedName().toString();
                ScreenAdaptation screenAdaptation = typeElement.getAnnotation(ScreenAdaptation.class);
                IdentificationEnum identificationEnum = screenAdaptation.value();
                mMessager.printMessage(Diagnostic.Kind.NOTE, "classPath--------------------" + classPath);
                methodBuild.addStatement(Options.WIDTH_FIELD_LIST + ".put($S,$N)", classPath, "IdentificationEnum." + identificationEnum);
            }
        }
        methodBuild.addStatement("return " + Options.WIDTH_FIELD_LIST);
        MethodSpec methodSpec = methodBuild.build();

        MethodSpec constructorMethod = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(xScreenType, "xScreenBean")
                .addStatement("xScreenBean.setBaseWidthActivitys($N())", methodSpec)
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder(Options.WIDTH_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodSpec)
                .addMethod(constructorMethod)
                .addField(fieldSpec)
                .addJavadoc("自动生成,请勿擅修改\n")
                .build();

        JavaFile javaFile = JavaFile.builder(Options.PACKAGE_NAME, typeSpec)
                .build();
        javaFile.writeTo(mFiler);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new HashSet<>();
        annotations.add(ScreenAdaptation.class.getCanonicalName());
        return annotations;
    }
}
