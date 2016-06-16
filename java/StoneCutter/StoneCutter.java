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

@Mod(modid = StoneCutter.MOD_ID,
        name = StoneCutter.MOD_NAME,
        version = StoneCutter.MOD_VERSION,
        dependencies = StoneCutter.MOD_DEPENDENCIES,
        useMetadata = true,
        acceptedMinecraftVersions = StoneCutter.MOD_MC_VERSION)
public class StoneCutter {
    public static final String MOD_ID = "StoneCutter";
    public static final String MOD_NAME = "StoneCutter";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES = "required-after:FML";
    public static final String MOD_MC_VERSION = "[1.8,1.8.9]";

    public static Item cutter;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        //アイテムの登録はpreInitで行う必要あり。
        cutter = (new ItemStoneCutter()).setUnlocalizedName("cutter")/*.setTextureName("shears")*/.setCreativeTab(CreativeTabs.tabTools);
        //1.7以降はこのメソッドで登録。
        GameRegistry.registerItem(cutter, "stone_cutter");
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        //1.8以降はこのメソッドでテクスチャ/モデルJSONファイルを登録。クライアント側のみの処理
        if (event.getSide().isClient()) {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
                    cutter,
                    0,
                    new ModelResourceLocation(MOD_ID + ":" + "stone_cutter", "inventory"));
        }
        //onCraftingがイベント処理に変わったので、FMLに登録。
        FMLCommonHandler.instance().bus().register(cutter);
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone_slab, 2, 3),
                new ItemStack(Blocks.cobblestone), new ItemStack(cutter, 1, 32767));
    }
}