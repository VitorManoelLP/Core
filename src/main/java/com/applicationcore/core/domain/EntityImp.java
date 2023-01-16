package com.applicationcore.core.domain;

import java.io.Serializable;

public interface EntityImp<T extends Serializable> {

    T getId();

    void setId(T id);

}
