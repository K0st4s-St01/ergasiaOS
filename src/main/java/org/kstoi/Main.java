package org.kstoi;

import org.kstoi.scheduler.*;

public class Main {
    public static void main(String[] args) {
        ProcessGenerator generator= new ProcessGenerator(new RoundRobinWithVaryingTimeQuantum());
        generator.runExample();
    }
}