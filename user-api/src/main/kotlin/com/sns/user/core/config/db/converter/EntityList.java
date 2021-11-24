package com.sns.user.core.config.db.converter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hyounglin Jun
 */
public class EntityList<E> extends ArrayList<E> {

    public EntityList(List<E> list) {
        super(list);
    }
}
