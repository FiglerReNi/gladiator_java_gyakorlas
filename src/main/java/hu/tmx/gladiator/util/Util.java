package hu.tmx.gladiator.util;

import org.ajbrown.namemachine.Gender;
import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;

import java.util.Random;

public class Util {

    public static final Random RANDOM = new Random();

    public static String randomName(){
        NameGenerator generator = new NameGenerator();
        Name name = generator.generateName(Gender.MALE);
        return name.getFirstName();
    }
}
