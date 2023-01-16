package com.applicationcore.core.util.test;

import org.junit.Test;

import com.applicationcore.core.util.ProfilesUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileUtilTest {

    @Test
    public void profilesTestingValues() {
        assertThat(ProfilesUtil.DEV).isEqualTo("dev");
        assertThat(ProfilesUtil.TEST).isEqualTo("test");
    }

}
