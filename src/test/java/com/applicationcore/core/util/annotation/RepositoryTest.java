package com.applicationcore.core.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.applicationcore.core.configurations.AspectConfigurations;
import com.applicationcore.core.util.ProfilesUtil;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@DataJpaTest
@Import({AspectConfigurations.class})
@ActiveProfiles(ProfilesUtil.TEST)
public @interface RepositoryTest {
}
