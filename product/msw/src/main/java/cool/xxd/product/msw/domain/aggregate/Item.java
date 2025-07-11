package cool.xxd.product.msw.domain.aggregate;

import cool.xxd.product.msw.domain.command.AddItemCommand;
import cool.xxd.product.msw.domain.enums.ItemType;
import lombok.Data;

@Data
public class Item {
    private Long id;
    private String code;
    private String name;
    private String itemTypeCode;
    private String itemTypeName;

    public void update(AddItemCommand addItemCommand) {
        this.name = addItemCommand.getName();
    }

    /**
     * 根据物品代码判断物品类型
     *
     * @param code 物品代码
     * @return 物品类型
     */
    public static ItemType getItemType(String code) {
        if (code == null || code.trim().isEmpty()) {
            return ItemType.OTHER;
        }
        try {
            int itemId = Integer.parseInt(code);

            // 装备类型判断
            if (isEquipment(itemId)) {
                return ItemType.EQUIP;
            }
            // 消耗品类型判断 (2000000-2999999)
            else if (itemId >= 2000000 && itemId <= 2999999) {
                return ItemType.USE;
            }
            // 其他类型判断 (4000000-4999999)
            else if (itemId >= 4000000 && itemId <= 4999999) {
                return ItemType.ETC;
            }
            // 未分类
            else {
                return ItemType.OTHER;
            }
        } catch (NumberFormatException e) {
            // 如果code不是数字，则归类为未分类
            return ItemType.OTHER;
        }
    }

    /**
     * 判断是否为装备类型
     *
     * @param itemId 物品ID
     * @return 是否为装备
     */
    public static boolean isEquipment(int itemId) {
        return (itemId >= 1000001 && itemId <= 1999999) ||
                (itemId >= 2060000 && itemId <= 2079999) ||
                (itemId >= 2330000 && itemId <= 2339999);
    }
}
