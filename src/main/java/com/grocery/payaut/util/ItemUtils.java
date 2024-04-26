package com.grocery.payaut.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.grocery.payaut.enumerator.ItemTypes;
import com.grocery.payaut.model.Item;

public class ItemUtils {

    public static long resolveBreadAge(Item item) {
        final long breadAge = item.getCreatedAt().until(LocalDateTime.now(), ChronoUnit.DAYS);
        return breadAge;
    }

    public static String resolveItemName(Item item) {
        if (item.getType().equals(ItemTypes.BREADS)) {
            final long breadAge = ItemUtils.resolveBreadAge(item);
            return "Breads (" + breadAge + " days old)";
        }

        return item.getName();
    }
}
