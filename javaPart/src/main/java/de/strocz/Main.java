package de.strocz;


import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class Main {
    public static void main(String[] args) throws Throwable {
        try (Arena arena = Arena.ofConfined()) {
            Linker linker = Linker.nativeLinker();

            // Lade native Bibliothek
            SymbolLookup loaderLookup = SymbolLookup.libraryLookup("/home/straczi/dev/uni/seminar/ffmSeminar/cPart/ffmExamplelib.so", arena);

            // Suche nach der "add"-Funktion
            MethodHandle addHandle = linker.downcallHandle(
                loaderLookup.find("add").orElseThrow(),
                FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)
            );

            // Funktionsaufruf
            int result = (int) addHandle.invoke(2, 3);
            System.out.println("Result of add(2, 3): " + result);
        }
    }
}
