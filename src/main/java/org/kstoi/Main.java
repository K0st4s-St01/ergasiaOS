package org.kstoi;

import org.kstoi.scheduler.*;
import org.kstoi.scheduler2.MultipleQueues;

public class Main {
    public static void main(String[] args) {
        ProcessGenerator generator= new ProcessGenerator(new MultipleQueues());
        generator.runExample();
    }
}