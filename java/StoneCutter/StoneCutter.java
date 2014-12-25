package StoneCutter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid="StoneCutter", name="StoneCutter", version="@VERSION@",dependencies="required-after:FML")
//@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class StoneCutter
{
    public static final String MOD_ID = "StoneCutter";
	@Mod.Instance("StoneCutter")
	public static StoneCutter instance;
	public static Item cutter;
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event)
	{
		//アイテムの登録はpreInitで行う必要あり。
		cutter = (new ItemStoneCutter()).setUnlocalizedName("cutter")/*.setTextureName("shears")*/.setCreativeTab(CreativeTabs.tabTools);
		//1.7以降はこのメソッドで登録。
		GameRegistry.registerItem(cutter, "stone_cutter");
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
        //1.8以降はこのメソッドでテクスチャ/モデルJSONファイルを登録。クライアント側のみの処理
        if (event.getSide().isClient()) {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(cutter, 0, new ModelResourceLocation(MOD_ID + ":" + "stone_cutter", "inventory"));
        }
		//onCraftingがイベント処理に変わったので、FMLに登録。
		FMLCommonHandler.instance().bus().register(cutter);
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone_slab, 2, 3),
				new ItemStack(Blocks.cobblestone), new ItemStack(cutter,1,32767));
	}
}