package com.pltfm.app.map;

import com.google.common.collect.ImmutableMap;

import com.pltfm.app.util.TransactionTypeEnum;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 交易流水类型枚举Map
 *
 * @author lijianjun
 * @since 15-06-26
 */
public class TransactionTypeEnumMap {
    private static Map<Integer, String> map = ImmutableMap.copyOf(new HashMap<Integer, String>(){
        {
            put(TransactionTypeEnum.TRANSACTION_TYPE_ONLINE_RECHARGE.getType(),
                    TransactionTypeEnum.TRANSACTION_TYPE_ONLINE_RECHARGE.getTitle());
            put(TransactionTypeEnum.TRANSACTION_TYPE_BACKGROUND_RECHARGE.getType(),
                    TransactionTypeEnum.TRANSACTION_TYPE_BACKGROUND_RECHARGE.getTitle());
            put(TransactionTypeEnum.TRANSACTION_TYPE_BALANCE_PAYMENT.getType(),
                    TransactionTypeEnum.TRANSACTION_TYPE_BALANCE_PAYMENT.getTitle());
            put(TransactionTypeEnum.TRANSACTION_TYPE_CANCEL_ORDER.getType(),
                    TransactionTypeEnum.TRANSACTION_TYPE_CANCEL_ORDER.getTitle());
            put(TransactionTypeEnum.TRANSACTION_TYPE_ORDER_REFUND.getType(),
                    TransactionTypeEnum.TRANSACTION_TYPE_ORDER_REFUND.getTitle());
          /*  put(TransactionTypeEnum.TRANSACTION_TYPE_INVITATION_AWARD.getType(),
                    TransactionTypeEnum.TRANSACTION_TYPE_INVITATION_AWARD.getTitle());*/
            put(TransactionTypeEnum.TRANSACTION_TYPE_MERCHAANT_CLEARING.getType(),
                    TransactionTypeEnum.TRANSACTION_TYPE_MERCHAANT_CLEARING.getTitle());
            put(TransactionTypeEnum.TRANSACTION_TYPE_BALANCE_FROZEN.getType(),
                    TransactionTypeEnum.TRANSACTION_TYPE_BALANCE_FROZEN.getTitle());
            put(TransactionTypeEnum.TRANSACTION_TYPE_BALANCE_THAW.getType(),
                    TransactionTypeEnum.TRANSACTION_TYPE_BALANCE_THAW.getTitle());
            /*put(TransactionTypeEnum.TRANSACTION_TYPE_CONSUMER_REBATES.getType(),
                    TransactionTypeEnum.TRANSACTION_TYPE_CONSUMER_REBATES.getTitle());
            put(TransactionTypeEnum.TRANSACTION_TYPE_PROMOTION_RETURN.getType(),
                    TransactionTypeEnum.TRANSACTION_TYPE_PROMOTION_RETURN.getTitle());
            put(TransactionTypeEnum.TRANSACTION_TYPE_DISTRIBUTION_RETURN.getType(),
                    TransactionTypeEnum.TRANSACTION_TYPE_DISTRIBUTION_RETURN.getTitle());*/
            put(TransactionTypeEnum.TRANSACTION_TYPE_PROMOTION_PAY.getType(),
                    TransactionTypeEnum.TRANSACTION_TYPE_PROMOTION_PAY.getTitle());
            put(TransactionTypeEnum.TRANSACTION_TYPE_PROMOTION_REFUND.getType(),
                    TransactionTypeEnum.TRANSACTION_TYPE_PROMOTION_REFUND.getTitle());
            put(TransactionTypeEnum.TRANSACTION_TYPE_MERCHAANT_TAKE.getType(),
                    TransactionTypeEnum.TRANSACTION_TYPE_MERCHAANT_TAKE.getTitle());
        }
    });

    public static Map<Integer, String> getMap() {

        return map;
    }

    public static String getValue(Integer key) {
        return map.get(key);
    }
}
