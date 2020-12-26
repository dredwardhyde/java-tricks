package com.company;

import org.apache.commons.io.IOUtils;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

class JavaSourceFromString extends SimpleJavaFileObject {
    final String code;
    public JavaSourceFromString(String name, String code) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) { return code; }
}

public class RuntimeCompTest {

    private static final String CLAZZ =
            "package lol.kek; " +
                    "public class MainTest { " +
                    "   public static void lol(){" +
                    "       System.out.println(\"in lol\");" +
                    "   }" +
                    "   public static void main(String[] args){ " +
                    "       System.out.println(\"in main lol\");" +
                    "   }" +
                    "}";

    /* Prints:
     * in lol
     * in main lol
     */
    public static void main(String[] args) throws Exception {
        Path target = Paths.get(".");
        String jarName = "output.jar";
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(new DiagnosticCollector<>(), null, null);
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singletonList(target.toFile()));
        Iterable<? extends JavaFileObject> shitToCompile = Collections.singletonList(new JavaSourceFromString("MainTest", CLAZZ));
        StringWriter output = new StringWriter();
        compiler.getTask(output, fileManager, null, Arrays.asList("-classpath",
                System.getProperty("java.class.path")), null, shitToCompile).call();
        String fullPath;
        String fullClassName;
        try (URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{target.toUri().toURL()})) {
            Class<?> clazzLoaded = urlClassLoader.loadClass("lol.kek.MainTest");
            fullPath = clazzLoaded.getName().replace(".", "/") + ".class";
            fullClassName = clazzLoaded.getName();
            clazzLoaded.getDeclaredMethod("lol").invoke(new Object());
        }
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, fullClassName);
        try (JarOutputStream targetJar = new JarOutputStream(
                new FileOutputStream(target.toAbsolutePath().normalize().toString() + File.separator + jarName),
                manifest)) {
            JarEntry entry = new JarEntry(fullPath);
            entry.setTime(System.currentTimeMillis());
            targetJar.putNextEntry(entry);
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(fullPath))) {
                IOUtils.copy(in, targetJar);
                targetJar.closeEntry();
            }
        }
        ProcessBuilder proc = new ProcessBuilder("java", "-jar", target.toAbsolutePath() + File.separator + jarName);
        proc.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        Process p = proc.start();
        p.waitFor();
    }
}