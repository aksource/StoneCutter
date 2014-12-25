package StoneCutter;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class ItemStoneCutter extends Item
{
    private boolean repair = false;
    public ItemStoneCutter()
    {
        super();
        this.setMaxStackSize(1);
        this.setMaxDamage(16); //効果がわかりやすいように数値を低く設定
    }

    //アイテムがクラフト後に戻らないようにする 1.8では不要
//    @Override
//    public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack)
//    {
//        return false;
//    }

    //修理以外ならクラフト後にgetContainerItemStackを呼び出す
    @Override
    public boolean hasContainerItem()
    {
        return !repair;
    }

    //修理以外ならクラフト後にgetContainerItemStackを呼び出す。1.7.10以降はこちらを推奨
    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return !repair;
    }

    //修理かどうかを判定する
	@SubscribeEvent
	public void onCrafting(PlayerEvent.ItemCraftedEvent event)
	{
		//IDが無くなったので、アイテムインスタンスで比較。
		repair = StoneCutter.cutter == event.crafting.getItem();
	}
    //クラフト後のアイテムを、ダメージを与えて返す
    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        if (itemStack != null && itemStack.getItem() == this)
        {
            itemStack.setItemDamage(itemStack.getItemDamage() + 1);
        }
        return itemStack;
    }

    //既存のハサミと見分けるため、テクスチャを赤で乗算
    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        return 0xFF0000;
    }
}
