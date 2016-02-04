package com.solanteq.test;

import java.util.UUID;


/**
 * @author dkochelaev
 */
@FunctionalInterface
public interface DataConsumer {
    void accept(UUID key, int result);
}
