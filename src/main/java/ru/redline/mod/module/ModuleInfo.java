package ru.redline.mod.module;

import ru.redline.mod.network.packet.Packet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    String name();

    String id();

    Class<? extends Packet>[] packets() default {};

    String server() default "";
}
