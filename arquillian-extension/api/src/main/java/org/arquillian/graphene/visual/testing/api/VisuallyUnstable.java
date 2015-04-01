package org.arquillian.graphene.visual.testing.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *Annotation used over tests methods or test classes which are not stable enough for visual testing.
 * 
 * Such test classes or test methods will be excluded from visual testing.
 * @author jhuska
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface VisuallyUnstable {

}
